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
#   ACTUATOR_HEADER_AUTH Actuator need http authorization, set it with base64
#
#   LEMON_LOGGER_LEVEL Should be DEBUG/INFO/WARN/ERROR
#
#   APM_OPTS        APM path
# -----------------------------------------------------------------------------
#global variable(version)
version=20210727

if [ "$1" = "version" ] ; then
  echo "version : ${version}"
  exit 1
elif [ ! -n "$1" ] ; then
  echo ""
else
  echo "Usage: offline.sh ( commands ... )"
  echo "commands:"
  echo "  version           What version are you running?"
  exit 1
fi

#global variable(version)
version=20210414

if [ "$1" = "version" ] ; then
  echo "version : ${version}"
  exit 1
elif [ ! -n "$1" ] ; then
  echo ""
else
  echo "Usage: offline.sh ( commands ... )"
  echo "commands:"
  echo "  version           What version are you running?"
  exit 1
fi

#function defined
offline() {
  #curl --connect-timeout 5 -m 15 -X POST http://127.0.0.1:${PORT}/offline
  local OFFLINE_ENDPOINT="http://127.0.0.1:${PORT}/${ENDPOINTS_PREFIX}offline"
  echo "Starting offline application, url: ${OFFLINE_ENDPOINT}"
  httpcode=$(curl -I -m 60 -o /dev/null -s -w %{http_code} -X POST -H "${HTTP_HEADER_AUTH}" ${OFFLINE_ENDPOINT})
  if [ ${httpcode} -eq 200 ]; then
    echo
    for wi in {1..60}; do
      echo -n "."
      sleep 1s
    done
    echo
  else
    echo "offline failed, http code is: ${httpcode}"
    exit 2
  fi
}

checkSpringBootVersion() {
  SPRING_BOOT_VERSION=$(unzip -q -p ${LEMON_JAR} META-INF/MANIFEST.MF | awk '/Spring-Boot-Version:/ {print$2}')
  # SPRING_BOOT_VERSION=$(jar tf ${LEMON_JAR} BOOT-INF/lib/spring-boot-starter|egrep "spring-boot-starter-([0-9]).([0-9])+.([0-9])+.RELEASE.jar"|egrep -o "([0-9]).([0-9])+.([0-9])+")

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
  PORT=$(netstat -anp 2>/dev/null | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' |grep ${MANAGEMENT_PORT})
}

getManagementPortByNet() {
  PID=$1
  # 随机取一个监听的端口，历史遗留处理
  PORT=$(netstat -anp 2>/dev/null | grep -w "${PID}" | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' | grep -v ${MANAGEMENT_PORT})
}

# Lemon home setting
if [ -z "${LEMON_HOME}" ]; then
  #本shell所在的上级目录
  # LEMON_HOME=$HOME
  LEMON_HOME="$(
    cd $(dirname $0)
    cd ..
    pwd
  )"
  # 需保证脚本运行目录在 LEMON_HOME 下统一生成、读取 application.pid、application.port
  cd "$LEMON_HOME"
fi

# The home of executable jar
if [ -z "${LEMON_JAR_HOME}" ]; then
  LEMON_JAR_HOME=${LEMON_HOME}/lib
fi

LEMON_JAR=$(ls ${LEMON_JAR_HOME}/*.jar)
if [ ! -f ${LEMON_JAR} ]; then
  LEMON_JAR=${LEMON_HOME}/${LEMON_JAR}
fi

# Actuator authorization setting
if [ -z "${ACTUATOR_HEADER_AUTH}" ]; then
  ACTUATOR_HEADER_AUTH="bGVtb25BY3R1YXRvcjpsZW1vbkFjdHVhdG9yMTIzIw=="
fi
export HTTP_HEADER_AUTH="Authorization: Basic ${ACTUATOR_HEADER_AUTH}"

checkSpringBootVersion

# Get application ID
APPID=$(echo $LEMON_HOME | awk -F "/" '{print $NF}')
echo $APPID

# Set PID file
PID_FILE="${LEMON_HOME}/${APPID}.pid"
MANAGEMENT_PORT=9527

# Check if server is running
if [ -f "${PID_FILE}" ]; then
  EXIST_PID=$(cat "${PID_FILE}")
  num=$(ps -p "${EXIST_PID}" | grep -c "${EXIST_PID}")
  if [ ${num} -ge 1 ]; then
    echo "An existing server[${EXIST_PID}] is running, starting to offline it, please wait for a moment."
    getManagementPortByFile

    if [ -z "${PORT}" ]; then
      getManagementDefaultPort
    fi

    if [ -z "${PORT}" ]; then
        getManagementPortByNet ${EXIST_PID}
    fi
    offline
  fi
fi
