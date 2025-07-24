package ir.welunch.core.usecase;

import ir.welunch.core.annotation.CoreComponent;
import ir.welunch.core.annotation.UseCase;
import ir.welunch.app.WalletChallengeApplication;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootTest(classes = WalletChallengeApplication.class, properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/wallet_db?useSSL=false&serverTimezone=UTC",
        "spring.datasource.username=root",
        "spring.datasource.password=mysecretpassword",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect"
})

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan(basePackages = {"ir.welunch"},
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {UseCase.class, CoreComponent.class}))
public abstract class BaseIntegrationTest {

}

