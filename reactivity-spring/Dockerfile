FROM openjdk:8-jre-alpine

COPY target/reactiveSpringMeetup-0.0.1-SNAPSHOT.jar /opt/app.jar
COPY data.txt /opt/data.txt

WORKDIR /opt

CMD java -Duser.timezone=GMT -Djava.security.egd=file:/dev/./urandom -Xms1g -Xmx13g -XX:+UseParallelGC -XX:NewRatio=3 -jar /opt/app.jar
