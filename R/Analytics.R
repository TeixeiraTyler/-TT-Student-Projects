#Tyler Teixeira

library(rjson)
library(jsonlite)
library(stringr)
library(data.table)
library(dplyr)

#function given in announcement that counts num of words.
nwords <- function(string, pseudo=F){
ifelse( pseudo, 
pattern <- "\\S+", 
pattern <- "[[:alpha:]]+" 
)
str_count(string, pattern)
}

#Use the following line to set your directory			<------------***
setwd("/Users/tyler/Desktop/R Stuff")



DATA <- stream_in(file("review.data"))

newDATA <- subset(DATA, nwords(DATA$reviewText) > 100)

result <- split(newDATA, newDATA$overall)

write(toJSON(result), file = "output.json")

cat("Rows found with greater than 100 words:", nrow(newDATA))

cat("Check file 'output.json' for formatted result.")