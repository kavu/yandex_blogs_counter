(ns solar.core
  (:use
    [clojure.data.json :as json]
    [org.httpkit.server :only [run-server] :as http]
    (ring.middleware
      [keyword-params :only [wrap-keyword-params]]
      [params :only [wrap-params]])))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body
    (let [q (-> req :params :q)]
      (json/write-str {:resp q}))})

(defn -main
  [& args]
  (println "Starting Server!")
  (http/run-server
    (-> app
      wrap-keyword-params
      wrap-params)
    {:port 8080}))
