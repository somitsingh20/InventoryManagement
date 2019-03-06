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
	
	JButton JBSearch = new JButton("Search",new ImageIcon("images/search.png"));
	JButton JBPrint = new JButton("Print",new ImageIcon("images/print.png"));
	
	Connection cnInv;
	public static Statement stInv;
	public static ResultSet rsInv;
	public static String strSQL;
	
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
		InvTableJSP.getViewport().add(InvTableJSP);
		InvTableJSP.setBounds(5,55,727,320);
		JPContainer.add(InvTableJSP);
		
		JBSearch.setBounds(5,382,105,25);
		JBSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearch.setMnemonic(KeyEvent.VK_A);
		JBSearch.addActionListener(JBActionListener);
		JBSearch.setActionCommand("Search");
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
			// TODO Auto-generated method stub
			
		}
	};

	public static JTable CreateTable() {
		// TODO Auto-generated method stub
		return null;
	}

	// private methods

	//////////// end of methods //////////////
}