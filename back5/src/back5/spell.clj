(ns back5.spell)

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn get-words []
  (let [f (clojure.java.io/reader "resources/big.txt")
        lines-lower (map #(.toLowerCase %) (line-seq f))]
    (mapcat #(re-seq #"[a-z]+" %) lines-lower)))

(def nwords
  (frequencies (get-words)))

(defn train [features]) ;; This is just Frequencies?

(defn without-char [word i]
  (str (apply str (take i word)) (apply str (drop (inc i) word))))

(defn deletes [word]
	(for [x (range (.length word))]
		{(without-char word x) 1}))

(defn edits1 [word]
  (let [s (for [i (range (inc (count word)))] (split-at i word))
        deletes (deletes word)
        transposes (for [[a,b] s :when (> (count b) 1)]
                     (concat a [(second b)] [(first b)] (drop 2 b)))
        replaces []
        inserts [] ]
    (zipmap (concat deletes transposes replaces inserts) (repeat 1))))


(defn edits1-replaces [a c b]
  (let [adjacents (get back5.error/adjacencies c)]
    (loop [adjacent-chars (apply concat adjacents)
           replaces {}]
      (if (= 0 (count adjacent-chars)) replaces
          (recur (rest adjacent-chars)
                 (assoc replaces (str a (first adjacent-chars) b) 1))))))

(defn known-edits2 [word])

(defn known [words]
  (into {}
        (for [word words
              :when (nwords word)]
          [word 1])))

(defn correct [word]

  )
