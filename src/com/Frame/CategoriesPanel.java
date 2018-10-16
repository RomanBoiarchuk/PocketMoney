package com.Frame;

import com.Components.CategoryButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CategoriesPanel {
    public static Map<String, JButton> CButtons; // category: button
    public static Map<JButton, String> BCategories; // button: category
    public static JToolBar CToolbar; // toolbar with category buttons
    static JButton addCategoryButton;
    public static Map<JMenuItem,String> ItemsCategories;
    public static Map<String, Double> categoriesMap; // category: sum of outgoings
    public static JPanel panel;

    public static JPanel getPanel() {
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
                        UsersChanger.writeUsersInFile(Swing.users);
                        JButton tempButton = new CategoryButton(name + ": 0.0 UAH", name);
                        JPanel tempPanel = new JPanel();
                        tempPanel.add(tempButton);
                        tempPanel.setBackground(Color.ORANGE);
                        CButtons.put(name, tempButton);
                        BCategories.put(tempButton, name);
                        categoriesMap.put(name,0d);
                        // remove addAccount button from toolbar and return it back after adding new account's button
                        JPanel addCategoryPanel = (JPanel) CToolbar.getComponentAtIndex(CToolbar.getComponentCount() - 1);
                        CToolbar.add(tempPanel);
                        CToolbar.add(addCategoryPanel);
                        panel.revalidate();
                    }
                }
            }
        });
        // add addCategoryButton to panel
        tempPanel = new JPanel();
        tempPanel.add(addCategoryButton);
        tempPanel.setBackground(Color.ORANGE);
        CToolbar.add(tempPanel);

        // add "Categories" label to panel
        tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        tempPanel.add(categoriesLabel, BorderLayout.WEST);
        tempPanel.setPreferredSize(new Dimension(250, 50));
        tempPanel.setBackground(Color.ORANGE);
        panel.add(tempPanel);

        // add scrollPane to toolbar
        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(300,350));
        scrollPane.setViewportView(CToolbar);
        panel.add(scrollPane, new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(30, 0, 0, 0), 0, 0));
        CToolbar.setBackground(Color.ORANGE);

        return panel;
    }
}
