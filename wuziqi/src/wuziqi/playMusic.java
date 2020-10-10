package wuziqi;
import java.io.FileInputStream;

//import sun.audio.*;
//
//public class playMusic {
//	public playMusic()
//	{
//		try {
//			FileInputStream fileau=new FileInputStream("music/sound.wav" );
//			AudioStream as=new AudioStream(fileau);
//			AudioPlayer.player.start(as);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//}

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
 
public class playMusic {
	String namespath;
	public playMusic(String spath){
		this.namespath=spath;
		play();
	}
  public void play() {  
 
  try   {  
	  FileInputStream fileau=new   FileInputStream(namespath);  
          AudioStream   as=new   AudioStream(fileau);  
          AudioPlayer.player.start(as);  
          AudioPlayer.player.start(as);  
          AudioPlayer.player.start(as);  
            }  
        catch   (Exception   e)   {
        	System.out.println("DFGHJKL");
        }  
    }  
 
}
