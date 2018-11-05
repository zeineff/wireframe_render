package render_v3;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.ArrayList;

public class ReadModel{
    double[][] verts;
    int[][] tris;
    double[][] normals;
    
    public ReadModel(String file){
        int index = file.lastIndexOf('.');
        String fileType = file.substring(index + 1);
        
        if (fileType.equals("obj")){
            BufferedReader br = null;

            try{
                FileReader fr = new FileReader(file);
                br = new BufferedReader(fr);
                String line;

                ArrayList<Double[]> vertices = new ArrayList<>();
                ArrayList<Integer[]> faces = new ArrayList<>();
                ArrayList<Double[]> norms = new ArrayList<>();

                while ((line = br.readLine()) != null){
                    line = filterString(line);
                    String[] segments = line.split(" ");
                    String type = segments[0];

                    if (type.length() > 0 && !segments[0].equals("vt"))
                        switch(type.charAt(0)){
                            case 'v':  // Vertice
                                Double[] vert = new Double[3];

                                for (int i = 0; i < 3; i++)
                                    vert[i] = Double.parseDouble(segments[i + 1]);

                                if (type.equals("v"))
                                    vertices.add(vert);
                                else if (type.equals("vn"))
                                    norms.add(vert);

                                break;
                            case 'f':  // Face
                                Integer[] face = new Integer[3];

                                for (int i = 0; i < 3; i++){
                                    String s = segments[i + 1];
                                    s = s.substring(0, s.indexOf('/'));
                                    String[] parts = s.split("/");

                                    face[i] = Integer.parseInt(parts[0]);
                                }

                                faces.add(face);
                                break;
                        }
                }

                verts = new double[vertices.size()][3];
                tris = new int[faces.size()][3];
                normals = new double[norms.size()][3];

                for (int i = 0; i < verts.length; i++)
                    for (int j = 0; j < verts[i].length; j++)
                        verts[i][j] = vertices.get(i)[j];

                for (int i = 0; i < tris.length; i++)
                    for (int j = 0; j < 3; j++)
                        tris[i][j] = faces.get(i)[j] - 1;

                for (int i = 0; i < normals.length; i++)
                    for (int j = 0; j < 3; j++)
                        normals[i][j] = norms.get(i)[j];

            }catch (IOException e){
                System.out.println("File not found");
            }finally{
                try{
                    if (br != null)
                        br.close();
                }catch (IOException e){
                    System.out.println("Failed to close file reader");
                }
            }
        }else{
            System.out.println("File type not recognized");
        }
    }
    
    private static String filterString(String s){
        if (s.length() > 0){
            for (int i = 0; i < s.length() - 1; i++){
                char a = s.charAt(i);
                char b = s.charAt(i + 1);

                if (a == ' ' && b == ' '){
                    String start = s.substring(0, i);
                    String end = s.substring(i + 1, s.length());
                    s = start + end;
                    i--;
                }
            }

            if (s.charAt(0) == ' ')
                s = s.substring(1);

            if (s.charAt(s.length() - 1) == ' ')
                s = s.substring(0, s.length() - 1);
        }
        
        return s;
    }
}
