//package com.br.fiap.fortaleza.sabor.infrastructure.controller;
//
//import com.br.fiap.fortaleza.sabor.application.ports.out.MenuItemsRepositoryPort;
//import com.br.fiap.fortaleza.sabor.application.usecase.menu.MenuItemUseCase;
//import com.br.fiap.fortaleza.sabor.application.usecase.menu.DeleteMenuItemUseCase;
//import com.br.fiap.fortaleza.sabor.application.usecase.menu.GetMenuItemUseCase;
//import com.br.fiap.fortaleza.sabor.application.usecase.menu.UpdateMenuItemUseCase;
//import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
//import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
//import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateMenuItemRequestDto;
//import com.br.fiap.fortaleza.sabor.infrastructure.mapper.MenuMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.br.fiap.fortaleza.sabor.mock.MockMenu.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = MenuItemsController.class)
//@ExtendWith(MockitoExtension.class)
//class MenuItemsControllerTest {
//
//    @InjectMocks
//    private MenuItemsController menuItemsController;
//
//    @MockitoBean
//    private MenuMapper menuMapper;
//    @MockitoBean
//    private MenuItemsRepositoryPort menuItemsRepositoryPort;
//    @MockitoBean
//    private MenuItemUseCase menuItemUseCase;
//    @MockitoBean
//    private GetMenuItemUseCase getMenuItemUseCase;
//    @MockitoBean
//    private UpdateMenuItemUseCase updateMenuItemUseCase;
//    @MockitoBean
//    private DeleteMenuItemUseCase deleteMenuItemUseCase;
//
//    @BeforeEach
//    public void setUp() {
//        menuItemsController = new MenuItemsController(menuItemUseCase, getMenuItemUseCase, updateMenuItemUseCase, deleteMenuItemUseCase, menuMapper);
//    }
//
//    @Test
//    @DisplayName("Should engrave object MENU ITEM in database - return response HTTP 201 CREATE")
//    void create() throws Exception {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//
//        //GIVEN
//        var request = "{\n\t\"nome\": \"Pizza Margherita\",\n\t\"itemDescription\": \"Deliciosa pizza tradicional com molho de tomate, queijo mussarela e manjericão fresco\",\n\t\"itemPrice\": \"29.90\",\n\t\"availability\": true,\n\t\"itemImage\": \"https://exemplo.com/images/pizza-margherita.png\"\n}";
//        var requestDto = objectMapper.readValue(request, MenuItemRequestDto.class);
//        var mapper = menuMapper.toMenuDomain(requestDto);
//
//        //WHEN
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(menuItemsController).build();
//        when(menuItemUseCase.save(mapper)).thenReturn(menuItemMockOne());
//
//        mockMvc.perform(post("/cardapio")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andExpect(status().isCreated());
//
//        //THEN
//        verify(menuItemUseCase, times(1)).save(mapper);
//    }
//
//    @Test
//    @DisplayName("Should return a list of menus - return response HTTP 200 OK")
//    void getAll() throws Exception {
//        // GIVEN
//        var menuItemMockOne = menuItemMockOne();
//        var menuItemMockTwo = menuItemMockTwo();
//
//        // WHEN
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(menuItemsController).build();
//        when(menuMapper.toMenuItemResponseDto(menuItemMockOne)).thenReturn(responseDtoMockOne());
//        when(menuMapper.toMenuItemResponseDto(menuItemMockTwo)).thenReturn(responseDtoMockTwo());
//        when(menuItemsRepositoryPort.getAll()).thenReturn(List.of(menuItemMockOne, menuItemMockTwo));
//        when(getMenuItemUseCase.getAll()).thenReturn(List.of(menuItemMockOne, menuItemMockTwo));
//
//        mockMvc.perform(get("/cardapio")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json("""
//            [
//                { "nome": "Pizza Margherita" },
//                { "nome": "Spaghetti Carbonara" }
//            ]
//        """));
//
//        // THEN
//        verify(getMenuItemUseCase, times(1)).getAll();
//    }
//
//
//    @Test
//    @DisplayName("Should return menu item by ID - return response HTTP 201 CREATED")
//    void getById() throws Exception {
//        // GIVEN
//        var menuItem = menuItemMockOne();
//        var responseDto = responseDtoMockOne();
//
//        // WHEN
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(menuItemsController).build();
//        when(getMenuItemUseCase.getById(1L)).thenReturn(Optional.of(menuItem));
//        when(menuMapper.getMenuByIdToMenuResponseDto(Optional.of(menuItem))).thenReturn(responseDto);
//
//        // THEN
//        mockMvc.perform(get("/cardapio/{idMenu}", 1L)
//                        .param("idMenu", "1"))
//                .andExpect(status().isAccepted());
//
//        verify(getMenuItemUseCase, times(1)).getById(1L);
//    }
//
//
//    @Test
//    @DisplayName("Should delete menu item by ID - return response HTTP 204 NO CONTENT")
//    void deleteById() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(menuItemsController).build();
//
//        mockMvc.perform(delete("/cardapio/{idMenu}", 1L)
//                        .param("idMenu", "1"))
//                .andExpect(status().isNoContent());
//
//        verify(deleteMenuItemUseCase, times(1)).delete(1L);
//    }
//
//    @Test
//    @DisplayName("Should update menu successfully - return HTTP 202 response")
//    void shouldUpdateMenuItemSuccessfully() throws Exception {
//
//        // GIVEN
//        UpdateMenuItemRequestDto dto = new UpdateMenuItemRequestDto(
//                "Pizza Quatro Queijos",
//                "Pizza com mix de queijos: mussarela, provolone, parmesão e gorgonzola",
//                "34.90",
//                true,
//                "https://exemplo.com/images/pizza-quatro-queijos.png"
//        );
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(menuItemsController).build();
//        when(menuMapper.updateToMenuDomain(dto)).thenReturn(menuItemMockOne());
//
//        // WHEN
//        mockMvc.perform(put("/cardapio/1")
//                        .content(new ObjectMapper().writeValueAsString(dto))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isAccepted());
//
//        // THEN
//        verify(updateMenuItemUseCase, times(1)).update(eq(1L), any(MenuItem.class));
//    }
//}