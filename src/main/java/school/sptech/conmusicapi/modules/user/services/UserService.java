package school.sptech.conmusicapi.modules.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.conmusicapi.config.security.jwt.JwtTokenManager;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.dtos.UserKpiDto;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.user.dtos.LoginDto;
import school.sptech.conmusicapi.modules.user.dtos.UserTokenDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;
import school.sptech.conmusicapi.modules.user.mapper.UserTokenMapper;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.statistics.GroupDateDoubleSum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IShowRecordRepository showRecordRepository;
    @Autowired
    private IShowRepository showRepository;

    public UserTokenDto authenticate(LoginDto dto) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()
        );
        final Authentication authentication = authenticationManager.authenticate(credentials);

        User authenticatedUser = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(
                        () -> new ResponseStatusException(404, "Email and/or Password are incorrect", null)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenManager.generateToken(authentication);
        return UserTokenMapper.toDto(authenticatedUser, token);
    }

    public Optional<UserKpiDto> getManagerOrArtistKpi(Integer lastDays) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s was not found", details.getUsername())
                ));

        LocalDate today = LocalDate.now();

        List<ShowRecord> records = showRecordRepository
                .findLifeCycleChangesByUserIdBetweenInterval(
                        today.minusDays(lastDays).atStartOfDay(),
                        today.atTime(23, 59, 59),
                        user.getId());

        if (records.isEmpty()) {
            return Optional.empty();
        }

        long receivedProposals = records.stream()
                .filter(r -> r.getStatus().equals(ShowStatusEnum
                        .getStatusByName(String.format("%S_PROPOSAL", user.getUserType()))
                        .getOppositeUserStatus())
                )
                .count();

        List<ShowRecord> negotiations = records.stream()
                .filter(r -> r.getStatus().equals(ShowStatusEnum.NEGOTIATION))
                .toList();

        long startedByYou = records.stream()
                .filter(r -> negotiations.stream().anyMatch(n -> n.getShow().getId().equals(r.getShow().getId()))
                        && r.getStatus().equals(ShowStatusEnum
                                .getStatusByName(String.format("%S_PROPOSAL", user.getUserType()))
                        )
                )
                .count();

        long countConfirmed = records.stream()
                .filter(r -> r.getStatus().equals(ShowStatusEnum.CONFIRMED))
                .count();

        long countCanceled = records.stream()
                .filter(r -> r.getStatus().equals(ShowStatusEnum.ARTIST_CANCELED)
                        || r.getStatus().equals(ShowStatusEnum.MANAGER_CANCELED))
                .count();

        return Optional.of(UserMapper.toKpiDto(
                receivedProposals,
                negotiations.size(),
                startedByYou,
                countConfirmed,
                countCanceled
        ));
    }

    public List<GroupDateDoubleSum> getTotalValueChart(Integer lastDays) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s was not found", details.getUsername())
                ));

        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(lastDays);

        List<Show> concluded = showRepository.findConcludedBetweenIntervalByUserId(
                start.atStartOfDay(),
                today.atTime(23, 59, 59),
                user.getId());

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
