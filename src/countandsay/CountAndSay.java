package countandsay;

public class CountAndSay {

    public static void main(String[] args) {
        CountAndSaySolution countAndSaySolution = new CountAndSaySolution();
        System.out.println(countAndSaySolution.countAndSay(1));
        System.out.println(countAndSaySolution.countAndSay(2));
        System.out.println(countAndSaySolution.countAndSay(3));
        System.out.println(countAndSaySolution.countAndSay(4));
        System.out.println(countAndSaySolution.countAndSay(5));
        System.out.println(countAndSaySolution.countAndSay(6));
    }
}

class CountAndSaySolution {
    public String countAndSay(int n) {

        StringBuilder result = new StringBuilder("1");

        for (int idx = 1; idx < n; idx++) {
            result = generateNextTerm(result.append("-"));
        }

        return result.toString();
    }

    private StringBuilder generateNextTerm(StringBuilder str) {

        StringBuilder generatedTerm = new StringBuilder();;
        int consecutiveCharCount = 1;

        for (int strIdx = 0; strIdx < str.length()-1; strIdx++) {

            char ch = str.charAt(strIdx);
            char nextCh = str.charAt(strIdx+1);

            if (ch == nextCh) {
                consecutiveCharCount++;
            } else {
                generatedTerm.append(consecutiveCharCount).append(ch);
                consecutiveCharCount = 1;
            }
        }
        return generatedTerm;
    }
}
