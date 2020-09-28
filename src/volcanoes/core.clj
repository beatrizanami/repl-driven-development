(ns volcanoes.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def csv-lines
  (with-open [csv (io/reader "./resources/GVP_Volcano_List_Holocene.csv")]
    (doall
      (csv/read-csv csv))))

(defn transform-header [header]
  (-> header
      str/lower-case
      (str/replace #"\([a-z]+\)" " ")
      str/trim
      (str/replace #" " "-")
      keyword))

(defn transform-header-row [header-line]
  (map transform-header header-line))

(def volcano-records
  (let [csv-lines (rest csv-lines)
        header (transform-header-row (first csv-lines))
        volcano-lines (rest csv-lines)]
    (map (fn [volcano-line]
           (zipmap header volcano-line))
         volcano-lines)))

(def types (set (map :primary-volcano-type volcano-records)))

(defn parse-numbers [volcano]
  (-> volcano
      (update :elevation #(Integer/parseInt %))
      (update :longitude #(Double/parseDouble %))
      (update :latitude #(Double/parseDouble %))))

(def volcanoes-parsed
  (map parse-numbers volcano-records))

(comment
  (let [volcano (nth volcanoes-parsed 100)]
    (clojure.pprint/pprint volcano))

  (let [volcano (first (filter #(= "221291" (:volcano-number %)) volcanoes-parsed))]
    (clojure.pprint/pprint volcano))

  (clojure.pprint/print-table
    (map #(select-keys % [:volcano-name :country]) volcanoes-parsed)))




