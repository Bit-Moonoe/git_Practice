package dev.bank.bankstatement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bank.bankstatement.entity.User;


//JpaRepository<Entity, Identifier type>
@Repository // 빈 등록을 위한 @Controller처럼 직관적인 네이밍 어노테이션
public interface UserRepository extends JpaRepository<User, String> {
	User findByIdAndPassword(String id, String password);
}
