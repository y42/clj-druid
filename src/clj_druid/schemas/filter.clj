(ns clj-druid.schemas.filter
  (:require [schema.core :as s]))

(s/defschema selectorFilter
  "The simplest filter is a selector filter.
The selector filter will match a specific dimension with a specific value.
Selector filters can be used as the base filters for more complex Boolean expressions of filters."

  {:type (s/enum :selector)
   :dimension s/Str
   :value s/Any})

(s/defschema regexFilter
  "The regular expression filter is similar to the selector filter,
 but using regular expressions. It matches the specified dimension with the given pattern.
 The pattern can be any standard Java regular expression."
  {:type (s/enum :regex)
   :dimension s/Str
   :pattern s/Str})

(s/defschema javascriptFilter
  {:type (s/enum :javascript)
   :dimension s/Str
   :function s/Str})

                                        ;Experimental

(s/defschema spatialFilter
  {:type (s/enum :spatial)
   :dimension s/Str
   :bound (s/either
           {:type (s/enum :rectangular)
            :minCoords [Float]
            :maxCoords [Float]}

           {:type (s/enum :radius)
            :coords [Float]
            :radius Float})})

(s/defschema Filter
  "A filter is a JSON object indicating which rows of data should be included in the computation for a query.
 It’s essentially the equivalent of the WHERE clause in SQL. Druid supports the following types of filters."
  (s/either selectorFilter
            regexFilter
            javascriptFilter
            spatialFilter
            {:type (s/enum :not)
             :field (s/recursive #'Filter)}
            {:type (s/enum :or :and)
             :fields [(s/recursive #'Filter)]}))
