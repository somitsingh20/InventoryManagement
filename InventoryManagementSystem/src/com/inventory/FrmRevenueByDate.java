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
	
	JRadioButton JRBSingleDate = new JRadioButton("Day");
	JRadioButton JRBDateRange = new JRadioButton("Date Range");
	
	ButtonGroup BGDate = new  ButtonGroup();
	
	JLabel JLPic1 = new JLabel(new ImageIcon("images/bSearch.png"));
	JLabel JLBanner = new JLabel("Select date to view records");
	JPanel JPDialogContainer = new JPanel();

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	SqlDateModel model = new SqlDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model);
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	
	SqlDateModel model1 = new SqlDateModel();
	JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
	JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1);
	
	SqlDateModel model2 = new SqlDateModel();
	JDatePanelImpl datePanel2 = new JDatePanelImpl(model2);
	JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2);
	
	String month;
	String month1;
	String month2;
	
	public FrmRevenueByDate(JFrame OwnerForm){
		super(OwnerForm,true);
	    setTitle("Search Records By Date");

		JPDialogContainer.setLayout(null);

		JLPic1.setBounds(25,5,32,32);
		JPDialogContainer.add(JLPic1);

		JLBanner.setBounds(65,5,280,48);
		JLBanner.setFont(new Font("Dialog",Font.PLAIN,12));
		JPDialogContainer.add(JLBanner);
		
		JRBSingleDate.setBounds(60, 50, 220, 30);
		JRBSingleDate.setSelected(true);
		JPDialogContainer.add(JRBSingleDate);
		
		datePicker.setBounds(60, 85, 220, 30);
		model.setSelected(true);
		JPDialogContainer.add(datePicker);
		
		JRBDateRange.setBounds(60, 130, 220, 30);
		JPDialogContainer.add(JRBDateRange);
		
		BGDate.add(JRBSingleDate);
		BGDate.add(JRBDateRange);
		
		datePicker1.setBounds(60, 170, 220, 30);
		model1.setSelected(true);
		JPDialogContainer.add(datePicker1);
		
		datePicker2.setBounds(60, 220, 220, 30);
		model2.setSelected(true);
		JPDialogContainer.add(datePicker2);
		
		JBok.setBounds(130,260,70,28);
		JBok.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBok.setMnemonic(KeyEvent.VK_S);
		JBok.addActionListener(JBActionListener);
		JBok.setActionCommand("ok");
		JPDialogContainer.add(JBok);

		getContentPane().add(JPDialogContainer);
		setSize(400,350);
		setResizable(false);
		setLocation((screen.width - 350)/2,((screen.height-465)/2));

	}
	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="ok"){
					
					if(JRBSingleDate.isSelected()){
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
					}
					else if(JRBDateRange.isSelected()){
						int day = model1.getDay();
						if(day<10){
							String selectedDay="0"+day;
						}
						int mth = (model1.getMonth()+1);
						switch(mth){
						case 1:
							month1 = "JAN";
							break;
						case 2:
							month1 = "FEB";
							break;
						case 3:
							month1 = "MAR";
							break;
						case 4:
							month1 = "APR";
							break;
						case 5:
							month1 = "MAY";
							break;
						case 6:
							month1 = "JUN";
							break;
						case 7:
							month1 = "JUL";
							break;
						case 8:
							month1 = "AUG";
							break;
						case 9:
							month1 = "SEP";
							break;
						case 10:
							month1 = "OCT";
							break;
						case 11:
							month1 = "NOV";
							break;
						case 12:
							month1 = "DEC";
							break;
						default:
							System.err.println("Wrong month format");
						}
						int year = model1.getYear();
						String selectedDate =  day+"/"+month1+"/"+year;
						
						//Date Picker 2
						
						int day1 = model2.getDay();
						if(day1<10){
							String selectedDay1="0"+day1;
						}
						int mth1 = (model2.getMonth()+1);
						switch(mth1){
						case 1:
							month2 = "JAN";
							break;
						case 2:
							month2 = "FEB";
							break;
						case 3:
							month2 = "MAR";
							break;
						case 4:
							month2 = "APR";
							break;
						case 5:
							month2 = "MAY";
							break;
						case 6:
							month2 = "JUN";
							break;
						case 7:
							month2 = "JUL";
							break;
						case 8:
							month2 = "AUG";
							break;
						case 9:
							month2 = "SEP";
							break;
						case 10:
							month2 = "OCT";
							break;
						case 11:
							month2 = "NOV";
							break;
						case 12:
							month2 = "DEC";
							break;
						default:
							System.err.println("Wrong month format");
						}
						int year1 = model2.getYear();
						String selectedDate1 =  day1+"/"+month2+"/"+year1;
						FrmRevenueRecord.reloadRecord("SELECT invoice_number,sales,to_char(datetime, 'DD.MM.YYYY') dt FROM imsRevenueRecord WHERE datetime between '" + selectedDate+"' and '" + selectedDate1+"'");
						dispose();
					}
			}else{
				dispose();
			}
		}
	};
}
