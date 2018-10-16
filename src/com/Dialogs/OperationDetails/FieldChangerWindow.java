package com.Dialogs.OperationDetails;

import com.Frame.Swing;

import javax.swing.*;
import java.awt.*;

class FieldChangerWindow extends JDialog {
    FieldChangerWindow(String title){
        super(Swing.frame,title,true);
        Toolkit toolkit1 =Toolkit.getDefaultToolkit();
        Dimension dimension1 = toolkit1.getScreenSize();
        int height1 =200;
        int width1 =300;
        setBounds((dimension1.width- width1)/2,(dimension1.height- height1)/2, width1, height1);
        setLayout(new GridBagLayout());
    }
}
