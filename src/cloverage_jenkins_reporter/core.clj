(ns cloverage-jenkins-reporter.core
    (:gen-class))

(require '[cloverage-jenkins-reporter.config :as config])

(defn -main
    "I don't do a whole lot ... yet."
    [& args]
    (config/load-configuration "config.cfg")
    (println "Hello, World!"))

