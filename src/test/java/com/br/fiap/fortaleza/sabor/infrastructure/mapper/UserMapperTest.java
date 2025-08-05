package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserMapper Tests")
class UserMapperTest {

    @Mock
    private TypeUserMapper typeUserMapper;

    @InjectMocks
    private UserMapper userMapper;

    private UserRequestDto userRequestDto;
    private UpdateRequestDto updateRequestDto;
    private AddressDto addressDto;
    private List<AddressDto> addressDtoList;

    @BeforeEach
    void setUp() {
        addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apt 45",
                123,
                "SP",
                "São Paulo",
                "12345678"
        );
        
        addressDtoList = Arrays.asList(addressDto);

        userRequestDto = new UserRequestDto(
                "João Silva",
                "joao.silva@test.com",
                "joaosilva",
                "password123",
                LocalDate.now(),
                "CLIENTE",
                addressDtoList
        );

        updateRequestDto = new UpdateRequestDto(
                "João Silva Atualizado",
                "joao.updated@test.com",
                "newpassword123",
                "ADMIN",
                addressDtoList
        );
    }

    @Test
    @DisplayName("Should convert UserRequestDto to User domain correctly")
    void shouldConvertUserRequestDtoToUserDomainCorrectly() {
        // Act
        User result = userMapper.toUserDomain(userRequestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("João Silva");
        assertThat(result.getEmail()).isEqualTo("joao.silva@test.com");
        assertThat(result.getLogin()).isEqualTo("joaosilva");
        assertThat(result.getPassword()).isEqualTo("password123");
        assertThat(result.getChangeDate()).isEqualTo(userRequestDto.changeDate());
        assertThat(result.getType()).isEqualTo("CLIENTE");
        
        assertThat(result.getAddress()).isNotNull();
        assertThat(result.getAddress()).hasSize(1);
        
        Address mappedAddress = result.getAddress().get(0);
        assertThat(mappedAddress.getStreet()).isEqualTo("Street das Flores");
        assertThat(mappedAddress.getDistrict()).isEqualTo("Centro");
        assertThat(mappedAddress.getComplement()).isEqualTo("Apt 45");
        assertThat(mappedAddress.getNumber()).isEqualTo(123);
        assertThat(mappedAddress.getState()).isEqualTo("SP");
        assertThat(mappedAddress.getCity()).isEqualTo("São Paulo");
        assertThat(mappedAddress.getPostCode()).isEqualTo("12345678");
    }

    @Test
    @DisplayName("Should convert UpdateRequestDto to User domain correctly")
    void shouldConvertUpdateRequestDtoToUserDomainCorrectly() {
        // Act
        User result = userMapper.updateToUserDomain(updateRequestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("João Silva Atualizado");
        assertThat(result.getEmail()).isEqualTo("joao.updated@test.com");
        assertThat(result.getPassword()).isEqualTo("newpassword123");
        assertThat(result.getType()).isEqualTo("ADMIN");
        
        assertThat(result.getAddress()).isNotNull();
        assertThat(result.getAddress()).hasSize(1);
        
        Address mappedAddress = result.getAddress().get(0);
        assertThat(mappedAddress.getStreet()).isEqualTo("Street das Flores");
        assertThat(mappedAddress.getDistrict()).isEqualTo("Centro");
        assertThat(mappedAddress.getComplement()).isEqualTo("Apt 45");
        assertThat(mappedAddress.getNumber()).isEqualTo(123);
        assertThat(mappedAddress.getState()).isEqualTo("SP");
        assertThat(mappedAddress.getCity()).isEqualTo("São Paulo");
        assertThat(mappedAddress.getPostCode()).isEqualTo("12345678");
    }

    @Test
    @DisplayName("Should handle multiple addresses when converting UserRequestDto")
    void shouldHandleMultipleAddressesWhenConvertingUserRequestDto() {
        // Arrange
        AddressDto anotherAddress = new AddressDto(
                "Avenida Paulista",
                "Bela Vista",
                "Sala 1001",
                1000,
                "SP",
                "São Paulo",
                "01310200"
        );
        
        List<AddressDto> multipleAddresses = Arrays.asList(addressDto, anotherAddress);
        
        UserRequestDto userWithMultipleAddresses = new UserRequestDto(
                "Maria Santos",
                "maria@test.com",
                "maria123",
                "password456",
                LocalDate.now(),
                "PROPRIETARIO",
                multipleAddresses
        );

        // Act
        User result = userMapper.toUserDomain(userWithMultipleAddresses);

        // Assert
        assertThat(result.getAddress()).hasSize(2);
        
        Address firstAddress = result.getAddress().get(0);
        assertThat(firstAddress.getStreet()).isEqualTo("Street das Flores");
        assertThat(firstAddress.getPostCode()).isEqualTo("12345678");
        
        Address secondAddress = result.getAddress().get(1);
        assertThat(secondAddress.getStreet()).isEqualTo("Avenida Paulista");
        assertThat(secondAddress.getPostCode()).isEqualTo("01310200");
    }

    @Test
    @DisplayName("Should handle empty address list when converting UserRequestDto")
    void shouldHandleEmptyAddressListWhenConvertingUserRequestDto() {
        // Arrange
        UserRequestDto userWithoutAddresses = new UserRequestDto(
                "Pedro Santos",
                "pedro@test.com",
                "pedro123",
                "password789",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList()
        );

        // Act
        User result = userMapper.toUserDomain(userWithoutAddresses);

        // Assert
        assertThat(result.getAddress()).isNotNull();
        assertThat(result.getAddress()).isEmpty();
    }

    @Test
    @DisplayName("Should handle null changeDate when converting UserRequestDto")
    void shouldHandleNullDataAlteracaoWhenConvertingUserRequestDto() {
        // Arrange
        UserRequestDto userWithNullDate = new UserRequestDto(
                "Ana Costa",
                "ana@test.com",
                "ana123",
                "password000",
                null,
                "CLIENTE",
                addressDtoList
        );

        // Act
        User result = userMapper.toUserDomain(userWithNullDate);

        // Assert
        assertThat(result.getChangeDate()).isNull();
        assertThat(result.getName()).isEqualTo("Ana Costa");
        assertThat(result.getEmail()).isEqualTo("ana@test.com");
    }

    @Test
    @DisplayName("Should preserve address complement as null when not provided")
    void shouldPreserveAddressComplementAsNullWhenNotProvided() {
        // Arrange
        AddressDto addressWithoutComplement = new AddressDto(
                "Street Principal",
                "Centro",
                null,
                456,
                "RJ",
                "Rio de Janeiro",
                "22000000"
        );
        
        UserRequestDto userWithAddressWithoutComplement = new UserRequestDto(
                "Carlos Lima",
                "carlos@test.com",
                "carlos123",
                "password111",
                LocalDate.now(),
                "CLIENTE",
                Arrays.asList(addressWithoutComplement)
        );

        // Act
        User result = userMapper.toUserDomain(userWithAddressWithoutComplement);

        // Assert
        Address mappedAddress = result.getAddress().get(0);
        assertThat(mappedAddress.getComplement()).isNull();
        assertThat(mappedAddress.getStreet()).isEqualTo("Street Principal");
        assertThat(mappedAddress.getNumber()).isEqualTo(456);
    }

    @Test
    @DisplayName("Should convert UpdateRequestDto with different address data")
    void shouldConvertUpdateRequestDtoWithDifferentAddressData() {
        // Arrange
        AddressDto differentAddress = new AddressDto(
                "Street Nova",
                "Novo District",
                "Casa",
                789,
                "MG",
                "Belo Horizonte",
                "30000000"
        );
        
        UpdateRequestDto updateWithDifferentAddress = new UpdateRequestDto(
                "Novo Name",
                "novo@test.com",
                "novasenha",
                "PROPRIETARIO",
                Arrays.asList(differentAddress)
        );

        // Act
        User result = userMapper.updateToUserDomain(updateWithDifferentAddress);

        // Assert
        assertThat(result.getName()).isEqualTo("Novo Name");
        assertThat(result.getEmail()).isEqualTo("novo@test.com");
        assertThat(result.getType()).isEqualTo("PROPRIETARIO");
        
        Address mappedAddress = result.getAddress().get(0);
        assertThat(mappedAddress.getStreet()).isEqualTo("Street Nova");
        assertThat(mappedAddress.getDistrict()).isEqualTo("Novo District");
        assertThat(mappedAddress.getState()).isEqualTo("MG");
        assertThat(mappedAddress.getCity()).isEqualTo("Belo Horizonte");
    }
}
