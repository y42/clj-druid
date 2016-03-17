(defproject y42/clj-druid "0.2.11"
  :description "Clojure library for Druid.io"
  :url "http://github.com/y42/clj-druid"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}


  :test-selectors {:default (fn [m] (not (or (:integration m) (:regression m))))
                   :integration :integration
                   :regression :regression}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [swiss-arrows "1.0.0"]
                 [clj-http "2.0.0"]
                 [prismatic/schema "1.0.4"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/data.json "0.2.6"]
                 [zookeeper-clj "0.9.1"]])
