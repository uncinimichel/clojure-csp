(ns clojure-csp.core
  (:require [clojure.core.async :as async]))


; A person as a price and it invest the price
; by sending it to a channel
(def invest-atom (atom {:a-person
                        {:price 100}}))

(def invest-chan (async/chan 2))
(def a-person-money (get-in @invest-atom [:a-person :price]))

(async/>!! invest-chan a-person-money)

(async/go-loop []
  (let [money (async/<!! invest-chan)]
    (println money)
    (recur)))

(defn split-money
  "Return a sequence of money slot based on money-split,
  by default it will split money by 1 unit"
 ([money] (split-money money money))
 ([money money-split]
  (let [slot (quot money money-split)]
    (for [_ (range money-split)] slot))))

(defn calculate-interest
  [money]
  (let [is-positive? (= 1 (rand-int 2))
        how-much (rand-int 10)]
    (if is-positive?
      (+ money how-much)
      (let [new-money (- money how-much)]
        (if (neg? new-money)
          0
          new-money)))))

(calculate-interest 1)

(split-money 100 2)

(range 1 10)
(for [x (range 10)]
  10)


; The price arrive to a knowledge engine
; price is split in many prices
; price is invested in a business
; business is choosen randomly
