def call() {
 
    configuration = readYaml file: "config.yml"
    subscribers = configuration.subscriber_repos
   
    if (subscribers == null || subscribers.size() == 0) {
        throw new Exception("The number of subscribers in config.yml is 0. Add a subscriber for the pipeline to continue execution")
    }
 
    for (String subscriber: subscribers) {
        if (!(subscriber =~ /(?i)ssh:\/\/git@git.pncint.net\/scm\/[a-z]{3}\/.*\.git/)) {
            throw new Exception("Subscriber " + subscriber + " does not match expected format - please fix and re-run pipeline")
        }
    }
           
}
