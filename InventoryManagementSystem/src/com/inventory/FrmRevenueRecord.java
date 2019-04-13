package com.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.text.*;

public class FrmRevenueRecord extends JInternalFrame {

	public static JScrollPane SlrTableJSP = new JScrollPane();

	public static JPanel JPContainer = new JPanel();

	public static JTable JTSlrTable;

	JLabel JLPicture1 = new JLabel(new ImageIcon("images/helper.png"));
	JLabel JLHelpText = new JLabel(
			"To display a certain  \n record click the 'search button' and look for the record that you want.");

	JFrame JFParentFrame;

	/*JButton JBAddNew = new JButton("Add New", new ImageIcon("images/new.png"));
	JButton JBModify = new JButton("Modify", new ImageIcon("images/modify.png"));
	JButton JBPrint = new JButton("Print", new ImageIcon("images/print.png"));
	JButton JBDelete = new JButton("Delete", new ImageIcon("images/delete.png"));*/
	
	JButton JBSearch = new JButton("Search By Date", new ImageIcon("images/search.png"));
	JButton JBCustSearch = new JButton("Search By Customer", new ImageIcon("images/search.png"));
	
	JLabel JLTotalSales = new JLabel("TOTAL SALES");
	public static JTextField JTFTotalSales = new JTextField();
	Connection cnSlr;

	public static Statement stSlr;

	public static ResultSet rsSlr;

	public static String strSQL;
	public static String Content[][];

	public static int rowNum = 0;
	public static int total = 0;

	boolean goEOF;

	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	public FrmRevenueRecord(Connection srcCon, JFrame getParentFrame) throws SQLException {

		super("Revenue Records", false, true, false, true);

		JPContainer.setLayout(null);

		JFParentFrame = getParentFrame;

		cnSlr = srcCon;
		stSlr = cnSlr.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		strSQL = "SELECT invoice_number,sales,to_char(datetime, 'DD.MM.YYYY') dt,cname,phone FROM view_revenue order by invoice_number desc";

		JLPicture1.setBounds(5, 5, 48, 48);
		JPContainer.add(JLPicture1);

		JLHelpText.setBounds(55, 5, 570, 48);
		JLHelpText.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLHelpText);

		JTSlrTable = CreateTable();
		SlrTableJSP.getViewport().add(JTSlrTable);
		SlrTableJSP.setBounds(5, 55, 580, 220);
		JPContainer.add(SlrTableJSP);

		/*JBAddNew.setBounds(5, 382, 105, 25);
		JBAddNew.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBAddNew.setMnemonic(KeyEvent.VK_A);
		JBAddNew.addActionListener(JBActionListener);
		JBAddNew.setActionCommand("add");
		JPContainer.add(JBAddNew);*/

		JLTotalSales.setBounds(112, 300, 99, 25);
		JLTotalSales.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFTotalSales.setBounds(220, 300, 80, 25);
		JTFTotalSales.setFont(new Font("Dialog", Font.BOLD, 12));
		JTFTotalSales.setEditable(false);
		
		JPContainer.add(JLTotalSales);
		JPContainer.add(JTFTotalSales);
		
		JBSearch.setBounds(70, 382, 150, 25);
		JBSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearch.setMnemonic(KeyEvent.VK_S);
		JBSearch.addActionListener(JBActionListener);
		JBSearch.setActionCommand("search");
		JPContainer.add(JBSearch);
		
		JBCustSearch.setBounds(300, 382, 180, 25);
		JBCustSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBCustSearch.setMnemonic(KeyEvent.VK_S);
		JBCustSearch.addActionListener(JBActionListener);
		JBCustSearch.setActionCommand("custsearch");
		JPContainer.add(JBCustSearch);

		/*JBPrint.setBounds(312, 382, 99, 25);
		JBPrint.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBPrint.setMnemonic(KeyEvent.VK_P);
		JBPrint.addActionListener(JBActionListener);
		JBPrint.setActionCommand("print");
		JPContainer.add(JBPrint);

		JBDelete.setBounds(413, 382, 105, 25);
		JBDelete.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBDelete.setMnemonic(KeyEvent.VK_D);
		JBDelete.addActionListener(JBActionListener);
		JBDelete.setActionCommand("delete");
		JPContainer.add(JBDelete);

		JBReload.setBounds(627, 382, 105, 25);
		JBReload.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReload.setMnemonic(KeyEvent.VK_R);
		JBReload.addActionListener(JBActionListener);
		JBReload.setActionCommand("reload");
		JPContainer.add(JBReload);*/

		getContentPane().add(JPContainer);

		setSize(600, 450);
		setLocation((screen.width - 600) / 2, ((screen.height - 450) / 2) - 45);
		setFrameIcon(new ImageIcon("images/SalesRep.png"));

	}

	ActionListener JBActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String srcObj = e.getActionCommand();

			if (srcObj == "search") {
				JDialog JDSearchRec = new FrmRevenueByDate(JFParentFrame);
				JDSearchRec.setVisible(true);

			}
			
			if (srcObj == "custsearch") {
				JDialog JDSearchRec = new FrmRevenueByCustomer(JFParentFrame,cnSlr);
				JDSearchRec.setVisible(true);

			}
		}
	};

	public static JTable CreateTable() {
		String ColumnHeaderName[] = { "Invoice Number", "Sales", "Date","Customer Name","Phone" };
		int totalSales = 0;
		try {
			rsSlr = stSlr.executeQuery(strSQL);
			total = 0;

			rsSlr.afterLast();

			if (rsSlr.previous())
				total = rsSlr.getRow();

			rsSlr.beforeFirst();
			if (total != 0) {
				Content = new String[total][5];
				while (rsSlr.next()) {
					Content[rowNum][0] = "" + rsSlr.getString("invoice_number");
					Content[rowNum][1] = "" + rsSlr.getString("sales");
					Content[rowNum][2] = "" + rsSlr.getString("dt");
					Content[rowNum][3] = "" + rsSlr.getString("cname");
					Content[rowNum][4] = "" + rsSlr.getString("phone");
					totalSales = totalSales + Integer.parseInt(rsSlr.getString("sales"));
					rowNum++;
				}
			} else {
				Content = new String[0][5];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
				Content[0][3] = " ";
				Content[0][4] = " ";
			}
			JTFTotalSales.setText(totalSales+"");
		} catch (Exception eE) {
		}
		JTable NewTable = new JTable(Content, ColumnHeaderName) {
			public boolean isCellEditable(int iRows, int iCols) {
				return false;
			}
		};

		NewTable.setPreferredScrollableViewportSize(new Dimension(727, 320));
		NewTable.setBackground(Color.white);

		NewTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(80);
		NewTable.getColumnModel().getColumn(4).setPreferredWidth(100);

		ColumnHeaderName = null;
		Content = null;

		rowNum = 0;

		return NewTable;
	}

	public static void reloadRecord(String srcSQL) {
		strSQL = srcSQL;
		SlrTableJSP.getViewport().remove(JTSlrTable);
		JTSlrTable = CreateTable();
		SlrTableJSP.getViewport().add(JTSlrTable);
		JPContainer.repaint();
	}

	public static void reloadRecord() {
		SlrTableJSP.getViewport().remove(JTSlrTable);
		JTSlrTable = CreateTable();
		SlrTableJSP.getViewport().add(JTSlrTable);
		JPContainer.repaint();
	}

}