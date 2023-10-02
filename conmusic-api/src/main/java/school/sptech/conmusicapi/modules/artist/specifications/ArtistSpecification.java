package school.sptech.conmusicapi.modules.artist.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.shared.utils.specifications.SearchOperation;
import school.sptech.conmusicapi.shared.utils.specifications.SpecSearchCriteria;

public class ArtistSpecification implements Specification<Artist> {

    private final SpecSearchCriteria criteria;

    public ArtistSpecification(SpecSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Artist> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation() == SearchOperation.GREATER_THAN) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation() == SearchOperation.LESS_THAN) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation() == SearchOperation.EQUAL) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
