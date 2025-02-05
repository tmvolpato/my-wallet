package br.com.tmvolpato.blockchain.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "smart-contract")
public class ContractProperty {

    private String myWalletContractAddress;

    public String getMyWalletAddress() {
        return myWalletContractAddress;
    }

    public void setMyWalletContractAddress(String myWalletContractAddress) {
        this.myWalletContractAddress = myWalletContractAddress;
    }
}
