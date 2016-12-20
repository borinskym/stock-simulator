FROM docker:1.11.2

# install python
RUN apk add --no-cache python && \
    python -m ensurepip && \
    rm -r /usr/lib/python*/ensurepip && \
    pip install --upgrade pip setuptools && \
    rm -r /root/.cache
# install aws cli profile
RUN pip install awscli --ignore-installed six
RUN aws configure set aws_access_key_id AKIAIOVK75FC3AX6DKFQ
 RUN aws configure set aws_secret_access_key L8k/I/3Z6dqo9ro4YiQ/jBOTfxzmi17ebYI5k1/M

# install aws cli profile
RUN pip install awscli --ignore-installed six
RUN aws configure set aws_access_key_id AKIAJUHGHBF4SEHXKLZA
 RUN aws configure set aws_secret_access_key pzHyzfkDiOLeFJVhwXjSxm4w0UNHjRQCGvencPzx

#copy code
COPY docker_registry_discovery.py /opt/app/


#define temp workdir
WORKDIR /local

# login to aws and run script
CMD  docker version && $(aws ecr get-login --region us-east-1) && python /opt/app/docker_registry_discovery.py