package Models;

import java.io.Serializable;

public class ServerDetails implements Serializable {
    private Integer id;
    private String nome;
    private String ip;
    private Integer portaTCP;
    private Integer portaUDP;

    public ServerDetails(Integer id, String nome, String ip, Integer portaTCP, Integer portaUDP) {
        this.id = id;
        this.nome = nome;
        this.ip = ip;
        this.portaTCP = portaTCP;
        this.portaUDP = portaUDP;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPortaTCP() {
        return portaTCP;
    }

    public Integer getPortaUDP() {
        return portaUDP;
    }

    public void setId(Integer newID) { this.id = newID; }

    public String toString(){
        return String.format("%d - %s - [%s | TCP: %d / UDP: %d]", this.id, this.nome, this.ip, this.portaTCP, this.portaUDP);
    }
}
