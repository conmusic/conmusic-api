package school.sptech.conmusicapi.modules.usuario.dtos;

public class ArtistaDto extends UsuarioDto{

    private String cpf;

    private String competencias;


    public ArtistaDto() {
    }

    public ArtistaDto(int idUsuario, String nome, String email, String telefone, String sobre, boolean autenticado, String cpf, String competencias) {
        super(idUsuario, nome, email, telefone, sobre, autenticado);
        this.cpf = cpf;
        this.competencias = competencias;
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

}
