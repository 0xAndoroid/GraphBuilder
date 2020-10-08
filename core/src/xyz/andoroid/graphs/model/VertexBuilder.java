package xyz.andoroid.graphs.model;


import com.badlogic.gdx.math.Circle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VertexBuilder {
    public int lastId;
    public List<Vertex> vertices;
    public EdgeBuilder edgeBuilder;

    public VertexBuilder (EdgeBuilder edgeBuilder) {
        lastId = 0;
        vertices = new ArrayList<>();
        this.edgeBuilder = edgeBuilder;
    }

    public void build(float x, float y) {
        boolean add = true;
        Circle c = new Circle(x,y,10);
        for(int i=0;i<vertices.size();i++) {
            if(vertices.get(i).circle.overlaps(c)) {
                add = false;
                break;
            }
        }
        if(add) vertices.add(new Vertex(lastId++,x,y));
    }

    public void delete(float x, float y) {
        List<Integer> toDelete = new ArrayList<>();
         for(int i=0;i<vertices.size();i++)
             if (vertices.get(i).circle.contains(x, y)) toDelete.add(i);
        Collections.reverse(toDelete);
        for (int i : toDelete) {
            Vertex v = vertices.remove(i);
            edgeBuilder.delete(v.id);
            for(int j=0;j<vertices.size();j++) {
                if(vertices.get(j).id > v.id) vertices.get(j).id--;
            }
            lastId--;
            for(int j=0;j<edgeBuilder.edges.size();j++) {
                if(edgeBuilder.edges.get(j).v1 > v.id) edgeBuilder.edges.get(j).v1--;
                if(edgeBuilder.edges.get(j).v2 > v.id) edgeBuilder.edges.get(j).v2--;
            }
        }

    }

    public boolean isOverlapping(float x, float y, float radius) {
        Circle tmp = new Circle(x,y,radius);
        for (Vertex vertex : vertices) if (vertex.circle.overlaps(tmp)) return true;
        return false;
    }

    public Vertex getVertex(int id) {
        for(int i=0;i<vertices.size();i++) if(vertices.get(i).id == id) return vertices.get(i);
        throw new NullPointerException("Vertex with asked ID ("+id+") doesn't exist.");
    }

    public Vertex getVertex(float x, float y) {
        for(int i=0;i<vertices.size();i++) if(vertices.get(i).circle.contains(x,y)) return vertices.get(i);
        return null;
    }
}
