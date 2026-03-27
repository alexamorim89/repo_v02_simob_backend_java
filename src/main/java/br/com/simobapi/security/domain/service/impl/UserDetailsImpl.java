package br.com.simobapi.security.domain.service.impl;

import br.com.simobapi.domain.entity.UsuarioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String matricula;
    private String nome;
    private String email;
    @JsonIgnore
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetailsImpl build(UsuarioEntity usuario){
        List<GrantedAuthority> permissoes = usuario
                                            .getPerfis().stream().map(perfil -> new SimpleGrantedAuthority(perfil.getTipo().toString() ))
                                            .collect(Collectors.toList());
        return UserDetailsImpl
                .builder()
                    .id(usuario.getCodigo())
                    .matricula(usuario.getMatricula())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .senha(usuario.getSenha())
                    .authorities(permissoes)
                .build();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
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
