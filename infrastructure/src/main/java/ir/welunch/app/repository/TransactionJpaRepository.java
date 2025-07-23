package ir.welunch.app.repository;

import ir.welunch.app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByWalletUserId(Long userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.createdAt >= :start AND t.createdAt < :end")
    Long sumAmountBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}

