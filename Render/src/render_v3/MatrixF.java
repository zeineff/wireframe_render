package render_v3;

public class MatrixF{
    public static double[][] mul(double[][] a, double[][] b){
        double[][] c = new double[b.length][a[0].length];
        
        for (int k = 0; k < b.length; k++)
            for (int i = 0; i < a[0].length; i++)
                for (int j = 0; j < a.length; j++)
                    c[k][i] += a[j][i] * b[k][j];
        
        return c;
    }
    
    public static double[][] mul2(double[][] a, double[][] b){
        double[][] c = new double[b.length][3];
        
        for (int k = 0; k < b.length; k++)
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++)
                    c[k][i] += a[j][i] * b[k][j];
                
                c[k][i] += a[3][i];
            }
        
        return c;
    }
    
    public static double[][] identity(){
        return new double[][]{
            {1,0,0},
            {0,1,0},
            {0,0,1}
        };
    }
    
    public static double[][] identity(int n){
        double[][] m = new double[n][n];
        
        for (int i = 0; i < n; i++)
            m[i][i] = 1;
        
        return m;
    }
    
    public static double[][] inverse(double[][] m){
        double[][] n = copy(m);
        double[][] identity = identity(m.length);
        
        for (int j = 0; j < m.length; j++){
            double a = n[j][j];
            div_row(n, j, a);
            div_row(identity, j, a);
            
            for (int i = 0; i < m.length; i++)
                if (i != j && n[j][i] != 0){
                    a = n[j][i];
                    sub_row(n, i, j, a);
                    sub_row(identity, i, j, a);
                }
        }
        
        return identity;
    }
    
    public static double[][] copy(double[][] a){
        double[][] b = new double[a.length][a[0].length];
        
        for (int j = 0; j < a.length; j++)
            System.arraycopy(a[j], 0, b[j], 0, a[j].length);
        
        return b;
    }
    
    public static double det(double[][] m){
        double[][] n = copy(m);
        
        for (int j = 0; j < m.length - 1; j++){
            double a = m[j][j];
            div_row(n, j, a);
            
            for (int i = j + 1; i < m.length; i++)
                if (n[j][i] != 0)
                    sub_row(n, i, j, n[j][i]);
            
            mul_row(n, j, a);
        }
        
        double det = 1;
        
        for (int i = 0; i < m.length; i++)
            det *= n[i][i];
        
        return det;
    }
    
    private static void mul_row(double[][] m, int i, double n){
        for (double[] vector:m)
            vector[i] *= n;
    }
    
    private static void div_row(double[][] m, int i, double n){
        mul_row(m, i, 1 / n);
    }
    
    private static void sub_row(double[][] m, int a, int b, double factor){
        for (double[] vector:m)
            vector[a] -= vector[b] * factor;
    }
    
    
    
    public static double[][] rot_x(double[][] m, double a){
        a = Math.toRadians(a);
        
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        
        double[][] temp = {
            {1, 0, 0},
            {0, cos, -sin},
            {0, sin, cos}
        };
        
        return mul(temp, m);
    }
    
    public static double[][] rot_y(double[][] m, double a){
        a = Math.toRadians(a);
        
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        
        double[][] temp = {
            {cos, 0, sin},
            {0, 1, 0},
            {-sin, 0, cos}
        };
        
        return mul(temp, m);
    }
    
    public static double[][] rot_z(double[][] m, double a){
        a = Math.toRadians(a);
        
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        
        double[][] temp = {
            {cos, -sin, 0},
            {sin, cos, 0},
            {0, 0, 1}
        };
        
        return mul(temp, m);
    }
    
    
    
    public static void print(double[][] m){
        for (int i = 0; i < m[0].length; i++){
            System.out.printf("[%.2f", m[0][i]);
            
            for (int j = 1; j < m.length; j++)
                System.out.printf(", %.2f", m[j][i]);
            
            System.out.print("]\n");
        }
        
        System.out.print("\n");
    }
}
