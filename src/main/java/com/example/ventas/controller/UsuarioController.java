package com.example.ventas.controller;

import com.example.ventas.model.DetalleOrden;
import com.example.ventas.model.Orden;
import com.example.ventas.model.Usuario;
import com.example.ventas.service.IUsuarioService;
import com.example.ventas.service.OrdenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();

    //mostrar pagina de registro
    @GetMapping("/registro")
    public String create(){
        return "usuario/registro.html";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        logger.info("Usuario Registro: {}",usuario);
        usuario.setTipo("USER");
        usuario.setPassword(passwordEncode.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login.html";
    }

    @GetMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){
        logger.info("Accesos; {}",usuario);
        Optional<Usuario> user= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        //logger.info("Usuario obtenido: {}", user.get());

        //validacion momentanea
        if (user.isPresent()){
            session.setAttribute("idusuario",user.get().getId());//mantener la sesion abierta
            if (user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else {
                return "redirect:/";
            }
        }else{
            logger.info("Usuario no existe");
        }
        return "redirect:/";
    }

    //obtener las compras
    @GetMapping("/compras")
    public String obtenerCompras(Model model,HttpSession session){
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
       List<Orden> ordenes = ordenService.findById(usuario);
       model.addAttribute("ordenes",ordenes);
       logger.info("La orden es: {}",ordenes);
        return "usuario/compras.html";
    }

    //Detalle de la orden
    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id,HttpSession session, Model model){
        logger.info("Id de la orden: {}",id);
        List<DetalleOrden> detalles = ordenService.findById(id);
        model.addAttribute("detalles",detalles);
        logger.info("Detalle de la orden: {}",detalles);
        //sesion
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        return "usuario/detallecompra.html";
    }

    //cerrar sesion
    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){
        session.removeAttribute("idusuario");
        return "redirect:/";
    }

}
