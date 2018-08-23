#Tyler Teixeira

DATA<-read.csv(file.choose(),header=T)

attach(DATA)

RAssignment1Problem1 = function(data.frame)	
{
	subData <- DATA[Month==6 & Day%%2==0 & Wind>9.0, ]
	return(mean(subData$Wind))
}

RAssignment1Problem2 = function(data.frame)
{
	validData <- na.omit(DATA)
	rtnData <- data.frame(Ozone = c(0,0), Solar.R = c(0,0), Wind = c(0,0), Temp = c(0,0))
	rtnData[1,1] <- min(validData$Ozone)
	rtnData[1,2] <- min(validData$Solar.R)
	rtnData[1,3] <- min(validData$Wind)
	rtnData[1,4] <- min(validData$Temp)
	rtnData[2,1] <- max(validData$Ozone)
	rtnData[2,2] <- max(validData$Solar.R)
	rtnData[2,3] <- max(validData$Wind)
	rtnData[2,4] <- max(validData$Temp)
	return(rtnData)
}