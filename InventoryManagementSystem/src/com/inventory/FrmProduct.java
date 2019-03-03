package com.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import java.awt.PrintJob;
import java.util.*;
import java.text.*;

public class FrmProduct extends JInternalFrame{

	public static JScrollPane CusTableJSP = new JScrollPane();

	public static JPanel JPContainer = new JPanel();

	public static JTable JTCusTable;

	JLabel JLPicture1 = new JLabel(new ImageIcon("images/helper.png"));
	JLabel JLHelpText = new JLabel("To display a certain  \n record click the 'search button' and look for the record that you want.");

	JFrame JFParentFrame;

	JButton JBAddNew = new JButton("Add New",new ImageIcon("images/new.png"));
	JButton JBModify = new JButton("Modify",new ImageIcon("images/modify.png"));
	JButton JBSearch = new JButton("Search",new ImageIcon("images/search.png"));
	JButton JBPrint = new JButton("Print",new ImageIcon("images/print.png"));
	JButton JBDelete = new JButton("Delete",new ImageIcon("images/delete.png"));
	JButton JBReload = new JButton("Reload",new ImageIcon("images/reload.png"));

	Connection cnCus;

	public static Statement stCus;

	public static ResultSet rsCus;

	public static String strSQL;
	public static String Content[][];

	public static int rowNum = 0;
	public static int total = 0;

	boolean goEOF;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();

	public FrmProduct(Connection srcCN,JFrame getParentFrame) throws SQLException{
		
		super("Product Records",false,true,false,true);
		JPContainer.setLayout(null);

		JFParentFrame = getParentFrame;

		cnCus = srcCN;
		stCus = cnCus.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		strSQL = "SELECT * FROM tblItem";

	
		JLPicture1.setBounds(5,5,48,48);
		JPContainer.add(JLPicture1);

		
		JLHelpText.setBounds(55,5,570,48);
		JLHelpText.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLHelpText);

		
		JTCusTable=CreateTable();
		CusTableJSP.getViewport().add(JTCusTable);
		CusTableJSP.setBounds(5,55,727,320);
		JPContainer.add(CusTableJSP);

		
		JBAddNew.setBounds(5,382,105,25);
		JBAddNew.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBAddNew.setMnemonic(KeyEvent.VK_A);
		JBAddNew.addActionListener(JBActionListener);
		JBAddNew.setActionCommand("add");
		JPContainer.add(JBAddNew);

		
		JBModify.setBounds(112,382,99,25);
		JBModify.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBModify.setMnemonic(KeyEvent.VK_M);
		JBModify.addActionListener(JBActionListener);
		JBModify.setActionCommand("modify");
		JPContainer.add(JBModify);

		
		JBSearch.setBounds(212,382,99,25);
		JBSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBSearch.setMnemonic(KeyEvent.VK_S);
		JBSearch.addActionListener(JBActionListener);
		JBSearch.setActionCommand("search");
		JPContainer.add(JBSearch);

		
		JBPrint.setBounds(312,382,99,25);
		JBPrint.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBPrint.setMnemonic(KeyEvent.VK_P);
		JBPrint.addActionListener(JBActionListener);
		JBPrint.setActionCommand("print");
		JPContainer.add(JBPrint);

		
		JBDelete.setBounds(413,382,105,25);
		JBDelete.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBDelete.setMnemonic(KeyEvent.VK_D);
		JBDelete.addActionListener(JBActionListener);
		JBDelete.setActionCommand("delete");
		JPContainer.add(JBDelete);

		
		JBReload.setBounds(627,382,105,25);
		JBReload.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReload.setMnemonic(KeyEvent.VK_R);
		JBReload.addActionListener(JBActionListener);
		JBReload.setActionCommand("reload");
		JPContainer.add(JBReload);

		
		getContentPane().add(JPContainer);
		setSize(747,450);
		setLocation((screen.width - 747)/2,((screen.height-450)/2)-45);
		setFrameIcon(new ImageIcon("images/customer.png"));

	}

	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			
			if(srcObj=="add"){
				JDialog JDAdd = new frm_add_edit_product(true,JFParentFrame,cnCus,"");
				JDAdd.show();
			
			}else if(srcObj=="modify"){
				if(total != 0){
					try{
							if(JTCusTable.getValueAt(JTCusTable.getSelectedRow(),JTCusTable.getSelectedColumn()) != null){
								JDialog JDEdit = new frm_add_edit_product(false,JFParentFrame,cnCus,"SELECT * FROM tblItems WHERE ItemNo = " + JTCusTable.getValueAt(JTCusTable.getSelectedRow(),1));
								JDEdit.show();

							}
					}catch(Exception sqlE){
						if(sqlE.getMessage() != null){
							System.out.println(sqlE.getMessage());
						}else{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to modify.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}

					}
				}
			}else if(srcObj=="search"){
				JDialog JDSearchRec = new FrmSearchProduct(JFParentFrame);
				JDSearchRec.isVisible();
			
			}else if(srcObj=="print"){
				if(total != 0){
					try{
							if(JTCusTable.getValueAt(JTCusTable.getSelectedRow(),JTCusTable.getSelectedColumn()) != null){
								clsPublicMethods PrintingClass = new clsPublicMethods();
								ResultSet rsPrint = stCus.executeQuery("SELECT * FROM tblItems WHERE ItemNo = " + JTCusTable.getValueAt(JTCusTable.getSelectedRow(),1));
								if(rsPrint.next()==true){
									String RecordToPrint = "";
									java.util.Date CurrentDate = new java.util.Date();
									SimpleDateFormat SDFDateFormatter = new SimpleDateFormat("MMM. dd, yyyy",Locale.getDefault());

									RecordToPrint += "                            C U S T O M E R   R E C O R D							 \n";
									RecordToPrint += "                              " + SDFDateFormatter.format(CurrentDate).toString() + "\n\n\n";


									RecordToPrint += "___________________________________________________________________________________\n\n\n";

									RecordToPrint += " Product ID: " + rsPrint.getString("ProductID") + "                 Company Name: " + rsPrint.getString("CompanyName") + "\n";
									RecordToPrint += "___________________________________________________________________________________\n";
									RecordToPrint += "___________________________________________________________________________________\n\n";

									RecordToPrint += " Contact Person: " + rsPrint.getString("ContactName") + "\n";
									RecordToPrint += " Contact Title: " + rsPrint.getString("ContactTitle") + "\n";
									RecordToPrint += " Address: " + rsPrint.getString("Address") + "\n";
									RecordToPrint += " City: " + rsPrint.getString("CityTown") + "\n";
									RecordToPrint += " State/Province: " + rsPrint.getString("StateProvince") + "\n";
									RecordToPrint += " Zip Code: " + rsPrint.getString("ZipCode") + "\n";
									RecordToPrint += " Country: " + rsPrint.getString("Country") + "\n";
									RecordToPrint += " Phone: " + rsPrint.getString("Phone") + "\n";
									RecordToPrint += " Fax: " + rsPrint.getString("Fax") + "\n\n";

									RecordToPrint += "___________________________________________________________________________________\n\n";

									PrintingClass.printRecord(RecordToPrint,JFParentFrame);

									CurrentDate=null;
									SDFDateFormatter=null;
									RecordToPrint=null;

								}else{
									JOptionPane.showMessageDialog(null,"The selected record has been change since last modified. Please click the 'Reload' button and try again!","No record to print",JOptionPane.WARNING_MESSAGE);
								}
								//Dispose the variable
								rsPrint=null;

							}
					}catch(Exception sqlE){
						if(sqlE.getMessage() != null){
							System.out.println(sqlE.getMessage());
						}else{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to print.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			
			}else if(srcObj=="delete"){
				if(total != 0){
					try{
						if(JTCusTable.getValueAt(JTCusTable.getSelectedRow(),JTCusTable.getSelectedColumn()) != null){
							String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the selected record?","Delete Record",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,ObjButtons,ObjButtons[1]);
							if(PromptResult==0){
								stCus.execute("DELETE * FROM tblItems WHERE ItemIndex = " + JTCusTable.getValueAt(JTCusTable.getSelectedRow(),0));
								reloadRecord();
								JOptionPane.showMessageDialog(null,"Record has been successfully deleted.","Comfirm Delete",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}catch(Exception sqlE){
						if(sqlE.getMessage()!=null){
							JOptionPane.showMessageDialog(null,"You cannot delete this customer because it already have an invoice transaction.\nIn order to delete this customer, delete its invoice first.","Comfirm Delete",JOptionPane.WARNING_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to deleted.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}else if(srcObj=="reload"){
				reloadRecord();
			}
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {
			"Index","Item No","Description","Sales Price","On Hand" ,"On Order"
		};
		try{
			rsCus = stCus.executeQuery(strSQL);
			total = 0;
			
			rsCus.afterLast();
			
			if(rsCus.previous())total = rsCus.getRow();
			
			rsCus.beforeFirst();
			if(total > 0){
				Content = new String[total][6];
				while(rsCus.next()){
					Content[rowNum][0] = "" + rsCus.getString("ItemIndex");
					Content[rowNum][1] = "" + rsCus.getString("ItemNo");
					Content[rowNum][2] = "" + rsCus.getString("Description");
					Content[rowNum][3] = "" + rsCus.getString("SalesPrice");
					Content[rowNum][4] = "" + rsCus.getString("QtyOnHand");
					Content[rowNum][5] = "" + rsCus.getString("QtyOnOrder");
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

	public static void reloadRecord(String srcSQL){
				strSQL = srcSQL;
				CusTableJSP.getViewport().remove(JTCusTable);
				JTCusTable=CreateTable();
				CusTableJSP.getViewport().add(JTCusTable);
				JPContainer.repaint();
	}
	public static void reloadRecord(){
				CusTableJSP.getViewport().remove(JTCusTable);
				JTCusTable=CreateTable();
				CusTableJSP.getViewport().add(JTCusTable);
				JPContainer.repaint();
	}

}
