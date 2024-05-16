import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class StudentGrade {
    private String name;
    private String discipline;
    private int grade;

    public StudentGrade(String name, String discipline, int grade) {
        this.name = name;
        this.discipline = discipline;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getDiscipline() {
        return discipline;
    }

    public int getGrade() {
        return grade;
    }
}

class GradeReader {
    public static List<StudentGrade> readGrades(String filePath) {
        List<StudentGrade> gradesList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String name = parts[0];
                    String discipline = parts[1];
                    int grade = Integer.parseInt(parts[2]);
                    StudentGrade studentGrade = new StudentGrade(name, discipline, grade);
                    gradesList.add(studentGrade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gradesList;
    }
}

class Classroom {
    private List<StudentGrade> studentGrades;

    public Classroom(List<StudentGrade> studentGrades) {
        this.studentGrades = studentGrades;
    }

    public List<Integer> getGradesForDiscipline(String discipline) {
        List<Integer> grades = new ArrayList<>();
        for (StudentGrade grade : studentGrades) {
            if (grade.getDiscipline().equals(discipline)) {
                grades.add(grade.getGrade());
            }
        }
        return grades;
    }

    public List<Integer> getGradesForStudent(String student) {
        List<Integer> grades = new ArrayList<>();
        for (StudentGrade grade : studentGrades) {
            if (grade.getName().equals(student)) {
                grades.add(grade.getGrade());
            }
        }
        return grades;
    }

    public StudentGrade getMaxGrade(String discipline) {
        List<StudentGrade> grades = new ArrayList<>();
        for (StudentGrade grade : studentGrades) {
            if (grade.getDiscipline().equals(discipline)) {
                grades.add(grade);
            }
        }
        if (!grades.isEmpty()) {
            return grades.stream().max((g1, g2) -> Integer.compare(g1.getGrade(), g2.getGrade())).get();
        } else {
            return null;
        }
    }

    public StudentGrade getMaxGrade() {
        return studentGrades.stream().max((g1, g2) -> Integer.compare(g1.getGrade(), g2.getGrade())).get();
    }

    public double getAverageGrade(String discipline) {
        List<Integer> grades = getGradesForDiscipline(discipline);
        if (!grades.isEmpty()) {
            double sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            return sum / grades.size();
        } else {
            return 0;
        }
    }

    public StudentGrade getWorstGrade(String discipline) {
        List<StudentGrade> grades = new ArrayList<>();
        for (StudentGrade grade : studentGrades) {
            if (grade.getDiscipline().equals(discipline)) {
                grades.add(grade);
            }
        }
        if (!grades.isEmpty()) {
            return grades.stream().min((g1, g2) -> Integer.compare(g1.getGrade(), g2.getGrade())).get();
        } else {
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String filePath = "grades.txt";
        List<StudentGrade> grades = GradeReader.readGrades(filePath);
        Classroom classroom = new Classroom(grades);

        System.out.println("Grades for Math: " + classroom.getGradesForDiscipline("Mathematics"));
        System.out.println("Grades for John: " + classroom.getGradesForStudent("John"));
        System.out.println("Max grade for Math: " + classroom.getMaxGrade("Mathematics").getGrade());
        System.out.println("Max grade overall: " + classroom.getMaxGrade().getGrade());
        System.out.println("Average grade for Math: " + classroom.getAverageGrade("Mathematics"));
        System.out.println("Worst grade for Math: " + classroom.getWorstGrade("Mathematics").getGrade());
    }
}
