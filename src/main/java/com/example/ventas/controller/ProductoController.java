package com.example.ventas.controller;

import com.example.ventas.model.Producto;
import com.example.ventas.model.Usuario;
import com.example.ventas.repository.ProductoRepository;
import com.example.ventas.service.IUsuarioService;
import com.example.ventas.service.ProductoService;
import com.example.ventas.service.UploadFileService;
import com.example.ventas.service.UsuarioServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UploadFileService upload;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos",productoService.finAll());
        return "productos/show.html";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create.html";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objeto {}",producto);

        Usuario u= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(u);


        //subir la imagen y guardarla en la base de datos
        if (producto.getId()==null){//cuando se crea un producto
            String nombreImagen= upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }else{

        }

        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model,@PathVariable Integer id){
        Producto producto =new Producto();
        Optional<Producto> optionalProducto=productoService.get(id);
        producto = optionalProducto.get();
        LOGGER.info("Producto Buscado: {}",producto);
        model.addAttribute("producto",producto);
        return "productos/edit.html";
    }

    //actualizar el producto editado
    @PostMapping("/update")
    public String update(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {

        //obtener el usuario
        Producto p=new Producto();
        p=productoService.get(producto.getId()).get();

        if(file.isEmpty()){//cuando se modifica el producto se carga la misma imagen
            producto.setImagen(p.getImagen());
        }else{//cuando se edita tambien la imagen
            //eliminar cuando no sea la imagen por defecto
            if(!p.getImagen().equals("default.jpg")){
                upload.deleteImage(p.getImagen());
            }
            String nombreImagen= upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    //eliminar
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Producto p=new Producto();
        p=productoService.get(id).get();

        //eliminar cuando no sea la imagen por defecto
        if(!p.getImagen().equals("default.jpg")){
            upload.deleteImage(p.getImagen());
        }
        productoService.delete(id);
        return "redirect:/productos";
    }

}
