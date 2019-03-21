package com.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FrmInvoice extends JInternalFrame
{
	
	public static JScrollPane InvTableJSP = new JScrollPane();

	public static JPanel JPContainer = new JPanel();

	public static JTable JTInvTable;

	JLabel JLPicture1 = new JLabel(new ImageIcon("images/helper.png"));
	JLabel JLHelpText = new JLabel("To display a certain  \n record click the 'search button' and look for the record that you want.");

	JFrame JFParentFrame;
	
	JButton JBNewInvoice = new JButton("New Invoice", new ImageIcon("images/invoice.png"));
	JButton JBSearch = new JButton("Search",new ImageIcon("images/search.png"));
	JButton JBPrint = new JButton("Print",new ImageIcon("images/print.png"));
	
	Connection cnInv;
	public static Statement stInv;
	public static ResultSet rsInv;
	public static String strSQL;
	public static String Content[][];
	public static int rowNum = 0;
	public static int total = 0;
	
	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	public FrmInvoice(Connection srcCon, JFrame getParentFrame) throws SQLException
	{
		super("Invoice", false, true, false, true);
		JPContainer.setLayout(null);

		JFParentFrame = getParentFrame;
		
		cnInv = srcCon;
		stInv = cnInv.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		strSQL = "SELECT * FROM tblInvoice";
		
		JLPicture1.setBounds(5,5,48,48);
		JPContainer.add(JLPicture1);

		JLHelpText.setBounds(55,5,570,48);
		JLHelpText.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLHelpText);
		
		JTInvTable=CreateTable();
		InvTableJSP.getViewport().add(JTInvTable);
		InvTableJSP.setBounds(5,55,727,320);
		JPContainer.add(InvTableJSP);
		
		JBNewInvoice.setBounds(5,382,150,25);
		JBNewInvoice.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBNewInvoice.setMnemonic(KeyEvent.VK_A);
		JBNewInvoice.addActionListener(JBActionListener);
		JBNewInvoice.setActionCommand("newinv");
		JPContainer.add(JBNewInvoice);
		
		JBSearch.setBounds(5,382,105,25);
		JBSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearch.setMnemonic(KeyEvent.VK_A);
		JBSearch.addActionListener(JBActionListener);
		JBSearch.setActionCommand("search");
		JPContainer.add(JBSearch);
		
		JBPrint.setBounds(312,382,99,25);
		JBPrint.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBPrint.setMnemonic(KeyEvent.VK_P);
		JBPrint.addActionListener(JBActionListener);
		JBPrint.setActionCommand("print");
		JPContainer.add(JBPrint);
		
		getContentPane().add(JPContainer);
		setSize(747,450);
		setLocation((screen.width - 747)/2,((screen.height-450)/2)-45);
		setFrameIcon(new ImageIcon("images/invoice.png"));
	}
	
	ActionListener JBActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String srcObj = e.getActionCommand();
			
			if(srcObj=="newinv"){
				JDialog JDAdd = new frm_add_edit_invoice(true,JFParentFrame,cnInv,"");
				JDAdd.show();
			
			}
			else if(srcObj=="search"){
				JDialog JDSearchRec = new FrmSearchInvoice(JFParentFrame);
				JDSearchRec.show(true);
			}
			else if(srcObj=="print"){
				
			}
			
		}
	};

	public static JTable CreateTable() {
		String ColumnHeaderName [] = {
				"Invoice Index","Invoice Number","Customer Name","Description","Quantity","Unit Cost","Total Price"
		};
		try{
		rsInv = stInv.executeQuery(strSQL);
		total = 0;
		
		rsInv.afterLast();
		
		if(rsInv.previous())total = rsInv.getRow();
		
		rsInv.beforeFirst();
		if(total > 0){
			Content = new String[total][6];
			while(rsInv.next()){
				Content[rowNum][0] = "" + rsInv.getString("InvoiceIndex");
				Content[rowNum][1] = "" + rsInv.getString("InvoiceNumber");
				Content[rowNum][2] = "" + rsInv.getString("Description");
				Content[rowNum][3] = "" + rsInv.getString("UnitCost");
				Content[rowNum][4] = "" + rsInv.getString("Quantity");
				Content[rowNum][5] = "" + rsInv.getString("TotalPrice");
				rowNum++;
			}
		}else{
			Content = new String[0][6];
			Content[0][0] = " ";
			Content[0][1] = " ";
			Content[0][2] = " ";
			Content[0][3] = " ";
			Content[0][4] = " ";
			Content[0][5] = " ";
		}
		}catch(Exception eE){
		}
		JTable NewTable = new JTable (Content,ColumnHeaderName){
			public boolean isCellEditable (int iRows, int iCols) {
				return false;
			}
		};
		
		NewTable.setPreferredScrollableViewportSize(new Dimension(727, 320));
		NewTable.setBackground(Color.white);

		NewTable.getColumnModel().getColumn(0).setMaxWidth(0);
		NewTable.getColumnModel().getColumn(0).setMinWidth(0);
		NewTable.getColumnModel().getColumn(0).setWidth(0);
		NewTable.getColumnModel().getColumn(0).setPreferredWidth(0);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		NewTable.getColumnModel().getColumn(4).setPreferredWidth(50);
		NewTable.getColumnModel().getColumn(5).setPreferredWidth(50);
		
		ColumnHeaderName=null;
		Content=null;

		rowNum = 0;

		return NewTable;
	}

	public static void reloadRecord(String srcSQL) {
		strSQL = srcSQL;
		InvTableJSP.getViewport().remove(InvTableJSP);
		JTInvTable=CreateTable();
		InvTableJSP.getViewport().add(JTInvTable);
		JPContainer.repaint();
}
	public static void reloadRecord(){
		InvTableJSP.getViewport().remove(JTInvTable);
		JTInvTable=CreateTable();
		InvTableJSP.getViewport().add(JTInvTable);
		JPContainer.repaint();
}
}