#!/bin/bash

cd ~/lib/kugga-spring || exit;
#git checkout origin/release/dev
git pull;

case "$#"
in
0 )
  mvn clean package -Dmaven.test.skip=true;
  # 打包成功后，修改权限
  sudo chmod -R g+w ${HOME}/lib/kugga-spring;
  ;;
1 )
  if [ "$1" = "-x" ]; then
      echo "skip maven package..."
  fi
  ;;
* )
  echo "THE PARAMETERS MUST BE TWO OR LESS.PLEASE CHECK AGAIN."
  exit;;
esac

#./gradlew :jack-manage-module-system:clean;
#./gradlew :jack-manage-module-system:bootJar;
#check file is exist
#
if [ -z "${MODULE_NAME}" ]; then
  #MODULE_NAME=kugga-server-duke
  MODULE_NAME=kugga-server-batch
fi

NEW_JAR_NAME=`ls ${HOME}/lib/kugga-spring/${MODULE_NAME}/target/*.jar|tail -n 1`
if [ ! -f "$NEW_JAR_NAME" ]; then
    echo "WARN: bootJar failed! please check!"
    exit 1
fi
#stop while
for wi in {1..5}; do
    echo -n "."
    sleep 1s
done
JAR_NAME=`ls  ${HOME}/lib/*.jar|tail -n 1`
echo "mv [${JAR_NAME}] to backup..."
mv $JAR_NAME  ${HOME}/lib/backup/
find ${HOME}/lib/backup/ -type d -mtime +3|xargs rm -rf
echo "copy [${NEW_JAR_NAME}] to lib...";
mv $NEW_JAR_NAME ${HOME}/lib/;
echo "success package ... start to run application.";
sh ~/bin/start.sh;
