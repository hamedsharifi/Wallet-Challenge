package ir.welunch.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ir.welunch.core.usecase.AddMoneyToWalletUseCase;
import ir.welunch.core.usecase.GetWalletBalanceByUserIdUseCase;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final GetWalletBalanceByUserIdUseCase getWalletBalanceByUserIdUseCase;
    private final AddMoneyToWalletUseCase addMoneyToWalletUseCase;


}
