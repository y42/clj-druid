(defproject clj-druid "0.1.0-SNAPSHOT"
  :description "Clojure library for Druid.io"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}

  :main clj-druid.validations
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [prismatic/schema "0.2.6"]
                 [org.clojure/tools.logging "0.3.0"]
                 [org.clojure/data.json "0.2.5"]
                 [smichal/curator "0.0.6"]
                 [zookeeper-clj "0.9.1"]])
