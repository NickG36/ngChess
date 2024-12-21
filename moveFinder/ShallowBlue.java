
package moveFinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.SecurityManager;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class ShallowBlue {

  final static String host = "localhost";
  static int TxPortNumber = 12000;
  static int RxPortNumber = 12001;

  public static void main(String[] args) throws java.io.IOException
  { 
      boolean I_Am_Black = false;
    
      //System.out.println("Usage : args[0]: Tx port, args[1]: Rx port, args[2]: 'white' or blank for black");            
      System.out.println("Usage : args[0]: 'white' or blank for black");            

      //if(args.length < 2)
      //  return;
 
      //TxPortNumber = Integer.parseInt(args[0]);
      //System.out.println("TxPortNumber:" + TxPortNumber);
       
      //RxPortNumber = Integer.parseInt(args[1]);
      //System.out.println("RxPortNumber:" + RxPortNumber);

      if( (args.length > 0) && 
           (args[0].equals("white") ) )
       {
System.out.println("Playing as white");
         I_Am_Black = false;
       }
       else
       {
System.out.println("Playing as black");
         I_Am_Black = true;
       } 
//      
System.out.println("In ShallowBlue main");

     //System.out.println("Will create sockets to host : " + host + ", Tx port " + TxPortNumber +
     //                                                  ", Rx port " + RxPortNumber);
//     boolean connectionMade = false;
     
System.out.println("Using host:" + host);

       //DatagramSocket receiving_dsocket = new DatagramSocket(RxPortNumber);
       //DatagramSocket sending_dsocket = new DatagramSocket();
           
       //InetAddress board_address = performHandshake(sending_dsocket, receiving_dsocket, I_Am_Black, TxPortNumber);
           
       //System.out.println("Have left performHandshake");

       Board theBoard = new Board();
       BoardRater boardRater = new BoardRater(); // New bit; ctor has side-effects
       MovesRecord gameMoves = new MovesRecord(true);
       MoveFinder moveFinder = new MoveFinder(theBoard);

       System.out.println("Start:" + theBoard);
       Controller controller = new Controller(theBoard,
                                              moveFinder,
                                              gameMoves,
                                              //sending_dsocket, receiving_dsocket,
                                              board_address,
                                              TxPortNumber);
     
       controller.Go(I_Am_Black);
System.out.println("Exiting ShallowBlue.");
  }
  
  

  static InetAddress performHandshake(DatagramSocket sending_dsocket,
                                      DatagramSocket receiving_dsocket,
                                      boolean I_Am_Black,
                                      int TxPortNumber)
  {
    System.out.println("In performHandshake.");

    boolean connectionMade = false;
    byte[] buf = new byte[1000];
    
    System.out.println("Creating DatagramPacket");
    DatagramPacket dgp = new DatagramPacket(buf, buf.length);

    System.out.println("Waiting for <Player_Connect<EOF> msg");
    while(!connectionMade)
    {
        try
        {
          receiving_dsocket.receive(dgp);
        }
        catch(IOException exc)
        {
           System.out.println("Error raised receiving msg:" + exc);  
        }
        
        String dgp_str = new String(dgp.getData() );
        String rcvd = new String(dgp.getData(), 0, dgp.getLength()) + ", from address: " +
                                                                         dgp.getAddress();
//              dgp.getAddress() + ", port: " + dgp.getPort();
        System.out.println("Received:" + rcvd);
          
        if(dgp_str.startsWith("Player_") || 
           dgp_str.startsWith("player_") )
        {
            connectionMade = true;
            System.out.println("Player_Connect message correctly received.");
        }
        else
        {
            System.out.println("Unrecognised message.");
        }
    }
      
    String response = "connect<EOF>";
    buf = response.getBytes();
    System.out.println("Sending 'connect' message:" + response);
    DatagramPacket out = new DatagramPacket(buf, buf.length, dgp.getAddress(), TxPortNumber);
    try
    {
       sending_dsocket.send(out);
    }
    catch(IOException exc)
    {
       System.out.println("Error raised sending msg:" + exc);  
    }
    
    // If we are playing as black then wait for a 'Game_Start' message
    /* Added to board in V2.8 */
    
    boolean game_start = false;
    
    if(I_Am_Black)
    {
        System.out.println("Waiting for 'Game_Start<EOF>' msg");
        while(!game_start)
        {
            try
            {
              receiving_dsocket.receive(dgp);
            }
            catch(IOException exc)
            {
               System.out.println("Error raised receiving msg:" + exc);  
            }
        
            String dgp_str = new String(dgp.getData() );
            String rcvd = new String(dgp.getData(), 0, dgp.getLength()) + ", from address: " +
                  dgp.getAddress() + ", port: " + dgp.getPort();
            System.out.println("Received:" + rcvd);
              
            if(dgp_str.startsWith("Game_") || 
               dgp_str.startsWith("game_") )
            {
                game_start = true;
                System.out.println("'Game_Start<EOF>' message correctly received.");
            }
            else
            {
                System.out.println("Unrecognised message.");
            }
        }
    }

    InetAddress board_address = dgp.getAddress();
    return board_address;
  }
}
