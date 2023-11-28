package school.sptech.conmusicapi.shared.utils.statistics;

public class GroupGenresCount {
    private String genre;
    private Long count;

    public GroupGenresCount() {}

    public GroupGenresCount(String genre, Long count) {
        this.genre = genre;
        this.count = count;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
