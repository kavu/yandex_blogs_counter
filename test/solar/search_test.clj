(ns solar.search-test
  (:require [clojure.test :refer :all]
            [solar.search :refer :all]))

(deftest link-to-domain-test
  (let [f #'solar.search/link-to-domain
        expected "google.com"]
    (testing "Just the domain"
      (is (= expected (f "http://google.com/")))
      (is (= expected (f "http://google.com/woot.html")))
      (is (= expected (f "http://google.com/woot.html?thread=1"))))

    (testing "With domain"
      (is (= expected (f "http://wave.google.com/")))
      (is (= expected (f "http://wave.plus.google.com/woot.html")))
      (is (= expected (f "ftp://google.com/woot.html?thread=1"))))))
