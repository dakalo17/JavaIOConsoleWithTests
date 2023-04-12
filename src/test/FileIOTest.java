package test;

import com.casiartifact.FileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileIOTest {
    private FileIO fileIOObj =null;


    @TempDir
    private File mockDir;
    private File mockIOFile;

    @BeforeEach
    public void init() throws IOException {
        fileIOObj =new FileIO();
        mockIOFile =File.createTempFile("testIO","txt",mockDir);
    }

    @ParameterizedTest
    @MethodSource("arrayArguments")
    public void readIntegers(List<Integer> expectedArrayList){


        List<Integer> initialArrayList = new ArrayList<>(expectedArrayList.size());

        initialArrayList.addAll(expectedArrayList);

        fileIOObj.writeToFile(initialArrayList,mockIOFile);

        //initialArrayList is cleared before reading any data
        fileIOObj.readIntegers(initialArrayList,mockIOFile);


        assertArrayEquals(expectedArrayList.toArray(),initialArrayList.toArray());

    }

    @ParameterizedTest
    @MethodSource("arrayArguments")
    public void writeToFile(List<Integer> initialArrayList){

            int status = fileIOObj.writeToFile(initialArrayList,mockIOFile);

            assertEquals(FileIO.SUCCESS,status);

    }


    @ParameterizedTest
    @MethodSource("arrayArguments")
    public void filterArray(List<Integer> initialArrayList,List<Integer> expectedList){

        fileIOObj.filterArray(initialArrayList);
        assertArrayEquals(expectedList.toArray(),initialArrayList.toArray());
    }


    private static List<Arguments> arrayArguments(){


        return Arrays.asList(
                Arguments.arguments(Arrays.asList(1, 2, 3, 5, 5, 5, 5, 4, 1, 3), Arrays.asList(1,null,2,3,null,4,5,null,null,null)),
                Arguments.arguments(Arrays.asList(1, 2, 3, 5, 3, 4, 5, 2, 5, 8), Arrays.asList(1,2,null,3,null,4,5,null,null,8)),
                Arguments.arguments(Arrays.asList(1, 1, 3, 5, 2, 4, 2, 4, 3, 7), Arrays.asList(1,null,2,null,3,null,4,null,5,7)),
                Arguments.arguments(Arrays.asList(1, 3, 3, 5,1,3,43,56,7,45,346,7, 6, 4, 2, 4, 2, 2,43,6,23,52,335,6,2,3), Arrays.asList(1,null,2,null,null,null,3,null,null,null,4,null,5,6,null,null,7,null,23,43,null,45,52,56,335,346)),
                Arguments.arguments(Arrays.asList(1, 3, 3, 3, 2, 1, 1, 4, 13, 2, 2, 2, 3, 4, 5, 1, 2, 41, 2), Arrays.asList(1,null,null,null,2,null,null,null,null,null,3,null,null,null,4,null,5,13,41)),
                Arguments.arguments(Arrays.asList(1, 3, 3, 3, 2, 1, 1, 4, 13, 2,45,3,2,53,32,32,13,3,1), Arrays.asList(1,null, null,null,2,null,null, 3, null, null,null,null,4,13,null,32,null,45,53)),
                Arguments.arguments(Arrays.asList(1, 3, 3, 3, 2, 1, 1, 4, 13, 2, 31, 4, 31), Arrays.asList(1,null,null,2,null,3,null,null,4,null,13,31,null)),
                Arguments.arguments(Arrays.asList(1, 1, 31, 2, 1, 4, 13, 2, 31, 4, 1), Arrays.asList(1,null,null,null,2,null,4,null,13,31,null)),
                Arguments.arguments(Arrays.asList(1, -1, 31, 2, 1, 4, 13, 2, 31, 4, 1), Arrays.asList(-1,1,null,null,2,null,4,null,13,31,null)),
                Arguments.arguments(List.of(1), List.of(1)),
                Arguments.arguments(List.of(0), List.of(0))
        );
    }



}
