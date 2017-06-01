;;;
;;;   Statistic about code coverage measured by Cloverage reporter.
;;;
;;; Copyright (c) 2015, 2016  Pavel Tisnovsky, Red Hat
;;; All rights reserved.
;;;
;;; Redistribution and use in source and binary forms, with or without
;;; modification, are permitted provided that the following conditions are met:
;;;     * Redistributions of source code must retain the above copyright
;;;       notice, this list of conditions and the following disclaimer.
;;;     * Redistributions in binary form must reproduce the above copyright
;;;       notice, this list of conditions and the following disclaimer in the
;;;       documentation and/or other materials provided with the distribution.
;;;     * Neither the name of the Red Hat nor the
;;;       names of its contributors may be used to endorse or promote products
;;;       derived from this software without specific prior written permission.
;;;
;;; THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
;;; ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
;;; WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
;;; DISCLAIMED. IN NO EVENT SHALL Pavel Tisnovsky BE LIABLE FOR ANY
;;; DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
;;; (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
;;; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
;;; ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
;;; (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
;;; SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

(ns cloverage-jenkins-reporter.jenkins-api)

(require '[clojure.data.json       :as json])

(defn encode-spaces
    "Encode spaces in URL into its codes."
    [^String url]
    (clojure.string/replace url " " "%20"))

(defn job-url
    ( [jenkins-url jenkins-job-prefix-url job-name]
      (str jenkins-url jenkins-job-prefix-url (encode-spaces job-name)))
    ( [jenkins-url jenkins-job-prefix-url job-name postfix]
      (str jenkins-url jenkins-job-prefix-url (encode-spaces job-name) postfix)))

(defn list-of-all-jobs-url
    [jenkins-url jenkins-job-list-url]
    (str jenkins-url jenkins-job-list-url))

(defn read-list-of-all-jobs
    [jenkins-url jenkins-job-list-url]
    (let [data (json/read-str (slurp (list-of-all-jobs-url jenkins-url jenkins-job-list-url)))]
        (if data
            (get data "jobs")
            nil)))

(defn read-job-info-as-json
    "Fetch the timestamp and duration of the last build from Jenkins server."
    [jenkins-url jenkins-job-prefix-url job-name]
    (let [full-json-url (job-url jenkins-url jenkins-job-prefix-url job-name "/lastBuild/api/json?pretty=true")
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
    [jenkins-url jenkins-job-prefix-url job-name]
    (let [full-json-url (job-url jenkins-url jenkins-job-prefix-url job-name "/api/json?pretty=true")
          inputstr (slurp full-json-url)]
        (if inputstr
            (-> (json/read-str inputstr)
                (get "lastBuild")
                (get "number"))
            nil)))

(defn url-to-file-from-last-build
    [jenkins-url jenkins-job-prefix-url job-name file-name]
    (str (job-url jenkins-url jenkins-job-prefix-url job-name "/lastBuild/artifact/") file-name))

(defn url-to-job-configuration
    [jenkins-url jenkins-job-prefix-url job-name]
    (str (job-url jenkins-url jenkins-job-prefix-url job-name "/config.xml")))

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

