package com.nickbi.elasticsearch.repository;

import com.nickbi.elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * user elasticsearch repository
 *
 * @author nickbi
 * @date 2019/1/18
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
    /**
     * find user by user name
     *
     * @param id user id
     * @return
     */
    User findUserById(Long id);
}
