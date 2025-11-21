package com.example.users.services.imp;

import com.example.users.dto.RolDtoInfo;
import com.example.users.dto.UserDtoInfo;
import com.example.users.dto.login.UserLoginDto;
import com.example.users.dto.post.UserPostDto;
import com.example.users.dto.update.UserUpdateDto;
import com.example.users.entity.RolEntity;
import com.example.users.entity.UserEntity;
import com.example.users.jwt.JwtUtil;
import com.example.users.repository.UserRepository;
import com.example.users.services.RolService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    @InjectMocks
    private UserServiceImp userServiceImp;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RolService rolService;
    @Mock
    private JwtUtil jwtUtil;

    private UserEntity userEntity;
    private UserEntity userUpdate;
    private UserEntity userNotActive;
    private RolDtoInfo rolDtoInfo;
    @BeforeEach
    void setUp(){
        RolEntity rolEntity=new RolEntity();
        rolEntity.setId(1L);
        rolEntity.setDescription("ADMIN");

        rolDtoInfo=new RolDtoInfo();
        rolDtoInfo.setId(1L);
        rolDtoInfo.setDescription("ADMIN");

        userEntity=new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("mmm@gmail.com");
        userEntity.setRole(rolEntity);
        userEntity.setUsername("Mati");
        userEntity.setPassword("1234");
        userEntity.setActive(true);
        userEntity.setRegisterDate(LocalDate.now());

        userUpdate=new UserEntity();
        userUpdate.setId(1L);
        userUpdate.setEmail("mmm@gmail.com");
        userUpdate.setRole(rolEntity);
        userUpdate.setUsername("MatiPro");
        userUpdate.setPassword("1234");

        userNotActive=new UserEntity();
        userNotActive.setId(1L);
        userNotActive.setEmail("mmm@gmail.com");
        userNotActive.setRole(rolEntity);
        userNotActive.setUsername("MatiPro");
        userNotActive.setPassword("1234");
        userNotActive.setActive(false);

    }


    @Test
    void registerUser() {
        Mockito.when(userRepository.findByUsername("Mati")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail("mmm@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(rolService.getByDescription("ADMIN")).thenReturn(rolDtoInfo);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        UserPostDto userPostDto=new UserPostDto();
        userPostDto.setUsername("Mati");
        userPostDto.setEmail("mmm@gmail.com");
        userPostDto.setPassword("1234");
        userPostDto.setRol("ADMIN");
        UserDtoInfo response=userServiceImp.registerUser(userPostDto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("Mati",response.getUsername());
        Assertions.assertEquals("mmm@gmail.com",response.getEmail());
        Assertions.assertEquals("ADMIN",response.getRol());
    }
    @Test
    void registerUserUsernameExist() {
        Mockito.when(userRepository.findByUsername("Mati")).thenReturn(Optional.of(userEntity));
        UserPostDto userPostDto=new UserPostDto();
        userPostDto.setUsername("Mati");
        userPostDto.setEmail("mmm@gmail.com");
        userPostDto.setPassword("1234");
        userPostDto.setRol("ADMIN");
        IllegalArgumentException response=Assertions.assertThrows(IllegalArgumentException.class,()->{
            userServiceImp.registerUser(userPostDto);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Un usuario ya tiene registrado ese nombre",response.getMessage());
    }
    @Test
    void registerUserEmailExist() {
        Mockito.when(userRepository.findByUsername("Mati")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail("mmm@gmail.com")).thenReturn(Optional.of(userEntity));
        UserPostDto userPostDto=new UserPostDto();
        userPostDto.setUsername("Mati");
        userPostDto.setEmail("mmm@gmail.com");
        userPostDto.setPassword("1234");
        userPostDto.setRol("ADMIN");
        IllegalArgumentException response=Assertions.assertThrows(IllegalArgumentException.class,()->{
            userServiceImp.registerUser(userPostDto);
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Un usuario ya tiene registrado ese correo",response.getMessage());
    }

    @Test
    void userNameUpdate() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userUpdate));
        Mockito.when(userRepository.findByUsername("MatiPro")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userUpdate);
        UserUpdateDto userUpdateDto=new UserUpdateDto();
        userUpdateDto.setId(1L);
        userUpdateDto.setEmail("mmm@gmail.com");
        userUpdateDto.setPassword("1234");
        userUpdateDto.setUsername("MatiPro");
        UserDtoInfo response=userServiceImp.userNameUpdate(userUpdateDto);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("MatiPro",response.getUsername());
        Assertions.assertEquals("mmm@gmail.com",response.getEmail());
        Assertions.assertEquals("ADMIN",response.getRol());
    }

    @Test
    void unsubscribeUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userNotActive));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userNotActive);
        UserDtoInfo response=userServiceImp.unsubscribeUser(1L);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("MatiPro",response.getUsername());
        Assertions.assertEquals("mmm@gmail.com",response.getEmail());
        Assertions.assertEquals("ADMIN",response.getRol());
        Assertions.assertFalse(response.isActive());
    }

    @Test
    void login() {
        Mockito.when(userRepository.findByEmail("mmm@gmail.com")).thenReturn(Optional.of(userEntity));
        Mockito.when(jwtUtil.generateToken("mmm@gmail.com",1L)).thenReturn("1232Matimmmm");
        UserLoginDto userLoginDto=new UserLoginDto();
        userLoginDto.setEmail("mmm@gmail.com");
        userLoginDto.setPassword("1234");
        UserDtoInfo response=userServiceImp.login(userLoginDto);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("Mati",response.getUsername());
        Assertions.assertEquals("mmm@gmail.com",response.getEmail());
        Assertions.assertEquals("ADMIN",response.getRol());
        Assertions.assertEquals("1232Matimmmm",response.getToken());
    }

    @Test
    void getById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        UserDtoInfo response=userServiceImp.getById(1L);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("Mati",response.getUsername());
        Assertions.assertEquals("mmm@gmail.com",response.getEmail());
        Assertions.assertEquals("ADMIN",response.getRol());
    }
    @Test
    void getAll(){
        List<UserEntity>userEntityList=new ArrayList<>();
        userEntityList.add(userEntity);
        Mockito.when(userRepository.findAll()).thenReturn(userEntityList);
        List<UserDtoInfo>response=userServiceImp.getAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
    }
    @Test
    void getUserActiveStats(){
        Mockito.when(userRepository.countByActive(true)).thenReturn(5L);
        Mockito.when(userRepository.countByActive(false)).thenReturn(2L);
        Map<String,Long>response=this.userServiceImp.getUserActiveStats();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(5,response.get("active"));
        Assertions.assertEquals(2,response.get("inactive"));
    }
}