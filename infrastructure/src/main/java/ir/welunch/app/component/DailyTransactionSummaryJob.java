package ir.welunch.app.component;

import ir.welunch.core.usecase.PrintTotalTransactionAmountUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyTransactionSummaryJob {

    private final PrintTotalTransactionAmountUseCase printTotalTransactionAmountUseCase;

    @Scheduled(cron = "0 47 10 * * *")
    public void runDailyTransactionSummary() {
        log.info("unDailyTransactionSummary");
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime start = yesterday.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        printTotalTransactionAmountUseCase.execute(start, end);
    }
}
