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

(ns cloverage-jenkins-reporter.core
    (:gen-class))

(require '[cloverage-jenkins-reporter.config :as config])
(require '[cloverage-jenkins-reporter.jenkins-api :as jenkins-api])

(defn filter-coverage-jobs
    [jobs pattern-for-coverage-jobs]
    ;(println pattern-for-coverage-jobs)
    (filter #(.endsWith (get % "name") pattern-for-coverage-jobs) jobs))

(defn get-index-html-url
    [job-url]
    (str job-url "lastSuccessfulBuild/artifact/target/coverage/coverage.txt"))

(defn read-lines
    [url]
    (try
        (-> (slurp url)
            clojure.string/split-lines)
        (catch Exception e
            nil)))

(defn parse-line
    [line]
    (re-matches #"\s+(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s+.*" line))

(defn parsed-lines
    [lines]
    ; return only parsed lines, not lines containing header or other unsupported content
    (filter seq
        (for [line lines]
            (parse-line line))))

(defn calc-sum
    "Calc sum for two consecutive parsed lines (to be used by reduce)."
    [vector1 vector2]
    (for [i (range 5)]
        (+ (nth vector1 i) (nth vector2 i))))

(defn percentage
    [x y]
    (Math/round
        (/ (* 100.0 x) y)))

(defn compute-coverage
    [job-name lines]
    (let [pp (for [p (parsed-lines lines)]
                 (for [i (range 1 6)]
                     (Integer/parseInt (nth p i))))]
         (if (seq pp)
             (let [sum (reduce calc-sum pp)]
                 {:job-name     job-name
                  :lines        (nth sum 0)
                  :non-blank    (nth sum 1)
                  :instrumented (nth sum 2)
                  :covered      (nth sum 3)
                  :lines-coverage (percentage (nth sum 3) (nth sum 2))}
             ))))

(defn get-coverage-for-job
    [job]
    (let [job-url  (get job "url")
          enabled? (= (get job "color") "blue")
          job-name (get job "name")]
          (if enabled?
              (let [index-html-url (get-index-html-url job-url)
                    result-file    (read-lines index-html-url)]
                    (compute-coverage job-name result-file)))))

(defn get-coverage-for-all-jobs
    [jobs]
    (for [job jobs]
        (get-coverage-for-job job)))

(defn make-report
    [jobs jenkins-url]
    (doseq [job jobs]
        (when (:job-name job)
            (println (str "<tr><td><a href='" jenkins-url "job/" (:job-name job) "/lastSuccessfulBuild/artifact/target/coverage/index.html'>" (:job-name job) "</a></td>"
                              "<td align='right'>" (:lines job) "</td>"
                              "<td align='right'>" (:non-blank job) "</td>"
                              "<td align='right'>" (:instrumented job) "</td>"
                              "<td align='right'>" (:covered job) "</td>"
                              "<td align='right'>" (:lines-coverage job) "%</td>"
                              "<td><hr align='left' noshade color='green' width='" (:lines-coverage job) "'></td>"
                              "</td></tr>")))))

(defn -main
    "Entry point to this tool, started by a shell script or from the Leiningen."
    [& args]
    (let [cfg (config/load-configuration "config.cfg")]
        ;(config/print-configuration cfg)
        (-> (jenkins-api/read-list-of-all-jobs (-> cfg :jenkins :url) (-> cfg :jenkins :job-list-url))
            (filter-coverage-jobs              (-> cfg :jenkins :pattern-for-coverage-jobs))
            (get-coverage-for-all-jobs)
            (make-report (-> cfg :jenkins :url))))
    ;(println "Done")
)

