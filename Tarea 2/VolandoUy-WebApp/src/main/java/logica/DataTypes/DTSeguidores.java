package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTSeguidores", propOrder = {
    "nicknameSeguidor",
    "nicknameSeguido"
})
public class DTSeguidores {
    private String nicknameSeguidor;
    private String nicknameSeguido;

    // Constructor sin argumentos requerido por JAXB
    public DTSeguidores() {
    }

    public DTSeguidores(String nicknameSeguidor, String nicknameSeguido){
        this.nicknameSeguidor = nicknameSeguidor;
        this.nicknameSeguido = nicknameSeguido;
    }

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
