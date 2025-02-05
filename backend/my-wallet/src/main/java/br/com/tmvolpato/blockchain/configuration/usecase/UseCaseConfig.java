package br.com.tmvolpato.blockchain.configuration.usecase;

import br.com.tmvolpato.blockchain.application.core.usecase.WalletBalanceUseCase;
import br.com.tmvolpato.blockchain.application.core.usecase.WalletDepositUseCase;
import br.com.tmvolpato.blockchain.application.core.usecase.WalletWithdrawUseCase;
import br.com.tmvolpato.blockchain.application.port.outbound.FindBalanceByWalletAddressOutputPort;
import br.com.tmvolpato.blockchain.application.port.outbound.WalletDepositOutputPort;
import br.com.tmvolpato.blockchain.application.port.outbound.WalletWithdrawOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public WalletBalanceUseCase findBalanceByWalletAddressUseCase(final FindBalanceByWalletAddressOutputPort findBalanceByWalletAddressOutputPort) {
        return new WalletBalanceUseCase(findBalanceByWalletAddressOutputPort);
    }

    @Bean
    public WalletDepositUseCase depositUseCase(final WalletDepositOutputPort walletDepositOutputPort, final FindBalanceByWalletAddressOutputPort findBalanceByWalletAddressOutputPort) {
        return new WalletDepositUseCase(walletDepositOutputPort, findBalanceByWalletAddressOutputPort);
    }

    @Bean
    public WalletWithdrawUseCase withdrawUseCase(final WalletWithdrawOutputPort walletWithdrawOutputPort, final FindBalanceByWalletAddressOutputPort findBalanceByWalletAddressOutputPort) {
        return new WalletWithdrawUseCase(walletWithdrawOutputPort, findBalanceByWalletAddressOutputPort);
    }

}
