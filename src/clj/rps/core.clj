(ns rps.core
(:require [clojure.string :as str]))

(defn key-check [key]
  "checks what character the user input"
  (cond
    (= key "p") (println "you have chosen paper")
    (= key "r") (println "you have chosen rock")
    (= key "s") (println "you have chosen scissors")
    :else (println "Invalid entry")))

(defn computer-player []
  "generates a random input by the computer"
  (rand-nth ["p" "r" "s"]))

(defn case-checker? [str1 str2]
  "checks for string equality despite case difference"
  (= (str/lower-case str1) (str/lower-case str2)))

(defn check-win [computer-input user-input computer-count user-count]
  "checks for  and keeps tally of win(s)/loss(s) by computer and user"
  (cond (case-checker? computer-input user-input) (println "it's a draw")
        (or
         (and (case-checker? user-input "r") (case-checker? computer-input "s"))
         (and (case-checker? user-input "p") (case-checker? computer-input "r"))
         (and (case-checker? user-input "s") (case-checker? computer-input "p")))
        (do
          (println "You win!")
          (swap! user-count inc))
        :else (do
                (println "You lose!")
                (swap! computer-count inc))))

; creating an atom and initializing it to 0 to keep track of computer and user wins
(let [user-count (atom 0)
      computer-count (atom 0)]
  (dotimes [_ 5] ; let's play the game 5 times
    (println "A game of Rock Paper & Scissors. Pres R for Rock, P for Paper and S for Scissors")
    (let [user-input (str/lower-case (read-line))
          computer-input (computer-player)]
      (println "You chose : " user-input)
      (key-check user-input)
      (println "Computer chose : " computer-input)
      (check-win  computer-input user-input computer-count user-count )
      (println "User wins : " @user-count " | Computer wins : " @computer-count))))
