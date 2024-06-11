package meliopayments;

class Transaction {
    final Flow flow;
    final PaymentMethodType paymentMethodType;
    final String accountId;

    public Transaction(Flow flow, PaymentMethodType paymentMethodType, String accountId) {
        this.flow = flow;
        this.paymentMethodType = paymentMethodType;
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "flow=" + flow +
                ", paymentMethodType=" + paymentMethodType +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
