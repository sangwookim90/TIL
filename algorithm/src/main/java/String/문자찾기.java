package String;

import java.util.Scanner;

public class 문자찾기 {

    /**
     * 설명
     *
     * 한 개의 문자열을 입력받고, 특정 문자를 입력받아 해당 특정문자가 입력받은 문자열에 몇 개 존재하는지 알아내는 프로그램을 작성하세요.
     * 대소문자를 구분하지 않습니다.문자열의 길이는 100을 넘지 않습니다.
     *
     * 입력
     * 첫 줄에 문자열이 주어지고, 두 번째 줄에 문자가 주어진다.
     * 문자열은 영어 알파벳으로만 구성되어 있습니다.
     *
     * 출력
     * 첫 줄에 해당 문자의 개수를 출력한다.
     *
     * 예시
     * Computercooler
     * c
     *
     * 출력
     * 2
     *
     * 풀이: equalsIgnoreCase를 사용하여 비교하거나, 모든 문자열을 Upper 또는 Lower로 변환 후 비교한다.
     *
     *
     */

    public static int solution(String text, String ch) {
        int result = 0;

        if (text.length() > 100) {
            System.out.println("문자열의 길이가 " + text.length() + "로 너무 깁니다.");
            return -1;
        }

        for(int i=0; i<text.length(); i++) {
            if (ch.equalsIgnoreCase(String.valueOf(text.charAt(i)))) {
                result++;
            }
        }

        return result;
    }

    static int solution2(String text, String t) {

        int result = 0;

        text = text.toUpperCase();
        t = t.toUpperCase();

        for (char s : text.toCharArray()) {
            if(String.valueOf(s).equals(t)) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String text = in.next();
        String ch = in.next();

        System.out.println(solution2(text, ch));
    }
}
