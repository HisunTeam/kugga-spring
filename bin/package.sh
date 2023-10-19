#!/bin/bash

cd ~/lib/kugga-spring || exit;
#git checkout origin/release/dev
git pull;

# Check the number of parameters passed to the script
case "$#"
in
0 )
  # If no parameters, build the project with Maven, skipping tests
  mvn clean package -Dmaven.test.skip=true;
  # After packaging is successful, change permissions
  sudo chmod -R g+w ${HOME}/lib/kugga-spring;
  ;;
1 )
  # If parameter is "-x", skip Maven packaging
  if [ "$1" = "-x" ]; then
      echo "skip maven package..."
  fi
  ;;
* )
  # If more than one parameter, show an error message and exit
  echo "THE PARAMETERS MUST BE TWO OR LESS.PLEASE CHECK AGAIN."
  exit;;
esac

#./gradlew :jack-manage-module-system:clean;
#./gradlew :jack-manage-module-system:bootJar;
# Check if the file exists
#
if [ -z "${MODULE_NAME}" ]; then
  #MODULE_NAME=kugga-server-duke
  MODULE_NAME=kugga-server-batch
fi

# Get the name of the new jar file
NEW_JAR_NAME=`ls ${HOME}/lib/kugga-spring/${MODULE_NAME}/target/*.jar|tail -n 1`
# If the jar file doesn't exist, exit with an error
if [ ! -f "$NEW_JAR_NAME" ]; then
    echo "WARN: bootJar failed! please check!"
    exit 1
fi
# A short pause
for wi in {1..5}; do
    echo -n "."
    sleep 1s
done
# Move the existing jar to backup
JAR_NAME=`ls  ${HOME}/lib/*.jar|tail -n 1`
echo "mv [${JAR_NAME}] to backup..."
mv $JAR_NAME  ${HOME}/lib/backup/
# Remove backups older than 3 days
find ${HOME}/lib/backup/ -type d -mtime +3|xargs rm -rf
# Move the new jar to the lib directory
echo "copy [${NEW_JAR_NAME}] to lib...";
mv $NEW_JAR_NAME ${HOME}/lib/;
# Successful packaging, start running the application
echo "success package ... start to run application.";
sh ~/bin/start.sh;
