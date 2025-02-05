package br.com.tmvolpato.blockchain.adapter.outbound.blockchain.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MyWallet extends Contract {
    public static final String BINARY = "0x6080604052348015600f57600080fd5b5061066b8061001f6000396000f3fe6080604052600436106100345760003560e01c80632e1a7d4d1461003957806370a0823114610062578063d0e30db01461009f575b600080fd5b34801561004557600080fd5b50610060600480360381019061005b919061039b565b6100a9565b005b34801561006e57600080fd5b5061008960048036038101906100849190610426565b61022e565b6040516100969190610462565b60405180910390f35b6100a7610276565b005b806000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101561012a576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610121906104da565b60405180910390fd5b806000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282546101789190610529565b925050819055503373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050501580156101c5573d6000803e3d6000fd5b503373ffffffffffffffffffffffffffffffffffffffff163073ffffffffffffffffffffffffffffffffffffffff167f9b1bfa7fa9ee420a16e124f794c35ac9f90472acc99140eb2f6447c714cad8eb836040516102239190610462565b60405180910390a350565b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b600034116102b9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102b0906105a9565b60405180910390fd5b346000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825461030791906105c9565b925050819055503373ffffffffffffffffffffffffffffffffffffffff167f5548c837ab068cf56a2c2479df0882a4922fd203edb7517321831d95078c5f62303460405161035692919061060c565b60405180910390a2565b600080fd5b6000819050919050565b61037881610365565b811461038357600080fd5b50565b6000813590506103958161036f565b92915050565b6000602082840312156103b1576103b0610360565b5b60006103bf84828501610386565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006103f3826103c8565b9050919050565b610403816103e8565b811461040e57600080fd5b50565b600081359050610420816103fa565b92915050565b60006020828403121561043c5761043b610360565b5b600061044a84828501610411565b91505092915050565b61045c81610365565b82525050565b60006020820190506104776000830184610453565b92915050565b600082825260208201905092915050565b7f496e73756666696369656e742062616c616e6365000000000000000000000000600082015250565b60006104c460148361047d565b91506104cf8261048e565b602082019050919050565b600060208201905081810360008301526104f3816104b7565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061053482610365565b915061053f83610365565b9250828203905081811115610557576105566104fa565b5b92915050565b7f56616c7565206d7573742062652067726561746572207468616e203000000000600082015250565b6000610593601c8361047d565b915061059e8261055d565b602082019050919050565b600060208201905081810360008301526105c281610586565b9050919050565b60006105d482610365565b91506105df83610365565b92508282019050808211156105f7576105f66104fa565b5b92915050565b610606816103e8565b82525050565b600060408201905061062160008301856105fd565b61062e6020830184610453565b939250505056fea264697066735822122011252091762febd25da24393c7085937c60e095de4c0ac7b69bdc63e93bbcaae64736f6c634300081c0033\n";

    private static String librariesLinkedBinary;

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event DEPOSIT_EVENT = new Event("Deposit", 
            Arrays.asList(new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAW_EVENT = new Event("Withdraw", 
            Arrays.asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    protected MyWallet(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected MyWallet(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DepositEventResponse> getDepositEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().getFirst().getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DepositEventResponse getDepositEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEPOSIT_EVENT, log);
        DepositEventResponse typedResponse = new DepositEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().getFirst().getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DepositEventResponse> depositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(MyWallet::getDepositEventFromLog);
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public static List<WithdrawEventResponse> getWithdrawEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAW_EVENT, transactionReceipt);
        ArrayList<WithdrawEventResponse> responses = new ArrayList<WithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawEventResponse typedResponse = new WithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().getFirst().getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WithdrawEventResponse getWithdrawEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WITHDRAW_EVENT, log);
        WithdrawEventResponse typedResponse = new WithdrawEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().getFirst().getValue();
        return typedResponse;
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(MyWallet::getWithdrawEventFromLog);
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAW_EVENT));
        return withdrawEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account) {
        final Function function = new Function(FUNC_BALANCEOF,
                List.of(new Address(160, account)),
                List.of(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DEPOSIT,
                List.of(),
                Collections.emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                List.of(new Uint256(amount)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static MyWallet load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new MyWallet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MyWallet load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MyWallet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MyWallet> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MyWallet.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<MyWallet> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MyWallet.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

   public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        return Objects.requireNonNullElse(librariesLinkedBinary, BINARY);
    }

    public static class DepositEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger amount;
    }

    public static class WithdrawEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger amount;
    }
}
