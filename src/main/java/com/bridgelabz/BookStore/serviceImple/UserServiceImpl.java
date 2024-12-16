package com.bridgelabz.BookStore.serviceImple;

import com.bridgelabz.BookStore.dto.LoginRequestDTO;
import com.bridgelabz.BookStore.dto.ResetPasswordDTO;
import com.bridgelabz.BookStore.dto.UserRequestDTO;
import com.bridgelabz.BookStore.dto.UserResponseDTO;
import com.bridgelabz.BookStore.entity.User;
import com.bridgelabz.BookStore.repo.UserRepository;
import com.bridgelabz.BookStore.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private EmailService emailService;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.emailService = new EmailService();
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        Boolean isPresent = userRepository.existsByEmail(userRequestDTO.getEmail());
        if(isPresent) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = mapToUser(userRequestDTO);
        user.setRegisteredDate(LocalDate.now());
        user = userRepository.save(user);
        return mapToResponseDTO(user);

    }

    @Override
    public User loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmailAndPassword(loginRequestDTO.getEmail(),loginRequestDTO.getPassword()).orElseThrow(() -> new IllegalArgumentException("Invalid Username and Password"));
        return user;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User"));
        return mapToResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User"));
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setDob(userRequestDTO.getDob());
        user.setPassword(userRequestDTO.getPassword());
        user.setUpdatedDate(LocalDate.now());
        user = userRepository.save(user);
        return mapToResponseDTO(user);
    }

    @Override
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        userRepository.delete(user);
        return "User deleted";
    }

    @Override
    public UserResponseDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userRepository.findByIdAndPassword(resetPasswordDTO.getId(),resetPasswordDTO.getOldPassword()).orElseThrow(() -> new IllegalArgumentException("Invalid User!"));
        user.setPassword(resetPasswordDTO.getNewPassword());
        user = userRepository.save(user);
        return mapToResponseDTO(user);
    }

    @Override
    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email"));
//        String subject = "Forgot Password";
//        String body = "Your password is: "+generateRandomPassword();
//        String mail = user.getEmail();
//        System.out.println(mail);
//        emailService.sendEmail(mail,subject,body);
//        return "The password has been sent to your email";
        return generateRandomPassword();
    }

    private String generateRandomPassword() {
        return "User@123";
    }

    private UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setDob(user.getDob());
        userResponseDTO.setRegisteredDate(user.getRegisteredDate());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUpdatedDate(user.getUpdatedDate());
        userResponseDTO.setPassword(user.getPassword());
        return userResponseDTO;
    }

    private User mapToUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setDob(userRequestDTO.getDob());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setRole(userRequestDTO.getRole());
        return user;
    }
}
