package school.sptech.conmusicapi.modules.show.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.shared.utils.statistics.GroupMonthCount;
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

    @Query("""
        SELECT s           
        FROM Show s
        WHERE
            s.status = 7
            AND (s.artist.id = :userId OR s.event.establishment.manager.id = :userId)
            AND s.schedule.startDateTime BETWEEN :startDate AND :endDate
    """)
    List<Show> findConcludedBetweenIntervalByUserId(LocalDateTime startDate, LocalDateTime endDate, Integer userId);

    @Query("""
        SELECT s           
        FROM Show s
        WHERE
            s.status = 7
            AND s.schedule.startDateTime BETWEEN :startDate AND :endDate
    """)
    List<Show> findConcludedBetweenInterval(LocalDateTime startDate, LocalDateTime endDate);

    List<Show> findAllByStatusAndScheduleEventId(ShowStatusEnum status, Integer id);

    List<Show> findAllByStatus(ShowStatusEnum status);
}
