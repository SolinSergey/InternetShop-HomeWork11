package ru.gb.internetshop.controllers;

import api.JwtRequest;
import api.JwtResponse;
import api.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.gb.internetshop.exceptions.AppError;
import ru.gb.internetshop.services.UserService;
import ru.gb.internetshop.utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Методы работы с аутентификацией")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(
            summary = "Запрос на получение jwtToken",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка авторизации", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> createUserToken(@RequestBody JwtRequest authRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        }catch(BadCredentialsException e){
            return new ResponseEntity<>(new AppError("CHECK_TOKEN_ERROR","Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails=userService.loadUserByUsername(authRequest.getUsername());
        String token=jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private final AuthenticationManager authenticationManager;

}
