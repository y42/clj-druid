(defproject y42/clj-druid-examples "0.2.3"
  :description "clj-druid-examples"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.9.0"] ;; needed as `lein ring` is broken
                 [metosin/compojure-api "0.20.3"]
                 [metosin/ring-http-response "0.6.1"]
                 [metosin/ring-swagger-ui "2.1.1-M2"]
                 [y42/clj-druid "0.2.3"]]
  :ring {:handler handler/app}
  :uberjar-name "examples.jar"
  :uberwar-name "examples.war"
  :profiles {:uberjar {:resource-paths ["swagger-ui"]
                       :aot :all}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.9.4"]]}})
