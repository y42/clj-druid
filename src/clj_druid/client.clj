(ns clj-druid.client
  (:require [zookeeper :as zk]
            [zookeeper.data :as data]
            [clojure.data.json :as json]
            [clj-druid.schemas :as sch]
            [clj-druid.validations :as v]
            [clojure.tools.logging :as log]))

(def nodes-list
  "contains all zk nodes discovered"
  (atom []))


(defn reset-node-list
  "update node list atom"
  [nodes]

  (println nodes)

  (reset! nodes-list nodes))

(defn make-node-path
  "make a zk path string"
  [discovery-path node-type]

  (str discovery-path "/druid:" node-type))

(defn make-host-http-str
  "make an http url from a zk node entry"
  [c]

  (str "http://" (get c "address") ":" (get c "port") "/druid/v2/"))


(defn update-node-list
  "Retrieve hosts from zk discovery"
  [zk-client path]

  (let [nodes (zk/children zk-client path)
        hosts-data (map #(data/to-string (:data (zk/data zk-client (str path "/" %)))) nodes)
        decoded-data (map #(json/read-str % :keywordize? true) hosts-data)
        hosts (map #(make-host-http-str %) decoded-data)]

    (reset-node-list hosts)))

(declare zk-client)

(defn from-zookeeper
  "Make a druid client from zookeeper"
  [config]

  (let [node-path (make-node-path (:discovery-path config) (:node-type config))
        zk-client (zk/connect (:zk-host config) :watcher (fn [x] (update-node-list zk-client node-path)))
        nodes (update-node-list zk-client node-path)]

    nodes))

(defn client
  "Create a druid client from zk or
  a user defined host"

  [config]

  (if-not (nil? (get config :zk-host)) (from-zookeeper config)))


