package com.casiartifact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FileIO {

    public final static int SUCCESS = 0;
    public final static int FILE_DOES_NOT_EXIST = 1;
    public final static int FILE_CAN_NOT_OPEN = 2;
    public final static int INVALID_NUMBER_FORMAT = 3;
    public final static int FILE_CAN_NOT_WRITE = 4;



    public int readIntegers(List<Integer> arrayList, File file){
        if(!file.exists())
            return FILE_DOES_NOT_EXIST;

        if(!file.canRead())
            return FILE_CAN_NOT_OPEN;
        try (Scanner reader = new Scanner(file)) {
            //this is where numbers are read and the converted to integer

            arrayList.clear();
            while (reader.hasNextLine()) {
                int number = Integer.parseInt(reader.nextLine());

                arrayList.add(number);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return FILE_DOES_NOT_EXIST;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return INVALID_NUMBER_FORMAT;
        }
        return SUCCESS;
    }

    public int writeToFile(List<Integer> arrayList, File file){

        if(!file.exists()) {
            if(!createFile(file))
                return FILE_DOES_NOT_EXIST;

        }
        if(!file.canWrite())
            return FILE_CAN_NOT_WRITE;

        PrintWriter printWriter = null;
        try  {
            printWriter = new PrintWriter(file);
            //this is where numbers are written to the file 'output.txt'

            for (Integer integer : arrayList) {
                //write to file if not file non-null numbers(int)
                if (integer != null)
                    printWriter.println(integer);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return FILE_DOES_NOT_EXIST;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
            finally {
            if(printWriter != null){

                printWriter.close();
                printWriter.flush();
            }
        }
        return SUCCESS;
    }

    public void filterArray(List<Integer> arrayList){

        //sort the numbers
        arrayList.sort((t1, t2) -> t1.compareTo(t2));

        List<Integer> tempList =new ArrayList<>(arrayList.size());

        Collections.copy(arrayList,tempList);

        //remove duplicates

        for (int i = 0; i < arrayList.size() && i >= 0 ; i++) {
            for (int j = i+1; j < arrayList.size(); j++){
                /*
                  * replace duplicates with null(a flag to indicate duplicated value)
                  * null also helps avoid duplicating the entire array
                 */
                if(Objects.equals(arrayList.get(i), arrayList.get(j)))
                    arrayList.set(j,null);
                else
                {
                    //improves the efficiency of the algorithm
                    i = j-1;
                    break;
                }

            }
        }
    }

    //create a new file
    private boolean createFile(File file){
        try {
            //create a new file and return true if created else false
           return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
