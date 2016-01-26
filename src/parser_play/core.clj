(ns parser-play.core
  (:require [instaparse.core :as insta]
    [me.raynes.fs :refer [normalized]]
    [clojure.walk :refer [postwalk]]
    [modules.modules :refer [create-module set-var]]
    [numbers.transforms :refer [transform-numeric-factor tranform-numeric-exp tranform-numeric-term]]
    [lists.transforms :refer [transform-empty-list transform-non-empty-list]]))


(def vars(atom {}))

(defn get-vars[id]
  (get @vars id))

(def module-name(atom ""))

(defn transform_def_module
  [[type id]]
  (swap! module-name (fn [a] id))
  (create-module id)
  [:def_module [type id]])

(defn transform_let_dec
  [[t id] [type value]]
  (set-var @module-name id type value)
  [:let_dec [t id] [type value]])

(def transform-options
  {:numeric_factor transform-numeric-factor
    :numeric_term tranform-numeric-term
    :numeric_exp tranform-numeric-exp
    :empty_list transform-empty-list
    :list_init transform-non-empty-list
    :let_dec transform_let_dec
    :def_module transform_def_module})





(def parser
  (insta/parser (clojure.java.io/resource "grammar.bnf")))

(defn parse [input]
 (->> (parser input) (insta/transform transform-options)))


(defn open-and-parse-file [filename]
  (let [text (slurp (normalized filename)) parsed_text(parse text)]
   (println parsed_text)
   (insta/visualize parsed_text)))

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
