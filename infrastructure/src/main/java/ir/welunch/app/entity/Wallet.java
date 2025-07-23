package ir.welunch.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "wallets", uniqueConstraints = @UniqueConstraint(columnNames = "userId"))
public class Wallet extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Long balance = 0L;
}
