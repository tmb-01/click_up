package uz.pdp.click_up.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.click_up.entity.User;
import uz.pdp.click_up.payload.ApiResponse;
import uz.pdp.click_up.payload.RegisterDto;
import uz.pdp.click_up.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    //    todo code
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public ApiResponse registerUser(RegisterDto registerDto) {

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ApiResponse("this user is exist", false);
        }

        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        int code = new Random().nextInt(999999);
        user.setEmailCode(String.valueOf(code).substring(0, 4));
        userRepository.save(user);

        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("user registered", true);
    }


    public Boolean sendEmail(String sendingEmail, String emailCode) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("maraimtuxtasunov@gmail.com");
            message.setTo(sendingEmail);
            message.setSubject("verification message");
            message.setText(emailCode);

            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getEmailCode().equals(emailCode)) {
                user.setEnabled(true);
                userRepository.save(user);
                return new ApiResponse("account activated", true);
            }
            return new ApiResponse("code is error", true);
        }
        return new ApiResponse("user is wrong", false);
    }

}
