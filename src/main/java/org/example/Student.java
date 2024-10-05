package org.example;

public class Student {
    private String lastName;
    private String firstName;
    private int grade;
    private int classroom;
    private int bus;

    public Student(String lastName, String firstName, int grade, int classroom, int bus) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.grade = grade;
        this.classroom = classroom;
        this.bus = bus;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getGrade() {
        return grade;
    }

    public int getClassroom() {
        return classroom;
    }

    public int getBus() {
        return bus;
    }


    @Override
    public String toString() {
        return firstName + " " + lastName + " (Grade " + grade + ", Classroom " + classroom + ") - Bus: " + bus + " ";
    }
}
