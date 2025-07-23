package ir.welunch.core.usecase;

import ir.welunch.core.dto.GetWalletBalanceRequest;
import ir.welunch.core.dto.GetWalletBalanceResponse;
import ir.welunch.core.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import ir.welunch.core.model.WalletModel;
import ir.welunch.core.repository.WalletRepositoryService;
import ir.welunch.core.annotation.UseCase;

@UseCase
@RequiredArgsConstructor
public class GetWalletBalanceByUserIdUseCase {

    private final WalletRepositoryService walletRepositoryService;

    public GetWalletBalanceResponse execute(GetWalletBalanceRequest request) {
        WalletModel wallet = walletRepositoryService
                .findByUserId(request.getUserId())
                .orElseThrow(() -> new ApplicationException("user_not_found", "User not found"));

        return new GetWalletBalanceResponse(wallet.getBalance());
    }
}
