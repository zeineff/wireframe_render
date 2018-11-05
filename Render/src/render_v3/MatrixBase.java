package render_v3;

public class MatrixBase{
    public double[][] m;
    
    public MatrixBase(int w, int h){
        m = new double[w][h];
    }
    
    public MatrixBase(int w, int h, double...nums){
        m = new double[w][h];
        
        for (int j = 0, k = 0; j < w; j++)
            for (int i = 0; i < h; i++, k++)
                m[j][i] = nums[k];
    }
    
    public MatrixBase(double[][] m){
        this.m = m;
    }
    
    
    
    public void rot(double x, double y, double z, double a){
        a = Math.toRadians(a);
        
        double xy = x * y;
        double xz = x * z;
        double yz = y * z;
        
        double sin = Math.sin(a);
        double cos = Math.cos(a);
        
        double x_sin = x * sin;
        double y_sin = y * sin;
        double z_sin = z * sin;
        
        double n_cos = 1 - cos;
        
        double[][] temp = {
            {cos + x * x * (n_cos),
            xy * n_cos + z_sin,
            xz * n_cos - y_sin},
            
            {xy * n_cos - z_sin,
            cos + y * y * n_cos,
            yz * n_cos + x_sin},
            
            {xz * n_cos + y_sin,
            yz * n_cos - x_sin,
            cos + z * z * n_cos}
        };
        
        m = new Matrix(temp).mul(this).m;
    }
    
    
    
    public void print(){
        for (int i = 0; i < m[0].length; i++){
            System.out.printf("[%.2f", m[0][i]);
            
            for (int j = 1; j < m.length; j++)
                System.out.printf(", %.2f", m[j][i]);
            
            System.out.print("]\n");
        }
        
        System.out.print("\n");
    }
}
