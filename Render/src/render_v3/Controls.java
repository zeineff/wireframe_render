package render_v3;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import render_v3.Draw.Render;

public class Controls extends KeyAdapter{
    Render render;
    Camera cam;
    
    double cam_speed = 0.25;
    double cam_rot_speed = 1;
    
    boolean w = false;
    boolean a = false;
    boolean s = false;
    boolean d = false;
    boolean q = false;
    boolean e = false;
    
    public Controls(Render render, Camera cam){
        this.render = render;
        this.cam = cam;
    }
    
    @Override
    public void keyReleased(KeyEvent evt){
        switch (evt.getKeyCode()){
            case KeyEvent.VK_W:
                w = false;
                break;
            case KeyEvent.VK_A:
                a = false;
                break;
            case KeyEvent.VK_S:
                s = false;
                break;
            case KeyEvent.VK_D:
                d = false;
                break;
            case KeyEvent.VK_Q:
                q = false;
                break;
            case KeyEvent.VK_E:
                e = false;
                break;
        }
        
        update();
    }
    
    @Override
    public void keyPressed(KeyEvent evt){
        switch (evt.getKeyCode()){
            case KeyEvent.VK_W:
                w = true;
                break;
            case KeyEvent.VK_A:
                a = true;
                break;
            case KeyEvent.VK_S:
                s = true;
                break;
            case KeyEvent.VK_D:
                d = true;
                break;
            case KeyEvent.VK_Q:
                q = true;
                break;
            case KeyEvent.VK_E:
                e = true;
                break;
            case KeyEvent.VK_UP:
                cam.rotX(-cam_rot_speed);
                break;
            case KeyEvent.VK_DOWN:
                cam.rotX(cam_rot_speed);
                break;
            case KeyEvent.VK_LEFT:
                cam.rotY(-cam_rot_speed);
                break;
            case KeyEvent.VK_RIGHT:
                cam.rotY(cam_rot_speed);
                break;
            case KeyEvent.VK_NUMPAD5:
                cam.setFov(100);
                break;
            case KeyEvent.VK_NUMPAD6:
                cam.setFov(Math.toDegrees(cam.fov) + 1);
                break;
            case KeyEvent.VK_NUMPAD4:
                cam.setFov(Math.toDegrees(cam.fov) - 1);
                break;
            case KeyEvent.VK_ADD:
                cam_speed *= 2;
                break;
            case KeyEvent.VK_SUBTRACT:
                cam_speed /= 2;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
            
        update();
    }
    
    private void update(){
        if (w)
            cam.movZ(cam_speed);
        if (a)
            cam.movX(-cam_speed);
        if (s)
            cam.movZ(-cam_speed);
        if (d)
            cam.movX(cam_speed);
        if (q)
            cam.movY(cam_speed);
        if (e)
            cam.movY(-cam_speed);

        render.repaint();
    }
}
