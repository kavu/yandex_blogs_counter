(ns solar.core
  (:require
   [clojure.data.json :only [write-str] :as json]
   [org.httpkit.server :only [run-server] :as http]
   [ring.middleware.defaults :only [wrap-defaults api-defaults] :as rm]
   [com.climate.claypoole :only [threadpool ncpus pmap] :as cp]))

(defonce yandex-blogs-rss
  "https://yandex.ru/blogs/rss/search?text=%s&ft=blog,comments&numdoc=10")

(defonce app-content-type
  {"Content-Type" "application/json; charset=utf-8"})

(defonce response-template
  {:status  200
   :headers app-content-type
   :body nil})

; Yes, we'll have one global threadpool for all requests
(def pool
  (cp/threadpool (cp/ncpus)))

; Our processing entry function
(defn search
  [data]
  {:query data})

(defn- process-search-request
  [req]
  (let [queries (-> req :params :q distinct)
        data (cp/pmap pool search queries)]
    (json/write-str data)))

(defn- app
  [req]
  (assoc
    response-template :body
    (process-search-request req)))

(defn -main
  [& args]
  (println "Starting Server!")
  (http/run-server (rm/wrap-defaults app rm/api-defaults)
    {:port 8080}))
