(ns brainfuck-clj.core)

(defn move-to-bracket [move source data-pointer]
  (loop [brackets move
         dp data-pointer]
    (if (not (zero? brackets))
      (case (nth source dp)
        \] (recur (dec brackets) (+ dp move))
        \[ (recur (inc brackets) (+ dp move))
        (recur brackets (+ dp move)))
      (- dp move))))

(defn run [source memory init-data-pointer init-memory-pointer stop]
  (loop [dp init-data-pointer
         mp init-memory-pointer
         mem memory]
    (if (not= dp stop)
      (case (nth source dp)
        \> (recur (inc dp) (inc mp) mem)
        \< (recur (inc dp) (dec mp) mem)
        \+ (recur (inc dp) mp (update-in mem [mp] inc))
        \- (recur (inc dp) mp (update-in mem [mp] dec))
        \, (recur (inc dp) mp (assoc mem mp (int (char (. System/in read)))))
        \. (do (print (char (get mem mp))) (recur (inc dp) mp mem))
        \[ (if (== (get mem mp) 0)
             (recur (move-to-bracket 1 source (inc dp)) mp mem)
             (recur (inc dp) mp mem))
        \] (if (not (== (get mem mp) 0))
             (recur (move-to-bracket -1 source (dec dp)) mp mem)
             (recur (inc dp) mp mem))
        (recur (inc dp) mp mem))
      (list mem dp mp)))) ; For testing.

(defn interpreter [source]
  (run source (vec (repeat 30000 0)) 0 0 (count source)))


(defn -main [& args]
  (if (not (= (count args) 1))
    (println "Usage: lein run <file.b>")
    (interpreter (slurp (last args)))))
