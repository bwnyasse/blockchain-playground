# Hedera 

## My starting project using the Hedera test network 

This **hello project** follows the [official tutorial](https://docs.hedera.com/guides/getting-started/introduction)

### 1- Checking My Account Balance

To know my actual account balance : 

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.AccountBalanceSample"

### 2- Create Account Sample

- Created new a Hedera account with an initial balance of 1,000 tinybar$

- Obtained the new account ID by requesting the receipt of the transaction

- Verified the starting balance of the new account by submitting a query to the network

#### To test it : 

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.CreateAccountSample"

the output : 

    The new account ID is: 0.0.34920415
    ...


### 3- Transfer hbar

- Set up your Hedera environment to submit transactions and queries

- Created an account

- Transferred hbars to another account
