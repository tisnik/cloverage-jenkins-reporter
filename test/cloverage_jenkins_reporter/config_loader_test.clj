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

(ns cloverage-jenkins-reporter.config-loader-test
  (:require [clojure.test :refer :all]
            [cloverage-jenkins-reporter.config-loader :refer :all]))

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

(deftest test-properties->map-existence
  "Check that the cloverage-jenkins-reporter.config-loader/properties->map definition exists."
  (testing
    "if the cloverage-jenkins-reporter.config-loader/properties->map definition exists."
    (is (callable? 'cloverage-jenkins-reporter.config-loader/properties->map))))


(deftest test-load-configuration-file-existence
  "Check that the cloverage-jenkins-reporter.config-loader/load-configuration-file definition exists."
  (testing
    "if the cloverage-jenkins-reporter.config-loader/load-configuration-file definition exists."
    (is (callable?
          'cloverage-jenkins-reporter.config-loader/load-configuration-file))))

;
; Test for function behaviours
;

(deftest test-properties->map-1
  "Check the behaviour of function emender-jenkins/properties->map."
  (let [property (doto (new java.util.Properties)
                       (.setProperty "a" "A")
                       (.setProperty "b" "B"))]
    (is (= {:a "A" :b "B"} (properties->map property)))))

(deftest test-properties->map-2
  "Check the behaviour of function emender-jenkins/properties->map."
  (let [property (doto (new java.util.Properties)
                       (.setProperty "propertyA" "property_a")
                       (.setProperty "propertyB" "property_b"))]
    (is (= {:propertyA "property_a", :propertyB "property_b"}
           (properties->map property)))))

(deftest test-properties->map-3
  "Check the behaviour of function emender-jenkins/properties->map."
  (let [property (doto (new java.util.Properties)
                       (.setProperty "value1" "1")
                       (.setProperty "value2" "2"))]
    (is (= {:value1 "1", :value2 "2"}
           (properties->map property)))))

(deftest test-load-configuration-file
  "Check the behaviour of function emender-jenkins/load-configuration-file."
  (println (properties->map (load-configuration-file "test/test1.properties")))
  (let [property (load-configuration-file "test/test1.properties")]
    (is (= {:value1 "value1", :value2 "42", :value3 "3"}
           (properties->map property)))))

