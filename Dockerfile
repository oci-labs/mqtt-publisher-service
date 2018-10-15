FROM java:openjdk-8u111-alpine
RUN apk --no-cache add curl
COPY build/libs/*-all.jar devo-mqtt-publisher.jar
CMD java ${JAVA_OPTS} -jar devo-mqtt-publisher.jar