package String;


import java.util.Scanner;

/*
    - 문제
    N개의 단어가 주어지면 각 단어를 뒤집어 출력하는 프로그램을 작성하세요.

    - 입력
    첫 줄에 자연수 N(3<=N<=20)이 주어집니다.
    두 번째 줄부터 N개의 단어가 각 줄에 하나씩 주어집니다. 단어는 영어 알파벳으로만 구성되어 있습니다.

    - 출력
    N개의 단어를 입력된 순서대로 한 줄에 하나씩 뒤집어서 출력합니다.

    ex)
    - input
    3
    good
    Time
    Big
    - output
    doog
    emiT
    giB


 */
public class 단어뒤집기 {

    // StringBuilder reverse 사용
    static String[] solution(String[] words) {

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<words.length; i++) {
            sb.append(words[i]);
            words[i] = sb.reverse().toString();
            sb.delete(0, words[i].length());
        }

        return words;
    }

    // 직접 reverse 구현
    static String[] solution2(String[] words) {
        String[] result = new String[words.length];
        int lt = 0;
        int rt;

        for(int i=0; i<words.length; i++) {
            char[] arr = words[i].toCharArray();
            rt = arr.length-1;

            while(lt<rt) {
                char tmp = arr[lt];
                arr[lt] = arr[rt];
                arr[rt] = tmp;
                lt++;
                rt--;
            }

            result[i] = String.valueOf(arr);
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] words = new String[n];
        for(int i=0; i<n; i++) {
            words[i] = in.next();
        }

        String[] result = solution2(words);
        for(String a : result) {
            System.out.println(a);
        }
    }
}
