(ns clj-druid.schemas.granularity
  (:require [schema.core :as s]))

(s/defschema simpleGranularity
  "Simple granularities are specified as a string and bucket timestamps by their UTC time (e.g., days start at 00:00 UTC)."
  (s/enum :all
          :none
          :minute
          :second
          :fifteen_minute
          :thirty_minute
          :hour
          :day))

(s/defschema durationGranularity
  "Duration granularities are specified as an exact duration in milliseconds and timestamps are returned as UTC. Duration granularity values are in millis."
  {:type (s/enum :duration)
   :duration Long
   (s/optional-key :origin) s/Str})

(s/defschema periodGranularity
  "Period granularities are specified as arbitrary period combinations of
years, months, weeks, hours,
minutes and seconds (e.g. P2W, P3M, PT1H30M, PT0.750S) in ISO8601 format."
  {:type (s/enum :period)
   :period s/Str
   :timeZone s/Str})

(s/defschema granularity
  "The granularity field determines how data gets bucketed across the time dimension, or how it gets aggregated by hour, day, minute, etc."
  (s/either simpleGranularity
            durationGranularity
            periodGranularity))



