/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.semana05.REPOSITORY;

import com.example.semana05.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}