package school.sptech.conmusicapi.modules.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.admin.dtos.AdminDto;
import school.sptech.conmusicapi.modules.admin.dtos.CreateAdminDto;
import school.sptech.conmusicapi.modules.admin.entities.Admin;
import school.sptech.conmusicapi.modules.admin.mappers.AdminMapper;
import school.sptech.conmusicapi.modules.admin.repositories.IAdminRepository;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.statistics.GroupIdCount;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private IAdminRepository adminRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IShowRecordRepository showRecordRepository;

    public AdminDto create(CreateAdminDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            throw new BusinessRuleException("Email is already in use.");
        }

        Boolean isCpfAlreadyInUse = userRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse) {
            throw new BusinessRuleException("CPF is already in use.");
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        Admin createdArtist = adminRepository.save(AdminMapper.fromDto(dto));
        return AdminMapper.toDto(createdArtist);
    }

    public AdminDto getAdminById(Integer adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with ID: " + adminId));

        return AdminMapper.toDto(admin);
    }

    public List<AdminDto> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(AdminMapper::toDto)
                .collect(Collectors.toList());
    }

    public AdminDto updateAdmin(Integer adminId, AdminDto adminDto) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with ID: " + adminId));

        existingAdmin.setName(adminDto.getName());
        existingAdmin.setEmail(adminDto.getEmail());


        Admin updatedAdmin = adminRepository.save(existingAdmin);

        return AdminMapper.toDto(updatedAdmin);
    }

    public void getKpis(LocalDate startDate, LocalDate endDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s was not found", details.getUsername())
                ));


    }
}
