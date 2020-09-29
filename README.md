# Simple Math Parser
A command line tool that evaluates infix mathematical expressions and supports variables. Operations currently supported include **addition**, **subtraction**, **multiplication** and **division**. The tool takes the precedence of these 4 operations into consideration when evaluating a non-trivial mathematical expression. Parenthesis can also be used to override the precedence of operators.

## Sample Usage
There are two use cases of the math parser demonstrated below, one without any variables, and one demonstrating how variables can be used.

1. No-Variable Usecase
```
>>> 8 * 9 + (18/2) - 4
77
```
2. Same expression, but with variables!
```
>>> a = 8 * 9
>>> b = 18/2
>>> a + b - 4
77
```
