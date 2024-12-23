package Ydm;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GradeViewer extends JFrame {

    private Map<String, String> gradeMap;
    private JTextField inputField;
    private JTextArea resultArea;

    public GradeViewer(String fileName) {
        gradeMap = new HashMap<>();
        loadGrades(fileName);
        initializeGUI();
    }

    private void loadGrades(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // 파일 형식: 학번,성적
                if (parts.length == 2) {
                    gradeMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("성적 파일을 찾을 수 없습니다. 파일명을 확인해주세요.");
        } catch (IOException e) {
            System.out.println("파일을 읽는 도중 오류가 발생했습니다.");
        }
    }

    private void initializeGUI() {
        setTitle("청주대 성적조회 시스템");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        inputField = new JTextField();
        inputField.setEditable(false);
        inputField.setFont(new Font("Malgun Gothic", Font.PLAIN, 28));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        add(inputField, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 10, 10));

        for (int i = 0; i <= 9; i++) {
            String number = String.valueOf(i);
            JButton button = new JButton(number);
            button.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
            button.addActionListener(e -> inputField.setText(inputField.getText() + number));
            buttonPanel.add(button);
        }

        JButton backspaceButton = new JButton("←");
        backspaceButton.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        backspaceButton.addActionListener(e -> {
            String text = inputField.getText();
            if (text.length() > 0) {
                inputField.setText(text.substring(0, text.length() - 1));
            }
        });
        buttonPanel.add(backspaceButton);

        JButton confirmButton = new JButton("확인");
        confirmButton.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        confirmButton.addActionListener(e -> displayGrade(inputField.getText()));
        buttonPanel.add(confirmButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void displayGrade(String studentId) {
        if (gradeMap.containsKey(studentId)) {
            resultArea.setText("학번: " + studentId + "의 성적은 " + gradeMap.get(studentId) + "입니다.");
        } else {
            resultArea.setText("해당 학번의 성적 정보를 찾을 수 없습니다.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeViewer("grades.txt"));
    }
}


