(ns clj-druid.schemas.extraction
  (:require [schema.core :as s]
            [clj-druid.schemas.search-query :refer [searchQuery]]))

(s/defschema regularExpressionExtractionFunction
  {:type (s/enum :regex)
   :expr s/Str})

(s/defschema partialExtractionFunction
  {:type (s/enum :partial)
   :expr s/Str})

(s/defschema searchQueryExtractionFunction
  {:type (s/enum :searchQuery)
   :query searchQuery})

(s/defschema timeFormatExtractionFunction
  {:type (s/enum :timeFormat)
   :format s/Str
   :timeZone s/Str
   :locale s/Str})

(s/defschema timeParsingExtractionFunction
  {:type (s/enum :time)
   :timeFormat s/Str
   :resultFormat s/Str})

(s/defschema javascriptExtractionFunction
  {:type (s/enum :javascript)
   :function s/Str})

(s/defschema extractionFn
  (s/conditional
   #(= :regex (:type %)) regularExpressionExtractionFunction
   #(= :partial (:type %)) partialExtractionFunction
   #(= :searchQuery (:type %)) searchQueryExtractionFunction
   #(= :timeFormat (:type %)) timeFormatExtractionFunction
   #(= :time (:type %)) timeParsingExtractionFunction
   #(= :javascript (:type %)) javascriptExtractionFunction))

(s/defschema extraction
  {:type (s/enum :extraction)
   :dimension s/Str
   :outputName s/Str
   :extractionFn extractionFn})
