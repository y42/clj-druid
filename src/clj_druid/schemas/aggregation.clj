(ns clj-druid.schemas.aggregation
  (:require [schema.core :as s]
            [clj-druid.schemas.filter :refer :all]
            [clj-druid.schemas.aggregation :refer :all]))

(s/defschema countAggregator
  "count computes the row count that match the filters"
  {:type (s/enum :count)
   :name s/Str})

(s/defschema longSumAggregator
  "computes the sum of values as a 64-bit, signed integer"
  {:type (s/enum :longSum)
   :name s/Str
   :fieldName s/Str})

(s/defschema doubleSumAggregator
  "Computes the sum of values as 64-bit floating point value. Similar to longSum"
  {:type (s/enum :doubleSum)
   :name s/Str
   :fieldName s/Str})

(s/defschema minAggregator
  "min computes the minimum metric value"
  {:type (s/enum :min)
   :name s/Str
   :fieldName s/Str})

(s/defschema maxAggregator
  "max computes the maximum metric value"
  {:type (s/enum :max)
   :name s/Str
   :fieldName s/Str})

(s/defschema javascriptAggregator
  "Computes an arbitrary JavaScript function over a set of columns (both metrics and dimensions)."
  {:type (s/enum :javascript)
   :name s/Str
   :fieldNames [s/Str]
   :fnAggregate s/Str
   :fnCombine s/Str
   :fnReset [s/Str]})

(s/defschema cardinalityAggregator
  "Computes the cardinality of a set of Druid dimensions, using HyperLogLog to estimate the cardinality."
  {:type (s/enum :cardinality)
   :name s/Str
   :fieldNames [s/Str]
   :byRow Boolean})

(s/defschema hyperUniqueAggregator
  "Uses HyperLogLog to compute the estimated cardinality of a dimension that has been aggregated as a hyperUnique metric at indexing time."
  {:type (s/enum :hyperUnique)
   :name s/Str
   :fieldName s/Str})

(declare aggregation)

(s/defschema filteredAggregator
  "A filtered aggregator wraps any given aggregator, but only aggregates the values for which the given dimension filter matches.
This makes it possible to compute the results of a filtered and an unfiltered aggregation simultaneously, without having to issue multiple queries, and use both results as part of post-aggregations."
  {:type (s/enum :filtered)
   :filter Filter
   (s/optional-key :name) s/Str
   :aggregator (s/recursive #'aggregation)})

(s/defschema aggregation
  "Aggregations are specifications of processing over metrics available in Druid"
  (s/conditional
   #(= :count (:type %)) countAggregator
   #(= :longSum (:type %)) longSumAggregator
   #(= :doubleSum (:type %)) doubleSumAggregator
   #(= :min (:type %)) minAggregator
   #(= :max (:type %)) maxAggregator
   #(= :javascript (:type %)) javascriptAggregator
   #(= :cardinality (:type %)) cardinalityAggregator
   #(= :hyperUnique (:type %)) hyperUniqueAggregator
   #(= :filtered (:type %)) filteredAggregator))



