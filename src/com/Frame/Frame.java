package com.Frame;

import javax.swing.*;
import java.awt.*;

public class Frame {
    static JFrame getFrame(){
        JFrame jFrame=new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension dimension=toolkit.getScreenSize();
        jFrame.setTitle("Pocket money");
        int height=600;
        int width=600;
        jFrame.setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
        return jFrame;
    }
}
