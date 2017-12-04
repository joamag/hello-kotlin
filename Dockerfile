FROM hivesolutions/python:latest

LABEL version="1.0"
LABEL maintainer="João Magalhães <joamag@gmail.com>"

EXPOSE 8080

ENV HOST 0.0.0.0
ENV PORT 8080

ADD . /app

RUN apk update && apk add openjdk8
RUN wget https://services.gradle.org/distributions/gradle-4.3.1-bin.zip && unzip gradle-4.3.1-bin.zip
RUN ln -s $(pwd)/gradle-4.3.1/bin/gradle /usr/bin/gradle
RUN gradle build

CMD ["gradle", "run"]
