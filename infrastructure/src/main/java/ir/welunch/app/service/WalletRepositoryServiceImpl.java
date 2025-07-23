package ir.welunch.app.service;

import ir.welunch.app.entity.Wallet;
import ir.welunch.app.mapper.WalletMapper;
import ir.welunch.app.repository.WalletJpaRepository;
import lombok.RequiredArgsConstructor;
import model.WalletModel;
import org.springframework.stereotype.Service;
import repository.WalletRepositoryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletRepositoryServiceImpl implements WalletRepositoryService {

    private final WalletMapper walletMapper;
    private final WalletJpaRepository walletJpaRepository;

    @Override
    public Optional<WalletModel> findByUserId(Long userId) {
        return walletJpaRepository.findById(userId).map(walletMapper::toModel);
    }

    @Override
    public WalletModel save(WalletModel walletModel) {
        Wallet wallet = walletMapper.toEntity(walletModel);
        return walletMapper.toModel(walletJpaRepository.save(wallet));
    }
}
