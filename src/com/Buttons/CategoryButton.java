package com.Buttons;

import com.PocketMoney.Outgoing;
import com.company.Functions;
import com.company.Swing;
import com.company.categoriesPanel;
import com.company.operationsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryButton extends JButton {
    public CategoryButton(String text,String category){
        super(text);
        Dimension dimension = new Dimension(250, 50);
        setPreferredSize(dimension);
        JPopupMenu popupMenu=new JPopupMenu();
        JMenuItem deleteItem=new JMenuItem("delete");
        deleteItem.addActionListener(e -> {
            int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this category?",
                    "Delete category",JOptionPane.YES_NO_OPTION);
            if (option==0){
                Outgoing outgoing;
                String line;
                for (int i=0;i<Swing.user.getOutgoingsSize();i++){
                    outgoing=Swing.user.getOutgoing(i);
                    if (outgoing.getGoal().equals(categoriesPanel.ItemsCategories.get(e.getSource()))){
                        outgoing.setUnknownCategory();
                        line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.format("%.2f",outgoing.getSum())+" UAH";
                        operationsPanel.OButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                    }
                }
                categoriesPanel.CToolbar.remove(categoriesPanel.CButtons.get(categoriesPanel.ItemsCategories.get(e.getSource())).getParent());
                categoriesPanel.CToolbar.revalidate();
                JButton button=categoriesPanel.CButtons.get(categoriesPanel.ItemsCategories.get(e.getSource()));
                categoriesPanel.CButtons.remove(categoriesPanel.ItemsCategories.get(e.getSource()));
                Swing.user.deleteCategory(categoriesPanel.ItemsCategories.get(e.getSource()));
                for (int i=0;i<1;i++)
                    categoriesPanel.ItemsCategories.remove(button.getComponentPopupMenu().getComponent(0));
                categoriesPanel.panel.revalidate();
                Functions.writeUsersInFile(Swing.users);
                JOptionPane.showMessageDialog(Swing.frame,"Category has been successfully deleted!");
            }
        });
        popupMenu.add(deleteItem);
        setComponentPopupMenu(popupMenu);
        addActionListener(e-> {
                categoriesPanel.AddOutgoing addOutgoing = new categoriesPanel.AddOutgoing((JButton) e.getSource());
                addOutgoing.setVisible(true);
        });
        categoriesPanel.ItemsCategories.put(deleteItem,category);
    }
}
