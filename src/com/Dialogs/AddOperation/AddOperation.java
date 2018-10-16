package com.Dialogs.AddOperation;

import com.Components.CommentArea;
import com.Frame.Swing;

import javax.swing.*;
import java.awt.*;

abstract class AddOperation extends JDialog {
    JTextArea comment;
    JTextField sumField;
    AddOperation(String title){
        super(Swing.frame,title,true);
        setBackground(Color.orange);
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension dimension=toolkit.getScreenSize();
        int height=300;
        int width=500;
        setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
        setLayout(new GridBagLayout());
        add(new JLabel("Sum "),new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(20,0,0,0),0,0));
        sumField=new JTextField(20);
        add(sumField,new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(20,20,0,0),0,0));
        comment=new CommentArea("Comment",3,5);
        JScrollPane commentPane=new JScrollPane(comment);
        add(commentPane,new GridBagConstraints(0,2,6,3,0,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,
                new Insets(20,0,0,0),100,80));
    }
}
