package com.nickbi.txlcn.apptwo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nickbi.txlcn.apptwo.dao.UserMapper;
import com.nickbi.txlcn.apptwo.entity.UserEntity;
import com.nickbi.txlcn.apptwo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nickbi
 * @since 2019-10-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
