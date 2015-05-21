(ns clj-druid.schemas.query
  (:require [schema.core :as s]
            [clj-druid.schemas.fragment :refer :all]
            [clj-druid.schemas.having :refer :all]
            [clj-druid.schemas.filter :refer :all]
            [clj-druid.schemas.search-query :refer :all]
            [clj-druid.schemas.granularity :refer [granularity]]))

(s/defschema dataSourceMetadata
  "Data Source Metadata queries return metadata information for a dataSource.
  It returns the timestamp of latest ingested event for the datasource"
  {:queryType (s/enum :dataSourceMetadata)
   :dataSource s/Str
   (s/optional-key :context) context})


(s/defschema groupBy
  "These types of queries take a groupBy query object and return an array of JSON objects where each object represents a grouping asked for by the query. Note: If you only want to do straight aggregates for some time range, we highly recommend using TimeseriesQueries instead. The performance will be substantially better"
  {:queryType (s/enum :groupBy)
   :dataSource s/Str
   :dimensions [dimensionSpec]
   :granularity granularity
   :aggregations aggregations
   :intervals intervals
   (s/optional-key :postAggregations) postAggregations
   (s/optional-key :limitSpec) limitSpec
   (s/optional-key :having) having
   (s/optional-key :filter) Filter
   (s/optional-key :context) context})

(s/defschema timeseries
  "These types of queries take a timeseries query object and return an array of JSON objects where each object represents a value asked for by the timeseries query."
  {:queryType (s/enum :timeseries)
   :dataSource s/Str
   :granularity granularity
   :aggregations aggregations
   :intervals intervals
   (s/optional-key :postAggregations) postAggregations
   (s/optional-key :filter) Filter
   (s/optional-key :context) context})

(s/defschema topN
  "TopN queries return a sorted set of results for the values in a given dimension according to some criteria. Conceptually, they can be thought of as an approximate GroupByQuery over a single dimension with an Ordering spec. TopNs are much faster and resource efficient than GroupBys for this use case. These types of queries take a topN query object and return an array of JSON objects where each object represents a value asked for by the topN query."
  {:queryType (s/enum :topN)
   :dataSource s/Str
   :granularity granularity
   :dimension s/Str
   :metric topNMetricSpec
   :threshold Long
   :aggregations aggregations
   :intervals intervals
   (s/optional-key :postAggregations) postAggregations
   (s/optional-key :filter) Filter
   (s/optional-key :context) context})

(s/defschema search
  "A search query returns dimension values that match the search specification."
  {:queryType (s/enum :search)
   :dataSource s/Str
   :granularity granularity
   :query searchQuery
   :sort searchSort
   :intervals intervals
   (s/optional-key :searchDimensions) [s/Str]
   (s/optional-key :filter) Filter
   (s/optional-key :context) context})

(s/defschema select
  "Select queries return raw Druid rows and support pagination."
  {:queryType (s/enum :select)
   :dataSource s/Str
   :intervals intervals
   (s/optional-key :metrics) [s/Str]
   (s/optional-key :granularity) granularity
   (s/optional-key :dimensions) [dimensionSpec]
   (s/optional-key :pagingSpec) pagingSpec
   (s/optional-key :limitSpec) limitSpec
   (s/optional-key :filter) Filter
   (s/optional-key :context) context})

(s/defschema segmentMetadata
  "Segment metadata queries return per segment information"
  {:queryType (s/enum :segmentMetadata)
   :dataSource s/Str
   :intervals intervals
   (s/optional-key :toInclude) segmentMetadataToInclude
   (s/optional-key :merge) Boolean
   (s/optional-key :context) context})

(s/defschema timeBoundary
  "Segment Metadata query schema"
  {:queryType (s/enum :timeBoundary)
   :dataSource s/Str
   (s/optional-key :toInclude) segmentMetadataToInclude
   (s/optional-key :bound) (s/enum :minTime :maxTime)
   (s/optional-key :context) context})

(s/defschema query
  (s/either groupBy
            search
            segmentMetadata
            timeBoundary
            timeseries
            topN
            select))

(def queries {:groupBy groupBy
              :search search
              :segmentMetadata segmentMetadata
              :timeBoundary timeBoundary
              :timeseries timeseries
              :topN topN
              :select select})
