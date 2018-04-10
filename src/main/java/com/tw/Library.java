package com.tw;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private final static String PRINT_MESSAGE_WHEN_CHOOSE_ONE = "请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：\n";

    private final static String PRINT_MESSAGE_WHEN_CHOOSE_tWO = "请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：\n";

    private final static String PRINT_MESSAGE_WHEN_IN_MAIN = "1. 添加学生\n2. 生成成绩单\n3. 退出\n请输入你的选择（1～3）：\n";

    private final static String MESSAGE_WHEN_STUDENT_INFO_FORMAT_WRONG = "请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：\n";

    private final static String MESSAGE_WHEN_STUDENT_IDS_FORMAT_WRONG = "请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：\n";

    private final static int ADD_STUDENT_WHEN_CHOOSE_ONE = 1;

    private final static int PRINT_STUDENT_REPORT_CARD_WHEN_CHOOSE_TWO = 2;

    private final static int INDEX_STUDENT_NAME = 0;
    private final static int INDEX_STUDENT_ID = 1;
    private final static int INDEX_STUDENT_FIRST_COURSE = 2;

    private final static int INDEX_COURSE = 0;
    private final static int INDEX_SCORE = 1;

    private static List<Student> stuList = new ArrayList<>();

    private InputReader readInput = new InputReader();

    private final static int NOT_IN_ONE_TO_THREE_CHOOSE_NUM = 4;

    public Library(InputReader readInput) {
        this.readInput = readInput;
    }

    public boolean entryMain() throws Exception {
        System.out.print(PRINT_MESSAGE_WHEN_IN_MAIN);
        boolean isSuccess = false;

        switch (Integer.parseInt(readInput.readChooseNum(NOT_IN_ONE_TO_THREE_CHOOSE_NUM))) {
            case InputReader.ADD_STUDENT_WHEN_CHOOSE_ONE:
                if (addStudent(readInput.readChooseNum(ADD_STUDENT_WHEN_CHOOSE_ONE))) {
                    isSuccess = true;
                }
                break;

            case InputReader.PRINT_STUDENT_REPORT_CARD_WHEN_CHOOSE_TWO:
                if (printReportCard(readInput.readChooseNum(PRINT_STUDENT_REPORT_CARD_WHEN_CHOOSE_TWO))) {
                    isSuccess = true;
                }
                break;

            default:
                break;
        }
        return isSuccess;
    }

    public boolean addStudent(String stuInfo) {
        System.out.print(PRINT_MESSAGE_WHEN_CHOOSE_ONE);
        boolean addSuccess = true;
        if (null != stuInfo && InputReader.checkInputFormat(stuInfo, InputReader.STUDENT_INFO_REG)) {

            String[] stuInfoArray = stuInfo.split("，");

            boolean isExited = false;
            int index = 0;
            for (int i = 0; i < stuList.size(); i++) {
                if (stuList.get(i).getStuId().equals(stuInfoArray[INDEX_STUDENT_ID])) {
                    isExited = true;
                    index = i;
                    break;
                }
            }

            Student student = new Student();
            if(isExited) {
                student = stuList.get(index);
            }

            Map<String, Integer> courseAndScoreMap = new HashMap<>();
            for (int j = INDEX_STUDENT_FIRST_COURSE; j < stuInfoArray.length; j++) {
                String[] courseAndScore = stuInfoArray[j].split("：");
                courseAndScoreMap.put(courseAndScore[INDEX_COURSE], Integer.parseInt(courseAndScore[INDEX_SCORE]));
            }
            student.setName(stuInfoArray[INDEX_STUDENT_NAME]);
            student.setCourseAndScoreMap(courseAndScoreMap);

            if (isExited) {
                stuList.set(index, student);
            } else {
                student.setStuId(stuInfoArray[INDEX_STUDENT_ID]);
                stuList.add(student);
            }

            System.out.print("学生"+ stuInfoArray[INDEX_STUDENT_NAME] +"的成绩被添加\n" + PRINT_MESSAGE_WHEN_IN_MAIN);

        } else {
            System.out.print(MESSAGE_WHEN_STUDENT_INFO_FORMAT_WRONG);
            addSuccess = false;
        }
        return addSuccess;
    }

    public boolean printReportCard(String stuIdStr) {

        System.out.print(PRINT_MESSAGE_WHEN_CHOOSE_tWO);
        boolean printSuccess = true;
        if (null != stuIdStr && InputReader.checkInputFormat(stuIdStr, InputReader.STUID_STRING_REG) ) {
            String[] stuIds = stuIdStr.split("，");

            int stuTotalNum = 0;
            int stuTotalScore = 0;
            List<Integer> totalScoreList = new ArrayList<>();

            StringBuilder reportCardMsg = new StringBuilder();
            reportCardMsg.append("成绩单\n姓名|数学|语文|英语|编程|平均分|总分\n========================\n");
            for (int i = 0; i < stuIds.length; i++) {
                for (int j = 0; j < stuList.size(); j++) {
                    if (stuIds[i].equals(stuList.get(j).getStuId())) {

                        stuTotalNum += 1;
                        stuTotalScore += stuList.get(j).getTotalScore();
                        totalScoreList.add(stuList.get(j).getTotalScore());
                        reportCardMsg.append(stuList.get(j).getStudentReportCard());
                    }
                }
            }

            double avgTotalScore = stuTotalNum>0? ((double) stuTotalScore)/stuTotalNum : 0.0;
            reportCardMsg.append("========================\n全班总分平均数："+ new DecimalFormat("#0.0").format(avgTotalScore)
                +"\n全班总分中位数："+ new DecimalFormat("#0.0").format(getMedian(totalScoreList)));
            System.out.print(reportCardMsg.toString() + "\n" + PRINT_MESSAGE_WHEN_IN_MAIN);

        } else {
            System.out.print(MESSAGE_WHEN_STUDENT_IDS_FORMAT_WRONG);
            printSuccess = false;
        }
        return printSuccess;
    }

    public double getMedian(List<Integer> list){
        double median = 0.0;
        if (list != null && list.size() > 0) {
            Collections.sort(list);
            if (list.size()%2 == 0) {
                median = (list.get(list.size()/2) + list.get(list.size()/2 - 1))/2.0;
            } else {
                median = list.get(list.size()/2);
            }
        }
        return median;
    }

}
