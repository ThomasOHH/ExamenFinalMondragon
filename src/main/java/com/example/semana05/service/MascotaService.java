/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.semana05.service;

import com.example.semana05.REPOSITORY.MascotaRepository;
import com.example.semana05.model.Mascota;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service

public class MascotaService {
    @Autowired
    private MascotaRepository repository;

    public List<Mascota> listarTodas() {
        return repository.findAll();
    }

    public Mascota guardar(Mascota mascota) {
        return repository.save(mascota);
    }

    public Mascota obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
