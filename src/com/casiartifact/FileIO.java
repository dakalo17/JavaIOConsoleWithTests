package com.casiartifact;

import java.io.*;
import java.util.*;

/***
 * @author Gumani Luvhengo
 */
public class FileIO {

    /**
     Indication of success of an operation.
     */
    public final static int SUCCESS = 0;
    /**
     Indication of fail of an operation,for when a file is not found or does not exist.
     */
    public final static int FILE_DOES_NOT_EXIST = 1;
    /**
     Indication of fail of an operation,for when a file can not be opened.
     */
    public final static int FILE_CAN_NOT_OPEN = 2;
    /**
     Indication of fail of an operation,for invalid number format.
     */
    public final static int INVALID_NUMBER_FORMAT = 3;
    /**
     Indication of fail of an operation,for when a file can not be written to.
     */
    public final static int FILE_CAN_NOT_WRITE = 4;

    /**
     Variable to handle writing to a file.
     */
    private PrintWriter printWriter = null;

    /**
     Variable to handle reading from a file.
     */
    private Scanner reader = null;

    /**
     * @param arrayList a list of integers,that will capture that retrieved data.
     * @param file a file that will be read.
     * @return integer to indicate whether the operation is successful or not.
     * */
    public int readIntegers(List<Integer> arrayList, File file){
        if(!file.exists())
            return FILE_DOES_NOT_EXIST;

        if(!file.canRead())
            return FILE_CAN_NOT_OPEN;
        try  {
            reader = new Scanner(file);
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
        }finally {
            if(reader !=null)
                reader.close();
        }
        return SUCCESS;
    }

    /**
     * @param arrayList a list of integers,that will has the data that needs to be saved.
     * @param file a file that will be written.
     * @return integer to indicate whether the operation is successful or not.
     * */
    public int writeToFile(List<Integer> arrayList, File file){

        if(!file.exists()) {
            if(!createFile(file))
                return FILE_DOES_NOT_EXIST;

        }
        if(!file.canWrite())
            return FILE_CAN_NOT_WRITE;


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

    /**
     * @param arrayList a list of integers,that will be sorted in ascending order and filtered.
     * */
    public void filterArray(List<Integer> arrayList){

        //sort the numbers
        arrayList.sort((t1, t2) -> t1.compareTo(t2));

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
    /**
     * @param file a file that will be created.
     * @return boolean to indicate whether the operation is successful or not.
     * */
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
