package ir.welunch.app.service;

import ir.welunch.app.entity.Transaction;
import ir.welunch.app.mapper.TransactionMapper;
import ir.welunch.app.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import ir.welunch.core.model.TransactionModel;
import org.springframework.stereotype.Service;
import ir.welunch.core.repository.TransactionRepositoryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionRepositoryServiceImpl implements TransactionRepositoryService {
    private TransactionJpaRepository transactionJpaRepository;
    private TransactionMapper transactionMapper;

    @Override
    public TransactionModel save(TransactionModel transactionModel) {
        Transaction transaction = transactionMapper.toEntity(transactionModel);
        return transactionMapper.toModel(transactionJpaRepository.save(transaction));
    }

    @Override
    public List<TransactionModel> findByWalletUserId(Long userId) {
        return transactionMapper.toModelList(transactionJpaRepository.findByWalletUserId(userId));
    }

    @Override
    public Long sumAmountBetween(LocalDateTime start, LocalDateTime end) {
        return transactionJpaRepository.sumAmountBetweenDates(start, end);
    }
}
