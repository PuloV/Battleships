(ns battleships.ui.console
    (:require [clojure.pprint :as pp]
              [battleships.logic.main :as logic]))

(defn print-dec
  "Prints the battlefield"
  [type boats]
  (cond
    (= type :enemy) (println "------------------ Enemy Field -----------------")
    (= type :user)  (println "------------------ Your Field ------------------")
    :else (prn)
    )
  (doseq [x (range 0 10)]
    (doseq [y (range 0 10)]
      (cond (= x 0) (print " " y " ")
            (= y 0) (print " " x " ")
            :else (print " " (logic/get-field boats x y type) " ")))
        (println ""))
  )

(defn build-dec
  "Builds the battlefield and then changes it"
  []
  (let [user-boats (logic/rand-placed)
        enemy-boats (logic/rand-placed)]
   (loop [u-b user-boats
          e-b enemy-boats
          ]
      (print-dec :enemy e-b)
      (print-dec :user u-b)
      (print "Enemy: " e-b)
      (cond
        (not-any? true? (vals e-b)) (println "============\n YOU WIN ! \n============")
        (not-any? true? (vals u-b)) (println "============\n YOU LOSE !\n============")
        :else (recur
                (logic/shoot u-b (logic/one->nine) (logic/one->nine) :enemy)
                (do
                  (println "Fire !")
                  (logic/shoot e-b (read-line) (read-line) :user))
              )
        )

    )))

(defn start
    "Fucntion printing the start message"
    []
    (println)

    (print "============ WELCOME TO BATTLESHIPS ============\n")
    (print "=                                              =\n")
    (print "=        Are you ready to play (y/n) ?         =\n")
    (print "=                                              =\n")
    (print "================================================\n")
    ;(pp/print-table [{:a 1 :b 2 :c 3} {:b 5 :a 7 :c "dog"}])
    (println)
    (let [answ (read-line)]
      (if (= answ "y") (build-dec)))


    )