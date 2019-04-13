package com.inventory;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FrmViewOrderDetails extends JDialog {
	
	public static JPanel JPContainer = new JPanel();
	public static JScrollPane ViewTableJSP = new JScrollPane();
	
	JLabel JLProductName = new JLabel("Product Name");
	JLabel JLOrderID = new JLabel("Order ID");
	JLabel JLDate = new JLabel("Order Date");
	JLabel JLQty = new JLabel("Quantity");
	JLabel JLTotalCost = new JLabel("Total Cost");
	JLabel JLUnitCost = new JLabel("Unit Cost");
	JLabel JLSupplierName = new JLabel("Supplier Name");
	JLabel JLSupplierID = new JLabel("Supplier ID");
	
	public static JTextField JTFProductName = new JTextField();
	public static JTextField JTFOrderID = new JTextField();
	public static JTextField JTFDate = new JTextField();
	public static JTextField JTFQty = new JTextField();
	public static JTextField JTFTotalCost = new JTextField();
	public static JTextField JTFUnitCost = new JTextField();
	public static JTextField JTFSupplierName = new JTextField();
	public static JTextField JTFSupplierID = new JTextField();
	
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	Connection cnVOD;
	Statement stVOD;
	ResultSet rsVOD;
	
	public static int qty;
	public static int ucost;
	public static int tcost;
	
	static DefaultTableModel model = new DefaultTableModel(
			new Object[] { "Time", "Current Status", "Comments"},0);

	public FrmViewOrderDetails(JFrame OwnerForm, Connection srcCon, String srcSQL) {
		
		super(OwnerForm,true);
		
		cnVOD = srcCon;
		try{
			stVOD = cnVOD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch( SQLException sqlEx){

		}
		
		try {
			rsVOD = stVOD.executeQuery(srcSQL);
			if(rsVOD.next()){
				JTFOrderID.setText(rsVOD.getString("orderid"));
				JTFDate.setText(rsVOD.getString("dt"));
				JTFProductName.setText(rsVOD.getString("product"));
				JTFQty.setText(rsVOD.getString("quantity"));
				JTFUnitCost.setText(rsVOD.getString("cost"));
				JTFSupplierID.setText(rsVOD.getString("supplierid"));
				JTFSupplierName.setText(rsVOD.getString("suppliername"));
				qty = Integer.parseInt(rsVOD.getString("quantity"));
				ucost = Integer.parseInt(rsVOD.getString("cost"));
				tcost = qty*ucost;
				JTFTotalCost.setText(tcost+"");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			int count = 0;
			int rows = model.getRowCount();
			rsVOD = stVOD.executeQuery("SELECT to_char(time, 'DD.MM.YYYY hh:mi AM') dt,currentstatus,comments FROM imsstatus WHERE orderid = '"+JTFOrderID.getText()+"' order by time");
			while(rsVOD.next()){
				model.setRowCount(count+1);
				model.setValueAt(rsVOD.getString("dt"), count, 0);
				model.setValueAt(rsVOD.getString("currentstatus"), count, 1);
				model.setValueAt(rsVOD.getString("comments"), count, 2);
				count++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JPContainer.setLayout(null);
		
		JLOrderID.setBounds(40, 10, 150, 25);
		JLDate.setBounds(260, 10, 150, 25);
		JPContainer.add(JLOrderID);
		JPContainer.add(JLDate);
		
		JTFOrderID.setBounds(40, 45, 150, 25);
		JTFDate.setBounds(260, 45, 150, 25);
		JPContainer.add(JTFOrderID);
		JPContainer.add(JTFDate);
		
		JLProductName.setBounds(40, 100, 120, 20);
		JTFProductName.setBounds(180, 100, 120, 20);
		JLQty.setBounds(40, 130, 120, 20);
		JTFQty.setBounds(180, 130, 120, 20);
		JLUnitCost.setBounds(40, 160, 120, 20);
		JTFUnitCost.setBounds(180, 160, 120, 20);
		JLTotalCost.setBounds(40, 190, 120, 20);
		JTFTotalCost.setBounds(180, 190, 120, 20);
		JLSupplierID.setBounds(40, 220, 120, 20);
		JTFSupplierID.setBounds(180, 220, 120, 20);
		JLSupplierName.setBounds(40, 250, 120, 20);
		JTFSupplierName.setBounds(180, 250, 120, 20);
		
		JPContainer.add(JLProductName);
		JPContainer.add(JTFProductName);
		JPContainer.add(JLQty);
		JPContainer.add(JTFQty);
		JPContainer.add(JLUnitCost);
		JPContainer.add(JTFUnitCost);
		JPContainer.add(JLTotalCost );
		JPContainer.add(JTFTotalCost);
		JPContainer.add(JLSupplierID);
		JPContainer.add(JTFSupplierID);
		JPContainer.add(JLSupplierName);
		JPContainer.add(JTFSupplierName);
		
		JTable table = new JTable(model);
		ViewTableJSP.getViewport().add(table);
		ViewTableJSP.setBounds(40,300,400,200);
		JPContainer.add(ViewTableJSP);
		
		getContentPane().add(JPContainer);
		setSize(500,600);
		setResizable(true);
		setModal(true);
		setLocation((screen.width - 420) / 2, ((screen.height - 660) / 2));
	}

}
