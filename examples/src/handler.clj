(ns handler
  (:require
   [compojure.api.sweet :refer :all]
   [ring.util.http-response :refer :all]
   [clj-druid.schemas.query :refer :all]
   [clj-druid.client :as client]))


(def default-druid-host "http://localhost:8083/druid/v2")
(client/connect {:hosts [default-druid-host]})

(defn do-query [q]
  (ok (client/query
       client/randomized
       (:queryType q) q)))

(defapi app
  {:formats [:edn]}
  (swagger-ui)
  (swagger-docs
    {:info {:title "clj-druid + Compojure API"
            :description "clj-druid sample application"}})
  (context* "/druid" []
            :tags ["query"]
   (POST* "/groupBy" []
      :body [q groupBy]
      :summary "Issue a groupBy query"
      (do-query q))

   (POST* "/topN" []
      :body [q topN]
      :summary "Issue a topN query"
      (do-query q))

   (POST* "/timeseries" []
      :body [q timeseries]
      :summary "Issue a timeseries query"
      (do-query q))

   (POST* "/search" []
      :body [q search]
      :summary "Issue a search query"
      (do-query q))

   (POST* "/timeBoundary" []
      :body [q timeBoundary]
      :summary "Issue a timeBoundary query"
      (do-query q))

   (POST* "/segmentMetadata" []
      :body [q segmentMetadata]
      :summary "Issue a segmentMetadata query"
      (do-query q))))
