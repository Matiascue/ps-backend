package com.example.users.dataLoader;

import com.example.users.entity.RolEntity;
import com.example.users.entity.UserEntity;
import com.example.users.repository.RolRepository;
import com.example.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {
    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    public DataLoader(RolRepository rolRepository,UserRepository userRepository){
        this.rolRepository=rolRepository;
        this.userRepository=userRepository;
    }
    @Override
    public void run(String... args) throws Exception {
       if(rolRepository.findByDescription("SUPERADMIN").isEmpty()){
           RolEntity rolEntity=new RolEntity();
           rolEntity.setDescription("SUPERADMIN");
           rolRepository.save(rolEntity);
       }
        if(rolRepository.findByDescription("ADMIN").isEmpty()){
            RolEntity rolEntity=new RolEntity();
            rolEntity.setDescription("ADMIN");
            rolRepository.save(rolEntity);
        }
        if(rolRepository.findByDescription("USER").isEmpty()){
            RolEntity rolEntity=new RolEntity();
            rolEntity.setDescription("USER");
            rolRepository.save(rolEntity);
        }
       if(userRepository.findById(1L).isEmpty()){
           Optional<RolEntity>entity=rolRepository.findByDescription("SUPERADMIN");
           UserEntity userEntity=new UserEntity();
           userEntity.setActive(true);
           userEntity.setEmail("superAdmin@gmail.com");
           userEntity.setPassword("supA1234");
           userEntity.setUsername("TheOne");
           userEntity.setRegisterDate(LocalDate.now());
           userEntity.setRole(entity.get());
           userRepository.save(userEntity);
       }
    }
}
