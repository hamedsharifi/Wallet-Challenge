package ir.welunch.core.usecase;

import ir.welunch.core.dto.AddMoneyToWalletRequest;
import ir.welunch.core.dto.AddMoneyToWalletResponse;
import ir.welunch.core.enums.TransactionType;
import ir.welunch.core.exception.ApplicationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ir.welunch.core.model.TransactionModel;
import ir.welunch.core.model.WalletModel;
import ir.welunch.core.repository.TransactionRepositoryService;
import ir.welunch.core.repository.WalletRepositoryService;
import ir.welunch.core.annotation.UseCase;

import java.time.LocalDateTime;
import java.util.UUID;


@UseCase
@RequiredArgsConstructor
public class AddMoneyToWalletUseCase {


    private final WalletRepositoryService walletRepositoryService;
    private final TransactionRepositoryService transactionRepositoryService;

    @Transactional
    public AddMoneyToWalletResponse execute(AddMoneyToWalletRequest request) {
        Long userId = request.getUserId();
        Long amount = request.getAmount();

        if (userId == null || amount == null) {
            throw new ApplicationException("bad_user_input", "You entered an invalid user id or amount");
        }

        WalletModel wallet = walletRepositoryService.findByUserId(userId)
                .orElseGet(() -> WalletModel.builder()
                        .userId(userId)
                        .balance(0L)
                        .build());

        long newBalance = wallet.getBalance() + amount;
        if (newBalance < 0) {
            throw new ApplicationException("wallet_insufficient_balance", "Insufficient balance for withdrawal");
        }

        String referenceId = UUID.randomUUID().toString();

        TransactionModel transaction = TransactionModel.builder()
                .wallet(wallet)
                .amount(amount)
                .type(amount >= 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAW)
                .referenceId(referenceId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        transactionRepositoryService.save(transaction);

        wallet.setBalance(newBalance);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepositoryService.save(wallet);

        return new AddMoneyToWalletResponse(referenceId);
    }
}
