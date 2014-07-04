(ns battleships.ui.ui
    (:use [seesaw core color graphics behave])
    (:require [battleships.logic.main :as logic]))

(declare user-field)

(defn shoot-enemy
  "Checks if a buttons is posible to be clicked and if not tries another one"
  [button]
  (if (.isEnabled (nth user-field button))
      (.doClick (nth user-field button))
      (shoot-enemy (rand 81))))

(def f "The main frame"
 (-> (frame :title "Battleships"
            :size [640 :by 480]
            :on-close :exit)
    pack!
    show!))

(def enemy
  "The enemy ships placed on the map that need to be shot down by the user"
  (logic/rand-placed))

(def user
  "Users ships that the PC will try to sunk"
  (logic/rand-placed))

(def user-field
  "Builds the user field like a vector of button that have specific action"
  (doall (for [i (range 82)]
                    (let [x (quot i 9)
                          y (rem i 9)
                          b (button :id i :class :type :text (name (logic/get-field user x y :user )))]
                      (listen b :action (fn [e] (let [field (logic/coord->string x y)]
                                                  (cond ( = ( (keyword field) user) true)
                                                          (do
                                                           (config! b :text (logic/get-field (-> user (dissoc field) (assoc (keyword field) false)) x y :user) :enabled? false)
                                                           (alert "SCORES")
                                                           (shoot-enemy (rand 81)))
                                                        :else (do
                                                                (config! b :text (logic/get-field (-> user (dissoc field) (assoc  (keyword field) "empty")) x y :user) :enabled? false)
                                                                (alert "MISS"))))))
                      b))))

(defn display
  "The display Fucntion"
  [fr content]
  (config! fr :content content)
  content)

(defn make-button-lisener
  "Creates a button and a listener for it . The button will be clicked by the user and will represent an enemy ship"
  [x y]
  (let [b (button :id x :class :type :text (name "-"))]
    (listen b :action (fn [e]
                        (let [field (logic/coord->string x y)]
                          ;(alert e x)
                          (cond ( = ( (keyword field) enemy) true) (config! b :text (logic/get-field (-> enemy (dissoc field) (assoc (keyword field) false)) x y :user) :enabled? false)
                                :else (do (config! b :text (logic/get-field (-> enemy (dissoc field) (assoc  (keyword field) "empty")) x y :user) :enabled? false)
                                          (shoot-enemy (rand 81)))

                          ))))

      b
    ))

(defn horizontal-line
  "Creates the items for the horizontal line with the index 'i' that will represent the enemy field row"
  [x]
  (for [i [1 2 3 4 5 6 7 8 9]]
    (make-button-lisener  x i)))

(defn button-grid
  "Creates the items for the vertical line containing the horizontal line for the enemy "
  []
  (for [i [1 2 3 4 5 6 7 8 9]]
    (horizontal-panel :items(horizontal-line i) :border 0)))

(defn user-horizontal-line
  "Creates the items for the horizontal line with the index 'i' that will represent the user field row"
  [x]
  (for [i [1 2 3 4 5 6 7 8 9]]
    (nth  user-field (+ (* (dec x) 9)  i))))

(defn user-button-grid
  "Creates the items for the vertical line containing the horizontal line for the user "
  []
  (for [i [1 2 3 4 5 6 7 8 9]]
    (horizontal-panel :items(user-horizontal-line i) :border 0)))

(defn start
  "The start Fucntion for the GUI"
  [x]
  (display f (border-panel
                  :north (label :text "Hi ! Lets play some battleships !" :font "ARIAL-BOLD-40" :foreground "#00AA00")
                  :center (vertical-panel :items  (button-grid))
                  :south (vertical-panel :items  (user-button-grid)))))
