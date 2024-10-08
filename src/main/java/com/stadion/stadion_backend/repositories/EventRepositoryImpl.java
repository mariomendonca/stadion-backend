package com.stadion.stadion_backend.repositories;

import com.stadion.stadion_backend.domains.dtos.event.EventFilterRequest;
import com.stadion.stadion_backend.domains.entities.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EventRepositoryImpl implements CustomEventRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Event> findByFilter(EventFilterRequest eventFilterRequest) {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);

        Predicate finalPredicate = buildFinalPredicate(criteriaBuilder, root, eventFilterRequest);
        criteriaQuery.where(finalPredicate);
        TypedQuery<Event> typedQuery = entityManager.createQuery(criteriaQuery);

        if (Objects.nonNull(eventFilterRequest.getPage()) && Objects.nonNull(eventFilterRequest.getItemsPerPage())) {
            buildPagination(eventFilterRequest.getPage(), eventFilterRequest.getItemsPerPage(), typedQuery);
        }

        return typedQuery.getResultList();
    }

    private void buildPagination(int page, int itemsPerPage, TypedQuery<Event> typedQuery) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
    }

    Predicate buildFinalPredicate(
            CriteriaBuilder criteriaBuilder,
            Root<Event> root,
            EventFilterRequest eventFilterRequest
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(eventFilterRequest.getName())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + eventFilterRequest.getName().toLowerCase() + "%"));
        }

        if (Objects.nonNull(eventFilterRequest.getStates()) && !eventFilterRequest.getStates().isEmpty()) {
            predicates.add(root.get("state").in(eventFilterRequest.getStates()));
        }

        if (Objects.nonNull(eventFilterRequest.getCategories()) && !eventFilterRequest.getCategories().isEmpty()) {
            predicates.add(root.get("category").in(eventFilterRequest.getCategories()));
        }

        if (Objects.nonNull(eventFilterRequest.getStartDate()) && Objects.isNull(eventFilterRequest.getEndDate())) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.function("date", LocalDate.class, root.get("startDate")), eventFilterRequest.getStartDate()));
        }

        if (Objects.nonNull(eventFilterRequest.getStartDate()) && Objects.nonNull(eventFilterRequest.getEndDate())) {
            predicates.add(criteriaBuilder.between(
                    criteriaBuilder.function("date", LocalDate.class, root.get("startDate")),
                    eventFilterRequest.getStartDate(),
                    eventFilterRequest.getEndDate()
            ));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
