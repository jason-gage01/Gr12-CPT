
package grade12cpt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import java.io.File;
import javax.imageio.ImageIO;



public class Map {
    
    public ArrayList<ArrayList<Integer>> map = new ArrayList<>();
    public final int B_WIDTH = Board.B_WIDTH;
    public final int B_HEIGHT = Board.B_HEIGHT;
    public final byte tile_size = 32;
    public static int MAP_Y, MAP_X = 0;
    public static int PIC_Y, PIC_X = 0;
    public static int tiledWidth, tiledHeight = 0;
    public static int mapWidth, mapHeight = 0;
    
    public Map(String file){
        init(file);
    }
    
    private void init(String file) {
        BufferedReader br = null;
        FileInputStream fis;
        String cvsSplitBy = ",";
        String line = "";
        tiledWidth = (int)(B_WIDTH / tile_size);
        tiledHeight = (int)(B_HEIGHT/ tile_size);
        
        try {
            
            fis = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fis));

            // Read the tile id's into map ArrayList
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                ArrayList<Integer> listValues= new ArrayList<>();
                
                // Create a list of the tile id's 
                for (int i = 0; i != values.length; i++) {
                    listValues.add(Integer.parseInt(values[i]));
                }
                // Add the list created above to the map ArrayList
                map.add(listValues);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }    
    
    public Image display(Graphics g, Player player) {
        int X_OFF = player.getX();
        switch (X_OFF + PIC_X) {
            case tile_size:
                X_OFF = 0;
                PIC_X = 0;
                MAP_X--;
                break;
            case -tile_size:
                X_OFF = 0;
                PIC_X = 0;
                MAP_X++;
                break;
            default:
                PIC_X += X_OFF;
                break;
        }
        
        int Y_OFF = 0;
        
        switch (Y_OFF + PIC_Y) {
            case tile_size:
                System.out.println("xxxx");
                Y_OFF = 0;
                PIC_Y = 0;
                MAP_Y--;
                break;
            case -tile_size:
                System.out.println("fffff");
                Y_OFF = 0;
                PIC_Y = 0;
                MAP_Y++;
                break;
            default:
                PIC_Y += Y_OFF;
                break;
        }
        
        String imageFile = "src/images/desert_sprite.png";
        BufferedImage background = new BufferedImage(B_WIDTH, B_HEIGHT, TYPE_3BYTE_BGR);
        BufferedImage sprites = null;
        BufferedImage temp;
        try {
            sprites = ImageIO.read(new File(imageFile));
        } catch (Exception e) {
            System.out.println(e);
        }       

        int tile_row = sprites.getWidth()/(tile_size + 1);
        

        //System.out.println(MAP_X + (B_WIDTH / tile_size));
        
        for (int y = MAP_Y; y != tiledHeight + MAP_Y + 3; y++ ){
            for (int x = MAP_X; x != tiledWidth + MAP_X + 3; x++) {
                int tile_id = 0;
                try {
                    tile_id = map.get(y).get(x);
                    if (tile_id < 0) tile_id = 38;
                } catch (Exception e) {
                    tile_id = 38;
                    
                }
                    
                int tile_x;
                int tile_y;
                
                // Find the x and y of the tile id in the sprite sheet
                tile_y = (tile_id % tile_row);
                tile_x = (int)(tile_id / tile_row);
                
                //adjust sizing     //The + 1 is to account for initial margin offset
                tile_x = tile_x * (tile_size + 1) + 1;
                tile_y = tile_y * (tile_size + 1) + 1;
                
                //Get the sprite from the sprite sheet based off the id's x and y
                temp = sprites.getSubimage(tile_y, tile_x, tile_size, tile_size);
                
                // Create the tile image in the final background image with appropriate offset
                background.createGraphics().drawImage(temp, tile_size * (x - MAP_X - 1) + PIC_X, 
                        tile_size * (y - MAP_Y - 1) + PIC_Y, null);
            }
        }
        
        

        
        
        
        return background;
    }
 

            
            
            
            
    // TODO Remove after testing maps
    public void publish() {
//        for (int x = 0; x != map.size(); x++){
//            for (int y = 0; y != map.size(); y++){
//                System.out.print(map.get(x).get(y) + " ");
//            }
//            System.out.println("");
//        }
        
        
    }
}

