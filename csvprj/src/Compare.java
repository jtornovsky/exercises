import java.util.*;

public class Compare {

    public static void main(String[] args) throws Exception {
        Map<Integer, Person> peopleMap = new TreeMap<>();
        peopleMap.put(2, new Person("Alice", 30));
        peopleMap.put(4, new Person("Alice", 30));
        peopleMap.put(3, new Person("Bob", 25));
        peopleMap.put(1, new Person("Charlie", 35));

//        peopleMap.forEach((k,v) -> System.out.println(v));

        List<Person> personList = new ArrayList<>(peopleMap.values());
        personList.sort(null);
//        personList.forEach(System.out::println);

        Set<Person> personSet = new TreeSet<>(peopleMap.values());
        personSet.forEach(System.out::println);


    }

    static class Person implements Comparable<Person> {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Person other) {
            return Integer.compare(this.age, other.age);
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }
}
