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
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.modules.admin.dtos.AdminKpiDto;
import school.sptech.conmusicapi.shared.utils.statistics.GroupDateDoubleSum;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Autowired
    private IShowRepository showRepository;

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

    public List<AdminKpiDto> getKpis(Integer lastDays) {
        LocalDate today = LocalDate.now();

        List<ShowRecord> records = showRecordRepository
                .findAllLifeCycleChangesBetweenInterval(
                        today.minusDays(lastDays).atStartOfDay(),
                        today.atTime(23, 59, 59));

        List<AdminKpiDto> kpis = new ArrayList<>();
        if (!records.isEmpty()) {
            long proposalsByArtists = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.ARTIST_PROPOSAL))
                    .count();
            long proposalsByManagers = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.MANAGER_PROPOSAL))
                    .count();

            List<ShowRecord> negotiations = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.NEGOTIATION))
                    .toList();

            long startedByArtist = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.ARTIST_PROPOSAL)
                            && negotiations.stream().anyMatch(n -> n.getShow().getId().equals(r.getShow().getId()))
                    )
                    .count();
            long startedByManager = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.MANAGER_PROPOSAL)
                            && negotiations.stream().anyMatch(n -> n.getShow().getId().equals(r.getShow().getId()))
                    )
                    .count();

            long confirmed = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.CONFIRMED))
                    .count();

            long canceledByArtist = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.ARTIST_CANCELED))
                    .count();
            long canceledByManager = records.stream()
                    .filter(r -> r.getStatus().equals(ShowStatusEnum.MANAGER_CANCELED))
                    .count();

            kpis.add(AdminMapper.toKpiDto("Propostas enviadas", proposalsByArtists, proposalsByManagers));
            kpis.add(AdminMapper.toKpiDto("Negociações iniciadas", startedByArtist, startedByManager));
            kpis.add(AdminMapper.toKpiDto("Shows confirmados", confirmed));
            kpis.add(AdminMapper.toKpiDto("Shows cancelados", canceledByArtist, canceledByManager));
        }

        return kpis;
    }

    public List<GroupDateDoubleSum> getTotalValueChart(Integer lastDays) {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(lastDays);

        List<Show> concluded = showRepository.findConcludedBetweenInterval(
                start.atStartOfDay(),
                today.atTime(23, 59, 59));

        List<GroupDateDoubleSum> chartData = new ArrayList<>();
        if (!concluded.isEmpty()) {
            concluded.stream()
                    .collect(Collectors.groupingBy((x) -> x.getSchedule().getStartDateTime().toLocalDate()))
                    .forEach((key, shows) ->
                            chartData.add(new GroupDateDoubleSum(key, shows.stream().mapToDouble(Show::getValue).sum()))
                    );
        }

        return chartData;
    }
}
