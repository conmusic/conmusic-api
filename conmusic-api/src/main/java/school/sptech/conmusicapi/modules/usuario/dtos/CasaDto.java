package school.sptech.conmusicapi.modules.usuario.dtos;

public class CasaDto extends UsuarioDto{

    private String cnpj;

    private int qtdTomada220;

    private int qtdTomada110;

    private String infraestrutura;

    public CasaDto() {
    }

    public CasaDto(int idUsuario, String nome, String email, String telefone, String sobre, boolean autenticado, String cnpj, int qtdTomada220, int qtdTomada110, String infraestrutura) {
        super(idUsuario, nome, email, telefone, sobre, autenticado);
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
