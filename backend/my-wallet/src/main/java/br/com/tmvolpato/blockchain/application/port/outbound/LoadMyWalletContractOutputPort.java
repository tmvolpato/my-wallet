package br.com.tmvolpato.blockchain.application.port.outbound;

import br.com.tmvolpato.blockchain.adapter.outbound.blockchain.contract.MyWallet;

public interface LoadMyWalletContractOutputPort {

    MyWallet execute();
}
