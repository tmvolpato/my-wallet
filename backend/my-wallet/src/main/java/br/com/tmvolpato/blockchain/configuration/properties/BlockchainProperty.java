package br.com.tmvolpato.blockchain.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "blockchain-node")
public class BlockchainProperty {

    private String url;
    private long chainId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getChainId() {
        return chainId;
    }

    public void setChainId(long chainId) {
        this.chainId = chainId;
    }
}
