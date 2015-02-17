(defproject y42/clj-druid "0.1.6-SNAPSHOT"
  :description "Clojure library for Druid.io"
  :url "http://github.com/y42/clj-druid"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [swiss-arrows "1.0.0"]
                 [http-kit "2.1.16"]
                 [prismatic/schema "0.3.3"]
                 [org.clojure/tools.logging "0.3.0"]
                 [org.clojure/data.json "0.2.5"]
                 [smichal/curator "0.0.6"]
                 [zookeeper-clj "0.9.1"]])
