package com.stadion.stadion_backend.repositories;

import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.enums.EventCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Event> findByFilter(List<String> states, List<EventCategory> categories, String name, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);

        Predicate finalPredicate = buildFinalPredicate(criteriaBuilder, root, states, categories, name, startDate, endDate);
        criteriaQuery.where(finalPredicate);
        TypedQuery<Event> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        return typedQuery.getResultList();
    }

    Predicate buildFinalPredicate(
            CriteriaBuilder criteriaBuilder,
            Root<Event> root,
            List<String> states,
            List<EventCategory> categories,
            String name,
            LocalDate startDate,
            LocalDate endDate
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(name)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (Objects.nonNull(states) && !states.isEmpty()) {
            predicates.add(root.get("state").in(states));
        }

        if (Objects.nonNull(categories) && !categories.isEmpty()) {
            predicates.add(root.get("category").in(categories));
        }

        if (Objects.nonNull(startDate) && Objects.isNull(endDate)) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("date", LocalDate.class, root.get("startDate")), startDate));
        }

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            predicates.add(criteriaBuilder.between(
                    criteriaBuilder.function("date", LocalDate.class, root.get("startDate")),
                    startDate,
                    endDate
            ));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
