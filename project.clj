(defproject solar "0.1.0-SNAPSHOT"
            :dependencies [[org.clojure/clojure "1.8.0"]
                           [org.clojure/data.json "0.2.6"]
                           [http-kit "2.2.0"]
                           [ring/ring-defaults "0.3.0"]
                           [com.climate/claypoole "1.1.4"]]
            :profiles {:dev {:dependencies [[proto-repl "0.3.1"]]}
                       :uberjar {:aot :all}}
            :main ^:skip-aot solar.core
            :target-path "target/%s")
