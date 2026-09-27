# Satellite Image Change Detection

A modular Java application for detecting and localizing changes between satellite images.

## GitHub Repository
**URL:** [https://github.com/bilguun082/satellite](https://github.com/bilguun082/satellite)

## Features & Architectural Constraints
- **Single Class:** Implemented in `satellite.Satellite`.
- **Static Methods Only:** All methods are static.
- **Strict Modularity:** Every method is **<= 6–7 lines of code**.
- **Unified Coordinate Method:** Single method `findBound(int start, int step, boolean isRow)` determines $x_1, y_1, x_2, y_2$.
- **Unified Image Reader:** Single method `readImage(BufferedReader reader)` reads both the old and new images.

## Quick Start
```bash
# Compile
javac -d target/classes src/main/java/satellite/Satellite.java

# Run on default input.txt
java -cp target/classes satellite.Satellite

# Run on any test case
java -cp target/classes satellite.Satellite tests/test4_sub_rectangle.txt
```

For detailed test reports and screenshots, see [DOCUMENTATION.md](DOCUMENTATION.md).
