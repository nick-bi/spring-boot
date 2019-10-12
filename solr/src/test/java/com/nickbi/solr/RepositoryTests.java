package com.nickbi.solr;

import com.nickbi.solr.entity.User;
import com.nickbi.solr.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author nickbi
 * @date 2019/1/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {

    private static Logger logger = LoggerFactory.getLogger(RepositoryTests.class);
    @Autowired
    private UserRepository userRepository;

    @Test
    public void index() {
        User test =
                User.builder().id("1002").username("test").password("123123").remark("solr user index test!").build();
        User save = userRepository.save(test);
        logger.debug(" user document saved with id: ", save.getId());
    }

    @Test
    public void update() {
        User user = User.builder().id("1001").createDate(new Date()).build();
        userRepository.save(user);
        logger.debug("user document updated  with id: ", user.getId());
    }

    @Test
    public void delete() {
        User user = User.builder().id("1001").build();
        userRepository.delete(user);
        logger.debug("user document deleted  with id: ", user.getId());
    }



}
