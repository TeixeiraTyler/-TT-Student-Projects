import Data.List

ints = [4, 13, 71, 43, 66, 90, 4, 52, 28]

flts = [11.3, 9.8, 31.6, 18.9, 82.1, 45.5, 41.7]

intList = sort ints
fltList = sort flts

listMax :: [l] -> l
listMax l = last l

listMin :: [l] -> l
listMin l = head l

len :: [l] -> Int
len l = length l

median :: [l] -> l
median l = l !! (round (((fromIntegral (len l)) / 2.0) - 0.5))

listSum :: (Num d) => [d] -> d
listSum [] = 0
listSum (x:xs) = x + sum xs

mean :: (Real r, Fractional f) => [r] -> f
mean xs = realToFrac (listSum xs) / genericLength xs