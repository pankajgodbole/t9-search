(ns t9-search.core
  (:require [clojure.string :refer [blank? upper-case split split-lines]])
  (:gen-class))

(def digit-map
  "A map of the digits to the letters they represent on a T-9 keypad"
  {:2 "ABC",
   :3 "DEF",
   :4 "GHI",
   :5 "JKL",
   :6 "MNO",
   :7 "PQRS",
   :8 "TUV",
   :9 "WXYZ"})

(def all-names
  "List of all names in the search space"
  (list "pankaj"
        "mor" "sam"
        "pam" "moshe"
        "alex" "avi"
        "ram" "beth"
        "clara" "qadir"
        "queeny" "nadir"
        "nitin" "oleg"
        "debbie" "ethan"
        "farhad" "Ganu"
        "hitesh" "ishwar"
        "james" "ketan"
        "luna" "maruti"
        "nisargadatta" "sasha"
        "tina" "umer"
        "vinod" "wall-e"
        "yatin" "zack"))

(names-matching-letter "P" 0 all-names (list ""))

(names-matching-letters names-matching-letter "PQRS" 0 all-names (list ""))

(names-matching-digit "2" 0 all-names (list ""))

(t9-search-helper "726" 0 all-names)

(t9-search "726" '("pankaj" "mor" "sam"))

(take 9
      (clojure.string/split-lines
        (slurp "/home/p/Clojure/t9-search/data/words-mac.txt")))



(defn ho-add-words-to-trie
  "Takes a trie (t) and one or more words (ws), and returns a new trie with ws in it."
  [t ws]
  (reduce (fn [t w] (assoc-in t (concat w [:value]) w)) t ws))


(def ho-words-list
  "A list of words"
  (clojure.string/split-lines
    (slurp "/home/p/Clojure/t9-search/data/words-mac.txt")))

(def ho-word-trie
  "A trie of English dictionary words"
  (ho-add-words-to-trie {} ho-words-list))

ho-word-trie


(get-in ho-word-trie "aa")

(defn ho-find-words-by-prefix
  "Takes a trie (t) and a prefix (p), searches t for words beginning with p and returns the result"
  [t p]
  (letfn [(search [node]
                  (mapcat (fn [[k v]] (if (= k :word) [v] (search v))) node))]
    (search (get-in ho-word-trie "aa"))))

(ho-find-words-by-prefix ho-word-trie "bary")


("aa" "aal" "aalii" "aam" "aardvark" "aardwolf")

(mapcat)

(get-in)
