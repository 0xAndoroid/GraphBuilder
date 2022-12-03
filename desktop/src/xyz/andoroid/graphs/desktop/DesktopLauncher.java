package xyz.andoroid.graphs.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import xyz.andoroid.graphs.Main;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		double multiplier = 0.85*Toolkit.getDefaultToolkit().getScreenResolution()/120;
		config.width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*multiplier);
		config.height = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*multiplier);
		config.x = 0;
		config.y = 0;
		config.resizable = false;
		new LwjglApplication(new Main(), config);
	}
}
