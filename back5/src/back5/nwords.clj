(ns back5.nwords)

(defn get-words []
  (let [f (clojure.java.io/reader "resources/big.txt")
        lines-lower (map #(.toLowerCase %) (line-seq f))]
    (mapcat #(re-seq #"[a-z]+" %) lines-lower)))

(defn nwords []
	(frequencies (get-words)))

