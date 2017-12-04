FROM hivesolutions/python:latest

LABEL version="1.0"
LABEL maintainer="João Magalhães <joamag@gmail.com>"

EXPOSE 8080

ENV HOST 0.0.0.0
ENV PORT 8080
ENV GRADLE_VERSION 4.3.1

ADD . /app

WORKDIR /app

RUN apk update && apk add openjdk8
RUN wget https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip && unzip gradle-$GRADLE_VERSION-bin.zip
RUN ln -s $(pwd)/gradle-$GRADLE_VERSION/bin/gradle /usr/bin/gradle
RUN gradle build

CMD ["gradle", "run"]
