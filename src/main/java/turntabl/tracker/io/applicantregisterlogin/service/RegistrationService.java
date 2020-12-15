package turntabl.tracker.io.applicantregisterlogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import turntabl.tracker.io.applicantregisterlogin.model.User;
import turntabl.tracker.io.applicantregisterlogin.repository.RegistrationRepository;

import java.text.MessageFormat;
import java.util.Optional;


@Service
public class RegistrationService implements UserDetailsService {

    @Autowired
    private RegistrationRepository registry;

    public void saveUser(User user){
        registry.save(user);
    }

    public Optional<User> fetchUserByEmail(String email){
       return registry.findByEmail(email);
    }

    public User fetchUserByPassword(String password){
        return registry.findByPassword(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Optional<User> optionalUser = registry.findByEmail(email);


        if (optionalUser.isPresent()) {
            return (UserDetails) optionalUser.get();
        }
        else {

            //if user is not found, throw username not found exception.
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }
    }
}
