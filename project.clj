(defproject y42/clj-druid "0.2.17-SNAPSHOT"
  :description "Clojure library for Druid.io"
  :url "http://github.com/y42/clj-druid"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}


  :test-selectors {:default (fn [m] (not (or (:integration m) (:regression m))))
                   :integration :integration
                   :regression :regression}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [swiss-arrows "1.0.0"]
                 [clj-http "3.4.1"]
                 [prismatic/schema "1.1.3"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/data.json "0.2.6"]
                 [curator "0.0.6"]])
