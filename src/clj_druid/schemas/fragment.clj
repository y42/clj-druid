(ns clj-druid.schemas.fragment
  (:require [schema.core :as s]
            [clj-druid.schemas.extraction :refer :all]
            [clj-druid.schemas.granularity :refer :all]
            [clj-druid.schemas.aggregation :refer :all]
            [clj-druid.schemas.post-aggregation :refer :all]))

(s/defschema aggregations
  [aggregation])

(s/defschema postAggregations
  [postAggregation])

(s/defschema context
  "Druid Context option schema"
  {(s/optional-key :timeout) Long
   (s/optional-key :priority) Long
   (s/optional-key :queryId) s/Str
   (s/optional-key :useCache) Boolean
   (s/optional-key :populateCache) Boolean
   (s/optional-key :bySegment) Boolean
   (s/optional-key :finalize) Boolean
   (s/optional-key :skipEmptyBuckets) Boolean
   (s/optional-key :chunkPeriod) s/Str})

(s/defschema defaultDimension
  "Returns dimension values as is and optionally renames the dimension"
  {:type (s/enum :default)
   :dimension s/Str
   :outputName s/Str})

(s/defschema dimensionSpec
  "define how dimension values get transformed prior to aggregation"
  (s/conditional
   #(= :default (:type %)) defaultDimension
   #(= :extraction (:type %)) extraction
   :else s/Str))

(s/defschema orderByColumnSpec
  "Druid orderByColumnSpec option schema"
  {:dimension s/Any :direction (s/enum :ASCENDING :DESCENDING)})

(s/defschema limitSpec
  "Druid limitSpec option schema"
  {:type (s/enum :default)
   :limit Long
   :columns [orderByColumnSpec]})

(s/defschema intervals
  "Druid interval schema"
  [s/Str])

(s/defschema queryType
  "Druid queryType option schema"
  (s/enum :groupBy
          :search
          :segmentMetadata
          :timeBoundary
          :timeseries
          :topN))

(s/defschema pagingSpec
  {(s/optional-key :pagingIdentifiers) {s/Keyword Long}
   (s/enum :threshold) Long})

(s/defschema searchSort
  "Druid queryType search sort schema"
  {:type (s/enum :lexicographic :strlen)})

(s/defschema topNMetricSpec
  "topN metric option schema"
  (s/conditional
   #(map? %) {:type (s/enum
                     :numeric
                     :lexicographic
                     :alphaNumeric
                     :inverted)
              (s/optional-key :metric) s/Str
              (s/optional-key :previousStop) s/Str}
   :else s/Str))

(s/defschema segmentMetadataToInclude
  "Druid SegmentMetadata toInclude option schema"
  {:type (s/enum :all :none :list)
   :columns [s/Str]})




