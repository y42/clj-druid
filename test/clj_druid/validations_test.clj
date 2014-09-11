(ns clj-druid.validations-test
  (:require [clojure.test :refer :all]
            [clj-druid.validations :refer :all]))

(def valid-groupby-query
  {:queryType :groupBy
   :dataSource "sample_datasource"
   :granularity :day
   :dimensions ["dim1" "dim2"]
   :limitSpec {:type :default
               :limit 5000
               :columns ["dim1" "metric1"]}
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
           :name "sample_name1"
           :fieldName "sample_fieldName1"}
          {:type :fieldAccess
           :name "sample_name2"
           :fieldName "sample_fieldName2"}]}]
   :intervals ["2012-01-01T00:00:00.000/2012-01-03T00:00:00.000"]
   :having {:type :greaterThan
            :aggregation "sample_name1"
            :value 0}})


(deftest test-valid-groupby-query
  (is (= (validate-groupby valid-groupby-query)
             valid-groupby-query)))
