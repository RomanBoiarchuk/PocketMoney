package com.company;

import com.Buttons.CategoryButton;
import com.PocketMoney.Outgoing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class categoriesPanel {
    public static Map<String, JButton> CButtons; // category: button
    static Map<JButton, String> BCategories; // button: category
    public static JToolBar CToolbar; // toolbar with category buttons
    static JButton addCategoryButton;
    public static Map<JMenuItem,String> ItemsCategories;
    static Map<String, Double> categoriesMap; // category: sum of outgoings
    public static JPanel panel;

    static JPanel getPanel() {
        //decorating panel
        panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new GridBagLayout());
        JLabel categoriesLabel = new JLabel("Categories");
        Dimension dimension = new Dimension(250, 50);
        // initializing static variables
        categoriesMap = Swing.user.getOutgoingsCategories();
        CButtons = new HashMap<>();
        BCategories = new HashMap<>();
        CToolbar = new JToolBar(null, JToolBar.VERTICAL);
        CToolbar.setFloatable(false);
        ItemsCategories=new HashMap<>();
        // creating toolbar with buttons that contain information about categories
        JButton tempButton;
        JPanel tempPanel;
        for (String category : categoriesMap.keySet()) {
            tempButton = new CategoryButton(category + ": " + String.format("%.2f",categoriesMap.get(category)) + " UAH", category);
            tempPanel = new JPanel();
            tempPanel.add(tempButton);
            tempPanel.setBackground(Color.ORANGE);
            CButtons.put(category, tempButton);
            BCategories.put(tempButton, category);
            CToolbar.add(tempPanel);
        }

        addCategoryButton = new JButton("Add new category");
        addCategoryButton.setPreferredSize(dimension);
        addCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Category name");
                if (name != null) {
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(Swing.frame, "The field is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        actionPerformed(e);
                    } else if (Swing.user.getOutgoingsCategories().containsKey(name)) {
                        JOptionPane.showMessageDialog(Swing.frame, "Category with such name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        actionPerformed(e);
                    } else {
                        Swing.user.addCategory(name);
                        Functions.writeUsersInFile(Swing.users);
                        JButton tempButton = new JButton(name + ": 0.0 UAH");
                        JPopupMenu popupMenu=new JPopupMenu();
                        JMenuItem deleteItem=new JMenuItem("delete");
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this category?",
                                        "Delete category",JOptionPane.YES_NO_OPTION);
                                if (option==0){
                                    Outgoing outgoing;
                                    String line;
                                    for (int i=0;i<Swing.user.getOutgoingsSize();i++){
                                        outgoing=Swing.user.getOutgoing(i);
                                        if (outgoing.getGoal().equals(ItemsCategories.get(e.getSource()))){
                                            outgoing.setUnknownCategory();
                                            line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.format("%.2f",outgoing.getSum())+" UAH";
                                            operationsPanel.OButtons.get(i).setText("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                                        }
                                    }
                                    CToolbar.remove(CButtons.get(ItemsCategories.get(e.getSource())).getParent());
                                    CToolbar.revalidate();
                                    JButton button=CButtons.get(ItemsCategories.get(e.getSource()));
                                    CButtons.remove(ItemsCategories.get(e.getSource()));
                                    Swing.user.deleteCategory(ItemsCategories.get(e.getSource()));
                                    for (int i=0;i<1;i++)
                                        ItemsCategories.remove(button.getComponentPopupMenu().getComponent(0));
                                    panel.revalidate();
                                    Functions.writeUsersInFile(Swing.users);
                                    JOptionPane.showMessageDialog(Swing.frame,"Category has been successfully deleted!");
                                }
                            }
                        });
                        popupMenu.add(deleteItem);
                        tempButton.setComponentPopupMenu(popupMenu);
                        JPanel tempPanel = new JPanel();
                        tempPanel.add(tempButton);
                        tempPanel.setBackground(Color.ORANGE);
                        tempButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                AddOutgoing addOutgoing = new AddOutgoing((JButton) e.getSource());
                                addOutgoing.setVisible(true);
                            }
                        });
                        tempButton.setPreferredSize(dimension);
                        CButtons.put(name, tempButton);
                        BCategories.put(tempButton, name);
                        JPanel addCategoryPanel = (JPanel) CToolbar.getComponentAtIndex(CToolbar.getComponentCount() - 1);
                        CToolbar.add(tempPanel);
                        CToolbar.add(addCategoryPanel);
                        ItemsCategories.put(deleteItem,name);
                        categoriesMap.put(name,0d);
                        panel.revalidate();
                    }
                }
            }
        });
        tempPanel = new JPanel();
        tempPanel.add(addCategoryButton);
        tempPanel.setBackground(Color.ORANGE);
        CToolbar.add(tempPanel);
        tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(categoriesLabel, BorderLayout.WEST);
        tempPanel.setPreferredSize(new Dimension(250, 50));
        tempPanel.setBackground(Color.ORANGE);
        panel.add(tempPanel);
        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(300,350));
        //scrollPane.setPreferredSize(scrollPane.getMinimumSize());
        scrollPane.setViewportView(CToolbar);
        panel.add(scrollPane, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(30, 0, 0, 0), 0, 0));
        CToolbar.setBackground(Color.ORANGE);

        return panel;
    }

    public static class AddOutgoing extends JDialog {
        public AddOutgoing(JButton button) {
            super(Swing.frame, "Outgoing", true);
            setLayout(new GridBagLayout());
            add(new JLabel("Account "), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            JComboBox<String> accountBox = new JComboBox<>();
            for (String key : Swing.user.getAccountsKeys()) {
                accountBox.addItem(key);
            }
            add(accountBox, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 20, 0, 0), 0, 0));
            add(new JLabel("Sum "), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(20, 0, 0, 0), 0, 0));
            JTextField sumField = new JTextField(20);
            add(sumField, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(20, 20, 0, 0), 0, 0));
            JTextArea comment = new JTextArea("Comment", 3, 5);
            comment.setLineWrap(true);
            JScrollPane commentPane = new JScrollPane(comment);
            add(commentPane, new GridBagConstraints(0, 2, 6, 3, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(20, 0, 0, 0), 100, 80));
            JButton confirm = new JButton("Confirm");
            JButton cancel = new JButton("Cancel");
            add(confirm, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(40, 0, 0, 0), 0, 0));
            add(cancel, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(40, 20, 0, 0), 0, 0));
            setBackground(Color.orange);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            int height = 300;
            int width = 500;
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String account = accountBox.getItemAt(accountBox.getSelectedIndex());
                    double sum = 0;
                    boolean isError = false;
                    try {
                        sum = Double.valueOf(sumField.getText().replaceAll(",", "."));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Swing.frame, "Incorrect input in sum field!", "Error", JOptionPane.ERROR_MESSAGE);
                        isError = true;
                    }
                    if (!isError) {
                        if (comment.getText() == null || comment.getText().equals("Comment"))
                            Swing.user.addOutgoing(sum, (String) BCategories.get(button), account);
                        else
                            Swing.user.addOutgoing(sum, (String) BCategories.get(button), account, comment.getText());
                        Outgoing outgoing = Swing.user.getOutgoing(Swing.user.getOutgoingsSize() - 1);
                        accountsPanel.AButtons.get(account).setText(account + ": " + String.format("%.2f",Swing.user.getAccountBalance(account)) + " UAH");
                        categoriesPanel.categoriesMap.replace(outgoing.getGoal(),categoriesPanel.categoriesMap.get(outgoing.getGoal())+outgoing.getSum());
                        button.setText(outgoing.getGoal() + ": " + String.format("%.2f",categoriesPanel.categoriesMap.get(outgoing.getGoal())) + " UAH");
                        accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                        String line = outgoing.getDate() + "\n" + "from \"" + outgoing.getAccount() + "\" to \"" + outgoing.getGoal() + "\": " + String.format("%.2f",outgoing.getSum()) + " UAH";
                        JButton operationButton = new JButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>");
                        JPopupMenu popupMenu=new JPopupMenu();
                        JMenuItem deleteItem=new JMenuItem("delete");
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int option=JOptionPane.showConfirmDialog(Swing.frame,"Are you sure you want to delete this operation?",
                                        "Delete operation",JOptionPane.YES_NO_OPTION);
                                if (option==0){
                                    Outgoing outgoing=(Outgoing) operationsPanel.ItemsOperations.get(e.getSource());
                                    Swing.user.deleteOutgoing(operationsPanel.OToolbar.getComponentIndex(operationsPanel.OperationsButtons.get(outgoing)));
                                    operationsPanel.OToolbar.remove(operationsPanel.OperationsButtons.get(outgoing));
                                    operationsPanel.OButtons.remove(outgoing);
                                    accountsPanel.balanceLabel.setText(String.format("%.2f",Swing.user.getBalance()));
                                    accountsPanel.AButtons.get(outgoing.getAccount()).setText(outgoing.getAccount()+": "+String.format("%.2f", Swing.user.getAccountBalance(outgoing.getAccount()))+" UAH");
                                    categoriesPanel.categoriesMap.replace(outgoing.getGoal(),categoriesPanel.categoriesMap.get(outgoing.getGoal())-outgoing.getSum());
                                    categoriesPanel.CButtons.get(outgoing.getGoal()).setText(outgoing.getGoal() + ": " + String.format("%.2f",categoriesPanel.categoriesMap.get(outgoing.getGoal())) + " UAH");
                                    operationsPanel.OToolbar.revalidate();
                                    Functions.writeUsersInFile(Swing.users);
                                }
                            }
                        });
                        popupMenu.add(deleteItem);
                        operationButton.setComponentPopupMenu(popupMenu);
                        operationButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                operationsPanel.OperationsDetails operationsDetails=new operationsPanel.OperationsDetails(Swing.user.getOutgoing(operationsPanel.OToolbar.getComponentIndex((JButton)e.getSource())));
                                operationsDetails.setVisible(true);
                            }
                        });
                        operationsPanel.OButtons.put(operationsPanel.OToolbar.getComponentCount(), operationButton);
                        operationsPanel.OToolbar.add(operationButton);
                        operationsPanel.ItemsOperations.put(deleteItem,outgoing);
                        operationsPanel.OperationsButtons.put(outgoing,operationButton);
                        JOptionPane.showMessageDialog(Swing.frame, "Outgoing has benn successfully added!");
                        Functions.writeUsersInFile(Swing.users);
                        dispose();
                    }
                }
            });
            setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height);
        }
    }
}
