package cz.muni.fi.pa165.travelagencyclient.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michal Jurc
 */
public class CustomerDTOResponse {
    
    private CustomerDTO customerDTO;
    private List<CustomerDTO> listData = new ArrayList<>();

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public List<CustomerDTO> getListData() {
        return listData;
    }

    public void setListData(List<CustomerDTO> listData) {
        this.listData = listData;
    }
    
}
