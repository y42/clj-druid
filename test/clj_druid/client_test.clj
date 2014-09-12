(ns clj-druid.client-test
  (:require [clojure.test :refer :all]
            [clj-druid.client :refer :all]))


(deftest test-connect
  (println (client {:zk-host "127.0.0.1"})))

