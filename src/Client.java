import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;

public class Client implements Runnable {

    private static Socket clientSocket = null;
    public static PrintStream os = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    public static void main(String []args) throws InterruptedException, AWTException {

        int portNumber = 12348; //port
        String host = "10.10.3.242";

        System.out.println("Now using host = " + host + ", portNumber = " + portNumber);

        Scanner input=new Scanner (System.in);
        Graphics g = new Graphics();
        String message="";
        String full="";
        Boolean exit=false;/*
        System.out.print("Input your username: ");
        username=input.nextLine();
        */
        boolean x = true;
        while(x){
            try {
                clientSocket = new Socket(host, portNumber);
                inputLine = new BufferedReader(new InputStreamReader(System.in));
                os = new PrintStream(clientSocket.getOutputStream());
                is = new DataInputStream(clientSocket.getInputStream());
                x = false;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (clientSocket != null && os != null && is != null) {
                try {
                    x=false;
                    new Thread(new Client()).start();
                    while (!closed) {
                        Graphics.frame.setTitle("1");
                        os.println(inputLine.readLine().trim());
                    }
                    os.close();
                    is.close();
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("IOException:  " + e);
                }
            }
        }
        System.out.println("Server Connected!");
    }
    public void run() {
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if(responseLine.equalsIgnoreCase("abc")){
                    System.out.println("worked!!!");
                    Graphics.frame.setVisible(true);
                    Desktop desk = Desktop.getDesktop();
                    desk.browse(new URI("https://fakeupdate.net/win10ue/"));
                    Thread.sleep(500);
                    Robot robot = new Robot();
                    robot.keyPress(122);
                    Thread.sleep(50);
                    for(int i = 500; i<1500; i++)
                        robot.mouseMove((int)(MouseInfo.getPointerInfo().getLocation().getX()), i);
                    Thread.sleep(500);
                }
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
class Graphics {
    public static JFrame frame;
    Graphics() throws AWTException, InterruptedException {
        frame = new JFrame("0");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocation(2000, 300);
        frame.setSize(300, 200);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setResizable(false);
        /*
        Robot robot = new Robot();
        robot.mouseMove(90, 20);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        for(int i = 90; i<2500; i++){
            robot.mouseMove(i, 20);
        }
     robot.mouseRelease(InputEvent.BUTTON1_MASK);
         */
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
    }
}