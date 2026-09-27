# Problem Set 01 — Unified Java Solution & Verification Report

**GitHub Repository URL:** [https://github.com/bilguun082/satellite](https://github.com/bilguun082/satellite)

---

## 1. Project Summary & Modularity

This project implements all three problems from **Problem Set 01** in **one single Java class** ([`satellite.Satellite`](src/main/java/satellite/Satellite.java)):

1. **Task 1: Satellite Image Change Detection** (`muhold.be` / `muhold.ki`)
2. **Task 2: Second-Degree Polynomial Solver** (`CHANGE`, `REMOVE`, `PRINT`, `SOLVE`, etc.)
3. **Task 3: Generalized Sudoku Checker** ($N^2 \times N^2$ grid with $N \times N$ sub-boxes for $N \le 6$)

### Modularity & Architectural Constraints
- **Single Class:** All functionality lives in `public class Satellite`.
- **Static Methods Only:** No objects instantiated; strictly static functions.
- **Strict Code Length Constraint:** Every method is **$\le$ 7 lines of code** (all 30 methods range between 3 and 7 lines).
- **Task 1 Unified Methods:**
  - `readImage(BufferedReader reader)`: Single method reading both old and new images.
  - `findBound(int start, int step, boolean isRow)`: Single parametrized method determining $x_1, y_1, x_2, y_2$.

---

## 2. Modularity Audit Table (Sample of Key Methods)

| Method Signature | Lines | Task | Purpose / Constraint Satisfied |
|---|:---:|:---:|---|
| `main(String[] args)` | **6** | All | Entry point and exception handling |
| `dispatch(String[] a)` | **6** | All | Dispatches to Satellite, Polynomial, or Sudoku |
| `findBound(start, step, isRow)` | **6** | Task 1 | **Unified coordinate method** for $x_1, y_1, x_2, y_2$ |
| `readImage(BufferedReader reader)` | **6** | Task 1 | **Unified image reader** for both photos |
| `handlePolyCmd(String line)` | **7** | Task 2 | Dispatches polynomial CLI commands |
| `setCoeff(String var, double val, add)` | **5** | Task 2 | Sets or increments coefficients $a, b, c$ |
| `solveQuad(double d)` | **6** | Task 2 | Solves quadratic equation via quadratic formula |
| `solveSudoku(String filename)` | **5** | Task 3 | Reads and verifies Sudoku board |
| `validBox(int br, int bc)` | **7** | Task 3 | Validates $N \times N$ sub-box uniqueness |
| `trackVal(boolean[] seen, int v)` | **4** | Task 3 | Helper checking values in range $[1, N^2]$ |

*Complete method count:* **30 methods, all $\le 7$ lines**.

---

## 3. How to Run Each Task

### Compile
```bash
javac -d target/classes src/main/java/satellite/Satellite.java
```

### Run Task 1: Satellite Image Change Detection
```bash
# Default input.txt
java -cp target/classes satellite.Satellite

# Contest problem dataset (muhold.be -> outputs 3 2 7 8)
java -cp target/classes satellite.Satellite tests/muhold.be
```

### Run Task 2: Second-Degree Polynomial Solver
```bash
java -cp target/classes satellite.Satellite poly
```
*Supported interactive commands:*
- `CHANGE a TO 2.3`
- `REMOVE b`
- `INCREASE c BY 2.1`
- `PRINT` $\rightarrow$ outputs `2.30*x^2 + 0.00*x + 2.10 = 0`
- `DISCRIMINANT` $\rightarrow$ outputs `Discriminant: -19.3200`
- `NUMBER_OF_DIFFERENT_ROOTS` $\rightarrow$ outputs `0`, `1`, `2`, or `Infinite roots`
- `SOLVE` $\rightarrow$ calculates real roots
- `EXIT` $\rightarrow$ terminates

### Run Task 3: Sudoku Checker
```bash
# Valid 9x9 board (N=3)
java -cp target/classes satellite.Satellite sudoku tests/sudoku_valid.txt
# Output: Valid Sudoku Solution

# Invalid 9x9 board
java -cp target/classes satellite.Satellite sudoku tests/sudoku_invalid.txt
# Output: Invalid Sudoku Solution
```

---

## 4. Test Suite Results

```
=========================================================================
             PROBLEM SET 01 — FULL THREE-TASK TEST SUITE                 
=========================================================================
Task 1: Satellite  input.txt                 Expected: 1 2 1 2                        [PASS]
Task 1: Satellite  tests/muhold.be           Expected: 3 2 7 8                        [PASS]
Task 1: Satellite  tests/test1_single_pixel.txt Expected: 2 2 2 2                        [PASS]
Task 1: Satellite  tests/test2_identical.txt Expected: The two images are the same    [PASS]
Task 1: Satellite  tests/test3_entire_image.txt Expected: 1 1 3 3                        [PASS]
Task 1: Satellite  tests/test4_sub_rectangle.txt Expected: 2 3 4 5                        [PASS]
Task 1: Satellite  tests/test5_corners.txt   Expected: 1 1 4 5                        [PASS]
Task 2: Polynomial Sample Script (Doc)       Expected: 2.30*x^2 + 0.00*x + 2.10 = ... [PASS]
Task 2: Polynomial Real Roots Test           Expected: Roots: 3.0000 and 2.0000       [PASS]
Task 3: Sudoku     Valid 9x9 Board           Expected: Valid Sudoku Solution          [PASS]
Task 3: Sudoku     Invalid 9x9 Board         Expected: Invalid Sudoku Solution        [PASS]
=========================================================================
```

---

## 5. Visual Proof: Test Execution and Output Screenshots

### Screenshot 1: Full Three-Task Terminal Test Suite
![Terminal Test Execution](screenshots/test_execution_terminal.png)

### Screenshot 2: Contest Example (muhold.be) & 9x9 Sudoku Validation
![Visual Matrix Comparison](screenshots/test_matrix_comparison.png)

### Screenshot 3: Code Modularity & Line Length Audit
![Code Modularity Audit](screenshots/code_modularity_check.png)
