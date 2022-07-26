package vttp2022.workshop14.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp2022.workshop14.model.Contact;

@Service
public class ContactsRedis implements ContactsRepo{
    private static final Logger logger = LoggerFactory.getLogger(ContactsRedis.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final Contact ctc){
        redisTemplate.opsForList().rightPush(ctc.getId(), ctc.getName());
        redisTemplate.opsForList().rightPush(ctc.getId(), ctc.getEmail());
        redisTemplate.opsForList().rightPush(ctc.getId(), ctc.getPhoneNumber());
        redisTemplate.opsForValue().set(ctc.getId(), ctc); //saves to redis database online
    }//saving here occurs by calling set command in redis

    @Override public Contact findById(final String contactId){
        List<Object> itemlist= redisTemplate.opsForList().range(contactId, 0, 2); //check if end is inclusive or exclusive
        String name = String.valueOf(itemlist.get(0));
        String email = String.valueOf(itemlist.get(1));
        Integer phoneNum = Integer.parseInt(String.valueOf(itemlist.get(2)));
        Contact result = new Contact(contactId, name, email, phoneNum);
        //Contact result = (Contact) redisTemplate.opsForValue().get(contactId);
        logger.info(">>> "+ result.getEmail());
        return result;
    }
}
