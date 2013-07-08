(ns back5.error)

;; 
;; from: https://github.com/mintplant/node-zxcvbn/blob/master/zxcvbn/adjacency_graphs.js

(def adjacencies
  {"!" ["`~", nil, nil, "2@", "qQ", nil]
   "\"" [";:", "[{", "]}", nil, nil, "/?"]
   ">#" ["2@", nil, nil, "4$", "eE", "wW"]
   "$" ["3#", nil, nil, "5%", "rR", "eE"]
   "%" ["4$", nil, nil, "6^", "tT", "rR"]
   "&" ["6^", nil, nil, "8*", "uU", "yY"]
   "'" [";:", "[{", "]}", nil, nil, "/?"]
   "(" ["8*", nil, nil, "0)", "oO", "iI"]
   ")" ["9(", nil, nil, "-_", "pP", "oO"]
   "*" ["7&", nil, nil, "9(", "iI", "uU"]
   "+" ["-_", nil, nil, nil, "]}", "[{"]
   "," ["mM", "kK", "lL", ".>", nil, nil]
   "-" ["0)", nil, nil, "=+", "[{", "pP"]
   "." [",<", "lL", ";:", "/?", nil, nil]
   "/" [".>", ";:", "'\"", nil, nil, nil]
   "0" ["9(", nil, nil, "-_", "pP", "oO"]
   "1" ["`~", nil, nil, "2@", "qQ", nil]
   "2" ["1!", nil, nil, "3#", "wW", "qQ"]
   "3" ["2@", nil, nil, "4$", "eE", "wW"]
   "4" ["3#", nil, nil, "5%", "rR", "eE"]
   "5" ["4$", nil, nil, "6^", "tT", "rR"]
   "6" ["5%", nil, nil, "7&", "yY", "tT"]
   "7" ["6^", nil, nil, "8*", "uU", "yY"]
   "8" ["7&", nil, nil, "9(", "iI", "uU"]
   "9" ["8*", nil, nil, "0)", "oO", "iI"]
   ":" ["lL", "pP", "[{", "'\"", "/?", ".>"]
   ";" ["lL", "pP", "[{", "'\"", "/?", ".>"]
   "<" ["mM", "kK", "lL", ".>", nil, nil]
   "=" ["-_", nil, nil, nil, "]}", "[{"]
   ">" [",<", "lL", ";:", "/?", nil, nil]
   "?" [".>", ";:", "'\"", nil, nil, nil]
   "@" ["1!", nil, nil, "3#", "wW", "qQ"]
   "A" [nil, "qQ", "wW", "sS", "zZ", nil]
   "B" ["vV", "gG", "hH", "nN", nil, nil]
   "C" ["xX", "dD", "fF", "vV", nil, nil]
   "D" ["sS", "eE", "rR", "fF", "cC", "xX"]
   "E" ["wW", "3#", "4$", "rR", "dD", "sS"]
   "F" ["dD", "rR", "tT", "gG", "vV", "cC"]
   "G" ["fF", "tT", "yY", "hH", "bB", "vV"]
   "H" ["gG", "yY", "uU", "jJ", "nN", "bB"]
   "I" ["uU", "8*", "9(", "oO", "kK", "jJ"]
   "J" ["hH", "uU", "iI", "kK", "mM", "nN"]
   "K" ["jJ", "iI", "oO", "lL", ",<", "mM"]
   "L" ["kK", "oO", "pP", ";:", ".>", ",<"]
   "M" ["nN", "jJ", "kK", ",<", nil, nil]
   "N" ["bB", "hH", "jJ", "mM", nil, nil]
   "O" ["iI", "9(", "0)", "pP", "lL", "kK"]
   "P" ["oO", "0)", "-_", "[{", ";:", "lL"]
   "Q" [nil, "1!", "2@", "wW", "aA", nil]
   "R" ["eE", "4$", "5%", "tT", "fF", "dD"]
   "S" ["aA", "wW", "eE", "dD", "xX", "zZ"]
   "T" ["rR", "5%", "6^", "yY", "gG", "fF"]
   "U" ["yY", "7&", "8*", "iI", "jJ", "hH"]
   "V" ["cC", "fF", "gG", "bB", nil, nil]
   "W" ["qQ", "2@", "3#", "eE", "sS", "aA"]
   "X" ["zZ", "sS", "dD", "cC", nil, nil]
   "Y" ["tT", "6^", "7&", "uU", "hH", "gG"]
   "Z" [nil, "aA", "sS", "xX", nil, nil]
   "[" ["pP", "-_", "=+", "]}", "'\"", ";:"]
   "\\" ["]}", nil, nil, nil, nil, nil]
   "]" ["[{", "=+", nil, "\\|", nil, "'\""]
   "^" ["5%", nil, nil, "7&", "yY", "tT"]
   "_" ["0)", nil, nil, "=+", "[{", "pP"]
   "`" [nil, nil, nil, "1!", nil, nil]
   "a" [nil, "qQ", "wW", "sS", "zZ", nil]
   "b" ["vV", "gG", "hH", "nN", nil, nil]
   "c" ["xX", "dD", "fF", "vV", nil, nil]
   "d" ["sS", "eE", "rR", "fF", "cC", "xX"]
   "e" ["wW", "3#", "4$", "rR", "dD", "sS"]
   "f" ["dD", "rR", "tT", "gG", "vV", "cC"]
   "g" ["fF", "tT", "yY", "hH", "bB", "vV"]
   "h" ["gG", "yY", "uU", "jJ", "nN", "bB"]
   "i" ["uU", "8*", "9(", "oO", "kK", "jJ"]
   "j" ["hH", "uU", "iI", "kK", "mM", "nN"]
   "k" ["jJ", "iI", "oO", "lL", ",<", "mM"]
   "l" ["kK", "oO", "pP", ";:", ".>", ",<"]
   "m" ["nN", "jJ", "kK", ",<", nil, nil]
   "n" ["bB", "hH", "jJ", "mM", nil, nil]
   "o" ["iI", "9(", "0)", "pP", "lL", "kK"]
   "p" ["oO", "0)", "-_", "[{", ";:", "lL"]
   "q" [nil, "1!", "2@", "wW", "aA", nil]
   "r" ["eE", "4$", "5%", "tT", "fF", "dD"]
   "s" ["aA", "wW", "eE", "dD", "xX", "zZ"]
   "t" ["rR", "5%", "6^", "yY", "gG", "fF"]
   "u" ["yY", "7&", "8*", "iI", "jJ", "hH"]
   "v" ["cC", "fF", "gG", "bB", nil, nil]
   "w" ["qQ", "2@", "3#", "eE", "sS", "aA"]
   "x" ["zZ", "sS", "dD", "cC", nil, nil]
   "y" ["tT", "6^", "7&", "uU", "hH", "gG"]
   "z" [nil, "aA", "sS", "xX", nil, nil]
   "{" ["pP", "-_", "=+", "]}", "'\"", ";:"]
   "|" ["]}", nil, nil, nil, nil, nil]
   "}" ["[{", "=+", nil, "\\|", nil, "'\""]
   "~" [nil, nil, nil, "1!", nil, nil]})
