import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import random as rnd
import seaborn as sb
from sklearn import tree
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import cross_val_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder

pd.set_option('display.max_columns', 20)
pd.set_option('display.max_rows', 100)

train_df = pd.read_csv('input/train.csv')
test_df = pd.read_csv('input/test.csv')
combine = [train_df, test_df]

labels = [1, 2, 3, 4, 5, 6, 7]
age_bins = [0, 10, 17, 23, 32, 40, 55, 81]
binned_ages = pd.cut(train_df['Age'], age_bins, labels=labels)
train_df['Age_Group'] = binned_ages
fare_bins = [0, 25, 50, 100, 150, 200, 300, 600]
binned_fares = pd.cut(train_df['Fare'], fare_bins, labels=labels)
train_df['Fare_Group'] = binned_fares
Sex_Val = LabelEncoder()

# print(train_df.groupby(['Fare_Group']).mean().sort_values('Survived', ascending=False))
# print(train_df.groupby(['Age_Group']).mean().sort_values('Survived', ascending=False))
# print(train_df.groupby(['Pclass']).mean().sort_values('Survived', ascending=False))
# print(train_df.groupby(['Sex']).mean().sort_values('Survived', ascending=False))

target = train_df['Survived']
modified_train_df = train_df.copy()
modified_train_df['Sex_Val'] = Sex_Val.fit_transform(modified_train_df['Sex'])
modified_train_df = modified_train_df.drop(columns=['PassengerId', 'Survived', 'Name', 'Cabin', 'Ticket', 'Embarked', 'Age', 'Fare', 'Sex'])

# print(modified_train_df)

# this model is using Gini by default
dt_model = tree.DecisionTreeClassifier()
dt_model.fit(modified_train_df, target)
print(dt_model.score(modified_train_df, target))
# print(dt_model.predict(modified_train_df))

rf_model = RandomForestClassifier()
rf_model.fit(modified_train_df, target)
print(rf_model.score(modified_train_df, target))
# print(rf_model.predict(modified_train_df))

# train_x, test_x, train_y, test_y = train_test_split(x, y, test_size=0.2)

tree.plot_tree(dt_model)
plt.show()
