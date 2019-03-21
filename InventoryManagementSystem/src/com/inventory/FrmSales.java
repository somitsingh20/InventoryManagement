package com.inventory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrmSales extends JInternalFrame {
	
	public static JScrollPane ProductTableJSP = new JScrollPane();

	public static JPanel JPContainer = new JPanel();

	public static JTable JTProductTable;
	
	public static JTable table;
	//public static JTable NewTable;
	
	JLabel JLPicture1 = new JLabel(new ImageIcon("images/helper.png"));
	JLabel JLHelpText = new JLabel("To display a certain  \n record click the 'search button' and look for the record that you want.");
	
	JFrame JFParentFrame;
	
	JButton JBAddNew = new JButton("Add",new ImageIcon("images/new.png"));
	JButton JBSearchCustomer = new JButton("Search Customer",new ImageIcon("images/search.png"));
	JButton JBSearchProduct = new JButton("Add Product",new ImageIcon("images/search.png"));
	JButton JBPrint = new JButton("Print Invoice",new ImageIcon("images/print.png"));
	JButton JBGrandTotal = new JButton("Grand Total");
	
	JLabel JLInvoiceNumber = new JLabel("Invoice Number");
	JLabel JLCustomerName = new JLabel("Customer Name");
	JLabel JLGrandTotal = new JLabel("Grand Total");
	JLabel JLPhone = new JLabel("Phone No.");
	
	public static JTextField JTFCustomerName = new JTextField();
	public static JTextField JTFInvoiceNumber = new JTextField();
	public static JTextField JTFGrandTotal = new JTextField();
	public static JTextField JTFPhone = new JTextField();
	
	public static Connection cnSale;
	public static Statement stSale;
	public static ResultSet rsSale;
	public static ResultSet rsSale1;
	
	public static String Content[][];
	public static String strSQL;
	public static int rowNum = 0;
	public static int total = 0;
	public static int count=0;
	boolean goEOF;
	
	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();
	
	static DefaultTableModel model = new DefaultTableModel(new Object[]{"ProductName","Unit Price","AvailableQty","Purchase Qty","Total Price"}, 0);
	
	//constructor
	public FrmSales(Connection srcCon,JFrame getParentFrame) throws SQLException {
		
		super("New Sale",false,true,false,true);
		
		JPContainer.setLayout(null);
		JFParentFrame = getParentFrame;
		
		cnSale = srcCon;
		stSale = cnSale.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		String generateInvoice = "SELECT MAX(invoice_number) as invoice from imscustomer";
		rsSale1 = srcCon.createStatement().executeQuery(generateInvoice);
		if(rsSale1.next()){
			//System.out.println(rsSale1.getString("invoice"));
			int getMaxNumber = Integer.parseInt(rsSale1.getString("invoice"));
			//System.out.println(invoiceNumber);
			JTFInvoiceNumber.setText((getMaxNumber+1)+"");
		}
		
		JTable table = new JTable(model);
		
		//JTable table = new JTable(new DefaultTableModel(new Object[]{"ProductName","Unit Price","Quantity","Total Price"}, 0));
		JLCustomerName.setBounds(5, 5, 100, 20);
		JLCustomerName.setFont(new Font("Dialog",Font.PLAIN,12));
		JTFCustomerName.setBounds(105, 5, 80, 20);
		JTFCustomerName.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLCustomerName);
		JPContainer.add(JTFCustomerName);
		
		JLInvoiceNumber.setBounds(200, 5, 100, 20);
		JLInvoiceNumber.setFont(new Font("Dialog",Font.PLAIN,12));
		JTFInvoiceNumber.setBounds(300, 5, 80, 20);
		JTFInvoiceNumber.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLInvoiceNumber);
		JPContainer.add(JTFInvoiceNumber);
		
		JLPhone.setBounds(5, 30, 100, 20);
		JLPhone.setFont(new Font("Dialog",Font.PLAIN,12));
		JTFPhone.setBounds(105, 30, 80, 20);
		JTFPhone.setFont(new Font("Dialog",Font.PLAIN,12));
		
		JPContainer.add(JLPhone);
		JPContainer.add(JTFPhone);
		
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
		
		//JTProductTable=CreateTable();
		ProductTableJSP.getViewport().add(table);
		ProductTableJSP.setBounds(5,90,400,240);
		JPContainer.add(ProductTableJSP);
		
		JBGrandTotal.setBounds(160, 340, 120, 20);
		JBGrandTotal.setFont(new Font("Dialog",Font.BOLD,14));
		JBGrandTotal.addActionListener(JBActionListener);
		JBGrandTotal.setActionCommand("total");
		
		JTFGrandTotal.setBounds(312, 340, 80, 20);
		JTFGrandTotal.setFont(new Font("Dialog",Font.BOLD,14));
		
		JPContainer.add(JBGrandTotal);
		JPContainer.add(JTFGrandTotal);
		
		JBAddNew.setBounds(60, 390, 80, 20);
		JBAddNew.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBAddNew.addActionListener(JBActionListener);
		JBAddNew.setActionCommand("add");
		
		JBPrint.setBounds(180, 390, 150, 20);
		JBPrint.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBPrint.addActionListener(JBActionListener);
		JBPrint.setActionCommand("print");
		
		JPContainer.add(JBAddNew);
		JPContainer.add(JBPrint);
		
		getContentPane().add(JPContainer);
		setSize(420,460);
		setResizable(true);
		setLocation((screen.width - 420)/2,((screen.height-660)/2));
	}
	
	ActionListener JBActionListener = new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			if(srcObj=="searchCustomer"){
				JDialog JDSearchCus = new FrmSearchCustomer1(JFParentFrame);
				JDSearchCus.show(true);
				}
			if(srcObj=="searchProduct"){
				JDialog JDSearchRec = new FrmSearchAddProduct(JFParentFrame);
				JDSearchRec.show(true);
			}
			if(srcObj=="print"){
				System.out.println("Print called");
				int rCount = model.getRowCount();
				if(rCount != 0){
					try{
							//if(rCount > 0){
								clsPublicMethods PrintingClass = new clsPublicMethods();
								//ResultSet rsPrint = stCus.executeQuery("SELECT * FROM imsproducts WHERE pid = " + JTCusTable.getValueAt(JTCusTable.getSelectedRow(),0));
								//if(rsPrint.next()==true){
									String RecordToPrint = "";
									java.util.Date CurrentDate = new java.util.Date();
									SimpleDateFormat SDFDateFormatter = new SimpleDateFormat("MMM. dd, yyyy",Locale.getDefault());

									RecordToPrint += "                             I N V O I C E							 \n";
									//RecordToPrint += "                              " + SDFDateFormatter.format(CurrentDate).toString() + "\n\n\n";


									RecordToPrint += "___________________________________________________________________________\n\n\n";

									RecordToPrint += " 	 CustomerName " + JTFCustomerName.getText() + "                      Invoice No. " + JTFInvoiceNumber.getText() + "\n";
									RecordToPrint += "  Phone No. " + JTFPhone.getText() + "                    Date: " + SDFDateFormatter.format(CurrentDate).toString()+ "\n" ;
									RecordToPrint += "___________________________________________________________________________\n";
									RecordToPrint += " ProductName                                       Quantity    Price  \n ";
									RecordToPrint += "___________________________________________________________________________\n\n";
									
									RecordToPrint += " " + model.getValueAt(0, 0) + "                                             "+ model.getValueAt(0, 2)+"      "+model.getValueAt(0, 4)+"\n";
									RecordToPrint += " " + model.getValueAt(1, 0) + "                                             "+ model.getValueAt(1, 2)+"      "+model.getValueAt(1, 4)+"\n";
									//RecordToPrint += " p3 " + rsPrint.getString("sprice") + "\n";

									RecordToPrint += "___________________________________________________________________________\n\n";
									RecordToPrint += "                                              Grand Total : "+ JTFGrandTotal.getText() +"\n";
									RecordToPrint += "____________________________________________________________________________\n\n";

									PrintingClass.printRecord(RecordToPrint,JFParentFrame);

									CurrentDate=null;
									SDFDateFormatter=null;
									RecordToPrint=null;
								/*else{
									JOptionPane.showMessageDialog(null,"The selected record has been change since last modified. Please click the 'Reload' button and try again!","No record to print",JOptionPane.WARNING_MESSAGE);
								}*/
								
								//rsPrint=null;

							}
					catch(Exception sqlE){
						if(sqlE.getMessage() != null){
							System.out.println(sqlE.getMessage());
						}else{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to print.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			
			}
			if(srcObj=="add"){
				//System.out.println("Inside add action");
				ArrayList<String> numdata = new ArrayList<String>();
		        for (int count = 0; count < model.getRowCount(); count++){
		        	System.out.println("Inside for loop");
		              numdata.add(model.getValueAt(count, 0).toString());
		              numdata.add(model.getValueAt(count, 1).toString());
		              numdata.add(model.getValueAt(count, 2).toString());
		              numdata.add(model.getValueAt(count, 3).toString());
		              strSQL = "INSERT INTO imssalesrecord(cname,productname,qty,datetime,phone,invoice_number) " +
						   	        "VALUES ('" +
						   	        JTFCustomerName.getText() + "','" +
						   	        numdata.get(0) + "', '" +
						   	        numdata.get(3) + "',sysdate,'" +
						   	        JTFPhone.getText() + "','" +
						   	        JTFInvoiceNumber.getText() + "')";
		              numdata.clear();
		              try {
						stSale.executeUpdate(strSQL);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		        //System.out.println(numdata);
			}
			if(srcObj=="total"){
				int total=0;
				int noOfRows = model.getRowCount();
				for(int i=0;i<noOfRows;i++){
					int qty = Integer.parseInt(model.getValueAt(i, 3).toString());
					int sellingprice = Integer.parseInt(model.getValueAt(i, 1).toString());
					int sprice = qty*sellingprice;
					model.setValueAt(sprice, i, 4);
					total = total + sprice;
				}
				model.fireTableDataChanged();
				JTFGrandTotal.setText(total+" Rs");
			}
			
		}
	};
	
	public static  JTable CreateTable(){
		String[] values = new String[6];
			try {
				int rows = model.getRowCount();
				model.setRowCount(rows+1);
				rsSale = stSale.executeQuery(strSQL);
				while(rsSale.next()){
				values[0] = "" + rsSale.getString("productname");
				values[1] = "" + rsSale.getString("sprice");
				values[2] = "" + rsSale.getString("quantity");
				//values[3] = "" + rsSale.getString("totalprice");
				if(count==0){
					model.setValueAt(values[0], 0, 0);
					model.setValueAt(values[1], 0, 1);
					model.setValueAt(values[2], 0, 2);
					model.setValueAt(null, 0, 3);
					model.setValueAt(null, 0, 4);
				}
				else{
					model.setValueAt(values[0], count, 0);
					model.setValueAt(values[1], count, 1);
					model.setValueAt(values[2], count, 2);
					model.setValueAt(null, count, 3);
					model.setValueAt(null, count, 4);
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return JTProductTable;
		
			
	}
	public static void reloadRecord(String srcSQL){
		strSQL = srcSQL;
		//ProductTableJSP.getViewport().remove(JTProductTable);
		JTProductTable=CreateTable();
		//ProductTableJSP.getViewport().add(JTProductTable);
		//JPContainer.repaint();
		count++;
		}
		
	/*public static void reloadRecord(){
		ProductTableJSP.getViewport().remove(JTProductTable);
		JTProductTable=CreateTable();
		ProductTableJSP.getViewport().add(JTProductTable);
		JPContainer.repaint();
	}*/
	
	public static void setCustomer(String srcSQL){
		strSQL = srcSQL;
		try {
			rsSale = stSale.executeQuery(strSQL);
			while(rsSale.next()){
				JTFCustomerName.setText(rsSale.getString("cname"));
				JTFPhone.setText(rsSale.getString("phone"));
				//JTFInvoiceNumber.setText();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
