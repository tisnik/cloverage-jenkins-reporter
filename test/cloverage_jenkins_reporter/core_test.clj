;;;
;;;   Statistic about code coverage measured by Cloverage reporter.
;;;
;;; Copyright (c) 2015, 2016, 2020  Pavel Tisnovsky, Red Hat
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

(ns cloverage-jenkins-reporter.core-test
  (:require [clojure.test :refer :all]
            [cloverage-jenkins-reporter.core :refer :all]))

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

(deftest test-filter-coverage-jobs-existence
  "Check that the cloverage-jenkins-reporter.core/filter-coverage-jobs definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/filter-coverage-jobs definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/filter-coverage-jobs))))


(deftest test-get-index-html-url-existence
  "Check that the cloverage-jenkins-reporter.core/get-index-html-url definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/get-index-html-url definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/get-index-html-url))))


(deftest test-read-lines-existence
  "Check that the cloverage-jenkins-reporter.core/read-lines definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/read-lines definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/read-lines))))


(deftest test-parse-line-existence
  "Check that the cloverage-jenkins-reporter.core/parse-line definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/parse-line definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/parse-line))))


(deftest test-parsed-lines-existence
  "Check that the cloverage-jenkins-reporter.core/parsed-lines definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/parsed-lines definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/parsed-lines))))


(deftest test-calc-sum-existence
  "Check that the cloverage-jenkins-reporter.core/calc-sum definition exists."
  (testing "if the cloverage-jenkins-reporter.core/calc-sum definition exists."
           (is (callable? 'cloverage-jenkins-reporter.core/calc-sum))))


(deftest test-percentage-existence
  "Check that the cloverage-jenkins-reporter.core/percentage definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/percentage definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/percentage))))


(deftest test-compute-coverage-existence
  "Check that the cloverage-jenkins-reporter.core/compute-coverage definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/compute-coverage definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/compute-coverage))))


(deftest test-get-coverage-for-job-existence
  "Check that the cloverage-jenkins-reporter.core/get-coverage-for-job definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/get-coverage-for-job definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/get-coverage-for-job))))


(deftest test-get-coverage-for-all-jobs-existence
  "Check that the cloverage-jenkins-reporter.core/get-coverage-for-all-jobs definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/get-coverage-for-all-jobs definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.core/get-coverage-for-all-jobs))))


(deftest test-make-report-existence
  "Check that the cloverage-jenkins-reporter.core/make-report definition exists."
  (testing
    "if the cloverage-jenkins-reporter.core/make-report definition exists."
    (is (callable? 'cloverage-jenkins-reporter.core/make-report))))


(deftest test--main-existence
  "Check that the cloverage-jenkins-reporter.core/-main definition exists."
  (testing "if the cloverage-jenkins-reporter.core/-main definition exists."
           (is (callable? 'cloverage-jenkins-reporter.core/-main))))

