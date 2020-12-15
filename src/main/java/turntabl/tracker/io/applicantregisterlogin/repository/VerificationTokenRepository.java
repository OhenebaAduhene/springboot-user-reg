package turntabl.tracker.io.applicantregisterlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import turntabl.tracker.io.applicantregisterlogin.model.User;
import turntabl.tracker.io.applicantregisterlogin.model.VerificationToken;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    Optional<VerificationToken> findByVerificationToken(String verificationToken);

    VerificationToken findByUser(User user);
    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);
    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

}
