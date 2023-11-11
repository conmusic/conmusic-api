package school.sptech.conmusicapi.modules.establishment.specifications;

import org.springframework.data.jpa.domain.Specification;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.specifications.ArtistSpecification;
import school.sptech.conmusicapi.modules.artist.specifications.ArtistSpecificationsBuilder;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.shared.utils.specifications.SearchOperation;
import school.sptech.conmusicapi.shared.utils.specifications.SpecSearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EstablishmentSpecificationsBuilder {

    private final List<SpecSearchCriteria> params;

    public EstablishmentSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public final EstablishmentSpecificationsBuilder with(String key, String operation, Object value, String prefix, String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final EstablishmentSpecificationsBuilder with(String orPredicate, String key, String operation, Object value, String prefix, String suffix) {
        SearchOperation op = SearchOperation.getOperation(operation);
        if (op != null) {
            if (op == SearchOperation.EQUAL) {
                boolean startWithAsterisk = prefix != null
                        && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX.getOperation());

                boolean endWithAsterisk = suffix != null &&
                        suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX.getOperation());

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }

            params.add(new SpecSearchCriteria(Objects.nonNull(orPredicate), key, op, value));
        }
        return this;
    }

    public Specification<Establishment> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Establishment> result = new EstablishmentSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(new EstablishmentSpecification(params.get(i)))
                    : Specification.where(result)
                    .and(new EstablishmentSpecification(params.get(i)));
        }

        return result;
    }
}
