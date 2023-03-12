package school.sptech.conmusicapi.modules.usuario.helpers;

import school.sptech.conmusicapi.modules.usuario.dtos.ArtistaDto;
import school.sptech.conmusicapi.modules.usuario.dtos.CasaDto;
import school.sptech.conmusicapi.modules.usuario.dtos.UsuarioDto;
import school.sptech.conmusicapi.modules.usuario.entities.Artista;
import school.sptech.conmusicapi.modules.usuario.entities.Casa;
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

    @Override
    public List<UsuarioDto> listarUsuariosComoDto() {
        return usuarios.stream()
                .map(u -> new UsuarioDto(
                    u.getIdUsuario(),
                    u.getNome(),
                    u.getEmail(),
                    u.getTelefone(),
                    u.getSobre(),
                    u.isAutenticado()
                ))
                .toList();
    }

    @Override
    public List<ArtistaDto> listarArtistasComoDto() {
        return usuarios.stream()
                .filter(u -> u instanceof Artista)
                .map(u -> (Artista) u)
                .map(a -> new ArtistaDto(
                        a.getIdUsuario(),
                        a.getNome(),
                        a.getEmail(),
                        a.getTelefone(),
                        a.getSobre(),
                        a.isAutenticado(),
                        a.getCpf(),
                        a.getCompetencias()
                ))
                .toList();
    }

    @Override
    public List<CasaDto> listarCasasComoDto() {
        return usuarios.stream()
                .filter(u -> u instanceof Casa)
                .map(u -> (Casa) u)
                .map(c -> new CasaDto(
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
                ))
                .toList();
    }
}
