(ns home-photo-bank.test.models.db
  (:require [clojure.test :refer :all]
            [home-photo-bank.models.db :as db]
            [home-photo-bank.utils :as u]            
            [clojure.string :as s]
            [clj-time.core :as t]
            [clj-time.format :as tf]))

(deftest test-1
  (testing "can access db"
    (is (some? (db/all-photos)))))


(deftest test-following-item
  (let [a-list (list {:name "Fred" :age 10}
                     {:name "Bill" :age 11}
                     {:name "James" :age 12}
                     {:name "Jane" :age 13})]
    (testing "simple next"
      (is (= 3 (u/following-item (list 1 2 3 4 5) #(= 2 %))))
      (is (nil? (u/following-item (list 1 2 3 4 5) #(= 5 %)))))
    (testing "dictionary next"
      (let [item2 (u/following-item a-list
                                     #(= "Bill" (:name %)))]
        (is (= "James" (:name item2)))))
    (testing "simple prev"
      (is (= 2 (u/previous-item (list 1 2 3 4 5) #(= 3 %)))))
    (testing "dictionary prev"
      (let [item2 (u/previous-item a-list
                                    #(= "Jane" (:name %)))
            item3 (u/previous-item a-list
                                    #(= "Fred" (:name %)))]
        (is (= "James" (:name item2)))
        (is (nil? item3))))
    ))

(deftest keywords
  (let [photos-mock-1 (list {:keywords (list "hat")}
                            {:keywords (list "hat" "scarf")}
                            {:keywords (list "glove" "scarf")})]
    (testing "simple case"
      (is (= #{"hat" "scarf" "glove"}
             (db/keywords-across-photos photos-mock-1))))))

