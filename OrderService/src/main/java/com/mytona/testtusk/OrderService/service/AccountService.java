package com.mytona.testtusk.OrderService.service;


import com.mytona.testtusk.OrderService.entity.users.Account;
import com.mytona.testtusk.OrderService.entity.users.Role;
import com.mytona.testtusk.OrderService.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Account findByLogin(String login) {
        return this.accountRepository.findAccountByUsername(login);
    }

    public Account findByLoginAndPassword(String login, String password) {
        Account account = this.findByLogin(login);
        if (account != null) {
            if (passwordEncoder.matches(password, account.getPassword())) {
                return account;
            }
        }
        return null;
    }

    public Account findAccountById(Long accountId) {
        Optional<Account> accountFromDb = accountRepository.findById(accountId);
        return accountFromDb.orElse(new Account());
    }

    public List<Account> allAccounts() {
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account) {
        Account accountFromDB = accountRepository.findAccountByUsername(account.getUsername());
        if (accountFromDB != null)
            return null;

        Role userRole = this.roleService.findByName("ROLE_USER");
        if (userRole == null)
            userRole = this.roleService.save(new Role("ROLE_USER"));
        account.setRole(userRole);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setCreateTime(new Date());
        return accountRepository.save(account);
    }

    public Account saveAccountAdmin(Account account) {
        Account accountFromDB = accountRepository.findAccountByUsername(account.getUsername());
        if (accountFromDB != null)
            return null;

        Role userRole = this.roleService.findByName("ROLE_ADMIN");
        if (userRole == null)
            userRole = this.roleService.save(new Role("ROLE_ADMIN"));
        account.setRole(userRole);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setCreateTime(new Date());
        return this.accountRepository.save(account);
    }
}
