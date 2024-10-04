def call() {
 
    checkout scm
 
    //def config = readYaml file: 'config.yml'
    //def artifactPath = config.artifact_path
    //def tarballName = artifactPath.tokenize('/')[-1]
 
    dir("${env.WORKSPACE}/output") {
        //echo "Tarball Name is: ${tarballName}"
        //sh "tar -xvf ${tarballName}"
        sh "tar -xvf correct-hash-values.tar"
        def checksumFile = 'checksum.txt'
        def checksumMap = [:]
 
        // Read checksum file and populate the map
        readFile(checksumFile).split('\n').each { line ->
            def parts = line.split()
            if (parts.size() == 2) {
                checksumMap[parts[1]] = parts[0]
            }
        }
 
        def jsonFiles = sh(script: 'ls *.json', returnStdout: true).trim().split('\n')
        def shaMatches = true
 
        jsonFiles.each { fileName ->
            if (fileExists(fileName)) {
                def shaCalculated = sha256(file: fileName)
 
                def shaExpected = checksumMap[fileName]
                if (shaExpected == null) {
                    error "Checksum for ${fileName} not found in ${checksumFile}"
                }
 
                if (shaCalculated != shaExpected) {
                    shaMatches = false
                    error "Checksum for ${fileName} does not match. Expected: ${shaExpected}, Calculated: ${shaCalculated}"
                }
            } else {
                error "File ${fileName} does not exist."
            }
        }
 
        if (shaMatches) {
            echo "All JSON files have matching checksums."
        }
    }
}
