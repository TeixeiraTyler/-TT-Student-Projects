--Tyler Teixeira
--ty034938

import Data.Char

--subtract 97 to base letters at 0.
let2nat :: Char -> Int
let2nat x = 
	if (x == ' ' || x == '_')
		then ord x
	else ord (toLower x) - 97

--add 97 to raise numbers back to ascii letters.
nat2let :: Int -> Char
nat2let x = 
	if (chr x == ' ' || chr x == '_')
		then chr x
	else chr (x + 97)

--mod 26 to wrap around.
shift :: Int -> Char -> Char
shift n c = 
	if (let2nat c >= 0 && let2nat c <= 25)
		then nat2let (((let2nat c) + n) `mod` 26)
	else c

encode :: Int -> String -> String
encode n str = map (shift n) str

decode :: Int -> String -> String
decode n str = map (shift (26 - n)) str

table :: [Float]
table = [8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0, 0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0, 6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1]

lowers :: String -> Int
lowers str = length (filter isLower str)

--count input letter 'c' within string
count :: Char -> String -> Int
count c str = length (filter (==c) str)

percent :: Int -> Int -> Float
percent x y = ((realToFrac x / realToFrac y) * 100.0)

freqs :: String -> [Float]
freqs str = [percent  (count a str) (lowers str) | a <- ['a'..'z']]

rotate :: Int -> [a] -> [a]
rotate n str = (drop n str) ++ (take n str)

chisqr :: [Float] -> [Float] -> Float
chisqr os es = sum [(x - y)^2 / y | (x,y) <- zip os es]

position :: Eq a => a -> [a] -> Int
position x xs = head [b | (a,b) <- zip xs [0..(length xs - 1)], a == x]

crack :: String -> String
crack str = decode (position (minimum (calculate str)) (calculate str)) str
calculate str = [chisqr (freqs (decode a str)) table | a <- [0..25]] 