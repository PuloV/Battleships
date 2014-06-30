(ns battleships.logic.main)

(def all-ships
  "All ships possible to have on the field"
  [{:type :big :size 3}
  {:type :big :size 3}
  {:type :small :size 1}
  {:type :small :size 1}
  {:type :small :size 1}
  {:type :small :size 1}
  {:type :medium :size 2}
  {:type :medium :size 2}
  {:type :medium :size 2}])

(defn coord->string
  [x y]
  "Tranclates the coordinates to a string"
  (clojure.string/join "_" [(str x) (str y)] ))

(defn build-ship
  "Returns a whole hash build ship"
  [size coordX coordY]
  (let [to-top (rand-int 2)]
    (loop [iter (range 0 (inc size))
           ship {}]
      (if (= (first iter) size) ship
          (recur (rest iter)
              (if (= to-top 0)
                  (conj ship { (keyword(coord->string (+ coordX (first iter)) coordY)) true })
                  (conj ship { (keyword(coord->string coordX (+ coordY (first iter)))) true })))
      ))))

(defn rand-placed
  "Places ships on random spots"
  []
  (loop [ship all-ships
        ships {}]
    (if (empty? ship) ships
         (recur (rest ship)
                ;(conj ships { (keyword(coord->string (rand-int 10) (rand-int 10))) true })))
                (conj ships (build-ship (:size (first ship)) (inc (rand-int 9)) (inc (rand-int 9)) ))))
  ))
(defn get-field
  "Returns the value of the cell"
  [ships x , y]
  (let [field (coord->string x y)]
     (cond ( = ( (keyword field) ships) false) "!"
           ( = ( (keyword field) ships) "empty") "X"
           ( = ( (keyword field) ships) true) "@"
          :else "-")))

(defn shoot [ships coordX coordY type]
  (let [field (coord->string coordX coordY)]
    (println "Shoot: " coordX coordY ships)
     (cond ( = ( (keyword field) ships) true)
            (do (println "Aaaaand scooores")
              (if (= type :user)
                (shoot (-> ships (dissoc field) (assoc (keyword field) false))
                      (read-line)
                      (read-line)
                      type)
                (shoot (-> ships (dissoc field) (assoc (keyword field) false))
                      (rand-int 10)
                      (rand-int 10)
                      type)))

           ( = ( (keyword field) ships) false) (do (println type "Missed try again ") ships)
          :else (do (println type "Missed try again ")
                 (-> ships (dissoc field) (assoc  (keyword field) "empty"))))))

; (defn get-board [ships]
;   (prn (for [x (range 0 10)
;            y (range 0 10)]
;         (get-field ships x y))))