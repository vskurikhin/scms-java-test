package su.svn.scms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import su.svn.scms.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}