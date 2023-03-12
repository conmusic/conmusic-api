package school.sptech.conmusicapi.modules.usuario.helpers;

import school.sptech.conmusicapi.modules.usuario.entities.Usuario;

import java.util.List;

public class ForUsuarioSearch extends UsuarioSearch {

    public ForUsuarioSearch(List<Usuario> usuarios) {
        super(usuarios);
    }

    @Override
    public Usuario obterPorId(int id) {
        for (Usuario usuario: usuarios){
            if (usuario.getIdUsuario() == id){
                return usuario;
            }
        }
            return null;
    }

    @Override
    public Usuario obterPorEmail(String email) {
        for (Usuario usuario: usuarios){
            if (usuario.getEmail().equals(email)){
                return usuario;
            }
        }
        return null;
    }
}
