package com.inventory;

import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.table.TableModel;

public class FrmViewInvoiceDetails extends JDialog {

	//public static JScrollPane ProductTableJSP = new JScrollPane();
	public static JPanel JPContainer = new JPanel();
	public static JScrollPane ViewTableJSP = new JScrollPane();
	JLabel JLInvoiceNumber = new JLabel("Invoice Number:");
	JLabel JLGrandTotal = new JLabel("Grand Total");
	
	JTextField JTFInvoiceNumber = new JTextField();
	JTextField JTFGrandTotal = new JTextField();
	
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	static DefaultTableModel model = new DefaultTableModel(
			new Object[] { "Product Name", "Quantity Purchased", "Cost"}, 1);
	
	Connection cnVID;
	Statement stVID;
	ResultSet rsVID;
	
	public FrmViewInvoiceDetails(boolean ADD_STATE, JFrame OwnerForm, Connection srcCon, String srcSQL) {
		
		super(OwnerForm,true);
		
		cnVID = srcCon;
		//ADDING_STATE = ADD_STATE;
		try{
			stVID = cnVID.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch( SQLException sqlEx){

		}
		try {
			int gtotal = 0;
			int count = 0;
			int rows = model.getRowCount();
			rsVID = stVID.executeQuery(srcSQL);
			while(rsVID.next()){
				model.setValueAt(rsVID.getString("productname"), count, 0);
				model.setValueAt(rsVID.getString("qty"), count, 1);
				model.setValueAt(rsVID.getString("cost"), count, 2);
				JTFInvoiceNumber.setText(rsVID.getString("invoice_number"));
				gtotal+= Integer.parseInt(model.getValueAt(count, 2).toString());
				model.setRowCount(rows + 1);
				count++;
			}
			JTFGrandTotal.setText(gtotal+"");
			model.fireTableDataChanged();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JPContainer.setLayout(null);
		
		JLInvoiceNumber.setBounds(5, 5, 100, 20);
		JLInvoiceNumber.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFInvoiceNumber.setBounds(105, 5, 80, 20);
		JTFInvoiceNumber.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFInvoiceNumber.setEditable(false);
		
		JPContainer.add(JLInvoiceNumber);
		JPContainer.add(JTFInvoiceNumber);
		
		JTable table = new JTable(model);
		
		ViewTableJSP.getViewport().add(table);
		ViewTableJSP.setBounds(5,40,370,100);
		JPContainer.add(ViewTableJSP);
		
		JLGrandTotal.setBounds(150, 150, 90, 20);
		JLGrandTotal.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFGrandTotal.setBounds(250, 150, 80, 20);
		JTFGrandTotal.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFGrandTotal.setEditable(false);
		
		JPContainer.add(JLGrandTotal);
		JPContainer.add(JTFGrandTotal);
		
		getContentPane().add(JPContainer);
		setSize(400,500);
		setResizable(true);
		setModal(true);
		setLocation((screen.width - 420) / 2, ((screen.height - 660) / 2));
	}

}
