package wuziqi;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class changeAlpha {
	 public changeAlpha(String path,String tarPath,int alpha) {
			//检查透明度是否越界  
	        if (alpha < 0) {  
	          alpha = 0;  
	        } else if (alpha > 10) {  
	          alpha = 10;  
	        }  
			 try {
			        BufferedImage image = ImageIO.read(new File(path));
			        int weight = image.getWidth();
			        int height = image.getHeight();
			        
			        BufferedImage output = new BufferedImage(weight ,height, BufferedImage.TYPE_INT_ARGB);
			        Graphics2D g2 = output.createGraphics();
			        output = g2.getDeviceConfiguration().createCompatibleImage(weight, height, Transparency.TRANSLUCENT);
			        g2.dispose();
			        g2 = output.createGraphics();
			    
			        //调制透明度
			        for (int j1 = output.getMinY(); j1 < output.getHeight(); j1++) {  
			            for (int j2 = output.getMinX(); j2 < output.getWidth(); j2++) {  
				            int rgb = output.getRGB(j2, j1);  
				            rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);  
				            output.setRGB(j2, j1, rgb);  
			            }  
			        }  
			        g2.setComposite(AlphaComposite.SrcIn);
			        g2.drawImage(image, 0, 0, weight,height, null);
			        g2.dispose();
			        ImageIO.write(output, "png", new File(tarPath));
			     
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
		}
	 public static void ChAlpha(String string,String string2,int alpha) {
		 changeAlpha p=new changeAlpha(string,string2, alpha);
	}
}
