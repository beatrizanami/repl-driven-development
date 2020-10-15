(ns volcanoes.core-test
  (:require [clojure.test :refer :all]
            [volcanoes.core :refer :all]))

(deftest transform-header-test
  (testing "Test header string to keyword transformation"
    (is (= :hello (transform-header "heLLo")))))

(deftest parse-eruption-date-test
  (testing "Test year transformation to integer"
    (is (= -189 (parse-eruption-date "189 BCE")))))
