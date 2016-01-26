(ns lists.transforms)

(defn transform-empty-list []
  nil)

(defn transform-non-empty-list[head & rest]
  (if (not= nil head)
    [:list
     (into []
      (cons (second head)
         (for [item rest]
           (second item))))]
   [:list []]))
