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
import com.example.users.services.ExternService;
import com.example.users.services.RolService;
import com.example.users.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RolService rolService;
    private final JwtUtil jwtUtil;
    private final ExternService externService;

    public UserServiceImp(UserRepository userRepository,RolService rolService
    ,JwtUtil jwtUtil,ExternService externService){
        this.userRepository=userRepository;
        this.rolService=rolService;
        this.jwtUtil=jwtUtil;
        this.externService=externService;
    }
    @Override
    public UserDtoInfo registerUser(UserPostDto userPostDto) {
        this.userNameValidate(userPostDto.getUsername());
        this.emailValidation(userPostDto.getEmail());
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(userPostDto.getUsername());
        userEntity.setEmail(userPostDto.getEmail());
        userEntity.setPassword(userPostDto.getPassword());
        userEntity.setActive(true);
        userEntity.setRegisterDate(LocalDate.now());
        userEntity.setRole(this.getRolBYDesc(userPostDto.getRol()));
        userEntity=userRepository.save(userEntity);
        this.externService.sendWelcomeMail(userEntity.getEmail(),userEntity.getId(),userEntity.getUsername());
        return this.mapToInfoDto(userEntity);
    }

    @Override
    public UserDtoInfo userNameUpdate(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro"));
        this.userNameValidate(userUpdateDto.getUsername());
        userEntity.setUsername(userUpdateDto.getUsername());
        userEntity=this.userRepository.save(userEntity);
        return this.mapToInfoDto(userEntity);
    }

    @Override
    public UserDtoInfo unsubscribeUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro"));
        userEntity.setActive(false);
        userEntity=userRepository.save(userEntity);
        return this.mapToInfoDto(userEntity);
    }

    @Override
    public UserDtoInfo login(UserLoginDto userLoginDto) {
        UserEntity userEntity = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro con ese correo"));
        if(userLoginDto.getPassword().equals(userEntity.getPassword())&&userEntity.isActive()){
            UserDtoInfo userDtoInfo=this.mapToInfoDto(userEntity);
            userDtoInfo.setToken(jwtUtil.generateToken(userLoginDto.getEmail(),userEntity.getId()));
            return userDtoInfo;
        }else {
            throw new IllegalArgumentException("No tienes permiso");
        }
    }

    @Override
    public UserDtoInfo getById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no se encontro"));
        return this.mapToInfoDto(userEntity);
    }

    @Override
    public List<UserDtoInfo> getAll() {
        List<UserEntity>userEntityList=this.userRepository.findAll();
        List<UserDtoInfo>userDtoInfos=new ArrayList<>();
        for(UserEntity u:userEntityList){
            if(u.isActive()){
                UserDtoInfo userDtoInfo=this.mapToInfoDto(u);
                userDtoInfos.add(userDtoInfo);
            }
        }
        return userDtoInfos;
    }

    @Override
    public Map<String, Long> getUserActiveStats() {
        long activeUsers=this.userRepository.countByActive(true);
        long inactiveUsers=this.userRepository.countByActive(false);
        Map<String,Long>stats=new HashMap<>();
        stats.put("active",activeUsers);
        stats.put("inactive",inactiveUsers);
        return stats;
    }

    @Override
    public String uploadProfilePicture(Long userId, MultipartFile file) throws IOException {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        user.setProfilePicture("/uploads/" + fileName);
        user=userRepository.save(user);
        return user.getProfilePicture();
    }

    private void userNameValidate(String username){
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Un usuario ya tiene registrado ese nombre");
                });
    }

    private void emailValidation(String email){
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Un usuario ya tiene registrado ese correo");
                });
    }
    private RolEntity getRolBYDesc(String description){
        RolDtoInfo rolDtoInfo=rolService.getByDescription(description);
        RolEntity rolEntity=new RolEntity();
        rolEntity.setId(rolDtoInfo.getId());
        rolEntity.setDescription(rolDtoInfo.getDescription());
        return rolEntity;
    }
    private UserDtoInfo mapToInfoDto(UserEntity userEntity){
        UserDtoInfo userDtoInfo=new UserDtoInfo();
        userDtoInfo.setId(userEntity.getId());
        userDtoInfo.setActive(userEntity.isActive());
        userDtoInfo.setUsername(userEntity.getUsername());
        userDtoInfo.setEmail(userEntity.getEmail());
        userDtoInfo.setPassword(userEntity.getPassword());
        userDtoInfo.setRol(userEntity.getRole().getDescription());
        userDtoInfo.setRegisterDate(userEntity.getRegisterDate());
        userDtoInfo.setLastPasswordChangeDate(userEntity.getLastPasswordChangeDate());
        userDtoInfo.setExpirationDate(userEntity.getExpirationDate());
        userDtoInfo.setProfilePicture(userEntity.getProfilePicture());
        return  userDtoInfo;
    }
}
