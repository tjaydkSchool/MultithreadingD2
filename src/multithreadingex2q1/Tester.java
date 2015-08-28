/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithreadingex2q1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Dennis
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Tester prog = new Tester();
        ExecutorService exe = Executors.newFixedThreadPool(3);

        System.out.println("Avilable Processors: " + Runtime.getRuntime().availableProcessors());

        myThread t1 = new myThread("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/fronter_big-logo.png");
        myThread t2 = new myThread("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/folder-image-transp.png");
        myThread t3 = new myThread("https://fronter.com/volY12-cache/cache/img/design_images/Classic/other_images/button_bg.png");

        long start1 = System.nanoTime();
        
        
        exe.execute(t1);
        exe.execute(t2);
        exe.execute(t3);

        exe.shutdown();

        exe.awaitTermination(10, TimeUnit.SECONDS);
        
        long end1 = System.nanoTime();
        System.out.println("Time parallel: " + (end1 - start1));
        
        
        
//        RUNNING THE PROCESSES IN PARALLEL
        
        long start2 = System.nanoTime();
        t1.run();
        t2.run();
        t3.run();

        

        long end2 = System.nanoTime();
        System.out.println("Time Sequental: " + (end2 - start2));

        
        int sum = t1.getSum() + t2.getSum() + t3.getSum();

        System.out.println("The sum is: " + sum/2);
    }

    protected static byte[] getBytesFromUrl(String url) {
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        try {
            InputStream is = new URL(url).openStream();
            byte[] bytebuff = new byte[4096];
            int read;
            while ((read = is.read(bytebuff)) > 0) {
                bis.write(bytebuff, 0, read);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return bis.toByteArray();
    }

    public static class myThread implements Runnable {

        String URL;
        int byteSum;
        InputStream input;
        byte[] ba;

        public myThread(String URL) {
            this.URL = URL;
        }

        @Override
        public void run() {

//            THIS WAY THE SUM IS CORRECT!!!!!!
//            ByteArrayInputStream bInput = new ByteArrayInputStream(getBytesFromUrl(URL));
//            while(bInput.read() != -1) {
//                System.out.println(bInput.read());
//            }
            ba = getBytesFromUrl(URL);

            for (int i = 0; i < ba.length; i++) {
                byteSum += ba[i];
            }

        }

        public int getSum() {
            return byteSum;
        }
    }

}
