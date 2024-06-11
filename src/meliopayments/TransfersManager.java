package meliopayments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TransfersManager {

    public List<Transfer> createTransfersFromPayment(Payment payment) {
        ConditionsManager conditionsManager = new ConditionsManager(payment, new Date());
        MelioBank melioBank = conditionsManager.getMelioBank();
        List<Transfer> transfersForPayment = new ArrayList<>(2);
        Transfer transfer1 = new Transfer(payment.amount, payment.collect.accountId, melioBank.name(), payment.collect.paymentMethodType, Flow.Collect);
        Transfer transfer2 = new Transfer(payment.amount, melioBank.name(), payment.deliver.accountId, payment.deliver.paymentMethodType, Flow.Deliver);
        transfersForPayment.add(transfer1);
        transfersForPayment.add(transfer2);
        return transfersForPayment;
    }
}
