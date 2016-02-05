(ns clj-druid.schemas.post-aggregation
  (:require [schema.core :as s]))

(s/defschema fieldAccessPostAggregator
  "This returns the value produced by the specified aggregator."
  {:type (s/enum :fieldAccess)
   (s/optional-key :name) s/Str
   :fieldName s/Str})

(s/defschema constantPostAggregator
  "The constant post-aggregator always returns the specified value."
  {:type (s/enum :constant)
   :name s/Str
   :value Long})

(s/defschema javascriptPostAggregator
  "Applies the provided JavaScript function to the given fields. Fields are passed as arguments to the JavaScript function in the given order."
  {:type (s/enum :javascript)
   :name s/Str
   :fieldNames [s/Str]
   :function s/Str})

(s/defschema hyperUniqueCardinalityPostAggregator
  "The hyperUniqueCardinality post aggregator is used to wrap a hyperUnique object such that it can be used in post aggregations."
  {:type (s/enum :hyperUniqueCardinality)
   :fieldName s/Str})

(s/defschema arithmeticPostAggregator
  "The arithmetic post-aggregator applies the provided function to the given fields from left to right. The fields can be aggregators or other post aggregators."
  {:type (s/enum :arithmetic)
   :name s/Str
   :fn (s/enum "+" "-" "*" "/" "quotient")
   :fields [(s/conditional
             #(= :arithmetic (:type %)) (s/recursive #'arithmeticPostAggregator)
             #(= :fieldAccess (:type %)) fieldAccessPostAggregator
             #(= :constant (:type %)) constantPostAggregator
             #(= :javascript (:type %)) javascriptPostAggregator
             #(= :hyperUniqueCardinality (:type %)) hyperUniqueCardinalityPostAggregator)]
   (s/optional-key :ordering) (s/enum nil "numericFirst")})


                                        ;---- Experimental

(s/defschema equalBucketsPostAggregator
  "Computes a visual representation of the approximate histogram with a given number of equal-sized bin
Bucket intervals are based on the range of the underlying data."
  {:type (s/enum :equalBuckets)
   :name s/Str
   :fieldName s/Str
   :numBuckets Long})

(s/defschema bucketsPostAggregator
  "Computes a visual representation given an initial breakpoint, offset, and a bucket size.
Bucket size determines the width of the binning interval.
Offset determines the value on which those interval bins align."
  {:type (s/enum :buckets)
   :name s/Str
   :fieldName s/Str
   :bucketSize Long
   :offset Long})

(s/defschema customBucketsPostAggregator
  "Computes a visual representation of the approximate histogram with bins laid out according to the given breaks."
  {:type (s/enum :customBuckets)
   :name s/Str
   :fieldName s/Str
   :breaks [s/Any]})

(s/defschema minPostAggregator
  "Returns the minimum value of the underlying approximate histogram aggregator"
  {:type (s/enum :min)
   :name s/Str
   :fieldName s/Str})

(s/defschema maxPostAggregator
  "Returns the minimum value of the underlying approximate histogram aggregator"
  {:type (s/enum :max)
   :name s/Str
   :fieldName s/Str})

(s/defschema quantilePostAggregator
  "Computes a single quantile based on the underlying approximate histogram aggregator"
  {:type (s/enum :quantile)
   :name s/Str
   :fieldName s/Str
   :probability s/Any})

(s/defschema quantilesPostAggregator
  "Computes an array of quantiles based on the underlying approximate histogram aggregator"
  {:type (s/enum :quantiles)
   :name s/Str
   :fieldName s/Str
   :probabilities [s/Any]})

(s/defschema postAggregation
  (s/conditional
   #(= :arithmetic (:type %)) arithmeticPostAggregator
   #(= :fieldAccess (:type %)) fieldAccessPostAggregator
   #(= :constant (:type %)) constantPostAggregator
   #(= :javascript (:type %)) javascriptPostAggregator
   #(= :hyperUniqueCardinality (:type %)) hyperUniqueCardinalityPostAggregator
   #(= :equalBuckets (:type %)) equalBucketsPostAggregator
   #(= :buckets (:type %)) bucketsPostAggregator
   #(= :customBuckets (:type %)) customBucketsPostAggregator
   #(= :min (:type %)) minPostAggregator
   #(= :max (:type %)) maxPostAggregator
   #(= :quantile (:type %)) quantilePostAggregator
   #(= :quantiles (:type %)) quantilesPostAggregator))
