package satellite;

import java.io.*;
import java.util.*;

public class Satellite {

    static int[][] oldImage, newImage;
    static int noOfRows, noOfCols;
    static double pa, pb, pc;
    static int sN, sN2, sBoard[][];

    public static void main(String[] args) {
        try {
            dispatch(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void dispatch(String[] a) throws Exception {
        if (a.length > 0 && a[0].matches("poly|2")) runPoly(new Scanner(System.in));
        else if (a.length > 0 && a[0].matches("sudoku|3"))
            solveSudoku(a.length > 1 ? a[1] : "sudoku.txt");
        else solve(a.length > 0 ? a[0] : "input.txt");
    }

    static void solve(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        init(reader);
        reader.close();
        printResult();
    }

    static void init(BufferedReader reader) throws IOException {
        String[] dims = reader.readLine().trim().split("\\s+");
        noOfRows = Integer.parseInt(dims[0]);
        noOfCols = Integer.parseInt(dims.length > 1 ? dims[1] : reader.readLine().trim());
        oldImage = readImage(reader);
        newImage = readImage(reader);
    }

    static int[][] readImage(BufferedReader reader) throws IOException {
        int[][] img = new int[noOfRows][noOfCols];
        for (int r = 0; r < noOfRows; r++)
            fillRow(img[r], reader.readLine().trim().split("\\s+"));
        return img;
    }

    static void fillRow(int[] row, String[] parts) {
        for (int c = 0; c < noOfCols; c++)
            row[c] = Integer.parseInt(parts[c]);
    }

    static int findBound(int start, int step, boolean isRow) {
        int idx = start, limit = isRow ? noOfRows : noOfCols;
        while (idx >= 0 && idx < limit && isEqual(idx, isRow))
            idx += step;
        return idx;
    }

    static boolean isEqual(int idx, boolean isRow) {
        int len = isRow ? noOfCols : noOfRows;
        for (int i = 0; i < len; i++)
            if (diff(idx, i, isRow)) return false;
        return true;
    }

    static boolean diff(int idx, int i, boolean isRow) {
        return isRow ? oldImage[idx][i] != newImage[idx][i]
                     : oldImage[i][idx] != newImage[i][idx];
    }

    static void printResult() {
        int x1 = findBound(0, 1, true), x2 = findBound(noOfRows - 1, -1, true);
        int y1 = findBound(0, 1, false), y2 = findBound(noOfCols - 1, -1, false);
        if (x1 > x2 || y1 > y2) System.out.println("The two images are the same");
        else System.out.println((x1 + 1) + " " + (y1 + 1) + " " + (x2 + 1) + " " + (y2 + 1));
    }

    static void runPoly(Scanner sc) {
        pa = pb = pc = 0.0;
        while (sc.hasNextLine() && handlePolyCmd(sc.nextLine().trim()));
    }

    static boolean handlePolyCmd(String line) {
        if (line.isEmpty() || line.equalsIgnoreCase("EXIT")) return false;
        if (line.startsWith("CHANGE ")) changeCoeff(line.substring(7));
        else if (line.startsWith("INCREASE ")) incCoeff(line.substring(9));
        else execCoeffCmd(line);
        return true;
    }

    static void execCoeffCmd(String line) {
        if (line.startsWith("REMOVE ")) setCoeff(line.substring(7).trim(), 0, false);
        else execPolyQuery(line);
    }

    static void changeCoeff(String arg) {
        String[] p = arg.split("\\s+TO\\s+");
        setCoeff(p[0].trim(), Double.parseDouble(p[1].trim()), false);
    }

    static void incCoeff(String arg) {
        String[] p = arg.split("\\s+BY\\s+");
        setCoeff(p[0].trim(), Double.parseDouble(p[1].trim()), true);
    }

    static void setCoeff(String var, double val, boolean add) {
        if (var.equalsIgnoreCase("a")) pa = add ? pa + val : val;
        else if (var.equalsIgnoreCase("b")) pb = add ? pb + val : val;
        else if (var.equalsIgnoreCase("c")) pc = add ? pc + val : val;
    }

    static void execPolyQuery(String cmd) {
        if (cmd.equalsIgnoreCase("PRINT")) printPoly();
        else if (cmd.equalsIgnoreCase("DISCRIMINANT")) printDisc();
        else if (cmd.equalsIgnoreCase("NUMBER_OF_DIFFERENT_ROOTS")) printRootCount();
        else if (cmd.equalsIgnoreCase("SOLVE")) solvePoly();
    }

    static void printPoly() {
        System.out.printf("%.2f*x^2 + %.2f*x + %.2f = 0\n", pa, pb, pc);
    }

    static double getDisc() {
        return pb * pb - 4 * pa * pc;
    }

    static void printDisc() {
        System.out.printf("Discriminant: %.4f\n", getDisc());
    }

    static void printRootCount() {
        if (pa == 0 && pb == 0) System.out.println(pc == 0 ? "Infinite roots" : "0");
        else if (pa == 0) System.out.println("1");
        else if (getDisc() > 0) System.out.println("2");
        else System.out.println(getDisc() == 0 ? "1" : "0");
    }

    static void solvePoly() {
        if (pa == 0 && pb == 0) System.out.println(pc == 0 ? "All real numbers" : "No solution");
        else if (pa == 0) System.out.printf("Root: %.4f\n", -pc / pb);
        else solveQuad(getDisc());
    }

    static void solveQuad(double d) {
        if (d < 0) System.out.println("No real roots");
        else if (d == 0) System.out.printf("Root: %.4f\n", -pb / (2 * pa));
        else System.out.printf("Roots: %.4f and %.4f\n", 
                (-pb + Math.sqrt(d)) / (2 * pa), (-pb - Math.sqrt(d)) / (2 * pa));
    }

    static void solveSudoku(String filename) throws IOException {
        loadSudoku(new Scanner(new File(filename)));
        boolean valid = checkSudoku();
        System.out.println(valid ? "Valid Sudoku Solution" : "Invalid Sudoku Solution");
    }

    static void loadSudoku(Scanner sc) {
        sN = sc.nextInt();
        sBoard = new int[sN2 = sN * sN][sN2];
        for (int r = 0; r < sN2; r++)
            for (int c = 0; c < sN2; c++) sBoard[r][c] = sc.nextInt();
    }

    static boolean checkSudoku() {
        for (int i = 0; i < sN2; i++)
            if (!validLine(i, true) || !validLine(i, false)) return false;
        return checkAllBoxes();
    }

    static boolean checkAllBoxes() {
        for (int r = 0; r < sN2; r += sN)
            for (int c = 0; c < sN2; c += sN)
                if (!validBox(r, c)) return false;
        return true;
    }

    static boolean validLine(int idx, boolean isRow) {
        boolean[] seen = new boolean[sN2 + 1];
        for (int i = 0; i < sN2; i++)
            if (!trackVal(seen, isRow ? sBoard[idx][i] : sBoard[i][idx])) return false;
        return true;
    }

    static boolean validBox(int br, int bc) {
        boolean[] seen = new boolean[sN2 + 1];
        for (int r = 0; r < sN; r++)
            for (int c = 0; c < sN; c++)
                if (!trackVal(seen, sBoard[br + r][bc + c])) return false;
        return true;
    }

    static boolean trackVal(boolean[] seen, int v) {
        if (v < 1 || v > sN2 || seen[v]) return false;
        return seen[v] = true;
    }
}
