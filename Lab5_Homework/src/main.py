import os

from SchedulingProblem import scheduling_problem

productions_input_ex1 = """(a) x := x + y
(b) y := y + 2z
(c) x := 3x + z
(d) z := y − z
"""
alphabet_input_ex1 = "A = {a,b,c,d}"
word_input_ex1 = "w = baadcb"
# productions = ["(a) x := x + y", "(b) y := y + 2z", "(c) x := 3x + z", "(d) z := y − z"]
# alphabet = ["a", "b", "c", "d"]
# word = ["b", "a", "a", "d", "c", "b"]

productions_input_ex2 = """(a) x := x + 1
(b) y := y + 2z
(c) x := 3x + z
(d) w := w + v
(e) z := y − z
(f) v := x + v
"""
alphabet_input_ex2 = "A = {a,b,c,d,e,f}"
word_input_ex2 = "w = acdcfbbe"

save_path = "solution1"
result = scheduling_problem(productions_input_ex1, alphabet_input_ex1, word_input_ex1, save_path)
print("===== Example 1 solution =====")
print("D = " + result[0])
print("I = " + result[1])
print("FNF = " + result[2])

solution_save_path = os.path.join(save_path, "solution.txt")

with open(solution_save_path, "w+") as f:
    f.write("===== Example 1 solution =====")
    f.write("\nD = " + result[0])
    f.write("\nI = " + result[1])
    f.write("\nFNF = " + result[2])

save_path = "solution2"
result = scheduling_problem(productions_input_ex2, alphabet_input_ex2, word_input_ex2, save_path)
print("===== Example 2 solution =====")
print("D = " + result[0])
print("I = " + result[1])
print("FNF = " + result[2])

solution_save_path = os.path.join(save_path, "solution.txt")

with open(solution_save_path, "w+") as f:
    f.write("===== Example 2 solution =====")
    f.write("\nD = " + result[0])
    f.write("\nI = " + result[1])
    f.write("\nFNF = " + result[2])