package com.tw;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputReader {

    public final static String STUDENT_INFO_REG = "(([\\u4e00-\\u9fa5])|([a-zA-Z]))*，\\d*(，((数学)|(语文)|(英语)|(编程))：(([0-9])|([1-9][0-9])|(100))){1,4}";

    public final static String STUID_STRING_REG = "\\d*(，\\d*)*";

    public final static int ADD_STUDENT_WHEN_CHOOSE_ONE = 1;

    public final static int PRINT_STUDENT_REPORT_CARD_WHEN_CHOOSE_TWO = 2;

    private Scanner scanner = new Scanner(System.in);

    public String readChooseNum(int chooseNum) throws Exception {
        boolean t = scanner.hasNextLine();
        if (scanner.hasNextLine()) {
            String inputStr = scanner.nextLine().trim();
            return checkInputLegalAccordingToChooseNum(inputStr, chooseNum) ? inputStr : null;
        }
        return null;
    }

    public boolean checkInputLegalAccordingToChooseNum(String inputStr, int chooseNum) {

        boolean isLegal = false;
        switch (chooseNum) {
            case ADD_STUDENT_WHEN_CHOOSE_ONE:
                if (checkInputFormat(inputStr, STUDENT_INFO_REG)) {
                    isLegal = true;
                }
                break;

            case PRINT_STUDENT_REPORT_CARD_WHEN_CHOOSE_TWO:
                if (checkInputFormat(inputStr, STUID_STRING_REG)) {
                    isLegal = true;
                }
                break;

            default:
                break;
        }
        return isLegal;
    }

    public static boolean checkInputFormat(String input, String reg) {
        return Pattern.compile(reg).matcher(input).matches();
    }

}
