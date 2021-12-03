package com.repository;

import com.model.Request;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestDao extends PagingAndSortingRepository<Request, Integer> {

}

