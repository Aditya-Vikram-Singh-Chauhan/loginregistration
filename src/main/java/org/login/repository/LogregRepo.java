package org.login.repository;

import org.login.entity.LogReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogregRepo extends JpaRepository<LogReg, Long> {

    @Query(value ="select * from aditya.LogReg a where a.userName = ?1 and a.password = ?2", nativeQuery=true)
    public List<LogReg> getUserByUsernamePassword(@Param("userName")String username, @Param("password")String password);

    @Query(value = "select * from aditya.LogReg a where a.userName = ?1 and a.emailID = ?2 and a.contact_num = ?3", nativeQuery=true)
    public List<LogReg> findByUsername(@Param("userName")String username, @Param("userName")String emailID, @Param("userName")Long number);

    @Query(value = "select * from aditya.LogReg a where a.userName = ?1", nativeQuery = true)
    List<LogReg> findByUsernameOnly(String username);
}
