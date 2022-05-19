package com.example.ventas.controller;

import com.example.ventas.model.DetalleOrden;
import com.example.ventas.model.Orden;
import com.example.ventas.model.Producto;
import com.example.ventas.model.Usuario;
import com.example.ventas.service.DetalleOrdenService;
import com.example.ventas.service.IUsuarioService;
import com.example.ventas.service.OrdenService;
import com.example.ventas.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log= LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    //almacenar el detalle de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //almacena los datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session){
        log.info("Sesion del usuario: {}",session.getAttribute("idusuario"));
        model.addAttribute("productos",productoService.finAll());

        //session
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        return "usuario/home.html";
    }

    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model){
        log.info("Id producto enviado como parametro {}",id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional=productoService.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto",producto);
        return "usuario/productoHome.html";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal =0;

        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}",optionalProducto.get());
        log.info("Cantidad: {}",cantidad);
        producto=optionalProducto.get();

        //obtenemos los detalles
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //validadar que el producto no se añada dos veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p->p.getProducto().getId()==idProducto);

        if (!ingresado){
            detalles.add(detalleOrden);
        }

        //sumatotal de los productos
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        //llevar a la vista toda la informacion
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        return "usuario/carrito.html";
    }

    //quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model){
        List<DetalleOrden> ordenesNuevas= new ArrayList<DetalleOrden>();
        for (DetalleOrden detalleOrden: detalles){
            if (detalleOrden.getProducto().getId()!=id){
                ordenesNuevas.add(detalleOrden);
            }
        }
        //poner nueva lista
        detalles=ordenesNuevas;
        //recalcular nuevamente el total
        double sumaTotal=0;
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);

        //llevar a la vista toda la informacion
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        return "usuario/carrito.html";
    }

    //obtener el carrito desde cualquier sitio
    @GetMapping("/getCart")
    public String getCart(Model model,HttpSession session){

        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        //sesion
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        return "/usuario/carrito.html";
    }


    @GetMapping("/order")
    public String order(Model model, HttpSession session){
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario",usuario);
        return "usuario/resumenorden.html";
    }

    //guardar la orden
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //usuario de la orden
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        orden.setUsuario(usuario);
        ordenService.save(orden);

        //guardar detalles de la orden
        for (DetalleOrden dt:detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

        //limpiar lista y orden
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    //busqueda del producto
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model){
        log.info("Nombre del producto: {}",nombre);

        //obtiene los productos por su nombre tal cual se encuentra en la base de datos
       List<Producto> productos =productoService.finAll().stream().filter(p->p.getNombre().contains(nombre)).collect(Collectors.toList());
       model.addAttribute("productos",productos);

       return "usuario/home.html";
    }

}
