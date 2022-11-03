import os
import re

import matplotlib.pyplot as plt
import numpy as np

results = {}

for filename in os.listdir(os.getcwd() + "\\results"):
    file = open(os.path.join(os.getcwd() + "\\results", filename), "rt")
    contents = file.read()
    file.close()
    method = filename.split("-")[0]
    name = filename.split("-", 1)[1].split(".")[0]
    contents = re.split("[\t\n]", contents)
    contents = [float(i.split(":")[1][1:]) for i in contents][1::2]

    if name in results.keys():
        results[name] += [method, contents]
    else:
        results[name] = [method, contents]

colors = ["blue", "red", "green"]

for key in results.keys():
    ind = np.arange(len(results[key][1]))
    width = 0.3
    fig = plt.figure(figsize=(11, 9))

    for i in range(1, len(results[key]), 2):
        plt.bar(ind + ((i - 1) // 2) * width, results[key][i], color=colors[((i - 1) // 2)], width=width)

    plt.xticks(ind + width / 2, ind)

    plt.xlabel("Philosophers Id")
    plt.ylabel("Average wait time")

    labels = results[key][::2]
    handles = [plt.Rectangle((0, 0), 1, 1, color=colors[i]) for i in range(len(results[key]) // 2)]
    plt.legend(handles, labels)

    plt.title(key)
    plt.show()
