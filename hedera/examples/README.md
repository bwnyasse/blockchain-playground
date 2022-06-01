
# Hedera 

![hedera](../hedera.png)

## Create and Transfer Your First NFT ( Non Fungible Token )

Using the Hedera Token Service you can create non-fungible tokens (NFTs). NFTs are uniquely identifiable. On the Hedera network, the token ID represents a collection of NFTs of the same class, and the serial number of each token uniquely identifies each NFT in the class.

### To test it 

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.CreateNFTSample"

### Example output

    Created NFT with token ID 0.0.34928502
    NFT association with Alice's account: SUCCESS
    Treasury balance: {0.0.34928502=1}NFTs of ID 0.0.34928502
    Alice's balance: {0.0.34928502=0}NFTs of ID 0.0.34928502
    NFT transfer from Treasury to Alice: SUCCESS
    Treasury balance: {0.0.34928502=0}NFTs of ID 0.0.34928502
    Alice's balance: {0.0.34928502=1}NFTs of ID 0.0.34928502

## Create and Transfer Your First Fungible Token

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

## Schedule Your First Transaction

TODO

## Deploy Your First Smart Contract


### To compile solidity input 

    docker run -v $PWD/src/main/resources/solidity:/sources \
        ethereum/solc:stable \
        -o /sources/output \
        --abi --overwrite \
        --bin /sources/input/HelloHedera.sol


### To test it 

    mvn compile exec:java -Dexec.mainClass="net.bwnyasse.DeployFirstSmartContratSample"



TODO

## Deploy a Contract Using the Hedera Token Service

TODO