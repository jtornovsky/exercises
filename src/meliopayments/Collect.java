package meliopayments;

class Collect extends Transaction {
    Collect(PaymentMethodType paymentMethodType, String accountId) {
        super(Flow.Collect, paymentMethodType, accountId);
    }
}
