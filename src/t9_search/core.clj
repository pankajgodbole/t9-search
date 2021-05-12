(ns t9-search.core
    (:require [clojure.string :refer [blank? upper-case split]])
  (:gen-class))

(def digit-map
  "A map of the digits to the letters they represent on a T-9 keypad"
  {:2 "ABC", :3 "DEF", :4 "GHI", :5 "JKL", :6 "MNO", :7 "PQRS", :8 "TUV", :9 "WXYZ"})

(defn names-matching-letter
  [letter digit-position all-matching-names cur-matching-names]
  (let [current-matches (filter (fn [name]
                                  (= letter
                                     (upper-case (subs name digit-position (+ digit-position 1)))))
                                all-matching-names)]
    (remove blank? (concat cur-matching-names current-matches))))

(defn names-matching-letters
  [func letters digit-position all-matching-names cur-matching-names]
  (if (empty? letters)
    cur-matching-names
    (recur func
           (subs letters 1)
           digit-position
           all-matching-names
           (func (subs letters 0 1) digit-position all-matching-names cur-matching-names))))

(defn names-matching-digit
  [digit digit-position all-matching-names cur-matching-names]
  (let [current-letters ((keyword digit) digit-map)]
    (names-matching-letters names-matching-letter current-letters digit-position
                            all-matching-names cur-matching-names)))

(defn t9-search-helper
  [search-key digit-pos names]
  (if (blank? search-key)
    names
    (let [cur-digit (subs search-key 0 1)
          cur-names (names-matching-digit cur-digit digit-pos names (list ""))]
        (t9-search-helper (subs search-key 1) (inc digit-pos) cur-names))))

(defn t9-search
  "Accepts a list of strings which represent contact names [NAMES], and another string that represents a numeric search key [SEARCH-KEY], and returns 0 or more names corresponding to digits in SEARCH-KEY"
  [search-key names]
  (t9-search-helper search-key 0 names))

(defn -main
  [& args]
  (println (t9-search "726" '("pankaj" "mor" "sam"))))
