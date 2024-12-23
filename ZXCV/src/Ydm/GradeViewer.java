package Ydm;

import java.io.*;
import java.util.*;

public class GradeViewer {

    private Map<String, String> gradeMap;

    public GradeViewer(String fileName) {
        gradeMap = new HashMap<>();
        loadGrades(fileName);
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

    public void displayGrade(String studentId) {
        if (gradeMap.containsKey(studentId)) {
            System.out.println("학번: " + studentId + "의 성적은 " + gradeMap.get(studentId) + "입니다.");
        } else {
            System.out.println("해당 학번의 성적 정보를 찾을 수 없습니다.");
        }
    }

    public void displayAllGrades() {
        List<String> sortedIds = new ArrayList<>(gradeMap.keySet());
        Collections.sort(sortedIds);

        System.out.println("\n전체 학생 성적 목록:");
        for (String id : sortedIds) {
            System.out.println("학번: " + id + ", 성적: " + gradeMap.get(id));
        }
    }

    public static void main(String[] args) {
        GradeViewer gradeViewer = new GradeViewer("grades.txt"); //텍스트 파일 추가 예정

        Scanner scanner = new Scanner(System.in);
        System.out.print("학번을 입력하세요: ");
        String studentId = scanner.nextLine().trim();

        gradeViewer.displayGrade(studentId);
        gradeViewer.displayAllGrades();

        scanner.close();
    }
}
