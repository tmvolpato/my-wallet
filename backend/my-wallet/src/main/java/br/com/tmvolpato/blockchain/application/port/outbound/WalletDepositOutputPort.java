package br.com.tmvolpato.blockchain.application.port.outbound;

import br.com.tmvolpato.blockchain.application.core.domain.Transaction;

import java.math.BigInteger;

public interface WalletDepositOutputPort {

    Transaction execute(BigInteger amount);
}
