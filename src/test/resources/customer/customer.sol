pragma solidity ^0.4.11;

//Simple contract example
contract Customer {
    address public owner;
    string private firstname;

    //Constructor may be empty
    function Customer(string _firstname) {
        owner = msg.sender;
        firstname = _firstname;
    }
    //Always invoke setters or method that change the ETH state methods only with transactions
    function setFirstname(string _firstname) {
        firstname = _firstname;
    }
    //Getters always called(without transaction)
    function getFirstname() constant returns (string) {
       return firstname;
    }
}