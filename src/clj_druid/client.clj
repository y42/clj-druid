(ns clj-druid.client
  (:require [curator.framework :refer [curator-framework]]
            [curator.discovery :refer [service-discovery service-cache instances]]
            [clojure.data.json :as json]
            [clj-druid.schemas.query :as sch]
            [clj-druid.validations :as v]
            [clj-http.client :as http]
            [schema.core :as s]))

(defprotocol ^:private Client
  (close [this]
    "Close all connections to dependencies (ex: zookeeper)")
  (nodes [this]
    "Get a list of druid available nodes"))

(defn static-client
  "Creates a druid client based on user defined hosts."
  [hosts]
  (reify Client
    (close [_])
    (nodes [_]
      hosts)))

(defn make-host-http-str
  "make an http url string from a curator instance"
  [instance]
  (str "http://"
       (.getAddress instance)
       ":"
       (.getPort instance)
       "/druid/v2/"))

(defn curator-nodes
  "Get the nodes list from zookeeper using the cached service provider."
  [service-cache]
  (map make-host-http-str (instances service-cache)))

(defn curator-client
  "Create a druid client using the curator service discovery."
  [config]
  (let [client (curator-framework (:host config))
        discovery (service-discovery client
                                     nil
                                     :base-path
                                     (:discovery-path config))
        sc (service-cache discovery (:node-type config))]
    (.start client)
    (.start discovery)
    (.start sc)
    (reify Client
      (close [_]
        (.close sc)
        (.close discovery)
        (.close client))
      (nodes [_]
        (curator-nodes sc)))))

(defn randomized
  "Take a random host"
  [nodes]
  (rand-nth nodes))

(defn fixed
  "Always take first host"
  [nodes]
  (first nodes))

(defn close [client]
  (close client))

(defn connect
  "Create a druid client from zk or
  a user defined host"
  [params]
  (if (:zk params)
    (curator-client (:zk params))
    (static-client (:hosts params))))

(s/defn execute
  "Issue a druid query"
  ([client balance-strategy druid-query :- sch/query]
   (execute client balance-strategy druid-query {}))
  ([client balance-strategy druid-query http-params]
   (let [nodes (nodes client)]
     (when (empty? nodes)
       (throw (Exception.
               "No druid node available for query")))
     (http/post (balance-strategy nodes)
                (merge {:body (json/write-str druid-query)
                        :as :text
                        :content-type :json}
                       http-params)))))
(defn query
  "Issue a druid query with validation"
  [client balance-strategy query-type druid-query & params]
  (let [http-params (apply hash-map params)
        query (assoc druid-query :queryType query-type)]
    (s/with-fn-validation (execute client balance-strategy query http-params))))
