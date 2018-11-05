package render_v3;

public class Triangle{
    public static double[] normal(double[] a, double[] b, double[] c){
        double[] x = {b[0] - a[0], b[1] - a[1], b[2] - a[2]};
        double[] y = {c[0] - a[0], c[1] - a[1], c[2] - a[2]};
        
        return Vector.crossProduct(x, y);
    }
}
