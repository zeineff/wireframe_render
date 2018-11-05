package render_v3;

import java.awt.Color;

public class Cube extends Shape{
    double r;
    double[][] verts;
    
    static final int[][] TRIS = {
        {0, 2, 3},
        {3, 1, 0},
        {1, 3, 7},
        {7, 5, 1},
        {5, 7, 6},
        {6, 4, 5},
        {4, 6, 2},
        {2, 0, 4},
        {4, 0, 1},
        {1, 5, 4},
        {2, 6, 7},
        {7, 3, 2}
    };
    
    public Cube(double x, double y, double z, double r){
        super(x, y, z);
        
        set_r(r);
    }
    
    public final void set_r(double r){
        this.r = r;
        
        set_vertices();
    }
    
    private void set_vertices(){
        double nr = -r;
        
        double[][] temp_01 = {
            {nr, nr, nr},
            {r, nr, nr},
            {nr, r, nr},
            {r, r, nr},
            {nr, nr, r},
            {r, nr, r},
            {nr, r, r},
            {r, r, r}
        };
        
        verts = temp_01;
    }
    
    @Override
    public double[][] verts(){
        return verts;
    }

    @Override
    public int[][] faces(){
        return TRIS;
    }

    @Override
    public Color color(){
        return Color.RED;
    }
}
