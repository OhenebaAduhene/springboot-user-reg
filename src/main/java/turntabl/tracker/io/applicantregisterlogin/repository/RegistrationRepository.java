package turntabl.tracker.io.applicantregisterlogin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import turntabl.tracker.io.applicantregisterlogin.model.User;

import java.util.Optional;


@Repository
public interface RegistrationRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User findByPassword(String password);
    User findUserByEmailAndPassword(String email, String password);
}
