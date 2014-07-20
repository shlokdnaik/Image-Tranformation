
	import java.awt.*;
	import java.awt.image.*;
	import java.io.*;
	import javax.swing.*;
	import java.awt.event.*;
	import java.lang.Object;
	import java.awt.geom.*;
	import javax.imageio.ImageIO;
	import static java.lang.Math.*;



	public class imageReader {

	  
	   public static void main(String[] args) { 
	   
		String fileName = args[0];       
		
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);	
		double SCALE = Double.parseDouble(args[3]);
		double angle = Double.parseDouble(args[4]);
		int anti = Integer.parseInt(args[5]);
		int h = height; 
		int w = width;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);			
		try {
			File file = new File(args[0]);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytes = new byte[(int)len];
			
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}
			
				
			int ind = 0;
			for(int y = 0; y < height; y++){
		
				for(int x = 0; x < width; x++){
			 
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 
					
					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					img.setRGB(x,y,pix);
					ind++;
				}
			}

			
		} catch (FileNotFoundException e) {
		  e.printStackTrace();
		} catch (IOException e) {
		  e.printStackTrace();
		}
		
		
		
		//SCALING
		int newH = (int)Math.round(h * SCALE);
		int newW = (int)Math.round(w * SCALE);
		BufferedImage scaledImage = new BufferedImage(newW,newH, BufferedImage.TYPE_INT_RGB);  
		Graphics2D g1 = scaledImage.createGraphics();   
		g1.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
		g1.dispose(); 
			
		
		//ROTATION
		double rad = toRadians(angle);
		double eps = 1e-3;
		int W=(int)(abs(cos(rad))*newW+abs(sin(rad))*newH-eps)+1;//W after rotation(calculated by using a little geometry ) 
		int H=(int)(abs(sin(rad))*newW+abs(cos(rad))*newH-eps)+1;//H after rotation 
		BufferedImage rotatedImage = new BufferedImage(W,H, BufferedImage.TYPE_INT_RGB);// you can change it to any type you want it's just a sample 
		Graphics2D g = rotatedImage.createGraphics();
		g.setColor(Color.WHITE); // background color of red for displaying the red edges when image is not completely fit 
		g.fillRect(0, 0, W, H);
		int x=(W-newW)/2;   
		int y=(H-newH)/2;  
		if(anti==1)
		{
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		}
		
		g.translate(x, y); // moving dimg center to img center ( this was what first code lack in  ) 
		g.rotate(-rad, newW/2, newH/2);  // now rotating dimg around the center of img ( which is now same as center of dimg )
		g.drawImage(scaledImage, null, 0, 0);
    
    /*g.drawImage(img,null,0,0);
		BufferedImage rotatedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);  
		Graphics2D g = rotatedImage.createGraphics();         
		g.rotate(Math.toRadians(angle), newW/2, newH/2); 
		g.translate((rotatedImage.getWidth() - scaledImage.getWidth()) / 2, (rotatedImage.getHeight() - scaledImage.getHeight()) / 2);	 
		g.drawImage(scaledImage, null, 0, 0);     
	
		//ANTI-ALIASING
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		*/
		   
		JFrame frame = new JFrame("Modified Image");
		
		JLabel label1 = new JLabel(new ImageIcon(img));
		JLabel label2 = new JLabel(new ImageIcon(rotatedImage));
		label1 = new JLabel ("<-Original", new ImageIcon(img), SwingConstants.LEFT);
		label2 = new JLabel ("<-Modified",new ImageIcon(rotatedImage), SwingConstants.RIGHT);
		JPanel subPanel1 = new JPanel();
		subPanel1.add (label1);
		subPanel1.add (label2);
		JScrollPane scrollPane = new JScrollPane(subPanel1);  
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
		frame.getContentPane().add(scrollPane);  
        subPanel1.setBackground(Color.white);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               

	   }
	  
	}
