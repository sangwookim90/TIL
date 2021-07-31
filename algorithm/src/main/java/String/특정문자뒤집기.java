package String;

import java.util.Scanner;

/*
    - 문제
    영어 알파벳과 특수문자로 구성된 문자열이 주어지면 영어 알파벳만 뒤집고,
    특수문자는 자기 자리에 그대로 있는 문자열을 만들어 출력하는 프로그램을 작성하세요.

    - 입력
    첫 줄에 길이가 100을 넘지 않는 문자열이 주어집니다.

    - 출력
    첫 줄에 알파벳만 뒤집힌 문자열을 출력합니다.

    ex)
    - input
    a#b!GE*T@S
    - output
    S#T!EG*b@a

 */
public class 특정문자뒤집기 {

    static String solution(String str) {
        char[] arr = str.toCharArray();

        int lt=0;
        int rt=arr.length-1;
        while(lt <= rt) {
            if(String.valueOf(arr[lt]).matches("([a-zA-Z])")) {
                if(String.valueOf(arr[rt]).matches("([a-zA-Z])")) {
                    char tmp = arr[lt];
                    arr[lt] = arr[rt];
                    arr[rt] = tmp;
                    lt++;
                }
                rt--;
            } else {
                if(String.valueOf(arr[rt]).matches("([a-zA-Z])") == false) {
                    rt--;
                }
                lt++;
            }
        }

        return String.valueOf(arr);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        System.out.println(solution(str));

    }

}
