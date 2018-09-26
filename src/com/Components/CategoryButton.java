package com.Components;

import com.Dialogs.AddOutgoing;
import com.PocketMoney.Outgoing;
import com.company.UsersChanger;
import com.company.Swing;
import com.company.CategoriesPanel;
import com.company.OperationsPanel;

import javax.swing.*;
import java.awt.*;


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
                    if (outgoing.getGoal().equals(CategoriesPanel.ItemsCategories.get(e.getSource()))){
                        outgoing.setUnknownCategory();
                        line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.format("%.2f",outgoing.getSum())+" UAH";
                        OperationsPanel.OButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                    }
                }
                CategoriesPanel.CToolbar.remove(CategoriesPanel.CButtons.get(CategoriesPanel.ItemsCategories.get(e.getSource())).getParent());
                CategoriesPanel.CToolbar.revalidate();
                JButton button=CategoriesPanel.CButtons.get(CategoriesPanel.ItemsCategories.get(e.getSource()));
                CategoriesPanel.CButtons.remove(CategoriesPanel.ItemsCategories.get(e.getSource()));
                Swing.user.deleteCategory(CategoriesPanel.ItemsCategories.get(e.getSource()));
                for (int i=0;i<1;i++)
                    CategoriesPanel.ItemsCategories.remove(button.getComponentPopupMenu().getComponent(0));
                CategoriesPanel.panel.revalidate();
                UsersChanger.writeUsersInFile(Swing.users);
                JOptionPane.showMessageDialog(Swing.frame,"Category has been successfully deleted!");
            }
        });
        popupMenu.add(deleteItem);
        setComponentPopupMenu(popupMenu);
        addActionListener(e-> {
                AddOutgoing addOutgoing = new AddOutgoing((JButton) e.getSource());
                addOutgoing.setVisible(true);
        });
        CategoriesPanel.ItemsCategories.put(deleteItem,category);
    }
}
