package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchoolSearchApp extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField;
    private JTextArea resultArea;
    private StudentRepo studentRepository;

    public SchoolSearchApp(StudentRepo repository) {
        this.studentRepository = repository;

        setTitle("Schoolsearch");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        inputField = new JTextField();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton searchButton = new JButton("Пошук (ввести)");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleCommand(inputField.getText());
                inputField.setText("");
            }
        });

        JButton clearButton = new JButton("Очистити");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
                resultArea.setText("");
            }
        });

        JButton helpButton = new JButton("Показати команди");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.append(showHelp());
            }
        });

        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(helpButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JPanel downPanel = new JPanel(new BorderLayout());
        downPanel.add(buttonPanel, BorderLayout.NORTH);
        downPanel.add(resultArea, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.NORTH);
        add(downPanel, BorderLayout.SOUTH);

    }

    private void handleCommand(String command) {
        String[] parts = command.split(":");
        String keyword = parts[0].trim().toUpperCase();
        long startTime = System.currentTimeMillis();

        switch (keyword) {
            case "S":
                String lastName = parts[1].trim();
                outputArea.append(studentRepository.searchStudentByLastName(lastName));
                break;
            case "T":
                String teacherLastName = parts[1].trim();
                outputArea.append(studentRepository.searchByTeacherLastName(teacherLastName));
                break;
            case "C":
                int classroom = Integer.parseInt(parts[1].trim());
                if (parts.length > 2 && parts[2].trim().equalsIgnoreCase("T")) {
                    outputArea.append(studentRepository.searchTeacherByClassroom(classroom));
                } else {
                    outputArea.append(studentRepository.searchByClassroom(classroom));
                }
                break;
            case "G":
                int grade = Integer.parseInt(parts[1].trim());
                outputArea.append(studentRepository.searchByGrade(grade));
                break;
            case "B":
                int bus = Integer.parseInt(parts[1].trim());
                outputArea.append(studentRepository.searchByBus(bus));
                break;
            case "I":
                outputArea.append(studentRepository.showStats());
                break;
            case "H":
                outputArea.append(showHelp());
                break;
            case "Q":
                outputArea.append("До побачення!\n");
                System.exit(0);
                break;
            default:
                outputArea.append("Спробуйте знову\n");
        }

        long endTime = System.currentTimeMillis();
        long searchTime = endTime - startTime;
        resultArea.setText("");
        resultArea.append("Час пошуку: " + searchTime + "мс\n");
    }

    public String showHelp() {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Доступні пошукові запити:\n")
                .append("S:<Прізвище студента> - Пошук студента за прізвищем\n")
                .append("T:<Прізвище викладача> - Пошук студентів за прізвищем викладача\n")
                .append("C:<Номер класу> - Пошук студентів за номером класу\n")
                .append("C:<Номер класу>:T - Пошук викладача за номером класу\n")
                .append("G:<Номер класу> - Пошук студентів за номером класу\n")
                .append("B:<Номер автобуса> - Пошук студентів за номером автобуса\n")
                .append("I - Статистика (кількість студентів)\n")
                .append("H - Допомога (інформація про доступні команди)\n")
                .append("Q - Вихід з програми\n");
        return helpMessage.toString();
    }

    public static void main(String[] args) {
        StudentRepo repository = new StudentRepo("list.txt", "teachers.txt");
        SwingUtilities.invokeLater(() -> {
            SchoolSearchApp app = new SchoolSearchApp(repository);
            app.setVisible(true);
        });
    }
}
