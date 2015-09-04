(ns cloverage-jenkins-reporter.config)

(require '[cloverage-jenkins-reporter.config-loader :as config-loader])

(def jenkins-url
    "URL for Jenkins page."
    (atom nil))

(def jenkins-job-prefix-url
    "URL for Jenkins page with job settings."
    (atom nil))

(def jenkins-job-list-url
    "URL for Jenkins page with job list."
    (atom nil))

(defn load-configuration
    [config-file-name]
    (let [configuration (config-loader/load-configuration-file config-file-name)]
        (reset! jenkins-url            (:jenkins-url configuration))
        (reset! jenkins-job-prefix-url (:jenkins-job-prefix-url-url configuration))
        (reset! jenkins-job-list-url   (:jenkins-job-list-url configuration))))

