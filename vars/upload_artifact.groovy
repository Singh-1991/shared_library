def call() {
 
    checkout scm
 
    dir("${env.WORKSPACE}/output") {
 
        def jsonFiles = sh(script: 'ls *.json', returnStdout: true).trim().split('\n')
   
        // Process each JSON file
        jsonFiles.each { fileName ->
            def parts = fileName.split('-')
            if (parts.size() >= 2) {
                def modelName = parts[0..-2].join('-')
                def version = parts[-1].replace('.json', '')
                def uploadPath = "${env.ARTIFACTORY_UPLOAD_PATH}/${modelName}/${version}/${fileName}"
               
               
                //withCredentials([usernamePassword(credentialsId: env.ARTIFACTORY_CRED_ID, passwordVariable: 'ART_PASSWORD', usernameVariable: 'ART_USERNAME')]) {
                //    sh "jfrog rt u ${fileName} ${uploadPath} --url ${env.ARTIFACTORY_REPO_URL} --user ${ART_USERNAME} --password ${ART_PASSWORD}"
                //}
            } else {
                echo "Filename format is unexpected: ${fileName}. Skipping file."
            }
        }
    }              
}
