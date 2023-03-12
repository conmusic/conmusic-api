package school.sptech.conmusicapi.modules.usuario.dtos;

public class UsuarioDto {

    private int idUsuario;

    private String nome;

    private String email;

    private String telefone;

    private String sobre;

    private boolean autenticado;

    public UsuarioDto() {
    }

    public UsuarioDto(int idUsuario, String nome, String email, String telefone, String sobre, boolean autenticado) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.sobre = sobre;
        this.autenticado = autenticado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }
}
