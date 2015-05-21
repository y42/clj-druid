(ns clj-druid.schemas.search-query
  (:require [schema.core :as s]))


(s/defschema insensitiveContainsSearchQuery
  "If any part of a dimension value contains the value specified in this search query spec, regardless of case, a match occurs."
  {:type (s/enum :insensitive_contains)
   :value s/Any})

(s/defschema fragmentSearchQuery
  {:type (s/enum :fragment)
   :values [String]})

(s/defschema searchQuery
  "Search query specs define how a match is defined between a search value and a dimension value."
  (s/either insensitiveContainsSearchQuery
            fragmentSearchQuery))
