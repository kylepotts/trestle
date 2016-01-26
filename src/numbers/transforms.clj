(ns numbers.transforms)

(defn tranform-numeric-exp
  ([number][:number number])
  ([number1 op number2]
   (cond
     (= op "+")[:number (+ (second number1) number2)]
     (= op "-")[:number (- (second number1) number2)])))

(defn tranform-numeric-term
  ([number]number)
  ([number1 op number2]
   (cond
     (= op "*")(* number1 number2)
     (= op "/")(double (/ number1 number2)))))

(defn transform-numeric-factor
  [head & rest]
  (if (= nil rest)
   (let [factor-type (first head)]
    (cond
      (= :number factor-type)(read-string (second head))
      (= :ID factor-type)0))

   rest))
