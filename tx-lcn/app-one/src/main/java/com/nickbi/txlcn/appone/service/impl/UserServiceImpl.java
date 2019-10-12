package com.nickbi.txlcn.appone.service.impl;

import com.nickbi.txlcn.appone.dao.UserMapper;
import com.nickbi.txlcn.appone.entity.UserEntity;
import com.nickbi.txlcn.appone.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
