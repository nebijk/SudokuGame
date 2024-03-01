package Sudoku.Game.Solver.model;

import java.io.*;

/**
 * Manages file operations related to the Sudoku game.
 */
public class FileHandler {

    /**
     * Serializes the SudokuGrid to  file.
     *
     * @param file The file to write the serialized data to.
     * @param grid The SudokuGrid object to serialize.
     * @throws IOException If there's an issue during file writing.
     */
    public static void serializeToFile(File file, SudokuGrid grid) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(grid);
        }
    }

    /**
     * Deserializes a SudokuGrid from a  file given.
     *
     * @param file The file to read the serialized data from.
     * @return The deserialized SudokuGrid object.
     * @throws IOException If there's an issue during file reading.
     * @throws ClassNotFoundException If the read class from the file is not found.
     */
    public static SudokuGrid deSerializeFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (SudokuGrid) in.readObject();
        }
    }
}