package school.sptech.conmusicapi.modules.user.dtos;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsDto implements UserDetails {
    private final String name;
    private final String email;
    private final String password;
    private final String userType;

    public UserDetailsDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userType = user.getUserType();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(getUserType()));
        return authorities;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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