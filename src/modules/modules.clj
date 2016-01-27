(ns modules.modules
  (:require [me.raynes.fs :refer [normalized]]
            [instaparse.core :as insta]))



(def modules (atom {}))


(defn create-module [id]
  (let [entry {:vars (atom {}) :functions (atom {})}]
   (swap! modules assoc id entry)))


(defn set-var [module-name id type value]
  (let [vars (get-in @modules [module-name :vars]) entry {:type type :value value}]
   (swap! vars assoc id entry)
   (println @vars)))



(defn load-module [module-name parser transform-options]
  (let [text (slurp (normalized (str module-name ".lang"))) parsed_text(->> (parser text)(insta/transform transform-options))]
   (println @modules)))
