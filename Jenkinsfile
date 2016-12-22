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
        dir("service"){
            sh returnStdout: true, script: "./gradlew dockerize -PimageName=${DOCKER_IMAGE_URI}"
        }

    stage 'ship'
        print "ship"
}
