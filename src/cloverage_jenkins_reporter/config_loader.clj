(ns cloverage-jenkins-reporter.config-loader)

(defn properties->map
    [properties]
    (into {}
        (for [[k v] properties]
              [(keyword k) (read-string v)])))

(defn load-configuration-file
    [file-name]
    (with-open [reader (clojure.java.io/reader file-name)] 
        (let [properties (java.util.Properties.)]
            (.load properties reader)
            (properties->map properties))))

