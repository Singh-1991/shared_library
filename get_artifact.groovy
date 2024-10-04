def call() {
 
    checkout scm
   
    def config = readYaml file: 'config.yml'
    def artifactPath = config.artifact_path
 
    echo "Artifact Path is : ${artifactPath}"
   
    def outputDir = "${env.WORKSPACE}/output"
    if (!fileExists(outputDir)) {
        echo "Directory ${outputDir} not found, creating it."
        sh "mkdir -p ${outputDir}"
    }
    else {
        echo "Directory ${outputDir} already exists."
    }
 
    /*
    dir(outputDir) {
 
        /*
        withCredentials([usernamePassword(credentialsId: 'aws_access_key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
 
            // Download artifacts from S3 bucket
            sh '''
                set +x
                export AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
                export AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
 
                TEMP_CREDS=$(aws sts assume-role --role-arn "arn:aws:iam::058264284230:role/sa-ebd-001" --role-session-name "ststoebd")
 
                export AWS_ACCESS_KEY_ID=$(echo $TEMP_CREDS | jq -r '.Credentials.AccessKeyId')
                export AWS_SECRET_ACCESS_KEY=$(echo $TEMP_CREDS | jq -r '.Credentials.SecretAccessKey')
                export AWS_SESSION_TOKEN=$(echo $TEMP_CREDS | jq -r '.Credentials.SessionToken')
                set -x
 
                aws --version
                def PWD = sh(script: "echo \$(pwd)", returnStdout: true).trim()
                aws s3 ls ${artifactPath}
                aws s3 cp ${artifactPath} ${PWD} --debug
            '''
        }
    }*/
               
}
