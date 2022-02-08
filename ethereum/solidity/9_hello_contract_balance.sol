//SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.5.0 <0.9.0;

contract Deposit {
    address public owner;

    constructor() {
        owner = msg.sender;
    }

    receive() external payable {}

    function getBalance() public view returns (uint256) {
        return address(this).balance;
    }

    // transfering ether from the contract to another address (recipient)
    function transferEther(address payable recipient, uint256 amount)
        public
        returns (bool)
    {
        // checking that only contract owner can send ether from the contract to another address
        require(
            owner == msg.sender,
            "Transfer failed, you are not the owner!!"
        );

        require(
            amount <= getBalance(),
            "Transfer failed, you do not have enough amount !!"
        );

        // transfering the amount of wei from the contract to the recipient address
        // anyone who can call this function have access to the contract's funds
        recipient.transfer(amount);

        return true;
    }
}
