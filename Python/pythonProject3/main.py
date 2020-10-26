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
from sklearn import svm

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

clf = svm.SVC(gamma=0.001, C=100)
clf.fit()
pred = clf.predict(test_df)
acc = metrics.accuracy_score(test_df, pred)

print(acc)

