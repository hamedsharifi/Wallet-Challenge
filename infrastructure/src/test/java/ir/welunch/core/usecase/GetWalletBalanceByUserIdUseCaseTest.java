package ir.welunch.core.usecase;



import ir.welunch.core.dto.GetWalletBalanceRequest;
import ir.welunch.core.dto.GetWalletBalanceResponse;
import ir.welunch.core.exception.ApplicationException;
import ir.welunch.core.model.WalletModel;
import ir.welunch.core.repository.WalletRepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GetWalletBalanceByUserIdUseCaseTest extends BaseIntegrationTest {

    @Autowired
    private GetWalletBalanceByUserIdUseCase getWalletBalanceByUserIdUseCase;

    @Autowired
    private WalletRepositoryService walletRepositoryService;

    @Test
    @Transactional
    void shouldReturnBalanceSuccessfully() {
        // Arrange
        Long userId = 100L;
        WalletModel wallet = createWallet(userId, 5000L);

        GetWalletBalanceRequest request = new GetWalletBalanceRequest(userId);

        // Act
        GetWalletBalanceResponse response = getWalletBalanceByUserIdUseCase.execute(request);

        // Assert
        assertThat(response.getBalance()).isEqualTo(wallet.getBalance());
    }

    @Test
    @Transactional
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        Long nonExistentUserId = 999L;
        GetWalletBalanceRequest request = new GetWalletBalanceRequest(nonExistentUserId);

        // Act & Assert
        assertThatThrownBy(() -> getWalletBalanceByUserIdUseCase.execute(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("User not found");
    }

    // Helper method
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
