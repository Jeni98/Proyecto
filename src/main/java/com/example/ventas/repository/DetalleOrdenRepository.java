package com.example.ventas.repository;

import com.example.ventas.model.DetalleOrden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenRepository extends CrudRepository<DetalleOrden,Integer> {
}
