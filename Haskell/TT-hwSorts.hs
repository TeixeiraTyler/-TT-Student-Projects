import Data.List

ints = [4, 13, 71, 43, 66, 90, 4, 52, 28]

-- Use bubSort [] to perform bubble sort.

bubbleSorter :: (Ord a) => [a] -> [a]
bubbleSorter (x:y:xs)
	| x > y = y : bubbleSorter (x:xs)
	| otherwise = x : bubbleSorter (y:xs)
bubbleSorter (x) = (x)

bubSort' :: (Ord a) => [a] -> Int -> [a]
bubSort' xs i
	| i == (length xs) = xs
	| otherwise = bubSort' (bubbleSorter xs) (i + 1)

bubSort :: (Ord a) => [a] -> [a]
bubSort xs = bubSort' xs 0

-- Use inSort [] to perform insertion sort.

insertSorter :: (Ord a) => a -> [a] -> [a]
insertSorter x [] = [x]
insertSorter x (y:ys)
	| x < y = x : y : ys
	| otherwise = y : insertSorter x ys

inSort :: (Ord a) => [a] -> [a]
inSort = foldr insertSorter []

-- Use selSort [] to perform selection sort.

selSort :: (Ord a) => [a] -> [a]
selSort [] = []
selSort xs = let x = maximum xs in selSort (remove x xs) ++ [x] 
  where remove _ [] = []
        remove a (x:xs)
          | x == a = xs
          | otherwise = x : remove a xs