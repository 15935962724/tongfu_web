package com.tongfu.entity;

public class CompanyRoleAuthority {
    private Long companyRole;

    private String authorities;

    public Long getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(Long companyRole) {
        this.companyRole = companyRole;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities == null ? null : authorities.trim();
    }
}