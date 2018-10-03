package com.company;

import com.Components.OperationButton.IncomeButton;
import com.Components.OperationButton.OperationButton;
import com.Components.OperationButton.OutgoingButton;
import com.Components.OperationButton.TransferButton;
import com.PocketMoney.Income;
import com.PocketMoney.Operation;
import com.PocketMoney.Outgoing;
import com.PocketMoney.Transfer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OperationsPanel {
    public static JToolBar OToolbar; // Outgoings toolbar
    public static JToolBar IToolbar; // Incomes toolbar
    public static JToolBar TToolbar; // Transfers toolbar
    public static Map<Integer,JButton> OButtons; // OutgoingButton index: button
    public static Map<Integer,JButton> IButtons; // Income index: button
    public static Map<Integer,JButton> TButtons; // Transfer index: button
    public static Map<JMenuItem,Operation> ItemsOperations;
    public static Map<Operation,JButton> OperationsButtons;
    static JPanel getPanel(){

        JPanel panel=new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new GridBagLayout());
        IButtons=new HashMap<>();
        OButtons=new HashMap<>();
        TButtons=new HashMap<>();
        TToolbar=new JToolBar("Transfers");
        IToolbar=new JToolBar("Incomes");
        OToolbar=new JToolBar("Outgoings");
        TToolbar.setFloatable(false);
        IToolbar.setFloatable(false);
        OToolbar.setFloatable(false);
        OperationsButtons=new HashMap<>();
        ItemsOperations=new HashMap<>();

        JButton tempButton;
        Income income;
        Outgoing outgoing;
        Transfer transfer;
        String line;
        // toolbar with information about incomes
        for (int i=0;i<Swing.user.getIncomesSize();i++) {
            income=Swing.user.getIncome(i);
            line=income.getDate()+"\n"+"from \""+income.getSource()+"\" to \""+income.getAccount()+"\": "+String.format("%.2f",income.getSum())+" UAH";
            tempButton=new IncomeButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>", income);
            IToolbar.add(tempButton);
            IButtons.put(i,tempButton);
        }
        // decorating and adding income toolbar to the panel
        IToolbar.setBackground(Color.ORANGE);
        panel.add(new JLabel("Incomes"),new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JScrollPane IncomePane=new JScrollPane();
        IncomePane.setViewportView(IToolbar);
        IncomePane.setPreferredSize(new Dimension(100,100));
        IToolbar.setLayout(new GridLayout());
        panel.add(IncomePane,new GridBagConstraints(0,3,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(10,0,0,0),0,0));
        // toolbar with information about transfers
        for (int i=0;i<Swing.user.getTransfersSize();i++) {
            transfer=Swing.user.getTransfer(i);
            line=transfer.getDate()+"\n"+"from \""+transfer.getAccountOut()+"\" to \""+transfer.getAccountIn()+"\": "+String.format("%.2f",transfer.getSum())+" UAH";
            tempButton=new TransferButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>",transfer);
            TToolbar.add(tempButton);
            TButtons.put(i,tempButton);
        }
        // decorating and adding transfers toolbar to the panel
        TToolbar.setBackground(Color.ORANGE);
        panel.add(new JLabel("Transfers"),new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JScrollPane TransferPane=new JScrollPane();
        TransferPane.setViewportView(TToolbar);
        TransferPane.setPreferredSize(new Dimension(100,100));
        TToolbar.setLayout(new GridLayout());
        panel.add(TransferPane,new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(10,0,0,0),0,0));
        // toolbar with information about outgoings
        for (int i=0;i<Swing.user.getOutgoingsSize();i++) {
            outgoing=Swing.user.getOutgoing(i);
            line=outgoing.getDate()+"\n"+"from \""+outgoing.getAccount()+"\" to \""+outgoing.getGoal()+"\": "+String.format("%.2f",outgoing.getSum())+" UAH";
            tempButton=new OutgoingButton("<html>" + line.replaceAll("\\n", "<br>") + "</html>",outgoing);
            OToolbar.add(tempButton);
            OButtons.put(i,tempButton);
        }
        // decorating and adding outgoings toolbar to the panel
        OToolbar.setBackground(Color.ORANGE);
        panel.add(new JLabel("Outgoings"),new GridBagConstraints(0,4,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(30,0,0,0),0,0));
        JScrollPane OutgoingPane= new JScrollPane();
        OutgoingPane.setViewportView(OToolbar);
        OutgoingPane.setPreferredSize(new Dimension(250,100));
        OToolbar.setLayout(new GridLayout());
        panel.add(OutgoingPane,new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(10,0,0,0),0,0));
        return panel;
    }
}
