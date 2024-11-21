package com.clinica.sistema.inventario.controlador;

import com.clinica.sistema.inventario.model.Categoria;
import com.clinica.sistema.inventario.model.Producto;
import com.clinica.sistema.inventario.service.CategoriaServicio;
import com.clinica.sistema.inventario.service.ProductoServicio;
import com.clinica.sistema.inventario.service.ProveedorServicio;
import com.clinica.sistema.inventario.util.paginacion.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class ProductoController {
    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private CategoriaServicio categoriaServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

@GetMapping("/producto")
    public String listarProductos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("isAdmin", userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));
        }
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Producto> productos = productoServicio.findAll(pageRequest);
        PageRender<Producto> pageRender = new PageRender<>("/producto", productos);

        model.addAttribute("producto", productos.getContent());
        model.addAttribute("categorias", categoriaServicio.listarCategorias());
        model.addAttribute("proveedores",proveedorServicio.listarProveedores());
        model.addAttribute("page", pageRender);

        return "ProductoListar";
    }

    @PostMapping("/producto/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes flash){
        try{
            productoServicio.save(producto);
            flash.addFlashAttribute("success", "Producto guardado con éxito");
        }catch(Exception e){
            flash.addFlashAttribute("error", "Error al guardar el producto");
        }
        return "redirect:/producto";
    }

    @PostMapping("/producto/actualizar")
    public String actualizar(@ModelAttribute Producto producto, RedirectAttributes flash){
        try {
            Optional<Producto> productoExistente = productoServicio.findById(producto.getIdProducto() );
            if (productoExistente.isPresent()) {
                productoExistente.get().setNombre(producto.getNombre());
                productoExistente.get().setDescripcion(producto.getDescripcion());
                productoExistente.get().setCategoria(producto.getCategoria());
                productoExistente.get().setProveedor(producto.getProveedor());
                productoServicio.save(productoExistente.orElse(null));
                flash.addFlashAttribute("success", "Producto actualizado con éxito");
            }
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al actualizar el producto");
        }
        return "redirect:/producto";
    }

    @GetMapping("/producto/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash){
        if (id > 0) {
            try {
                productoServicio.delete(id);
                flash.addFlashAttribute("success", "Producto eliminado con éxito");
            } catch (Exception e) {
                flash.addFlashAttribute("error", "Error al eliminar el producto");
            }
        }
        return "redirect:/producto";
    }
}
