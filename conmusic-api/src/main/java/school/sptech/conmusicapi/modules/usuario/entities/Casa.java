package school.sptech.conmusicapi.modules.usuario.entities;

import school.sptech.conmusicapi.modules.usuario.entities.Usuario;

public class Casa extends Usuario {
    private String cnpj;

    private int qtdTomada220;

    private int qtdTomada110;

    private String infraestrutura;

    public Casa() {
    }

    public Casa(int idUsuario, String nome, String email, String senha, String telefone, String sobre, String cnpj, int qtdTomada220, int qtdTomada110, String infraestrutura) {
        super(idUsuario, nome, email, senha, telefone, sobre);
        this.cnpj = cnpj;
        this.qtdTomada220 = qtdTomada220;
        this.qtdTomada110 = qtdTomada110;
        this.infraestrutura = infraestrutura;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getQtdTomada220() {
        return qtdTomada220;
    }

    public void setQtdTomada220(int qtdTomada220) {
        this.qtdTomada220 = qtdTomada220;
    }

    public int getQtdTomada110() {
        return qtdTomada110;
    }

    public void setQtdTomada110(int qtdTomada110) {
        this.qtdTomada110 = qtdTomada110;
    }

    public String getInfraestrutura() {
        return infraestrutura;
    }

    public void setInfraestrutura(String infraestrutura) {
        this.infraestrutura = infraestrutura;
    }
}
