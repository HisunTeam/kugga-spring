#!/usr/bin/env bash
# -----------------------------------------------------------------------------
# Environment Variable Prerequisites
#
#   LEMON_HOME      Points to the lemon server home directory.
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
#   ACTUATOR_HEADER_AUTH Actuator needs HTTP authorization, set it with base64
#
#   LEMON_LOGGER_LEVEL Should be DEBUG/INFO/WARN/ERROR
#
#   APM_OPTS        APM path
# -----------------------------------------------------------------------------
# Global variable (version)
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

# Global variable (version)
version=20210414

if [ "$1" = "version" ] ; then
  echo "version : ${version}"
  exit 1
elif [ ! -n "$1" ] ; then
  echo ""
else
  echo "Usage: health.sh ( commands ... )"
  echo "commands:"
  echo "  version           What version are you running?"
  exit 1
fi

# Function definition
health_check() {
  local HEALTH_ENDPOINT="http://127.0.0.1:${PORT}/${ENDPOINTS_PREFIX}health"
  result=$(curl -H "${HTTP_HEADER_AUTH}" ${HEALTH_ENDPOINT})
  echo ${result}
}

checkSpringBootVersion() {
  # SPRING_BOOT_VERSION=$(unzip -q -p ${LEMON_JAR} META-INF/MANIFEST.MF | awk '/Spring-Boot-Version:/ {print$2}')
  SPRING_BOOT_VERSION=$(jar tf ${LEMON_JAR} BOOT-INF/lib/spring-boot-starter|egrep "spring-boot-starter-([0-9]).([0-9])+.([0-9])+.RELEASE.jar"|egrep -o "([0-9]).([0-9])+.([0-9])+")

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

# Check if the default port is listening
getManagementDefaultPort() {
  PORT=$(netstat -anp 2>/dev/null | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' |grep ${MANAGEMENT_PORT})
}

getManagementPortByNet() {
  PID=$1
  # Randomly select a listening port, a legacy treatment
  PORT=$(netstat -anp 2>/dev/null | grep -w "${PID}" | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' | grep -v ${MANAGEMENT_PORT})
}

# Lemon home setting
if [ -z "${LEMON_HOME}" ]; then
  # The parent directory of the current shell script
  # LEMON_HOME=$HOME
  LEMON_HOME="$(
    cd $(dirname $0)
    cd ..
    pwd
  )"
  # Ensure script execution directory is under LEMON_HOME for consistent generation and reading of application.pid, application.port
  cd "$LEMON_HOME"
fi

# The home of the executable jar
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

# Check if the server is running
if [ -f "${PID_FILE}" ]; then
  EXIST_PID=$(cat "${PID_FILE}")
  num=$(ps -p "${EXIST_PID}" | grep -c "${EXIST_PID}")
  if [ ${num} -ge 1 ]; then
    echo "An existing server[${EXIST_PID}] is running, starting health check, please wait for a moment."
    getManagementPortByFile

    if [ -z "${PORT}" ]; then
      getManagementDefaultPort
    fi

    if [ -z "${PORT}" ]; then
        getManagementPortByNet ${EXIST_PID}
    fi
    health_check
  fi
fi
