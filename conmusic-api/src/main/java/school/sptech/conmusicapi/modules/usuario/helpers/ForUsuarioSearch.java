package school.sptech.conmusicapi.modules.usuario.helpers;

import school.sptech.conmusicapi.modules.usuario.dtos.ArtistaDto;
import school.sptech.conmusicapi.modules.usuario.dtos.CasaDto;
import school.sptech.conmusicapi.modules.usuario.dtos.UsuarioDto;
import school.sptech.conmusicapi.modules.usuario.entities.Artista;
import school.sptech.conmusicapi.modules.usuario.entities.Casa;
import school.sptech.conmusicapi.modules.usuario.entities.Usuario;

import java.util.ArrayList;
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

    @Override
    public List<UsuarioDto> listarUsuariosComoDto() {
        List<UsuarioDto> resultado = new ArrayList<>();

        for (Usuario u : usuarios) {
            resultado.add(new UsuarioDto(
                    u.getIdUsuario(),
                    u.getNome(),
                    u.getEmail(),
                    u.getTelefone(),
                    u.getSobre(),
                    u.isAutenticado()
            ));
        }

        return resultado;
    }

    @Override
    public List<ArtistaDto> listarArtistasComoDto() {
        List<ArtistaDto> resultado = new ArrayList<>();

        for (Usuario u : usuarios) {
            if (u instanceof Artista a) {
                resultado.add(new ArtistaDto(
                        a.getIdUsuario(),
                        a.getNome(),
                        a.getEmail(),
                        a.getTelefone(),
                        a.getSobre(),
                        a.isAutenticado(),
                        a.getCpf(),
                        a.getCompetencias())
                );
            }
        }

        return resultado;
    }

    @Override
    public List<CasaDto> listarCasasComoDto() {
        List<CasaDto> resultado = new ArrayList<>();

        for (Usuario u : usuarios) {
            if (u instanceof Casa c) {
                resultado.add(new CasaDto(
                        c.getIdUsuario(),
                        c.getNome(),
                        c.getEmail(),
                        c.getTelefone(),
                        c.getSobre(),
                        c.isAutenticado(),
                        c.getCnpj(),
                        c.getQtdTomada220(),
                        c.getQtdTomada110(),
                        c.getInfraestrutura()
                ));
            }
        }

        return resultado;
    }
}
