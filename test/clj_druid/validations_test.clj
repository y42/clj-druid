(ns clj-druid.validations-test
  (:require [clojure.test :refer :all]
            [clj-druid.validations :refer :all]))

(def valid-groupby-query
  {:queryType :groupBy
   :dataSource "sample_datasource"
   :granularity :day
   :dimensions ["dim1" "dim2"]
   :filter {
     :type :and
     :fields [
       {:type :selector
        :dimension "sample_dimension1"
        :value "sample_value1"}
       {:type :or
        :fields [{:type :selector
                  :dimension "sample_dimension2"
                  :value "sample_value2"}
                 {:type :selector
                  :dimension "sample_dimension3"
                  :value "sample_value3"}]}]}
   :aggregations [
      {:type :longSum
       :name "sample_name1"
       :fieldName "sample_fieldName1"}
      {:type :doubleSum
       :name "sample_name2"
       :fieldName "sample_fieldName2"}]
   :postAggregations [
      {:type :arithmetic
       :name "sample_divide"
       :fn "/"
       :fields [
          {:type :fieldAccess
           :name "sample_fieldName1"
           :fieldName "sample_name1"}
          {:type :fieldAccess
           :name "sample_fieldName2"
           :fieldName "sample_name2"}]}]
   :intervals ["2012-01-01T00:00:00.000/2012-01-03T00:00:00.000"]
   :having {:type :greaterThan
            :aggregation "sample_name1"
            :value 0}})

(def valid-search-query
  {:queryType :search
   :dataSource "sample_datasource"
   :granularity :day
   :searchDimensions ["dim1" "dim2"]
   :query {
     :type :insensitive_contains
     :value "Ke"}
   :sort {:type :lexicographic}
   :intervals ["2013-01-01T00:00:00.000/2013-01-03T00:00:00.000"]})


(def valid-segment-metadata-query
  {:queryType :segmentMetadata
   :dataSource "sample_datasource"
   :intervals ["2013-01-01/2014-01-01"]})

(def valid-time-boundary-query
  {:queryType :timeBoundary
   :dataSource "sample_datasource"
   :bound :maxTime})

(def valid-timeseries-query
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


(def valid-select-query
 {:queryType :select
  :dataSource "wikipedia"
  :dimensions []
  :metrics []
  :granularity :all
  :intervals ["2013-01-01/2013-01-02"]
  :pagingSpec {:pagingIdentifiers {} :threshold 5}})


(def valid-topN-query
  (into valid-timeseries-query {:queryType :topN
                                :dimension "dim1"
                                :threshold 5
                                :metric "count"}))

 (deftest test-valid-groupby-query
   (is (= (validate-groupby valid-groupby-query)
              valid-groupby-query)))

(deftest test-valid-search-query
  (is (= (validate-search valid-search-query)
             valid-search-query)))

(deftest test-valid-segment-metadata-query
  (is (= (validate-segment-metadata valid-segment-metadata-query)
             valid-segment-metadata-query)))

(deftest test-valid-time-boundary-query
  (is (= (validate-time-boundary valid-time-boundary-query)
             valid-time-boundary-query)))

(deftest test-valid-timeseries-query
  (is (= (validate-timeseries valid-timeseries-query)
             valid-timeseries-query)))

(deftest test-valid-topN-query
  (is (= (validate-topN valid-topN-query)
             valid-topN-query)))

(deftest test-valid-select-query
  (is (= (validate valid-select-query :select)
             valid-select-query)))


