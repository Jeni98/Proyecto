package com.example.ventas.repository;

import com.example.ventas.model.Orden;
import com.example.ventas.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends CrudRepository<Orden,Integer> {
    List<Orden> findById(Usuario usuario);
    //obtener las ordenes por usuario

}
