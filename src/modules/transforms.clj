(ns modules.transforms
  (:require [modules.modules :refer [create-module set-var load-module]]
   [instaparse.core :as insta]
   [me.raynes.fs :refer [normalized]]
   [clojure.walk :refer [postwalk]]
   [modules.modules :refer [load-module]]
   [numbers.transforms :refer [transform-numeric-factor tranform-numeric-exp tranform-numeric-term]]
   [lists.transforms :refer [transform-empty-list transform-non-empty-list]]))

(declare transform_imports)

(def module-name(atom ""))

(def parser
 (insta/parser (clojure.java.io/resource "grammar.bnf")))

(defn transform_def_module
  [[type id]]
  (swap! module-name (fn [a] id))
  (create-module id)
  [:def_module [type id]])

(defn transform_let_dec
  [[t id] [type value]]
  (set-var @module-name id type value)
  [:let_dec [t id] [type value]])
