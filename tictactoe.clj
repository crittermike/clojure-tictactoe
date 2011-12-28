(def starting-board [[1 2 3][4 5 6][7 8 9]])

(defn num-to-space 
  "Converts the param to a space if the param is a number. Used when printing board."
  [test]
  (if (number? test)
      " "
      test))

(defn switch-players 
  "Swaps 1 with 2 and vise versa."
  [player]
  (if (= player 1)
      2
      1))

(defn get-move 
  "Returns the X char if player 1, otherwise O."
  [player]
  (if (= player 1)
      \X
      \O))

(defn third
  "Returns the third item in the sequence."
  [obj]
  (nth obj 2))

(defn draw?
  "Returns true if the board is full, hence a draw, otherwise false."
  [board]
  (nil? (some #(some number? %) board)))

(defn winner?
  "Returns true if the board has a winner, otherwise false."
  [board]
    (or
      ; rows
      (apply = (first board))
      (apply = (second board))
      (apply = (third board))
      ; columns
      (apply = (map first board))
      (apply = (map second board))
      (apply = (map third board))
      ; diagonals
      (= (first (first board))
         (second (second board))
         (third (third board)))
      (= (third (first board))
         (second (second board))
         (first (third board)))))

(defn printable-row 
  "Converts a 3-item vector to a printable row, so for example
   [X, 5, O] becomes 'X| |O'"
  [row]
  (apply str (interpose \| (map num-to-space row))))

(defn printable-board 
  "Converts a 3x3 vector into a printable board, using rows from
   printable-row interposed with lines with dashes."
  [board]
  (apply str (interpose "\n-+-+-\n" (map printable-row board))))

(defn pos-to-coords 
  "Takes a number 1-9 and returns the appropriate vector key. Examples: 
   2 returns [0, 1]
   5 returns [1, 1]
   9 returns [2, 2]
   1 returns [0, 0]"
  [pos]
  ((juxt quot mod) (dec (read-string pos)) 3))

(defn valid-move?
  "Returns true (valid move) only if the desired vector key's value
   is null, meaning it's unoccupied."
  [board pos]
  (if (number? (read-string pos))
      (number? (get-in board (pos-to-coords pos)))
      false)) 

(defn prompt-move 
  "Ask the player for the move, and read the input."
  [player]
  (println (str "Player " player ", enter a number 1-9 of the square you want"))
  (read-line))

(defn make-move 
  "Given a player, the desired position and the current board,
   make the move if it's valid, otherwise return recursively to try again."
  [player, pos, board]
  (if (valid-move? board pos)
      (assoc-in board
                (pos-to-coords pos)
                (get-move player))
      (make-move player (prompt-move player) board)))

(defn run-game 
  "The game loop. Print the board and then do the heavy lifting, returning recursively."
  [board player]
  (println (printable-board board))
  (cond 
    (draw? board) (println "There was a draw!")
    (winner? board) (println (str "Player " (switch-players player) " has won!"))
    :else (run-game (make-move player
                               (prompt-move player)
                               board)
                    (switch-players player))))

(run-game starting-board 1)
