package com.inventory;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FrmUpdateStatus extends JDialog {
	
	public static JPanel JPContainer = new JPanel();
	public static JScrollPane StatTableJSP = new JScrollPane();
	
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	JLabel JLStatus = new JLabel("Status");
	JLabel JLTime = new JLabel("Time");
	JLabel JLComment = new JLabel("Comment");
	JLabel JLProductName = new JLabel("Product Name");
	JLabel JLOrderID = new JLabel("Order ID");
	
	public static JTextField JTFStatus = new JTextField();
	public static JTextField JTFCommment = new JTextField();
	public static JTextField JTFProductName = new JTextField();
	public static JTextField JTFOrderID = new JTextField();
	
	JButton JBStatus = new JButton("SET");
	
	public static String currentStatus="";
	public static String comments = "";
	public static String lstat = "";
	
	Connection cnStat;
	Statement stStat;
	ResultSet rsStat;
	
	static DefaultTableModel model = new DefaultTableModel(
			new Object[] { "Time", "Current Status", "Comments"},0);

	public FrmUpdateStatus(JFrame OwnerForm, Connection srcCon, String strSQL) {
		
		super(OwnerForm,true);
		cnStat = srcCon;
		
		try{
			stStat = cnStat.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch( SQLException sqlEx){

		}
		
		try {
			int count = 0;
			int rows = model.getRowCount();
			rsStat = stStat.executeQuery(strSQL);
			while(rsStat.next()){
				model.setRowCount(count+1);
				JTFOrderID.setText(rsStat.getString("orderid"));
				JTFProductName.setText(rsStat.getString("productname"));
				model.setValueAt(rsStat.getString("dt"), count, 0);
				model.setValueAt(rsStat.getString("currentstatus"), count, 1);
				model.setValueAt(rsStat.getString("comments"), count, 2);
				count++;
				}
			lstat = model.getValueAt(count-1, 1).toString();
			if(lstat.equalsIgnoreCase("received")){
				JTFCommment.setEditable(false);
				JTFStatus.setEditable(false);
				JTFOrderID.setEditable(false);
				JTFProductName.setEditable(false);
			}
			}
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		JPContainer.setLayout(null);
		
		JLOrderID.setBounds(20, 10, 100, 25);
		JLOrderID.setFont(new Font("Dialog", Font.PLAIN, 12));
		JLProductName.setBounds(150, 10, 100, 25);
		JLProductName.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLOrderID);
		JPContainer.add(JLProductName);
		
		JTFOrderID.setBounds(20,45,100,25);
		JTFProductName.setBounds(150, 45, 150, 25);
		JPContainer.add(JTFOrderID);
		JPContainer.add(JTFProductName);
		
		JTable table = new JTable(model);
		StatTableJSP.getViewport().add(table);
		StatTableJSP.setBounds(20,90,540,200);
		JPContainer.add(StatTableJSP);
		
		JLStatus.setBounds(50, 340, 150, 25);
		JLStatus.setFont(new Font("Dialog", Font.PLAIN, 12));
		JLComment.setBounds(220, 340, 150, 25);
		JLComment.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLStatus);
		JPContainer.add(JLComment);
		
		JTFStatus.setBounds(50,380,150,25);
		JTFStatus.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFCommment.setBounds(220, 380, 250, 25);
		JTFCommment.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JTFStatus);
		JPContainer.add(JTFCommment);
		
		JBStatus.setBounds(200, 415, 100, 25);
		JBStatus.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBStatus.setMnemonic(KeyEvent.VK_A);
		JBStatus.addActionListener(JBActionListener);
		JBStatus.setActionCommand("set");
		JPContainer.add(JBStatus);
		
		getContentPane().add(JPContainer);
		setSize(600,500);
		setResizable(true);
		setLocation((screen.width - 420) / 2, ((screen.height - 660) / 2));
	}
	
	ActionListener JBActionListener = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			String com = "commit";
			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        int count = 1;
	        
	        int row = model.getRowCount();
			model.setRowCount(row + 1);
			String srcObj = e.getActionCommand();
			if(srcObj=="set"){
				currentStatus = JTFStatus.getText();
				comments = JTFCommment.getText();
				model.setValueAt(currentStatus, row, 1);
				model.setValueAt(comments, row, 2);
				model.setValueAt(sdf.format(cal.getTime()), row, 0);
				model.fireTableDataChanged();
			}
			if(JTFStatus.getText().equalsIgnoreCase("received")){
				String updateStatusInPurchaseRecord = "UPDATE imspurchase set status ='received' where orderid='"+JTFOrderID.getText()+"'";
				String updateStatus = "Insert into imsstatus values('"+JTFOrderID.getText()+"','"+JTFProductName.getText()+"',sysdate,'"+currentStatus+"','"+comments+"')";
				try {
					stStat.executeUpdate(updateStatusInPurchaseRecord);
					stStat.executeUpdate(updateStatus);
					stStat.executeUpdate(com);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				String updateStatus = "Insert into imsstatus values('"+JTFOrderID.getText()+"','"+JTFProductName.getText()+"',sysdate,'"+currentStatus+"','"+comments+"')";
				try {
					stStat.executeUpdate(updateStatus);
					stStat.executeUpdate(com);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	};
}
