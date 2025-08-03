package com.br.fiap.fortaleza.sabor.application.usecase.auth;

import com.br.fiap.fortaleza.sabor.application.ports.in.AuthUseCasePort;
import com.br.fiap.fortaleza.sabor.application.ports.out.UsersRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.token.Token;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
public class AuthUseCase implements AuthUseCasePort {

    private final UsersRepositoryPort usersRepositoryPort;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthUseCase(UsersRepositoryPort usersRepositoryPort, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.usersRepositoryPort = usersRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public Token validateLogin(String email, String password) {

        var user = usersRepositoryPort.findByEmail(email);
        if(user.isEmpty() || !isLoginCorrect(email, password, passwordEncoder)) {
            throw new UserCredentialsException("Invalid email or password");
        }

        var now = Instant.now();
        var expiresIn = 300L;
        var scope = user.stream().map(User::getTipo).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("BackendFortalezaSabor")
                .subject(email)
                .issuedAt(now)
                .claim("scope", scope)
                .expiresAt(now.plusSeconds(expiresIn)).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new Token(jwtValue, expiresIn);
    }

    public void updatePassword(String email, String password) {
        usersRepositoryPort.updatePassword(email, password);
    }

    private boolean isLoginCorrect(String email, String password, PasswordEncoder passwordEncoder) {
        var userPassword = usersRepositoryPort.findByEmail(email).stream().map(User::getSenha).collect(Collectors.joining());
        return passwordEncoder.matches(password, userPassword);
    }
}
