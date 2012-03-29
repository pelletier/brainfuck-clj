(ns brainfuck-clj.test.core
  (:use [brainfuck-clj.core])
  (:use [clojure.test]))

(deftest default-state
  (let [res (interpreter "")]
    (is (every? zero? (first res)))
    (is (= (nth res 1) 0))
    (is (= (last res) 0))))

(deftest inc-mp
  (let [res (interpreter ">")]
    (is (every? zero? (first res)))
    (is (= (nth res 1) 1))
    (is (= (last res) 1))))

(deftest dec-mp
  (let [res (interpreter "<")]
    (is (every? zero? (first res)))
    (is (= (nth res 1) 1))
    (is (= (last res) -1))))

(deftest inc-data
  (let [res (interpreter "+")]
    (is (every? zero? (rest (first res))))
    (is (= 1 (first (first res))))
    (is (= (nth res 1) 1))
    (is (= (last res) 0))))

(deftest dec-data
  (let [res (interpreter "-")]
    (is (every? zero? (rest (first res))))
    (is (= -1 (first (first res))))
    (is (= (nth res 1) 1))
    (is (= (last res) 0))))
