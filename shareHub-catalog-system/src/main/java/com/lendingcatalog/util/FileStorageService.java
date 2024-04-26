package com.lendingcatalog.util;

import com.lendingcatalog.util.exception.FileStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStorageService {

    // Requirement: File I/O
    public static void writeContentsToFile(String contents, String filename, boolean appendFile) throws FileStorageException {

        try(PrintWriter writer = new PrintWriter(new FileOutputStream(filename, appendFile))){
            writer.println(contents);
        }catch (FileNotFoundException e){
            System.out.println("*** Unable to find file: " + filename + "\n" + e.getMessage());
        }

    }

    public static List<String> readContentsOfFile(String filename) throws FileStorageException {
        List<String> contentsOfFile = new ArrayList<>();
        File readFile = new File(filename);

        try(Scanner inputFile = new Scanner(readFile)){
            while(inputFile.hasNextLine()){
                String textOfLine = inputFile.nextLine();
                contentsOfFile.add(textOfLine);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("*** Unable to find file: " + filename + "\n" + e.getMessage());
        }
        return contentsOfFile;

    }
}
