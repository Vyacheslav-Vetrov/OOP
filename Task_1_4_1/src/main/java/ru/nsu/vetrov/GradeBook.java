package ru.nsu.vetrov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents an electronic grade book for a student, with functionalities
 * to manage grades and evaluate academic achievements.
 */
public class GradeBook {
    private static final int EXCELLENT_GRADE = 5;
    private static final int SATISFACTORY_GRADE = 3;
    private String studentName;
    private int currentSemester;
    private List<Grade> grades;
    private Grade finalQualifyingWorkGrade;

    /**
     * Constructs a new GradeBook with initial values.
     *
     * @param studentName     The name of the student.
     * @param initialSemester The initial semester of the student.
     */
    public GradeBook(String studentName, int initialSemester) {
        this.studentName = studentName;
        this.currentSemester = initialSemester;
        this.grades = new ArrayList<>();
    }

    /**
     * Adds a grade to the grade book.
     *
     * @param grade    The grade achieved.
     * @param subject  The subject for which the grade was achieved.
     * @param semester The semester in which the grade was achieved.
     */
    public void addGrade(int grade, String subject, int semester) {
        if (grade < 1 || grade > 5) {
            throw new IllegalArgumentException("Grade must be between 1 and 5.");
        }
        grades.add(new Grade(grade, subject, semester));
    }


    /**
     * Sets the grade for the final qualifying work.
     *
     * @param grade The grade of the final qualifying work.
     */
    public void setFinalQualifyingWorkGrade(int grade) {
        this.finalQualifyingWorkGrade = new Grade(grade, "Final Qualifying Work", currentSemester);
    }

    /**
     * Calculates the average grade across all semesters.
     *
     * @return The average grade.
     */
    public double calculateAverageGrade() {
        return grades.stream().mapToInt(Grade::getGrade).average().orElse(0.0);
    }

    /**
     * Determines if the student is eligible for honors.
     *
     * @return True if eligible for honors, false otherwise.
     */
    public boolean isEligibleForHonors() {
        Map<String, Grade> latestGrades = grades.stream()
                .collect(Collectors.toMap(
                        Grade::getSubject,
                        grade -> grade,
                        (existing, replacement) -> existing.getSemester()
                                > replacement.getSemester() ? existing : replacement));

        long excellentGradesCount = latestGrades.values().stream()
                .filter(g -> g.getGrade() == EXCELLENT_GRADE)
                .count();
        boolean hasSatisfactoryGrade = latestGrades.values().stream()
                .anyMatch(g -> g.getGrade() == SATISFACTORY_GRADE);
        boolean finalWorkIsExcellent = finalQualifyingWorkGrade != null
                && finalQualifyingWorkGrade.getGrade() == EXCELLENT_GRADE;

        return excellentGradesCount >= latestGrades.size() * 0.75
                && !hasSatisfactoryGrade && finalWorkIsExcellent;
    }


    /**
     * Determines if the student is eligible for an increased scholarship
     * based on the grades of the current semester.
     *
     * @return True if eligible for increased scholarship, false otherwise.
     */
    public boolean isEligibleForIncreasedScholarship() {
        List<Grade> lastSemesterGrades = grades.stream()
                .filter(g -> g.getSemester() == currentSemester)
                .collect(Collectors.toList());

        return lastSemesterGrades.stream().allMatch(g -> g.getGrade() == EXCELLENT_GRADE);
    }

    /**
     * Increments the current semester.
     */
    public void incrementSemester() {
        currentSemester++;
    }

    /**
     * Represents a grade for a specific subject in a specific semester.
     */
    private static class Grade {
        private int grade;
        private String subject;
        private int semester;

        /**
         * Constructs a new Grade.
         *
         * @param grade    The grade achieved.
         * @param subject  The subject for which the grade was achieved.
         * @param semester The semester in which the grade was achieved.
         */
        public Grade(int grade, String subject, int semester) {
            this.grade = grade;
            this.subject = subject;
            this.semester = semester;
        }

        /**
         * Gets the grade.
         *
         * @return The grade.
         */
        public int getGrade() {
            return grade;
        }

        /**
         * Gets the semester of the grade.
         *
         * @return The semester.
         */
        public int getSemester() {
            return semester;
        }

        /**
         * Gets the subject of the grade.
         *
         * @return The subject.
         */
        public String getSubject() {
            return subject;
        }

    }
}
