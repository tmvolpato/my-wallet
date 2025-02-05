package br.com.tmvolpato.blockchain.adapter.outbound.blockchain.mapper;

import br.com.tmvolpato.blockchain.application.core.domain.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TransactionReceiptMapper {

    @Mapping(target = "transactionHash", source = "transactionReceipt.transactionHash")
    @Mapping(target = "transactionIndex", source = "transactionReceipt.transactionIndex")
    @Mapping(target = "blockHash", source = "transactionReceipt.blockHash")
    @Mapping(target = "from", source = "transactionReceipt.from")
    @Mapping(target = "to", source = "transactionReceipt.to")
    @Mapping(target = "revertReason", source = "transactionReceipt.revertReason")
    Transaction toTransaction(TransactionReceipt transactionReceipt);
}
