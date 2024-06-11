
# Payments Router Coding Interview Question

## Overview

The main premise of Melio is to offer choices in how businesses send and receive money. If a business wants to pay an invoice using a credit card and the vendor wants to receive money via a check, then Melio supports that. However, this requires Melio to store funds on behalf of the users in intermediate bank accounts.

## Step 0

In Melio, we use a transfer entity to represent money movement between a source and a destination, which has the following properties:

- **Amount** (e.g. 27.35)
- **Source account id** (e.g. “123”)
- **Destination account id** (e.g. “456”)
- **Payment method type** - ach/check/card
- **Flow** - “collect” / “deliver”

A source/destination can be a payer’s account, a vendor’s account, and a Melio bank account. For now, you can assume there is only one Melio bank account with id “melio”.

Implement a `createTransfersForPayment` function that receives the payment and returns the transfers. Note that a payment in Melio has two sides, one that collects money from the business and moves it to Melio bank account, and one that delivers money from Melio bank account to the vendor.

### Terminology Glossary

- **Payer** -> The user who is paying Melio
- **Payee** -> The vendor who is receiving funds
- **ACH** -> Bank transfer

### Input (Payment)

```json
{
  "amount": 25.73,
  "collect": {
    "accountId": "123",
    "type": "ach"
  },
  "deliver": {
    "accountId": "456",
    "type": "check"
  }
}
```

### Output (Transfers)

```json
[
  {
    "amount": 25.73,
    "sourceAccountId": "123",
    "destinationAccountId": "melio",
    "paymentMethodType": "ach",
    "flow": "collect"
  },
  {
    "amount": 25.73,
    "sourceAccountId": "melio",
    "destinationAccountId": "456",
    "paymentMethodType": "check",
    "flow": "deliver"
  }
]
```

## Step 1

Now Melio has 3 bank accounts with ids: “melio-A”, “melio-B”, and “melio-C”. Adjust the `createTransfersForPayment` from the previous step to route the payment to the correct bank according to the criteria below:

- Bank C is the only bank that can process payments delivered via Virtual Card (`deliver.type == "virtualCard"`).
- Bank A is the only bank that can process payments originated from Texas (`payerState == "TX"`).
- Bank C is the only bank that can process international payments (`deliver.type == "international"`).
- Bank B is the only bank that can process check payments (`deliver.type == "check"`) after 15:00 (`new Date().getHours() > 14`).

If none of the above criteria apply, then any bank can be chosen randomly.

### [Bonus] Load Balancing

Instead of choosing any bank arbitrarily, make sure that the number of payments that didn’t match any criteria will be distributed between the banks as follows:
- 30% to bank A
- 50% to bank B
- 20% to bank C

For the purpose of this bonus item, assume that the code will run in a single process and without access to a persistence layer (DB / filesystem).

### Notes

- When routing a payment to a bank, both the collect and deliver transfer should use the same bank.
- You can assume that if a payment is applicable for more than one rule, the first rule wins. If none, load balance.
- The solution should be extensible to support more requirements and constraints that will come in the future and also a re-ordering of the rules.

In the diagram below, the payment is routed to bank C since the delivery type is virtual card.

### Input

```json
{
  "amount": 25.73,
  "payerState": "NY",
  "collect": {
    "accountId": "123",
    "type": "ach"
  },
  "deliver": {
    "accountId": "456",
    "type": "virtualCard"
  }
}
```

### Output

```json
[
  {
    "amount": 25.73,
    "sourceAccountId": "123",
    "destinationAccountId": "melio-C",
    "paymentMethodType": "ach",
    "flow": "collect"
  },
  {
    "amount": 25.73,
    "sourceAccountId": "melio-C",
    "destinationAccountId": "456",
    "paymentMethodType": "virtualCard",
    "flow": "deliver"
  }
]
```

## Step 2

In the previous step, we assumed that if a payment is applicable for multiple rules, then the first rule wins. For example, when a payment is both from Texas and of type Virtual Card. Now, let’s assume that this payment cannot be routed through any single bank. In this case, we would like to route the payment through multiple banks between the payer and the payee. Describe how you would address this new requirement and adjust your solution.
