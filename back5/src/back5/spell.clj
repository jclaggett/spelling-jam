(ns back5.spell
  (:require [back5.error]
            [back5.test-correct :as t]))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn get-words []
  (let [f (clojure.java.io/reader "resources/big.txt")
        lines-lower (map #(.toLowerCase %) (line-seq f))]
    (mapcat #(re-seq #"[a-z]+" %) lines-lower)))

(def nwords
  (frequencies (concat (get-words) (map second (t/parse t/test1)))))

(defn without-char [word i]
  (str (apply str (take i word)) (apply str (drop (inc i) word))))

(defn deletes [word]
  (into {}
	(for [x (range (.length word))]
		[(without-char word x) 1])))

(defn replaces [s]
  (into {}
        (for [[a b] s
              :while (seq b)
              r alphabet]
          [(apply str (concat a [r] (rest b))) 1])))

(defn inserts [s]
  (into {}
        (for [[a b] s
              i alphabet]
          [(apply str (concat a [i] b)) 1])))

(defn split-word [word]
  (for [i (range (inc (count word)))] (split-at i word)))

(defn edits1 [word]
  (let [s (split-word word)
        deletes (deletes word)
        transposes (into {}
                         (for [[a,b] s :when (> (count b) 1)]
                           [(apply str
                                   (concat a [(second b)] [(first b)] (drop 2 b)))
                            1]))
        replaces (replaces s)
        inserts (inserts s)]
    (merge-with max deletes transposes replaces inserts)))

(defn edits2 [word]
  (into {}
        (for [[word prob] (edits1 word)
              [word2 prob2] (edits1 word)]
          [word2 (* prob prob2)])))


(defn edits1-replaces [a c b]
  (let [adjacents (get back5.error/adjacencies c)]
    (loop [adjacent-chars (apply concat adjacents)
           replaces {}]
      (if (= 0 (count adjacent-chars)) replaces
          (recur (rest adjacent-chars)
                 (assoc replaces (str a (first adjacent-chars) b) 1))))))

(defn known [words]
  (into {}
        (for [[word probability] words
              :when (nwords word)]
          [word probability])))

(defn correct [word]
  (map key
       (sort-by #(- (val %))
                (merge-with max
                            {word 0.1}
                            (known (edits2 word))
                            (known {word 2})
                            (known (edits1 word))))))
