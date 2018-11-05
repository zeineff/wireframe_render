package render_v3;

public class Camera extends Local{
    double d = 2;
    double w, h, fov, aspectRatio;
    
    double[][] close = new double[2][];
    double[][] far = new double[2][];
    double[][] left = new double[2][];
    double[][] right = new double[2][];
    double[][] up = new double[2][];
    double[][] down = new double[2][];
    double[][][] frustrum = {close, far, left, right, up, down};
    
    public Camera(double x, double y, double z, double fov, double aspectRatio){
        super(x, y, z);
        
        this.fov = Math.toRadians(fov);
        this.aspectRatio = aspectRatio;
        
        update();
    }
    
    public Camera(double aspectRatio){
        this(0, 0, 0, 100, aspectRatio);
    }
    
    
    
    public void setFov(double fov){
        this.fov = Math.toRadians(fov);
        update();
    }
    
    public void setAspectRatio(double aspectRatio){
        this.aspectRatio = aspectRatio;
        update();
    }
    
    private void setFrustrum(){
        close[0] = new double[]{0, 0, 1};
        close[1] = new double[]{0, 0, 1};
        //far[0] = new double[]{0, 0, -1};
        //far[1] = new double[]{0, 0, 10};
        
        /**
        double[] v1 = {-1, 0, 1 / Math.cos((Math.PI - fov) / 2)};
        double[] v2 = {-1, 1, 1 / Math.cos((Math.PI - fov) / 2)};
        v1 = Point.mul(v1, -1);
        left[0] = Vector.crossProduct(v1, v2);
        Vector.print(v1);
        Vector.print(v2);
        Vector.print(left[0]);
        **/
        
        for (int i = 2; i < frustrum.length; i++)
            frustrum[i][1] = new double[]{0, 0, 0};
    }
        
    
    private void update(){
        w = 2 * Math.tan(fov / 2) * d;
        h = w * aspectRatio;
        setFrustrum();
    }
}
