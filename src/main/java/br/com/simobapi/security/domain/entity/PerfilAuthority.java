package br.com.simobapi.security.domain.entity;

import lombok.Data;
import lombok.Builder;

import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
public class PerfilAuthority implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}