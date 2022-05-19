package com.example.ventas.service;

import com.example.ventas.model.DetalleOrden;
import com.example.ventas.model.Orden;
import com.example.ventas.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface OrdenService {
    List<Orden> findAll();
    List<DetalleOrden> findById(Integer id);
    Optional<Orden> find(Integer id);
    Orden save(Orden orden);
    String generarNumeroOrden();
    List<Orden> findById(Usuario usuario);

}
