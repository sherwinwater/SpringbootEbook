package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Role;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findFirstByName(String roleName){
        return roleRepository.findFirstByName(roleName);
    }
}
