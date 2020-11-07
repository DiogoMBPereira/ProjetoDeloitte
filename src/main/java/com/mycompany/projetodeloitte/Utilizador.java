/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetodeloitte;

import java.io.Serializable;

/**
 *
 * @author Utilizador
 */
public class Utilizador implements Serializable{
    //Atributos       ------------------------------------
    private String nome;
    private String pass;
    private static int cntdrUtiliz;
    
    //Construtores    ------------------------------------
    public Utilizador()
    {   
        cntdrUtiliz++;
        this.nome = "user"+cntdrUtiliz;
        this.pass = "12345678";
    }
     public Utilizador(String nome, String pass)
    {
        this.nome = nome;
        this.pass = pass;
    }
     
    //Seletores   ------------------------------------
     public String getNome()
     {
         return nome;
     }
     
     public String getPass()
     {
         return pass;
     }
    //Modificadores   ------------------------------------
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public void setPass(String pass)
    {
        this.pass = pass;
    }
    @Override
    public String toString() {
        return "nome:" + nome + "\npass: " + pass;
    }
}
