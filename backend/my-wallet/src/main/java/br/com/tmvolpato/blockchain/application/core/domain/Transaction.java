package br.com.tmvolpato.blockchain.application.core.domain;

public record Transaction(
        String transactionHash,
        String transactionIndex,
        String blockHash,
        String from,
        String to,
        String revertReason
) {}
