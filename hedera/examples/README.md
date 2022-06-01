
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

TODO