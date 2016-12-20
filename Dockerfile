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

# install aws cli profile
RUN pip install awscli --ignore-installed six

# login to aws and run script
CMD  aws configure set aws_access_key_id ${KEY_ID} && aws configure set aws_secret_access_key ${ACCESS_KEY} && $(aws ecr get-login --region us-east-1) && python /opt/app/docker_registry_discovery.py