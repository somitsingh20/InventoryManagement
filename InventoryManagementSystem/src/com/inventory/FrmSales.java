//new

package com.inventory;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FrmSales extends JInternalFrame {

	public static JScrollPane ProductTableJSP = new JScrollPane();

	public static JPanel JPContainer = new JPanel();

	public static JTable JTProductTable;

	public static JTable table;
	// public static JTable NewTable;

	JLabel JLPicture1 = new JLabel(new ImageIcon("images/helper.png"));
	JLabel JLHelpText = new JLabel(
			"To display a certain  \n record click the 'search button' and look for the record that you want.");

	static JFrame JFParentFrame;

	JButton JBAddNew = new JButton("Add", new ImageIcon("images/new.png"));
	JButton JBSearchCustomer = new JButton("Search Customer", new ImageIcon("images/search.png"));
	JButton JBSearchProduct = new JButton("Add Product", new ImageIcon("images/search.png"));
	JButton JBPrint = new JButton("Print", new ImageIcon("images/print.png"));
	JButton JBGrandTotal = new JButton("Grand Total");
	JButton JBReset = new JButton("Reset", new ImageIcon("images/reload.png"));

	JLabel JLInvoiceNumber = new JLabel("Invoice Number");
	JLabel JLCustomerName = new JLabel("Customer Name");
	JLabel JLGrandTotal = new JLabel("Grand Total");
	JLabel JLPhone = new JLabel("Phone No.");
	JLabel JLDate = new JLabel("Date");

	public static JTextField JTFCustomerName = new JTextField();
	public static JTextField JTFInvoiceNumber = new JTextField();
	public static JTextField JTFGrandTotal = new JTextField();
	public static JTextField JTFPhone = new JTextField();
	public static JTextField JTFDate = new JTextField();
	public static JTextField JTFCustID = new JTextField();

	public static Connection cnSale;
	public static Statement stSale;
	public static ResultSet rsSale;
	public static ResultSet rsSale1;
	public static ResultSet rsSale2;
	public static ResultSet rsSale3;

	public static String Content[][];
	public static String strSQL;
	public static String strUpdateProductTable;
	public static String strUpdateCustomerTable;
	public static String strUpdateInvoiceTable;
	public static String strUpdateRevenueTable;
	public static String commitTable;
	
	public static int rowNum = 0;
	public static int total = 0;
	public static int count = 0;
	public static int cuscount = 0;
	public static int cid;

	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	HashMap<String, Long> cusData = new HashMap<>();
	
	static DefaultTableModel model = new DefaultTableModel(
			new Object[] { "ProductName", "UnitPrice", "AvlQty", "PurchaseQty","DiscPrice", "Total Price" }, 0);
	
	// constructor
	public FrmSales(Connection srcCon, JFrame getParentFrame) throws SQLException {

		super("New Sale", false, true, false, true);

		JPContainer.setLayout(null);
		JFParentFrame = getParentFrame;
		
		cnSale = srcCon;
		stSale = cnSale.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		

		//Generate Invoice Number
		String generateInvoice = "SELECT MAX(invoice_number) as invoice from imsinvoice";
		String getCustId = "Select MAX(cid) as custID from imscustomer";
		rsSale1 = srcCon.createStatement().executeQuery(generateInvoice);
		
		if(rsSale1.next()){
			String str = rsSale1.getString("invoice");
			if(str==null){
				JTFInvoiceNumber.setText(101+"");
			}
			else
			{
				int invNumber = Integer.parseInt(str);
				JTFInvoiceNumber.setText((invNumber+1)+"");
			}
		}
		rsSale3 = srcCon.createStatement().executeQuery(getCustId);
		if(rsSale3.next()){
			String str = rsSale3.getString("custID");
			if(str==null){
				JTFCustID.setText(1+"");
			}
			else
			{
				int custID = Integer.parseInt(str);
				JTFCustID.setText((custID+1)+"");
			}
		}
		
		String getCustomer = "Select cname,phone from imscustomer";
		rsSale2 = srcCon.createStatement().executeQuery(getCustomer);
		while(rsSale2.next()){
			cusData.put(rsSale2.getString("cname"),rsSale2.getLong("phone"));
		}
		//Set current date
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yy");
		JTFDate.setText(sf.format(date));

		JTable table = new JTable(model);

		JLCustomerName.setBounds(5, 5, 100, 20);
		JLCustomerName.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFCustomerName.setBounds(105, 5, 80, 20);
		JTFCustomerName.setFont(new Font("Dialog", Font.PLAIN, 12));

		JPContainer.add(JLCustomerName);
		JPContainer.add(JTFCustomerName);

		JLInvoiceNumber.setBounds(200, 5, 100, 20);
		JLInvoiceNumber.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFInvoiceNumber.setBounds(300, 5, 80, 20);
		JTFInvoiceNumber.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFInvoiceNumber.setEditable(false);

		JPContainer.add(JLInvoiceNumber);
		JPContainer.add(JTFInvoiceNumber);

		JLPhone.setBounds(5, 30, 100, 20);
		JLPhone.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFPhone.setBounds(105, 30, 80, 20);
		JTFPhone.setFont(new Font("Dialog", Font.PLAIN, 12));

		JPContainer.add(JLPhone);
		JPContainer.add(JTFPhone);
		
		JLDate.setBounds(200, 30, 100, 20);
		JLDate.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFDate.setBounds(300, 30, 80, 20);
		JTFDate.setFont(new Font("Dialog", Font.PLAIN, 12));
		JTFDate.setEditable(false);
		
		JPContainer.add(JLDate);
		JPContainer.add(JTFDate);

		JBSearchCustomer.setBounds(10, 60, 180, 20);
		JBSearchCustomer.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearchCustomer.addActionListener(JBActionListener);
		JBSearchCustomer.setActionCommand("searchCustomer");
		JPContainer.add(JBSearchCustomer);

		JBSearchProduct.setBounds(220, 60, 150, 20);
		JBSearchProduct.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearchProduct.addActionListener(JBActionListener);
		JBSearchProduct.setActionCommand("searchProduct");
		JPContainer.add(JBSearchProduct);

		ProductTableJSP.getViewport().add(table);
		ProductTableJSP.setBounds(5, 90, 400, 240);
		JPContainer.add(ProductTableJSP);

		JBGrandTotal.setBounds(160, 340, 120, 20);
		JBGrandTotal.setFont(new Font("Dialog", Font.BOLD, 14));
		JBGrandTotal.addActionListener(JBActionListener);
		JBGrandTotal.setActionCommand("total");

		JTFGrandTotal.setBounds(312, 340, 80, 20);
		JTFGrandTotal.setFont(new Font("Dialog", Font.BOLD, 14));
		JTFGrandTotal.setEditable(false);

		JPContainer.add(JBGrandTotal);
		JPContainer.add(JTFGrandTotal);

		JBAddNew.setBounds(20, 390, 120, 20);
		JBAddNew.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBAddNew.addActionListener(JBActionListener);
		JBAddNew.setActionCommand("add");
		
		JBReset.setBounds(150, 390, 120, 20);
		JBReset.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReset.addActionListener(JBActionListener);
		JBReset.setActionCommand("reset");
		
		JBPrint.setBounds(280, 390, 120, 20);
		JBPrint.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBPrint.addActionListener(JBActionListener);
		JBPrint.setActionCommand("print");
		
		JPContainer.add(JBAddNew);
		JPContainer.add(JBReset);
		JPContainer.add(JBPrint);

		getContentPane().add(JPContainer);
		setSize(420, 460);
		setResizable(true);
		setLocation((screen.width - 420) / 2, ((screen.height - 660) / 2));
	}

	ActionListener JBActionListener = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			String srcObj = e.getActionCommand();
			// Search for customer in DB
			if (srcObj == "searchCustomer") {
				JDialog JDSearchCus = new FrmSearchCustomer1(JFParentFrame,cnSale);
				JDSearchCus.show(true);
				cuscount++;
			}
			//Search for product in DB
			if (srcObj == "searchProduct") {
				JDialog JDSearchRec = new FrmSearchAddProduct(JFParentFrame,cnSale);
				JDSearchRec.show(true);
			}
			//Print invoice
			if (srcObj == "print") {
				int rCount = model.getRowCount();
				if (rCount != 0) {
					try {
						FileInputStream fin = new FileInputStream("images/logo.jpg");
						clsPublicMethods PrintingClass = new clsPublicMethods();
						String RecordToPrint = "";
						java.util.Date CurrentDate = new java.util.Date();
						SimpleDateFormat SDFDateFormatter = new SimpleDateFormat("MMM. dd, yyyy", Locale.getDefault());
						Image img = Toolkit.getDefaultToolkit().getImage("./images/image.png");
						
						RecordToPrint += fin;
						RecordToPrint += "                             I N V O I C E							 \n";

						RecordToPrint += "___________________________________________________________________________\n\n\n";

						RecordToPrint += " 	 CustomerName: " + JTFCustomerName.getText()
								+ "                      Invoice No: " + JTFInvoiceNumber.getText() + "\n";
						RecordToPrint += "  Phone No: " + JTFPhone.getText() + "                    Date: "
								+ SDFDateFormatter.format(CurrentDate).toString() + "\n";
						RecordToPrint += "___________________________________________________________________________\n";
						RecordToPrint += " ProductName                                       Quantity    Price  \n ";
						RecordToPrint += "___________________________________________________________________________\n\n";
						
						for(int i=0;i<rCount;i++){
						
						RecordToPrint += " " + model.getValueAt(i, 0) + "                                             "
								+ model.getValueAt(i, 3) + "      " + model.getValueAt(i, 4) + "\n";
						/*RecordToPrint += " " + model.getValueAt(i, 0) + "                                             "
								+ model.getValueAt(i, 3) + "      " + model.getValueAt(i, 4) + "\n";*/
						}

						RecordToPrint += "___________________________________________________________________________\n\n";
						RecordToPrint += "                                              Grand Total : "
								+ JTFGrandTotal.getText() + "\n";
						RecordToPrint += "____________________________________________________________________________\n\n";

						PrintingClass.printRecord(RecordToPrint, JFParentFrame);

						CurrentDate = null;
						SDFDateFormatter = null;
						RecordToPrint = null;
						

					} catch (Exception sqlE) {
						if (sqlE.getMessage() != null) {
							System.out.println(sqlE.getMessage());
						} else {
							JOptionPane.showMessageDialog(null, "Please select a record in the list to print.",
									"No Record Selected", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

			}
			//Add products to Invoice/purchase record
			if (srcObj == "add") {
				int quantity;
				ArrayList<String> numdata = new ArrayList<String>();
				for (int count = 0; count < model.getRowCount(); count++) {
					numdata.add(model.getValueAt(count, 0).toString()); // productname
					numdata.add(model.getValueAt(count, 1).toString()); // unit price
					numdata.add(model.getValueAt(count, 2).toString()); // available qty
					numdata.add(model.getValueAt(count, 3).toString()); // buy quantity
					numdata.add(model.getValueAt(count, 5).toString());
					//Updating sales record
					strSQL = "INSERT INTO imssalesrecord(cname,productname,qty,datetime,phone,invoice_number,cost) "
							+ "VALUES ('" + JTFCustomerName.getText() + "','" + numdata.get(0) + "', '" + numdata.get(3)
							+ "',sysdate,'" + JTFPhone.getText() + "','" + JTFInvoiceNumber.getText() + "','" + numdata.get(4) +"')";
					numdata.clear();
					quantity = Integer.parseInt(model.getValueAt(count, 2).toString())
							- Integer.parseInt(model.getValueAt(count, 3).toString());
					//System.out.println("Updated quantity =" + quantity);
					
					//Update product quantity
					strUpdateProductTable = "UPDATE imsproducts SET quantity =" + quantity + " WHERE PRODUCTNAME ='"
							+ model.getValueAt(count, 0).toString() + "'";
					
					try {
						stSale.executeUpdate(strSQL);
						stSale.executeUpdate(strUpdateProductTable);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//Update customer record if new customer added
				strUpdateCustomerTable = "INSERT INTO imscustomer(cname,phone,datetime) values('"
						+ JTFCustomerName.getText() + "','" + JTFPhone.getText() + "',sysdate)";
				/*strUpdateInvoiceTable = "INSERT INTO imsinvoice values('"
						+ JTFCustID.getText() + "',sysdate,'" + JTFInvoiceNumber.getText() + "',sysdate,'" + JTFGrandTotal.getText() + "')";*/
				
				commitTable = "commit";
				String getCustId = "Select MAX(cid) as custID from imscustomer";
				try {
					rsSale3 = cnSale.createStatement().executeQuery(getCustId);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if(cuscount>0){     //If customer exists
					try {
						strUpdateRevenueTable = "INSERT INTO imsrevenuerecord values('"+JTFInvoiceNumber.getText()+"','"+JTFGrandTotal.getText()+"',to_date(sysdate,'dd/mm/yy'))";
						strUpdateInvoiceTable = "INSERT INTO imsinvoice values('"
								+ cid + "',sysdate,'" + JTFInvoiceNumber.getText() + "',sysdate,'" + JTFGrandTotal.getText() + "')";
						stSale.executeUpdate(strUpdateInvoiceTable);
						stSale.executeUpdate(strUpdateRevenueTable);
						stSale.executeUpdate(commitTable);
						JOptionPane.showMessageDialog(null, "The record has been successfully saved.",
								"Inventory Management System", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					cuscount=0;
				}
				else{
					try {
						if(rsSale3.next()){  
							String str = rsSale3.getString("custID");
							
							//If no customer records exist
							if(str==null){
								JTFCustID.setText(1+"");
							}
							else
							{
								int custID = Integer.parseInt(str);
								JTFCustID.setText((custID+1)+"");
							}
						}
						strUpdateRevenueTable = "INSERT INTO imsrevenuerecord values('"+JTFInvoiceNumber.getText()+"','"+JTFGrandTotal.getText()+"',to_date(sysdate,'dd/mm/yy'))";
						strUpdateInvoiceTable = "INSERT INTO imsinvoice values('"
								+ JTFCustID.getText() + "',sysdate,'" + JTFInvoiceNumber.getText() + "',sysdate,'" + JTFGrandTotal.getText() + "')";
						stSale.executeUpdate(strUpdateCustomerTable);
						stSale.executeUpdate(strUpdateRevenueTable);
						stSale.executeUpdate(strUpdateInvoiceTable);
						stSale.executeUpdate(commitTable);
						JOptionPane.showMessageDialog(null, "The record has been successfully saved.",
								"Inventory Management System", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			//Calculate Grand Total of Invoice Items
			if (srcObj == "total") {
				Double total = 0d;
				int noOfRows = model.getRowCount();
				for (int i = 0; i < noOfRows; i++) {
					int qty = Integer.parseInt(model.getValueAt(i, 3).toString());
					//int sellingprice = Integer.parseInt(model.getValueAt(i, 1).toString());
					Double disprice = Double.parseDouble(model.getValueAt(i, 4).toString());
					Double sprice = qty * disprice;
					model.setValueAt(sprice, i, 5);
					total = total + sprice;
				}
				model.fireTableDataChanged();
				JTFGrandTotal.setText(total+"");
			}
			//Reset all fields and clear table
			if(srcObj == "reset"){
				String generateInvoice = "SELECT MAX(invoice_number) as invoice from imsinvoice";
				try {
					rsSale1 = cnSale.createStatement().executeQuery(generateInvoice);
					if(rsSale1.next()){
						String str = rsSale1.getString("invoice");
						if(str==null){
							JTFInvoiceNumber.setText(101+"");
						}
						else
						{
							int invNumber = Integer.parseInt(str);
							JTFInvoiceNumber.setText((invNumber+1)+"");
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				model.setRowCount(0);
				try{
				if(cuscount==0){
					String getCustId = "Select MAX(cid) as custID from imscustomer";
					rsSale3 = cnSale.createStatement().executeQuery(getCustId);
					if(rsSale3.next()){
						String str = rsSale3.getString("custID");
						int custID = Integer.parseInt(str);
						JTFCustID.setText((custID+1)+"");
					}
				}
				}
				catch(SQLException e1){
					e1.printStackTrace();
				}
				JTFCustomerName.setText("");
				JTFPhone.setText("");
				JTFGrandTotal.setText("");
				count=0;
			}

		}
	};
	
	public static JTable CreateTable() {
		String[] values = new String[6];
		try {
			int rows = model.getRowCount();
			model.setRowCount(rows + 1);
			rsSale = stSale.executeQuery(strSQL);
			while (rsSale.next()) {
				//System.out.println(rsSale.getString("productname"));
				values[0] = "" + rsSale.getString("productname");
				values[1] = "" + rsSale.getString("sprice");
				values[2] = "" + rsSale.getString("quantity");
				values[3] = "" + rsSale.getString("discountedprice");
				if (count == 0) {
					model.setValueAt(values[0], 0, 0);
					model.setValueAt(values[1], 0, 1);
					model.setValueAt(values[2], 0, 2);
					model.setValueAt(null, 0, 3);
					model.setValueAt(values[3], 0, 4);
					model.setValueAt(null, 0, 5);
				} else {
					model.setValueAt(values[0], count, 0);
					model.setValueAt(values[1], count, 1);
					model.setValueAt(values[2], count, 2);
					model.setValueAt(null, count, 3);
					model.setValueAt(values[3], count, 4);
					model.setValueAt(null, count, 5);
					//count=0;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JTProductTable;

	}

	public static void reloadRecord(String srcSQL) {
		strSQL = srcSQL;
		JTProductTable = CreateTable();
		count++;
	}

	public static void setCustomer(String srcSQL) {
		strSQL = srcSQL;
		try {
			rsSale = stSale.executeQuery(strSQL);
			while (rsSale.next()) {
				JTFCustomerName.setText(rsSale.getString("cname"));
				JTFPhone.setText(rsSale.getString("phone"));
				cid = Integer.parseInt(rsSale.getString("cid"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
