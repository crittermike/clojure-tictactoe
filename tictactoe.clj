(def starting-board [[nil nil nil][nil nil nil][nil nil nil]])

(defn nil-to-space [obj]
  (if (nil? obj)
      " "
      obj))

(defn printable-row [row]
  (apply str 
         (interpose \| 
                    (map nil-to-space 
                         row))))

(defn printable-board [board]
  (apply str
         (interpose "\n-----\n"
                    (map printable-row board))))

(defn prompt-move []
  (println "Enter a number 1-9 of the square you'd like to X")
  (read-line))

(defn pos-to-coords [pos]
  ((juxt quot mod) (dec (read-string pos)) 3))

(defn make-move [move, pos, board]
  (assoc-in board
            (pos-to-coords pos)
            move))

(defn run-game [board]
  (println (printable-board board))
  (run-game (make-move \x
                       (prompt-move)
                       board)))

(run-game starting-board)
