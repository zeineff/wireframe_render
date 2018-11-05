package render_v3;

public class Point{
    public static double[] sub(double[] a, double[] b){
        double[] c = new double[a.length];
        
        for (int i = 0; i < c.length; i++)
            c[i] = a[i] - b[i];
        
        return c;
    }
    
    public static double[] add(double[] a, double[] b){
        double[] c = new double[a.length];
        
        for (int i = 0; i < a.length; i++)
            c[i] = a[i] + b[i];
        
        return c;
    }
    
    public static double[] mul(double[] a, double b){
        double[] c = new double[a.length];
        
        for (int i = 0; i < c.length; i++)
            c[i] = a[i] * b;
        
        return c;
    }
    
    
    
    public static void print(double[] a){
        System.out.print("(");
        
        if (a.length > 0){
            System.out.printf("%.2f", a[0]);
            
            for (int i = 1; i < a.length; i++)
                System.out.printf(", %.2f", a[i]);
        }
        
        System.out.print(")\n");
    }
}
