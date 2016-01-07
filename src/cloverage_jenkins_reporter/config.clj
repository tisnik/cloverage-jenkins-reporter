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
        (reset! jenkins-job-prefix-url (:jenkins-job-prefix-url configuration))
        (reset! jenkins-job-list-url   (:jenkins-job-list-url configuration))))

(defn print-configuration
    []
    (println "jenkins-url:            " @jenkins-url)
    (println "jenkins-job-prefix-url: " @jenkins-job-prefix-url)
    (println "jenkins-job-list-url:   " @jenkins-job-list-url))
