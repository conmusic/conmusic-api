package school.sptech.conmusicapi.modules.events.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.shared.utils.specifications.SearchOperation;
import school.sptech.conmusicapi.shared.utils.specifications.SpecSearchCriteria;

public class EventSpecification implements Specification<Event> {

    private final SpecSearchCriteria criteria;

    public EventSpecification(SpecSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation() == SearchOperation.GREATER_THAN) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation() == SearchOperation.LESS_THAN) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation() == SearchOperation.EQUAL
                || criteria.getOperation() == SearchOperation.CONTAINS) {
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
