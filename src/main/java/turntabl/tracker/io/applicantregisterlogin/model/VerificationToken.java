package turntabl.tracker.io.applicantregisterlogin.model;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class VerificationToken {
    private static final int EXPIRATION = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String verificationToken;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private Date expiryDate;


    public VerificationToken( final User user) {
        super();

        this.verificationToken = UUID.randomUUID().toString();
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String verificationToken) {
        this.verificationToken = verificationToken;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
