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
  (s/either regularExpressionExtractionFunction
            partialExtractionFunction
            searchQueryExtractionFunction
            timeFormatExtractionFunction
            timeParsingExtractionFunction
            javascriptExtractionFunction))

(s/defschema extraction
  {:type (s/enum :regex)
   :dimension s/Str
   :outputName s/Str
   :extractionFn extractionFn})

