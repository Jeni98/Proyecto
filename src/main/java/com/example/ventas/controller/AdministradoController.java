package com.example.ventas.controller;

import com.example.ventas.model.DetalleOrden;
import com.example.ventas.model.Orden;
import com.example.ventas.model.Producto;
import com.example.ventas.service.IUsuarioService;
import com.example.ventas.service.OrdenService;
import com.example.ventas.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradoController {

    private Logger logg= LoggerFactory.getLogger(AdministradoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    @GetMapping("")
    public String home(Model model){
        List<Producto> productos=productoService.finAll();
        model.addAttribute("productos",productos);
        return "administrador/home.html";
    }

    //ver usuarios
    @GetMapping("/usuarios")
    public String usuarios(Model model){
        model.addAttribute("usuarios",usuarioService.findAll());
        return "administrador/usuarios.html";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model){
        model.addAttribute("ordenes",ordenService.findAll());
        return "administrador/ordenes.html";
    }

    //ver detalles de la orden
    @GetMapping("/detalle/{id}")
    public String detalle(Model model, @PathVariable Integer id){
        logg.info("id de la orden: {}",id);
        List<DetalleOrden> detalles = ordenService.findById(id);
        model.addAttribute("detalles",detalles);
        //Orden orden = ordenService.find(id).get();

       // model.addAttribute("detalles",orden.getDetalle());
        return "administrador/detalleorden.html";
    }

}
