package br.com.tmvolpato.blockchain.adapter.outbound;

import br.com.tmvolpato.blockchain.application.port.outbound.FindBalanceByWalletAddressOutputPort;
import br.com.tmvolpato.blockchain.application.port.outbound.LoadMyWalletContractOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindBalanceByWalletAddressAdapter implements FindBalanceByWalletAddressOutputPort {

    private final LoadMyWalletContractOutputPort loadMyWalletContractOutputPort;

    @Override
    public BigInteger execute(final String walletAddress) {
        log.info("Find balance by wallet address: [{}]", walletAddress);

        final var contract = this.loadMyWalletContractOutputPort.execute();

        try {
            return contract.balanceOf(walletAddress).send();
        } catch (final Exception ex) {
            log.error("Error to find balance by wallet address: [{}]", walletAddress, ex);
            return BigInteger.ZERO;
        }
    }
}
