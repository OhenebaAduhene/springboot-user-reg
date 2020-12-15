package turntabl.tracker.io.applicantregisterlogin.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turntabl.tracker.io.applicantregisterlogin.model.VerificationToken;
import turntabl.tracker.io.applicantregisterlogin.repository.VerificationTokenRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationTokenService {
    @Autowired
    private final VerificationTokenRepository repository;

    public void saveVerificationToken(VerificationToken token){
        repository.save(token);
    }

    public Optional<VerificationToken> findVerificationToken(String token){
        return repository.findByVerificationToken(token);
    }
}
