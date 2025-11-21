package com.example.users.services.imp;

import com.example.users.dto.RolDtoInfo;
import com.example.users.entity.RolEntity;
import com.example.users.repository.RolRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RolServiceImpTest {
    @InjectMocks
    private RolServiceImp rolServiceImp;
    @Mock
    private RolRepository rolRepository;

    private RolEntity rolEntity;
    private RolEntity rolEntity1;
    @BeforeEach
    void setUp(){
    rolEntity=new RolEntity();
    rolEntity.setId(1L);
    rolEntity.setDescription("ADMIN");

    rolEntity1=new RolEntity();
    rolEntity1.setId(2L);
    rolEntity1.setDescription("USER");
    }
    @Test
    void getByDescription() {
        Mockito.when(rolRepository.findByDescription("ADMIN")).thenReturn(Optional.of(rolEntity));
        RolDtoInfo response=rolServiceImp.getByDescription("ADMIN");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L,response.getId());
        Assertions.assertEquals("ADMIN",response.getDescription());
    }
    @Test
    void getByDescriptionError(){
        Mockito.when(rolRepository.findByDescription("ADMIN")).thenReturn(Optional.empty());
        RuntimeException response=Assertions.assertThrows(RuntimeException.class,()->{
            rolServiceImp.getByDescription("ADMIN");
        });
        Assertions.assertNotNull(response);
        Assertions.assertEquals("The rol not exist",response.getMessage());
    }

    @Test
    void getAll() {
        List<RolEntity> rolEntityList=new ArrayList<>();
        rolEntityList.add(rolEntity);
        rolEntityList.add(rolEntity1);
        Mockito.when(rolRepository.findAll()).thenReturn(rolEntityList);
        List<RolDtoInfo>response=rolServiceImp.getAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2,response.size());
        Assertions.assertEquals(1L,response.get(0).getId());
        Assertions.assertEquals(2L,response.get(1).getId());
    }
}