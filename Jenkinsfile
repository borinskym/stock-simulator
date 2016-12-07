properties([
  // disableConcurrentBuilds(),
  pipelineTriggers([]),
  buildDiscarder(
    logRotator(artifactDaysToKeepStr: '10', artifactNumToKeepStr: '10', daysToKeepStr: '10', numToKeepStr: '10')
  ),
  [ $class: 'GitLabConnectionProperty', gitLabConnection: 'GitLab' ],
])

// https://jenkins.io/doc/pipeline/steps/
node {

    stage 'clean'
      // start with an empty workspace
      deleteDir()

    stage 'checkout'
      checkout scm

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
          sh "./gradlew clean assemble"
        } catch (err) {
          step([$class: 'WarningsPublisher', consoleParsers: [[parserName: 'Java Compiler (javac)']]])
          gitlabCommitStatus { }
          throw err
        }
      }

    stage 'build'
        sh "./gradlew build"

    stage 'dockerize'
        sh "./gradlew build dockerize"

    stage 'deploy to k8s'
        //sh "python run deployer"
}
