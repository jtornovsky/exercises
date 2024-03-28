package anagrams;

import java.util.*;

public class GroupAnagrams {

    public static void main(String[] args) {
        GroupAnagramsSolution groupAnagramsSolution = new GroupAnagramsSolution();
        System.out.println(groupAnagramsSolution.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println(groupAnagramsSolution.groupAnagrams(new String[]{""}));
        System.out.println(groupAnagramsSolution.groupAnagrams(new String[]{"a"}));
    }

}

class GroupAnagramsSolution {
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> mappedStrs = new HashMap<>();

        for (String s : strs) {
            String key = generateKey(s);
            if (!mappedStrs.containsKey(key)) {
                mappedStrs.put(key, new ArrayList<>());
            }
            mappedStrs.get(key).add(s);
        }

        return new ArrayList<>(mappedStrs.values());
    }

    private String generateKey(String str) {
        char[] charArray = str.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }
}
