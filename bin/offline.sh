#!/usr/bin/env bash
# -----------------------------------------------------------------------------
# Environment Variable Prerequisites
#
#   LEMON_HOME      (Required) The home directory of the lemon server.
#
#   LEMON_ENV       (Optional) The deployment environment. Can be one of dev/sit/uat/str/pre/prd.
#
#   JAVA_HOME       (Required) Must point at your Java Development Kit installation.
#
#   JAVA_OPTS       (Optional) Java runtime options used when the command is executed.
#
#   LEMON_BATCH_ENABLED (Optional) Should be true/false, default is false.
#
#   ACTUATOR_HEADER_AUTH (Required for secured actuator endpoints) For HTTP basic authentication, set it with base64-encoded credentials.
#
#   LEMON_LOGGER_LEVEL (Optional) The logging level. Can be one of DEBUG/INFO/WARN/ERROR.
#
#   APM_OPTS        (Optional) The path for the APM if used.
# -----------------------------------------------------------------------------

# Global variable for version control. It seems to be manually set and may indicate the script version.
version=20210727

# Handling input arguments for the script. It looks like it's meant to handle a 'version' command.
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

# A duplicate version variable. This might be an error or for different versioning (e.g., script vs application).
version=20210414

# Duplicate handling of the 'version' command. This might be an error.
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

# Function to take the application offline, presumably using Spring Boot's actuator endpoints.
offline() {
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

# Function to check the Spring Boot version used in the application. It dictates the actuator endpoint prefix.
checkSpringBootVersion() {
  SPRING_BOOT_VERSION=$(unzip -q -p ${LEMON_JAR} META-INF/MANIFEST.MF | awk '/Spring-Boot-Version:/ {print$2}')

  echo "Spring-Boot-Version: ${SPRING_BOOT_VERSION}"

  if [[ ${SPRING_BOOT_VERSION} == 1.* ]]; then
    ENDPOINTS_PREFIX=""
  fi

  if [[ ${SPRING_BOOT_VERSION} == 2.* ]]; then
    ENDPOINTS_PREFIX="actuator/"
  fi
}

# Function to get the management port from a file.
getManagementPortByFile() {
  local MANAGEMENT_PORT_FILE="${LEMON_HOME}/application-management.port"

  if [ -f "${MANAGEMENT_PORT_FILE}" ]; then
    PORT=$(cat "${MANAGEMENT_PORT_FILE}")
  fi
}

# Function to check the default port status.
getManagementDefaultPort() {
  PORT=$(netstat -anp 2>/dev/null | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' |grep ${MANAGEMENT_PORT})
}

# Function to get the management port dynamically based on the process ID.
getManagementPortByNet() {
  PID=$1
  PORT=$(netstat -anp 2>/dev/null | grep -w "${PID}" | grep -w "LISTEN" | awk '{print $4}' | awk -F ":" '{print $NF}' | grep -v ${MANAGEMENT_PORT})
}

# Setting up the Lemon home directory.
if [ -z "${LEMON_HOME}" ]; then
  LEMON_HOME="$(
    cd $(dirname $0)
    cd ..
    pwd
  )"
  cd "$LEMON_HOME"
fi

# Setting up the home of the executable jar.
if [ -z "${LEMON_JAR_HOME}" ]; then
  LEMON_JAR_HOME=${LEMON_HOME}/lib
fi

LEMON_JAR=$(ls ${LEMON_JAR_HOME}/*.jar)
if [ ! -f ${LEMON_JAR} ]; then
  LEMON_JAR=${LEMON_HOME}/${LEMON_JAR}
fi

# Setting up actuator authorization.
if [ -z "${ACTUATOR_HEADER_AUTH}" ]; then
  ACTUATOR_HEADER_AUTH="bGVtb25BY3R1YXRvcjpsZW1vbkFjdHVhdG9yMTIzIw=="
fi
export HTTP_HEADER_AUTH="Authorization: Basic ${ACTUATOR_HEADER_AUTH}"

checkSpringBootVersion

# Get application ID.
APPID=$(echo $LEMON_HOME | awk -F "/" '{print $NF}')
echo $APPID

# Set PID file.
PID_FILE="${LEMON_HOME}/${APPID}.pid"
MANAGEMENT_PORT=9527

# Check if the server is running and take it offline if it is.
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
