import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import CountVectorizer
from surprise import SVD
from surprise import KNNWithMeans
from surprise import Dataset
from surprise import Reader
from surprise.model_selection import cross_validate
import matplotlib.pyplot as plt

df = pd.read_csv('ratings_small.csv', nrows=100)

print(df.head(3))

print(df.shape)

ratingsByUser = pd.DataFrame(df.groupby('userId')['rating', 'movieId', 'timestamp'].mean())

print(ratingsByUser.head(3))

ratingsByMovie = pd.DataFrame(df.groupby('movieId')['rating', 'userId', 'timestamp'].mean())

print(ratingsByMovie.head(3))

reader = Reader(rating_scale=(0, 5))

data1 = Dataset.load_from_df(df[['userId', 'movieId', 'rating']], reader)

data2 = Dataset.load_from_df(ratingsByUser[['timestamp', 'movieId', 'rating']], reader)

data3 = Dataset.load_from_df(ratingsByMovie[['timestamp', 'userId', 'rating']], reader)

PMF = SVD()

kval = 5

knn = KNNWithMeans(k=kval, min_k=kval, verbose=False)

cross_validate(PMF, data1, measures=['MAE', 'RMSE'], cv=5, verbose=True)

cross_validate(knn, data2, measures=['MAE', 'RMSE'], cv=3, verbose=True)

cross_validate(knn, data3, measures=['MAE', 'RMSE'], cv=5, verbose=True)


# knn1 = KNNWithMeans(k=2, min_k=1, verbose=False)
#
# knn2 = KNNWithMeans(k=5, min_k=5, verbose=False)
#
# knn3 = KNNWithMeans(k=9, min_k=9, verbose=False)
#
# knn4 = KNNWithMeans(k=15, min_k=15, verbose=False)
#
# knn5 = KNNWithMeans(k=20, min_k=20, verbose=False)
#
# cross_validate(knn1, data3, measures=['MAE', 'RMSE'], cv=5, verbose=True)
#
# cross_validate(knn2, data3, measures=['MAE', 'RMSE'], cv=5, verbose=True)
#
# cross_validate(knn3, data3, measures=['MAE', 'RMSE'], cv=5, verbose=True)
#
# cross_validate(knn4, data3, measures=['MAE', 'RMSE'], cv=5, verbose=True)
#
# cross_validate(knn5, data3, measures=['MAE', 'RMSE'], cv=5, verbose=True)
#
# plt.plot([0.7787, 0.8026, 0.7728, 0.7656, 0.7626])
#
# plt.show()
