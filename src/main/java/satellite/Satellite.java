package satellite;

import java.io.*;

public class Satellite {

    static int[][] oldImage;
    static int[][] newImage;
    static int noOfRows, noOfCols;

    public static void main(String[] args) {
        try {
            solve(args.length > 0 ? args[0] : "input.txt");
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    static void solve(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        init(reader);
        reader.close();
        printResult();
    }

    static void init(BufferedReader reader) throws IOException {
        noOfRows = Integer.parseInt(reader.readLine().trim());
        noOfCols = Integer.parseInt(reader.readLine().trim());
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
}
