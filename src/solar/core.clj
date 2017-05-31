(ns solar.core
  (:use
   [clojure.data.json :only [write-str] :as json]
   [org.httpkit.server :only [run-server] :as http]
   [ring.middleware.defaults :only [wrap-defaults api-defaults]]))

(defonce app-content-type
  {"Content-Type" "application/json; charset=utf-8"})

(defonce body-template
  {:status  200
   :headers app-content-type
   :body nil})

(defn app [req]
  (assoc body-template :body
         (let [q (-> req :params :q)]
           (json/write-str {:resp q}))))

(defn -main
  [& args]
  (println "Starting Server!")
  (http/run-server (wrap-defaults app api-defaults)
                   {:port 8080}))
