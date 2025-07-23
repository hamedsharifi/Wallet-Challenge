package ir.welunch.core.usecase;

import ir.welunch.core.repository.TransactionRepositoryService;

import java.time.LocalDateTime;

public class PrintTotalTransactionAmountUseCase {

    private TransactionRepositoryService transactionRepositoryService;

    public void execute(LocalDateTime start, LocalDateTime end) {
        Long sum = transactionRepositoryService.sumAmountBetween(start, end);
        System.out.println(sum);
    }
}
