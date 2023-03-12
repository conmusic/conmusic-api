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
        return new ArtistaDto(artista.getIdUsuario(), artista.getNome(), artista.getEmail(), artista.getTelefone(), artista.getSobre(), artista.isAutenticado(), artista.getCpf(), artista.getCompetencias());
         
    }

    @PostMapping("/casa")
    public CasaDto cadastrarCasa(@RequestBody CriarCasaDto dto){
        Casa casa = new Casa(identity, dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getTelefone(), dto.getSobre(),
                dto.getCnpj(), dto.getQtdTomada220(), dto.getQtdTomada110(), dto.getInfraestrutura());
        this.usuarios.add(casa);
        return new CasaDto(casa.getIdUsuario(), casa.getNome(), casa.getEmail(), casa.getTelefone(), casa.getSobre(), casa.isAutenticado(), casa.getCnpj(), casa.getQtdTomada220(), casa.getQtdTomada110(), casa.getInfraestrutura());
    }

    @PostMapping("/login")
    public UsuarioDto login(@RequestBody LoginDto loginDto){
        this.usuarioSearch = new StreamUsuarioSearch(usuarios);
        Usuario usuarioEncontrado = usuarioSearch.obterPorEmail(loginDto.getEmail());
        if (usuarioEncontrado != null){
            if (usuarioEncontrado.getSenha().equals(loginDto.getSenha())){
                int index = usuarios.indexOf(usuarioEncontrado);
                usuarioEncontrado.setAutenticado(true);
                usuarios.set(index, usuarioEncontrado);
                return new UsuarioDto(usuarioEncontrado.getIdUsuario(), usuarioEncontrado.getNome(), usuarioEncontrado.getEmail(), usuarioEncontrado.getTelefone(), usuarioEncontrado.getSobre(), usuarioEncontrado.isAutenticado());
            }
        }
        return null;
    }

    @GetMapping
    public List<UsuarioDto> listarUsuarios(){
        List<UsuarioDto> usuariosDto = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuariosDto.add(new UsuarioDto(usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getSobre(), usuario.isAutenticado()));
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
    
    @GetMapping("/casa")
    public List<CasaDto> listarCasas(){
        List<CasaDto> casaDtos = new ArrayList<>();
        for (Usuario usuario: usuarios) {
            casaDtos.add(new CasaDto());
        }
        return casaDtos;
    }


}
