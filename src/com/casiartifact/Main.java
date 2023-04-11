package com.casiartifact;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static com.casiartifact.FileIO.*;

public class Main {

    private static final String DEFAULT_PATH = ".%ssrc%scom%scasiartifact%s".
            formatted(File.separator,File.separator,File.separator,File.separator);



    public static void main(String[] args) {
        FileIO fileIO = new FileIO();

        ArrayList<Integer> arr = new ArrayList<>();

        File inFile= new File(DEFAULT_PATH.concat("input.txt"));

        final int result = fileIO.readIntegers(arr, inFile);

        switch (result){
            case SUCCESS -> {
                //System.out.println(arr);
                System.out.println("File successfully read!");


                long st = System.nanoTime();


                //filter and sort the array
                fileIO.filterArray(arr);

                long end = System.nanoTime();

                long elapsedTime = end - st;
                double elapsedMilliseconds = (double) elapsedTime / 1000000.0;
                System.out.println("Elapsed time: " + elapsedMilliseconds + " ms");
                //now we write to a file
                File outFile = new File(DEFAULT_PATH.concat("output.txt"));

                switch (fileIO.writeToFile(arr,outFile)) {
                    case SUCCESS -> {
                        System.out.println(arr);

                        System.out.println("Data successfully written to ".concat(outFile.getName()));
                    }
                    case FILE_CAN_NOT_WRITE -> {
                        System.err.println("Data can not be written to ".concat(outFile.getName()));
                    }
                    case FILE_DOES_NOT_EXIST ->
                            System.err.println("File does not exist");
                    default -> {
                        System.err.println("Unexpected error occurred!");
                    }
                }


            }

            case FILE_DOES_NOT_EXIST->
                System.err.println("File could not be read, because it does not exist or can not be found!");

            case FILE_CAN_NOT_OPEN->
                System.err.println("File could not read, because it can not be opened!");

            case INVALID_NUMBER_FORMAT->
                System.err.println("File could not be read successfully, because the content(s) are not of type Integer.");

            default ->
                System.err.println("Unexpected error occurred!");

        }
    }

}