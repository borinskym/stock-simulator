properties([
  pipelineTriggers([]),
  buildDiscarder(
    logRotator(artifactDaysToKeepStr: '10', artifactNumToKeepStr: '10', daysToKeepStr: '10', numToKeepStr: '10')
  ),
  [ $class: 'GitLabConnectionProperty', gitLabConnection: 'GitLab' ],
])

@Library('jenkinsSharedLib')
import commons.ConfigParser

@Library('awsDocker')
import docker.AwsDocker

node {
    def DOCKER_IMAGE_URI = ""

    stage 'clean'
      deleteDir()

    stage 'checkout'
      checkout scm

    stage 'build'
      // https://issues.jenkins-ci.org/browse/JENKINS-26100 super ugly workaround :(
      sh 'git rev-parse --short=8 HEAD > GIT_COMMIT'
      env.SERVICE_VERSION = env.BUILD_TS + '-' + env.BUILD_NUMBER + '-' + readFile('GIT_COMMIT').trim()
      if (env.BRANCH_NAME) { // when building a branch, set a more specific version name
        env.SERVICE_VERSION = env.SERVICE_VERSION + '-' + env.BRANCH_NAME.replaceAll('/', '-')
      }

      timestamps {
        try {
           "./gradlew clean build"
        } catch (err) {
          step([$class: 'WarningsPublisher', consoleParsers: [[parserName: 'Java Compiler (javac)']]])
          gitlabCommitStatus { }
          throw err
        }
      }

    stage 'package'
        def fileContent = sh returnStdout: true, script: 'cat config.yml'
        DOCKER_IMAGE_URI = new commons.ConfigParser().getImageUri(fileContent)
        print DOCKER_IMAGE_URI
        sh returnStdout: true, script: "cd service &&  ./gradlew dockerize -PimageName=${DOCKER_IMAGE_URI}"

    stage 'ship'
        timestamps {
            withCredentials([
                    [ $class: 'AmazonWebServicesCredentialsBinding',
                      credentialsId: 'aws-registry-k8s',
                      accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                      secretKeyVariable: 'AWS_SECRET_ACCESS_KEY' ]
            ]) {
                sh "aws configure set aws_access_key_id AWS_ACCESS_KEY_ID"
                sh "aws configure set aws_secret_access_key AWS_SECRET_ACCESS_KEY"
                def docker_login = sh returnStdout: true, script: 'aws ecr get-login --region us-east-1'
                sh docker_login
                print "${DOCKER_IMAGE_URI}"
                print '${DOCKER_IMAGE_URI}'
                def push = sh returnStdout: true, script: "docker run -v /var/run/docker.sock:/var/run/docker.sock -e KEY_ID=${AWS_ACCESS_KEY_ID} -e ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} -e IMAGE_NAME=${DOCKER_IMAGE_URI} 911479539546.dkr.ecr.us-east-1.amazonaws.com/pusher:latest"
            }
        }
        sh "docker run -v /var/run/docker.sock:/var/run/docker.sock -e IMAGE_NAME=${DOCKER_IMAGE_URI} -t 911479539546.dkr.ecr.us-east-1.amazonaws.com/k8s-deployer:latest"
}
