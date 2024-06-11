package meliopayments;

class Deliver extends Transaction {
    Deliver(PaymentMethodType paymentMethodType, String accountId) {
        super(Flow.Deliver, paymentMethodType, accountId);
    }
}
