package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }




    public void addCredentials(Credentials credentials){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);

        credentials.setKey(encodedKey);
        credentials.setPassword(encryptedPassword);

        credentialsMapper.insert(credentials);
    }

    public List<Credentials> getCredentials(Integer userId){
        return credentialsMapper.getUserCredentials(userId);
    }


    public void deleteCredentials(Integer credentialId) {
        credentialsMapper.delete(credentialId);
    }

    public void updateCredentials(Credentials credentials) {

        String key = credentialsMapper.getCredentialsById(credentials.getCredentialId()).getKey();

        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), key);
        credentials.setPassword(encryptedPassword);
        credentialsMapper.update(credentials);
    }
}
