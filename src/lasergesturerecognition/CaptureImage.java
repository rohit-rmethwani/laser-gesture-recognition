/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasergesturerecognition;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Raju Methwani
 */
public class CaptureImage {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public Mat doCapture(){
        Mat webcamImage = new Mat();
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH,320);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT,240);
        if( capture.isOpened()){
            while (true){
                capture.read(webcamImage);
                if( !webcamImage.empty() ){
                    try {
                        Mat newMat = new Mat();
                        Imgproc.threshold(webcamImage, newMat, 220, 255, Imgproc.THRESH_BINARY);
                        ImageIO.write(toBufferedImage(newMat), "jpg", new File("C:\\Users\\Raju Methwani\\Desktop\\3.jpg"));
                        return webcamImage;
                    } catch (IOException ex) {
                        Logger.getLogger(CaptureImage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    System.out.println(" -- Frame not captured -- Break!");
                }
            }
       }
        return null;
    }

    public BufferedImage toBufferedImage(Mat matrix){
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if ( matrix.channels() > 1 ) {
                    type = BufferedImage.TYPE_3BYTE_BGR;
            }
            int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
            byte [] buffer = new byte[bufferSize];
            matrix.get(0,0,buffer); // get all the pixels
            BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
            final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);  
            return image;
    }
  
    public static void main(String[] args) {
        new CaptureImage().doCapture();
    }
}
