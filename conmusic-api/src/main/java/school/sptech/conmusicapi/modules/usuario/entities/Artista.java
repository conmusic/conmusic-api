package school.sptech.conmusicapi.modules.usuario.entities;

import school.sptech.conmusicapi.modules.usuario.entities.Usuario;

public class Artista extends Usuario {

    private String cpf;

    private String competencias;



    public Artista(int idUsuario, String nome, String email, String senha, String telefone, String sobre, String cpf, String competencias) {
        super(idUsuario, nome, email, senha, telefone, sobre);
        this.cpf = cpf;
        this.competencias = competencias;

    }

    public Artista() {
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
