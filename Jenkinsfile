properties([
  pipelineTriggers([]),
  buildDiscarder(
    logRotator(artifactDaysToKeepStr: '10', artifactNumToKeepStr: '10', daysToKeepStr: '10', numToKeepStr: '10')
  ),
  [ $class: 'GitLabConnectionProperty', gitLabConnection: 'GitLab' ],
])

@Library('jenkinsSharedLib')
import commons.Common

@Library('localDocker')
import docker.LocalDocker

@Library('awsDocker')
import docker.AwsDocker

node {
    static final def AWS_REPO_URI = "911479539546.dkr.ecr.us-east-1.amazonaws.com"

    stage 'clean'
        print 'eli--@@->'
      print '=====>'
      deleteDir()
      new docker.LocalDocker().clean()

    stage 'checkout'
      checkout scm

    stage 'process configuration'
      print 'reading from yml'
      def fileContent = sh returnStdout: true, script: 'cat config.yml'
      def common = new commons.Common()
      common.loadAsYml(fileContent)
      print common.getByKey('name')

    stage 'compile'
      // https://issues.jenkins-ci.org/browse/JENKINS-26100 super ugly workaround :(
      sh 'git rev-parse --short=8 HEAD > GIT_COMMIT'

      // set version for builds done in Jenkins
      // BUILD_TS is 'Build Timestamp' plugin additional configuration
      env.SERVICE_VERSION = env.BUILD_TS + '-' + env.BUILD_NUMBER + '-' + readFile('GIT_COMMIT').trim()
      if (env.BRANCH_NAME) { // when building a branch, set a more specific version name
        env.SERVICE_VERSION = env.SERVICE_VERSION + '-' + env.BRANCH_NAME.replaceAll('/', '-')
      }

      timestamps {
        try {
           "./gradlew clean assemble"
        } catch (err) {
          step([$class: 'WarningsPublisher', consoleParsers: [[parserName: 'Java Compiler (javac)']]])
          gitlabCommitStatus { }
          throw err
        }
      }

    stage 'build'
        sh "./gradlew build"

    //stage 'dockerize'
       // print "cd service && ./gradlew dockerize -PimageName=" + AWS_REPO_URI + "/" + common.getByKey('name') + ":" + common.getByKey('version')
        //sh "cd service && ./gradlew dockerize -PimageName=" + AWS_REPO_URI + "/" + common.getByKey('name') + ":" + common.getByKey('version')
        //def images = sh(script: 'docker images', returnStdout: true)

    stage 'deploy to k8s'
            def output = sh(script: 'docker push 911479539546.dkr.ecr.us-east-1.amazonaws.com/hello-world-java:0.1.0', returnStdout: true)
            print output
         //def awsDocker  = new docker.AwsDocker()
         //print awsDocker.push(common.getByKey('name'), common.getByKey('version'))
}
