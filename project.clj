(defproject cloverage-jenkins-reporter "0.1.0-SNAPSHOT"
    :description "Reports basic statistic about code coverage measured by Cloverage."
    :url "http://example.com/FIXME"
    :license {:name "BSD 3-Clause license"
              :url "http://opensource.org/licenses/BSD-3-Clause"}
    :dependencies [[org.clojure/clojure "1.6.0"]
                   [org.clojure/data.json "0.2.5"]
                   [org.clojure/tools.cli "0.3.1"]
                   [hiccup "1.0.4"]]
    :main ^:skip-aot cloverage-jenkins-reporter.core
    :plugins [[codox "0.8.11"]
              [test2junit "1.1.0"]
              [lein-cloverage "1.0.2"]]
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}})

