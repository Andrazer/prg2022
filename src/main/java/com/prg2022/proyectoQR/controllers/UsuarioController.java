package com.prg2022.proyectoQR.controllers;

import java.util.List;

import com.prg2022.proyectoQR.Repository.MovimientoRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.modelos.Movimiento;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository urepository;

    @Autowired
    private MovimientoRepository mrepository;

    @GetMapping("/detalle/{id}")
    /*    @ResponseBody public String detalles(){
        UserDetailsImpl quien = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return quien.getId().toString();
    }*/

    public ModelAndView showBrigada(@PathVariable Long id, ModelAndView modelAndView) {
        Usuario usuario = urepository.getById(id);
        List<Movimiento> movimientos = mrepository.findTop10ByUsuario(usuario);
        //List<Usuario> usuarios = urepository.findByBrigada(brigada);
        //UploadFileRequest importarArchivo = new UploadFileRequest();
        modelAndView.addObject("movimientos", movimientos);
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("id", id);
        modelAndView.setViewName("usuario_detail");
        //modelAndView.addObject("importarArchivo", importarArchivo);
        return modelAndView;
    }
}
