package meliopayments;

class Payment {
    double amount;
    Collect collect;
    String payerState;
    Deliver deliver;

    public Payment(double amount, Collect collect, String payerState, Deliver deliver) {
        this.amount = amount;
        this.collect = collect;
        this.payerState = payerState;
        this.deliver = deliver;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", collect=" + collect +
                ", payerState='" + payerState + '\'' +
                ", deliver=" + deliver +
                '}';
    }
}
