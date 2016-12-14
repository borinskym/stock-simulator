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
    static final def AWS_REPO_URI = "911479539546.dkr.ecr.us-east-1.amazonaws.com"
    //static final def REGISRTY_SERVICE_URI = sh(script: 'python docker_registry_discovery.py', returnStdout: true)

    stage 'clean'
      // start with an empty workspace
      deleteDir()
      // delete images
      def docker_dangling_imgs = sh returnStdout: true, script: 'docker images -f \"dangling=true\" -q --no-trunc'
      if (!docker_dangling_imgs.equals("")) {
         sh "docker rmi -f " + docker_dangling_imgs
      }

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
           "./gradlew clean assemble"
        } catch (err) {
          step([$class: 'WarningsPublisher', consoleParsers: [[parserName: 'Java Compiler (javac)']]])
          gitlabCommitStatus { }
          throw err
        }
      }

    stage 'build'
        sh "./gradlew build"

    stage 'dockerize'
        sh "cd service && ./gradlew dockerize"

    //stage 'AWS Access'
        //timestamps {
            //withCredentials([
                //    [ $class: 'AmazonWebServicesCredentialsBinding',
                    //  credentialsId: 'aws-registry-k8s',
                      //accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                      //secretKeyVariable: 'AWS_SECRET_ACCESS_KEY' ]
            //]) {
                //sh "aws configure set aws_access_key_id AWS_ACCESS_KEY_ID"
                //sh "aws configure set aws_secret_access_key AWS_SECRET_ACCESS_KEY"
                //def docker_login = sh returnStdout: true, script: 'aws ecr get-login --region us-east-1'
                //sh docker_login
		//sh "aws ecr --region us-east-1 batch-delete-image --repository-name hello-world-java --image-ids imageTag=0.1.0"
            //}
        //}

    //stage 'push docker image'
        //sh "docker push " + REGISRTY_SERVICE_URI


    stage 'deploy to k8s'
        withCredentials([
                            [ $class: 'AmazonWebServicesCredentialsBinding',
                              credentialsId: 'aws-registry-k8s',
                              accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                              secretKeyVariable: 'AWS_SECRET_ACCESS_KEY' ]
                    ]) {
                        def dockerImageUri = sh(script: 'python docker_registry_discovery.py ${AWS_ACCESS_KEY_ID} ${AWS_SECRET_ACCESS_KEY}', returnStdout: true)

                        def docker_login = sh returnStdout: true, script: 'aws ecr get-login --region us-east-1'
                         sh docker_login
                print "URIIIII -> " + dockerImageUri
                        def runCommand =  "docker run -v /var/run/docker.sock:/var/run/docker.sock -e IMAGE_NAME=" + dockerImageUri + "  -t ${AWS_REPO_URI}/k8s-deployer:latest"
                        sh runCommand
                    }

         //def dockerImageUri = sh(script: 'python docker_registry_discovery.py', returnStdout: true)
        //def runCommand =  "docker run -v /var/run/docker.sock:/var/run/docker.sock -e IMAGE_NAME=" + dockerImageUri + "  -t ${AWS_REPO_URI}/k8s-deployer:latest"
        //def runCommand =  "docker run -v /var/run/docker.sock:/var/run/docker.sock -e IMAGE_NAME=${REGISRTY_SERVICE_URI}  -t ${AWS_REPO_URI}/k8s-deployer:latest"
        //sh runCommand
}
