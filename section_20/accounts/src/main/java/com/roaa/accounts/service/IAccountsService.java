package com.roaa.accounts.service;

import com.roaa.accounts.dto.CustomerDto;

public interface IAccountsService {


    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Fetches the account details for a given mobile number.
     *
     * @param mobileNumber - The mobile number associated with the account
     * @return CustomerDto containing the account details
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * Updates the account details.
     *
     * @param customerDto - CustomerDto containing the updated account details.
     * @return true if the account is updated successfully, false otherwise.
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Deletes the account associated with the given mobile number.
     *
     * @param mobileNumber - The mobile number associated with the account to be deleted.
     * @return true if the account is deleted successfully, false otherwise.
     */
    boolean deleteAccount(String mobileNumber);

    /**
     *
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    boolean updateCommunicationStatus(Long accountNumber);

}
