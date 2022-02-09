//SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.5.0 <0.9.0;

/*
Challenges:

- Add a public state variable of type address called owner.

- Declare the constructor and initialize all the state variables 
in the constructor. The owner should be initialized with the address 
of the account that deploys the contract.
*/
contract CryptosToken {
    string public constant name = "Cryptos";
    uint256 supply;
    address public owner;

    constructor(uint256 s) {
        supply = s;
        owner = msg.sender;
    }

    function getSupply() public view returns (uint256) {
        return supply;
    }

    function setSupply(uint256 _supply) public {
        supply = _supply;
    }
}
