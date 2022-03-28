package coronaManagement;

import java.util.Arrays;

public class test {

    public static int solution(String[][] rounds) {
        int result = 0;
        boolean isCouple = false;
        String[] temp = new String[4];

        for (int i = 0; i < rounds.length; i++) {
            for (int j = 0; j < 4; j++) {
                String s = Integer.toString(j + 97); //지목하는 사람 (a,b,c,d)

                if (s.equals(rounds[i][j])) {
                    result++;
                    break;

                } else {
                    temp[Integer.parseInt(rounds[i][j]) - 97] = s;

                    if (!temp[j].equals(null)) {

                    }
                }
            }

            Arrays.stream(temp).forEach(t -> t = null);
        }

        return result;
    }

    public static void main(String[] args) {
        String[][] rounds = new String[3][4];

        int result = solution(rounds);
        System.out.println(result);
    }
}
