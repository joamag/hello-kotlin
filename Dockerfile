FROM hivesolutions/python:latest

LABEL version="1.0"
LABEL maintainer="João Magalhães <joamag@gmail.com>"

EXPOSE 8080

ENV HOST 0.0.0.0
ENV PORT 8080

RUN pip3 install -r /requirements.txt && pip3 install -r /extra.txt && pip3 install --upgrade netius

CMD ["/usr/bin/python3", "/src/hello_appier/main.py"]
