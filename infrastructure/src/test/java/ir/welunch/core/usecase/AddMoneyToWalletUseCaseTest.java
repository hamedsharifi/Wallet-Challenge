package ir.welunch.core.usecase;

import ir.welunch.core.dto.AddMoneyToWalletRequest;
import ir.welunch.core.dto.AddMoneyToWalletResponse;
import ir.welunch.core.model.TransactionModel;
import ir.welunch.core.model.WalletModel;
import ir.welunch.core.repository.TransactionRepositoryService;
import ir.welunch.core.repository.WalletRepositoryService;
import ir.welunch.core.usecase.AddMoneyToWalletUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AddMoneyToWalletUseCaseTest extends BaseIntegrationTest {

    @Autowired
    private AddMoneyToWalletUseCase addMoneyToWalletUseCase;

    @Autowired
    private WalletRepositoryService walletRepositoryService;

    @Autowired
    private TransactionRepositoryService transactionRepositoryService;

    @Test
    @Transactional
    void shouldDepositToExistingWallet() {
        WalletModel wallet = createWallet(1L, 1000L);

        AddMoneyToWalletRequest request = new AddMoneyToWalletRequest(wallet.getUserId(), 500L);

        AddMoneyToWalletResponse response = addMoneyToWalletUseCase.execute(request);

        assertThat(response.getReferenceId()).isNotBlank();

        WalletModel updatedWallet = walletRepositoryService.findByUserId(wallet.getUserId()).orElseThrow();
        assertThat(updatedWallet.getBalance()).isEqualTo(1500L);

        List<TransactionModel> transactions = transactionRepositoryService.findByWalletUserId(wallet.getUserId());
        assertThat(transactions).hasSize(1);
        assertThat(transactions.get(0).getAmount()).isEqualTo(500L);
    }

    @Test
    @Transactional
    void shouldWithdrawFromExistingWallet() {
        WalletModel wallet = createWallet(3L, 1000L);

        AddMoneyToWalletRequest request = new AddMoneyToWalletRequest(wallet.getUserId(), -400L);

        AddMoneyToWalletResponse response = addMoneyToWalletUseCase.execute(request);


        WalletModel updatedWallet = walletRepositoryService.findByUserId(wallet.getUserId()).orElseThrow();
        assertThat(updatedWallet.getBalance()).isEqualTo(600L);
    }

    @Test
    @Transactional
    void shouldFailWithdrawIfInsufficientBalance() {
        WalletModel wallet = createWallet(4L, 300L);

        AddMoneyToWalletRequest request = new AddMoneyToWalletRequest(wallet.getUserId(), -500L);

        assertThatThrownBy(() -> addMoneyToWalletUseCase.execute(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Insufficient balance");

        WalletModel updatedWallet = walletRepositoryService.findByUserId(wallet.getUserId()).orElseThrow();
        assertThat(updatedWallet.getBalance()).isEqualTo(300L);

        List<TransactionModel> transactions = transactionRepositoryService.findByWalletUserId(wallet.getUserId());
        assertThat(transactions).isEmpty();
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
}
