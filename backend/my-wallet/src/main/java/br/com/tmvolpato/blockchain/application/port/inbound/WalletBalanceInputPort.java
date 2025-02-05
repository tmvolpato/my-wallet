package br.com.tmvolpato.blockchain.application.port.inbound;

import java.math.BigDecimal;

public interface WalletBalanceInputPort {

    BigDecimal execute(String walletAddress);
}
