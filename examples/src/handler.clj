(ns handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [clj-druid.schemas.query :refer :all]))

(defapi app
  (swagger-ui)
  (swagger-docs
    {:info {:title "clj-druid + Compojure API"
            :description "clj-druid sample application"}})
  (context* "/druid" []
            :tags ["query"]
   (POST* "/groupBy" []
      :body [q groupBy]
      :summary "Issue a groupBy query"
      (ok true))

   (POST* "/topN" []
      :body [q topN]
      :summary "Issue a topN query"
      (ok true))

   (POST* "/timeseries" []
      :body [q timeseries]
      :summary "Issue a timeseries query"
      (ok true))

   (POST* "/search" []
      :body [q search]
      :summary "Issue a search query"
      (ok true))

   (POST* "/timeBoundary" []
      :body [q timeBoundary]
      :summary "Issue a timeBoundary query"
      (ok true))

   (POST* "/segmentMetadata" []
      :body [q segmentMetadata]
      :summary "Issue a segmentMetadata query"
      (ok true))))
