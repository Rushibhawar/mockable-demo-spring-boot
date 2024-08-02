package com.mockable.app.repository;

import com.mockable.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRespository extends JpaRepository<Account, String> {
}
