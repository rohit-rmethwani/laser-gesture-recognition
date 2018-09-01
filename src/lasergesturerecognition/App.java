/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lasergesturerecognition;

/**
 *
 * @author Raju Methwani
 */
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.Videoio;
import org.opencv.videoio.VideoCapture;
public class App
{
 static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
 }

 private JFrame frame;
 private JLabel imageLabel;

 public static void main(String[] args) {
 App app = new App();
 app.initGUI();
 app.runMainLoop(args);
 }

 private void initGUI() {frame = new JFrame("Camera Input Example");
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.setSize(400,400);
 imageLabel = new JLabel();
 frame.add(imageLabel);
 frame.setVisible(true);
 }
 private void runMainLoop(String[] args) {
 Mat webcamMatImage = new Mat();
 Image tempImage;
 VideoCapture capture = new VideoCapture(0);
 capture.set(Videoio.CAP_PROP_FRAME_WIDTH,320);
 capture.set(Videoio.CAP_PROP_FRAME_HEIGHT,240);
 if( capture.isOpened()){
 while (true){
 capture.read(webcamMatImage);
 if( !webcamMatImage.empty() ){
 tempImage= toBufferedImage(webcamMatImage);
 ImageIcon imageIcon = new ImageIcon(tempImage, "Captured video");
 imageLabel.setIcon(imageIcon);
 frame.pack(); //this will resize the window to fit the image
 }
 else{
 System.out.println(" -- Frame not captured -- Break!");
 break;
 }
 }
 } else{
 System.out.println("Couldn't open capture.");
 }
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
}