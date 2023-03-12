package school.sptech.conmusicapi.modules.usuario.dtos;

public class ArtistaDto extends UsuarioDto{

    private String cpf;

    private String competencias;

    private Boolean ativo;

    public ArtistaDto() {
    }

    public ArtistaDto(int idUsuario, String nome, String email, String telefone, String sobre, String cpf, String competencias, Boolean ativo) {
        super(idUsuario, nome, email, telefone, sobre);
        this.cpf = cpf;
        this.competencias = competencias;
        this.ativo = ativo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCompetencias() {
        return competencias;
    }

    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
