package com.tw;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;

public class Student {

    private final static String[] COURSE_ARRAY = {"数学","语文","英语","编程"};

    private String stuId;

    private String name;

    private Map<String, Integer> courseAndScoreMap;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getCourseAndScoreMap() {
        return courseAndScoreMap;
    }

    public void setCourseAndScoreMap(Map<String, Integer> courseAndScoreMap) {
        this.courseAndScoreMap = courseAndScoreMap;
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (Entry<String, Integer> entry : this.courseAndScoreMap.entrySet()) {
            totalScore += entry.getValue();
        }
        return totalScore;
    }

    public double getAverageScore() {
        return (double)getTotalScore()/COURSE_ARRAY.length;
    }

    public String getStudentReportCard() {

        StringBuilder reportCard = new StringBuilder();
        reportCard.append(this.name).append("|");
        for (int k = 0; k < COURSE_ARRAY.length; k++) {
            if (this.courseAndScoreMap.containsKey(COURSE_ARRAY[k])) {
                reportCard.append(this.courseAndScoreMap.get(COURSE_ARRAY[k])).append("|");
            } else {
                reportCard.append("0").append("|");
            }
        }
        reportCard.append(new DecimalFormat("#0.00").format(getAverageScore())).append("|")
            .append(getTotalScore()).append("\n");

        return reportCard.toString();
    }

}
