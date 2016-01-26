(ns modules.modules)

(def modules (atom {}))


(defn create-module [id]
  (let [entry {:vars (atom {}) :functions (atom {})}]
   (swap! modules assoc id entry)))


(defn set-var [module-name id type value]
  (let [vars (get-in @modules [module-name :vars]) entry {:type type :value value}]
   (swap! vars assoc id entry)
   (println @vars)))
