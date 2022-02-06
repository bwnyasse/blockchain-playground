//SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.5.0 <0.9.0;

contract Property {
    // declaring state variables saved in contract's storage
    uint256 price; // by default is private
    string public location;

    // can be initialized at declaration or in the constructor only
    address public immutable owner;

    // declaring a constant
    int256 constant area = 100;

    // declaring the constructor
    // is executed only once at contract's deployment
    constructor(uint256 _price, string memory _location) {
        price = _price;
        location = _location;
        owner = msg.sender; // initializing owner to the account's address that deploys the contract
    }

    // getter function, returns a state variable
    // a function declared `view` does not alter the blockchain
    function getPrice() public view returns (uint256) {
        return price;
    }

    // setter function, sets a state variable
    function setPrice(uint256 _price) public {
        int256 a; // local variable saved on stack
        a = 10;
        price = _price;
    }

    function setLocation(string memory _location) public {
        //string types must be declared memory or storage
        location = _location;
    }
}
