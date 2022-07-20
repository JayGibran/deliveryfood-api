package com.jaygibran.deliveryfood.infrastructure.service;

import com.jaygibran.deliveryfood.domain.filter.DailySaleFilter;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.model.OrderStatus;
import com.jaygibran.deliveryfood.domain.model.dto.DailySale;
import com.jaygibran.deliveryfood.domain.service.SaleQueryService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DailySale> queryDailySale(DailySaleFilter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DailySale> query = builder.createQuery(DailySale.class);
        Root<Order> root = query.from(Order.class);

        var predicates = new ArrayList<Predicate>();

        predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        if (filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }

        if (filter.getDateCreatedStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dateCreated"), filter.getDateCreatedStart()));
        }

        if (filter.getDateCreatedFinish() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dateCreated"), filter.getDateCreatedFinish()));
        }

        Expression<LocalDate> functionDatedDateCreated = builder
                .function("date", LocalDate.class, root.get("dateCreated"));

        CompoundSelection<DailySale> selection = builder
                .construct(DailySale.class,
                        functionDatedDateCreated,
                        builder.count(root.get("id")),
                        builder.sum(root.get("total")));

        query.select(selection);
        query.groupBy(functionDatedDateCreated);
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
