package com.tongfu.shiro;

import com.tongfu.dao.CompanyRoleAuthorityDao;
import com.tongfu.dao.CompanyRoleDao;
import com.tongfu.dao.RoleAuthorityDao;
import com.tongfu.entity.Admin;
import com.tongfu.entity.CompanyRole;
import com.tongfu.service.AdminService;
import com.tongfu.service.RoleAuthorityService;
import com.tongfu.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleAuthorityService roleAuthorityService;

    @Autowired
    CompanyRoleDao companyRoleDao;

    @Autowired
    CompanyRoleAuthorityDao companyRoleAuthorityDao;



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //获取登录用户名
//        String userName= (String) principalCollection.getPrimaryPrincipal();
//        //查询用户名称
//        Admin admin = adminService.selectByUserName(userName);

        //查询用户
        Admin admin = adminService.getCurrent();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        if (admin.getCompanyId()!=null){//获取供应商对应的权限和角色

            List<String> roleNames = companyRoleDao.getCompanyRoleName(admin.getUsername());
            logger.info("拥有"+String.valueOf(roleNames.size())+"个角色");
            //添加角色
            simpleAuthorizationInfo.addRoles(roleNames);

            Collection<String> authorities = companyRoleAuthorityDao.getAuthorities(admin.getUsername());

            logger.info("拥有"+String.valueOf(authorities.size())+"个权限");
            //        //添加权限
            simpleAuthorizationInfo.addStringPermissions(authorities);

        }else{
            List<String> roleNames = roleService.getRoleName(admin.getUsername());
            logger.info("拥有"+String.valueOf(roleNames.size())+"个角色");
            //添加角色
            simpleAuthorizationInfo.addRoles(roleNames);

            Collection<String> authorities = roleAuthorityService.getAuthorities(admin.getUsername());

            logger.info("拥有"+String.valueOf(authorities.size())+"个权限");
            //        //添加权限
            simpleAuthorizationInfo.addStringPermissions(authorities);
        }




//        for (String authoritie:authorities) {
//            simpleAuthorizationInfo.addStringPermission(authoritie);
//        }

        try {
            //不确定是什么原因导致权限可能会生成一个空值"", 会报错,所以将空值删除
            if (simpleAuthorizationInfo != null && simpleAuthorizationInfo.getStringPermissions() != null) {
                Set<String> permissions = simpleAuthorizationInfo.getStringPermissions();
                for (String permission : permissions) {
                    if (StringUtils.isEmpty(permission)) {
                        permissions.remove(permission);
                    }
                }
            }
        }catch (Exception e){
            logger.info("移除空值权限出错---"+e.getMessage());
        }

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        String username = authenticationToken.getPrincipal().toString();
        logger.info("username = {}", username);
        Admin admin = null;
        try {
             admin  = adminService.selectByUserName(username);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        logger.info("{}", null!=admin?admin.toString():"null");
        if(null != admin) {

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    admin, //用户名
                    admin.getPassword(), //密码
                    ByteSource.Util.bytes(admin.getUsername()),//salt=username+salt
                    getName()  //realm name
            );

//            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, admin.getPassword(), ,getName());
            return authenticationInfo;
        }
        return null;
    }
}
