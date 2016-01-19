(ns parser-play.core
  (:require [instaparse.core :as insta]
    [me.raynes.fs :refer [normalized]]
    [clojure.walk :refer [postwalk postwalk-demo]]
    [numbers.transforms :refer [transform-numeric-factor tranform-numeric-exp tranform-numeric-term]]))


(defn transform-empty-list []
  [:list []])

(defn is-leaf [x]
  (println "leaf")
  (println x)
  (cond
    (= :non_empty_list (first x)) true
    (= :assignable_type (first x)) true
   :else false))

(defn get-children [x]
  (println "children")
  (println x)
  (drop 1 x))

(defn transform-non-empty-list[l]
  (let [a (first (tree-seq :children :children l))]
   (println "a")
   (println a)
   (for [item a]
     ((println item)[]))
   []))







(def transform-options
  {:numeric_factor transform-numeric-factor
    :numeric_term tranform-numeric-term
    :numeric_exp tranform-numeric-exp
    :empty_list transform-empty-list
    :list_items transform-non-empty-list})


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
