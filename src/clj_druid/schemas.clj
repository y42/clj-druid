(ns clj-druid.schemas
  (:require [schema.core :as s]))

(s/defschema granularity
  "Druid Granularity option schema"
  (s/enum :all
          :none
          :minute
          :fifteen_minute
          :thirty_minute
          :hour
          :day))

(s/defschema limitSpec
  "Druid limitSpec option schema"
  {:type (s/enum :default) :limit Long :columns [String]})

(declare filterSchema)

(s/defschema filterSchema
  "Druid filter field option schema"
  {:type (s/enum :selector :regex :and :or :not)
   (s/optional-key :dimension) String
   (s/optional-key :value) String
   (s/optional-key :pattern) String
   (s/optional-key :fields) [(s/recursive #'filterSchema)]
   (s/optional-key :field) (s/recursive #'filterSchema)})

(s/defschema aggregationSchema
  "Druid filter field option schema"
  {:type (s/enum :count :longSum :doubleSum :min :max)
   :name String
   (s/optional-key :fieldName) String})

(s/defschema postAggregationSchema
  "Druid filter field option schema"
  {:type (s/enum :arithmetic :fieldAccess :constant)
   :name String
   (s/optional-key :fields) [(s/recursive #'postAggregationSchema)]
   (s/optional-key :fieldName) String
   (s/optional-key :value) String
   (s/optional-key :fn) String})

(s/defschema intervalSchema
  "Druid interval schema"
  [String])

(s/defschema havingSchema
  "Druid having option schema"
  {:type (s/enum :equalTo
                 :greaterThan
                 :lessThan
                 :and
                 :or
                 :not)

   (s/optional-key :aggregation) String
   (s/optional-key :value) (s/either String Long)
   (s/optional-key :havingSpecs) [(s/recursive #'havingSchema)]})


(s/defschema QueryType
  "Druid queryType option schema"
  (s/enum :groupBy
          :search
          :segmentMetadata
          :timeBoundary
          :timeseries
          :topN))


(s/defschema groupBy
  "Main  druid query schema"
  {:queryType (s/enum :groupBy
                      :search
                      :segmentMetadata
                      :timeBoundary
                      :timeseries
                      :topN)

  :dataSource String
  :dimensions [String]
  :limitSpec limitSpec
  :having havingSchema
  :granularity granularity
  :filter filterSchema
  :aggregations [aggregationSchema]
  :postAggregations [postAggregationSchema]
  :intervals intervalSchema})
