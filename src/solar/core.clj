(ns solar.core
  (:require
    [solar.search :only [search-all] :as search]
    [clojure.data.json :only [write-str] :as json]
    [org.httpkit.server :only [run-server] :as http-server]
    [ring.middleware.defaults :only [wrap-defaults api-defaults] :as rm]))

(defonce ^:private app-content-type
  {"Content-Type" "application/json; charset=utf-8"})

(defonce ^:private response-template
  {:status  200
   :headers app-content-type
   :body nil})

(defn- process-search-request
  [request]
  (-> request
    :params
    :q
    distinct
    search/search-all
    json/write-str))

(defn- app
  [req]
  (assoc
    response-template :body
    (process-search-request req)))

(defn -main
  [& args]
  (println "Starting Server!")
  (http-server/run-server
    (rm/wrap-defaults app rm/api-defaults)
    {:port 8080}))
