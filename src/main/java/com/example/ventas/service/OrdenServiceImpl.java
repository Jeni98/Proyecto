package com.example.ventas.service;

import com.example.ventas.model.DetalleOrden;
import com.example.ventas.model.Orden;
import com.example.ventas.model.Usuario;
import com.example.ventas.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements OrdenService{
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public List<Orden> findAll() {
        return (List<Orden>) ordenRepository.findAll();
    }

    @Override
    public List<DetalleOrden> findById(Integer id) {
        String sql= "SELECT * FROM DETALLES";
        List<DetalleOrden> detalles=template.query(sql,new BeanPropertyRowMapper<DetalleOrden>(DetalleOrden.class));
        return detalles;
    }

    @Override
    public Optional<Orden> find(Integer id) {
        return ordenRepository.findById(id);
    }


    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public String generarNumeroOrden(){
        int numero =0;
        String numeroConcatenado="";
        List<Orden> ordenes = findAll();
        List<Integer> numeros = new ArrayList<Integer>();

        //pasar de una cadena a numeros
        ordenes.stream().forEach(o->numeros.add(Integer.parseInt(o.getNumero())));

        //hacer el incrementeo
        if (ordenes.isEmpty()){
            numero=1;
        }else {
            numero=numeros.stream().max(Integer::compare).get(); //mayor numero de todos las ordenes
            numero++;
        }

        //numeros de ordenes
        if (numero<10){
            numeroConcatenado="000000000"+String.valueOf(numero);
        }else if(numero<100){
            numeroConcatenado="00000000"+String.valueOf(numero);
        }else if (numero<1000){
            numeroConcatenado="0000000"+String.valueOf(numero);
        }else if (numero<10000){
            numeroConcatenado="000000"+String.valueOf(numero);
        }
        return numeroConcatenado;
    }

    @Override
    public List<Orden> findById(Usuario usuario) {
        String sql= "SELECT * FROM ORDENES";
        List<Orden> ordens=template.query(sql,new BeanPropertyRowMapper<Orden>(Orden.class));
        return ordens;
    }


}
