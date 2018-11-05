package render_v3;

public class Local extends MatrixBase{
    double x;
    double y;
    double z;
    
    public Local(double x, double y, double z){
        super(MatrixF.identity());
        
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    
    public void setLocation(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    
    public void mov(double[] v, double n){
        mov(v[0], v[1], v[2], n);
    }
    
    public void mov(double x, double y, double z, double n){
        this.x += n * x;
        this.y += n * y;
        this.z += n * z;
    }
    
    public void movX(double n){
        mov(m[0][0], m[0][1], m[0][2], n);
    }
    
    public void movY(double n){
        mov(m[1][0], m[1][1], m[1][2], n);
    }
    
    public void movZ(double n){
        mov(m[2][0], m[2][1], m[2][2], n);
    }
    
    
    
    public void rotX(double a){
        rot(m[0][0], m[0][1], m[0][2], a);
    }
    
    public void rotY(double a){
        rot(m[1][0], m[1][1], m[1][2], a);
    }
    
    public void rotZ(double a){
        rot(m[2][0], m[2][1], m[2][2], a);
    }
    
    
    
    public double[][] transformMatrix(){
        double[][] n = new double[4][4];
        
        for (int j = 0; j < 3; j++)
            System.arraycopy(m[j], 0, n[j], 0, 3);
        
        double[] vec = {x, y, z, 1};
        n[3] = vec;
        
        return n;
    }
}
