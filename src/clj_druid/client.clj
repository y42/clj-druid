(ns clj-druid.client
  (:require [zookeeper :as zk]
            [clj-druid.schemas :as sch]
            [clj-druid.validations :as v]
            [clojure.tools.logging :as log]))


(def zk-druid-node-path
  "/druid/broker")

(defn from-zookeeper
  [config]

  (let [zk-client (zk/connect (get config :zk-host))
        zk-druid-data (if (zk/exists zk-client zk-druid-node-path)
                      (zk/data zk-client zk-druid-node-path) nil)

        zk-druid-nodes (:data zk-druid-data)]

    zk-druid-nodes))

(defn client
  "Create a druid client"
  [config]

  (if-not (nil? (get config :zk-host)) (from-zookeeper config)))


