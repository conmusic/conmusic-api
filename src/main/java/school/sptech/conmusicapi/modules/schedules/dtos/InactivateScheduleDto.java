package school.sptech.conmusicapi.modules.schedules.dtos;

public class InactivateScheduleDto {
        private Integer id;

        private Boolean isDeleted;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }



        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean confirmed) {
            this.isDeleted = confirmed;
        }


    }


