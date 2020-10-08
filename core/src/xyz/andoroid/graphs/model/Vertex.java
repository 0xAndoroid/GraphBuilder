package xyz.andoroid.graphs.model;

import com.badlogic.gdx.math.Circle;

public class Vertex {
    public Circle circle;
    public int id;

    protected Vertex (int id, float x, float y) {
        this.id = id;
        circle = new Circle(x,y,10);
    }
}
