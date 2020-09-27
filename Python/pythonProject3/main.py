import pandas as pd
import random as rnd
import numpy as np

import sklearn
from sklearn import metrics, neighbors, preprocessing
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
from sklearn.naive_bayes import BernoulliNB, GaussianNB, MultinomialNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import cross_val_score, train_test_split, KFold, cross_val_predict
from sklearn.preprocessing import LabelEncoder

pd.set_option('display.max_columns', 20)
pd.set_option('display.max_rows', 1000)


# Task 1 #
print("Task 1")

train_df = pd.read_csv('train.csv')
test_df = pd.read_csv('test.csv')

HomeOrAway = LabelEncoder()
train_df['HomeOrAway'] = HomeOrAway.fit_transform(train_df['Is_Home_or_Away'])
test_df['HomeOrAway'] = HomeOrAway.fit_transform(test_df['Is_Home_or_Away'])

OutOrIn = LabelEncoder()
train_df['OutOrIn'] = OutOrIn.fit_transform(train_df['Is_Opponent_in_AP25_Preseason'])
test_df['OutOrIn'] = OutOrIn.fit_transform(test_df['Is_Opponent_in_AP25_Preseason'])

MediaNum = LabelEncoder()
train_df['MediaNum'] = MediaNum.fit_transform((train_df['Media']))
test_df['MediaNum'] = MediaNum.fit_transform((test_df['Media']))

WinOrLose = LabelEncoder()
train_df['WinOrLose'] = WinOrLose.fit_transform(train_df['Label'])
test_df['WinOrLose'] = WinOrLose.fit_transform(test_df['Label'])

train_target = train_df['WinOrLose']
test_target = test_df['WinOrLose']

train_df = train_df.drop(columns=["Date", "ID", "Opponent", "Is_Home_or_Away", "Is_Opponent_in_AP25_Preseason", "Media", "Label", "WinOrLose"])
test_df = test_df.drop(columns=["Date", "ID", "Opponent", "Is_Home_or_Away", "Is_Opponent_in_AP25_Preseason", "Media", "Label", "WinOrLose"])

NBclassifier = GaussianNB()
NBclassifier.fit(train_df, train_target)
NBpredictedValues = NBclassifier.predict(test_df)

KNNclassifier = KNeighborsClassifier(n_neighbors=5)
KNNclassifier.fit(train_df, train_target)
KNNpredictedValues = NBclassifier.predict(test_df)

print("Bayesian Values")
print(metrics.accuracy_score(test_target, NBpredictedValues))
print(metrics.precision_score(test_target, NBpredictedValues))
print(metrics.recall_score(test_target, NBpredictedValues))
print(metrics.f1_score(test_target, NBpredictedValues))

print("KNN Values")
print(metrics.accuracy_score(test_target, KNNpredictedValues))
print(metrics.precision_score(test_target, KNNpredictedValues))
print(metrics.recall_score(test_target, KNNpredictedValues))
print(metrics.f1_score(test_target, KNNpredictedValues))

# Task 2 #
print("Task 2")

trainTitanic_df = pd.read_csv('trainTitanic.csv')
testTitanic_df = pd.read_csv('testTitanic.csv')
trainTitanic_target = trainTitanic_df["Survived"]

sex_val = LabelEncoder()
trainTitanic_df['Sex_val'] = sex_val.fit_transform(trainTitanic_df['Sex'])

trainTitanic_df = trainTitanic_df.drop(columns=["PassengerId", "Survived", "Name", "Sex", "Ticket", "Fare", "Cabin", "Embarked"])

trainTitanic_df = trainTitanic_df.fillna(0)

NBclassifier = GaussianNB()
NBpredictedValues = cross_val_predict(NBclassifier, trainTitanic_df, trainTitanic_target)

KNNclassifier = KNeighborsClassifier(n_neighbors=8)
KNNpredictedValues = cross_val_predict(KNNclassifier, trainTitanic_df, trainTitanic_target)

print("Bayesian Values")
print(metrics.accuracy_score(trainTitanic_target, NBpredictedValues))
print(metrics.precision_score(trainTitanic_target, NBpredictedValues))
print(metrics.recall_score(trainTitanic_target, NBpredictedValues))
print(metrics.f1_score(trainTitanic_target, NBpredictedValues))

print("KNN Values")
print(metrics.accuracy_score(trainTitanic_target, KNNpredictedValues))
print(metrics.precision_score(trainTitanic_target, KNNpredictedValues))
print(metrics.recall_score(trainTitanic_target, KNNpredictedValues))
print(metrics.f1_score(trainTitanic_target, KNNpredictedValues))

