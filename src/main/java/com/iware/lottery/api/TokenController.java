package com.iware.lottery.api;

import com.iware.lottery.Constants;
import com.iware.lottery.auth.Authentication;
import com.iware.lottery.auth.CurrentUser;
import com.iware.lottery.domain.User;
import com.iware.lottery.exception.InvalidRequestException;
import com.iware.lottery.model.*;
import com.iware.lottery.service.TokenService;
import com.iware.lottery.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by johnma on 2016/11/10.
 */
@RestController
@RequestMapping (value = Constants.URI_API + Constants.URI_TOKEN)
public class TokenController {
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseBody
    public ResponseEntity<Result> login(@RequestBody @Valid LoginForm form, BindingResult ret){
        logger.debug("login. user @" + form.getName());

        if (ret.hasErrors()) {
            logger.debug("binding result error");
            throw new InvalidRequestException(ret);
        }

        UsernamePasswordToken shiroToken = new UsernamePasswordToken(form.getName(), form.getPassword());

        Subject currentUser = SecurityUtils.getSubject();
        try{
            currentUser.login(shiroToken);
        }
        catch (UnknownAccountException uae ) {
            //username wasn't in the system, show them an error message?
            logger.debug("account is not exist. name @" + form.getName());
        } catch ( IncorrectCredentialsException ice ) {
            //password didn't match, try again?
            logger.debug("password is invalid. name @" + form.getName());
        } catch ( LockedAccountException lae ) {
            //account for that username is locked - can't login.  Show them a message?
            logger.debug("account is locked. name @" + form.getName());
        }catch ( AuthenticationException ae ) {
            //unexpected condition - error?
            logger.debug("unexpected error. name @" + form.getName());
        }

        //将用户token写入redis
        logger.debug("get token. user @" + form.getName());
        UserDetails user = userService.findUserByName(form.getName());
        Long userId = user.getId();

        Token token = tokenService.createToken(userId);

        return new ResponseEntity<>(new Result(100, "success to login", token), HttpStatus.OK);
    }

    @Authentication
    @RequestMapping(method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authentication", value = "authentication", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResponseMessage> logout(@CurrentUser User user){
        //将用户token从redis中删除
        return new ResponseEntity<>(ResponseMessage.success("success to logout"), HttpStatus.OK);
    }
}
