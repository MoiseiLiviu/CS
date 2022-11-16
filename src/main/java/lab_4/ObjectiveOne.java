package lab_4;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Log
public class ObjectiveOne {

    private final UserRepository userRepository;

    @PostConstruct
    public void execute() {
        String password = "Secret_password";
        String hashedPwd = BCrypt.hashpw(password, BCrypt.gensalt());
        SecretiveUser secretiveUser = new SecretiveUser();
        secretiveUser.setPwd(hashedPwd);
        SecretiveUser savedUser = userRepository.save(secretiveUser);

        log.info("Persisted hashed pwd : "+savedUser.getPwd());
    }
}
