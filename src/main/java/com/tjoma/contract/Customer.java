package com.tjoma.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.2.2.
 */
public final class Customer extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b604051610423380380610423833981016040528051015b60008054600160a060020a03191633600160a060020a03161790558051610051906001906020840190610059565b505b506100f9565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061009a57805160ff19168380011785556100c7565b828001600101855582156100c7579182015b828111156100c75782518255916020019190600101906100ac565b5b506100d49291506100d8565b5090565b6100f691905b808211156100d457600081556001016100de565b5090565b90565b61031b806101086000396000f300606060405263ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416638da5cb5b8114610050578063ba681d0d14610089578063d32ac670146100e1575bfe5b341561005857fe5b610060610171565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b341561009157fe5b6100df600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965061018d95505050505050565b005b34156100e957fe5b6100f16101a5565b604080516020808252835181830152835191928392908301918501908083838215610137575b80518252602083111561013757601f199092019160209182019101610117565b505050905090810190601f1680156101635780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60005473ffffffffffffffffffffffffffffffffffffffff1681565b80516101a090600190602084019061023d565b505b50565b6101ad6102bc565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102325780601f1061020757610100808354040283529160200191610232565b820191906000526020600020905b81548152906001019060200180831161021557829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061027e57805160ff19168380011785556102ab565b828001600101855582156102ab579182015b828111156102ab578251825591602001919060010190610290565b5b506102b89291506102ce565b5090565b60408051602081019091526000815290565b61023a91905b808211156102b857600081556001016102d4565b5090565b905600a165627a7a7230582082364f387ceec0afa8413ec305977744205e28fa8f9fd91c86180f9db89d21450029";

    private Customer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Customer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<Address> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setFirstname(Utf8String _firstname) {
        Function function = new Function("setFirstname", Arrays.<Type>asList(_firstname), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Utf8String> getFirstname() {
        Function function = new Function("getFirstname", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<Customer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Utf8String _firstname) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_firstname));
        return deployAsync(Customer.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Future<Customer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, Utf8String _firstname) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_firstname));
        return deployAsync(Customer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static Customer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Customer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Customer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Customer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
