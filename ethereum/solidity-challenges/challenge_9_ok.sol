//SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.5.0 <0.9.0;

/*
Declare a function that concatenates two strings.

Note: Since Solidity does not offer a native way to concatenate 
strings use abi.encodePacked() to do that. 
Read the official doc for examples.

https://docs.soliditylang.org/en/latest/abi-spec.html

*/
contract A {
    string public val1 = "aaa";
    string public val2 = "bbb";
    string public new_str;

    function concatenate_1() public view returns (string memory) {
        string memory value;
        value = string(abi.encodePacked(val1, val2));
        return value;
    }

    function concatenate_2() public {
        new_str = string(abi.encodePacked(val1, val2));
    }
}
