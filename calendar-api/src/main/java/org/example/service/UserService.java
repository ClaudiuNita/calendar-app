package org.example.service;

import jakarta.annotation.Resource;
import org.example.dto.UserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail())
        ).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        UserDto userDto;

        if (userOptional.isPresent()) {
            userDto = new UserDto(
                    userOptional.get().getId(),
                    userOptional.get().getUsername(),
                    userOptional.get().getFullName(),
                    userOptional.get().getEmail()
            );
        } else {
            throw new NoSuchElementException();
        }

        return userDto;
    }

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());

        userRepository.save(user);
    }

    public void updateUser(UserDto userDto) {
        User user;
        Optional<User> userOptional = userRepository.findById(userDto.getId());

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            throw new NoSuchElementException();
        }

        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
           throw new NoSuchElementException("No user with id: " + id);
        }
    }
}
