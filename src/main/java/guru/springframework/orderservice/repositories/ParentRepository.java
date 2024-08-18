package guru.springframework.orderservice.repositories;

import guru.springframework.orderservice.domain.Customer;
import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hasan on 18/8/24.
 */
public interface ParentRepository extends JpaRepository<Parent, Long> {
}
