;;;
;;;   Statistic about code coverage measured by Cloverage reporter.
;;;
;;; Copyright (c) 2015, 2016, 2017, 2020  Pavel Tisnovsky, Red Hat
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

(ns cloverage-jenkins-reporter.jenkins-api-test
  (:require [clojure.test :refer :all]
            [cloverage-jenkins-reporter.jenkins-api :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
  "Test if given function-name is bound to the real function."
  [function-name]
  (clojure.test/function? function-name))

;
; Tests for various defs and functions
;

(deftest test-list-of-all-jobs-url-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/list-of-all-jobs-url definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/list-of-all-jobs-url definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.jenkins-api/list-of-all-jobs-url))))


(deftest test-read-list-of-all-jobs-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/read-list-of-all-jobs definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/read-list-of-all-jobs definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.jenkins-api/read-list-of-all-jobs))))


(deftest test-read-job-info-as-json-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/read-job-info-as-json definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/read-job-info-as-json definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.jenkins-api/read-job-info-as-json))))


(deftest test-read-build-number-for-job-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/read-build-number-for-job definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/read-build-number-for-job definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.jenkins-api/read-build-number-for-job))))


(deftest test-encode-spaces-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/encode-spaces definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/encode-spaces definition exists."
    (is (callable? 'cloverage-jenkins-reporter.jenkins-api/encode-spaces))))


(deftest test-url-to-file-from-last-build-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/url-to-file-from-last-build definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/url-to-file-from-last-build definition exists."
    (is
      (callable?
        'cloverage-jenkins-reporter.jenkins-api/url-to-file-from-last-build))))


(deftest test-url-to-job-configuration-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/url-to-job-configuration definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/url-to-job-configuration definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.jenkins-api/url-to-job-configuration))))


(deftest test-read-file-from-last-build-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/read-file-from-last-build definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/read-file-from-last-build definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.jenkins-api/read-file-from-last-build))))


(deftest test-read-job-configuration-existence
  "Check that the cloverage-jenkins-reporter.jenkins-api/read-job-configuration definition exists."
  (testing
    "if the cloverage-jenkins-reporter.jenkins-api/read-job-configuration definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.jenkins-api/read-job-configuration))))

;
; Actual tests
;

(deftest test-encode-spaces
  "Check the jenkins-api/encode-spaces function"
  (testing "the jenkins-api/encode-spaces function"
           (are [x y] (= x y)
                ""            (encode-spaces "")
                "%20"         (encode-spaces " ")
                "%20%20"      (encode-spaces "  ")
                "%20%20%20"   (encode-spaces "   ")
                "x%20"        (encode-spaces "x ")
                "x%20%20"     (encode-spaces "x  ")
                "%20y"        (encode-spaces " y")
                "%20%20y"     (encode-spaces "  y")
                "x%20y"       (encode-spaces "x y")
                "x%20y%20z"   (encode-spaces "x y z")
                "x%20%20%20z" (encode-spaces "x   z"))))

(deftest test-encode-spaces-NPE
  "Check the function the jenkins-api/encode-spaces"
  (testing "the function jenkins-api/encode-spaces"
           (is (thrown? NullPointerException (encode-spaces nil)))))

(deftest test-job-name->url-1
  "Check the clj-jenkins-api.jenkins-api/job-name->url"
  (testing "the clj-jenkins-api.jenkins-api/job-name->url"
           (are [x y] (= x y)
                "job/"                  (job-name->url "" "job/" "")
                " job/"                 (job-name->url " " "job/" "")
                "jenkins-url/job/"      (job-name->url "jenkins-url/" "job/" "")
                "jenkins-url:8080/job/" (job-name->url "jenkins-url:8080/" "job/" ""))))

(deftest test-job-name->url-2
  "Check the clj-jenkins-api.jenkins-api/job-name->url"
  (testing "the clj-jenkins-api.jenkins-api/job-name->url"
    (are [x y] (= x y)
         "jenkins-url:8080/job/"                  (job-name->url "jenkins-url:8080/" "job/" "")
         "jenkins-url:8080/job/job-name"          (job-name->url "jenkins-url:8080/" "job/" "job-name")
         "jenkins-url:8080/job/job%20name"        (job-name->url "jenkins-url:8080/" "job/" "job name")
         "jenkins-url:8080/job/long%20job%20name" (job-name->url "jenkins-url:8080/" "job/" "long job name"))))

(deftest test-job-name->url-not-NPE
  "Check the clj-jenkins-api.jenkins-api/job-name->url"
  (testing "the clj-jenkins-api.jenkins-api/job-name->url"
           (are [x y] (= x y)
                "job/" (job-name->url "" "job/"  "")
                "job/" (job-name->url nil "job/" ""))))

(deftest test-job-name->url-NPE
  "Check the clj-jenkins-api.jenkins-api/job-name->url"
  (testing "the clj-jenkins-api.jenkins-api/job-name->url"
           (is (thrown? NullPointerException (job-name->url ""  nil nil)))
           (is (thrown? NullPointerException (job-name->url nil nil nil)))))

