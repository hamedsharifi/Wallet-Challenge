package usecase;

import dto.GetWalletBalanceRequest;
import dto.GetWalletBalanceResponse;
import exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import model.WalletModel;
import repository.WalletRepositoryService;
import util.annotation.UseCase;

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
