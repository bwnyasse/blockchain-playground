package net.bwnyasse;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;
import com.google.common.io.ByteStreams;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.ContractCallQuery;
import com.hedera.hashgraph.sdk.ContractCreateTransaction;
import com.hedera.hashgraph.sdk.ContractExecuteTransaction;
import com.hedera.hashgraph.sdk.ContractFunctionParameters;
import com.hedera.hashgraph.sdk.ContractFunctionResult;
import com.hedera.hashgraph.sdk.ContractId;
import com.hedera.hashgraph.sdk.FileCreateTransaction;
import com.hedera.hashgraph.sdk.FileId;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import com.hedera.hashgraph.sdk.TransactionReceipt;
import com.hedera.hashgraph.sdk.TransactionResponse;

public class DeployFirstSmartContratSample {

    public static final String SOLIDITY_BIN_OUT = "solidity/output/HelloHedera.bin";

    public static void main(String[] args)
            throws IOException, TimeoutException, PrecheckStatusException, ReceiptStatusException {

        // Create your Hedera testnet client
        Client client = BlockchainPlaygroundHederaCommon.getHederaClientForTestnet();

        //
        // -- Store the Smart Contract Bytecode on Hedera
        //

        // Retrieve the smart contract byte code
        InputStream initialStream = DeployFirstSmartContratSample.class.getClassLoader()
                .getResourceAsStream(SOLIDITY_BIN_OUT);
        byte[] bytecode = ByteStreams.toByteArray(initialStream);

        // Create a file on Hedera and store the hex-encoded bytecode
        FileCreateTransaction fileCreateTx = new FileCreateTransaction()
                // Set the bytecode of the contract
                .setContents(bytecode);

        // Submit the file to the Hedera test network signing with the transaction fee payer key
        // specified with the client
        TransactionResponse submitTx = fileCreateTx.execute(client);

        // Get the receipt of the file create transaction
        TransactionReceipt fileReceipt = submitTx.getReceipt(client);

        // Get the file ID from the receipt
        FileId bytecodeFileId = fileReceipt.fileId;

        // Log the file ID
        System.out.println("The smart contract bytecode file ID is " + bytecodeFileId);


        //
        // -- Deploy a Hedera Smart Contract
        //

        // Instantiate the contract instance
        ContractCreateTransaction contractTx = new ContractCreateTransaction()
                // Set the file ID of the Hedera file storing the bytecode
                .setBytecodeFileId(bytecodeFileId)
                // Set the gas to instantiate the contract
                .setGas(100_000)
                // Provide the constructor parameters for the contract
                .setConstructorParameters(
                        new ContractFunctionParameters()
                                .addString("Hello from Hedera asking by Me!"));

        // Submit the transaction to the Hedera test network
        TransactionResponse contractResponse = contractTx.execute(client);

        // Get the receipt of the file create transaction
        TransactionReceipt contractReceipt = contractResponse.getReceipt(client);

        // Get the smart contract ID
        ContractId newContractId = contractReceipt.contractId;

        // Log the smart contract ID
        System.out.println("The smart contract ID is " + newContractId);

        //
        // -- Call the get_message contract function
        //

        // Calls a function of the smart contract
        ContractCallQuery contractQuery = new ContractCallQuery()
                // Set the gas for the query
                .setGas(100000)
                // Set the contract ID to return the request for
                .setContractId(newContractId)
                // Set the function of the contract to call
                .setFunction("get_message")
                // Set the query payment for the node returning the request
                // This value must cover the cost of the request otherwise will fail
                .setQueryPayment(new Hbar(2));

        // Submit to a Hedera network
        ContractFunctionResult getMessage = contractQuery.execute(client);
        // Get the message
        String message = getMessage.getString(0);

        // Log the message
        System.out.println("The contract message: " + message);

        //
        // -- Call the set_message contract function
        //
        // Create the transaction to update the contract message
        ContractExecuteTransaction contractExecTx = new ContractExecuteTransaction()
                // Set the ID of the contract
                .setContractId(newContractId)
                // Set the gas for the call
                .setGas(100_000)
                // Set the function of the contract to call
                .setFunction("set_message",
                        new ContractFunctionParameters().addString("Hello from Hedera again!"));

        // Submit the transaction to a Hedera network and store the response
        TransactionResponse submitExecTx = contractExecTx.execute(client);

        // Get the receipt of the transaction
        TransactionReceipt receipt2 = submitExecTx.getReceipt(client);

        // Confirm the transaction was executed successfully
        System.out.println("The transaction status is" + receipt2.status);

        // Query the contract for the contract message
        ContractCallQuery contractCallQuery = new ContractCallQuery()
                // Set ID of the contract to query
                .setContractId(newContractId)
                // Set the gas to execute the contract call
                .setGas(100_000)
                // Set the contract function
                .setFunction("get_message")
                // Set the query payment for the node returning the request
                // This value must cover the cost of the request otherwise will fail
                .setQueryPayment(new Hbar(2));

        // Submit the query to a Hedera network
        ContractFunctionResult contractUpdateResult = contractCallQuery.execute(client);

        // Get the updated message
        String message2 = contractUpdateResult.getString(0);

        // Log the updated message
        System.out.println("The contract updated message: " + message2);

        // indicates successful termination
        Runtime.getRuntime().exit(0);
    }
}
