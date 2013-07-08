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

(defn measure
  "Takes a list of word pairs and a correct-fn to measure. Returns the
  percentage of properly corrected words of total attempts."
  [word-pairs correct-fn]
  (let [wins (count
              (for [[input corrected] word-pairs
                    :when (= corrected (first (correct-fn input)))]
                :win))]
    (float (* 100 (/ wins (count word-pairs))))))

(comment

  ;; For example, test using data file test1 and the provided lame-correct:
  (require '[back5.test-correct :as t] :reload)

  (t/measure (t/parse t/test1) t/lame-correct)
  ;=> 34.549877

  ;; 34% correct! not bad for a first try
  )
