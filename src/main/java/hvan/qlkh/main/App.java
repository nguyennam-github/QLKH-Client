/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package hvan.qlkh.main;

import hvan.qlkh.services.Services;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;

/**
 *
 * @author PC
 */
public class App {

    public static void main(String[] args) throws IOException, JAXBException, ClassNotFoundException{
        SwingUtilities.invokeLater(() -> {
            hvan.qlkh.views.App.main(args);
            try {
                Services.getInstance().getUser();
                Services.getInstance().get();
            } catch (IOException | JAXBException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        Main.getInstance().initStatistic();
    }
    
//    private static void write(BufferedWriter os, String message) throws IOException{
//        os.write(message);
//        os.newLine();
//        os.flush();
//    }
//    
//    public static Thread thread;
//    public static volatile BufferedWriter os;
//    public static volatile BufferedReader is;
//    
//    public static void main(String[] args) throws IOException {
//        Services.getInstance().create(new Product("1", "1", "1", 10, BigDecimal.ONE, new Date(), "1"));
//        try {
//            thread = new Thread() {
//                @Override
//                public void run() {
//                    try {
//                            // Gửi yêu cầu kết nối tới Server đang lắng nghe
//                            // trên máy 'localhost' cổng 7777.
//                            Socket socketOfClient = new Socket("localhost", 7777);
//                            System.out.println("Kết nối thành công!");
//                            // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
//                            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
//                            // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
//                            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
//                        String message;
//                        while (true) {
//                            message = is.readLine();
//                            if(message==null){
//                                break;
//                            }
//                            System.out.println(message);
//    //                            if(message.equals("modify")){
//    //                                System.out.println("modify");
//    //                            }
//                        }
//    //                    os.close();
//    //                    is.close();
//    //                    socketOfClient.close();
//                    } catch (UnknownHostException e) {
//                    } catch (IOException e) {
//                    }
//                }
//            };
//            thread.start();
//        } catch (Exception e) {
//        }
//        Scanner sc = new Scanner(System.in);
//        while (true) {            
//        int c = sc.nextInt();
//        String request;
//        request = "Delete/1";
//            System.out.println(request);
//            if (c == 0){
//                write(os, request);
//            }
//        }
//    }
}
