package com.google.springboot.cassandrarepository;
import com.google.springboot.model.cassandrea.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
/**
 * @Author kris
 * @Create 2024-10-19 11:43
 * @Description
 */
@Repository
public interface UserRepository extends CassandraRepository<User, String> {
    // 可以添加更多自定义查询方法
}
