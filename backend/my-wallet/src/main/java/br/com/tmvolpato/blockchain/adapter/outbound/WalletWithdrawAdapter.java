package br.com.tmvolpato.blockchain.adapter.outbound;

import br.com.tmvolpato.blockchain.adapter.outbound.blockchain.contract.MyWallet;
import br.com.tmvolpato.blockchain.adapter.outbound.blockchain.mapper.TransactionReceiptMapper;
import br.com.tmvolpato.blockchain.application.core.domain.Transaction;
import br.com.tmvolpato.blockchain.application.port.outbound.LoadMyWalletContractOutputPort;
import br.com.tmvolpato.blockchain.application.port.outbound.WalletWithdrawOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletWithdrawAdapter implements WalletWithdrawOutputPort {

    private final LoadMyWalletContractOutputPort loadMyWalletContractOutputPort;
    private final TransactionReceiptMapper mapper;

    @Override
    public Transaction execute(final BigInteger amount) {
        log.info("Wallet withdraw: [{}]", amount);

        final MyWallet contract = this.loadMyWalletContractOutputPort.execute();

        try {
            final var transactionReceipt = contract.withdraw(amount).send();
            return mapper.toTransaction(transactionReceipt);

        } catch (final Exception ex) {
            log.error("Error to withdraw: [{}]", amount, ex);
            return null;
        }
    }
}
