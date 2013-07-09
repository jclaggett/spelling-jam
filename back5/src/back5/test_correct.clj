(ns back5.test-correct)

(def test1 (slurp "../data/test1.txt"))
(def test2 (slurp "../data/test2.txt"))

(defn parse
  "Returns a sequence of vectors like [incorrect-word good-correction]"
  [text]
  (for [line (.split #"\n" text)
        :when (not (re-find #"^#" line))
        :let [words (.split #"\s+" line)
              correct (first words)]
        word words]
    [word correct]))

(defn lame-correct
  "Correct the word 'offen' to 'often', otherwise return the given
  word. Returns a vector of just the one word."
  [word]
  [({"offen" "often"} word word)])

(defn failseq
  "Takes a list of word pairs and a correct-fn to test. Returns a
  sequence of maps, each describing a failure case. Useful for
  debugging the correct-fn at the repl."
  [word-pairs correct-fn]
  (for [[input want] word-pairs
        :let [corrects (correct-fn input)]
        :when (not= want (first corrects))]
    {:in input :want want :got corrects}))

(defn measure
  "Takes a list of word pairs and a correct-fn to measure. Returns the
  percentage of properly corrected words of total attempts."
  [word-pairs correct-fn]
  (let [tests (count word-pairs)
        fails (count (failseq word-pairs correct-fn))]
    (float (* 100 (/ (- tests fails) tests)))))



(comment

  ;; For example, test using data file test1 and the provided lame-correct:
  (require '[back5.test-correct :as t] :reload)

  (t/measure (t/parse t/test1) t/lame-correct)
  ;=> 34.549877

  ;; 34% correct! not bad for a first try

  (take 2 (t/failseq (t/parse t/test1) t/lame-correct))
  ;=> ({:in "acess", :want "access", :got ["acess"]}
  ;    {:in "accesing", :want "accessing", :got ["accesing"]}
  )
