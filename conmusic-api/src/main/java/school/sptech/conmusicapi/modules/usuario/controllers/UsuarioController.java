package school.sptech.conmusicapi.modules.usuario.controllers;

import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.usuario.dtos.*;
import school.sptech.conmusicapi.modules.usuario.entities.Artista;
import school.sptech.conmusicapi.modules.usuario.entities.Casa;
import school.sptech.conmusicapi.modules.usuario.entities.Usuario;
import school.sptech.conmusicapi.modules.usuario.helpers.ForUsuarioSearch;
import school.sptech.conmusicapi.modules.usuario.helpers.StreamUsuarioSearch;
import school.sptech.conmusicapi.modules.usuario.helpers.UsuarioSearch;

import java.util.ArrayList;
import java.util.List;
@RestController
public class UsuarioController {

    private List<Usuario> usuarios;

    private int identity;

    private String searchMode;

    private UsuarioSearch usuarioSearch;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
        this.searchMode = "For";
    }

    @PostMapping("/artistas")
    public ArtistaDto cadastrarArtista(@RequestBody CriarArtistaDto dto){
        identity++;
        Artista artista = new Artista(
                identity,
                dto.getNome(),
                dto.getEmail(),
                dto.getSenha(),
                dto.getTelefone(),
                dto.getSobre(),
                dto.getCpf(),
                dto.getCompetencias()
        );

        this.usuarios.add(artista);

        return new ArtistaDto(
                artista.getIdUsuario(),
                artista.getNome(),
                artista.getEmail(),
                artista.getTelefone(),
                artista.getSobre(),
                artista.isAutenticado(),
                artista.getCpf(),
                artista.getCompetencias()
        );
         
    }

    @PostMapping("/casas")
    public CasaDto cadastrarCasa(@RequestBody CriarCasaDto dto){
        identity++;
        Casa casa = new Casa(
                identity,
                dto.getNome(),
                dto.getEmail(),
                dto.getSenha(),
                dto.getTelefone(),
                dto.getSobre(),
                dto.getCnpj(),
                dto.getQtdTomada220(),
                dto.getQtdTomada110(),
                dto.getInfraestrutura()
        );

        this.usuarios.add(casa);

        return new CasaDto(
                casa.getIdUsuario(),
                casa.getNome(),
                casa.getEmail(),
                casa.getTelefone(),
                casa.getSobre(),
                casa.isAutenticado(),
                casa.getCnpj(),
                casa.getQtdTomada220(),
                casa.getQtdTomada110(),
                casa.getInfraestrutura()
        );
    }

    @PostMapping("/usuarios/autenticacao")
    public UsuarioDto login(@RequestBody LoginDto loginDto){
        this.usuarioSearch = getUsuarioSearch();

        Usuario usuarioEncontrado = usuarioSearch.obterPorEmail(loginDto.getEmail());

        if (usuarioEncontrado != null){
            if (usuarioEncontrado.getSenha().equals(loginDto.getSenha())){
                int index = usuarios.indexOf(usuarioEncontrado);
                usuarioEncontrado.setAutenticado(true);

                usuarios.set(index, usuarioEncontrado);

                UsuarioDto dtoResposta = null;

                if (usuarioEncontrado instanceof Artista a) {
                    dtoResposta = new ArtistaDto(
                            a.getIdUsuario(),
                            a.getNome(),
                            a.getEmail(),
                            a.getTelefone(),
                            a.getSobre(),
                            a.isAutenticado(),
                            a.getCpf(),
                            a.getCompetencias()
                    );
                } else if (usuarioEncontrado instanceof Casa c) {
                    dtoResposta = new CasaDto(
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
                    );
                }

                return dtoResposta;
            }
        }
        return null;
    }

    @DeleteMapping("/usuarios/autenticacao/{id}")
    public String logoff(@PathVariable int id){
        this.usuarioSearch = getUsuarioSearch();

        Usuario usuarioEncontrado = usuarioSearch.obterPorId(id);

        if (usuarioEncontrado != null) {
            if (usuarioEncontrado.isAutenticado()) {
                int index = usuarios.indexOf(usuarioEncontrado);
                usuarioEncontrado.setAutenticado(false);

                usuarios.set(index, usuarioEncontrado);

                return "Logoff feito com sucesso!";
            }

            return "Usuário não está logado no momento";
        }

        return "Usuário não encontrado";
    }

    @GetMapping("/usuarios")
    public List<UsuarioDto> listarUsuarios(){
        this.usuarioSearch = getUsuarioSearch();

        return usuarioSearch.listarUsuariosComoDto();
    }

    @GetMapping("/artistas")
    public List<ArtistaDto> listarArtistas(){
        this.usuarioSearch = getUsuarioSearch();

        return usuarioSearch.listarArtistasComoDto();
    }
    
    @GetMapping("/casas")
    public List<CasaDto> listarCasas(){
        this.usuarioSearch = getUsuarioSearch();

        return usuarioSearch.listarCasasComoDto();
    }

    @PatchMapping("/usuarios/configuracoes")
    public String atualizarConfiguracoes() {
        String opcao = searchMode.equals("For") ? "Stream" : "For";

        searchMode = opcao;

        return String.format("Opção de pesquisa de usuários atualizada para: %s", opcao);
    }

    private UsuarioSearch getUsuarioSearch() {
        if (searchMode.equals("For")) {
            return new ForUsuarioSearch(usuarios);
        }

        return new StreamUsuarioSearch(usuarios);
    }
}
