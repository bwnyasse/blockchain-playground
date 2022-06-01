
# Hedera 

![hedera](../hedera.png)

## 0 - Compile the whole project

I am using [Apache Maven](https://maven.apache.org/) to `compile` and `run` my samples. 

To build the project : 

    mvn clean package

I don't commit my `.env` file. So if you want to use this folder as example, you must follow [the introduction guide provided by Hedera](https://docs.hedera.com/guides/getting-started/introduction).

## 1- Create and Transfer Your First NFT ( Non Fungible Token )

Using the Hedera Token Service you can create non-fungible tokens (NFTs). NFTs are uniquely identifiable. On the Hedera network, the token ID represents a collection of NFTs of the same class, and the serial number of each token uniquely identifies each NFT in the class.

### To test it :

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.CreateNFTSample"

### Example output :

    Created NFT with token ID 0.0.34928502
    NFT association with Alice's account: SUCCESS
    Treasury balance: {0.0.34928502=1}NFTs of ID 0.0.34928502
    Alice's balance: {0.0.34928502=0}NFTs of ID 0.0.34928502
    NFT transfer from Treasury to Alice: SUCCESS
    Treasury balance: {0.0.34928502=0}NFTs of ID 0.0.34928502
    Alice's balance: {0.0.34928502=1}NFTs of ID 0.0.34928502

## 2- Create and Transfer Your First Fungible Token

Fungible tokens share a single set of properties and have interchangeable value with one another. Use cases for fungible tokens include applications like stablecoins, in-game rewards systems, crypto tokens, loyalty program points, and much more.

### To test it 

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.CreateFungibleSample"

### Example output

    Created token with ID: 0.0.34960559
    Token association with Alice's account: SUCCESS
    Treasury balance: {0.0.34960559=10000} units of token ID0.0.34960559
    Alice's balance: {0.0.34960559=0} units of token ID 0.0.34960559
    Stablecoin transfer from Treasury to Alice: SUCCESS
    Treasury balance {0.0.34960559=7500} units of token ID 0.0.34960559
    Alice's balance: {0.0.34960559=2500} units of token ID 0.0.34960559

## 3- Schedule Your First Transaction

TODO

## 4- Deploy Your First Smart Contract


### To compile solidity input 

My **solidity files** are in `src/main/resources/solidity`. To compile the `.sol` files, 
I am using the **solidity compiler** provided as **docker image** :  

    docker run -v $PWD/src/main/resources/solidity:/sources \
        ethereum/solc:stable \
        -o /sources/output \
        --abi --overwrite \
        --bin /sources/input/HelloHedera.sol


### To test it 

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.DeployFirstSmartContratSample"

### Example output

    The smart contract bytecode file ID is 0.0.34961788
    The smart contract ID is 0.0.34961789
    The contract message: Hello from Hedera asking by Me!
    The transaction status is SUCCESS
    The contract updated message: Hello from Hedera again!

## 5- Deploy a Contract Using the Hedera Token Service


### To compile solidity input 

My **solidity files** are in `src/main/resources/solidity`. To compile the `.sol` files, 
I am using the **solidity compiler** provided as **docker image** :  

    docker run -v $PWD/src/main/resources/solidity:/sources \
        ethereum/solc:stable \
        -o /sources/output \
        --abi --overwrite \
        --bin /sources/input/HTS.sol

### To test it 

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.DeployContractWithHTS"

### Example output

    The transaction status: SUCCESS
    The transaction record for the associate transactionTransactionRecord{receipt=TransactionReceipt{status=SUCCESS, exchangeRate=ExchangeRate{hbars=0, cents=0, expirationTime=1970-01-01T00:00:00Z, exchangeRateInCents=NaN}, accountId=null, fileId=null, contractId=null, topicId=null, tokenId=null, topicSequenceNumber=null, topicRunningHash=null, totalSupply=0, scheduleId=null, scheduledTransactionId=null, serials=[], duplicates=[], children=[]}, transactionHash=63f08a1b3ef2d30ada55bc923a5de1c67fd7a2735f02fffcb6a9d298d76aa2fcb6ca4612dc18b8366f742dfc13341268, consensusTimestamp=2022-06-01T23:10:15.632707230Z, transactionId=0.0.34913282@1654125004.457672399/1, transactionMemo=, transactionFee=0 tℏ, contractFunctionResult=ContractFunctionResult{contractId=0.0.359, evmAddress=null, errorMessage=null, bloom=, gasUsed=706076, logs=[], createdContractIds=[], stateChanges=[], gas=1943732, hbarAmount=0 tℏ, contractFunctionparametersBytes=49146bde000000000000000000000000000000000000000000000000000000000214bc020000000000000000000000000000000000000000000000000000000002157a52, rawResult=0000000000000000000000000000000000000000000000000000000000000016, senderAccountId=null}, transfers=[], tokenTransfers={}, tokenNftTransfers={}, scheduleRef=null, assessedCustomFees=[], automaticTokenAssociations=[], aliasKey=null, children=[], duplicates=[], parentConsensusTimestamp=2022-06-01T23:10:15.632707229Z, ethereumHash=63f08a1b3ef2d30ada55bc923a5de1c67fd7a2735f02fffcb6a9d298d76aa2fcb6ca4612dc18b8366f742dfc13341268}
    The 0.0.34962002 should now be associated to my account: {0.0.34962002=0}