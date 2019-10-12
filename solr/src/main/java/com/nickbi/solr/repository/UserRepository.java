package com.nickbi.solr.repository;

import com.nickbi.solr.entity.User;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

/**
 * @author nickbi
 * @date 2019/1/21
 */
public interface UserRepository extends SolrCrudRepository<User, Long> {
    /**
     * find user by username
     *
     * @param username
     * @return
     */
    public List<User> findByUsernameContains(String username);

    

}
