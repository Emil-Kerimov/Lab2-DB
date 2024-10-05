package org.example;

public class Teacher {
    private String firstName;
    private String lastName;
    private int classroom;

    public Teacher(String lastName, String firstName, int classroom) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.classroom = classroom;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + classroom;
    }

}
