package ir.welunch.core.usecase;

import ir.welunch.core.enums.TransactionType;
import ir.welunch.core.model.TransactionModel;

import ir.welunch.core.model.WalletModel;
import ir.welunch.core.repository.TransactionRepositoryService;
import ir.welunch.core.repository.WalletRepositoryService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PrintTotalTransactionAmountUseCaseTest extends BaseIntegrationTest {

    @Autowired
    private PrintTotalTransactionAmountUseCase printTotalTransactionAmountUseCase;

    @Autowired
    private TransactionRepositoryService transactionRepositoryService;

    @Autowired
    private WalletRepositoryService walletRepositoryService;

    @Test
    @Transactional
    void shouldPrintCorrectTotalAmount() {
        Long userId = 77L;
        WalletModel wallet = createWallet(userId, 0L);
        createTransaction(wallet, 500L);   // deposit
        createTransaction(wallet, -100L);  // withdrawal
        createTransaction(wallet, 200L);   // deposit

        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);

        Long result = printTotalTransactionAmountUseCase.execute(start, end);

        assertThat(result).isEqualTo(600); // 500 - 100 + 200
    }

    @Test
    @Transactional
    void shouldPrintZeroWhenNoTransactionsExist() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);

        Long result = printTotalTransactionAmountUseCase.execute(start, end);

        assertThat(result).isEqualTo(0);
    }


    private WalletModel createWallet(Long userId, Long balance) {
        WalletModel wallet = WalletModel.builder()
                .userId(userId)
                .balance(balance)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return walletRepositoryService.save(wallet);
    }

    private void createTransaction(WalletModel wallet, Long amount) {
        TransactionModel tx = TransactionModel.builder()
                .wallet(wallet)
                .amount(amount)
                .type(amount >= 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAW)
                .referenceId(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        transactionRepositoryService.save(tx);
    }
}
