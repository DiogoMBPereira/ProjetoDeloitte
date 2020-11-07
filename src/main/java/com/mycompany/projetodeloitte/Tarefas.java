/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetodeloitte;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Utilizador
 */
public class Tarefas implements Serializable{
    //Atributos      ------------------------------------
    private Date dataDeInicio;
    private Date dataDeFim;
    private String titulo;
    private ArrayList<Utilizador> participanteList;
    private boolean reminder;
    
    //Construtores   ------------------------------------
    public Tarefas()
    {
        Calendar cal = Calendar.getInstance(); 
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        dataDeInicio = cal.getTime();        
        Calendar cal2 = Calendar.getInstance();  
        cal2.add(Calendar.YEAR,1);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        dataDeFim = cal2.getTime();
        titulo = "Unknown";
        participanteList = new ArrayList<>();
        reminder = false;
    }
    public Tarefas(Date dataDeInicio,Date dataDeFim, String titulo, ArrayList<Utilizador> participanteList,boolean reminder)
    {
        Date d1 = new Date();        
        this.dataDeInicio = dataDeInicio;   
        this.dataDeFim = dataDeFim;
        this.titulo = titulo;        
        this.participanteList = participanteList;
        this.reminder = reminder;
    }
       
        
    
    
    //Seletores   ------------------------------------
    public Date getDataDeInicio()
    {
        return dataDeInicio;
    }
    
     public Date getDataDeFim()
    {
        return dataDeFim;
    }
     
     public String getTitulo()
     {
         return titulo;
     }
     public boolean getReminder()
     {
         return reminder;
     }
     public ArrayList<Utilizador> getListParticipantes()
     {
         return participanteList;
     }
    
    //Moficadores     ------------------------------------
     public void setDataDeInicio(Date dataDeInicio)
     {
         this.dataDeInicio = dataDeInicio;
     }
     
     public void setDataDeFim(Date dataDeFim)
     {
         this.dataDeFim = dataDeFim;
     }
     
     public void setTitulo(String titulo)
     {
        this.titulo = titulo;
     }
     public void setReminder(boolean reminder)
     {
         this.reminder = reminder;
     }
    
     //Metodos    ------------------------------------
    
    public void lerTarefa()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH");
        System.out.println("----------");
        System.out.println(titulo);
        System.out.println("Data de Inicio:"+df.format(dataDeInicio));
        System.out.println("Data de Fim:"+df.format(dataDeFim));
        System.out.println("Participantes:");
        getParticipantes();
        if(updateReminder() == true && reminder == true)
        {
            System.out.println("Alerta: A tarefa já comecou");
        }
        System.out.println("\n");
    }
    public boolean Notificar()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date presentTime = cal.getTime();
        if(dataDeInicio.compareTo(presentTime) == 0)
        {
             if(updateReminder() == true && reminder == true)
            {
                return true;
            }
        }
        return false;
    }
    public boolean updateReminder()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date presentTime = cal.getTime();
        if(dataDeInicio.compareTo(presentTime) <= 0)
        {
            return true;
        }
        return false;
    }
     public void getParticipantes()
    {
        
         for(int i = 0; i < participanteList.size(); i++) 
        {
            System.out.println(i + " - " + participanteList.get(i).getNome());                       
        }
        
    }
     @Override
    public String toString() {
        return "dataDeInicio:" + dataDeInicio+"\ndataDeFim"+dataDeFim + "\ntitulo"+titulo+"\nparticipanteList: " + participanteList+"\nreminder"+reminder;
    }
}
