(ns back5.spell)

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn get-words []
  (let [f (clojure.java.io/reader "resources/big.txt")
        lines-lower (map #(.toLowerCase %) (line-seq f))]
    (mapcat #(re-seq #"[a-z]+" %) lines-lower)))

(def nwords
  (frequencies (get-words)))

(defn train [features]) ;; This is just Frequencies?

(defn edits1 [word]
  (let [s (for [i (range (inc (count word)))] (split-at i word))
        deletes []
        transposes []
        replaces []
        inserts [] ]
    (zipmap (concat deletes transposes replaces inserts) (repeat 1))))

(defn known-edits2 [word])

(defn known [words]
  (into {}
        (for [word words
              :when (nwords word)]
          [word 1])))

(defn correct [word]

  )
