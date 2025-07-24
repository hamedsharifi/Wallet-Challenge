package ir.welunch.core.usecase;

import ir.welunch.core.annotation.UseCase;
import ir.welunch.core.repository.TransactionRepositoryService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class PrintTotalTransactionAmountUseCase {

    private TransactionRepositoryService transactionRepositoryService;

    public void execute(LocalDateTime start, LocalDateTime end) {
        Long sum = transactionRepositoryService.sumAmountBetween(start, end);
        System.out.println(sum);
    }
}
