package render_v3;

import java.awt.Color;

public class Model extends Shape{
    double[][] verts;
    int[][] tris;
    
    public Model(double x, double y, double z, String file){
        super(x, y, z);
        
        ReadModel a = new ReadModel(file);
        
        verts = a.verts;
        tris = a.tris;
    }
    
    @Override
    public double[][] verts(){
        return verts;
    }

    @Override
    public int[][] faces(){
        return tris;
    }

    @Override
    public Color color(){
        return Color.BLUE;
    }
}
