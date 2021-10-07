package com.example.demo.service.imp;

import com.example.demo.entity.BaseEntity;
import com.example.demo.exception.CustomException;
import com.example.demo.model.BaseModel;
import com.example.demo.model.enu.Code;
import com.example.demo.model.search.BaseSearch;
import com.example.demo.repository.BaseRepository;
import com.example.demo.service.BaseService;
import com.example.demo.util.CommonUtils;
import com.example.demo.util.QueryTemplate;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public abstract class BaseServiceImp<R extends BaseRepository<E, ID>, E extends BaseEntity, ID extends Serializable> implements BaseService<E, ID> {

    public abstract R getRepository();

    protected QueryTemplate getBaseQuery(BaseSearch search) {
        QueryTemplate queryTemplate = new QueryTemplate();
        Class entityClass = getRepository().getEntityClass();
        String query = " from " + entityClass.getSimpleName() + " e where 1=1 ";
        HashMap<String, Object> params = queryTemplate.getParameterMap();
        if (search.isAdmin()) {
            if(search.getIsDeleted() != null){
                query += " and e.deleted = :deleted";
                params.put("deleted", search.getIsDeleted());
            }
        }else {
            query += " and e.deleted = :deleted";
            params.put("deleted", false);
        }
        if (search.getId() != null && search.getId() > 0) {
            query += " and e.id = :id ";
            params.put("id", search.getId());
        }
        if (search.getFromCreatedAt() != null && search.getFromCreatedAt() > 0) {
            Timestamp fromCreatedAt = new Timestamp(search.getFromCreatedAt());
            query += " and e.createdAt >= :fromCreatedAt ";
            params.put("fromCreatedAt", fromCreatedAt);
        }
        if (search.getToCreatedAt() != null && search.getToCreatedAt() > 0) {
            Timestamp toCreatedAt = new Timestamp(search.getToCreatedAt());
            query += " and e.createdAt < :toCreatedAt ";
            params.put("toCreatedAt", toCreatedAt);
        }
        if (search.getFromUpdatedAt() != null && search.getFromUpdatedAt() > 0) {
            Timestamp fromUpdatedAt = new Timestamp(search.getFromUpdatedAt());
            query += " and e.updatedAt >= :fromUpdatedAt ";
            params.put("fromUpdatedAt", fromUpdatedAt);
        }
        if (search.getToUpdatedAt() != null && search.getToUpdatedAt() > 0) {
            Timestamp toUpdatedAt = new Timestamp(search.getToUpdatedAt());
            query += " and e.updatedAt < :toUpdatedAt ";
            params.put("toUpdatedAt", toUpdatedAt);
        }
        queryTemplate.setPageable(search.getPageable());
        queryTemplate.setQuery(query);
        queryTemplate.setParameterMap(params);
        return queryTemplate;
    }

    @Override
    public List<E> findAll() {
        return getRepository().findAll();
    }

    @Override
    public E find(ID id) {
        return getRepository().find(id);
    }

    @Override
    public E create(E entity) {
        return getRepository().insert(entity);
    }

    @Override
    public E update(E entity) {
        return getRepository().update(entity);
    }

    public List<E> find(QueryTemplate queryTemplate) {
        return getRepository().find(queryTemplate);
    }

    public Page<E> search(QueryTemplate queryTemplate) {
        return getRepository().search(queryTemplate);
    }

    public boolean isUnique(Class<E> entityClass, Long id, String fieldName, String value) {
        QueryTemplate queryTemplate = new QueryTemplate();
        String query = "select count(*) from " + entityClass.getSimpleName() + " e where e." + fieldName + " = :" + fieldName;
        HashMap<String, Object> params = queryTemplate.getParameterMap();
        params.put(fieldName, value);
        if (id != null) {
            query += " and e.id != :id";
            params.put("id", id);
        }
        queryTemplate.setQuery(query);
        queryTemplate.setParameterMap(params);
        return !(getRepository().count(queryTemplate) > 0);
    }

    public boolean idIsExisted(Class<E> entityClass, Long id) {
        QueryTemplate queryTemplate = new QueryTemplate();
        String query = "select count(*) from " + entityClass.getSimpleName() + " e where e.id = " + id;
        queryTemplate.setQuery(query);
        return getRepository().count(queryTemplate) > 0;
    }

    public void checkExistedId(BaseModel model, Code errorCode) throws CustomException {
        if (model.getId() == null)
            return;
        if (idIsExisted(getRepository().getEntityClass(), model.getId()))
            throw CommonUtils.createException(errorCode);
    }

    public void checkNotExistedId(BaseModel model, Code errorCode) throws CustomException {
        if (model.getId() == null)
            return;
        if (!idIsExisted(getRepository().getEntityClass(), model.getId()))
            throw CommonUtils.createException(errorCode);
    }

}
