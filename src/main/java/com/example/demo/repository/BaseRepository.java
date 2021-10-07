package com.example.demo.repository;

import com.example.demo.entity.BaseEntity;
import com.example.demo.util.QueryTemplate;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface BaseRepository<E extends BaseEntity, ID extends Serializable> {

    public Class getEntityClass();

    public E insert(E e);

    public E find(ID id) ;

//    public E find(ID id, boolean lock);

    public List<E> findAll();

    public long countAll();

    public List<E> find(QueryTemplate queryTemplate);

    public Page<E> search(QueryTemplate queryTemplate);

    public E update(E entity);

    public List<E> update(List<E> entity);

    public int delete(E entity);

    public int delete(ID id);

    public long count(QueryTemplate queryTemplate);

//
//    public List<E> findByExample(E exampleInstance, String[] excludeProperty) throws Exception;
//
//    public int count(E exampleInstance, String[] excludeProperty, boolean isLike) throws Exception;
//
//
//    public int count(Criterion... criterion) throws Exception;
//
//    public E create(E entity) throws Exception;
//
//    public E update(E entity) throws Exception;
//
//    public void delete(E entity) throws Exception;
//
//    public List<E> findByCriteria(Criterion... criterion) throws Exception;
//
//    public Timestamp getSystemTimestamp() throws Exception;
//
//    public Page<E> search(final QueryTemplate queryTemplate) throws Exception;
}
