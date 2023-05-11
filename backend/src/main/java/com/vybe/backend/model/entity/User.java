package com.vybe.backend.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Stack;

/**
 * User class that will be inherited by Customer and VenueAdmin classes
 * @author Harun Can Surav
 */
@Getter
@Setter
@Entity
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    /**
     * The users username, unique
     */
    @Id
    private String username;

    private String name;

    private String surname;
    /**
     * The users password
     */
    private String password;
    /**
     * The users phone number
     */
    @Column(unique=true)
    private String phoneNumber;
    /**
     * The users profile picture
     */
    //TODO: Decide on Object type
    @Transient
    private Object profilePicture;
    /**
     * The venue user is currently checked in
     */
    @Transient
    private Venue currentVenue;
    /**
     * The song requests issued by the user
     */
    // should it be transient?
    @Transient
    private Stack<SongRequest> requests;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("USER");
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
