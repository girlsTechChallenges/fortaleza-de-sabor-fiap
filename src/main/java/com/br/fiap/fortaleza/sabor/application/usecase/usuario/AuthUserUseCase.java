package com.br.fiap.fortaleza.sabor.application.usecase.usuario;

import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
import com.br.fiap.fortaleza.sabor.domain.token.Token;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
public class AuthUserUseCase {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthUserUseCase(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public Token validateLogin(String email, String password) {

        var user = usersRepository.findByEmail(email);
        if(user.isEmpty() || !isLoginCorrect(email, password, passwordEncoder)) {
            throw new UserCredentialsException("Invalid email or password");
        }

        var now = Instant.now();
        var expiresIn = 300L;
        var scope = user.stream().map(u -> u.getTipo().getNameType()).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("BackendFortalezaSabor")
                .subject(email)
                .issuedAt(now)
                .claim("scope", scope)
                .expiresAt(now.plusSeconds(expiresIn)).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new Token(jwtValue, expiresIn);
    }

    private boolean isLoginCorrect(String email, String password, PasswordEncoder passwordEncoder) {
        var userPassword = usersRepository.findByEmail(email).stream().map(User::getSenha).collect(Collectors.joining());
        return passwordEncoder.matches(password, userPassword);
    }

    public void updatePassword(String email, String password) {
        usersRepository.updatePassword(email, password);
    }
}
