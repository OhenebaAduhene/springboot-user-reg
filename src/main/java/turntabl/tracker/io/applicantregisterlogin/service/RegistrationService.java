package turntabl.tracker.io.applicantregisterlogin.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import turntabl.tracker.io.applicantregisterlogin.model.User;
import turntabl.tracker.io.applicantregisterlogin.model.VerificationToken;
import turntabl.tracker.io.applicantregisterlogin.repository.RegistrationRepository;

import java.text.MessageFormat;
import java.util.Optional;


@Service
@AllArgsConstructor
public class RegistrationService implements UserDetailsService {

    @Autowired
    private RegistrationRepository registrationRepository;

    private final EmailSenderService emailSenderService;

    public void sendVerificationEmail(String userMail, String verificationToken){
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Thank you for registering. Please click link the below to activate your account." + "http://localhost:8080/sign-up/confirm?token="
                        + verificationToken);

        emailSenderService.sendEmail(mailMessage);
    }

    public void confirmUser(VerificationToken token){
        final User user = token.getUser();
        user.setEnabled(true);
        registrationRepository.save(user);
    }


    public void saveUser(User user){
        registrationRepository.save(user);
    }

    public Optional<User> fetchUserByEmail(String email){
       return registrationRepository.findByEmail(email);
    }

    public User fetchUserByPassword(String password){
        return registrationRepository.findByPassword(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> optionalUser = registrationRepository.findByEmail(email);


        if (optionalUser.isPresent()) {
            return (UserDetails) optionalUser.get();
        }
        else {

            //if user is not found, throw username not found exception.
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }
    }
}
