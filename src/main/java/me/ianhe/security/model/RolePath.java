package me.ianhe.security.model;

/**
 * @author iHelin
 * @date 2018-12-17 17:37
 */
public class RolePath {

    private String pathPattern;
    private String role;

    public RolePath(String pathPattern, String role) {
        this.pathPattern = pathPattern;
        this.role = role;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
