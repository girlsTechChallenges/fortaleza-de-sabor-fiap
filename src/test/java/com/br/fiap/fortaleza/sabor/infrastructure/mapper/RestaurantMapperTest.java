package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.BusinessHoursDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RestaurantMapper - Testes unitários")
class RestaurantMapperTest {

    @InjectMocks
    private RestaurantMapper restaurantMapper;

    private AddressDto addressDto;
    private BusinessHoursDto businessHoursDto;
    private RestaurantRequestDto restaurantRequestDto;

    @BeforeEach
    void setUp() {
        addressDto = new AddressDto(
                "Street das Flores",
                "Centro",
                "Apto 45",
                123,
                "SP",
                "São Paulo",
                "01234567"
        );

        businessHoursDto = new BusinessHoursDto(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                "Funcionamento normal"
        );

        restaurantRequestDto = new RestaurantRequestDto(
                "Restaurant do João",
                "Brasileira",
                "joao@restaurant.com",
                Arrays.asList(addressDto),
                Arrays.asList(businessHoursDto)
        );
    }

    @Test
    @DisplayName("Deve mapear RestaurantRequestDto para Restaurant com sucesso")
    void deveMapearRestaurantRequestDtoParaRestaurantComSucesso() {
        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Restaurant do João", result.getName());
        assertEquals("Brasileira", result.getKitchenType());
        assertEquals("joao@restaurant.com", result.getEmail());
        
        // Validar endereço
        assertNotNull(result.getAddress());
        assertEquals(1, result.getAddress().size());
        Address mappedAddress = result.getAddress().get(0);
        assertEquals("Street das Flores", mappedAddress.getStreet());
        assertEquals(123, mappedAddress.getNumber());
        assertEquals("Apto 45", mappedAddress.getComplement());
        assertEquals("Centro", mappedAddress.getDistrict());
        assertEquals("São Paulo", mappedAddress.getCity());
        assertEquals("SP", mappedAddress.getState());
        assertEquals("01234567", mappedAddress.getPostCode());
        
        // Validar horário de funcionamento
        assertNotNull(result.getBusinessHours());
        assertEquals(1, result.getBusinessHours().size());
        BusinessHours mappedBusinessHours = result.getBusinessHours().get(0);
        assertEquals(DayOfWeek.MONDAY, mappedBusinessHours.getDayOfWeek());
        assertEquals(LocalTime.of(8, 0), mappedBusinessHours.getOpeningTime());
        assertEquals(LocalTime.of(18, 0), mappedBusinessHours.getClosingTime());
        assertEquals("Funcionamento normal", mappedBusinessHours.getObservations());
    }

    @Test
    @DisplayName("Deve mapear Restaurant com múltiplos endereços corretamente")
    void deveMapearRestaurantComMultiplosEnderecosCorretamente() {
        // Arrange
        AddressDto secondAddressDto = new AddressDto(
                "Avenida Paulista",
                "Bela Vista",
                "Loja 15",
                1000,
                "SP",
                "São Paulo",
                "01310100"
        );

        RestaurantRequestDto restaurantWithMultipleAddresses = new RestaurantRequestDto(
                "Pizzaria da Maria",
                "Italiana",
                "maria@pizzaria.com",
                Arrays.asList(addressDto, secondAddressDto),
                Arrays.asList(businessHoursDto)
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithMultipleAddresses);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getAddress().size());
        
        Address firstAddress = result.getAddress().get(0);
        assertEquals("Street das Flores", firstAddress.getStreet());
        assertEquals(123, firstAddress.getNumber());
        
        Address secondMappedAddress = result.getAddress().get(1);
        assertEquals("Avenida Paulista", secondMappedAddress.getStreet());
        assertEquals(1000, secondMappedAddress.getNumber());
        assertEquals("Loja 15", secondMappedAddress.getComplement());
        assertEquals("Bela Vista", secondMappedAddress.getDistrict());
    }

    @Test
    @DisplayName("Deve mapear Restaurant com múltiplos horários de funcionamento corretamente")
    void deveMapearRestaurantComMultiplosHorariosFuncionamentoCorretamente() {
        // Arrange
        BusinessHoursDto weekendHours = new BusinessHoursDto(
                DayOfWeek.SATURDAY,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                "Horário de fim de semana"
        );

        RestaurantRequestDto restaurantWithMultipleHours = new RestaurantRequestDto(
                "Café da Esquina",
                "Café",
                "cafe@esquina.com",
                Arrays.asList(addressDto),
                Arrays.asList(businessHoursDto, weekendHours)
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithMultipleHours);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getBusinessHours().size());
        
        BusinessHours firstHours = result.getBusinessHours().get(0);
        assertEquals(DayOfWeek.MONDAY, firstHours.getDayOfWeek());
        assertEquals(LocalTime.of(8, 0), firstHours.getOpeningTime());
        
        BusinessHours secondHours = result.getBusinessHours().get(1);
        assertEquals(DayOfWeek.SATURDAY, secondHours.getDayOfWeek());
        assertEquals(LocalTime.of(10, 0), secondHours.getOpeningTime());
        assertEquals(LocalTime.of(22, 0), secondHours.getClosingTime());
        assertEquals("Horário de fim de semana", secondHours.getObservations());
    }

    @Test
    @DisplayName("Deve mapear Restaurant sem endereços quando lista estiver vazia")
    void deveMapearRestaurantSemEnderecosQuandoListaEstiverVazia() {
        // Arrange
        RestaurantRequestDto restaurantWithoutAddresses = new RestaurantRequestDto(
                "Restaurant Virtual",
                "Delivery",
                "virtual@delivery.com",
                Collections.emptyList(),
                Arrays.asList(businessHoursDto)
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithoutAddresses);

        // Assert
        assertNotNull(result);
        assertEquals("Restaurant Virtual", result.getName());
        assertEquals("Delivery", result.getKitchenType());
        assertEquals("virtual@delivery.com", result.getEmail());
        assertNotNull(result.getAddress());
        assertTrue(result.getAddress().isEmpty());
        assertEquals(1, result.getBusinessHours().size());
    }

    @Test
    @DisplayName("Deve mapear Restaurant sem horários de funcionamento quando lista estiver vazia")
    void deveMapearRestaurantSemHorariosFuncionamentoQuandoListaEstiverVazia() {
        // Arrange
        RestaurantRequestDto restaurantWithoutHours = new RestaurantRequestDto(
                "Restaurant 24h",
                "Fast Food",
                "24h@fastfood.com",
                Arrays.asList(addressDto),
                Collections.emptyList()
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithoutHours);

        // Assert
        assertNotNull(result);
        assertEquals("Restaurant 24h", result.getName());
        assertEquals("Fast Food", result.getKitchenType());
        assertEquals("24h@fastfood.com", result.getEmail());
        assertEquals(1, result.getAddress().size());
        assertNotNull(result.getBusinessHours());
        assertTrue(result.getBusinessHours().isEmpty());
    }

    @Test
    @DisplayName("Deve mapear endereço sem complement corretamente")
    void deveMapearEnderecoSemComplementoCorretamente() {
        // Arrange
        AddressDto addressWithoutComplement = new AddressDto(
                "Street da Praia",
                "Copacabana",
                null,
                456,
                "RJ",
                "Rio de Janeiro",
                "22070900"
        );

        RestaurantRequestDto restaurantWithAddressWithoutComplement = new RestaurantRequestDto(
                "Lanchonete da Praia",
                "Lanches",
                "praia@lanches.com",
                Arrays.asList(addressWithoutComplement),
                Arrays.asList(businessHoursDto)
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithAddressWithoutComplement);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getAddress().size());
        Address mappedAddress = result.getAddress().get(0);
        assertEquals("Street da Praia", mappedAddress.getStreet());
        assertEquals(456, mappedAddress.getNumber());
        assertNull(mappedAddress.getComplement());
        assertEquals("Copacabana", mappedAddress.getDistrict());
        assertEquals("Rio de Janeiro", mappedAddress.getCity());
        assertEquals("RJ", mappedAddress.getState());
        assertEquals("22070900", mappedAddress.getPostCode());
    }

    @Test
    @DisplayName("Deve mapear horário de funcionamento sem observações corretamente")
    void deveMapearHorarioFuncionamentoSemObservacoesCorretamente() {
        // Arrange
        BusinessHoursDto hoursWithoutObservations = new BusinessHoursDto(
                DayOfWeek.SUNDAY,
                LocalTime.of(12, 0),
                LocalTime.of(20, 0),
                null
        );

        RestaurantRequestDto restaurantWithHoursWithoutObservations = new RestaurantRequestDto(
                "Restaurant Domingo",
                "Familiar",
                "domingo@familiar.com",
                Arrays.asList(addressDto),
                Arrays.asList(hoursWithoutObservations)
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithHoursWithoutObservations);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getBusinessHours().size());
        BusinessHours mappedHours = result.getBusinessHours().get(0);
        assertEquals(DayOfWeek.SUNDAY, mappedHours.getDayOfWeek());
        assertEquals(LocalTime.of(12, 0), mappedHours.getOpeningTime());
        assertEquals(LocalTime.of(20, 0), mappedHours.getClosingTime());
        assertNull(mappedHours.getObservations());
    }

    @Test
    @DisplayName("Deve mapear horários com precisão de minutos corretamente")
    void deveMapearHorariosComPrecisaoMinutosCorretamente() {
        // Arrange
        BusinessHoursDto preciseHours = new BusinessHoursDto(
                DayOfWeek.TUESDAY,
                LocalTime.of(7, 30),
                LocalTime.of(23, 45),
                "Horário estendido"
        );

        RestaurantRequestDto restaurantWithPreciseHours = new RestaurantRequestDto(
                "Restaurant Preciso",
                "Internacional",
                "preciso@internacional.com",
                Arrays.asList(addressDto),
                Arrays.asList(preciseHours)
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithPreciseHours);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getBusinessHours().size());
        BusinessHours mappedHours = result.getBusinessHours().get(0);
        assertEquals(DayOfWeek.TUESDAY, mappedHours.getDayOfWeek());
        assertEquals(LocalTime.of(7, 30), mappedHours.getOpeningTime());
        assertEquals(LocalTime.of(23, 45), mappedHours.getClosingTime());
        assertEquals("Horário estendido", mappedHours.getObservations());
    }

    @Test
    @DisplayName("Deve mapear Restaurant com caracteres especiais corretamente")
    void deveMapearRestaurantComCaracteresEspeciaisCorretamente() {
        // Arrange
        RestaurantRequestDto restaurantWithSpecialChars = new RestaurantRequestDto(
                "Açaí & Cia",
                "Sobremesas & Sucos",
                "acai@cia.com.br",
                Arrays.asList(addressDto),
                Arrays.asList(businessHoursDto)
        );

        // Act
        Restaurant result = restaurantMapper.toRestaurantDomain(restaurantWithSpecialChars);

        // Assert
        assertNotNull(result);
        assertEquals("Açaí & Cia", result.getName());
        assertEquals("Sobremesas & Sucos", result.getKitchenType());
        assertEquals("acai@cia.com.br", result.getEmail());
        assertEquals(1, result.getAddress().size());
        assertEquals(1, result.getBusinessHours().size());
    }
}
