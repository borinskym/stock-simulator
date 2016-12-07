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
          sh './build.sh compile'
        } catch (err) {
          step([$class: 'WarningsPublisher', consoleParsers: [[parserName: 'Java Compiler (javac)']]])
          gitlabCommitStatus { }
          throw err
        }
      }

    stage 'build'
    // don't docker for now
      // sh "./build.sh remove || true" // ignore failure
      // sh "./build.sh build"

    stage 'dockerize'
    // don't docker for now
      // sh "./build.sh push"

    stage 'deploy to k8s'
//      withCredentials([[$class: 'UsernamePasswordMultiBinding',
//        credentialsId: 's3-int-nyfw-heed',
//        passwordVariable: 'AWS_SECRET_ACCESS_KEY',
//        usernameVariable: 'AWS_ACCESS_KEY_ID']]
//      ) {
//        withEnv(['S3_BUCKET=artifacts.int-nyfw.heedapps.io']) {
//          sh "./build.sh s3upload"
//        }
//      }


    // stage 'dev deploy'
    //   input message: "Do you want to deploy to environment?", ok: "Deploy!"
    //   // depends on NOMAD_ADDR to select target environment
    //   sh "./build.sh deploy"

}
