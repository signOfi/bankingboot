package com.banking.app.service.mapper;

import com.banking.app.dto.TransferTransactionDto;
import com.banking.app.model.TransferTransaction;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TransferTransactionDtoMapper implements Function<TransferTransaction, TransferTransactionDto> {
    @Override
    public TransferTransactionDto apply(TransferTransaction transferTransaction) {
        return new TransferTransactionDto(
                transferTransaction.getTimestamp(),
                transferTransaction.getBeforeBalance(),
                transferTransaction.getAfterBalance(),
                transferTransaction.getFromAccount(),
                transferTransaction.getToAccount()
        );
    }
}
