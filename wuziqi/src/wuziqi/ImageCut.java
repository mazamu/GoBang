package wuziqi;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class ImageCut {
	public static void scale(String srcImageFile, String result) {
		int scale = 0;
		  try {
			   BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			   int width = src.getWidth(); // 得到源图宽
			   int height = src.getHeight(); // 得到源图长
			
			   width=GameData.GAME_WIDTH;
			   height=GameData.GAME_HEIGHT;
			   
			   Image image = src.getScaledInstance(width, height,
			     Image.SCALE_SMOOTH); // 返回图像的缩放版本。
			   BufferedImage tag = new BufferedImage(width, height,
			     BufferedImage.TYPE_INT_RGB); // 预定义一个图像
			   Graphics g = tag.getGraphics(); // 返回Graphics，可用于绘制预定义的图像。
			   g.drawImage(image, 0, 0, null); // 用图像的缩放版本去绘制缩放后的图
			
			   g.dispose(); // 释放此图形的上下文以及它使用的所有系统资源。调用 dispose 之后，就不能再使用 Graphics
			   // 对象
			   ImageIO.write(tag,"png", new File(result)); // 输出到文件流
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		 }
	
		 public static void CUT(String string,String string2) {
		  ImageCut.scale(string, string2);
		 }
}
