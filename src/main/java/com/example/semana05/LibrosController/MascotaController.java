/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.semana05.LibrosController;

import com.example.semana05.model.Mascota;
import com.example.semana05.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/mascotas")
public class MascotaController {
     @Autowired
    private MascotaService service;

    @GetMapping
    public String listarMascotas(Model model) {
        model.addAttribute("mascotas", service.listarTodas());
        return "mascotas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "mascotas/formulario";
    }

    @PostMapping
    public String guardarMascota(@ModelAttribute Mascota mascota) {
        service.guardar(mascota);
        return "redirect:/mascotas";
    }

    @GetMapping("/editar/{id}")
    public String editarMascota(@PathVariable Long id, Model model) {
        Mascota mascota = service.obtenerPorId(id);
        model.addAttribute("mascota", mascota);
        return "mascotas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/mascotas";
    }
}
