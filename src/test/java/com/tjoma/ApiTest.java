package com.tjoma;

import com.tjoma.contract.Customer;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bouncycastle.util.encoders.Hex;
import org.junit.BeforeClass;
import org.junit.Test;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Before start see doc: https://github.com/web3j/web3j
 * @author ArtjomAminov
 */
public class ApiTest {
    private static Web3j web3;
    private static Credentials credSender;
    //gas price 1wei, depends on network. For private network we can use 1wei
    private static final BigInteger GAS_PRICE = BigInteger.ONE;

    @BeforeClass
    public static void init (){
        //address with money
        credSender = Credentials.create("123176c1314e222721e619e1b38a0c6ec81fdc60e73ab039f5f4f73ea992d17d");

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setConnectionTimeToLive(20, TimeUnit.MINUTES);
        clientBuilder.setConnectionManagerShared(true);
        clientBuilder.setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE);
        SocketConfig.Builder socketConfig = SocketConfig.custom();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoTimeout(2_000);
        socketConfig.setSoReuseAddress(true);
        socketConfig.setRcvBufSize(1024);
        socketConfig.setSndBufSize(1024);
        clientBuilder.setDefaultSocketConfig(socketConfig.build());

        ConnectionConfig.Builder config = ConnectionConfig.custom();
        config.setBufferSize(1024);

        clientBuilder.setDefaultConnectionConfig(config.build());

        web3 = Web3j.build(new HttpService("http://127.0.0.1:8199", clientBuilder.build()));

    }

    /**
     * Generate private keys
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    @Test
    public void generateAddress () throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException {
        for (int i=0; i<10; i++)   {
            ECKeyPair pair = Keys.createEcKeyPair();
            System.out.println(Keys.getAddress(pair) + "/" + Numeric.toHexStringNoPrefix(pair.getPrivateKey()));
        }

    }

    /**
     * Get some block
     * @throws IOException
     */
    @Test
    public void getBlock () throws IOException {
        Request<?,EthBlock> request = web3.ethGetBlockByHash(
                "0x07cc5552cc0fe52f61e8cd979b1a14950e706a16b30c47168317293021025383", false);
        EthBlock block = request.send();
        if (block.getError() == null){
            System.out.println(block.getBlock().getGasLimit());
        }else {
            System.out.println(block.getError().getMessage());
        }
    }

    @Test
    public void getBalance () throws IOException {
        EthGetBalance response = web3.ethGetBalance("0xe7e90d1901f9e9b341e0f71f314cc507f315e662",DefaultBlockParameterName.LATEST).send();
        if (response.getError() == null){
            System.out.println(response.getBalance());
        }else {
            System.out.println(response.getError());
        }
    }

    /**
     * Create raw tx, sign and publish
     * @throws InterruptedException
     * @throws TransactionTimeoutException
     * @throws IOException
     * @throws ExecutionException
     */
    @Test
    public void sendRawTx() throws InterruptedException, TransactionTimeoutException, IOException, ExecutionException {
        System.out.println("to address: " + credSender.getAddress());
        String toAddress = "3c2f2d61c1af6a5e10a3de79f5969c41b58f0c3f";
        // get the next available nonce
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
                credSender.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println("Address none: " + nonce);
        // create raw transaction
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce, GAS_PRICE, Transfer.GAS_LIMIT, toAddress, BigInteger.valueOf(10));
        // sign
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credSender);
        String hexValue = "0x" + Hex.toHexString(signedMessage);
        System.out.println("Signed tx: " + hexValue);
        //send transaction
        EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
        if (ethSendTransaction.getError() == null){
            System.out.println("result: " + ethSendTransaction.getTransactionHash());
        }else{
            System.out.println("result err: " + ethSendTransaction.getError().getCode() + " " +
                    ethSendTransaction.getError().getMessage());
        }
    }

    /**
     * Deploy contract
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void deployContract () throws ExecutionException, InterruptedException {
        System.out.println("Start deploy contract");
        Customer customerContract = Customer.deploy(web3, credSender, GAS_PRICE,
                BigInteger.valueOf(330_000), BigInteger.ZERO, new Utf8String("Tjoma")).get();
        //copy contract address and update methods: "changeContractValue" & "callContract"
        System.out.println("End deploy contract. addr: " + customerContract.getContractAddress());
        System.out.println("Tx: " + customerContract.getTransactionReceipt().get().getTransactionHash());
    }

    /**
     * Change contract state
     */
    @Test
    public void changeContractValue () throws ExecutionException, InterruptedException {
        System.out.println("Start publish tx");
        //use contract
        Customer contract = Customer.load("0x8cd2b20c62614b86bc55d6e081705d6421252473",
            web3, credSender, GAS_PRICE, BigInteger.valueOf(40_000));
        //for write we use transaction
        TransactionReceipt rec = contract.setFirstname(new Utf8String("SomeName")).get();
        System.out.println("Tx:" + rec.getTransactionHash());
    }

    /**
     * Read value from contract
     */
    @Test
    public void callContract() throws ExecutionException, InterruptedException {
        Customer contract = Customer.load("0x8cd2b20c62614b86bc55d6e081705d6421252473",
                web3, credSender, GAS_PRICE, BigInteger.ZERO);
        //call, get data without transaction (read)
        String firstname = contract.getFirstname().get().getValue();
        System.out.println("Contract data: " + firstname);
    }


}
