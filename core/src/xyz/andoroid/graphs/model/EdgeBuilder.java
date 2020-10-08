package xyz.andoroid.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EdgeBuilder {
    public List<Edge> edges;

    public EdgeBuilder () {
        edges = new ArrayList<>();
    }

    public void build(int v1, int v2) {
        boolean add = true;
        for(int i=0;i<edges.size();i++) {
            if((edges.get(i).v1 == v1 && edges.get(i).v2 == v2) || (edges.get(i).v1 == v2 && edges.get(i).v2 == v1)) {
                add = false;
                break;
            }
        }
        if(add) edges.add(new Edge(v1, v2));
    }

    public void delete(int v) {
        List<Integer> toDelete = new ArrayList<>();
        for(int i=0;i<edges.size();i++) if(edges.get(i).v1 == v || edges.get(i).v2 == v) toDelete.add(i);
        Collections.reverse(toDelete);
        for(int i : toDelete) edges.remove(i);
    }

    public void delete(int v1, int v2) {
        List<Integer> toDelete = new ArrayList<>();
        for(int i=0;i<edges.size();i++) if((edges.get(i).v1 == v1 && edges.get(i).v2 == v2) || (edges.get(i).v1 == v2 && edges.get(i).v2 == v1)) toDelete.add(i);
        Collections.reverse(toDelete);
        for(int i : toDelete) edges.remove(i);
    }

}
