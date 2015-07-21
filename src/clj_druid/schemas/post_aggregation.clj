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
   :fields [(s/either
             (s/recursive #'arithmeticPostAggregator)
             fieldAccessPostAggregator
             constantPostAggregator
             javascriptPostAggregator
             hyperUniqueCardinalityPostAggregator)]
   (s/optional-key :ordering) (s/enum nil "numericFirst")})


(s/defschema postAggregation
  (s/either arithmeticPostAggregator
            fieldAccessPostAggregator
            constantPostAggregator
            javascriptPostAggregator
            hyperUniqueCardinalityPostAggregator))

