package com.br.fiap.fortaleza.sabor.domain.user;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
public class User {

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Email
    @NotNull(message = "Email é obrigatório")
    private String email;

    private String login;

    @NotNull(message = "Senha é obrigatório")
    private String senha;

    private LocalDate dataAlteracao;

    private TypeEnum tipo;

    @NotNull(message = "Endereço é obrigatório")
    private List<Address> address;

    @Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", dataAlteracao=" + dataAlteracao +
                ", tipo=" + tipo +
                ", address=" + address +
                '}';
    }

}
