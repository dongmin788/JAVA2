package Ydm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
/**
 * 성적 조회 시스템을 관리하는 GUI 프로그램.
 * 
 * @author Author
 * @version 1.0
 * @since 2024-12-24
 * 
 * @created 2024-12-24
 * @lastModified 2024-12-24
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * GradeViewer는 학생의 성적 정보를 파일에서 읽어와 GUI를 통해 표시하는 프로그램입니다.
 */
public class GradeViewer extends JFrame {

    /**
     * 학번과 학생 데이터를 저장하는 맵.
     * Key: 학번, Value: [과목명, 이름, 점수, 성취도].
     */
    private Map<String, String[]> gradeMap;

    /** 사용자 입력 필드 */
    private JTextField inputField;

    /** 성적 결과 표시 영역 */
    private JTextArea resultArea;

    /**
     * GradeViewer 생성자. 파일 이름을 입력받아 데이터를 로드하고 GUI를 초기화합니다.
     * 
     * @param fileName 데이터를 읽을 파일 이름.
     */
    public GradeViewer(String fileName) {
        gradeMap = new HashMap<>();
        loadGrades(fileName);
        initializeGUI();
    }

    /**
     * 파일에서 학생 데이터를 읽어와 gradeMap에 저장합니다.
     * 
     * @param fileName 데이터를 읽을 파일 이름.
     */
   //chat GPT 이용
    private void loadGrades(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // 파일 형식: 학번,과목명,이름,점수,성취도
                if (parts.length == 5) {
                    gradeMap.put(parts[0].trim(), new String[]{parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim()});
                }
            }
        } catch (IOException e) {
            System.out.println("파일 처리 오류: " + e.getMessage());
        }
    }

    /**
     * GUI를 초기화합니다. 버튼, 입력 필드 및 결과 영역을 설정합니다.
     */
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

        //chat GPT 이용
        for (int i = 0; i <= 9; i++) {
            String number = String.valueOf(i);
            JButton button = new JButton(number);
            button.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
            button.setBackground(Color.WHITE);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.addActionListener(e -> inputField.setText(inputField.getText() + number));
            buttonPanel.add(button);
        }

        JButton backspaceButton = new JButton("←");
        backspaceButton.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        backspaceButton.setBackground(Color.WHITE);
        backspaceButton.setOpaque(true);
        backspaceButton.setBorderPainted(false);
        backspaceButton.addActionListener(e -> {
            String text = inputField.getText();
            if (text.length() > 0) {
                inputField.setText(text.substring(0, text.length() - 1));
            }
        });
        buttonPanel.add(backspaceButton);

        JButton confirmButton = new JButton("확인");
        confirmButton.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
        confirmButton.setBackground(Color.WHITE);
        confirmButton.setOpaque(true);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(e -> displayGrade(inputField.getText()));
        buttonPanel.add(confirmButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * 학번에 해당하는 성적 정보를 표시합니다. 
     * 학번 목록을 정렬하여 전체 학생 목록도 출력합니다.
     * 
     * @param studentId 조회할 학번.
     */
   // Chat GPT 이용
    public void displayGrade(String studentId) {
        if (gradeMap == null || gradeMap.isEmpty()) {
            resultArea.setText("데이터가 없습니다. 파일을 확인하세요.");
            return;
        }

        if (gradeMap.containsKey(studentId)) {
            String[] data = gradeMap.get(studentId);
            resultArea.setText(
                "과목명: " + data[0] + "\n" +
                "학번: " + studentId + "\n" +
                "이름: " + data[1] + "\n" +
                "점수: " + data[2] + "\n" +
                "성취도: " + data[3]
            );

            // 추가 컬렉션 사용: 학번 목록 정렬 후 출력
            List<String> sortedKeys = new ArrayList<>(gradeMap.keySet());
            Collections.sort(sortedKeys);
            resultArea.append("\n\n전체 학생 목록:\n");
            for (String key : sortedKeys) {
                String[] sortedData = gradeMap.get(key);
                resultArea.append("학번: " + key + ", 이름: " + sortedData[1] + ", 점수: " + sortedData[2] + "\n");
            }
        } else {
            resultArea.setText("해당 학번의 성적 정보를 찾을 수 없습니다.");
        }
    }

    /**
     * 프로그램의 진입점. 데이터를 읽을 파일 이름을 전달받아 GradeViewer를 실행합니다.
     * 
     * @param args 명령줄 인자. 첫 번째 인자는 파일 이름으로 사용됩니다.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeViewer("grades.txt"));
    }
}

