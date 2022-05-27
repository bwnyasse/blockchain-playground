
# Hedera 

![hedera](../hedera.png)

## Create and Transfer Your First NFT

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