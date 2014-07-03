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

(defn one->nine
  []
  "Returns a integer from [1:9]"
  (inc (rand-int 9)))

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
                (conj ships (build-ship (:size (first ship)) (one->nine) (one->nine) ))))
  ))
(defn get-field
  "Returns the value of the cell"
  [ships x y type]
  (let [field (coord->string x y)]
     (cond ( = ( (keyword field) ships) false) "!"
           ( = ( (keyword field) ships) "empty") "X"
           (and ( = ( (keyword field) ships) true ) (= type :user)) "@"
          :else "-")))

(defn shoot
  "The logic in a shoot from a ship !
  If there is a successfull shot on the coordinates -> create new hash of ships"
  [ships coordX coordY type]
  (let [field (coord->string coordX coordY)]
    (println "Shoot: " coordX coordY ships)
     (cond ( = ( (keyword field) ships) true)
            (do (println "Aaaaand scooores")
              (if (= type :user)
                (do (println "Fire again !")
                    (shoot (-> ships (dissoc field) (assoc (keyword field) false))
                          (read-line)
                          (read-line)
                          type))
                (do (println "Enemy fires again !")
                                (shoot (-> ships (dissoc field) (assoc (keyword field) false))
                                      (one->nine)
                                      (one->nine)
                                      type))))

           ( = ( (keyword field) ships) false) (do (println type "Missed try again ") ships)
          :else (do (println type "Missed try again ")
                 (-> ships (dissoc field) (assoc  (keyword field) "empty"))))))
