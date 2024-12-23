package Ydm;

import java.io.*;
import java.util.*;

public class GradeViewer {
    public static void main(String[] args) {
        // 학번과 성적 데이터를 저장할 맵
        Map<String, String> gradeMap = new HashMap<>();

        // 파일에서 데이터 읽기
        String fileName = "grades.txt";
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
            return;
        } catch (IOException e) {
            System.out.println("파일을 읽는 도중 오류가 발생했습니다.");
            return;
        }

        // 사용자 입력 처리
        Scanner scanner = new Scanner(System.in);
        System.out.print("학번을 입력하세요: ");
        String studentId = scanner.nextLine().trim();

        // 성적 조회
        if (gradeMap.containsKey(studentId)) {
            System.out.println("학번: " + studentId + "의 성적은 " + gradeMap.get(studentId) + "입니다.");
        } else {
            System.out.println("해당 학번의 성적 정보를 찾을 수 없습니다.");
        }

        // 모든 성적을 리스트로 정렬하여 출력 (컬렉션 활용)
        List<String> sortedIds = new ArrayList<>(gradeMap.keySet());
        Collections.sort(sortedIds);

        System.out.println("\n전체 학생 성적 목록:");
        for (String id : sortedIds) {
            System.out.println("학번: " + id + ", 성적: " + gradeMap.get(id));
        }

        scanner.close();
    }
}
