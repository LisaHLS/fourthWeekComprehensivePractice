package com.tw;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

public class InputReaderTest {

    InputReader inputReader;

    @Before
    public final void before(){
        inputReader = new InputReader();
    }
    private void setInputStream(String input) throws NoSuchFieldException, IllegalAccessException {
        Field scannerField = inputReader.getClass().getDeclaredField("scanner");
        scannerField.setAccessible(true);
        Scanner scannerWithMockedStream;
        scannerWithMockedStream = new Scanner(new ByteArrayInputStream(input.getBytes()));
        scannerField.set(inputReader, scannerWithMockedStream);
    }

    @Test
    public void should_return_input_when_stu_info_format_right() throws Exception {
        String inputString = "张三，007，数学：75，语文：95，英语：80，编程：80";
        setInputStream(inputString);
        assertEquals(inputString, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_null_when_stu_info_no_course() throws Exception {
        String inputString = "张三, 121";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_null_when_stu_info_has_english_comma() throws Exception {
        String inputString = "张三，007,数学：75，语文：95，英语：80，编程：80";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_null_when_stu_info_has_english_colon() throws Exception {
        String inputString = "张三，007，数学：75，语文:95，英语：80，编程：80";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_null_when_stu_info_has_space() throws Exception {
        String inputString = "张三，007， 数学：75，语文：95，英语：80，编程：80";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_null_when_stu_info_id_not_digit() throws Exception {
        String inputString = "张三，hhhh，数学：75，语文：95，英语：80，编程：80";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_null_when_stu_info_score_not_digit() throws Exception {
        String inputString = "张三，009，数学：sd，语文：95，英语：80，编程：80";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_null_when_stu_info_score_not_in_zero_to_hundred() throws Exception {
        String inputString = "张三，009，数学：120，语文：95，英语：80，编程：80";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(1));
    }

    @Test
    public void should_return_input_when_stu_ids_str_format_right() throws Exception {
        String inputString = "007，009";
        setInputStream(inputString);
        assertEquals(inputString, inputReader.readChooseNum(2));
    }

    @Test
    public void should_return_null_when_stu_ids_str_has_english_comma() throws Exception {
        String inputString = "007,009";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(2));
    }

    @Test
    public void should_return_null_when_stu_ids_str_has_space() throws Exception {
        String inputString = "007， 009";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(2));
    }

    @Test
    public void should_return_null_when_stu_ids_str_has_char() throws Exception {
        String inputString = "007，0hhh9";
        setInputStream(inputString);
        assertEquals(null, inputReader.readChooseNum(2));
    }

}
