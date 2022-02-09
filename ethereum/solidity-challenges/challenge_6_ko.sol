//SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.5.0 <0.9.0;

/*
Add a new immutable state variable called admin and initialize it with the address of the account that deploys the contract;

Add a restriction so that only the admin can transfer the balance of the contract to another address;

Deploy and test the contract on Rinkeby Testnet.
*/
contract Deposit {

    receive() external payable {}

    function getBalance() public view returns (uint256) {
        return address(this).balance;
    }

    function transferBalance(address payable recipient) public {
        recipient.transfer(getBalance());
    }
}
