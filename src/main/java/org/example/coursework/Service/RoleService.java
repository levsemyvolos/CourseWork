package org.example.coursework.Service;

import org.example.coursework.model.Role;
import org.example.coursework.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String name) {
        logger.info("Создание новой роли: {}", name);
        Role role = new Role(name);
        Role savedRole = roleRepository.save(role);
        logger.info("Роль успешно создана: {}", savedRole);
        return savedRole;
    }

    public Role getRoleById(Long roleId) {
        logger.info("Поиск роли по ID: {}", roleId);
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            logger.info("Роль найдена: {}", role);
        } else {
            logger.info("Роль с ID {} не найдена", roleId);
        }
        return role;
    }

    public List<Role> getAllRoles() {
        logger.info("Получение всех ролей");
        List<Role> roles = roleRepository.findAll();
        logger.info("Все роли успешно получены: {}", roles);
        return roles;
    }
}
