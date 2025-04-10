FROM openjdk:8-jdk

ENV FOLDER="/home/jenkins/docker"
ENV MODULE_NAME="jenkins-demo"
ENV CONFIG_FILE="application.properties"

ENV CONFIG_FOLDER="$FOLDER/config"
ENV JAVA_OPTS="-Dfile.encoding=UTF8 -Dspring.config.location=$CONFIG_FOLDER/$CONFIG_FILE"
ENV JAR_FILE="$MODULE_NAME.jar"
ENV MODULE_OPTS=""

WORKDIR $FOLDER

COPY target/jenkins-demo/jenkins-demo.jar jenkins-demo.jar

COPY target/jenkins-demo/lib lib

COPY config/application.properties config/application.properties
COPY config/log4j2.xml config/log4j2.xml

CMD java $JAVA_OPTS -jar $JAR_FILE $MODULE_OPTS


