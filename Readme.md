![Meta Heuristics](https://docs.google.com/drawings/d/1GvSXJfp7RvjN4T2VtgJfQZ9LzGrHRIelw-awrcxYYVo/pub?w=249&h=101)

# MetaHeuristics

A repository with an explanation and a comparission of different metaheuristics

## Base Problem - Travelling salesman problem
We must travel from the origin city, go through all the cities and come back to the origin city. For this we should use the shortest path that we can found and we can't pass two times for the same city.

Example representation of the problem

![Search Space](https://docs.google.com/drawings/d/1CqxjTGnPX06tfltqM3eUIMCvVwr4fED_Np9bWlAhzjY/pub?w=469&h=302)

### Representation of Solutions

In the representation of the solution, we assume that we start in city 0 and that we have to return to city 0. So we represent the solution as an ordered array of cities, in which the first element is the city that we are going to visit if we start at city 0, the second element is the city that we should visit if we are at city 1,..

| Order  | 1   | 2   | 3   | 4   | 5   | 6   | 7   | n   |
| -----  | --- | --- | --- | --- | --- | --- | --- | --- |
| City   | 2   | 5   | 7   | 6   | 3   | 1   | 4   | n   |

### Cost of Solution

The cost of the solution is the sum of the distances of traveling from one city to another following the solution path. We must remember that we start at city 0 and we have to return to city 0.

    C = D(0,1) + D(1,2)+ ··· + D(j,k) + ··· + D(k,n-1) + D(n-1,n) + D(n,0)


## Implemented MetaHeuristics



### Tabu Search

[WIP - description]
<br>
[Tabu search wikipedia](https://en.wikipedia.org/wiki/Tabu_search)
### Simulated Annealing

[WIP - description]
[Simulated Annealing wikipedia](https://en.wikipedia.org/wiki/Simulated_annealing)
### Evolutive Search

[WIP - description]
[Evolutive search](https://en.wikipedia.org/wiki/Evolutionary_computation)

## Operators used for all heuristics

- **Permute:** exchanges the position of two cities in the solution array

    Example:

    | Order  | 1   | 2   | 3   | 4   | 5   | 6   | 7   | n   |
    | -----  | --- | --- | --- | --- | --- | --- | --- | --- |
    | City   | 2   | 5   | 7   | 6   | 3   | 1   | 4   | n   |

    Permute(2,6)

    | Order  | 1   | 2   | 3   | 4   | 5   | 6   | 7   | n   |
    | -----  | --- | --- | --- | --- | --- | --- | --- | --- |
    | City   | 2   | **1**   | 7   | 6   | 3   | **5**   | 4   | n   |

## Stop condition

The algorithm stops once we can't generate a better solution applying an operator to our current solution or when it reaches a certain number [1000] of iterations

```