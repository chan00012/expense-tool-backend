package com.ibm.expensetool.client.expensedb.repository;

import com.ibm.expensetool.client.expensedb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);

    User findUserByEmail(String email);
}
