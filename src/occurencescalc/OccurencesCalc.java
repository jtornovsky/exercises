package occurencescalc;

import java.util.*;

public class OccurencesCalc {

    public static void main(String[] args) {

        OccurencesCalc occurencesCalc = new OccurencesCalc();

        int[] numbers1 = {5, 6, 1, 6, 7};
        int[] numbers2 = {5, 7, 8, 1, 8, 1, 7, 4};
        int[] numbers3 = {5, 7, 8, 1, 8, 8, 7, 4, 5, 8, 1, 1, 1, 1};
        int[] numbers4 = {8, 5, 8, 1, 8, 8, 7, 4, 5, 5, 1, 1, 5, 1};

        int[] sortedNumbers1 = occurencesCalc.sortNumbersByOccurrences(numbers1);
        int[] sortedNumbers2 = occurencesCalc.sortNumbersByOccurrences(numbers2);
        int[] sortedNumbers3 = occurencesCalc.sortNumbersByOccurrences(numbers3);
        int[] sortedNumbers4 = occurencesCalc.sortNumbersByOccurrences(numbers4);

        System.out.println(Arrays.toString(sortedNumbers1)); // Output: [6, 6, 5, 1, 7]
        System.out.println(Arrays.toString(sortedNumbers2)); // Output: [7, 7, 8, 8, 1, 1, 5, 4]
        System.out.println(Arrays.toString(sortedNumbers3)); // Output: [1, 1, 1, 1, 1, 8, 8, 8, 8, 5, 5, 7, 7, 4]
        System.out.println(Arrays.toString(sortedNumbers4)); // Output: [8, 8, 8, 8, 5, 5, 5, 5, 1, 1, 1, 1, 7, 4]
    }

    class NumberStat {
        int number;
        int numberOfOccurs;
        int position;

        NumberStat(int number, int numberOfOccurs, int position) {
            this.number = number;
            this.numberOfOccurs = numberOfOccurs;
            this.position = position;
        }
    }

    public int[] sortNumbersByOccurrences(int[] numbers) {

        Map<Integer, NumberStat> accumulatorMap = new HashMap();

        // here is the loop we started from - accumulating the numbers and their stats:
        // how many times appears and the first position
        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            NumberStat numberStat;
            // didn't use here getOrDefault just to be more clear
            if (accumulatorMap.containsKey(number)) {
                numberStat = accumulatorMap.get(number);
                numberStat.numberOfOccurs++;
            } else {
                numberStat = new NumberStat(number, 1, i);
            }
            accumulatorMap.put(number, numberStat);
        }

        // sort an accumulated values refer the exercise policy
        List<NumberStat> numberStatList = new ArrayList<>(accumulatorMap.values());
        numberStatList.sort((o1, o2) -> {
            if (o1.numberOfOccurs != o2.numberOfOccurs) {
                // sort by number of occurrences first
                return Integer.compare(o2.numberOfOccurs, o1.numberOfOccurs);
            } else {
                // in case of same occurrences, sort by first appearance
                return Integer.compare(o1.position, o2.position);
            }
        });

        int[] result = new int[numbers.length];
        int idx = 0;
        // here just deflate the number as many as it appears in the input.
        for (NumberStat numberStat : numberStatList) {
            for (int j = 0; j < numberStat.numberOfOccurs; j++) {
                result[idx++] = numberStat.number;
            }
        }

        return result;
    }
}
