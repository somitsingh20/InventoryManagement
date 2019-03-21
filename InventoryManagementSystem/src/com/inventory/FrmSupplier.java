package com.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.text.*;

public class FrmSupplier extends JInternalFrame{

	public static JScrollPane SupTableJSP = new JScrollPane();

	public static JPanel JPContainer = new JPanel();

	public static JTable JTSupTable;

	JLabel JLPicture1 = new JLabel(new ImageIcon("images/helper.png"));
	JLabel JLHelpText = new JLabel("To display a certain  \n record click the 'search button' and look for the record that you want.");

	JFrame JFParentFrame;

	JButton JBAddNew = new JButton("Add New",new ImageIcon("images/new.png"));
	JButton JBModify = new JButton("Modify",new ImageIcon("images/modify.png"));
	JButton JBSearch = new JButton("Search",new ImageIcon("images/search.png"));
	JButton JBPrint = new JButton("Print",new ImageIcon("images/print.png"));
	JButton JBDelete = new JButton("Delete",new ImageIcon("images/delete.png"));
	JButton JBReload = new JButton("Reload",new ImageIcon("images/reload.png"));

	Connection cnSup;

	public static Statement stSup;

	public static ResultSet rsSup;

	public static String strSQL;
	public static String Content[][];

	public static int rowNum = 0;
	public static int total = 0;

	boolean goEOF;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();

	public FrmSupplier(Connection srcCon,JFrame getParentFrame) throws SQLException{
		
		super("Supplier Records",false,true,false,true);
		
		JPContainer.setLayout(null);

		JFParentFrame = getParentFrame;

		cnSup = srcCon;
		stSup = cnSup.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		strSQL = "SELECT * FROM imssupplier";

		
		JLPicture1.setBounds(5,5,48,48);
		JPContainer.add(JLPicture1);

		
		JLHelpText.setBounds(55,5,570,48);
		JLHelpText.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLHelpText);

		
		JTSupTable=CreateTable();
		SupTableJSP.getViewport().add(JTSupTable);
		SupTableJSP.setBounds(5,55,727,320);
		JPContainer.add(SupTableJSP);

		
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
		setFrameIcon(new ImageIcon("images/Supplier.png"));
		

	}

	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			
			if(srcObj=="add"){
				JDialog JDAdd = new frm_add_edit_supplier(true,JFParentFrame,cnSup,"");
				JDAdd.show();
			//Modify Record
			}else if(srcObj=="modify"){
				if(total != 0){
					try{
							if(JTSupTable.getValueAt(JTSupTable.getSelectedRow(),JTSupTable.getSelectedColumn()) != null){
								JDialog JDEdit = new frm_add_edit_supplier(false,JFParentFrame,cnSup,"SELECT * FROM imssupplier WHERE sid = " + JTSupTable.getValueAt(JTSupTable.getSelectedRow(),0));
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
			//Search Record
			}else if(srcObj=="search"){
				JDialog JDSearchRec = new FrmSearchSupplier(JFParentFrame);
				JDSearchRec.show(true);
			//Print Record
			}else if(srcObj=="print"){
				if(total != 0){
					try{
						if(JTSupTable.getValueAt(JTSupTable.getSelectedRow(),JTSupTable.getSelectedColumn()) != null){
							clsPublicMethods PrintingClass = new clsPublicMethods();
							ResultSet rsPrint = stSup.executeQuery("SELECT * FROM imssupplier WHERE sid = " + JTSupTable.getValueAt(JTSupTable.getSelectedRow(),0));
							if(rsPrint.next()==true){
								String RecordToPrint = "";
								java.util.Date CurrentDate = new java.util.Date();
								SimpleDateFormat SDFDateFormatter = new SimpleDateFormat("MMM. dd, yyyy",Locale.getDefault());

								RecordToPrint += "                          S U P P L I E R   R E C O R D							 \n";
								RecordToPrint += "                              " + SDFDateFormatter.format(CurrentDate).toString() + "\n\n\n";


								RecordToPrint += "___________________________________________________________________________________\n\n\n";

								RecordToPrint += " Supplier ID: " + rsPrint.getString("SupplierID") + "                 Company Name: " + rsPrint.getString("CompanyName") + "\n";
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
								RecordToPrint += " Website: " + rsPrint.getString("Website") + "\n\n";

								RecordToPrint += "___________________________________________________________________________________\n\n";

								PrintingClass.printRecord(RecordToPrint,JFParentFrame);

								CurrentDate=null;
								SDFDateFormatter=null;
								RecordToPrint=null;

							}else{
								JOptionPane.showMessageDialog(null,"The selected record has been change since last modified. Please click the 'Reload' button and try again!","No record to print",JOptionPane.WARNING_MESSAGE);
							}
								
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
						if(JTSupTable.getValueAt(JTSupTable.getSelectedRow(),JTSupTable.getSelectedColumn()) != null){
							String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the selected record?","Delete Record",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,ObjButtons,ObjButtons[1]);
							if(PromptResult==0){
								stSup.execute("DELETE FROM imssupplier WHERE sid = " + JTSupTable.getValueAt(JTSupTable.getSelectedRow(),0));
								reloadRecord();
								JOptionPane.showMessageDialog(null,"Record has been successfully deleted.","Comfirm Delete",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}catch(Exception sqlE){
						if(sqlE.getMessage()!=null){
							JOptionPane.showMessageDialog(null,"You cannot delete this Supplier because it already have an invoice transaction.\nIn order to delete this Supplier, delete its invoice first.","Comfirm Delete",JOptionPane.WARNING_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to deleted.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			//Reload Record
			}else if(srcObj=="reload"){
				reloadRecord();
			
			}
		}
	};

	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {
			"Supplier ID","Supplier Name","Location","Phone Number"
		};
		
		try{
			rsSup = stSup.executeQuery(strSQL);
			total = 0;
			
			rsSup.afterLast();
		
			if(rsSup.previous())total = rsSup.getRow();
			rsSup.beforeFirst();
			if(total > 0){
				Content = new String[total][4];
				while(rsSup.next()){
					Content[rowNum][0] = "" + rsSup.getString("sid");
					Content[rowNum][1] = "" + rsSup.getString("supplier");
					Content[rowNum][2] = "" + rsSup.getString("location");
					Content[rowNum][3] = "" + rsSup.getString("pnumber");
					rowNum++;
				}
			}else{
				Content = new String[0][4];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
				Content[0][3] = " ";
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
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		
		
		ColumnHeaderName=null;
		Content=null;

		rowNum = 0;

		return NewTable;
	}


	public static void reloadRecord(String srcSQL){
				strSQL = srcSQL;
				SupTableJSP.getViewport().remove(JTSupTable);
				JTSupTable=CreateTable();
				SupTableJSP.getViewport().add(JTSupTable);
				JPContainer.repaint();
	}
	public static void reloadRecord(){
				SupTableJSP.getViewport().remove(JTSupTable);
				JTSupTable=CreateTable();
				SupTableJSP.getViewport().add(JTSupTable);
				JPContainer.repaint();
	}

}
