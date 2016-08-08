# clj-druid

[![Build Status](https://travis-ci.org/y42/clj-druid.svg?branch=master)](https://travis-ci.org/y42/clj-druid)

Clojure library for [Druid](http://druid.io/).

clj-druid eases druid integration providing a client as well as a set of [Schema] (https://github.com/Prismatic/schema) to validate queries.
Current version is up to date with latest query API from official docs.

## Installing

Add the following to your [Leiningen](http://github.com/technomancy/leiningen) `project.clj`:

![latest clj-druid version](https://clojars.org/y42/clj-druid/latest-version.svg)

## Usage

### Client

Connect to a druid cluster through Zookeeper, 
this method supports auto detection and update of available brokers for easy HA/load balancing

```clj
(use 'clj-druid.client)
(connect {:zk {:host "127.0.0.1:2181"
               :discovery-path "/druid/discovery"
               :node-type "broker"}})
```

you can also connect by supplying a vector of hosts, useful for dev, local testing

```clj
(use 'clj-druid.client)
(connect {:hosts ["http://127.0.0.1:8083/druid/v2"]})
```

### Querying

Issue druid queries supplying
* a load balance strategy [fixed random]
* a queryType
* a query in the following form

```clj
(def q
  {:queryType :timeseries
   :dataSource "sample_datasource"
   :granularity :day
   :filter {
     :type :and
     :fields [
       {:type :selector :dimension "sample_dimension1" :value "sample_value1"}
       {:type :or
         :fields [
           {:type :selector :dimension "sample_dimension2" :value "sample_value2"}
           {:type :selector :dimension "sample_dimension3" :value "sample_value3"}]}]}
   :aggregations [
     {:type :longSum :name "sample_name1", :fieldName "sample_fieldName1"}
     {:type :doubleSum :name "sample_name2", :fieldName "sample_fieldName2"}]
   :postAggregations [
     {:type :arithmetic
      :name "sample_divide"
      :fn "/"
      :fields [{:type :fieldAccess :name "sample_name1" :fieldName "sample_fieldName1"}
               {:type :fieldAccess :name "sample_name2" :fieldName "sample_fieldName2"}]}]

   :intervals ["2012-01-01T00:00:00.000/2012-01-03T00:00:00.000"]})
   (let [client (connect {:zk {:host "127.0.0.1:2181"
                          :discovery-path "/druid/discovery"
                          :node-type "broker"}})]
     (query client random (:queryType q) q)
     (close client))
```
   
### Example

An example using compojure-api to spawn a full druid API is located in the examples folder

```bash
git clone https://github.com/y42/clj-druid
cd clj-druid/examples
lein ring server
```

Have Fun!

## License

Copyright &copy; 2015 Guillaume Buisson

Distributed under the Eclipse Public License, the same as Clojure.

