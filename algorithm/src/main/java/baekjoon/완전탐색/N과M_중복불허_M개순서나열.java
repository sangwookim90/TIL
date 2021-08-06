package baekjoon.완전탐색;

import java.io.*;
import java.util.StringTokenizer;


/*
    https://www.acmicpc.net/problem/15649

    - 문제
    자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
    1) 1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열

    - 입력
    첫째 줄에 자연수 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

    - 출력
    한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.
    수열은 사전 순으로 증가하는 순서로 출력해야 한다.


 */
public class N과M_중복불허_M개순서나열 {

    /*
        ex) n=4, m=3
        4! 의 경우의 수

        rec_func_using_for 의 for문 추가 사용으로 인한 시간복잡도 증가 => rec_func로 해결

     */

    static int N,M;
    static int[] selected, used;

    static StringBuilder sb = new StringBuilder();

    static void rec_func(int k) {
        if (k == M+1) { // 모두 고름
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i=1; i<=M; i++) {
                sb.append(selected[i]).append(' ');
            }
            sb.append('\n');
        } else {
            for (int cand = 1; cand<=N; cand++) {
                if (used[cand] == 1) continue;
                selected[k] = cand;
                used[cand] = 1;

                rec_func(k+1);

                selected[k] = 0;
                used[cand] = 0;
            }
        }
    }

    static void rec_func_using_for(int k) {
        if (k == M+1) { // 모두 고름
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i=1; i<=M; i++) {
                sb.append(selected[i]).append(' ');
            }
            sb.append('\n');
        } else {
            for (int cand = 1; cand<=N; cand++) {
                boolean isUsed = false;
                for(int i=1; i<k;  i++) {
                    if (cand == selected[i]) {
                        isUsed = true;
                    }
                }

                // k번째에 cand가 올 수 있으면
                if(isUsed == false) {
                    selected[k] = cand;
                    rec_func(k+1);
                    selected[k] = 0;
                }
            }
        }
    }

    static void input() {
        N과M_중복허용_M개순서나열.FastReader scan = new N과M_중복허용_M개순서나열.FastReader();
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M+1];

    }

    public static void main(String[] args) {
        input();

        rec_func(1);
        System.out.println(sb.toString());
    }


    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(new File(s)));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }

        long nextLong() { return Long.parseLong(next()); }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
