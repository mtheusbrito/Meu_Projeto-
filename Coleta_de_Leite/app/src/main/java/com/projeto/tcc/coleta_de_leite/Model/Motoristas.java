package com.projeto.tcc.coleta_de_leite.Model;

/**
 * Created by raphael on 05/05/17.
 */

public class Motoristas {
    String id_motorista;
    String emailmotorista;
     String senha;

    public Motoristas (){}

    public Motoristas(String id_motorista , String emailmotorista,String senha){
        this.id_motorista=id_motorista;
        this.emailmotorista=emailmotorista;
        this.senha=senha;


    }
    public String getId_motorista() {return id_motorista;}

    public String getEmailmotorista() {
        return emailmotorista;
    }

    public void setEmailmotorista(String emailmotorista) {
        this.emailmotorista = emailmotorista;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
