package com.example.BancoSimples.Model.Repository;

import com.example.BancoSimples.Model.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    List<Usuario> findAllBycontaAtivaTrue();

   List<Usuario> findAllByCPF(String Cpf);

   Optional<Usuario> findByCPF(String CPF);

    List<Usuario> findByEmail(String email);

}
