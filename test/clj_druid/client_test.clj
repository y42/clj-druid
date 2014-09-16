(ns clj-druid.client-test
  (:require [clojure.test :refer :all]
            [clj-druid.client :refer :all]))


(deftest test-connect-zookeeper
  (client {:zk {:host "192.168.59.103:2181"
                :discovery-path "/discovery"
                :node-type "broker"}}))

