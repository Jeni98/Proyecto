package com.example.ventas.service;

import com.example.ventas.model.DetalleOrden;
import com.example.ventas.model.Usuario;
import com.example.ventas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public List<Usuario> findAll() {
        String sql= "SELECT * FROM USUARIOS";
        List<Usuario> usuarios=template.query(sql,new BeanPropertyRowMapper<Usuario>(Usuario.class));
        return usuarios;
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
