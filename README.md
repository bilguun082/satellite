# Problem Set 01 — Unified Java Application

A single-class modular Java project containing implementations for all three tasks from Problem Set 01:
1. **Satellite Image Change Detection** (muhold.be / muhold.ki)
2. **Second-Degree Polynomial Solver** (interactive CLI)
3. **Generalized Sudoku Checker** ($N^2 \times N^2$ grid, $N \le 6$)

## GitHub Repository
**URL:** [https://github.com/bilguun082/satellite](https://github.com/bilguun082/satellite)

## Architectural Constraints
- **Single Class:** Encapsulated entirely in `satellite.Satellite`.
- **Static Methods Only:** 30 methods, all `static`.
- **Method Length Constraint:** Every method is **$\le$ 7 lines of code** (limit: 7–8 lines).
- **Task 1 Unified Methods:**
  - `readImage(BufferedReader reader)`: Single method reading both photos.
  - `findBound(int start, int step, boolean isRow)`: Single parametrized method for $x_1, y_1, x_2, y_2$.

## Quick Commands
```bash
# Compile
javac -d target/classes src/main/java/satellite/Satellite.java

# Task 1: Satellite on contest example
java -cp target/classes satellite.Satellite tests/muhold.be

# Task 2: Polynomial Solver
java -cp target/classes satellite.Satellite poly

# Task 3: Sudoku Checker
java -cp target/classes satellite.Satellite sudoku tests/sudoku_valid.txt

# Run Full Test Suite & Generate Screenshots
javac -d target/classes -cp target/classes src/test/java/TestRunnerAndVisualizer.java
java -Djava.awt.headless=true -cp target/classes test.TestRunnerAndVisualizer
```

See [DOCUMENTATION.md](DOCUMENTATION.md) for full details and visual proof.
