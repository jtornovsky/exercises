package meliopayments;

class Transfer {
    double amount;
    String sourceAccountId;
    String destinationAccountId;
    PaymentMethodType paymentMethodType;
    Flow flow;

    public Transfer(double amount, String sourceAccountId, String destinationAccountId, PaymentMethodType paymentMethodType, Flow flow) {
        this.amount = amount;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.paymentMethodType = paymentMethodType;
        this.flow = flow;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "amount=" + amount +
                ", sourceAccountId='" + sourceAccountId + '\'' +
                ", destinationAccountId='" + destinationAccountId + '\'' +
                ", paymentMethodType=" + paymentMethodType +
                ", flow=" + flow +
                '}';
    }
}
