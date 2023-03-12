package school.sptech.conmusicapi.modules.usuario.controllers;

import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.usuario.dtos.*;
import school.sptech.conmusicapi.modules.usuario.entities.Artista;
import school.sptech.conmusicapi.modules.usuario.entities.Casa;
import school.sptech.conmusicapi.modules.usuario.entities.Usuario;
import school.sptech.conmusicapi.modules.usuario.helpers.StreamUsuarioSearch;
import school.sptech.conmusicapi.modules.usuario.helpers.UsuarioSearch;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private List<Usuario> usuarios;

    private int identity;

    private UsuarioSearch usuarioSearch;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
    }

    @PostMapping("/artista")
    public ArtistaDto cadastrarArtista(@RequestBody CriarArtistaDto dto){
        Artista artista = new Artista(identity, dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getTelefone(),
                dto.getSobre(), dto.getCpf(), dto.getCompetencias());
        this.usuarios.add(artista);
        ArtistaDto artistaDto = new ArtistaDto(artista.getIdUsuario(), artista.getNome(), artista.getEmail(),
                artista.getTelefone(), artista.getSobre(), artista.getCpf(), artista.getCompetencias(),
                artista.getAtivo());
        return artistaDto;
    }

    @PostMapping("/casa")
    public CasaDto cadastrarCasa(@RequestBody CriarCasaDto dto){
        Casa casa = new Casa(identity, dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getTelefone(), dto.getSobre(),
                dto.getCnpj(), dto.getQtdTomada220(), dto.getQtdTomada110(), dto.getInfraestrutura());
        this.usuarios.add(casa);
         CasaDto casaDto = new CasaDto(casa.getIdUsuario(), casa.getNome(), casa.getEmail(), casa.getTelefone(),
                 casa.getSobre(), casa.getCnpj(), casa.getQtdTomada220(), casa.getQtdTomada110(), casa.getInfraestrutura());
        return casaDto;
    }

    @PostMapping("/login")
    public UsuarioDto login(@RequestBody LoginDto loginDto){
        this.usuarioSearch = new StreamUsuarioSearch(usuarios);
        Usuario usuarioEncontrado = usuarioSearch.obterPorEmail(loginDto.getEmail());
        if (usuarioEncontrado != null){
            if (usuarioEncontrado.getSenha().equals(loginDto.getSenha())){
                    return new UsuarioDto(usuarioEncontrado.getIdUsuario(), usuarioEncontrado.getEmail(), usuarioEncontrado.getNome(),
                            usuarioEncontrado.getTelefone(), usuarioEncontrado.getSobre());
            }
        }
        return null;
    }

    @GetMapping
    public List<UsuarioDto> listarUsuarios(){
        List<UsuarioDto> usuariosDto = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuariosDto.add(new UsuarioDto(usuario.getIdUsuario(), usuario.getEmail(), usuario.getNome(),
                    usuario.getTelefone(), usuario.getSobre()));
        }

        return usuariosDto;
    }

    @GetMapping("/artista")
    public List<ArtistaDto> listarArtistas(){
        List<ArtistaDto> artistasDto = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            artistasDto.add(new ArtistaDto());
        }

        return artistasDto;
    }


}
