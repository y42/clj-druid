(ns clj-druid.client-test
  (:require [clojure.test :refer :all]
            [clj-druid.validations-test :as f]
            [clj-druid.client :refer :all]))

(deftest ^:integration test-connect-zookeeper
  (let [client (connect {:zk {:host "127.0.0.1:2181"
                        :discovery-path "/druid/discovery"
                              :node-type "broker"}})]
    (close client)))

(deftest ^:integration test-connect-user
  (let [client (connect {:hosts ["http://localhost:8082/druid/v2/"]})]
    (close client)))

(deftest ^:integration test-zk-query
  (let [client (connect {:zk {:host "127.0.0.1:2181"
                              :discovery-path "/druid/discovery"
                              :node-type "broker"}})]
    (query client randomized :groupBy f/valid-groupby-query)
    (close client)))

(deftest ^:integration test-user-query
  (let [client (connect {:hosts ["http://127.0.0.1:8083/druid/v2/"]})]
    (query client randomized :groupBy f/valid-groupby-query :timeout 5000)
    (close client)))
