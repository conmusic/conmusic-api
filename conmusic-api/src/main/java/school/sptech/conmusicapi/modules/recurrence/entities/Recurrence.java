    package school.sptech.conmusicapi.modules.recurrence.entities;

    import jakarta.persistence.*;
    import school.sptech.conmusicapi.modules.events.entities.Event;
    import school.sptech.conmusicapi.modules.recurrence.util.DayOfWeek;
    import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.util.Collections;
    import java.util.List;
    import java.util.Objects;

    @Entity
    @Table(name = "recorrencia")
    public class Recurrence {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Enumerated(EnumType.STRING)
        @Column(name = "dia_semana")
        private DayOfWeek dayOfWeek;

        @Column(name = "horario_inicio")
        private LocalDateTime startTime;

        @Column(name = "horario_termino")
        private LocalDateTime endTime;

        @ManyToOne
        @JoinColumn(name = "fk_evento")
        private Event event;

        @OneToMany(mappedBy = "recurrence", fetch = FetchType.LAZY)
        List<Schedule> schedules;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }


        public DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }
        public void setDayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }

        public List<Schedule> getSchedules() {
            return Objects.isNull(schedules)
                    ? Collections.emptyList()
                    : schedules;
        }

        public void setSchedules(List<Schedule> schedules) {
            this.schedules = schedules;
        }
    }
