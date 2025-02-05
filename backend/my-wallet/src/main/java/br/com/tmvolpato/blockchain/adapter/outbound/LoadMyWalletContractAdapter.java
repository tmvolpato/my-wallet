package br.com.tmvolpato.blockchain.adapter.outbound;

import br.com.tmvolpato.blockchain.adapter.outbound.blockchain.contract.MyWallet;
import br.com.tmvolpato.blockchain.adapter.outbound.blockchain.provider.CustomGasProvider;
import br.com.tmvolpato.blockchain.application.port.outbound.LoadMyWalletContractOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadMyWalletContractAdapter implements LoadMyWalletContractOutputPort {

    private final Web3j web3j;
    private final String myWalletAddress;
    private final String ownerWalletPrivateKey;
    private final long chainId;

    @Override
    public MyWallet execute() {
        log.info("Loading contract");

        final TransactionManager transactionManager = new FastRawTransactionManager(
                this.web3j, Credentials.create(this.ownerWalletPrivateKey), chainId);
        return MyWallet.load(this.myWalletAddress, this.web3j, transactionManager, this.getContractGasProvider());
    }

    private ContractGasProvider getContractGasProvider() {
        final BigInteger gasPrice = BigInteger.valueOf(35_000_000_000L);
        final BigInteger gasLimit = BigInteger.valueOf(500_000L);
        return new CustomGasProvider(gasPrice, gasLimit);
    }

}
