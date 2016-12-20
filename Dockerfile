FROM docker:1.11.2

# install python
RUN apk add --no-cache python && \
    python -m ensurepip && \
    rm -r /usr/lib/python*/ensurepip && \
    pip install --upgrade pip setuptools && \
    rm -r /root/.cache

#copy code
COPY docker_registry_discovery.py /opt/app/
COPY config.yml /opt/app/

#define temp workdir
WORKDIR /opt/app

# login to aws and run script
CMD  python /opt/app/docker_registry_discovery.py