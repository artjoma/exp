#!/usr/bin/env bash
#generate Customer.abi and Customer.bin
solc customer.sol --bin --abi --optimize -o . --overwrite
#gnerate java src. You can add to path web3j.jar
java -cp "../*" org.web3j.codegen.SolidityFunctionWrapperGenerator Customer.bin Customer.abi -o ../../../main/java -p com.tjoma.contract