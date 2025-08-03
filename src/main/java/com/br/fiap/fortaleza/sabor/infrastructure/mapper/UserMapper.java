package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.token.Token;
import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.br.fiap.fortaleza.sabor.infrastructure.common.MessageConstants.USER_NOT_FOUND;

@Component
public class UserMapper {

    private final TypeUserMapper typeUserMapper;

    public UserMapper(TypeUserMapper typeUserMapper) {
        this.typeUserMapper = typeUserMapper;
    }

    public User toUserDomain(UserRequestDto userRequestDto) {

        List<Address> addresses = userRequestDto.address().stream()
                .map(addressEntity -> new Address(
                        addressEntity.rua(),
                        addressEntity.bairro(),
                        addressEntity.complemento(),
                        addressEntity.numero(),
                        addressEntity.estado(),
                        addressEntity.cidade(),
                        addressEntity.cep()
                )).toList();

        return new User(
                userRequestDto.nome(),
                userRequestDto.email(),
                userRequestDto.login(),
                userRequestDto.senha(),
                userRequestDto.dataAlteracao(),
                userRequestDto.tipo(),
                addresses);
    }

    public User updateToUserDomain(UpdateRequestDto updateRequestDto) {

        List<Address> addresses = updateRequestDto.address().stream()
                .map(addressEntity -> new Address(
                        addressEntity.rua(),
                        addressEntity.bairro(),
                        addressEntity.complemento(),
                        addressEntity.numero(),
                        addressEntity.estado(),
                        addressEntity.cidade(),
                        addressEntity.cep()
                )).toList();

        return new User(
                updateRequestDto.nome(),
                updateRequestDto.email(),
                updateRequestDto.senha(),
                updateRequestDto.tipo(),
                addresses);
    }

    public UserEntity toUserEntity(User user) {

        List<AddressEntity> addressEntities = user.getAddress()
                .stream().map(address -> new AddressEntity(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getEstado(),
                        address.getCidade(), address.getCep())).toList();

        return new UserEntity(
                null,
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getSenha(),
                user.getDataAlteracao(),
                typeUserMapper.toTypeEntity(new TypeUser(null, user.getTipo())),
                addressEntities
                );
    }

    public User toUserDomain(UserEntity userEntity) {

        List<Address> addresses = userEntity.getEnderecos().stream()
                .map(addressEntity -> new Address(
                        addressEntity.getRua(),
                        addressEntity.getBairro(),
                        addressEntity.getComplemento(),
                        addressEntity.getNumero(),
                        addressEntity.getEstado(),
                        addressEntity.getCidade(),
                        addressEntity.getCep()
                )).toList();

        return new User(
                userEntity.getNome(),
                userEntity.getEmail(),
                userEntity.getLogin(),
                userEntity.getSenha(),
                userEntity.getDataAlteracao(),
                userEntity.getTipo().getNameType(),
                addresses
                );
    }

    public UserResponseDto toUserResponseDto(User user) {

        List<AddressDto> addressDtos = user.getAddress().stream()
                .map(address -> new AddressDto(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getCidade(),
                        address.getEstado(),
                        address.getCep()))  .toList();

        return new UserResponseDto(user.getNome(), user.getLogin(), user.getEmail(), user.getTipo(), addressDtos);
    }

    public UserResponseDto getUserByIdToUserResponseDto(Optional<User> optionalUser) {

        User user = optionalUser.orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        List<AddressDto> addressDtos = user.getAddress().stream()
                .map(address -> new AddressDto(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getCidade(),
                        address.getEstado(),
                        address.getCep()))
                .toList();

        return new UserResponseDto(user.getNome(), user.getLogin(), user.getEmail(), user.getTipo(), addressDtos);
    }

    public List<AddressEntity> toAddressEntityList(List<Address> addresses) {
        return addresses.stream()
                .map(address -> new AddressEntity(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getEstado(),
                        address.getCidade(),
                        address.getCep()))
                .toList();
    }

    public UserAuthDto toTokenResponseDto(Token token) {
        return new UserAuthDto(token.getAccessToken(), token.getExpiresIn());
    }

}