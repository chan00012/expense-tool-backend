package com.ibm.expensetool.repository;

import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp(){
        User user = new User("christian", "robles", "cstrobles@gmail.com", "thisisnotapassword");
        entityManager.persist(user);
    }

    @Test
    public void findByUserId_thenReturnUser() {
        User user = new User("christian", "robles", "cstrobles@gmail.com", "thisisnotapassword");

        User userExtract1 = userRepository.findByUserId(1L);

        Assert.notNull(userExtract1);
    }

    @Test
    public void findUserByEmail_thenReturnUser() {
        User user = new User("christian", "robles", "cstrobles@gmail.com", "thisisnotapassword");

        User userExtract1 = userRepository.findUserByEmail("cstrobles@gmail.com");

        Assert.notNull(userExtract1);
    }
}
