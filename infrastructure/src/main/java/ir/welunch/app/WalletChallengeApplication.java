package ir.welunch.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import ir.welunch.core.annotation.CoreComponent;
import ir.welunch.core.annotation.UseCase;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"ir.welunch"},
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {UseCase.class, CoreComponent.class}))
@EnableScheduling
public class WalletChallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletChallengeApplication.class, args);
    }
}