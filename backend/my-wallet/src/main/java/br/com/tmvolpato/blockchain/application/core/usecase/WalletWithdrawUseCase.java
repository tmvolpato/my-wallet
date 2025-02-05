package br.com.tmvolpato.blockchain.application.core.usecase;

import br.com.tmvolpato.blockchain.application.core.domain.Wallet;
import br.com.tmvolpato.blockchain.application.port.inbound.WalletWithdrawInputPort;
import br.com.tmvolpato.blockchain.application.port.outbound.FindBalanceByWalletAddressOutputPort;
import br.com.tmvolpato.blockchain.application.port.outbound.WalletWithdrawOutputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.MutationMapping;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.List;

import static br.com.tmvolpato.blockchain.application.core.utils.ConvertWeiOrEtherUtil.toEther;
import static br.com.tmvolpato.blockchain.application.core.utils.ConvertWeiOrEtherUtil.toWei;
import static java.util.Objects.isNull;

public class WalletWithdrawUseCase implements WalletWithdrawInputPort {

    private static final Logger LOG = LoggerFactory.getLogger(WalletWithdrawUseCase.class);

    private static final String WALLET_ADDRESS = "0x136E9c27aE7581C31851eE759769644d438061f7";

    private final WalletWithdrawOutputPort walletWithdrawOutputPort;
    private final FindBalanceByWalletAddressOutputPort findBalanceByWalletAddressOutputPort;

    public WalletWithdrawUseCase(final WalletWithdrawOutputPort walletWithdrawOutputPort,
                                 final FindBalanceByWalletAddressOutputPort findBalanceByWalletAddressOutputPort) {
        this.walletWithdrawOutputPort = walletWithdrawOutputPort;
        this.findBalanceByWalletAddressOutputPort = findBalanceByWalletAddressOutputPort;
    }

    @Override
    @MutationMapping(name = "withdraw")
    public Wallet execute(final BigDecimal amount) {
        LOG.info("Wallet withdraw: [{}]", amount);

        if (isNull(amount) || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount cannot be null or zero");
        }

        final BigInteger weiValue = toWei(amount);
        final var transaction = this.walletWithdrawOutputPort.execute(weiValue);
        final var balanceOf = this.findBalanceByWalletAddressOutputPort.execute(WALLET_ADDRESS);

        return new Wallet("Test",
                WALLET_ADDRESS,
                toEther(balanceOf),
                List.of(transaction),
                OffsetDateTime.now());
    }
}
