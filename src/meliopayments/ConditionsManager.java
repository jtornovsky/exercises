package meliopayments;

import java.util.*;
import java.util.function.Supplier;

class ConditionsManager {

    final PaymentMethodType paymentMethodType;
    final String payerState;
    final Date processTime;

    List<Supplier<Optional<MelioBank>>> conditionFunctions = new ArrayList<>();
    MelioBank melioBank;

    Random random = new Random();

    public ConditionsManager(Payment payment, Date processTime) {
        this.paymentMethodType = payment.deliver.paymentMethodType;
        this.payerState = payment.payerState;
        this.processTime = processTime;

        addConditionFunctions();
    }

    void addConditionFunctions() {
        conditionFunctions.add(this::chooseBankForVirtualCard);
        conditionFunctions.add(this::chooseBankForTexas);
        conditionFunctions.add(this::chooseBankForInternational);
        conditionFunctions.add(this::chooseBankForCheckAfter3PM);
        conditionFunctions.add(this::chooseDefaultMelioBank);
    }

    MelioBank getMelioBank() {
        for (Supplier<Optional<MelioBank>> func : conditionFunctions) {
            Optional<MelioBank> melioBankOptional = func.get();
            if (melioBankOptional.isPresent()) {
                System.out.println("----------------------------------");
                return melioBankOptional.get();
            }
        }
        throw new RuntimeException("No melio bank found");
    }

    Optional<MelioBank> chooseBankForVirtualCard() {

        System.out.println("chooseBankForVirtualCard");

        if (isVirtualCard(paymentMethodType)) {
            return Optional.of(MelioBank.Melio_C);
        }
        return Optional.empty();
    }

    Optional<MelioBank> chooseBankForTexas() {

        System.out.println("chooseBankForTexas");

        if (isFromTexas(payerState)) {
            return Optional.of(MelioBank.Melio_A);
        }
        return Optional.empty();
    }

    Optional<MelioBank> chooseBankForInternational() {

        System.out.println("chooseBankForInternational");

        if (isInternational(paymentMethodType)) {
            return Optional.of(MelioBank.Melio_C);
        }
        return Optional.empty();
    }

    Optional<MelioBank> chooseBankForCheckAfter3PM() {

        System.out.println("chooseBankForCheckAfter3PM");

        if (isCheckAfter3PM(paymentMethodType)) {
            return Optional.of(MelioBank.Melio_B);
        }
        return Optional.empty();
    }

    Optional<MelioBank> chooseDefaultMelioBank() {

        System.out.println("chooseDefaultMelioBank");

        int randomValue = random.nextInt(50);
        if (randomValue < 30) {
            return Optional.of(MelioBank.Melio_A);
        } else if (randomValue < 80) {
            return Optional.of(MelioBank.Melio_B);
        } else {
            return Optional.of(MelioBank.Melio_C);
        }
    }

    boolean isVirtualCard(PaymentMethodType deliverPaymentMethodType) {
        return PaymentMethodType.VirtualCard == deliverPaymentMethodType;
    }

    boolean isFromTexas(String payerState) {
        return "TX".equalsIgnoreCase(payerState);
    }

    boolean isInternational(PaymentMethodType deliverPaymentMethodType) {
        return PaymentMethodType.International == deliverPaymentMethodType;
    }

    boolean isCheckAfter3PM(PaymentMethodType deliverPaymentMethodType) {
        return PaymentMethodType.Check == deliverPaymentMethodType && new Date().getHours() > 14;
    }
}
