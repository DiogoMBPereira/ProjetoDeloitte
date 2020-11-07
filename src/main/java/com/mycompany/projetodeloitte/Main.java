/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetodeloitte;




import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author Utilizador
 */
public class Main {
    public static void main(String[] args) throws ParseException {
       
        gestorDeTarefas();
    }  
    
    public static GestorDeTarefas varPreDef() throws ParseException
    {
        //Utilizadores Criados -------------------------------------------
        Utilizador user1 = new Utilizador();
        Utilizador user2 = new Utilizador();
        Utilizador user3 = new Utilizador("Joao","A1232134");
        Utilizador user4 = new Utilizador("Miguel","pass1234");
        Utilizador user5 = new Utilizador("Diogo","nada12345678");
        Utilizador user6 = new Utilizador("Andre","naoSeiEscrever");
        
        //Listas de Utilizadores ----------------------------------------
        List<Utilizador> namesList = Arrays.asList(user1,user2,user3,user4);        
        ArrayList<Utilizador> utiList = new ArrayList<>();        
        utiList.addAll(namesList);   
        
        namesList = Arrays.asList(user1,user3,user5);        
        ArrayList<Utilizador> utiList2 = new ArrayList<>();
        utiList2.addAll(namesList);   
        
        //Datas --------------------------------------------------------
        //Calendars
        Calendar cal2 = Calendar.getInstance();  
        cal2.add(Calendar.DAY_OF_MONTH,5);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        
        Calendar cal = Calendar.getInstance();  
        cal.add(Calendar.YEAR,1);
        cal.set(Calendar.MONTH,10);
        cal.set(Calendar.DAY_OF_MONTH,5);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        
        Calendar cal3 = Calendar.getInstance();  
        cal3.set(Calendar.MINUTE, 0);
        cal3.set(Calendar.SECOND, 0);
        cal3.set(Calendar.MILLISECOND, 0);
        //Date format
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy HH");
        String strDate = ("05/11/2020 15");
        //Date date
        Date d1 = cal2.getTime(); 
        Date d2 = cal.getTime();
        Date d3 = sdfrmt.parse(strDate);
        Date d4 = cal3.getTime(); //Data presente
        
        //Tarefas --------------------------------------------------------
        Tarefas t1 = new Tarefas();
        Tarefas t2 = new Tarefas(d1,d2,"Limpar Casa",utiList,false);
        Tarefas t3 = new Tarefas(d3,d2,"Fazer Panquecas",utiList,true);
        Tarefas t4 = new Tarefas(d4,d2,"Lembrar Compras",utiList2,true);
        Tarefas t5 = new Tarefas(d4,d4,"Vai ser Apagado",utiList,true);//Vai ser sempre apagado
        
        //Lista de Tarefas e Utilizador Principal -----------------------
        ArrayList<Tarefas> listTar = new ArrayList<>();
        List<Tarefas> tarefasList = Arrays.asList(t1,t2,t3,t4,t5); 
        listTar.addAll(tarefasList);
        ArrayList<Utilizador> listUtil = new ArrayList<>();
        namesList = Arrays.asList(user1,user2,user3,user4,user5,user6); 
        listUtil.addAll(namesList);
        
        GestorDeTarefas gestor = new GestorDeTarefas(listTar,listUtil);
        return gestor;
    } 
    public static void gestorDeTarefas() throws ParseException
    {
        //GestorDeTarefas gestor = varPreDef();
        GestorDeTarefas gestor = new GestorDeTarefas();
        gestor = readFromFile(gestor);
        gestor.apagarTaskAtrasadas();
        Scanner scan = new Scanner(System.in);   
        int escolha = 0;
        do
        {
            menuPrincipal();
            escolha = validInt(escolha);        
            switch(escolha)
            {
                case 1:
                    System.out.println("Criação");
                    menuOpcoes();
                    escolha = validInt(escolha);    
                    switch(escolha)
                    {
                        case 1:
                            gestor.criacaoDeTarefas();                            
                        break;
                        case 2:
                            gestor.criacaoDeUtilizadores(); 
                        break;
                        default:
                            System.out.println("Erro!");
                    } 
                break;
                case 2:
                    System.out.println("Edição");
                    menuOpcoes();
                    escolha = validInt(escolha);
                     switch(escolha)
                    {
                        case 1:
                            gestor.editarTarefas();
                        break;
                        case 2:
                            gestor.editarUtilizadores();
                        break;
                        default:
                            System.out.println("Erro!");
                    }   
                break;
                case 3:
                    System.out.println("Eliminação");
                    menuOpcoes();
                    escolha = validInt(escolha);
                    switch(escolha)
                    {
                        case 1:
                            gestor.eliminarTarefas();
                        break;
                        case 2:
                            gestor.eliminarUtilizadores();
                        break;
                        default:
                            System.out.println("Erro!");
                    }   
                break;
                case 4:
                    System.out.println("Ler");
                    menuOpcoes();
                    escolha = validInt(escolha); 
                    switch(escolha)
                    {
                        case 1:
                            gestor.showListTarefas();
                        break;
                        case 2:
                            gestor.showListUtilizadores();
                        break;
                        default:
                            System.out.println("Erro!");
                    }                    
                break;
                case 5:
                    System.out.println("Login");
                    login(gestor);
                break;
                case 6:
                    System.out.println("Files");
                    System.out.println("1 - Escrever");
                    System.out.println("2 - Ler");
                    escolha = validInt(escolha); 
                    switch(escolha)
                    {
                        case 1:
                            writeToFile(gestor);
                        break;
                        case 2:
                            gestor = readFromFile(gestor);
                        break;
                        default:
                            System.out.println("Erro!");
                    } 
                break;
                case 0:
                    writeToFile(gestor);
                    System.exit(0); 
                 break;
                default:
                    System.out.println("Não Existe");
                break;
            }  
        }while(escolha != 0);
       
        scan.close();
    }
    public static void writeToFile(GestorDeTarefas gestor)
    {
        //write to file
        try{
            FileOutputStream writeData = new FileOutputStream("peopledata.ser");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(gestor);
            writeStream.flush();
            writeStream.close();
            System.out.println("Escrever Ficheiro\nSucesso!");

        }catch (IOException e) {
            System.out.println("Erro!!");
        }
    }
    
     public static GestorDeTarefas readFromFile(GestorDeTarefas gestor) throws ParseException
    {
        try{
        FileInputStream readData = new FileInputStream("peopledata.ser");
        ObjectInputStream readStream = new ObjectInputStream(readData);

        GestorDeTarefas gestor2 = (GestorDeTarefas) readStream.readObject();
        readStream.close();
        System.out.println("Ficheiro Carregado");
        return gestor = gestor2;
        }catch (IOException | ClassNotFoundException e) {            
            System.out.println("Não existe ficheiro - Carregar Variaveis Locais");
            return gestor = varPreDef();
        }
        
    }
    public static void login(GestorDeTarefas gestor)
    {
            Scanner scan = new Scanner(System.in);
            String nome;
            String pass;
            do
            {
                System.out.println("Name: ");
                nome = scan.next(); 
                System.out.println("Pass: ");
                pass = scan.next(); 
            }while(!gestor.utilExist(nome, pass));
            Utilizador temp = gestor.getIdUtil(nome, pass);
            gestor.getTdsTarfsDoUti(temp);
    }
    public static int validInt(int isInt)
    {
        Scanner scan = new Scanner(System.in); 
        boolean seForInt = false;  
        do{ 
            if(scan.hasNextInt())
            {                        
                seForInt = true;
                isInt = scan.nextInt();
            }
            else
            {
                System.out.println("Apenas pode inserir numeros");
                scan.next();
            }
        }while(seForInt == false);
        return isInt;
    }
    public static int intPositiv(int posInt)
   {
       do
       {
           posInt = validInt(posInt);
           if(posInt < 0)
           {
               System.out.println("Não pode ser negativo");
           }     
       }while(posInt <= 0);   
       return posInt;
       
   }
    public static void menuPrincipal()
    {
        String texto = "";
        texto += "Gestor de Trefas   \n";
        texto += "1 - Criação        \n";
        texto += "2 - Edição         \n";
        texto += "3 - Eliminação     \n";
        texto += "4 - Ler            \n";
        texto += "5 - Login          \n";
        texto += "6 - Files          \n";
        texto += "0 - Sair           \n";
        texto += "--------------     \n";
        System.out.println(texto);
    }
    public static void menuOpcoes()
    {
        String texto = "";
        texto += "1 - Tarefas        \n";
        texto += "2 - Utilizadores   \n";
        texto += "--------------     \n";
        System.out.println(texto);
    }
    
}
