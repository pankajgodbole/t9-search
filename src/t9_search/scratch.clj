(ns t9-search.core
  (:require [clojure.string :refer [blank? upper-case split split-lines]])
  (:gen-class))

(def digit-map
  "A map of the digits to the letters they represent on a T-9 keypad"
  {:2 ["A" "a" "B" "b" "C" "c"],
   :3 ["D" "d" "E" "e" "F" "f"],
   :4 ["G" "g" "H" "h" "I" "i"],
   :5 ["J" "j" "K" "k" "L" "l"],
   :6 ["M" "m" "N" "n" "O" "o"],
   :7 ["P" "p" "Q" "q" "R" "r" "S" "s"],
   :8 ["T" "t" "U" "u" "V" "v"],
   :9 ["W" "w" "X" "x" "Y" "y" "Z" "z"]})

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
("aa" "aal" "aalii" "aam" "aardvark" "aardwolf")

(defn ho-find-words-by-prefix
  "Takes a trie (t) and a prefix (p), searches t for words beginning with p and returns the result"
  [t p]
  (letfn [(search [node]
                  (mapcat (fn [[k v]] (if (= k :word) [v] (search v))) node))]
    (search (get-in ho-word-trie "aa"))))

(count (ho-find-words-by-prefix ho-word-trie "bary"))
;; 25

(count (mapcat #(ho-find-words-by-prefix ho-word-trie %)
         (map #(subs (str %) 0 1) (:2 digit-map))))
;; 6449

(doseq [w (take 9
                (mapcat #(ho-find-words-by-prefix ho-word-trie %)
                  (map #(subs (str %) 0 1) (:2 digit-map))))]
  (println w))

(with-open
  [wrtr
     (clojure.java.io/writer
       "/home/p/Clojure/t9-search/output/t9-all-words-matching-one-digit.txt")]
  (let [words (take 9
                    (mapcat #(ho-find-words-by-prefix ho-word-trie %)
                      (map #(subs (str %) 0 1) (:2 digit-map))))]
    (doseq [w words] (.write wrtr (str w "\n")))))
;; Aani
;; Aaron
;; Aaronic
;; Aaronical
;; Aaronite
;; Aaronitic
;; Aaru
;; Ababdeh
;; Ababua
;; nil

(defn ho-get-letters-for-digit
  "Takes a digit (d), and returns all the letters in the map that pertain to d"
  [d]
  (let [kw (keyword (str d))] (kw digit-map)))

(def ho-letters-for-digit-2 (ho-get-letters-for-digit 2))

ho-letters-for-digit-2
["A" "a" "B" "b" "C" "c"]

(defn ho-find-words-by-digit
  "Takes a collection of letters (ls), and returns a list of all the words in a given trie that pertain to ls"
  [ls]
  (mapcat #(ho-find-words-by-prefix ho-word-trie %) ls))

(def ho-words-for-digit-2 (ho-find-words-by-digit (ho-get-letters-for-digit 2)))

(count ho-words-for-digit-2)
;; 48067

(defn ho-write-words-to-file
  "Takes a filepath (fp) and a collection of words (ws), and writes ws to the file existing at fp"
  [fp ws]
  (with-open [wrtr (clojure.java.io/writer fp)]
    (doseq [w ws] (.write wrtr (str w "\n")))))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



(mapcat)

(get-in)

(iterate first (:2 digit-map))
