package br.com.tmvolpato.blockchain.configuration;

import br.com.tmvolpato.blockchain.configuration.properties.BlockchainProperty;
import br.com.tmvolpato.blockchain.configuration.properties.ContractProperty;
import br.com.tmvolpato.blockchain.configuration.properties.WalletProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    private final BlockchainProperty blockchainProperty;
    private final ContractProperty contractProperty;
    private final WalletProperty walletProperty;

    public Web3jConfig(final BlockchainProperty blockchainProperty, final ContractProperty contractProperty, final WalletProperty walletProperty) {
        this.blockchainProperty = blockchainProperty;
        this.contractProperty = contractProperty;
        this.walletProperty = walletProperty;
    }

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(this.blockchainProperty.getUrl()));
    }

    @Bean
    public long chainId() {
        return this.blockchainProperty.getChainId();
    }

    @Bean
    public String myWalletAddress() {
        return this.contractProperty.getMyWalletAddress();
    }

    @Bean
    public String ownerWalletPrivateKey() {
        return this.walletProperty.getPrivateKey();
    }
}
