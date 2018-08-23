main = do
	putStrLn "Hello, I am Tyler Teixeira"
	
	let colors = ["Red","Green","Blue","White","Black","Yellow","Magenta","Cyan","Gray","Salmon","Purple","Lime"]
	putStr "colors = "
	print (colors)
	
	let months = ["January","February","March","April","May","June","July","August","September","October","November","December"]
	putStr "months = "
	print (months)

	putStr "tupleList = "
	let tupleList = zip colors months
	print (tupleList)
	
	putStr "first = "
	let first = head tupleList
	print (first)
	
	putStr "third = "
	let third = tupleList !! 2
	print (third)

	putStr "reverseTupleList"
	let revtupleList = reverse tupleList
	print (revtupleList)