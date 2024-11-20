package com.example.BancoSimples.Controller;


import com.example.BancoSimples.Model.Entity.Usuario;
import com.example.BancoSimples.Model.Repository.UsuarioRepository;
import com.example.BancoSimples.Model.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity getAllUsuarios(){
        var allUsuarios = usuarioRepository.findAllBycontaAtivaTrue();
        return ResponseEntity.ok(allUsuarios);
    }

    @PostMapping
    public ResponseEntity registerUsuario(@RequestBody @Validated Usuario usuario){
        try {
            usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateUsuario(@RequestBody @Validated Usuario usuario){
        try {
            usuarioService.atualizarUsuario(usuario);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUsuario(@PathVariable @Validated Long id){
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
