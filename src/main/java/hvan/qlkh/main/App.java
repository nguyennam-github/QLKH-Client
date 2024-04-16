/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.models.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;
import javax.xml.bind.JAXB;

/**
 *
 * @author PC
 */
public class App {

//    public static void main(String[] args) throws IOException, JAXBException, ClassNotFoundException{
//        long before = Clock.systemDefaultZone().millis();
//        hvan.qlkh.views.App.main(args);
//        long after = Clock.systemDefaultZone().millis();
//        System.out.println("Thoi gian thu thi: " + (after - before));
//    }
    
    private static void write(BufferedWriter os, String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int c = sc.nextInt();
        Thread thread;
        int id;
        try {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        // Gửi yêu cầu kết nối tới Server đang lắng nghe
                        // trên máy 'localhost' cổng 7777.
                        Socket socketOfClient = new Socket("localhost", 7777);
                        System.out.println("Kết nối thành công!");
                        // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
                        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
                        // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
                        BufferedReader is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
                    String request;
                    StringWriter sw = new StringWriter();
                    JAXB.marshal(new Product("2", "2", "2", 10, new BigDecimal("2"), new Date(), "2"), sw);
                    String xmlString = sw.toString();
                    request = "Delete/2";
                        System.out.println(request);
                        if (c == 0){
                            write(os, request);
                        }
                        String message;
                        while (true) {
                            message = is.readLine();
                            if(message==null){
                                break;
                            }
                            System.out.println(message);
//                            if(message.equals("modify")){
//                                System.out.println("modify");
//                            }
                        }
//                    os.close();
//                    is.close();
//                    socketOfClient.close();
                    } catch (UnknownHostException e) {
                    } catch (IOException e) {
                    }
                }
            };
            thread.run();
        } catch (Exception e) {
        }
        
    }
}
