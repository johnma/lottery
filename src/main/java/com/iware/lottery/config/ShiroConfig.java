package com.iware.lottery.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.*;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnma on 2016/11/2.
 */
@Configuration
@ComponentScan({"com.iware.lottery"})
public class ShiroConfig {

    @Inject
    private DataSource dataSource;

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(ApplicationContext applicationContext) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        final DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) applicationContext.getBean("mySecurityManager");
        shiroFilter.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
        filterChainDefinitionMapping.put("/login", "authc");
        filterChainDefinitionMapping.put("/logout", "logout");
        filterChainDefinitionMapping.put("/api/users/create", "anon");
        filterChainDefinitionMapping.put("/**", "anon");
        //filterChainDefinitionMapping.put("/**", "authc");


        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("anon", new AnonymousFilter());

        AuthenticationFilter authenticationFilter = new FormAuthenticationFilter();
        authenticationFilter.setLoginUrl("/login");
        authenticationFilter.setSuccessUrl("/home");
        filters.put("authc", authenticationFilter);

        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login?logout");

        filters.put("logout", logoutFilter);
        filters.put("roles", new RolesAuthorizationFilter());
        filters.put("user", new UserFilter());
        shiroFilter.setFilters(filters);

        return shiroFilter;
    }

    @Bean(name = "mySecurityManager")
    public DefaultWebSecurityManager securityManager(ApplicationContext applicationContext) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(jdbcRealm());
        final Realm myRealm = (Realm)applicationContext.getBean("myRealm");
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    @Bean(name = "myRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public JdbcRealm jdbcRealm() {
        JdbcRealm realm = new JdbcRealm();
        //HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        //realm.setCredentialsMatcher(credentialsMatcher);
        realm.setDataSource(this.dataSource);
        //realm.setAuthenticationQuery("SELECT password, CONCAT('${shiro.applicationSalt}', ':', salt) AS salt FROM user WHERE name = ?");
        realm.setAuthenticationQuery("SELECT password, salt FROM user WHERE name = ?");
        realm.setUserRolesQuery("SELECT name FROM role LEFT JOIN userRole ON role.id = user_role.roleId LEFT JOIN user ON userRole.userId = user.id WHERE user.id = ?");
        realm.setPermissionsQuery("SELECT permission FROM rolePermission LEFT JOIN role ON rolePermission.roleId = role.id WHERE role.name = ?");
        realm.init();
        return realm;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}

