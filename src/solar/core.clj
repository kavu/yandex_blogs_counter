(ns solar.core
  (:require [org.httpkit.server :as http]
            [clojure.data.json :as json]))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body    (json/write-str {"habr.ru" 13})})

(defn -main
  [& args]
  (println "Starting Server!")
  (http/run-server app
                   {:port 8080}))
