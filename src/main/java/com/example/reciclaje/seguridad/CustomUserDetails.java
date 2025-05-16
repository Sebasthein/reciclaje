package com.example.reciclaje.seguridad;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	 private String email;
	    private String password;  // Si necesitas agregar la contraseña (aunque no la uses directamente)
	    private Collection<? extends GrantedAuthority> authorities;

	    // Constructor
	    public CustomUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities) {
	        this.email = email;
	        this.password = password;
	        this.authorities = authorities;
	    }
	    
	    @Override
	    public String getUsername() {
	        return email;  // El username generalmente es el email
	    }

	    @Override
	    public String getPassword() {
	        return password;  // Si tienes la contraseña, devuelve eso
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;  // Retorna los roles o permisos del usuario
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;  // Para no marcar la cuenta como expirada
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;  // Para no bloquear la cuenta
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;  // Para no bloquear las credenciales
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;  // Para indicar que la cuenta está habilitada
	    }
}
