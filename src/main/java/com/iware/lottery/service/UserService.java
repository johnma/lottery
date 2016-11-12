package com.iware.lottery.service;

import com.iware.lottery.DTOUtils;
import com.iware.lottery.common.IdKit;
import com.iware.lottery.domain.User;
import com.iware.lottery.exception.ResourceNotFoundException;
import com.iware.lottery.model.RegistrationForm;
import com.iware.lottery.model.UserDetails;
import com.iware.lottery.model.UserForm;
import com.iware.lottery.repository.UserRepository;
import com.iware.lottery.repository.UserSpecifications;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by johnma on 2016/11/2.
 */
@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.logger(UserService.class);

    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails saveUser(RegistrationForm form){
        logger.debug("save user @" + form);

        User user = DTOUtils.map(form, User.class);

        User saved = userRepository.save(user);

        logger.debug("saved user is @" + saved);

        return DTOUtils.map(saved, UserDetails.class);
    }

    public Page<UserDetails> searchUsersByCriteria(String q, Pageable page){

        logger.debug("search users by keyword@" + q + ", page @" + page);

        Page<User> users = userRepository.findAll(UserSpecifications.filterByKeyword(q), page);

        logger.debug("get users size @" + users.getTotalElements());

        return DTOUtils.mapPage(users, UserDetails.class);
    }

    public UserDetails findUserById(Long id){
        Assert.notNull(id, "user id can not be null");

        logger.debug("find user by id@" + id);

        User user = userRepository.findOne(id);

        if (user == null){
            throw new ResourceNotFoundException(id+"");
        }

        return DTOUtils.map(user, UserDetails.class);
    }

    public UserDetails findUserByName(String name){
        Assert.notNull(name, "user name can not be null");

        logger.debug("find user by name@" + name);

        //User user = userRepository.findOne(UserSpecifications.exactfilterByKeyword(name));
        User user = userRepository.findByName(name);

        if (user == null || !user.getName().equals(name)){
            throw new ResourceNotFoundException(name);
        }

        return DTOUtils.map(user, UserDetails.class);
    }

    public UserDetails updateUser(UserForm form){
        Assert.notNull(form.getId(), "user id can not be null");

        logger.debug("update user@" + form);

        User user = userRepository.findOne(form.getId());

        DTOUtils.mapTo(form, user);

        User saved = userRepository.save(user);

        logger.debug("updated user@" + saved);

        return DTOUtils.map(saved, UserDetails.class);
    }

    public boolean deleteUserById(Long id){
        Assert.notNull(id, "user id can not be null");

        logger.debug("delete user by id@" + id);

        User user = userRepository.findOne(id);

        if (null == user){
            throw new  ResourceNotFoundException(id+"");
        }

        userRepository.delete(id);

        return true;
    }

    public UserDetails findUserByToken(String token){
        Assert.notNull(token, "user token can not be null");

        logger.debug("find user by name@" + token);

        //User user = userRepository.findOne(UserSpecifications.exactfilterByToken(token));
        User user = userRepository.findByToken(token);

        if (user == null || !user.getName().equals(token)){
            throw new ResourceNotFoundException(token);
        }

        return DTOUtils.map(user, UserDetails.class);
    }
}
