(ns clj-druid.validations
  (:require [clj-druid.schemas :as sch]
            [schema.core :as s]
            [schema.coerce :as c]))



(defn validate-groupby
  "Validate a druid groupBy query"
  [query]

  (s/validate sch/groupBy query))


(defn validate-search
  "Validate a druid search query"
  [query]

  (s/validate sch/search query))


(defn validate-segment-metadata
  "Validate a druid segment metadata query"
  [query]

  (s/validate sch/segmentMetadata query))

(defn validate-time-boundary
  "Validate a druid time boundary query"
  [query]

  (s/validate sch/timeBoundary query))

(defn validate-timeseries
  "Validate a druid timeseries query"
  [query]

  (s/validate sch/timeseries query))


(defn validate-topN
  "Validate a druid topN query"
  [query]

  (s/validate sch/topN query))


(defn validate
  [query query-type]

  (s/validate (query-type sch/queries) query))
