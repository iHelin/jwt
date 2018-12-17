package me.ianhe.security.config;

import me.ianhe.security.model.RolePath;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author iHelin
 * @date 2018-12-13 15:05
 */
public class AppFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final PathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 模拟从DB加载
     *
     * @return
     */
    private List<RolePath> getFromDB() {
        List<RolePath> list = new ArrayList<>();
        list.add(new RolePath("/test", "ROLE_ADMIN"));
        list.add(new RolePath("/", "ROLE_ADMIN"));
        list.add(new RolePath("/", "ROLE_USER"));
        return list;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();

        //查询当前url需要的角色
        List<ConfigAttribute> attributes = new ArrayList<>();
        for (RolePath rolePath : getFromDB()) {
            if (antPathMatcher.match(rolePath.getPathPattern(), url)) {
                attributes.add(new SecurityConfig(rolePath.getRole()));
            }
        }
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
