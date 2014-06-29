(ns battleships.ui.ui
    (:use seesaw.core))

(declare foo)

(defn start
    "Fucntion printing the start message"
    [x]
    (foo x))

(def menu-items
  [
   (label :text "Funds:"
          :foreground "#4f81bd"
          :background "#4f81bd")])

(defn make-panel []
  (border-panel
    ;:west (vertical-panel :items menu-items
    ;                      :background :green)
    :center (canvas :paint #(.drawString %2 "I'm a canvas" 10 10))
                    :background :black
                    :cursor :hand))

(defn display [fr content]

  (config! fr :content content)
  content)

(defn foo
  "I don't do a whole lot."
  [x]
  (display (-> (frame :title "Battleships",
      :content "Event 2",
      :size [640 :by 480]
      :on-close :exit)
      show!
      ) make-panel) )