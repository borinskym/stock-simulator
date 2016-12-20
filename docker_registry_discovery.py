import os
import sys

print "reading config.yml"
f = open('config.yml', 'r')

registryUri = "911479539546.dkr.ecr.us-east-1.amazonaws.com" #hard coded for now

conf = {}
for line in f.readlines():
    splited = line.split(':')
    conf[splited[0]] = splited[1].strip()

print "batch-delete-image"
os.system("aws ecr --region us-east-1 batch-delete-image --repository-name " + conf['name']  + " --image-ids imageTag=" + conf['version'])

retVal = registryUri + "/" + conf['name'] # + ":" + conf['version']
print "retVal -> " + retVal

print "push new version to registry"
os.system("docker push " + retVal)

# print "run deployer"
# os.system("docker run -v /var/run/docker.sock:/var/run/docker.sock -e IMAGE_NAME=" + retVal + " -t " + registryUri + "/k8s-deployer:latest")