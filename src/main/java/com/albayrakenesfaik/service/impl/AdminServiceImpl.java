package com.albayrakenesfaik.service.impl;

import com.albayrakenesfaik.domain.Admin;
import com.albayrakenesfaik.repository.AdminRepository;
import com.albayrakenesfaik.service.AdminService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Admin}.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin save(Admin admin) {
        log.debug("Request to save Admin : {}", admin);
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        log.debug("Request to update Admin : {}", admin);
        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> partialUpdate(Admin admin) {
        log.debug("Request to partially update Admin : {}", admin);

        return adminRepository
            .findById(admin.getId())
            .map(existingAdmin -> {
                if (admin.getEmail() != null) {
                    existingAdmin.setEmail(admin.getEmail());
                }
                if (admin.getPassword() != null) {
                    existingAdmin.setPassword(admin.getPassword());
                }

                return existingAdmin;
            })
            .map(adminRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Admin> findAll(Pageable pageable) {
        log.debug("Request to get all Admins");
        return adminRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findOne(Long id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.deleteById(id);
    }
}
