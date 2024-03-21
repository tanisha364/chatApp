import java.io.*;
import java.net.*;

class Client{

Socket socket;

BufferedReader br;
PrintWriter out;


public Client(){
      try{
        System.out.println("Sending request........");
        socket = new Socket("192.168.200.201",7778);
       System.out.println("connection done.");

       br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       out= new PrintWriter(socket.getOutputStream());

       startReading();
       startWriting();
        }catch(Exception e){
            e.printStackTrace();
        }
}

public void startReading()
    {
        Runnable r1=()->{
           System.out.println("reader started........");
           try{
           while(true){

            
            String msg=br.readLine();
            if(msg.equals("exit")){
                System.out.println("chat terminate by server");
                socket.close();
                break;
            }
            System.out.println("Server:" +msg);
        }
    }
        catch(Exception e){
           // e.printStackTrace();
           System.out.println("connection is closed");
        }

        };

        new Thread(r1).start();
    }

    public void startWriting()
    {
        Runnable r2=()->{
            System.out.println("writer started........");
            try{
                while(!socket.isClosed()){
 
            

                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                   String content=br1.readLine();
             
             out.println(content);
             out.flush();
             if(content.equals("exit")){
                socket.close();
                break;
            }
         }
        }
         catch(Exception e){
             //e.printStackTrace();
             System.out.println("connection is closed");
         }
 
         };
         new Thread(r2).start();
    }

    public static void main(String args[]){
        System.out.println("Client");
        new Client();

    }
}