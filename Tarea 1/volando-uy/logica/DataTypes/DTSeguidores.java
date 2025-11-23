package logica.DataTypes;

public class DTSeguidores {
    private String nicknameSeguidor;
    private String nicknameSeguido;


    public DTSeguidores(String nicknameSeguidor, String nicknameSeguido){
        this.nicknameSeguidor = nicknameSeguidor;
        this.nicknameSeguido = nicknameSeguido;
    };

    public String getNicknameSeguido() {
        return nicknameSeguido;
    }

    public void setNicknameSeguido(String nicknameSeguido) {
        this.nicknameSeguido = nicknameSeguido;
    }

    public String getNicknameSeguidor() {
        return nicknameSeguidor;
    }

    public void setNicknameSeguidor(String nicknameSeguidor) {
        this.nicknameSeguidor = nicknameSeguidor;
    }
}
