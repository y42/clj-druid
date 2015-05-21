(ns clj-druid.schemas.having
  (:require [schema.core :as s]))

(declare having)

(s/defschema havingEqualTo
  {:type (s/enum :equalTo)
   :aggregation String
   :value Long})

(s/defschema havingGreaterThan
  {:type (s/enum :greaterThan)
   :aggregation String
   :value Long})

(s/defschema havingLessThan
  {:type (s/enum :lessThan)
   :aggregation String
   :value Long})

(s/defschema having
  (s/either havingEqualTo
            havingGreaterThan
            havingLessThan
            {:type (s/enum :or :not :and)
             :havingSpecs (s/recursive #'having)}))
