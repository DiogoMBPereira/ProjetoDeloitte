/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetodeloitte;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TimeZone;

/**
 *
 * @author Utilizador
 */
public class GestorDeTarefas implements Serializable {
    //Atributos       ------------------------------------
    private  ArrayList<Tarefas> tarList;
    private  ArrayList<Utilizador> utiList;
    
    //Construtores  ------------------------------------
    public GestorDeTarefas()
    {
        this.tarList = new ArrayList<>();
        this.utiList = new ArrayList<>();
    }
    public GestorDeTarefas(ArrayList<Tarefas> tarList,ArrayList<Utilizador> utiList)
    {
        this.tarList = tarList;        
        this.utiList = utiList;
    }
    //Seletores
    public ArrayList<Tarefas> getTarList()
    {
        return tarList;
    }
    
    public ArrayList<Utilizador> getUitList()
    {
        return utiList;
    }
    //Metodos    --------------------------------------
    public void showListTarefas()
    {
        apagarTaskAtrasadas();
        //Mostra todos os elementos
        System.out.println("Lista de Tarefas");       
         for(int i = 0; i < tarList.size(); i++) 
        {
            
            System.out.print(i + " ");
            tarList.get(i).lerTarefa();                       
        }
        System.out.println("");  
    }
    public void showListTarefasSimplified()
    {
        apagarTaskAtrasadas();
        //Apenas mostra o titulo das tarefas em vez dos seus elementos
        //Usar showListTarefas para ver elementos
        System.out.println("Lista de Tarefas");       
         for(int i = 0; i < tarList.size(); i++) 
        {
            
            System.out.print(i + " - ");
            System.out.println(tarList.get(i).getTitulo() + " \n");                       
        }
        System.out.println("");  
    }
    
    public void showListUtilizadores()
    {
        //Mostra todos os elementos
        System.out.println("Lista de Utilizadores");
         for(int i = 0; i < utiList.size(); i++) 
        {
            
            System.out.println(i + " - " + utiList.get(i).getNome());                       
        }
        System.out.println("");  
    }
    public void criacaoDeUtilizadores()
    {        
        Scanner scan = new Scanner(System.in); 
        int contador=0;
        int total = -1;
        System.out.println("Criação de Utilizador");
        System.out.println("Quantos Utilizadores quer Criar?");
        total = intPositiv(total);//validar int
        String nome = " ";
        do
        {            
            
            nome = validrNome(nome);
            System.out.println("Pass: ");
            String pass = scan.next();   
            Utilizador temp = new Utilizador(nome,pass);
            utiList.add(temp);
            contador++;
        }while(contador < total);
        System.out.println("");  
    }
    public String validrNome(String nome)
    {
        Scanner scan = new Scanner(System.in); 
        int contador;
        boolean existe;
        do 
        {
            System.out.println("Nome: (sem espaços)");
            nome = scan.next();
            contador = 0;
            existe = false;
            do{
                if(nome.toLowerCase().equals(utiList.get(contador).getNome().toLowerCase()))
                {
                    System.out.println("Esse nome já existe");
                    existe = true;
                }
                contador++;
            }while(contador < utiList.size());
        }while(existe == true);
        return nome;
        
    }
    public void criacaoDeTarefas() throws ParseException 
    {
        //Variaveis
        Scanner scan = new Scanner(System.in); 
        boolean contador = false;
        Date d1Temp = new Date();
        Date d2Temp = new Date();
        //Titulo de Pagina
        System.out.println("Criação de Tarefa");
        //Data de Inicio
        System.out.println("Data de Inicio(dd/MM/yyyy hh): ");
        d1Temp = compareTime(d1Temp);//validar data
        //Data De Fim
        do
        {
            System.out.println("Data de Fim(dd/MM/yyyy):");
            d2Temp = compareTime(d2Temp);//validar data
        }while(d2Temp.compareTo(d1Temp) <= 0);//Obriga a DataDeFim a ser maior que a da DataDeInicio
        //Titulo da Tarefa
        System.out.println("Titulo: ");
        String titulo = scan.nextLine();
        
        //Lista de Participantes
        showListUtilizadores();
        int total = -1;
        int user = -1;
        System.out.println("Quantos Utilizadores quer Adicionar?");
        total = intPositiv(total);//validar int
        
        HashSet<Utilizador> hset = new HashSet<>();
        do
        {
            do
            {
                System.out.println("Utilizador a Adicionar\n(Selecione usando os numeros)");
                user = validInt(user);//validar int

            }while(validRspsta(user,utiList.size()) == false);  
            hset.add(utiList.get(user));//guardar no hashset para não haver duplicados
            if(hset.size() == total)
            {
                contador=true;//so é true quando o hset tiver o mesmo tamnaho que o que foi pedido
            }
            
        }while(!contador);//quando o hashset tiver o mesmo tamanho que o numero de Utilizadores a adicioanr para
        ArrayList<Utilizador> participanteList = new ArrayList<>(hset);//guardar hasset num ArrayList
        
        boolean reminderTemp = false;
        char escolha = ' ';
        escolha = charAnswer(escolha);
        if(Character.toLowerCase(escolha) == 's')
        {
            reminderTemp = true;
        }
        //Tarefa Temporaria para adição na Lista
        Tarefas temp = new Tarefas(d1Temp,d2Temp,titulo,participanteList,reminderTemp);
        tarList.add(temp);
    }
    public void editarTarefas() throws ParseException
    {
        Scanner scan = new Scanner(System.in); 
        System.out.println("Edição de Tarefas");
        if(tarList.isEmpty())//Verifica se a Lista está vazia
        {
            System.out.println("Não Existem Tarefas");
        }
        else
        {
            showListTarefas();//Mostra Lista de Tarefas
            int user = -1;
            int tarPosicao = -1;
            //Seleção de Tarefa ---------------------------------------------
            do{                     
                System.out.println("Qual a Tarefa que quer editar?");
                tarPosicao = validInt(tarPosicao);  //validar int
                
             }while(validRspsta(tarPosicao,tarList.size()) == false); 
            //Edição em si --------------------------------------------------
            String texto = "Edição de:\n";
            texto += "1 - Datas        \n";
            texto += "2 - Titulo   \n";
            texto += "3 - Participantes   \n";
            texto += "4 - Reminder   \n";
            texto += "--------------     \n";
            System.out.println(texto);
            int opcao = -1;
            opcao = validInt(opcao);
            Date d1Temp = tarList.get(tarPosicao).getDataDeInicio();
            Date d2Temp = tarList.get(tarPosicao).getDataDeFim();
            String titulo = tarList.get(tarPosicao).getTitulo();
            ArrayList<Utilizador> participanteList = tarList.get(tarPosicao).getListParticipantes();
            boolean reminderTemp = tarList.get(tarPosicao).getReminder();
            switch (opcao){
                case 1:
                        //Data de Inicio  
                        System.out.println("Data de Inicio(dd/MM/yyyy hh): ");
                        d1Temp = compareTime(d1Temp);//validar Data
                        //Data De Fim
                        do
                        {
                            System.out.println("Data de Fim(dd/MM/yyyy):");
                            d2Temp = compareTime(d2Temp);//validar Data
                        }while(d2Temp.compareTo(d1Temp) <= 0);//Obriga a DataDeFim a ser maior que a da DataDeInicio
                    break;
                case 2:
                        //Titulo da Tarefa
                       System.out.println("Titulo: ");
                       titulo = scan.nextLine();
                    break;
                case 3:
                        boolean contador = false;
                        //Lista de Participantes
                        showListUtilizadores();
                        int total = -1;
                        System.out.println("Quantos Utilizadores quer Adicionar?");
                        total = intPositiv(total);//validar int
                        HashSet<Utilizador> hset = new HashSet<>();
                        do
                        {
                            do
                            {
                                System.out.println("Utilizador a Adicionar\n(Selecione usando os numeros)");
                                user = validInt(user);//validar int

                            }while(validRspsta(user,utiList.size()) == false);  
                            hset.add(utiList.get(user));//guardar no hashset para não haver duplicados
                            if(hset.size() == total)
                            {
                                contador=true;//so é true quando o hset tiver o mesmo tamnaho que o que foi pedido
                            }

                        }while(!contador);//quando o hashset tiver o mesmo tamanho que o numero de Utilizadores a adicioanr para
                        participanteList = new ArrayList<>(hset);//guardar hasset num ArrayList
                    break;
                case 4:
                    char escolha = ' ';
                    escolha = charAnswer(escolha);
                    if(Character.toLowerCase(escolha) == 's')
                    {
                        reminderTemp = true;
                    }
                    else
                    {
                        reminderTemp = false;
                    }
                    break;
                default:
            }
           
            //Tarefa Temporaria para adição na Lista
            Tarefas temp = new Tarefas(d1Temp,d2Temp,titulo,participanteList,reminderTemp);
            temp.lerTarefa();
            tarList.set(tarPosicao,temp);//Faz Update na Lista
        }
    }    
    public void editarUtilizadores()
    {
        Scanner scan = new Scanner(System.in); 
        System.out.println("Edição de Utilizador");
        if(utiList.isEmpty())//Verifica se a Lista está vazia
        {
            System.out.println("Não Existem Utilizadores");
        }
        else
        {
            showListUtilizadores();//Mostra Lista de Utilizadores
            int user = -1;
            //Seleção de Utilizador ---------------------------------------------
            do{                     
                    System.out.println("Qual o Utilizador que quer editar?");
                    user = validInt(user);  
                
             }while(validRspsta(user,utiList.size()) == false); 
            //Edição em si --------------------------------------------------
            String nome = " ";        
            nome = validrNome(nome);
            System.out.println("Pass: ");
            String pass = scan.next();   
            Utilizador temp = new Utilizador(nome,pass);
            updateUtilizadorNasTarefas(utiList.get(user),temp);
            utiList.set(user,temp);//Faz Update na Lista
        }
    }
    public void updateUtilizadorNasTarefas(Utilizador queVaiMudar,Utilizador temp)
    {
        for(int i = 0; i < tarList.size(); i++)
        {
            
            for(int j = 0; j < tarList.get(i).getListParticipantes().size(); j++)
            {
               if(queVaiMudar == tarList.get(i).getListParticipantes().get(j))
               {
                   tarList.get(i).getListParticipantes().set(j,temp);
               }
            }
        }
    }   
    public void eliminarTarefas()
    {
        if(tarList.isEmpty())//Verifica se a Lista está vazia
        {
            System.out.println("Não Existem Tarefas");
        }
        else
        {
            showListTarefasSimplified();//Mostra Lista de Tarefas
            int user = -1;
            do{  
                System.out.println("Qual a Tarefa quer Eliminar");
                user = validInt(user);    
             }while(validRspsta(user,tarList.size()) == false); 
             tarList.remove(user);//Remove da Lista
        }
           
    }   
    public void eliminarUtilizadores()
    {        
        if(utiList.isEmpty())//Verifica se a Lista está vazia
        {
            System.out.println("Não Existem Utilizadores");
        }
        else
        {
            showListUtilizadores();//Mostra Lista de Utilizadores
            int user = -1;
            do{                     
                    System.out.println("Qual o Utilizador que quer Eliminar");
                    user = validInt(user);  
                
             }while(validRspsta(user,utiList.size()) == false); 
             eliminarUtilizadorNasTarefas(utiList.get(user));
             utiList.remove(user);//Remove da Lista
        }
    }
    public void eliminarUtilizadorNasTarefas(Utilizador queVaiMudar)
    {
        for(int i = 0; i < tarList.size(); i++)
        {
            
            for(int j = 0; j < tarList.get(i).getListParticipantes().size(); j++)
            {
               if(queVaiMudar == tarList.get(i).getListParticipantes().get(j))
               {
                   tarList.get(i).getListParticipantes().remove(j);
               }
            }
        }
    }  
    public boolean validRspsta(int user, int sizeOfList)
    {
        return !(user >= sizeOfList || user < 0);
    }
     public int validInt(int isInt)
    {
        //Verifica so o User inseriu uma Int valida
        Scanner scan = new Scanner(System.in); 
        boolean seForInt = false;  
        do{ 
            if(scan.hasNextInt())
            {                        
                seForInt = true;
                isInt = scan.nextInt();
            } //Se não inseriu irá receber a mensagem abaixo
            else
            {               
                System.out.println("Apenas pode inserir numeros");
                scan.next();
            }
        }while(seForInt == false);
        return isInt;
    }
    public int intPositiv(int posInt)
   {
       //Verifica se o User usou um int Negativo
       do
       {
           posInt = validInt(posInt);
           if(posInt < 0)
           {//Se sim msg de Erro
               System.out.println("Não pode ser negativo");
           }     
       }while(posInt <= 0);   
       return posInt;
       
   }
    public char charAnswer(char escolha)
    {
        Scanner scan = new Scanner(System.in); 
            do
            {
                System.out.println("Quer adicionar um reminder?(s/n)");
                escolha =  scan.next().charAt(0);
            }while(Character.toLowerCase(escolha) != 's' && Character.toLowerCase(escolha) != 'n');
            return escolha;
    }
    public Date compareTime(Date d1Temp) throws ParseException
    {
        Scanner scan = new Scanner(System.in); 
        SimpleDateFormat isoFormat = new SimpleDateFormat("dd/MM/yyyy HH");
        isoFormat.setTimeZone(TimeZone.getTimeZone("WET"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);//tem quer se guardar a data com tudo a 0 menos as horas
        //Date presentTime = new Date();
        Date presentTime = cal.getTime();
        String sD1= "";
        do
        {
            do{
                sD1 = scan.nextLine(); 
            }while(!validateJavaDate(sD1));
            
            d1Temp = isoFormat.parse(sD1); //guarda a data no seu formato correcto
            if(d1Temp.compareTo(presentTime) < 0)
            {
                System.out.println("A data não pode ser antes da data Atual: ");
            }
        }while(d1Temp.compareTo(presentTime) < 0);
        return d1Temp;
    }
    public static boolean validateJavaDate(String strDate)
   {
	//Verifica se a data está Vazia
	if (strDate.trim().equals(""))
	{
            System.out.println("Data não pode estar vazia");
	    return false;
	}
	else
	{
	    //Formata da Data
	    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy HH");
	    sdfrmt.setLenient(false);
	    try
	    {
	        Date javaDate = sdfrmt.parse(strDate); //Objecto tipo data com parse
	    }
	    //Formato da data é Invalido
	    catch (ParseException e)
	    {
	        System.out.println(strDate+" não é uma data valida");
	        return false;
	    }
	    //Se o formato estiver correcto retorna true
	    return true;
	}
   }
    public boolean utilExist(String nome,String pass)
    {
        //Verifica se o Utilizador exite
        int contador = 0;
        do{
            if(nome.toLowerCase().equals(utiList.get(contador).getNome().toLowerCase()))//se o nome existe
            {
                if(pass.equals(utiList.get(contador).getPass()))//se a password é a do Utilizador
                {
                    System.out.println("Sucesso!!");
                    return true;
                }
                else
                {
                    System.out.println("Password Incorrecta");//Apareçe se inserir a pass errada
                    return false;
                }
            }
            contador++;
       }while(contador < utiList.size());
        System.out.println("Nome e Password Incorrectos");//Apareçe se inserir tanto o nome como a pass errada
        return false;
    }
    public Utilizador getIdUtil(String nome, String pass)
    {
        Utilizador temp = new Utilizador();
        int contador = 0;
        do{
            if(nome.toLowerCase().equals(utiList.get(contador).getNome().toLowerCase()))
            {
                if(pass.equals(utiList.get(contador).getPass()))
                {
                    temp = utiList.get(contador);
                    return temp;//devolve o Utilizador que esta correto
                }
            }
            contador++;
       }while(contador < utiList.size());
       return temp;
    }
    public void getTdsTarfsDoUti(Utilizador temp)
    {
        
        System.out.println("Tarefas do Utilizador: " +temp.getNome()+ "\n");
        for(int i = 0; i < tarList.size(); i++)
        {
            
            for(int j = 0; j < tarList.get(i).getListParticipantes().size(); j++)
            {
               if(temp.getNome().equals(tarList.get(i).getListParticipantes().get(j).getNome()))
               {
                   System.out.print(tarList.get(i).getTitulo());
                   if(tarList.get(i).Notificar())
                   {
                       System.out.print(" - Tarefa comeca agora");
                   }
                   System.out.println(" ");
               }
            }
        }
    }
    public void apagarTaskAtrasadas()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);//tem quer se guardar a data com tudo a 0 menos as horas        
        Date presentTime = cal.getTime();
        for (int i = 0; i < tarList.size();i++)
        {
            if(tarList.get(i).getDataDeFim().compareTo(presentTime) == 0)
            {
                System.out.println("("+tarList.get(i).getTitulo() + ") foi apagada por estar em atraso");
                tarList.remove(i);//Remove da Lista
            }
        }
        
    }
    @Override
    public String toString() {
        return "ListaTarefas:" + tarList + "\nListaUtilizadores: " + utiList;
    }
    
    
   
}
    
