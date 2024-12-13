package vttp.batch5.ssf.noticeboard.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.ssf.noticeboard.models.Notice;

@Repository
public class NoticeRepository {
	@Autowired @Qualifier("notice")
    private RedisTemplate<String,Object> template;
	

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */

	// 	/*redis-cli command: hset hashkey key value*/
	public void insertNotices(String key, String value){
        template.opsForHash().put("title", key, value);
    }

	// //task 6: healthcheck 
	//redis-cli: randomkey
	public String generateRandomKey(){
		return template.randomKey();
	}



}
