package ru.gb.internetshop.services;

import api.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.internetshop.entities.Role;
import ru.gb.internetshop.entities.User;
import ru.gb.internetshop.exceptions.AppError;
import ru.gb.internetshop.repositories.UserRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public ResponseEntity registerNewUser(RegisterUserDto registerUserDto){
        Optional<User> user = findByUsername(registerUserDto.getUsername());
        if (!user.isPresent()) {
            if (registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
                if (registerUserDto.getEmail() != null) {
                    User newUser = new User();
                    newUser.setUsername(registerUserDto.getUsername());
                    newUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
                    newUser.setEmail(registerUserDto.getEmail());
                    Collection<Role> roles = new ArrayList<>();
                    roles.add(new Role(4L,"USER_ROLE"));
                    newUser.setRoles(roles);
                    userRepository.save(newUser);
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new AppError("USER_EMAIL_NOT_PRESENT",
                            "Не введен адрес электронной почты"), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new AppError("USER_PASSWORD_NOT_EQUAL_CONFIRMPASSWORD",
                        "Ошибка регистрации!\nВведенные пароли не совпадают"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new AppError("USERNAME_IS_ALREADY_PRESENT",
                    "Ошибка регистрации!\nПользователь с именем " + registerUserDto.getUsername() + " уже существует"), HttpStatus.BAD_REQUEST);
        }
    }
}