import os
import graphviz

os.environ["PATH"] += os.pathsep + 'C:\\Program Files\\Graphviz\\bin\\'


def graph_calculation(word, D):  # Graph edges are calculated using D
    relations = []
    numbers_into_letters = {_: word[_] for _ in range(len(word))}
    for i in range(len(word)):
        tmp = []
        for j in range(i + 1, len(word)):
            if word[j] in D[numbers_into_letters[i]]:
                tmp.append(j)
        relations.append(tmp)

    list_of_edges_for_graph = []  # Removal of redundant edges
    for i in range(len(word)):
        tmp = []
        for j in relations[i]:
            if not rec(i, i, j, relations):
                tmp.append(j)
        list_of_edges_for_graph.append(tmp)
    return list_of_edges_for_graph


def graph_generation(g, list_of_edges_for_graph, word):  # Graph is generated using calculated edges
    for i in range(len(word)):
        g.node(str(i), word[i])

    for i in range(len(word)):
        for j in list_of_edges_for_graph[i]:
            g.edge(str(i), str(j))
    return g


def rec(curr, checked, searched, relations):
    # Recursive removal of redundant edges in graph, function checks if "child" of node connects to another of it's
    # children, if not, checks children of any child until it finds indirect connection or checks everything
    if checked == searched:
        return True
    if not relations[checked]:
        return False
    iterator = 0
    while relations[checked][iterator] <= searched:
        if checked == curr and relations[checked][iterator] == searched:
            return False
        if rec(curr, relations[checked][iterator], searched, relations):
            return True
        iterator += 1
    return False


def calculate_fnf(list_of_stacks, alphabet, word_len, D):
    # FNF is calculated using method described in the document attached to the task description, the document itself
    # can be found here: https://citeseerx.ist.psu.edu/pdf/d67ac4c1e5967f7e114f390245f28909f259c034
    FNF = ""
    count = 0
    while count < word_len:
        tmp = set()
        for letter in alphabet:
            if list_of_stacks[letter][-1] != "*":
                tmp.add(list_of_stacks[letter].pop())

        FNF += "("
        for letter in tmp:
            for letter2 in D[letter]:
                if letter != letter2:
                    list_of_stacks[letter2].pop()
            FNF += letter
            count += 1
        FNF += ")"
    return FNF


def calculate_list_of_stacks(word, alphabet, D):
    # List of stacks (this implementation uses Python List) calculated for further use using method desribed in
    # the document given in the task description https://citeseerx.ist.psu.edu/pdf/d67ac4c1e5967f7e114f390245f28909f259c034
    list_of_stacks = {i: [] for i in alphabet}

    for letter in word[::-1]:
        for letter2 in alphabet:
            if letter == letter2:
                list_of_stacks[letter2].append(letter2)
            elif letter2 in D[letter]:
                list_of_stacks[letter2].append("*")
    return list_of_stacks


def parser(productions_input, alphabet_input, word_input, partially_parsed):
    # Parser of input data, allows for different input if user wishes to give already partially parsed one(example
    # provided in main.py after normal input of example 1 from task description), implemented due to its help in testing
    if not partially_parsed:
        productions = [i for i in productions_input.split("\n")][:-1]
        alphabet = [i for i in alphabet_input.split("{")[1]][:-1:2]
        word = [i for i in word_input.split("=")[1][1:]]
    else:
        productions = productions_input
        alphabet = alphabet_input
        word = word_input

    first_letters = [i[4] for i in productions]
    dict1 = {}

    for row in productions:
        tmp_dict1 = [row[4]]
        for char in row.split("=")[1]:
            if char in first_letters and char not in tmp_dict1:
                tmp_dict1.append(char)

        dict1[row[1]] = tmp_dict1
    dict2 = {}

    for letter in first_letters:
        first_char = []
        rest_chars = []
        for letter2 in alphabet:
            if letter in dict1[letter2]:
                first_char.append(letter2)
            if letter == dict1[letter2][0]:
                rest_chars.append(letter2)
        dict2[letter] = [first_char, rest_chars]

    D = {}
    I = {}
    D_string = "{"
    I_string = "{"

    for letter in alphabet:
        tmp_D = []
        tmp_I = []
        tmp_D_string = ""
        tmp_I_string = ""
        letters = set()

        for i in dict2[dict1[letter][0]][0]:
            letters.add(i)
        for letter2 in dict1[letter][1:]:
            for i in dict2[letter2][1]:
                letters.add(i)

        for letter2 in alphabet:
            if letter2 in letters:
                tmp_D.append(letter2)
                tmp_D_string += ("(" + letter + "," + letter2 + "),")
            else:
                tmp_I.append(letter2)
                tmp_I_string += ("(" + letter + "," + letter2 + "),")

        D[letter] = tmp_D
        I[letter] = tmp_I
        D_string += tmp_D_string
        I_string += tmp_I_string

    D_string = D_string[:-1] + "}"
    I_string = I_string[:-1] + "}"
    return D, I, word, alphabet, D_string, I_string


def scheduling_problem(productions_input, alphabet_input, word_input, save_path, partially_parsed=False):
    D, I, word, alphabet, D_string, I_string = parser(productions_input, alphabet_input, word_input, partially_parsed)
    word_len = len(word)
    list_of_stacks = calculate_list_of_stacks(word, alphabet, D)

    FNF = calculate_fnf(list_of_stacks, alphabet, word_len, D)

    list_of_edges_for_graph = graph_calculation(word, D)

    g = graphviz.Digraph(save_path + "\\dependencies_graph")
    graph_generation(g, list_of_edges_for_graph, word)
    g.view()

    return D_string, I_string, FNF
