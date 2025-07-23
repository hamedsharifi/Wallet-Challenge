package usecase;

import dto.AddMoneyToWalletRequest;
import dto.AddMoneyToWalletResponse;
import enums.TransactionType;
import exception.ApplicationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.TransactionModel;
import model.WalletModel;
import repository.TransactionRepositoryService;
import repository.WalletRepositoryService;
import util.annotation.UseCase;

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
