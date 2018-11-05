package render_v3;

public class Matrix extends MatrixBase{
    public Matrix(int w, int h){
        super(w, h);
    }
    
    public Matrix(int w, int h, double...nums){
        super(w, h, nums);
    }
    
    public Matrix(double[][] m){
        super(m);
    }
    
    
    
    public Matrix mul(MatrixBase n){
        double[][] newM = new double[n.m.length][m[0].length];
        
        for (int k = 0; k < n.m.length; k++)
            for (int i = 0; i < m[0].length; i++)
                for (int j = 0; j < m.length; j++)
                    newM[k][i] += m[j][i] * n.m[k][j];
        
        return new Matrix(newM);
    }
    
    public Matrix mul2(MatrixBase n){
        double[][] newM = new double[n.m.length][3];
        
        for (int k = 0; k < n.m.length; k++)
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++)
                    newM[k][i] += m[j][i] * n.m[k][j];
                
                newM[k][i] += m[3][i];
            }
        
        return new Matrix(newM);
    }
    
    public Matrix inverse(){
        Matrix n = copy();
        Matrix identity = identity(m.length);
        
        for (int j = 0; j < m.length; j++){
            double a = n.m[j][j];
            n.div_row(j, a);
            identity.div_row(j, a);
            
            for (int i = 0; i < m.length; i++)
                if (i != j && n.m[j][i] != 0){
                    a = n.m[j][i];
                    n.sub_row(i, j, a);
                    identity.sub_row(i, j, a);
                }
        }
        
        return identity;
    }
    
    public Matrix copy(){
        double[][] newM = new double[m.length][m[0].length];
        
        for (int j = 0; j < m.length; j++)
            System.arraycopy(m[j], 0, newM[j], 0, m[j].length);
        
        return new Matrix(newM);
    }
    
    public double[] column(int j){
        double[] temp = new double[m[j].length];
        System.arraycopy(m[j], 0, temp, 0, m[j].length);
        return temp;
    }
    
    public double[] row(int i){
        double[] temp = new double[m.length];
        
        for (int j = 0; j < m.length; j++)
            temp[j] = m[j][i];
        
        return temp;
    }
    
    public double det(){
        Matrix n = copy();
        
        for (int j = 0; j < m.length - 1; j++){
            double a = n.m[j][j];
            n.div_row(j, a);
            
            for (int i = j + 1; i < m.length; i++)
                if (n.m[j][i] != 0)
                    n.sub_row(i, j, n.m[j][i]);
            
            n.mul_row(j, a);
        }
        
        double det = 1;
        
        for (int i = 0; i < m.length; i++)
            det *= n.m[i][i];
        
        return det;
    }
    
    private void mul_row(int i, double n){
        for (double[] vector:m)
            vector[i] *= n;
    }
    
    private void div_row(int i, double n){
        mul_row(i, 1 / n);
    }
    
    private void sub_row(int a, int b, double factor){
        for (double[] vector:m)
            vector[a] -= vector[b] * factor;
    }
    
    public void rot_x(double a){
        a = Math.toRadians(a);
        
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        
        double[][] temp = {
            {1, 0, 0},
            {0, cos, -sin},
            {0, sin, cos}
        };
        
        m = new Matrix(temp).mul(this).m;
    }
    
    public void rot_y(double a){
        a = Math.toRadians(a);
        
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        
        double[][] temp = {
            {cos, 0, sin},
            {0, 1, 0},
            {-sin, 0, cos}
        };
        
        m = new Matrix(temp).mul(this).m;
    }
    
    public void rot_z(double a){
        a = Math.toRadians(a);
        
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        
        double[][] temp = {
            {cos, -sin, 0},
            {sin, cos, 0},
            {0, 0, 1}
        };
        
        m = new Matrix(temp).mul(this).m;
    }
    
    
    
    public static Matrix identity(){
        double[][] m = {
            {1,0,0},
            {0,1,0},
            {0,0,1}
        };
        
        return new Matrix(m);
    }
    
    public static Matrix identity(int n){
        double[][] m = new double[n][n];
        
        for (int i = 0; i < n; i++)
            m[i][i] = 1;
        
        return new Matrix(m);
    }
}
