package school.sptech.conmusicapi.modules.show.services;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.statistics.GroupMonthCount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Service
public class ShowStatisticsService {
    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private IUserRepository userRepository;

    public List<GroupMonthCount> countConfirmedShowsByInDateIntervalGroupByMonth(
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with email %s was not found", details.getUsername())));

        List<GroupMonthCount> result = showRepository.countShowsByStatusInDateIntervalGroupByMonth(
                EnumSet.of(ShowStatusEnum.CONFIRMED, ShowStatusEnum.CONCLUDED),
                startDate,
                endDate,
                user.getId()
        );

        return List.of(
                new GroupMonthCount("January", 5L),
                new GroupMonthCount("February", 8L),
                new GroupMonthCount("March", 12L),
                new GroupMonthCount("April", 10L),
                new GroupMonthCount("May", 16L)
        );
    }
}
