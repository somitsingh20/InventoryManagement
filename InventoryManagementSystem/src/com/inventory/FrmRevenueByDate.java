package com.inventory;

import javax.swing.*;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

import java.awt.*;
import java.awt.event.*;

public class FrmRevenueByDate extends JDialog{
	//Start create variables
	JButton JBok = new JButton("OK");

	JLabel JLPic1 = new JLabel(new ImageIcon("images/bSearch.png"));
	JLabel JLBanner = new JLabel("Select date to view records");
	JPanel JPDialogContainer = new JPanel();

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	SqlDateModel model = new SqlDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model);
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	String month;
	
	public FrmRevenueByDate(JFrame OwnerForm){
		super(OwnerForm,true);
	    setTitle("Search Records By Date");

		JPDialogContainer.setLayout(null);

		JLPic1.setBounds(25,5,32,32);
		JPDialogContainer.add(JLPic1);

		JLBanner.setBounds(65,5,280,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPDialogContainer.add(JLBanner);
		
		datePicker.setBounds(60, 65, 220, 30);
		model.setSelected(true);
		
		JPDialogContainer.add(datePicker);
	
		JBok.setBounds(290,65,70,28);
		JBok.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBok.setMnemonic(KeyEvent.VK_S);
		JBok.addActionListener(JBActionListener);
		JBok.setActionCommand("ok");
		JPDialogContainer.add(JBok);

		getContentPane().add(JPDialogContainer);
		setSize(400,330);
		setResizable(false);
		setLocation((screen.width - 350)/2,((screen.height-465)/2));

	}
	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="ok"){
				//Proper
					int day = model.getDay();
					if(day<10){
						String selectedDay="0"+day;
					}
					int mth = (model.getMonth()+1);
					switch(mth){
					case 1:
						month = "JAN";
						break;
					case 2:
						month = "FEB";
						break;
					case 3:
						month = "MAR";
						break;
					case 4:
						month = "APR";
						break;
					case 5:
						month = "MAY";
						break;
					case 6:
						month = "JUN";
						break;
					case 7:
						month = "JUL";
						break;
					case 8:
						month = "AUG";
						break;
					case 9:
						month = "SEP";
						break;
					case 10:
						month = "OCT";
						break;
					case 11:
						month = "NOV";
						break;
					case 12:
						month = "DEC";
						break;
					default:
						System.err.println("Wrong month format");
					}
					int year = model.getYear();
					String selectedDate =  day+"/"+month+"/"+year;
					//System.out.println("0"+day+"/"+month+"/"+year);
					FrmRevenueRecord.reloadRecord("SELECT invoice_number,sales,to_char(datetime, 'DD.MM.YYYY') dt FROM imsRevenueRecord WHERE datetime='" + selectedDate+"'");
					dispose();
			}else{
				dispose();
			}
		}
	};
}
