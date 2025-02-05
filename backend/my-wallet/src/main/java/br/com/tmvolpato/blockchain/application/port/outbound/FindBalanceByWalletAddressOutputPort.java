package br.com.tmvolpato.blockchain.application.port.outbound;

import java.math.BigInteger;

public interface FindBalanceByWalletAddressOutputPort {

    BigInteger execute(String walletAddress);
}
