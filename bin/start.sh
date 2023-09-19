#!/usr/bin/env bash
# -----------------------------------------------------------------------------
# Environment Variable Prerequisites
#
#   LEMON_HOME      Point to the lemon server home directory.
#
#   LEMON_ENV       Should be dev/sit/uat/str/pre/prd.
#
#   JAVA_HOME       Must point at your Java Development Kit installation.
#
#   JAVA_OPTS       (Optional) Java runtime options used when any command
#                   is executed.
#
#   LEMON_BATCH_ENABLED Should be true/false, default false
#
#   LEMON_LOGGER_LEVEL Should be DEBUG/INFO/WARN/ERROR
#
#   ACTUATOR_HEADER_AUTH Actuator need http authorization, set it with base64
#
#   APM_OPTS        APM path
# -----------------------------------------------------------------------------

#global variable(version)
version=20210727

if [ "$1" = "version" ] ; then
  echo "version : ${version}"
  exit 1
elif [ ! -n "$1" ] ; then
  echo " ███╗  ██╗ ██████╗     ██████╗ ██╗   ██╗ ██████╗"
  echo "████╗  ██║██╔═══██╗    ██╔══██╗██║   ██║██╔════╝ "
  echo "██╔██╗ ██║██║   ██║    ██████╔╝██║   ██║██║  ███╗"
  echo "██║╚██╗██║██║   ██║    ██╔══██╗██║   ██║██║   ██║"
  echo "██║ ╚████║╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝"
  echo "╚═╝  ╚═══╝ ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ "

else
  echo "Usage: start.sh ( commands ... )"
  echo "commands:"
  echo "  version           What version are you running?"
  exit 1
fi

#function defined

lock() {
  # use flock preventing duplicate
  scriptname=$(basename "$0")
  lock="${HOME}/.${scriptname}.lock"
  exec 200>"$lock"
  if ! flock -n 200; then
    echo "WARN: the script is already running."
    exit 4
  fi
}

unlock() {
  flock --unlock 200
}
trap unlock EXIT

offline() {
  #curl --connect-timeout 5 -m 15 -X POST http://127.0.0.1:${PORT}/offline
  local OFFLINE_ENDPOINT="http://127.0.0.1:${PORT}/${ENDPOINTS_PREFIX}offline"
  echo "Starting offline application, url: ${OFFLINE_ENDPOINT}"
  httpcode=$(curl -I -m 60 -o /dev/null -s -w %{http_code} -X POST -H "${HTTP_HEADER_AUTH}" ${OFFLINE_ENDPOINT})
  if [ "${httpcode}" -eq 200 ]; then
    echo
    for wi in {1..5}; do
      echo -n "."
      sleep 1s
    done
    echo
  else
    echo "offline failed, http code is: ${httpcode}"
  fi
}

shutdown() {
  local SHUTDOWN_ENDPOINT="http://127.0.0.1:${PORT}/${ENDPOINTS_PREFIX}shutdown"
  echo "Starting shutdown application, url: ${SHUTDOWN_ENDPOINT}"
  httpcode=$(curl --connect-timeout 5 -m 15 -o /dev/null -w %{http_code} -X POST -H "${HTTP_HEADER_AUTH}" "${SHUTDOWN_ENDPOINT}")
  if [ "${httpcode}" -eq 200 ]; then
      echo
      for wi in {1..10}; do
        echo -n "."
        sleep 1s
      done
      echo
    else
      echo "shutdown failed, http code is: ${httpcode}"
    fi
}

forceShutdown() {
  kill -9 "$1"
  for wi in {1..5}; do
    echo -n "."
    sleep 1s
  done
  echo
}

healthCheck() {
  local HEALTH_ENDPOINT="http://127.0.0.1:${PORT}/${ENDPOINTS_PREFIX}health"
  echo "Starting health check, url: ${HEALTH_ENDPOINT}"
  HEALTH_RESULT=$(curl -m 60 -s -X GET -H "${HTTP_HEADER_AUTH}" "${HEALTH_ENDPOINT}")
  STATUS=$(echo "${HEALTH_RESULT}" | awk -F ":{" '{print $1}' | awk -F "status\":\"" '{print $2}'|awk -F "\"" '{print $1}')
  if [ "${STATUS}" != "UP" ]; then
    HEALTH_ENDPOINT="http://127.0.0.1:${MANAGEMENT_PORT}/${ENDPOINTS_PREFIX}health"
    echo "First Health check fail, Starting health check again, url: ${HEALTH_ENDPOINT}"
    HEALTH_RESULT=$(curl -m 60 -s -X GET -H "${HTTP_HEADER_AUTH}" "${HEALTH_ENDPOINT}")
    sleep 1s
    STATUS=$(echo "${HEALTH_RESULT}" | awk -F ":{" '{print $1}' | awk -F "status\":\"" '{print $2}'|awk -F "\"" '{print $1}')
    if [ "${STATUS}" != "UP" ]; then
      echo "WARN: Health check failure, service status is ${STATUS}"
      exit 2
    fi
  fi
  echo "Health check passed"
}

environmentCheck() {

  if [ -z "${LEMON_ENV}" ]; then
    echo "WARN: LEMON_ENV environment variable not defined, value should be dev/sit/uat/str/pre/prd."
    exit 1
  fi

  if [ -z "${JAVA_HOME}" ]; then
    echo "WARN: JAVA_HOME environment variable not defined."
    exit 1
  fi

  #Check ulimit -n setting
  ulimit=$(ulimit -n)
  if [ ${ulimit} -le 10000 ]; then
    echo "WARN: the ulimit -n ${ulimit} setting is too small"
    exit 1
  fi

}

checkSpringBootVersion() {
  SPRING_BOOT_VERSION=$(unzip -q -p ${LEMON_JAR} META-INF/MANIFEST.MF | awk '/Spring-Boot-Version:/ {print$2}')
  #SPRING_BOOT_VERSION=$(jar tf ${LEMON_JAR} BOOT-INF/lib/spring-boot-starter|egrep "spring-boot-starter-([0-9]).([0-9])+.([0-9])+.RELEASE.jar"|egrep -o "([0-9]).([0-9])+.([0-9])+")

  echo "Spring-Boot-Version: ${SPRING_BOOT_VERSION}"

  if [[ ${SPRING_BOOT_VERSION} == 1.* ]]; then
    ENDPOINTS_PREFIX=""
  fi

  if [[ ${SPRING_BOOT_VERSION} == 2.* ]]; then
    ENDPOINTS_PREFIX="actuator/"
  fi
}

getManagementPortByFile() {
  local MANAGEMENT_PORT_FILE="${LEMON_HOME}/application-management.port"

  if [ -f "${MANAGEMENT_PORT_FILE}" ]; then
    PORT=$(cat "${MANAGEMENT_PORT_FILE}")
  fi
}

# 检查默认端口是否监听
getManagementDefaultPort() {
  PORT=$(netstat -anp 2>/dev/null | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' | grep ${MANAGEMENT_PORT})
}

getManagementPortByNet() {
  PID=$1
  # 随机取一个监听的端口，历史遗留处理
  PORT=$(netstat -anp 2>/dev/null | grep -w "${PID}" | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' | grep -v ${MANAGEMENT_PORT})
}

# 从落地文件中获取应用端口
getApplicationPortByFile() {
  local PORT_FILE="${LEMON_HOME}/application.port"
  if [ -f "${PORT_FILE}" ]; then
      APPLICATION_PORT=$(cat "${PORT_FILE}")
  fi
}

# 从启动监听端口中，获取应用端口
getApplicationPortByNet() {
  PID=$1
  # 随机取一个监听的端口，历史遗留处理
  APPLICATION_PORT=$(netstat -anp 2>/dev/null | grep -w "${PID}" | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}'|grep -v ${MANAGEMENT_PORT})
}


# Main class of starting lemon server
# LEMON_MAIN="com.cmpay.lemon.Application"

# system env setting
source ~/.bash_profile
ulimit -n 60000
umask 0022
export LANG=zh_CN.utf8

# check if the script is already running
lock

# Lemon home setting
if [ -z "${LEMON_HOME}" ]; then
  #本shell所在的上级目录
  # LEMON_HOME=$HOME
  LEMON_HOME="$(
    cd $(dirname $0)
    cd ..
    pwd
  )"
  # 需保证脚本运行目录在 LEMON_HOME 下统一生成、读取application.pid、application.port
  cd "$LEMON_HOME"
fi

# The home of executable jar
if [ -z "${LEMON_JAR_HOME}" ]; then
  LEMON_JAR_HOME=${LEMON_HOME}/lib
fi

# LEMON_LOGGER_LEVEL default
if [ -z "${LEMON_LOGGER_LEVEL}" ]; then
  LEMON_LOGGER_LEVEL="INFO"
fi

# Actuator authorization setting
if [ -z "${ACTUATOR_HEADER_AUTH}" ]; then
  ACTUATOR_HEADER_AUTH="bGVtb25BY3R1YXRvcjpsZW1vbkFjdHVhdG9yMTIzIw=="
fi
export HTTP_HEADER_AUTH="Authorization: Basic ${ACTUATOR_HEADER_AUTH}"

# Check environment variables
environmentCheck

# Executable jar of lemon server
if [ $(ls ${LEMON_JAR_HOME}/*.jar | wc -l) -gt 1 ]; then
  echo "WARN: Can only have one start jar file in lemon home $LEMON_HOME"
  exit 1
fi

LEMON_JAR=$(ls ${LEMON_JAR_HOME}/*.jar)
if [ ! -f ${LEMON_JAR} ]; then
  LEMON_JAR=${LEMON_HOME}/${LEMON_JAR}
fi
echo $LEMON_JAR

checkSpringBootVersion

# Get application ID
APPID=$(echo $LEMON_HOME | awk -F "/" '{print $NF}')
echo "APP_ID: $APPID"

# Set PID file
PID_FILE="${LEMON_HOME}/${APPID}.pid"
echo "PID_FILE: $PID_FILE"

# Set default actuator port
MANAGEMENT_PORT=9527

# Check if server is running
if [ -f "${PID_FILE}" ]; then
  EXIST_PID=$(cat "${PID_FILE}")
  num=$(ps -p "${EXIST_PID}" | grep "${EXIST_PID}" | wc -l)
  if [ ${num} -ge 1 ]; then

    echo "An existing server[${EXIST_PID}] is running, starting to graceful shutdown it, please wait for a moment."

    # Graceful Shutdown
    getManagementPortByFile

    if [ -z "${PORT}" ]; then
        getManagementDefaultPort
    fi

    # 若management端口未监听，默认使用应用端口做为management端口
    if [ -z "${PORT}" ]; then
        getManagementPortByNet ${EXIST_PID}
    fi

    #offline
    shutdown 

    num=$(ps -p "${EXIST_PID}" | grep "${EXIST_PID}" | wc -l)
    if [ ${num} -ge 1 ]; then
      echo "The server [${EXIST_PID}] has not stopped yet, force shutdown it."
      forceShutdown ${EXIST_PID}
    fi
  fi
fi

# logs settting
LOG_DIR="${LEMON_HOME}/logs"
$(mkdir -p "${LOG_DIR}")
OUT_FILE="${LOG_DIR}/server.out"
ERR_FILE="${LOG_DIR}/server.err"
GC_FILE="/dev/shm/server-${APPID}-gc.log"

# rename gc log
TS=$(date +%Y%m%d%H%M%S)
if [ -e "${GC_FILE}" ]; then
  mv "${GC_FILE}" "${LEMON_HOME}/backup/server-${APPID}-gc.log_${TS}"
fi

# Set options for server starting
case ${LEMON_ENV} in
prd)
  if [ -z "${MEM_SIZE}" ]; then
    MEM_SIZE="2g"
  fi
  if [ -z "${MAX_METASPACE_SIZE}" ]; then
    MAX_METASPACE_SIZE="256m"
  fi
  ;;
*)
  if [ -z "${MEM_SIZE}" ]; then
    MEM_SIZE="512m"
  fi
  if [ -z "${MAX_METASPACE_SIZE}" ]; then
    MAX_METASPACE_SIZE="256m"
  fi
  ;;
esac

MEM_OPTS="-Xms${MEM_SIZE} -Xmx${MEM_SIZE} -XX:MaxMetaspaceSize=${MAX_METASPACE_SIZE}"
GC_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=50 -verbose:gc -Xloggc:${GC_FILE} -XX:+PrintGCDateStamps -XX:+PrintGCDetails"
DEBUG_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:+UnlockCommercialFeatures -XX:+FlightRecorder"
DEBUG_OPTS="-XX:AutoBoxCacheMax=10000 -XX:+PrintCommandLineFlags ${DEBUG_OPTS}"
JAVA_OPTS="-server -Djava.security.egd=file:/dev/./urandom -Dfastjson.parser.safeMode=true ${JAVA_OPTS} ${MEM_OPTS} ${DEBUG_OPTS} ${GC_OPTS} "

CLASSPATH="."

if [ ! -z "${LEMON_BATCH_ENABLED}" ]; then
  LEMON_BATCH_ENABLE="-Dlemon.batch.enabled=${LEMON_BATCH_ENABLED}"
fi

if [ ! -z "${LEMON_LOGGER_LEVEL}" ]; then
  LEMON_LOGGER_LEVEL_OPT="-Dlemon.logger.level=${LEMON_LOGGER_LEVEL}"
fi

LEMON_OPTS="-Dlemon.env=${LEMON_ENV} -Dlemon.home=${LEMON_HOME} -Dlemon.log.path=${LOG_DIR} ${LEMON_LOGGER_LEVEL_OPT} ${LEMON_BATCH_ENABLE}"

SPRING_OPTS_ACTIVE="--spring.profiles.active=${LEMON_ENV}"
if [ -z "${SPRING_OPTS}" ]; then
  SPRING_OPTS="--spring.cloud.inetutils.preferredNetworks=172"
fi
SPRING_OPTS="${SPRING_OPTS} ${SPRING_OPTS_ACTIVE}"

echo "--------------------------------------------------"
echo "Starting Lemon Server "
echo "--------------------------------------------------"
echo "LEMON_HOME   : ${LEMON_HOME}"
echo "LEMON_ENV    : ${LEMON_ENV}"
echo "LEMON_JAR    : ${LEMON_JAR}"
echo "LEMON_OPTS   : ${LEMON_OPTS}"
echo "SPRING_OPTS  : ${SPRING_OPTS}"
echo "LOG_DIR      : ${LOG_DIR}"
echo "JAVA_HOME    : ${JAVA_HOME}"
echo "JAVA_OPTS    : ${JAVA_OPTS}"
echo "APM_OPTS     : ${APM_OPTS}"
echo "--------------------------------------------------"

startTm_s=$(date +%s)
# Start server
RUN_CMD="${JAVA_HOME}/bin/java ${JAVA_OPTS} ${APM_OPTS} ${LEMON_OPTS} -jar ${LEMON_JAR} ${SPRING_OPTS}"
echo "Ready to run Lemon Server with command: " >${OUT_FILE}
echo "${RUN_CMD}" >>${OUT_FILE}
nohup ${RUN_CMD} >>${OUT_FILE} 2>${ERR_FILE} &

# Save PID file
PID=$!
echo ${PID} >"${PID_FILE}"

# Waiting for server starting
echo -n "Waiting for server[${PID}] to start."
start_sec=0
max_sec=420

getManagementPortByFile
while [ ${start_sec} -lt ${max_sec} ]; do

  if [ -z "${PORT}" ]; then
    num=$(netstat -anp 2>/dev/null | grep -w "${PID}" | grep -w "LISTEN" |wc -l)
  else
    num=$(netstat -anp 2>/dev/null | grep -w "${PID}" | grep -w "${PORT}" | grep -w "LISTEN" |wc -l)
  fi

  if [ "${num}" -ge 1 ]; then
    endTm_s=$(date +%s)
    execTm=$((endTm_s - startTm_s))

    getApplicationPortByFile
    if [ -z "${APPLICATION_PORT}" ]; then
      getApplicationPortByNet ${PID}
    fi

    if [ -z "${APPLICATION_PORT}" ]; then
      start_sec=$(expr ${start_sec} + 1)
      echo -n "."
      sleep 1
    fi

    echo
    echo "Lemon started on port(s): ${APPLICATION_PORT}"

    # 获取健康检查端口
    if [ -z "${PORT}" ]; then
      getManagementDefaultPort
    fi

    if [ -z "${PORT}" ]; then
      getManagementPortByNet ${PID}
    fi

    sleep 5s
    healthCheck
    echo "Started Lemon Application in ($execTm) seconds "
    exit 0
  fi

  start_sec=$(expr ${start_sec} + 1)
  echo -n "."
  sleep 1
done

echo
echo "WARN: Server did not started in ${max_sec} seconds, please check log files."
exit 3