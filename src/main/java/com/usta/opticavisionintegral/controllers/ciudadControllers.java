package com.usta.opticavisionintegral.controllers;

import com.usta.opticavisionintegral.Entities.ciudadEntity;
import com.usta.opticavisionintegral.models.services.IciudadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
public class ciudadControllers {

    @Autowired
    private IciudadServices iciudadServices;

    @GetMapping("listasCiudades")
    public String listarCiudad(Model model){
        model.addAttribute("titulo","listar Universidades");
        model.addAttribute("Ciudades",iciudadServices.finAll());
        return "listasCiudades";
    }

    @GetMapping("crearCiudad")
    public String crearCiudad(Model model){
        model.addAttribute("titulo","Crear Ciudad");
        model.addAttribute("Ciudad",new ciudadEntity());
        return "crearCiudad";
    }

    @PostMapping(value = "crearCiudad")
    public String guardarCiudad(@Valid ciudadEntity ciudad, BindingResult result, SessionStatus status){
        if (result.hasErrors()){
            return "crearCiudad";
        }
        iciudadServices.save(ciudad);
        status.setComplete();
        return "redirect:/listasCiudades";
    }
    @RequestMapping(value = "/eliminarCiudad/{id}")
    public String eliminarByIdCiudad(@PathVariable(value = "id") Long id){
        if (id>0){
            iciudadServices.remove(id);
        }else{
            return "redirect:/error500";
        }
        return "redirect:/listasCiudades";

    }

    @RequestMapping(value = "/cambiarEstadoCiudad/{id}")
    public String cambiarEstadoCiudad(@PathVariable(value = "id") Long id){
        if (id>0){
            iciudadServices.changeState(id);
        }else {
            return "redirect:/error500";
        }
        return "redirect:/listasCiudades";
    }
    @GetMapping("/editarCiudad/{id}")
    public String mostrarFormularioCiudad(@PathVariable(value = "id")Long id, Model model){
        model.addAttribute("titulo","Editar Ciudad");
        model.addAttribute("ciudadActualizar",iciudadServices.findOne(id));
        return "editarCiudad";
    }
    @PostMapping("editarCiudad/{id}")
    public String actualizarCiudad(@PathVariable(value = "id") Long id, @ModelAttribute("seccionalActualizar") ciudadEntity ciudad){
        ciudadEntity ciudadlExistente = iciudadServices.findOne(id);
        ciudadlExistente.setNombre_ciudad(ciudad.getNombre_ciudad());

        iciudadServices.updateCiudad(ciudadlExistente);
        return "redirect:/listasCiudades";
    }
}
