package com.Dialogs;

import com.PocketMoney.User;
import com.Frame.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.Frame.Swing.*;

public class ChangeUserDialog extends JDialog {
    Map<JMenuItem,JButton> ItemsButtons; // to get access to user by clicking menu item
    public ChangeUserDialog(){
        super(frame,"Users",true);
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension dimension=toolkit.getScreenSize();
        int height=300;
        int width=300;
        setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
        setBackground(Color.CYAN);
        ItemsButtons=new HashMap<>();
        // each button in toolbar contains information about its user
        JToolBar usersBar=new JToolBar("Users",JToolBar.VERTICAL);
        usersBar.setFloatable(false);
        usersBar.setBackground(Color.CYAN);

        JButton tempButton;
        String line;
        JPopupMenu popupMenu;
        JMenuItem changeNameItem,deleteItem;
        for (User user:users){
            line=user.getName()+"\n"+String.format("%.2f",user.getBalance())+" UAH";
            tempButton=new JButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
            usersBar.add(tempButton);
            popupMenu=new JPopupMenu();
            changeNameItem=new JMenuItem("change name");
            changeNameItem.addActionListener(e -> {
                String name=JOptionPane.showInputDialog(frame,"User name");
                boolean isError=false;
                if (name!=null && !(name.trim().equals("") && name.length()<=20)) {
                    for (User user1:users){
                        if (user1.getName().trim().equals(name.trim())){
                            JOptionPane.showMessageDialog(frame,"User with such name already exists!","Error",JOptionPane.ERROR_MESSAGE);
                            isError=true;
                            break;
                        }
                    }
                    if (!isError) {
                        // detect user whose name to change
                        JButton button = ItemsButtons.get(e.getSource());
                        User user1=users.get(usersBar.getComponentIndex(button));
                        user1.setName(name.trim());
                        if (usersBar.getComponentIndex(button)==0) nameLabel.setText(name); // make changes in dialog
                        // make changes in the frame
                        String line1 =user1.getName()+"\n"+String.format("%.2f",user1.getBalance())+" UAH";
                        button.setText("<html>" + line1.replaceAll("\\n", "<br>") + "</html>");
                        UsersChanger.writeUsersInFile(users);
                    }
                }
                else {
                    if (name!=null)
                        JOptionPane.showMessageDialog(frame,"Incorrect input!","Error",JOptionPane.ERROR_MESSAGE);
                }
            });
            deleteItem=new JMenuItem("delete");
            deleteItem.addActionListener(e -> {
                int option=JOptionPane.showConfirmDialog(frame,"Are you sure you want to delete this user?","Delete user",JOptionPane.YES_NO_OPTION);
                if (option==0){
                    JButton button = ItemsButtons.get(e.getSource());
                    users.remove(usersBar.getComponentIndex(button));
                    // remove all links to the user from other collections
                    ItemsButtons.remove(e.getSource());
                    ItemsButtons.remove(button.getComponentPopupMenu().getComponent(0));
                    usersBar.remove(button);
                    UsersChanger.writeUsersInFile(users);
                    revalidate();
                }
            });
            popupMenu.add(changeNameItem);
            popupMenu.add(deleteItem);
            tempButton.setComponentPopupMenu(popupMenu);
            tempButton.addActionListener(e -> {
                User newUser=users.get(usersBar.getComponentIndex((JButton)e.getSource()));
                // place new user in the beginning of list (to load him first next time program starts)
                users.remove(newUser);
                UsersChanger.pushForward(users,newUser);
                // make all necessary changes to the frame
                Swing.user=newUser;
                nameLabel.setText(newUser.getName());
                mainPanel.remove(accountsPanel);
                mainPanel.remove(categoriesPanel);
                mainPanel.remove(operationsPanel);
                accountsPanel= AccountsPanel.getPanel();
                accountsPanel.revalidate();
                categoriesPanel= CategoriesPanel.getPanel();
                categoriesPanel.revalidate();
                operationsPanel= OperationsPanel.getPanel();
                operationsPanel.revalidate();
                mainPanel.add(categoriesPanel,"categories");
                mainPanel.add(accountsPanel,"accounts");
                mainPanel.add(operationsPanel,"operations");
                categoriesButton.setEnabled(false);
                unableButton.setEnabled(true);
                unableButton=categoriesButton;
                frame.revalidate();
                UsersChanger.writeUsersInFile(users);
                dispose();
            });
            ItemsButtons.put(changeNameItem,tempButton);
            ItemsButtons.put(deleteItem,tempButton);
        }
        usersBar.getComponent(0).setEnabled(false);
        ((JButton)usersBar.getComponent(0)).getComponentPopupMenu().getComponent(1).setEnabled(false); // to forbid deleting this user
        // customize return button
        JButton backButton=new JButton("<html>" + "Back\n".replaceAll("\\n", "<br>") + "</html>");
        Font font=new Font("my font",Font.BOLD,14);
        backButton.setFont(font);

        usersBar.add(backButton);
        backButton.addActionListener(e -> dispose());
        // necessary if there are many users
        JScrollPane scrollPane=new JScrollPane(usersBar);
        add(scrollPane);
    }
}
