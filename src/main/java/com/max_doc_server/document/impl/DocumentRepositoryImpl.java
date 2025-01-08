package com.max_doc_server.document.impl;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.max_doc_server.document.domain.Document;
import com.max_doc_server.document.dto.DocumentDTO;
import com.max_doc_server.document.dto.DocumentFilterDTO;
import com.max_doc_server.document.repository.DocumentRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class  DocumentRepositoryImpl  implements DocumentRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
    @Override
    public Page<DocumentDTO> documentsFilterPaged(
            DocumentFilterDTO filter,
            Pageable pageable
    ) {
        StringBuilder hql = new StringBuilder();
        hql.append(
                " SELECT new com.max_doc_server.document.dto.DocumentDTO(d.id, d.title, d.description, d.version, d.acronym, d.phase) "
        );
        hql.append(" FROM Document d ");
        hql.append(getWheres(filter));
        hql.append(" ORDER BY d.title DESC ");

        TypedQuery<DocumentDTO> query = entityManager.createQuery(
                hql.toString(),
                DocumentDTO.class
        );
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        setParameters(filter, query);

        TypedQuery<Long> queryCount = getCountTypedQuery(filter);
        return new PageImpl(query.getResultList(), pageable, queryCount.getSingleResult());
    }

    private String getWheres(DocumentFilterDTO filter) {
        StringBuilder where = new StringBuilder();
        where.append(" WHERE 1 = 1 ");

        if (!isEmpty(filter.getTitle())) {
            where.append(" AND d.title LIKE :title ");
        }

        if (!isEmpty(filter.getAcronym())) {
            where.append(" AND d.acronym LIKE :acronym ");
        }

        if (!isEmpty(filter.getPhase())) {
            where.append(" AND d.phase = :phase ");
        }
        return where.toString();
    }

    private void setParameters(DocumentFilterDTO filter, TypedQuery<?> query) {
        if (!isEmpty(filter.getTitle())) {
            query.setParameter("title", "%" + filter.getTitle() + "%");
        }
        if (!isEmpty(filter.getAcronym())) {
            query.setParameter("acronym", "%" +filter.getAcronym() + "%");
        }
        if (!isEmpty(filter.getPhase())) {
            query.setParameter("phase", filter.getPhase());

        }
    }

    private TypedQuery<Long> getCountTypedQuery(DocumentFilterDTO filter) {
        StringBuilder hql = new StringBuilder();
        hql.append(" SELECT COUNT(d.id) ");
        hql.append(" FROM Document d ");
        hql.append(getWheres(filter));

        TypedQuery<Long> query = entityManager.createQuery(hql.toString(), Long.class);
        setParameters(filter, query);
        return query;
    }
}
