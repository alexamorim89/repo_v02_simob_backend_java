package br.com.simobapi.security.domain.service.impl;

import br.com.simobapi.domain.entity.UsuarioEntity;
import br.com.simobapi.domain.repository.criteria.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = repository.findByEmail(email);
        if(usuario == null){ throw new UsernameNotFoundException("Usuario não encontrado"); }

        return UserDetailsImpl.build(usuario);
    }
}