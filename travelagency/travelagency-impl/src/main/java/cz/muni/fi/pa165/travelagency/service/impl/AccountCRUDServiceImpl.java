package cz.muni.fi.pa165.travelagency.service.impl;

import cz.muni.fi.pa165.travelagency.data.dao.AccountDAO;
import cz.muni.fi.pa165.travelagency.data.dao.CustomerDAO;
import cz.muni.fi.pa165.travelagency.data.dto.AccountDTO;
import cz.muni.fi.pa165.travelagency.data.entity.Account;
import cz.muni.fi.pa165.travelagency.service.AccountCRUDService;
import cz.muni.fi.pa165.travelagency.service.adapter.AccountDetailsAdapter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michal Jurc
 */
@Service(value = "accountCRUDService")
@Transactional
public class AccountCRUDServiceImpl implements AccountCRUDService {
    
    @Autowired
    @Qualifier(value = "accountDAO")
    private AccountDAO accountDAO;
    
    @Autowired
    @Qualifier(value = "customerDAO")
    private CustomerDAO customerDAO;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private SaltSource saltSource;

    @Override
    public void create(AccountDTO accountDTO) {
        if (accountDTO == null) {
            throw new IllegalArgumentException("Account to be created is null");
        }
        if (accountDTO.getId() != null) {
            throw new IllegalArgumentException("AccountDTO has set id");
        }
        validateAccount(accountDTO);
        validateDuplicity(accountDTO);
        Account account = accountDTOToEntity(accountDTO);
        accountDAO.create(account);
        account = accountDAO.get(account.getId());
        AccountDetailsAdapter details = new AccountDetailsAdapter(account);
        String password = details.getPassword();
        Object salt = saltSource.getSalt(details);
        account.setPassword(passwordEncoder.encodePassword(password, salt));
        accountDAO.update(account);
    }

    @Override
    public AccountDTO get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID to retrieve can't be null");
        }
        Account account = accountDAO.get(id);
        AccountDTO result = accountEntityToDTO(account);
        return result;
    }

    @Override
    public AccountDTO getByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username to retrieve can't be null");
        }
        Account account = accountDAO.getByUsername(username);
        AccountDTO result = accountEntityToDTO(account);
        return result;
    }

    @Override
    public void update(AccountDTO accountDTO) {
        if (accountDTO == null) {
            throw new IllegalArgumentException("Account to be created is null");
        }
        if (accountDTO.getId() == null) {
            throw new IllegalArgumentException("AccountDTO doesn't have set id");
        }
        validateAccount(accountDTO);
        Account account = accountDTOToEntity(accountDTO);
        accountDAO.update(account);
        account = accountDAO.get(account.getId());
        AccountDetailsAdapter details = new AccountDetailsAdapter(account);
        String password = details.getPassword();
        Object salt = saltSource.getSalt(details);
        account.setPassword(passwordEncoder.encodePassword(password, salt));
        accountDAO.update(account);
    }

    @Override
    public void delete(AccountDTO accountDTO) {
        if (accountDTO == null) {
            throw new IllegalArgumentException("Account to be created is null");
        }
        if (accountDTO.getId() == null) {
            throw new IllegalArgumentException("AccountDTO doesn't have set id");
        }
        Account account = accountDTOToEntity(accountDTO);
        accountDAO.delete(account);
    }

    @Override
    public List<AccountDTO> getAll() {
        List<AccountDTO> result = new ArrayList<>();
        List<Account> accounts = accountDAO.getAll();
        for (Account account : accounts) {
            result.add(accountEntityToDTO(account));
        }
        return result;
    }
    
    private void validateAccount(AccountDTO accountDTO) {
        //TODO:
    }
    
    public void validateDuplicity(AccountDTO accountDTO) {
        String username = accountDTO.getName();
        try {
            getByUsername(username);
        } catch (DataAccessException ex) {
            //OK there is no duplicity
            return;
        }
        throw new IllegalArgumentException("Username already exists!");
    }
    
    private Account accountDTOToEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        Account result = new Account();
        result.setId(accountDTO.getId());
        result.setName(accountDTO.getName());
        result.setPassword(accountDTO.getPassword());
        result.setEnabled(accountDTO.isEnabled());
        result.setRoles(accountDTO.getRoles());
        result.setCustomer(customerDAO.get(accountDTO.getCustomerId()));
        return result;
    }
    
    private AccountDTO accountEntityToDTO(Account account) {
        if (account == null) {
            return null;
        }
        AccountDTO result = new AccountDTO();
        result.setId(account.getId());
        result.setName(account.getName());
        result.setPassword(account.getPassword());
        result.setEnabled(account.isEnabled());
        result.setRoles(account.getRoles());
        result.setCustomerId(account.getCustomer().getId());
        return result;
    }
    
}
