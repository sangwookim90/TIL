package String;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/*
    - 문제
    소문자로 된 한개의 문자열이 입력되면 중복된 문자를 제거하고 출력하는 프로그램을 작성하세요.
    중복이 제거된 문자열의 각 문자는 원래 문자열의 순서를 유지합니다.

    - 입력
    첫 줄에 문자열이 입력됩니다. 문자열의 길이는 100을 넘지 않는다.

    - 출력
    첫 줄에 중복문자가 제거된 문자열을 출력합니다.

    ex)
    - input
    ksekkset
    - output
    kset

 */
public class 중복문자제거 {

    // HashSet 사용
    static String solution(String str) {
        String result = "";
        char[] arr = str.toCharArray();
        Set<Character> set = new HashSet<>();

        for(char a : arr) {
            if(set.contains(a) == false) {
                set.add(a);
                result += a;
            }
        }

        return result;
    }


    // charAt 사용 : 처음 발견된 index
    static String solution2 (String input) {
        String answer = "";

        for(int i=0; i<input.length(); i++) {

            // 해당 문자의 index가 처음 발견된 index 와 같은 경우
            if(input.indexOf(input.charAt(i))==i) {
                answer+=input.charAt(i);
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.next();
        System.out.println(solution(str));

    }
}
