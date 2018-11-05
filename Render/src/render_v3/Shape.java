package render_v3;

import java.awt.Color;

public abstract class Shape extends Local{
    public Shape(double x, double y, double z){
        super(x, y, z);
    }
    
    public abstract double[][] verts();
    public abstract int[][] faces();
    public abstract Color color();
}
