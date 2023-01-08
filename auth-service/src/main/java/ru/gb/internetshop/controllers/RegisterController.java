package ru.gb.internetshop.controllers;

import api.RegisterUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.internetshop.entities.User;
import ru.gb.internetshop.exceptions.AppError;
import ru.gb.internetshop.exceptions.UsernameIsAlreadyPresent;
import ru.gb.internetshop.services.UserService;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Tag(name = "Создание нового пользователя", description = "Методы работы с созданием пользователей")
public class RegisterController {

    private final UserService userService;

    @Operation(
            summary = "Запрос на создание нового поользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Ошибка создания", responseCode = "400"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity registrateNewUser(@RequestBody RegisterUserDto registerUserDto) {
        return userService.registerNewUser(registerUserDto);
    }
}
