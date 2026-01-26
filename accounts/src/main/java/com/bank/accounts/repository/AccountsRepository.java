package com.bank.accounts.repository;

import com.bank.accounts.entity.Accounts;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

}
