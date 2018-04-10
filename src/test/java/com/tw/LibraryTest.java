package com.tw;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.LinkedList;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class LibraryTest {

    public static final String TEST_STUDENT_INFO = "张三，007，数学：75，语文：95，英语：80，编程：80";
    public static final String TEST_STUDENT_INFO2 = "李四，009，数学：85，语文：80，英语：70，编程：90";

    private Library library;
    private InputReader inputReader;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private String systemOut() { return outContent.toString(); }

    @Before
    public void setup() {
        inputReader = mock(InputReader.class);
        library = new Library(inputReader);;
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void should_print_main_interface_when_entry() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("3");
        library.entryMain();
        assertThat(systemOut()).isEqualTo("1. 添加学生\n2. 生成成绩单\n3. 退出\n请输入你的选择（1～3）：\n");
    }

    @Test
    public void should_return_false_when_choose_num_three() throws Exception {
        library = mock(Library.class);
        when(inputReader.readChooseNum(4)).thenReturn("3");
        assertFalse(library.entryMain());
    }

    @Test
    public void should_return_false_when_choose_num_over_three() throws Exception {
        library = mock(Library.class);
        when(inputReader.readChooseNum(4)).thenReturn("9");
        assertFalse(library.entryMain());
    }

    @Test
    public void should_return_false_when_choose_num_less_one() throws Exception {
        library = mock(Library.class);
        when(inputReader.readChooseNum(4)).thenReturn("-1");
        assertFalse(library.entryMain());
    }

    @Test
    public void should_return_false_when_choose_num_is_not_digit() throws Exception {
        library = mock(Library.class);
        when(inputReader.readChooseNum(4)).thenReturn("ss");
        assertFalse(library.entryMain());
    }

    @Test
    public void should_give_right_msg_when_choose_two_to_add_student() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn(TEST_STUDENT_INFO);
        library.entryMain();
        assertThat(systemOut().contains("请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：\n")).isTrue();
        assertThat(systemOut().contains("学生张三的成绩被添加\n")).isTrue();
        assertThat(systemOut().contains("1. 添加学生\n2. 生成成绩单\n3. 退出\n请输入你的选择（1～3）：\n")).isTrue();
    }

    @Test
    public void should_give_prompt_msg_when_student_info_format_wrong() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn("张三，2015");
        library.entryMain();
        assertThat(systemOut().contains("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：\n")).isTrue();
    }

    @Test
    public void should_print_report_card_When_no_stu_id() throws Exception {

        when(inputReader.readChooseNum(4)).thenReturn("2");
        when(inputReader.readChooseNum(2)).thenReturn("000089");
        library.entryMain();
        assertThat(systemOut().contains("成绩单\n姓名|数学|语文|英语|编程|平均分|总分\n========================\n" +
            "========================\n全班总分平均数：0.0\n全班总分中位数：0.0\n")).isTrue();
    }

    @Test
    public void should_print_report_card_when_stu_id_exit() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn(TEST_STUDENT_INFO);
        library.entryMain();
        when(inputReader.readChooseNum(4)).thenReturn("2");
        when(inputReader.readChooseNum(2)).thenReturn("007");
        library.entryMain();
        assertThat(systemOut().contains("成绩单\n姓名|数学|语文|英语|编程|平均分|总分\n========================\n" +
            "张三|75|95|80|80|82.50|330\n========================\n全班总分平均数：330.0\n全班总分中位数：330.0\n")).isTrue();
    }

    @Test
    public void should_print_report_card_when_some_stu_id_exit_some_not_exit() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn(TEST_STUDENT_INFO);
        library.entryMain();
        when(inputReader.readChooseNum(4)).thenReturn("2");
        when(inputReader.readChooseNum(2)).thenReturn("007，110");
        library.entryMain();
        assertThat(systemOut().contains("成绩单\n姓名|数学|语文|英语|编程|平均分|总分\n========================\n" +
            "张三|75|95|80|80|82.50|330\n========================\n全班总分平均数：330.0\n全班总分中位数：330.0\n")).isTrue();
    }

    @Test
    public void should_print_report_card_when_some_stu_over_one() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn(TEST_STUDENT_INFO);
        library.entryMain();
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn(TEST_STUDENT_INFO2);
        library.entryMain();
        when(inputReader.readChooseNum(4)).thenReturn("2");
        when(inputReader.readChooseNum(2)).thenReturn("007，009");
        library.entryMain();
        assertThat(systemOut().contains("成绩单\n姓名|数学|语文|英语|编程|平均分|总分\n========================\n" +
            "张三|75|95|80|80|82.50|330\n李四|85|80|70|90|81.25|325\n========================\n全班总分平均数：327.5\n全班总分中位数：327.5\n")).isTrue();
    }

    @Test
    public void should_print_report_card_when_input_same_id_stu() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn(TEST_STUDENT_INFO);
        library.entryMain();
        when(inputReader.readChooseNum(4)).thenReturn("1");
        when(inputReader.readChooseNum(1)).thenReturn("张三，007，数学：75，语文：95，英语：70，编程：80");
        library.entryMain();
        when(inputReader.readChooseNum(4)).thenReturn("2");
        when(inputReader.readChooseNum(2)).thenReturn("007");
        library.entryMain();
        assertThat(systemOut().contains("成绩单\n姓名|数学|语文|英语|编程|平均分|总分\n========================\n" +
            "张三|75|95|70|80|80.00|320\n========================\n全班总分平均数：320.0\n全班总分中位数：320.0\n")).isTrue();
    }

    @Test
    public void should_give_prompt_msg_when_student_ids_format_wrong() throws Exception {
        when(inputReader.readChooseNum(4)).thenReturn("2");
        when(inputReader.readChooseNum(2)).thenReturn("ddffff,djjjj");
        library.entryMain();
        assertThat(systemOut().contains("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：\n")).isTrue();
    }

//    @Test
//    public void testSomeLibraryMethod() {
//        Library classUnderTest = new Library();
//        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
//    }
//
//    @Test
//    public void testMockClass() throws Exception {
//        // you can mock concrete classes, not only interfaces
//        LinkedList mockedList = mock(LinkedList.class);
//
//        // stubbing appears before the actual execution
//        String value = "first";
//        when(mockedList.get(0)).thenReturn(value);
//
//        assertEquals(mockedList.get(0), value);
//
//    }



}
