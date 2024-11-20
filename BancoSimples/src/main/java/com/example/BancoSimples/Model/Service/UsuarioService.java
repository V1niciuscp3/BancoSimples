package com.example.BancoSimples.Model.Service;


import com.example.BancoSimples.Model.Entity.Usuario;
import com.example.BancoSimples.Model.Repository.UsuarioRepository;
import com.example.BancoSimples.Model.Util.CpfUtil;
import com.example.BancoSimples.Model.Util.SenhaUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public void deletarUsuario(Long usuarioId){
        Optional<Usuario> usuarioExiste = usuarioRepository.findById(usuarioId);
        if (usuarioExiste.isPresent()){
            Usuario usuario = usuarioExiste.get();
            usuario.setContaAtiva(false);
            usuarioRepository.save(usuario);
        }
    }

    public String registrarUsuario(Usuario usuario){

        CpfUtil cpfUtil = new CpfUtil(usuario.getCPF());
        if (!cpfUtil.isValido()){
            throw new IllegalArgumentException("CPF invalido");
        }

        if(usuario.getCPF() == null || usuario.getCPF().isEmpty()){
            throw new IllegalArgumentException("O campo CPF nao pode ser vazio");
        }
        List<Usuario> usuarios = usuarioRepository.findAllByCPF(usuario.getCPF());
        if (!usuarios.isEmpty()) {
            throw new IllegalArgumentException("CPF ja cadastrado no sistema");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()){
            throw new IllegalArgumentException("Campo email nao pode ser vazio");
        }
        List<Usuario> emailUsuarios = usuarioRepository.findByEmail(usuario.getEmail());
        if (!emailUsuarios.isEmpty()){
            throw new IllegalArgumentException("Email ja cadastrado no sistema");
        }
        String hashSenha = SenhaUtil.enconder(usuario.getSenha());
        usuario.setSenha(hashSenha);
        usuario.setContaAtiva(true);
        usuarioRepository.save(usuario);
        return "Cadastrado realizado com sucesso";
    }

    public String atualizarUsuario(Usuario usuario){
        Usuario usuarioExiste = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario nao existe"));

        if (usuario.getCep() != null && !usuario.getCep().isEmpty()) {
            usuarioExiste.setCep(usuario.getCep());
        }

        if (usuario.getEndereco() != null && !usuario.getEndereco().isEmpty()){
            usuarioExiste.setEndereco(usuario.getEndereco());
        }

        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()){
            String hashSenha = SenhaUtil.enconder(usuario.getSenha());
            usuario.setSenha(hashSenha);
        }

        if (!usuarioExiste.getCPF().equals(usuario.getCPF())){
            throw new EntityNotFoundException();
        }
        usuarioRepository.save(usuario);
        return "Dados atualizados com sucesso";
    }


}
