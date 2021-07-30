package Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
    Programmers link: https://programmers.co.kr/learn/courses/30/lessons/42748?language=java

    - 문제
    배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때, k번째에 있는 수를 구하려 합니다.
    예를 들어 array가 [1, 5, 2, 6, 3, 7, 4], i = 2, j = 5, k = 3이라면
    array의 2번째부터 5번째까지 자르면 [5, 2, 6, 3]입니다.

    1에서 나온 배열을 정렬하면 [2, 3, 5, 6]입니다.
    2에서 나온 배열의 3번째 숫자는 5입니다.
    배열 array, [i, j, k]를 원소로 가진 2차원 배열 commands가 매개변수로 주어질 때, commands의 모든 원소에 대해 앞서 설명한 연산을 적용했을 때 나온 결과를 배열에 담아 return 하도록 solution 함수를 작성해주세요.

    - 제한사항
    array의 길이는 1 이상 100 이하입니다.
    array의 각 원소는 1 이상 100 이하입니다.
    commands의 길이는 1 이상 50 이하입니다.
    commands의 각 원소는 길이가 3입니다.


    - 입출력 예 설명
    [1, 5, 2, 6, 3, 7, 4]를 2번째부터 5번째까지 자른 후 정렬합니다. [2, 3, 5, 6]의 세 번째 숫자는 5입니다.
    [1, 5, 2, 6, 3, 7, 4]를 4번째부터 4번째까지 자른 후 정렬합니다. [6]의 첫 번째 숫자는 6입니다.
    [1, 5, 2, 6, 3, 7, 4]를 1번째부터 7번째까지 자릅니다. [1, 2, 3, 4, 5, 6, 7]의 세 번째 숫자는 3입니다.
 */

public class K번째수 {

    /*
      Arrays.copyOf(원본배열, 복사할 길이);
      Arrays.copyOfRange(원본 배열, 복사할 시작인덱스, 복사할 끝인덱스) 인덱스는 0부터 시작하는것 기준
     */

    static int[] best_solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        for(int i=0; i<commands.length; i++){
            int[] temp = Arrays.copyOfRange(array, commands[i][0]-1, commands[i][1]);
            Arrays.sort(temp);
            answer[i] = temp[commands[i][2]-1];
        }

        return answer;
    }

    static int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];


        for(int i = 0; i < commands.length; i++) {
            List<Integer> integers = new ArrayList<>();
            int start = commands[i][0];
            int end = commands[i][1];
            int target = commands[i][2];

            for(int j = start -1; j < end; j++ ) {
                integers.add(array[j]);
            }
            Collections.sort(integers);


            answer[i] = integers.get(target -1);
        }

        return answer;
    }

    public static void main(String[] args) {

        int[] array = {1,5,2,6,3,7,4};
        int[][] commands = {{2,5,3},{4,4,1},{1,7,3}};

        int[] result = solution(array, commands);
        for(int i=0; i<commands.length; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println();

    }

}
