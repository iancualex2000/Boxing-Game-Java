/*;
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package boxinggame;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author iancu
 */
public class BoxingGame extends JFrame {
    int gloveX = 150,rect_bar_health=100,current_power = 0,pressed_time = 0,delay=1000,trophyX=800,trophyY=800,power_saver=0;
    float current_health=100,max_health=100,percentage=(current_health/max_health)*100,max_power = 300,power_level_bar = 300,power_percentage= 0;
    Timer stopwatch;
    
    
    JButton startAgain = new JButton("Start Again");
    JPanel drawingArea = new JPanel(){
        {
            setBackground(Color.GRAY);
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
            ImageIcon punchingBox = new ImageIcon("src/Images/punchingBag.png");
            punchingBox.paintIcon(this,g,300,150);
            ImageIcon boxGlove = new ImageIcon("src/Images/boxGlove.png");
            ImageIcon trophy = new ImageIcon("src/Images/trophy.png");
            boxGlove.paintIcon(this, g, gloveX, 300);
            trophy.paintIcon(this, g, trophyX,trophyY);
            String healthString = "Health: ";
            String healthPercentage = percentage+"%";
            String powerPercentage = power_percentage+"%";
            Font f = new Font("Arial",Font.BOLD,20);
            g.setFont(f);
            g.drawString(healthString,25,37);
            g.drawString(healthPercentage,205,37);
            g.drawRect(100, 20, 100, 20);
            g.setColor(Color.red);
            g.fillRect(100, 20, rect_bar_health, 20);
            g.drawString(powerPercentage, 475, 110);
            g.drawRect(480, 130, 20, 300);
            g.setColor(Color.green);
            g.fillRect(480, 130, 20, current_power);
            
            
        }
    };
    
    
    BoxingGame(){
        setSize(600,500);
        setLayout(new BorderLayout());
        
        add(drawingArea,BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        class BoxingKeyListener extends KeyAdapter{
            @Override
            public void keyPressed(KeyEvent e){
                int code = e.getKeyCode();
                
                if(code == KeyEvent.VK_SPACE&&current_power<300){
                    gloveX=150;
                    current_power+=30;
                }else if(code==KeyEvent.VK_R){
                    restart();
                }
                
                power_saver=current_power;
                power_level_bar=current_power;
                power_percentage=(power_level_bar/max_power)*300;

                drawingArea.repaint();
            }
            @Override
            public void keyReleased(KeyEvent e){
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_SPACE){
                    for(int i =0;i<=current_power;i++){
                        current_power-=30;

                    }
                    gloveX+=60;
                }
                power_percentage = 0;
                
                handleCollision();
                changeHealthBar();
                if(youWon()){ trophyX=100;trophyY=100;}
                drawingArea.repaint();
                
                
            }
            
            
            
        }
      
        addKeyListener(new BoxingKeyListener());
        
        
    }
    
    
    public void restart(){
        if(current_health==0){
            current_health=100;
            percentage=(current_health/max_health)*100;
            rect_bar_health=100;
            trophyX=800;
            trophyY=800;
        }
    }
    public boolean youWon(){
        boolean winner = false;
        if(percentage==0){
            winner = true;
        }
        return winner;
        
    }
    
    public void changeHealthBar(){
        
        if(handleCollision()&&current_health>0){
            if(power_saver>0 && power_saver<=150 && rect_bar_health!=0){
                rect_bar_health -= 25;
            }else if(power_saver>150 && power_saver<=300 && rect_bar_health!=0){
                rect_bar_health -= 50;
   
            }
            current_health = rect_bar_health;
            percentage=(current_health/max_health)*100;
            if(percentage<=0){
                percentage=0;
            }
            
        }
        
    }
    
    public boolean handleCollision(){
        boolean handle = false;
        if(gloveX==210){
            handle = true;
        }
        return handle;
    }
    
    
    
    
    
    public static void main(String[] args) {
        
        new BoxingGame();
    }
    
}
