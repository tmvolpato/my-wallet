package br.com.tmvolpato.blockchain.application.core.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record Wallet(
        String name,
        String walletAddress,
        BigDecimal balance,
        List<Transaction> transactions,
        OffsetDateTime updatedAt
) {}
