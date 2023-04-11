package test;

import com.casiartifact.FileIO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FileIOTest {
    private FileIO obj =null;

    private static final String DEFAULT_PATH = ".%ssrc%scom%scasiartifact%s".
            formatted(File.separator,File.separator,File.separator,File.separator);


    @BeforeEach
    public void init(){
        obj =new FileIO();
    }

    @Test
    public void readIntegers(){
        List<Integer> arrayList = new ArrayList<>();
        File testFile = new File(DEFAULT_PATH.concat("hj"));

        obj.readIntegers(arrayList,testFile);


        Integer[] expectedArray = {1 ,2, 3, 4, 5, 5, 1, 2, 3};

        assertArrayEquals(expectedArray,arrayList.toArray());

    }


    @ParameterizedTest
    @MethodSource("arrayArgument")
    public void writeToFile(List<Integer> initialArrayList,List<Integer> expectedList){


        //creating a mock file
        File mockFile = mock(File.class);

        when(mockFile.getName()).thenReturn("testOutput.txt");
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.canWrite()).thenReturn(true);



        //File testFile = new File(DEFAULT_PATH.concat("testOutput.txt"));


        obj.writeToFile(initialArrayList,mockFile);

        assertArrayEquals(initialArrayList.toArray(),expectedList.toArray());


    }
    private List<Arguments> arrayArguments(){
        return List.of(
                Arguments.of( new Integer[]{1, 2, 3, 5, 5, 5, 5, 4, 1, 3}, new Integer[]{1,null,2,3,null,4,5,null,null,null}),
                Arguments.of( new Integer[]{1, 2, 3, 5, 3, 4, 5, 2, 5, 8},new Integer[]{1,2,null,3,null,4,5,null,null,8}),
                Arguments.of(new Integer[]{1, 1, 3, 5, 2, 4, 2, 4, 3, 7},new Integer[]{1,null,2,null,3,null,4,null,5,7}),
                Arguments.of( new Integer[]{1, 3, 3, 5,1,3,43,56,7,45,346,7, 6, 4, 2, 4, 2, 2,43,6,23,52,335,6,2,3},new Integer[]{1,null,2,null,null,null,3,null,null,null,4,null,5,6,null,null,7,null,23,43,null,45,52,56,335,346}),
                Arguments.of(new Integer[]{1, 3, 3, 3, 2, 1, 1, 4, 13, 2, 2, 2, 3, 4, 5, 1, 2, 41, 2},new Integer[]{1,null,null,null,2,null,null,null,null,null,3,null,null,null,4,null,5,13,41}),
                Arguments.of(new Integer[]{1, 3, 3, 3, 2, 1, 1, 4, 13, 2,45,3,2,53,32,32,13,3,1},new Integer[]{1,null, null,null,2,null,null, 3, null, null,null,null,4,13,null,32,null,45,53}),
                Arguments.of(new Integer[]{1, 3, 3, 3, 2, 1, 1, 4, 13, 2, 31, 4, 31 },new Integer[]{1,null,null,2,null,3,null,null,4,null,13,31,null}),
                Arguments.of( new Integer[]{1, 1, 31, 2, 1, 4, 13, 2, 31, 4, 1},new Integer[]{1,null,null,null,2,null,4,null,13,31,null})
        );
    }


    @Test
    public void filterArray(){

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(3);
        arrayList.add(2);
        arrayList.add(4);
        arrayList.add(1);


        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(null);
        expectedList.add(null);
        expectedList.add(2);
        expectedList.add(null);
        expectedList.add(3);
        expectedList.add(null);
        expectedList.add(4);



        obj.filterArray(arrayList);

        assertArrayEquals(expectedList.toArray(),arrayList.toArray());
    }


}
