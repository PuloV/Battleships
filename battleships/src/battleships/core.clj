(ns battleships.core
    (:gen-class)
    (:require [battleships.ui.ui :as ui]
              [battleships.ui.console :as console]))

(defn get-first
  "Returns the first param from lein run like a keyword"
  [args]
  (-> args
      (first)
      (keyword)))

(defn -main [&  args]
    (cond
      (= (get-first args) :console) (console/start)
      (= (get-first args) :gui) (prn "GUI")
      :else (ui/start 1)
      ))