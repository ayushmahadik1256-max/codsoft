public class GradeCalculatorEngine {
    
    public static double calculateAverage(double totalMarks, int totalSubjects) {
        if (totalSubjects == 0) return 0.0;
        return totalMarks / totalSubjects;
    }

    /**
     * Maps percentage to standard letter metrics[cite: 48].
     */
    public static String determineGrade(double percentage) {
        if (percentage >= 90) return "A+ (Outstanding)";
        if (percentage >= 80) return "A (Excellent)";
        if (percentage >= 70) return "B (Very Good)";
        if (percentage >= 60) return "C (Good)";
        if (percentage >= 50) return "D (Pass)";
        return "F (Fail)";
    }
}
