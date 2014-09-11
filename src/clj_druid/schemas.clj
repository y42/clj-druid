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
  {:type String :limit Long :columns [String]})

(s/defschema filterSchema
  "Druid filter field option schema"
  {:type (s/enum :selector :regex :and :or :not)
   (s/optional-key :dimension) String
   (s/optional-key :value) String
   (s/optional-key :pattern) String
   (s/optional-key :fields) [filterSchema]
   (s/optional-key :field) filterSchema})

(s/defschema aggregationSchema
  "Druid filter field option schema"
  {:type (s/enum :count :longSum :doubleSum :min :max)
   :name String
   (s/optional-key :fieldName) String})

(s/defschema postAggregationSchema
  "Druid filter field option schema"
  {:type (s/enum :arithmetic :fieldAccess :constant)
   :name String
   (s/optional-key :fieldName) String
   (s/optional-key :value) String})

(s/defschema intervalSchema
  "Druid interval schema"
  [String])

(s/defschema havingSchema
  {:type (s/enum :equalTo
                 :greaterThan
                 :lessThan
                 :and
                 :or
                 :not)

   (s/optional-key :aggregation) String
   (s/optional-key :value) String
   (s/optional-key :havingSpecs) [havingSchema]})


(s/defschema druidQuery
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
  :aggregations aggregationSchema
  :postAggregations postAggregationSchema
  :intervals intervalSchema})
