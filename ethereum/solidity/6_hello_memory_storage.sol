// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract UnderstandStorageVsMemory {
    string[] public cities = ["Toulouse", "Paris"];

    function f_memory() public view {
        string[] memory s1 = cities;
        s1[0] = "Berlin"; // Won't change the blockchain
    }

    function f_storage() public {
        string[] storage s1 = cities;
        s1[0] = "Montreal"; // Will change the blockchain
    }
}
