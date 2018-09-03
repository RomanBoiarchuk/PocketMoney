package com.company;

import com.PocketMoney.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Swing {
    static List<User> users;
    static JButton unableButton; // to switch from one panel to another and set current panel button unable
    static User user; //current user
    static JFrame frame;
    // panels, frame
    static JPanel accountsPanel;
    static JPanel categoriesPanel;
    static JPanel operationsPanel;
    static JLabel nameLabel=new JLabel();
    static JPanel mainPanel;
    static JButton categoriesButton;
    public static void main(String[] args) {
        // looking for file with users
        try {
            users = Main.readUsersFromFile();
        }
        catch (Exception e){
            users=new ArrayList<>();
        }
        // check whether there are any users
        if (users.isEmpty()){
            JOptionPane.showMessageDialog(frame,"You don't have any user. Add one!");
            // add new user if there are no users yet
            if (!addUser()){
                System.exit(0);
            }

        }
        user=users.get(0);
        frame = Frame.getFrame();
        mainPanel=new JPanel();
        accountsPanel= com.company.accountsPanel.getPanel();
        categoriesPanel= com.company.categoriesPanel.getPanel();
        operationsPanel= com.company.operationsPanel.getPanel();
        CardLayout card=new CardLayout();
        mainPanel.setLayout(card);
        mainPanel.add(accountsPanel,"accounts");
        mainPanel.add(categoriesPanel,"categories");
        mainPanel.add(operationsPanel,"operations");
        JPanel southButtonsPanel=new JPanel();
        southButtonsPanel.setBackground(Color.CYAN);
        southButtonsPanel.setLayout(new GridLayout());
        JButton accountsButton=new JButton("Accounts");
        accountsButton.setBackground(Color.CYAN);
        categoriesButton=new JButton("Categories");
        categoriesButton.setBackground(Color.CYAN);
        JButton operationsButton=new JButton("Operations");
        operationsButton.setBackground(Color.CYAN);
        southButtonsPanel.add(accountsButton);
        southButtonsPanel.add(categoriesButton);
        southButtonsPanel.add(operationsButton);
        card.show(mainPanel,"categories");
        categoriesButton.setEnabled(false);
        unableButton=categoriesButton;
        JPanel panel=new JPanel();
        panel.add(southButtonsPanel);
        frame.add(panel,BorderLayout.SOUTH);
        panel.setBackground(Color.CYAN);
        frame.add(mainPanel,BorderLayout.CENTER);

        JPanel northPanel=new JPanel();
        northPanel.setBackground(Color.CYAN);
        nameLabel.setText(user.getName());
        JButton newUserButton=new JButton("New user");
        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        JButton changeUserButton=new JButton("Users");
        changeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeUserDialog changeUserDialog=new ChangeUserDialog();
                changeUserDialog.setVisible(true);
            }
        });
        newUserButton.setBackground(Color.CYAN);
        changeUserButton.setBackground(Color.CYAN);
        JPanel northButtonsPanel=new JPanel();
        northButtonsPanel.setLayout(new GridLayout());
        northButtonsPanel.add(nameLabel);
        northButtonsPanel.add(newUserButton);
        northButtonsPanel.add(changeUserButton);
        northButtonsPanel.setBackground(Color.CYAN);
        nameLabel.setBorder(new EmptyBorder(0,0,0,0));
        northButtonsPanel.setBorder(new EmptyBorder(0,0,0,0));
        northPanel.add(northButtonsPanel);
        frame.add(northPanel,BorderLayout.NORTH);
        categoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unableButton.setEnabled(true);
                card.show(mainPanel,"categories");
                unableButton=categoriesButton;
                categoriesButton.setEnabled(false);
            }
        });
        accountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unableButton.setEnabled(true);
                card.show(mainPanel,"accounts");
                unableButton=accountsButton;
                accountsButton.setEnabled(false);
            }
        });
        operationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unableButton.setEnabled(true);
                card.show(mainPanel,"operations");
                unableButton=operationsButton;
                operationsButton.setEnabled(false);
            }
        });
        frame.setVisible(true);
    }
    static boolean addUser() {
        String name = JOptionPane.showInputDialog(frame,"User name");
        if (name != null) {
            boolean isError = false;
            if (name.trim().equals("")) {
                JOptionPane.showMessageDialog(frame, "Incorrect input!", "Error", JOptionPane.ERROR_MESSAGE);
                isError = true;
            } else {
                for (User user : users) {
                    if (user.getName().trim().equals(name.trim())) {
                        JOptionPane.showMessageDialog(frame, "User with such name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        isError = true;
                        break;
                    }
                }
            }
            if (isError) {
                return addUser();
            }
            else {
                Main.pushForward(users, new User(name));
                user = users.get(0);
                try{
                    mainPanel.remove(accountsPanel);
                    mainPanel.remove(categoriesPanel);
                    mainPanel.remove(operationsPanel);
                } catch (Exception e){}
                accountsPanel = com.company.accountsPanel.getPanel();
                categoriesPanel = com.company.categoriesPanel.getPanel();
                operationsPanel = com.company.operationsPanel.getPanel();
                try{
                    mainPanel.add(categoriesPanel,"categories");
                    mainPanel.add(accountsPanel,"accounts");
                    mainPanel.add(operationsPanel,"operations");
                } catch (Exception e){}
                nameLabel.setText(user.getName());
                if (unableButton!=null) {
                    unableButton.setEnabled(true);
                    unableButton = categoriesButton;
                    unableButton.setEnabled(false);
                }
                if (frame != null)
                    frame.revalidate();
                Main.writeUsersInFile(users);
                JOptionPane.showMessageDialog(frame, "User has been successfully added!");
                return true;
            }
        }
        else
            return false;
    }
    static class ChangeUserDialog extends JDialog{
        static Map<JMenuItem,JButton> ItemsButtons;
        public ChangeUserDialog(){
            super(frame,"Users",true);
            Toolkit toolkit=Toolkit.getDefaultToolkit();
            Dimension dimension=toolkit.getScreenSize();
            int height=300;
            int width=300;
            setBounds((dimension.width-width)/2,(dimension.height-height)/2,width,height);
            setBackground(Color.CYAN);
            ItemsButtons=new HashMap<>();
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
                    if (!name.trim().equals("") && name.length()<=20) {
                        for (User user1:users){
                            if (user1.getName().trim().equals(name.trim())){
                                JOptionPane.showMessageDialog(frame,"User with such name already exists!","Error",JOptionPane.ERROR_MESSAGE);
                                isError=true;
                                break;
                            }
                        }
                        if (!isError) {
                            JButton button = ItemsButtons.get(e.getSource());
                            User user1=users.get(usersBar.getComponentIndex(button));
                            user1.setName(name.trim());
                            if (usersBar.getComponentIndex(button)==0) nameLabel.setText(name);
                            String line1 =user1.getName()+"\n"+String.format("%.2f",user1.getBalance())+" UAH";
                            button.setText("<html>" + line1.replaceAll("\\n", "<br>") + "</html>");
                            Main.writeUsersInFile(users);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(frame,"Incorrect input!","Error",JOptionPane.ERROR_MESSAGE);
                    }
                });
                deleteItem=new JMenuItem("delete");
                deleteItem.addActionListener(e -> {
                    int option=JOptionPane.showConfirmDialog(frame,"Are you sure you want to delete this user?","Delete user",JOptionPane.YES_NO_OPTION);
                    if (option==0){
                        JButton button = ItemsButtons.get(e.getSource());
                        users.remove(usersBar.getComponentIndex(button));
                        ItemsButtons.remove(e.getSource());
                        ItemsButtons.remove(button.getComponentPopupMenu().getComponent(0));
                        usersBar.remove(button);
                        Main.writeUsersInFile(users);
                        revalidate();
                    }
                });
                popupMenu.add(changeNameItem);
                popupMenu.add(deleteItem);
                tempButton.setComponentPopupMenu(popupMenu);
                tempButton.addActionListener(e -> {
                    User newUser=users.get(usersBar.getComponentIndex((JButton)e.getSource()));
                    users.remove(newUser);
                    Main.pushForward(users,newUser);
                    Swing.user=newUser;
                    nameLabel.setText(newUser.getName());
                    mainPanel.remove(accountsPanel);
                    mainPanel.remove(categoriesPanel);
                    mainPanel.remove(operationsPanel);
                    accountsPanel= com.company.accountsPanel.getPanel();
                    accountsPanel.revalidate();
                    categoriesPanel= com.company.categoriesPanel.getPanel();
                    categoriesPanel.revalidate();
                    operationsPanel= com.company.operationsPanel.getPanel();
                    operationsPanel.revalidate();
                    mainPanel.add(categoriesPanel,"categories");
                    mainPanel.add(accountsPanel,"accounts");
                    mainPanel.add(operationsPanel,"operations");
                    categoriesButton.setEnabled(false);
                    unableButton.setEnabled(true);
                    unableButton=categoriesButton;
                    frame.revalidate();
                    Main.writeUsersInFile(users);
                    dispose();
                });
                ItemsButtons.put(changeNameItem,tempButton);
                ItemsButtons.put(deleteItem,tempButton);
            }
            usersBar.getComponent(0).setEnabled(false);
            ((JButton)usersBar.getComponent(0)).getComponentPopupMenu().getComponent(1).setEnabled(false);
            JButton backButton=new JButton("<html>" + "Back\n".replaceAll("\\n", "<br>") + "</html>");
            Font font=new Font("my font",Font.BOLD,14);
            backButton.setFont(font);
            usersBar.add(backButton);
            backButton.addActionListener(e -> dispose());
            JScrollPane scrollPane=new JScrollPane(usersBar);
            add(scrollPane);
        }
    }
}
