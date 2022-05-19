package com.example.ventas.service;

import com.example.ventas.model.Producto;
import com.example.ventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> finAll() {
        String sql= "SELECT * FROM PRODUCTOS";
        List<Producto> productos=template.query(sql,new BeanPropertyRowMapper<Producto>(Producto.class));
        return productos;
    }



}
