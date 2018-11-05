package render_v3;

public class Vector{
    public static void normalize(double[] a){
        double m = 0;
        
        for (int i = 0; i < a.length; i++)
            m += a[i] * a[i];
        
        m = 1 / Math.sqrt(m);
        
        for (int i = 0; i < a.length; i++)
            a[i] *= m;
    }
    
    public static double dotProduct(double[] a, double[] b){
        double sum = 0;
        
        for (int i = 0; i < a.length; i++)
            sum += a[i] * b[i];
        
        return sum;
    }
    
    public static double[] crossProduct(double[] a, double[] b){
        return new double[]{
            a[1] * b[2] - b[1] * a[2],
            a[2] * b[0] - b[2] * a[0],
            a[0] * b[1] - b[0] * a[1]
        };
    }
    
    
    
    public static void print(double[] a){
        for (int i = 0; i < a.length; i++)
            System.out.printf("[%.2f]\n", a[i]);
    }
}
