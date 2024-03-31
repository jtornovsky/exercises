package sum;

public class Sum {
    private int total = 0;

    public Sum(int n) {
        this.total = n;
    }

    public Sum sum(int n) {
        total += n;
        return this;
    }

    public int calculate() {
        return total;
    }

    public static void main(String[] args) {
        int result = new Sum(1).sum(34).sum(5).sum(108).calculate();
        System.out.println(result); // Output: 148
    }
}


