(def starting-board [[nil nil nil][nil nil nil][nil nil nil]])

(defn nil-to-space 
  "Converts obj to a space if obj is null"
  [obj]
  (if (nil? obj)
      " "
      obj))

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

(defn printable-row 
  "Converts a 3-item vector to a printable row, so for example
   [X, nil, O] becomes 'X| |O'"
  [row]
  (apply str (interpose \| (map nil-to-space row))))

(defn printable-board 
  "Converts a 3x3 vector into a printable board, using rows from
   printable-row interposed with lines with dashes."
  [board]
  (apply str (interpose "\n-----\n" (map printable-row board))))

(defn valid-move 
  "Returns true (valid move) only if the desired vector key's value
   is null, meaning it's unoccupied."
  [board coords]
  (nil? (get-in board coords)))

(defn prompt-move 
  "Ask the player for the move, and read the input."
  [player]
  (println (str "Player " player ", enter a number 1-9 of the square you want"))
  (read-line))

(defn pos-to-coords 
  "Takes a number 1-9 and returns the appropriate vector key. Examples: 
   2 returns [0, 1]
   5 returns [1, 1]
   9 returns [2, 2]
   1 returns [0, 0]"
  [pos]
  ((juxt quot mod) (dec (read-string pos)) 3))

(defn make-move 
  "Given a player, the desired position and the current board,
   make the move if it's valid, otherwise return recursively to try again."
  [player, pos, board]
  (if (valid-move board (pos-to-coords pos))
      (assoc-in board
                (pos-to-coords pos)
                (get-move player))
      (make-move player (prompt-move player) board)))

(defn run-game 
  "The game loop. Print the board and then do the heavy lifting, returning recursively."
  [board player]
  (println (printable-board board))
  (run-game (make-move player
                       (prompt-move player)
                       board)
            (switch-players player)))

(run-game starting-board 1)
