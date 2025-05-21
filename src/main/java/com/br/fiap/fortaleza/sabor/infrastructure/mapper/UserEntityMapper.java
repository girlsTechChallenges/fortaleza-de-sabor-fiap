// package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

// import com.br.fiap.fortaleza.sabor.domain.address.Address;
// import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
// import com.br.fiap.fortaleza.sabor.domain.user.User;
// import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
// import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
// import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
// import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
// import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
// import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
// import org.springframework.stereotype.Component;

// import java.util.List;

// @Component
// public class UserEntityMapper {

//     public UserEntity toUserEntity(User user) {

//         List<AddressEntity> addressEntities = user.getAddress()
//                 .stream().map(address -> new AddressEntity(
//                         address.getRua(),
//                         address.getBairro(),
//                         address.getComplemento(),
//                         address.getNumero(),
//                         address.getEstado(),
//                         address.getCidade(), address.getCep())).toList();

//         return new UserEntity(
//                 user.getNome(),
//                 user.getEmail(),
//                 user.getLogin(),
//                 user.getDataAlteracao(),
//                 TypeEntityEnum.valueOf(user.getTipo().name()),
//                 addressEntities,
//                 user.getSenha());
//     }

//     public User toUserDomain(UserEntity userEntity) {

//         List<Address> addresses = userEntity.getEnderecos().stream()
//                 .map(addressEntity -> new Address(
//                         addressEntity.getRua(),
//                         addressEntity.getBairro(),
//                         addressEntity.getComplemento(),
//                         addressEntity.getNumero(),
//                         addressEntity.getEstado(),
//                         addressEntity.getCidade(),
//                         addressEntity.getCep()
//                 )).toList();

//         return new User(
//                 userEntity.getNome(),
//                 userEntity.getId(),
//                 userEntity.getEmail(),
//                 userEntity.getLogin(),
//                 userEntity.getSenha(),
//                 userEntity.getDataAlteracao(), TypeEnum.valueOf(userEntity.getTipo().name()),
//                 addresses);
//     }

//     public User toUserDomain(UserRequestDto userRequestDto) {

//         List<Address> addresses = userRequestDto.address().stream()
//                 .map(addressEntity -> new Address(
//                         addressEntity.rua(),
//                         addressEntity.bairro(),
//                         addressEntity.complemento(),
//                         addressEntity.numero(),
//                         addressEntity.estado(),
//                         addressEntity.cidade(),
//                         addressEntity.cep()
//                 )).toList();

//         return new User(
//                 userRequestDto.nome(),
//                null,
//                 userRequestDto.email(),
//                 userRequestDto.login(),
//                 userRequestDto.senha(),
//                 userRequestDto.dataAlteracao(), TypeEnum.valueOf(userRequestDto.tipo().name()),
//                 addresses);
//     }

//     public UserResponseDto toUserResponseDto(User user) {

//         List<AddressDto> addressDtos = user.getAddress().stream()
//                 .map(address -> new AddressDto(
//                         address.getRua(),
//                         address.getBairro(),
//                         address.getComplemento(),
//                         address.getNumero(),
//                         address.getCidade(),
//                         address.getEstado(),
//                         address.getCep()))  .toList();

//         return new UserResponseDto(user.getId(), user.getNome(), user.getLogin(), user.getEmail(), user.getTipo(), addressDtos);
//     }
// }
