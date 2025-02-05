package br.com.tmvolpato.blockchain.application.port.inbound;

import br.com.tmvolpato.blockchain.application.core.domain.Wallet;

import java.math.BigDecimal;

public interface WalletDepositInputPort {

    Wallet execute(BigDecimal amount);
}
