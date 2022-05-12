package com.prg2022.proyectoQR.services;
/*
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.prg2022.proyectoQR.modelos.Alumno;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AlumnosService implements UserDetails{
	private static final long serialVersionUID = 1L;
	private final Alumno alumno;
	
	public AlumnosService(Alumno alumno) {
		this.alumno = alumno;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(alumno.getRol()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return alumno.getClave();
	}

	@Override
	public String getUsername() {
		return alumno.getDni();
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
*/








import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.prg2022.proyectoQR.modelos.Alumno;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String username;

  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  public UserDetailsImpl(Long id, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }
  public static UserDetailsImpl build(Alumno user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getDescripcion().name()))
        .collect(Collectors.toList());
    return new UserDetailsImpl(
        user.getId(), 
        user.getDni(), 
        user.getClave(), 
        authorities);
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  public Long getId() {
    return id;
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
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}