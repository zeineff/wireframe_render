package render_v3;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Draw extends JFrame{
    public static void main(String[] args){
        a();
    }
    
    public static void a(){
        Model spyro = new Model(2, 0, 5, "models\\Spyro.obj");
        Cube cube_01 = new Cube(2, -2, 5, 1);
        spyro.rotY(180);
        
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(spyro);
        shapes.add(cube_01);
        
        int w = 1600;
        int h = 900;
        Draw a = new Draw(w, h, shapes);
    }
    
    
    
    Render render;
    Camera cam;
    ArrayList<Shape> shapes;
    Container cp = super.getContentPane();
    boolean disableShadowing = false;
    
    public Draw(int w, int h, ArrayList objects){
        super.setVisible(true);
        super.setSize(w, h);
        super.setTitle("Render");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.shapes = objects;
        
        render = new Render();
        cam = new Camera(2, 1, -5.213, 100, (double) h / w);
                
        cp.add(render);
        super.addKeyListener(new Controls(render, cam));
        super.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        super.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                cam.setAspectRatio((double) getHeight() / getWidth());
            }
        });
    }
    
    public class Render extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
           
            g2.setBackground(Color.BLACK);
            g2.clearRect(0, 0, super.getWidth(), super.getHeight());
            g2.setColor(new Color(192, 0, 0));
            
            double[][] m = MatrixF.inverse(cam.transformMatrix());
            
            shapes.stream().forEach((s) -> {
                double[][] toCamWorld = MatrixF.mul(m, s.transformMatrix());
                double[][] camVerts = MatrixF.mul2(toCamWorld, s.verts());
                
                for (int[] face:s.faces()){
                    double[] a = camVerts[face[0]];  // Vertice 01
                    double[] b = camVerts[face[1]];  // Vertice 02
                    double[] c = camVerts[face[2]];  // Vertice 03
                    
                    double[] normal = Triangle.normal(a, b, c);  // Face Normal
                    
                    if (Vector.dotProduct(normal, a) < 0){  // If face visible
                        double[][][] tris = new double[][][]{{a, b, c}};
                        
                        for (double[][] plane:cam.frustrum)
                            if (plane[0] != null)
                                tris = clip(tris, plane[0], plane[1]);
                        
                        for (double[][] tri:tris){
                            int[][] r = new int[3][2];  // Screen coordinates

                            for (int i = 0; i < 3; i++){
                                double[] p = new double[2];

                                // Perspective Divide
                                double factor = cam.d / tri[i][2];
                                p[0] = tri[i][0] * factor;
                                p[1] = tri[i][1] * factor;

                                // Normalization
                                p[0] = p[0] / cam.w + 0.5;
                                p[1] = p[1] / cam.h + 0.5;

                                // Rasterization
                                r[i][0] = (int) Math.floor(p[0] * super.getWidth());
                                r[i][1] = (int) Math.floor((1 - p[1]) * super.getHeight());
                            }

                            g2.drawLine(r[0][0], r[0][1], r[1][0], r[1][1]);
                            g2.drawLine(r[1][0], r[1][1], r[2][0], r[2][1]);
                            g2.drawLine(r[2][0], r[2][1], r[0][0], r[0][1]);
                        }
                    }
                }
            });
        }
        
        public double[][][] clip(double[][][] tris, double[] pNormal, double[] pPoint){
            int[] visP = new int[tris.length];  // Number of visible vertices for each triangle
            int neededTris = 0;  // Number of triangles needed to clip current triangle
            int clippedTris = 0;
            
            for (int i = 0; i < tris.length; i++){
                double[][] tri = tris[i];
                
                int visible = visiblePoints(tri, pNormal, pPoint);
                neededTris += visible;
                
                if (visible == 3)
                    neededTris -= 2;
                
                if (visible == 1 || visible == 2)
                    clippedTris += 2;
                
                visP[i] = visible;
            }
            
            double[][][] newTris = new double[neededTris][3][3];
            double[][] newPoints = new double[clippedTris][3];
            int k = 0;
            int j = 0;
            
            for (int i = 0; i < tris.length; i++){
                double[][] tri = tris[i];
                int visiblePoints = visP[i];

                switch (visiblePoints){
                    case 3:
                        newTris[k] = tri;
                        k++;
                        break;
                    case 1:
                    case 2:
                        order(tri, pNormal, pPoint);  // Visible points first

                        double[] a = tri[0];  // Vertice 01
                        double[] b = tri[1];  // Vertice 02
                        double[] c = tri[2];  // Vertice 03

                        double[] linePoint = (visiblePoints == 2 ? c : a);
                        double[] line01Vector = Point.sub(a, c);
                        double[] line02Vector = Point.sub(b, linePoint);
                        double numerator = Vector.dotProduct(Point.sub(pPoint, linePoint), pNormal);
                        double denominator = Vector.dotProduct(line01Vector, pNormal);
                        double d = numerator / denominator;
                        double[] x = Point.add(Point.mul(line01Vector, d), linePoint);  // Plane intersection point 01

                        denominator = Vector.dotProduct(line02Vector, pNormal);
                        d = numerator / denominator;
                        double[] y = Point.add(Point.mul(line02Vector, d), linePoint);  // Plane intersection point 02

                        newPoints[j] = x;
                        j++;
                        newPoints[j] = y;
                        j++;
                        
                        if (visiblePoints == 2){
                            newTris[k] = new double[][]{a, b, x};
                            k++;
                            newTris[k] = new double[][]{b, y, x};
                        }else
                            newTris[k] = new double[][]{a, y, x};
                        
                        k++;
                }
            }
            
            return newTris;
        }
        
        public int visiblePoints(double[][] tri, double[] pNormal, double[] pPoint){
            int count = 0;
            
            for (double[] p:tri)
                if (Vector.dotProduct(Point.sub(p, pPoint), pNormal) > 0)
                    count++;
            
            return count;
        }
        
        public void order(double[][] tri, double[] pNormal, double[] pPoint){
            while (!isOrdered(tri, pNormal, pPoint)){
                double[] a = tri[0];
                tri[0] = tri[1];
                tri[1] = tri[2];
                tri[2] = a;
            }
        }
        
        public boolean isOrdered(double[][] tri, double[] pNormal, double[] pPoint){
            boolean visible = true;
            
            for (double[] p:tri){
                double x = Vector.dotProduct(Point.sub(p, pPoint), pNormal);
                
                if (visible){
                    if (x <= 0)
                        visible = false;
                }else if (x > 0)
                    return false;
            }
            
            return true;
        }
    }
}

class Foo<X>{
    X x;
    public Foo(X x){
        this.x = x;
    }
}