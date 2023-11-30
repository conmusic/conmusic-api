package school.sptech.conmusicapi.modules.user.mapper;

import school.sptech.conmusicapi.modules.user.dtos.UserKpiDto;
import school.sptech.conmusicapi.modules.user.dtos.*;
import school.sptech.conmusicapi.modules.user.entities.User;

public class UserMapper {
    public static void fromDto(CreateUserDto dto, User user) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCpf(dto.getCpf());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setBirthDate(dto.getBirthDate());
        user.setAbout(dto.getAbout());
        user.setInstagram(dto.getInstagram());
    }

    public static void fromDtoUpdate(UpdateUserDto dto, User user){
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAbout(dto.getAbout());
        user.setInstagram(dto.getInstagram());
    }

    public static void toDto(User user, UserDto dto) {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCpf(user.getCpf());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setBirthDate(user.getBirthDate());
        dto.setAbout(user.getAbout());
        dto.setInstagram(user.getInstagram());
    }

    public static LoginUserDTO toLoginDto(User user) {
        LoginUserDTO dto = new LoginUserDTO();

        UserMapper.toDto(user, dto);
        dto.setUserType(user.getUserType());
        dto.setNeedCompletion(user);

        return dto;
    }


    public static UserKpiDto toKpiDto(
            long receivedProposals,
            long negotiations,
            long startedByYou,
            long confirmed,
            long canceled
    ) {
        UserKpiDto kpiDto = new UserKpiDto();

        double percentageConfirmed =  ((double) confirmed / (double) negotiations) * 100;
        double percentageCanceled = ((double) canceled / (double) negotiations) * 100;

        kpiDto.setReceivedProposals(receivedProposals);
        kpiDto.setNegotiations(negotiations);
        kpiDto.setNegotiationsStartedByYou(startedByYou);
        kpiDto.setConfirmed(confirmed);
        kpiDto.setCanceled(canceled);
        kpiDto.setPercentageConfirmed(percentageConfirmed);
        kpiDto.setPercentageCanceled(percentageCanceled);

        return kpiDto;
    }
}
