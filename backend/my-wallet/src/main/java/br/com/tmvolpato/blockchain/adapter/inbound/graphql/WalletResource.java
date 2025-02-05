package br.com.tmvolpato.blockchain.adapter.inbound.graphql;

import br.com.tmvolpato.blockchain.application.core.domain.Wallet;
import br.com.tmvolpato.blockchain.application.port.inbound.WalletBalanceInputPort;
import br.com.tmvolpato.blockchain.application.port.inbound.WalletDepositInputPort;
import br.com.tmvolpato.blockchain.application.port.inbound.WalletWithdrawInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WalletResource {

    private final WalletDepositInputPort walletDepositInputPort;
    private final WalletWithdrawInputPort walletWithdrawInputPort;
    private final WalletBalanceInputPort walletBalanceInputPort;

    @MutationMapping
    public Wallet deposit(@Argument final BigDecimal amount) {
        return this.walletDepositInputPort.execute(amount);
    }

    @MutationMapping
    public Wallet withdraw(@Argument final BigDecimal amount) {
        return this.walletWithdrawInputPort.execute(amount);
    }

    @QueryMapping
    public BigDecimal balanceOf(@Argument final String walletAddress) {
        return this.walletBalanceInputPort.execute(walletAddress);
    }

}
