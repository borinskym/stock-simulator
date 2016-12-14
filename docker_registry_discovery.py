import os
import sys


# print sys.argv[1]
# print sys.argv[2]

print "reading config.yml"
f = open('config.yml', 'r')

registryUri = "911479539546.dkr.ecr.us-east-1.amazonaws.com" #hard coded for now

conf = {}
for line in f.readlines():
    splited = line.split(':')
    conf[splited[0]] = splited[1].strip()

os.system("aws configure set aws_access_key_id " + sys.argv[1])
os.system("aws configure set aws_secret_access_key " + sys.argv[2])
os.system("aws ecr get-login --region us-east-1")
os.system("aws ecr --region us-east-1 batch-delete-image --repository-name hello-world-java --image-ids imageTag=0.1.0")
#os.system("aws ecr --region us-east-1 batch-delete-image --repository-name" + conf['name']  + "--image-ids imageTag=" + conf['version'])

retVal = registryUri + "/" + conf['name'] + ":" + conf['version']

#print "push new version to registry"
#os.system("docker push " + retVal)

sys.stdout.write(retVal)

# import sys
#
# f = open('config.yml', 'r')
#
# registryUri = "911479539546.dkr.ecr.us-east-1.amazonaws.com" #hard coded for now
#
# conf = {}
# for line in f.readlines():
#     splited = line.split(':')
#     conf[splited[0]] = splited[1].strip()
#
# retVal = registryUri + "/" + conf['name'] + ":" + conf['version']
# sys.stdout.write(retVal)