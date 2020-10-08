package xyz.andoroid.graphs.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import xyz.andoroid.graphs.Main;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85);
		config.height = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.85);
		config.x = 0;
		config.y = 0;
		new LwjglApplication(new Main(), config);
	}
}
