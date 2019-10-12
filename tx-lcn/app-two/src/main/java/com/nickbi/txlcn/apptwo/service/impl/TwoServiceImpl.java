package com.nickbi.txlcn.apptwo.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.nickbi.txlcn.apptwo.entity.UserEntity;
import com.nickbi.txlcn.apptwo.service.TwoService;
import com.nickbi.txlcn.apptwo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * package com.nickbi.txlcn.apptwo.service.impl
 * description <p/>
 *
 * @author wzfc-001
 * create on 2019-10-12 09:43
 */
@Service
public class TwoServiceImpl implements TwoService {

	private final UserService userService;

	public TwoServiceImpl(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 保存用户名
	 *
	 * @param name
	 * @return
	 */
	@LcnTransaction
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveInfo(String name) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);
		userService.save(userEntity);
		return !"ex".equals(name);
	}
}
