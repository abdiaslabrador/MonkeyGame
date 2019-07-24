package libgdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import libgdx.game.MonkeyGame;

public class DesktopLauncher {
	public static void main (String[] arg){
		/*LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MAIN_STRIKE(), config);*/
		
		MonkeyGame myprogram = new MonkeyGame();
		LwjglApplication launcher = new LwjglApplication(myprogram);
	}
}
