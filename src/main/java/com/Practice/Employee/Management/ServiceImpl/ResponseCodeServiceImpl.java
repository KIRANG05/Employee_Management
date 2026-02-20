package com.Practice.Employee.Management.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.Practice.Employee.Management.Modal.ResponseMessages;
import com.Practice.Employee.Management.Repository.ResponseCodeRespository;
import com.Practice.Employee.Management.Service.ResponseCodeService;

@Service
public class ResponseCodeServiceImpl implements ResponseCodeService {

	 @Autowired
	 private ResponseCodeRespository responseCodeRepository;
	
	@Override
	 @Cacheable(
		        value = "responseMessages",
		        key = "#code + '_' + #operation"
		    )
	public String getMessageByCode(String code, String operation) {
	  return responseCodeRepository.getMessageByCode(code, operation);
	}

	
	  // Update DB and evict cache
    @CacheEvict(value = "responseMessages", key = "#code + '_' + #operation")
    public String updateMessage(String code, String operation, String newMessage) {
        // Update DB
        ResponseMessages msg = responseCodeRepository.findByCodeAndOperation(code, operation);
        msg.setMessage(newMessage);
        responseCodeRepository.save(msg);

        // Cache for this key is automatically removed
        return newMessage;
    }

    // Clear all cache in responseMessages
    @CacheEvict(value = "responseMessages", allEntries = true)
    public void clearAllMessagesCache() {
        // This removes ALL keys under 'responseMessages'
    }
}
