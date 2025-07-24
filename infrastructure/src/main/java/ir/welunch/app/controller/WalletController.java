package ir.welunch.app.controller;

import ir.welunch.core.dto.AddMoneyToWalletRequest;
import ir.welunch.core.dto.AddMoneyToWalletResponse;
import ir.welunch.core.dto.GetWalletBalanceRequest;
import ir.welunch.core.dto.GetWalletBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ir.welunch.core.usecase.AddMoneyToWalletUseCase;
import ir.welunch.core.usecase.GetWalletBalanceByUserIdUseCase;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final GetWalletBalanceByUserIdUseCase getWalletBalanceByUserIdUseCase;
    private final AddMoneyToWalletUseCase addMoneyToWalletUseCase;


    @GetMapping("/get-balance")
    public ResponseEntity<GetWalletBalanceResponse> getBalance(GetWalletBalanceRequest request) {
        return ResponseEntity.ok(getWalletBalanceByUserIdUseCase.execute(request));
    }

    @PostMapping("/charge")
    public ResponseEntity<AddMoneyToWalletResponse> addToCart(@RequestBody AddMoneyToWalletRequest request) {
        return ResponseEntity.ok(addMoneyToWalletUseCase.execute(request));
    }
}
