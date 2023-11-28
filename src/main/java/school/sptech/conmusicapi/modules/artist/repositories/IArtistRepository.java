package school.sptech.conmusicapi.modules.artist.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.artist.entities.Artist;

import java.util.List;

@Repository
public interface IArtistRepository extends JpaRepository<Artist, Integer>, JpaSpecificationExecutor<Artist> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuario_genero WHERE user_id = :artistId AND genre_id = :genreId", nativeQuery = true)
    int deleteGenreArtist(Integer artistId, Integer genreId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuario_genero WHERE user_id = :artistId AND genre_id IN :genresId", nativeQuery = true)
    int deleteGenreArtist(Integer artistId, List<Integer> genresId);
}
