## [ERC20 Token Standard](https://eips.ethereum.org/EIPS/eip-20)

1- **A token** is designed to represent something of value but also things like voting rights or
discount vouchers. **It can represent any fungible trading good;**

2- **ERC** stands for **Ethereum Request for Comments**. An ERC is a form of proposal and its
purpose is to define standards and practices;

3- **EIP** stands for **Ethereum Improvement Proposal** and makes changes to the actual code
of Ethereum. **ERC is just guidance on how to use different features of Ethereum.**

4- **ERC20** is **a proposal that intends to standardize** how a token contract should be
defined, how we interact with such a token contract and how these contracts interact
with each other.

5- **ERC20 is a standard interface** used by applications such as wallets, decentralized
exchanges, and so on to interact with tokens;

6- We need a token standard for **interoperability**

7- **We use the same wallet** in which we store Ether to buy, sell or transfer a token, but we
are actually interacting with a contract.

8- **A token holder has full control and complete ownership of their tokens.** The token’s
contract keeps track of token ownership in the same way the Ethereum network keeps
track of who owns ETH;

9- There are tokens that are fully-ERC20-compliant and tokens that are only
partially-ERC20-compliant;

10- A full compatible **ERC20 Token must implement 6 functions and 2 events.**

11- There are thousands of ERC20 token contracts defined: https://etherscan.io/tokens

## ERC20 Tokens - allowed, transferFrom() and approve()

1- **transfer()** function is used to send tokens from one user to another, but it doesn’t
work well when tokens are being used to pay for a function in a smart contract;

2- **ERC20** standard defines a mapping data structure named **allowed** and 2 functions
`approve(...)` and `transferFrom(...)` that permit a token owner to **give another address
approval to transfer up to a number of tokens** known as allowance;

3- Allowances for an address can only be set by the owner of that address;

Imagine there are 2 users **A** and **B**. **A has 1000 tokens and wants to give permission to B to spend 100 of them.**


- **A** will call `approve(address_of_B, 100)`. After that the allowed data structure will
contain the following information: `allowed[address_of_A][address_of_B] = 100`

- If **B** wants later to transfer 20 tokens from **A** to his account, **B** will execute the
`transferFrom()` function in this way: `transferFrom(address_of_A, address_of_B, 20)`.

After calling the `transferFrom()` function (by B) the balance of A decreased by 20
and the balance of B increased by 20 tokens and the allowed mapping will contain
the following info: `allowed[address_of_A][address_of_B] = 80`
