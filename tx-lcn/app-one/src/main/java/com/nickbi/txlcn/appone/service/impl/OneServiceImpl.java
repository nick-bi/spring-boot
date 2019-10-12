package com.nickbi.txlcn.appone.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.nickbi.txlcn.appone.entity.UserEntity;
import com.nickbi.txlcn.appone.service.OneService;
import com.nickbi.txlcn.appone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * package com.nickbi.txlcn.appone.service
 * description <p/>
 *
 * @author wzfc-001
 * create on 2019-10-11 18:18
 */
@Service
public class OneServiceImpl implements OneService {

	@Autowired
	private UserService userService;

	@Autowired
	private RestTemplate restTemplate;


	@LcnTransaction
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveInfo(String name) throws Exception {
		final Boolean twoResult = restTemplate.getForObject("http://127.0.0.1:8002/two?name=" + name, Boolean.class);
		if (!twoResult) {
			throw new Exception("transaction test");
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setName("one");
		userService.save(userEntity);

	}
}
