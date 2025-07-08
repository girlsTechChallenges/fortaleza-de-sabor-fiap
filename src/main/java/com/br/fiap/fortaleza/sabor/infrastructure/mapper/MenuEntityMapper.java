package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.menu.Menu;
import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.*;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.MenuEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuEntityMapper {
//    public MenuItem toMenuDomain(MenuRequestDto menuRequestDto) {
//        return new MenuItem(
//                menuRequestDto.nome(),
//                menuRequestDto.itemDescription(),
//                menuRequestDto.itemPrice(),
//                menuRequestDto.availability()
//                menuRequestDto.itemImage()
//                );
//    }

//    public User updateToUserDomain(UpdateRequestDto updateRequestDto) {
//        return new User(
//                updateRequestDto.nome(),
//                updateRequestDto.email(),
//                updateRequestDto.senha(),
//                TypeEnum.valueOf(updateRequestDto.tipo().name()),
//                addresses);
//    }

//    public MenuEntity toUserEntity(MenuItem menuItem) {
//        return new MenuEntity(
//                menuItem.getNome(),
//                menuItem.getItemDescription(),
//                menuItem.getItemPrice(),
//                menuItem.getAvailability(),
//                menuItem.getItemImage()
//        );
//    }

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
                TypeEnum.valueOf(userEntity.getTipo().name()),
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
}
