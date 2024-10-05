package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRepo {
    private ArrayList<Student> students = new ArrayList<>();
    private Map<Integer, List<Teacher>> classroomTeachers = new HashMap<>();

    public StudentRepo(String stFileName, String tFileName) {
        loadData(stFileName);
        loadTeachersData(tFileName);
    }

    private void loadData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 5) {
                    String lastName = parts[0];
                    String firstName = parts[1];
                    int grade = Integer.parseInt(parts[2]);
                    int classroom = Integer.parseInt(parts[3]);
                    int bus = Integer.parseInt(parts[4]);

                    students.add(new Student(lastName, firstName, grade, classroom, bus));
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні " + fileName);
        }
    }

    private void loadTeachersData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String lastName = parts[0];
                    String firstName = parts[1];
                    int classroom = Integer.parseInt(parts[2]);

                    Teacher teacher = new Teacher(lastName, firstName, classroom);

                    classroomTeachers.putIfAbsent(classroom, new ArrayList<>());
                    classroomTeachers.get(classroom).add(teacher);
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні " + fileName);
        }
    }

    public String showStats() {
        StringBuilder result = new StringBuilder();
        result.append(students.size()).append(" студентів і вчителів: ").append(classroomTeachers.size());
        return result.toString();
    }

    public String searchStudentByLastName(String lastName) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        for (Student student : students) {
            if (student.getLastName().equalsIgnoreCase(lastName)) {
                List<Teacher> teachers = findTeachersByClassroom(student.getClassroom());
                if (!teachers.isEmpty()) {
                    result.append(student.toString());
                    for (Teacher teacher : teachers) {
                        result.append("Teachers: ").append(teacher.getLastName()).append(" ").append(teacher.getFirstName()).append("\n");
                    }
                    found = true;
                }
            }
        }
        if (!found) {
            result.append("Немає таких студентів: ").append(lastName).append("\n");
        }
        return result.toString();
    }

    private List<Teacher> findTeachersByClassroom(int classroom) {
        return classroomTeachers.getOrDefault(classroom, new ArrayList<>());
    }

    public String searchByTeacherLastName(String teacherLastName) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        for (List<Teacher> teachers : classroomTeachers.values()) {
            for (Teacher teacher : teachers) {
                if (teacher.getLastName().equalsIgnoreCase(teacherLastName)) {
                    int classroom = teacher.getClassroom();
                    for (Student student : students) {
                        if (student.getClassroom() == classroom) {
                            result.append(student.toString()).append(teacher.getLastName()).append(" ").append(teacher.getFirstName()).append("\n");
                            found = true;
                        }
                    }
                }
            }
        }
        if (!found) {
            result.append("Немає такого викладача: ").append(teacherLastName).append("\n");
        }
        return result.toString();
    }

    public String searchByGrade(int grade) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        for (Student student : students) {
            if (student.getGrade() == grade) {
                result.append(student.toString()).append(")\n");
                found = true;
            }
        }
        if (!found) {
            result.append("Немає студентів у цьому класі: ").append(grade).append("\n");
        }
        return result.toString();
    }

    public String searchTeacherByClassroom(int classroom) {
        StringBuilder result = new StringBuilder();
        List<Teacher> teachers = findTeachersByClassroom(classroom);
        if (!teachers.isEmpty()) {
            for (Teacher teacher : teachers) {
                result.append(teacher.getFirstName()).append(" ").append(teacher.getLastName()).append("\n");
            }
        } else {
            result.append("Немає викладачів для кімнати: ").append(classroom).append("\n");
        }
        return result.toString();
    }

    public String searchTeachersByGrade(int grade) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        for (List<Teacher> teachers : classroomTeachers.values()) {
            for (Teacher teacher : teachers) {
                for (Student student : students) {
                    if (student.getGrade() == grade && student.getClassroom() == teacher.getClassroom()) {
                        result.append(teacher.getFirstName()).append(" ").append(teacher.getLastName()).append("\n");
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            result.append("Немає викладачів для класу: ").append(grade).append("\n");
        }
        return result.toString();
    }

    public String searchByClassroom(int classroom) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        for (Student student : students) {
            if (student.getClassroom() == classroom) {
                List<Teacher> teachers = findTeachersByClassroom(student.getClassroom());
                if (!teachers.isEmpty()) {
                    result.append(student.toString()).append("\n");
                    found = true;
                }
            }
        }
        if (!found) {
            result.append("Немає студентів з таким кабінетом: ").append(classroom).append("\n");
        }
        return result.toString();
    }

    public String searchByBus(int bus) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        for (Student student : students) {
            if (student.getBus() == bus) {
                List<Teacher> teachers = findTeachersByClassroom(student.getClassroom());
                if (!teachers.isEmpty()) {
                    result.append(student.toString()).append("\n");
                    found = true;
                }
            }
        }
        if (!found) {
            result.append("Немає студентів з таким маршрутом: ").append(bus).append("\n");
        }
        return result.toString();
    }
}
