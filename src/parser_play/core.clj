(ns parser-play.core
  (:require [instaparse.core :as insta]
    [me.raynes.fs :refer [normalized]]
    [numbers.transforms :refer [transform-numeric-factor tranform-numeric-exp tranform-numeric-term]]))

(def transform-options
  {:numeric_factor transform-numeric-factor
    :numeric_term tranform-numeric-term
    :numeric_exp tranform-numeric-exp})

(def parser
  (insta/parser (clojure.java.io/resource "grammar.bnf")))

(defn parse [input]
 (->> (parser input) (insta/transform transform-options)))


(defn open-and-parse-file [filename]
  (let [text (slurp (normalized filename))]
   (println (parse text))
   (insta/visualize (parse text))))

(defn repl []
  (do
    (print "> ")
    (flush))
  (let [input (read-line)]
    (println (parse input))
    (recur)))


(defn -main
  [& args]
  (if (not= nil args)
   (let [arg (first args) type (second args)]
    (cond
      (= "--file" arg)(open-and-parse-file type)
      (= "--repl" arg)(repl)))))
