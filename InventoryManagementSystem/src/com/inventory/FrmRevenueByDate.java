package com.inventory;

import javax.swing.*;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.*;

public class FrmRevenueByDate extends JDialog{
	//Start create variables
	JButton JBok = new JButton("OK");
	//JButton JBCancel = new JButton("Cancel",new ImageIcon("images/cancel.png"));

	JLabel JLPic1 = new JLabel(new ImageIcon("images/bSearch.png"));
	JLabel JLBanner = new JLabel("Select date to view records");
	JPanel JPDialogContainer = new JPanel();

	//JLabel JLSearchFor = new JLabel("Search For:");
	//JLabel JLSearchIn = new JLabel("Look In:");

	//JTextField JTFSearchFor = new JTextField();

	//JComboBox JCSearchIn;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();

	public FrmRevenueByDate(JFrame OwnerForm){
		super(OwnerForm,true);
	    setTitle("Search Records By Date");

		/*String StrListItem[]={"SalesRep ID","Name"};
		JCSearchIn = new JComboBox(StrListItem);
		StrListItem = null;*/

		JPDialogContainer.setLayout(null);

		JLPic1.setBounds(25,5,32,32);
		JPDialogContainer.add(JLPic1);

		
		JLBanner.setBounds(65,5,280,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPDialogContainer.add(JLBanner);
		
		/*JLSearchFor.setBounds(5,50,105,20);
		JLSearchFor.setFont(new Font("Dialog",Font.PLAIN,12));

		JTFSearchFor.setBounds(110,50,225,20);
		JTFSearchFor.setFont(new Font("Dialog",Font.PLAIN,12));

		JPDialogContainer.add(JLSearchFor);
		JPDialogContainer.add(JTFSearchFor);

		JLSearchIn.setBounds(5,72,105,20);
		JLSearchIn.setFont(new Font("Dialog",Font.PLAIN,12));

		JCSearchIn.setBounds(110,72,225,20);
		JCSearchIn.setFont(new Font("Dialog",Font.PLAIN,12));

		JPDialogContainer.add(JLSearchIn);
		JPDialogContainer.add(JCSearchIn);*/
		
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(60, 65, 220, 30);
		model.setSelected(true);
		
		JPDialogContainer.add(datePicker);
	
		JBok.setBounds(290,65,70,28);
		JBok.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBok.setMnemonic(KeyEvent.VK_S);
		JBok.addActionListener(JBActionListener);
		JBok.setActionCommand("ok");
		JPDialogContainer.add(JBok);
		/*
		JBCancel.setBounds(237,100,99,25);
		JBCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBCancel.setMnemonic(KeyEvent.VK_C);
		JBCancel.addActionListener(JBActionListener);
		JBCancel.setActionCommand("cancel");
		JPDialogContainer.add(JBCancel);*/

		getContentPane().add(JPDialogContainer);
		setSize(400,400);
		setResizable(false);
		setLocation((screen.width - 350)/2,((screen.height-465)/2));

	}
	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="ok"){
					//FrmRevenueRecord.reloadRecord("SELECT * FROM imsRevenueRecord WHERE " + JCSearchIn.getSelectedItem().toString().replaceAll(" ", "") + " LIKE '%" + JTFSearchFor.getText() + "%' ORDER BY Name ASC");
					dispose();
			}else{
				dispose();
			}
		}
	};
}
