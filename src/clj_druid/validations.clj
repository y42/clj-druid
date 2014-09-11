(ns clj-druid.validations
  (:require [clj-druid.schemas :as sch]
            [schema.core :as s]))



(defn validate-groupby
  "Validate a druid groupBy query"
  [query]

  (s/validate sch/groupBy query))
