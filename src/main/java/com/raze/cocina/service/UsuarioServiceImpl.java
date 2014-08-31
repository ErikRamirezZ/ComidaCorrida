package com.raze.cocina.service;

import com.raze.cocina.domain.Usuario;
import com.raze.cocina.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
    UsuarioRepository usuarioRepository;

	public long countAllUsuarios() {
        return usuarioRepository.count();
    }

	public void deleteUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

	public Usuario findUsuario(Long id) {
        return usuarioRepository.findOne(id);
    }

	public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

	public List<Usuario> findUsuarioEntries(int firstResult, int maxResults) {
        return usuarioRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

	public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
