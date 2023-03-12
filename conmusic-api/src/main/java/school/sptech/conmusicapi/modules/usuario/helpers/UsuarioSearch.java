package school.sptech.conmusicapi.modules.usuario.helpers;

import school.sptech.conmusicapi.modules.usuario.entities.Usuario;

import java.util.List;

public abstract class UsuarioSearch {

    protected List<Usuario> usuarios;

    public UsuarioSearch(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public abstract Usuario obterPorId(int id);

    public abstract Usuario obterPorEmail(String email);
}
