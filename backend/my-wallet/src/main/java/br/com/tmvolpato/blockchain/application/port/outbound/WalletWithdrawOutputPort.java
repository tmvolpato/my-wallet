package br.com.tmvolpato.blockchain.application.port.outbound;

import br.com.tmvolpato.blockchain.application.core.domain.Transaction;

import java.math.BigInteger;

public interface WalletWithdrawOutputPort {

    Transaction execute(BigInteger amount);
}
