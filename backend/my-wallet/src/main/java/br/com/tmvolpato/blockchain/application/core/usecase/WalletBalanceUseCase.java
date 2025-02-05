package br.com.tmvolpato.blockchain.application.core.usecase;

import br.com.tmvolpato.blockchain.application.port.inbound.WalletBalanceInputPort;
import br.com.tmvolpato.blockchain.application.port.outbound.FindBalanceByWalletAddressOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.math.BigDecimal;

import static br.com.tmvolpato.blockchain.application.core.utils.ConvertWeiOrEtherUtil.toEther;
import static org.springframework.util.StringUtils.hasLength;

public class WalletBalanceUseCase implements WalletBalanceInputPort {

    private static final Logger LOG = LoggerFactory.getLogger(WalletBalanceUseCase.class);

    private final FindBalanceByWalletAddressOutputPort findBalanceByWalletAddressOutputPort;

    public WalletBalanceUseCase(final FindBalanceByWalletAddressOutputPort findBalanceByWalletAddressOutputPort) {
        this.findBalanceByWalletAddressOutputPort = findBalanceByWalletAddressOutputPort;
    }

    @Override
    @QueryMapping(name = "balanceOf")
    public BigDecimal execute(final String walletAddress) {
        LOG.info("Find balance by wallet address: [{}]", walletAddress);

        if (!hasLength(walletAddress)) {
            throw new IllegalArgumentException("Wallet address cannot be null or empyt");
        }

        final var balance = findBalanceByWalletAddressOutputPort.execute(walletAddress);
        return toEther(balance);
    }
}
