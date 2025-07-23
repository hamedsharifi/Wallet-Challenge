package repository;

import model.WalletModel;

import java.util.Optional;

public interface WalletRepositoryService {

    Optional<WalletModel> findByUserId(Long userId);

    WalletModel save(WalletModel walletModel);
}

