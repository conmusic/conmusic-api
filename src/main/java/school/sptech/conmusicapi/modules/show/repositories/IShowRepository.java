package school.sptech.conmusicapi.modules.show.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.shared.utils.statistics.GroupMonthCount;
import school.sptech.conmusicapi.shared.utils.statistics.StatusCount;
import school.sptech.conmusicapi.modules.show.entities.Show;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface IShowRepository extends JpaRepository<Show, Integer> {
    Optional<Show> findByArtistIdAndEventIdAndScheduleId(Integer artistId, Integer eventId, Integer scheduleId);

    @Query("""
        SELECT s 
        FROM Show s 
        WHERE 
            s.status IN :status 
            AND (s.artist.id = :id OR s.event.establishment.manager.id = :id)
        ORDER BY s.schedule.startDateTime
    """)
    List<Show> findAllByUserIdAndStatus(Integer id, EnumSet status);

    List<Show> findByIdNotAndScheduleIdEquals(Integer id, Integer id1);

    @Query("""
        SELECT 
            new school.sptech.conmusicapi.shared.utils.statistics.StatusCount(s.status, COUNT(s.id))
        FROM ShowRecord s
        WHERE
            s.status IN :status
            AND (s.show.artist.id = :userId OR s.show.event.establishment.manager.id = :userId)
            AND (s.show.schedule.startDateTime BETWEEN :startDate AND :endDate
                OR s.dateAction BETWEEN :startDate AND :endDate
        GROUP BY s.status
    """)
    List<StatusCount> countShowsByStatusInDateInterval(EnumSet<ShowStatusEnum> status, LocalDateTime startDate, LocalDateTime endDate, Integer userId);

    @Query("""
        SELECT 
            new school.sptech.conmusicapi.shared.utils.statistics.GroupMonthCount(FUNCTION('MONTH', s.schedule.startDateTime), COUNT(s.id))            
        FROM Show s
        WHERE
            s.status IN :status
            AND (s.artist.id = :userId OR s.event.establishment.manager.id = :userId)
            AND s.schedule.startDateTime BETWEEN :startDate AND :endDate
        GROUP BY FUNCTION('MONTH', s.schedule.startDateTime)
        ORDER BY FUNCTION('MONTH', s.schedule.startDateTime)
    """)
    List<GroupMonthCount> countShowsByStatusInDateIntervalGroupByMonth(EnumSet status, LocalDateTime startDate, LocalDateTime endDate, Integer userId);

    List<Show> findAllByStatusAndScheduleEventId(ShowStatusEnum status, Integer id);
}
