package school.sptech.conmusicapi.modules.usuario.helpers;

import school.sptech.conmusicapi.modules.usuario.entities.Usuario;

import java.util.List;

public class StreamUsuarioSearch extends UsuarioSearch {

    public StreamUsuarioSearch(List<Usuario> usuarios) {
        super(usuarios);
    }

    @Override
    public Usuario obterPorId(int id) {
        return usuarios.stream().filter(u -> u.getIdUsuario() == id).findFirst().orElse(null);
    }

    @Override
    public Usuario obterPorEmail(String email) {
        return usuarios.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }



}
