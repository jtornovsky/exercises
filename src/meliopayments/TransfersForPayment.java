package meliopayments;

import java.util.*;

public class TransfersForPayment {

    public static void main(String[] args) {
        TransfersForPayment transfersForPayment = new TransfersForPayment();
        transfersForPayment.createTransfersForPayment();
    }


    public void createTransfersForPayment() {
        Set<Payment> payments = new HashSet<>();
        Map<Payment, List<Transfer>> transfersMap = new HashMap<>();
        TransfersManager transfersManager = new TransfersManager();

        // ------------- seeding payments
        payments.add(new Payment(25.73, new Collect(PaymentMethodType.Check, "123"), "NY", new Deliver(PaymentMethodType.Check, "456")));
        payments.add(new Payment(50.00, new Collect(PaymentMethodType.Check, "124"), "TX", new Deliver(PaymentMethodType.VirtualCard, "457")));
        payments.add(new Payment(100.00, new Collect(PaymentMethodType.Card, "125"), "CA", new Deliver(PaymentMethodType.International, "458")));
        payments.add(new Payment(75.50, new Collect(PaymentMethodType.Check, "126"), "TX", new Deliver(PaymentMethodType.Check, "459")));
        payments.add(new Payment(60.25, new Collect(PaymentMethodType.Ach, "127"), "FL", new Deliver(PaymentMethodType.Check, "460")));
        payments.add(new Payment(80.80, new Collect(PaymentMethodType.Card, "128"), "TX", new Deliver(PaymentMethodType.VirtualCard, "461")));
        payments.add(new Payment(45.45, new Collect(PaymentMethodType.Check, "129"), "NY", new Deliver(PaymentMethodType.International, "462")));
        payments.add(new Payment(110.10, new Collect(PaymentMethodType.Ach, "130"), "CA", new Deliver(PaymentMethodType.Check, "463")));
        payments.add(new Payment(35.35, new Collect(PaymentMethodType.Card, "131"), "TX", new Deliver(PaymentMethodType.VirtualCard, "464")));
        payments.add(new Payment(90.90, new Collect(PaymentMethodType.Check, "132"), "FL", new Deliver(PaymentMethodType.International, "465")));
        payments.add(new Payment(120.45, new Collect(PaymentMethodType.VirtualCard, "133"), "NY", new Deliver(PaymentMethodType.Card, "466")));

        // ------------ creating transfers
        for (Payment payment : payments) {
            List<Transfer> transfersForPayment = transfersManager.createTransfersFromPayment(payment);
            transfersMap.put(payment, transfersForPayment);
        }

        // ------------ printing transactions
        for (Payment payment : payments) {
            System.out.println("--- Payment ---");
            System.out.println(payment);
            System.out.println("--- Transfers ---");
            transfersMap.get(payment).forEach(System.out::println);
        }
    }
}
