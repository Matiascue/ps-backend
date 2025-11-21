package com.example.users.services.imp;

import com.example.users.dto.RolDtoInfo;
import com.example.users.entity.RolEntity;
import com.example.users.repository.RolRepository;
import com.example.users.services.RolService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImp implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImp(RolRepository rolRepository){
        this.rolRepository=rolRepository;
    }

    @Override
    public RolDtoInfo getByDescription(String description) {
        Optional<RolEntity>rolEntity=rolRepository.findByDescription(description);
        RolDtoInfo rolDtoInfo=new RolDtoInfo();
        if(rolEntity.isPresent()){
            rolDtoInfo.setId(rolEntity.get().getId());
            rolDtoInfo.setDescription(rolEntity.get().getDescription());
        }
        else{
            throw new RuntimeException("The rol not exist");
        }
        return rolDtoInfo;
    }

    @Override
    public List<RolDtoInfo> getAll() {
        List<RolEntity>rolEntities=rolRepository.findAll();
        List<RolDtoInfo>rolDtoInfos=new ArrayList<>();
        for(RolEntity r:rolEntities){
            RolDtoInfo rolDtoInfo=new RolDtoInfo();
            rolDtoInfo.setId(r.getId());
            rolDtoInfo.setDescription(r.getDescription());
            rolDtoInfos.add(rolDtoInfo);
        }
        return rolDtoInfos;
    }
}
