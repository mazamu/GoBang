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
			   BufferedImage src = ImageIO.read(new File(srcImageFile)); // �����ļ�
			   int width = src.getWidth(); // �õ�Դͼ��
			   int height = src.getHeight(); // �õ�Դͼ��
			
			   width=GameData.GAME_WIDTH;
			   height=GameData.GAME_HEIGHT;
			   
			   Image image = src.getScaledInstance(width, height,
			     Image.SCALE_SMOOTH); // ����ͼ������Ű汾��
			   BufferedImage tag = new BufferedImage(width, height,
			     BufferedImage.TYPE_INT_RGB); // Ԥ����һ��ͼ��
			   Graphics g = tag.getGraphics(); // ����Graphics�������ڻ���Ԥ�����ͼ��
			   g.drawImage(image, 0, 0, null); // ��ͼ������Ű汾ȥ�������ź��ͼ
			
			   g.dispose(); // �ͷŴ�ͼ�ε��������Լ���ʹ�õ�����ϵͳ��Դ������ dispose ֮�󣬾Ͳ�����ʹ�� Graphics
			   // ����
			   ImageIO.write(tag,"png", new File(result)); // ������ļ���
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		 }
	
		 public static void CUT(String string,String string2) {
		  ImageCut.scale(string, string2);
		 }
}
