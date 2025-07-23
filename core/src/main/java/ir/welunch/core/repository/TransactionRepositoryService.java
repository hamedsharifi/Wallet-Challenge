package ir.welunch.core.repository;

import ir.welunch.core.model.TransactionModel;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepositoryService {

    TransactionModel save(TransactionModel transactionModel);

    List<TransactionModel> findByWalletUserId(Long userId);

    Long sumAmountBetween(LocalDateTime start, LocalDateTime end);

}

