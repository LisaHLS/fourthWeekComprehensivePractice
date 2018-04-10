package com.tw;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class StudentTest {

    private Student student;

    @Before
    public void beforeEach() {
        student = new Student();
        student.setName("張三");
        student.setStuId("20150001");
        Map<String,Integer> courseAndScore = new HashMap<>();
        courseAndScore.put("数学",75);
        courseAndScore.put("语文",95);
        courseAndScore.put("英语",80);
        courseAndScore.put("编程",80);
        student.setCourseAndScoreMap(courseAndScore);
    }

    @Test
    public void should_student_have_stuId_name_courseAndScore(){
        assertThat(student.getName()).isEqualTo("張三");
        assertThat(student.getStuId()).isEqualTo("20150001");
        assertThat(student.getCourseAndScoreMap().get("数学")).isEqualTo(75);
        assertThat(student.getCourseAndScoreMap().get("语文")).isEqualTo(95);
        assertThat(student.getCourseAndScoreMap().get("英语")).isEqualTo(80);
        assertThat(student.getCourseAndScoreMap().get("编程")).isEqualTo(80);

    }

    @Test
    public void should_return_student_right_report_card_when_have_four_course(){
        assertThat(student.getStudentReportCard()).isEqualTo("張三|75|95|80|80|82.50|330\n");

    }

    @Test
    public void should_return_student_right_report_card_when_have_three_course(){
        Map<String,Integer> courseAndScore = new HashMap<>();
        courseAndScore.put("数学",75);
        courseAndScore.put("语文",95);
        courseAndScore.put("英语",80);
        student.setCourseAndScoreMap(courseAndScore);
        assertThat(student.getStudentReportCard()).isEqualTo("張三|75|95|80|0|62.50|250\n");

    }

}
