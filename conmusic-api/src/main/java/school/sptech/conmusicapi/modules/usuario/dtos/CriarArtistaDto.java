package school.sptech.conmusicapi.modules.usuario.dtos;

public class CriarArtistaDto extends CriarUsuarioDto{

    private String cpf;

    private String competencias;

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
