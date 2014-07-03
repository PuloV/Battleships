(ns battleships.ui.ui
    (:use [seesaw core color graphics behave])
    (:require [battleships.logic.main :as logic]))

(declare foo)

(def f (-> (frame :title "Battleships",
              :size [640 :by 480]
              :on-close :exit)
        pack! ))

(def enemy  (logic/rand-placed))

(defn display [fr content]
  (config! fr :content content)
  content)

(defn make-button-lisener [x y]
  (let [b (button :id x :class :type :text (name "-"))]
    (listen b :action (fn [e]
                        (let [field (logic/coord->string x y)]
                          ;(alert e x)
                          (cond ( = ( (keyword field) enemy) true) (config! b :text (logic/get-field (-> enemy (dissoc field) (assoc (keyword field) false)) x y :user))
                                :else (config! b :text (logic/get-field (-> enemy (dissoc field) (assoc  (keyword field) "empty")) x y :user))

                          ))))
      b
    ))

(defn horizontal-line [x]
  (for [i [1 2 3 4 5 6 7 8 ]]
      (make-button-lisener  x i)))

(defn button-grid []
  (for [i [1 2 3 4 5 6 7 8 ]]
      (horizontal-panel :items(horizontal-line i) :border 0)))

(defn start
    "Fucntion printing the start message"
    [x]

    (display f (border-panel
                    :north (label :text "Hi ! Lets play some battleships !" :font "ARIAL-BOLD-40" :foreground "#00AA00")
                    :center (vertical-panel :items  (button-grid))
                    :south (vertical-panel :items  (button-grid))))

    )


