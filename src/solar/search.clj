(ns solar.search
  (:require
    [clojure.string :as str]
    [com.climate.claypoole :only [threadpool ncpus pmap] :as cp]
    [feedparser-clj.core :only [parse-feed] :as fp]
    [inet.data.format.psl :only [lookup] :as psl]
    [clojurewerkz.urly.core :only [url-like host-of] :as urly]))

(defonce ^:private yandex-blogs-rss
 "https://yandex.ru/blogs/rss/search?text=%s&ft=blog,comments&numdoc=10")

; Yes, we'll have one global threadpool for all requests
(defonce ^:private pool
  (cp/threadpool (cp/ncpus)))

(defn- get-feed-for
  [query]
  (fp/parse-feed (format yandex-blogs-rss query)))

; Our processing entry function
(defn- get-links
  [query]
  (->> query
    str
    str/trim
    get-feed-for
    :entries
    (map #(:link %))))

(defn- transform-links-to-domains
  [links]
  (map #(-> %
          urly/url-like
          urly/host-of
          psl/lookup
          str)
    links))

(defn search-all
  [queries]
  (let [links (cp/pmap pool get-links queries)]
    (-> links
      flatten
      distinct
      transform-links-to-domains
      frequencies)))
