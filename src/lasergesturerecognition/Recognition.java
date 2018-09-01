/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasergesturerecognition;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class Recognition{
   int i=0;
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public void captureImage(){
        Mat capturedImage = new Mat();
        Mat thresholdImage = new Mat();
        byte[] thresholdImageBytes;
        Point cordinates=new Point();
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH,640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT,480);
        if(capture.isOpened()){
            while(true){
                System.out.println("Naya image capture hua");
                capture.read(capturedImage);
                System.out.println("Image read hua");
                if(!capturedImage.empty()){
                    System.out.println("Andar if");
                    System.out.println("Andar try");
                    thresholdImage=getThresholdImage(capturedImage);
                    System.out.println("threshold image mila uska");
                    try {
                        ImageIO.write(toBufferedImage(thresholdImage), "jpg", new File("C:\\Users\\Raju Methwani\\Desktop\\NayaLaser.jpg"));
                    } catch (IOException ex) {
                        Logger.getLogger(Recognition.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Write hua");
                    thresholdImageBytes=toBytes(thresholdImage);
                    System.out.println("Bytes me convert kiya image ko");
//                    for(int i=0;i<thresholdImageBytes.length;i++){
//                        for(int j=0;j<thresholdImageBytes[i].length;j++){
//                            if(thresholdImageBytes[j]!=0){
//                                System.out.println("inside j wala for"+j);
//                                cordinates.setLocation((j%thresholdImage.width()),(i/thresholdImage.width()));
//                                comparePoints(cordinates,i);
//                                i++;
//                            }
//                            else{
//                                System.out.println("Ye image kaam ka nai hai");
//                            }
//                        }
//                    }
//                        cordinates=getPoint(thresholdImageBytes);
//                        System.out.println("cordinates nikale");
//                        comparePoints(cordinates,i);
//                        System.out.println("comapre ko cordinates diye");
//                        i++;
//                        System.out.println("I++hua");
                }
            }
        }
    }
    
    public Mat getThresholdImage(Mat image){
        Mat thresholdImage = new Mat();
        Imgproc.threshold(image, thresholdImage, 230, 255, Imgproc.THRESH_BINARY);
        return thresholdImage;
    }
    
    public byte[] toBytes(Mat image){
        byte[] imageBytes = new byte[(int)image.total()*image.channels()];
        image.get(0, 0,imageBytes);
        return imageBytes;
    }
    
    public Point getPoint(byte[] image){
        Point point =new Point();
        System.out.println("Inside point");
        for(int i=0;i<image.length;i++){
            for(int j=0;j<image.length;j++){
                if(image[i]!=0 && image[j]!=0){
                    point.setLocation(j,i);
                }
                else{
                    System.out.println("i:"+image[i]+"j:"+image[j]);
                    System.out.println("Kaam ka nai hai pixel");
                }
            }
        }
        return point;
    }
    
    public void comparePoint(Point cordinates,int i){
        Point oldCordinates=new Point();
        if(i!=0){
            if(oldCordinates.getX()<cordinates.getX()){
                System.out.println("Right swipe");
            }
            else{
                System.out.println("Left swipe");
            }
        }
        else{
            System.out.println("This is first image");
            oldCordinates=cordinates;
        }
    }
    private static BufferedImage toBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if(mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] buffer = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        mat.get(0,0,buffer);
        return image;
    }
    
    public void comparePoints(Point p,int i){
        Point oldCordinates = new Point();
        if(i!=0){
            if(oldCordinates.getX() > p.getX()){
                System.out.println("Left side");
            }
            else{
                System.out.println("Right side");
            }
        }
        else{
            System.out.println("This is the first image");
            oldCordinates=p;
        }
    }
    public static void main(String[] args) {
        new Recognition().captureImage();
    }
}
