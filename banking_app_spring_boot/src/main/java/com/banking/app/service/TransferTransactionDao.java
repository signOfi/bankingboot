package com.banking.app.service;

import com.banking.app.dto.TransferTransactionDto;

import java.util.List;

public interface TransferTransactionDao {

    TransferTransactionDto createTransferTransaction (
             long fromId,
             long toId,
             double beforeBalance,
             double afterBalance
    );

}
