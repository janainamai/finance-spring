package br.com.planner.authentication.security;

import br.com.planner.authentication.domain.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserAccount userAccount) {
        this.username = userAccount.getUsername();
        this.password = userAccount.getPassword();
        this.authorities = getAuthorities(userAccount);
    }

    private static List<SimpleGrantedAuthority> getAuthorities(UserAccount userAccount) {
        return userAccount.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName())))
                .toList();
    }

    public static CustomUserDetails create(UserAccount userAccount) {
        return new CustomUserDetails(userAccount);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
