package com.example.users.services;

import com.example.users.dto.RolDtoInfo;

import java.util.List;

public interface RolService {
    RolDtoInfo getByDescription(String description);
    List<RolDtoInfo>getAll();
}
