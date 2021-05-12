(ns t9-search.core
    (:require [clojure.string :refer [blank? upper-case split]])
  (:gen-class))

(def digit-map
  "A map of the digits to the letters they represent on a T-9 keypad"
  {:2 "ABC", :3 "DEF", :4 "GHI", :5 "JKL", :6 "MNO", :7 "PQRS", :8 "TUV", :9 "WXYZ"})

(def all-names
  "List of all names in the search space"
  (list "pankaj" "mor" "sam" "pam" "moshe" "alex" "avi" "ram" "beth" "clara" "qadir" "queeny" "nadir"
        "nitin" "oleg" "debbie" "ethan" "farhad" "Ganu" "hitesh" "ishwar" "james" "ketan"
        "luna" "maruti" "nisargadatta" "sasha" "tina" "umer" "vinod" "wall-e" "yatin" "zack"))

(names-matching-letter "P" 0 all-names (list ""))

(names-matching-letters names-matching-letter "PQRS" 0 all-names (list ""))

(names-matching-digit "2" 0 all-names (list ""))

(t9-search-helper "726" 0 all-names)

(t9-search "726" '("pankaj" "mor" "sam"))
