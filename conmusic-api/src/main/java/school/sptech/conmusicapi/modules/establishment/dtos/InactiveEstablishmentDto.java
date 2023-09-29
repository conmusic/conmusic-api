package school.sptech.conmusicapi.modules.establishment.dtos;



public class InactiveEstablishmentDto {

        private Integer id;
        private boolean deleted;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }


        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }


    }

