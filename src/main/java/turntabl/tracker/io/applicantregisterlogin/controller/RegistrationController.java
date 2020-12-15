package turntabl.tracker.io.applicantregisterlogin.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import turntabl.tracker.io.applicantregisterlogin.model.User;
import turntabl.tracker.io.applicantregisterlogin.model.VerificationToken;
import turntabl.tracker.io.applicantregisterlogin.service.RegistrationService;
import turntabl.tracker.io.applicantregisterlogin.service.VerificationTokenService;

import java.util.Optional;


@RestController
@AllArgsConstructor
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private final VerificationTokenService verificationTokenService;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) throws Exception {
        String tempEmail = user.getEmail();
        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

//        if (tempEmail != null && !"".equals(tempEmail)){
//            Optional<User> oldEmail =  registrationService.fetchUserByEmail(tempEmail);
//
//            if (oldEmail != null){
//                throw new Exception("User already exist");
//            }
//        }
        registrationService.saveUser(user);

        final VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenService.saveVerificationToken(verificationToken);
        registrationService.sendVerificationEmail(user.getEmail(), verificationToken.getVerificationToken());
    }

    @GetMapping("/register/confirm")
    String confirmEmail(@RequestParam("token") String token){
        Optional<VerificationToken> optionalVerificationToken = verificationTokenService.findVerificationToken(token);
        optionalVerificationToken.ifPresent(registrationService::confirmUser);
        return "user confirmed";
    }

    @PostMapping("/login")
    public Optional<User> loginUser(@RequestBody User user) throws Exception {


        Optional<User> userDetails = registrationService.fetchUserByEmail(user.getEmail());

        if (userDetails.isPresent()){
            User userInfoInDB = userDetails.get();

            if (bCryptPasswordEncoder.matches(user.getPassword(), userInfoInDB.getPassword())) {
                return userDetails;
            }
        }


        return null;
    }


}

