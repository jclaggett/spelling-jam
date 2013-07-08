(ns back5.spell)

(def alphabet "abcdefghijklmnopqrstuvwxyz")

defn get-words []
  (let [f (clojure.java.io/reader "resources/big.txt")
        lines-lower (map #(.toLowerCase %) (line-seq f))]
    (mapcat #(re-seq #"[a-z]+" %) lines-lower)))

(defn nwords []
	(frequencies (get-words)))

(defn train [features]) ;; This is just Frequencies?

(defn edits1 [word]
  (let [s []
        deletes []
        transposes []
        replaces []
        inserts [] ]
    (zipmap (concat deletes transposes replaces inserts) (repeat 1))))

(defn known-edits2 [word])

(defn known1 [words])

(defn correct [word])