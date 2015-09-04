(ns cloverage-jenkins-reporter.jenkins-api)

(require '[clojure.data.json       :as json])

(defn list-of-all-jobs-url
    []
    (str @config/jenkins-url @config/jenkins-job-list-url))

(defn read-list-of-all-jobs
    []
    (let [data (json/read-str (slurp (list-of-all-jobs-url)))]
        (if data
            (get data "jobs")
            nil)))

(defn read-job-info-as-json
    "Fetch the timestamp and duration of the last build from Jenkins server."
    [job-name]
    (let [full-json-url (str @config/jenkins-url @config/jenkins-job-prefix-url (.replace job-name " " "%20") "/lastBuild/api/json?pretty=true")
          inputstr (slurp full-json-url)]
        (if inputstr
            (let [parsed   (json/read-str inputstr)
                  branch   (get (nth (get parsed "actions") 2) "lastBuiltRevision")]
                {:timestamp (get parsed "timestamp")
                 :duration  (get parsed "duration")
                 :branch    (get (first (get branch "branch")) "name")
                 :sha       (get branch "SHA1")
                }))))

(defn read-build-number-for-job
    [job-name]
    (let [full-json-url (str @config/jenkins-url @config/jenkins-job-prefix-url (.replace job-name " " "%20") "/api/json?pretty=true")
          inputstr (slurp full-json-url)]
        (if inputstr
            (-> (json/read-str inputstr)
                (get "lastBuild")
                (get "number"))
            nil)))

(defn encode-space
    [string]
    (.replace string " " "%20"))

(defn url-to-file-from-last-build
    [job-name file-name]
    (str @config/jenkins-url @config/jenkins-job-prefix-url (encode-space job-name) "/lastBuild/artifact/" file-name))

(defn url-to-job-configuration
    [job-name]
    (str @config/jenkins-url @config/jenkins-job-prefix-url (encode-space job-name) "/config.xml"))

(defn read-file-from-last-build
    [job-name file-name]
    (let [full-url (url-to-file-from-last-build job-name file-name)]
        (try
            (slurp full-url)
            (catch Exception e nil))))

(defn read-job-configuration
    [job-name]
    (let [full-url (url-to-job-configuration job-name)]
        (try
            (clojure.string/split-lines (slurp full-url))
            (catch Exception e nil))))

