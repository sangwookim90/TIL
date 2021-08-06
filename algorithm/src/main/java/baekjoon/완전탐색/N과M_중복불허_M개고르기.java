package baekjoon.완전탐색;

import java.io.*;
import java.util.StringTokenizer;

/*
    https://www.acmicpc.net/problem/15650

    - 문제
    자연수 N과 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
    1) 1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열
    2) 고른 수열은 오름차순이어야 한다.

    - 입력
    첫째 줄에 자연수 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

    - 출력
    한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.
    수열은 사전 순으로 증가하는 순서로 출력해야 한다.

 */
public class N과M_중복불허_M개고르기 {

    static int N, M;
    static int[] selected;

    static StringBuilder sb = new StringBuilder();

    static void rec_func(int k) {
        if (k == M+1) { // 모두 고름
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i=1; i<=M; i++) {
                sb.append(selected[i]).append(' ');
            }
            sb.append('\n');
        } else {
            for (int cand = selected[k-1] + 1; cand<=N; cand++) {
                selected[k] = cand;
                // k+1번 ~ M번을 모두 탐색하는 일을 해야하는 상황
                rec_func(k+1);
                selected[k] = 0;
            }
        }
    }


    static void input() {
        FastReader scan = new FastReader();
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
