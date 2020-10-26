import pandas as pd
from matplotlib import pyplot as plt
import numpy as np


def euclidean_distance(x1, x2):
    return np.sqrt(np.sum((x1 - x2) ** 2))


def cosine_similarity(x1, x2):
    return np.dot(x1, x2)


def jaccard_similarity(x1, x2):
    return


class KMeans:

    def __init__(self, K):
        self.K = K
        self.max_iters = 100

        self.clusters = [[] for _ in range(self.K)]
        self.centroids = []

    def predict(self, X):
        self.X = X
        self.n_samples, self.n_features = X.shape

        random_sample_idxs = np.random.choice(self.n_samples, self.K, replace=False)
        self.centroids = [self.X[idx] for idx in random_sample_idxs]

        for _ in range(self.max_iters):
            self.clusters = self.makeClusters(self.centroids)
            centroids_old = self.centroids
            self.centroids = self.getCentroids(self.clusters)
            if self.converged(centroids_old, self.centroids):
                break
        return self.getLabels(self.clusters)

    def getLabels(self, clusters):
        labels = np.empty(self.n_samples)
        for cluster_idx, cluster in enumerate(clusters):
            for sample_idx in cluster:
                labels[sample_idx] = cluster_idx
        return labels

    def makeClusters(self, centroids):
        clusters = [[] for _ in range(self.K)]
        for idx, sample in enumerate(self.X):
            centroid_idx = self.closestCentroid(sample, centroids)
            clusters[centroid_idx].append(idx)
        return clusters

    def closestCentroid(self, sample, centroids):
        distances = [euclidean_distance(sample, point) for point in centroids]
        closest_idx = np.argmin(distances)
        return closest_idx

    def getCentroids(self, clusters):
        centroids = np.zeros((self.K, self.n_features))
        for cluster_idx, cluster in enumerate(clusters):
            cluster_mean = np.mean(self.X[cluster], axis=0)
            centroids[cluster_idx] = cluster_mean
        return centroids

    def converged(self, centroids_old, centroids):
        distances = [euclidean_distance(centroids_old[i], centroids[i]) for i in range(self.K)]
        return sum(distances) == 0

    def plot(self):
        fig, ax = plt.subplots(figsize=(10, 10))

        for i, index in enumerate(self.clusters):
            point = self.X[index].T
            ax.scatter(*point)

        for point in self.centroids:
            ax.scatter(*point)
        plt.show()


df = pd.DataFrame({
    'x': [3, 3, 2, 2, 6, 6, 7, 7, 8, 7],
    'y': [5, 4, 8, 3, 2, 4, 3, 4, 5, 6]
})

np.random.seed(100)

km = KMeans(K=2)
pred = km.predict(df)
km.plot()
