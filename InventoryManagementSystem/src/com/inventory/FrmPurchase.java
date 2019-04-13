package com.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import java.awt.PrintJob;
import java.util.*;
import java.text.*;

public class FrmPurchase extends JInternalFrame{

	public static JScrollPane PurTableJSP = new JScrollPane();

	public static JPanel JPContainer = new JPanel();

	public static JTable JTPurTable;

	JLabel JLPicture1 = new JLabel(new ImageIcon("images/helper.png"));
	JLabel JLHelpText = new JLabel("To display a certain  \n record click the 'search button' and look for the record that you want.");

	JFrame JFParentFrame;

	JButton JBAddNew = new JButton("New Order",new ImageIcon("images/new.png"));
	JButton JBStatus = new JButton("Update Status");
	JButton JBReload = new JButton("Reload",new ImageIcon("images/reload.png"));
	JButton JBView = new JButton("View Details");
	
	Connection cnPur;

	public static Statement stPur;

	public static ResultSet rsPur;

	public static String strSQL;
	public static String Content[][];

	public static int rowNum = 0;
	public static int total = 0;

	boolean goEOF;

	Dimension screen = 	Toolkit.getDefaultToolkit().getScreenSize();

	public FrmPurchase(Connection srcCon,JFrame getParentFrame) throws SQLException{
		
		super("Purchase Records",false,true,false,true);
		

		JPContainer.setLayout(null);

		JFParentFrame = getParentFrame;

		cnPur = srcCon;
		stPur = cnPur.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		strSQL = "SELECT orderid,product,quantity,supplierid,suppliername,suppliername,cost,to_char(orderdate, 'DD.MM.YYYY hh:mi AM') dt,status,suppliercity,to_char(lastupdated, 'DD.MM.YYYY hh:mi AM')last FROM view_purchase";

		JLPicture1.setBounds(5,5,48,48);
		JPContainer.add(JLPicture1);

		JLHelpText.setBounds(55,5,570,48);
		JLHelpText.setFont(new Font("Dialog", Font.PLAIN, 12));
		JPContainer.add(JLHelpText);

		JTPurTable=CreateTable();
		PurTableJSP.getViewport().add(JTPurTable);
		PurTableJSP.setBounds(5,55,830,320);
		JPContainer.add(PurTableJSP);

		JBAddNew.setBounds(25,382,150,25);
		JBAddNew.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBAddNew.setMnemonic(KeyEvent.VK_A);
		JBAddNew.addActionListener(JBActionListener);
		JBAddNew.setActionCommand("add");
		JPContainer.add(JBAddNew);

		JBStatus.setBounds(230, 382, 150, 25);
		JBStatus.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBStatus.setMnemonic(KeyEvent.VK_A);
		JBStatus.addActionListener(JBActionListener);
		JBStatus.setActionCommand("status");
		JPContainer.add(JBStatus);

		JBView.setBounds(410,382,120,25);
		JBView.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBView.setMnemonic(KeyEvent.VK_D);
		JBView.addActionListener(JBActionListener);
		JBView.setActionCommand("view");
		JPContainer.add(JBView);
		
		JBReload.setBounds(600,382,100,25);
		JBReload.setFont(new Font("Dialog", Font.PLAIN, 12));
		JBReload.setMnemonic(KeyEvent.VK_R);
		JBReload.addActionListener(JBActionListener);
		JBReload.setActionCommand("reload");
		JPContainer.add(JBReload);

		getContentPane().add(JPContainer);
		setSize(850,450);
		setLocation((screen.width - 747)/2,((screen.height-450)/2)-45);
		setFrameIcon(new ImageIcon("images/Warehouse.png"));	

	}

	ActionListener JBActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String srcObj = e.getActionCommand();
			
			if(srcObj=="add"){
				JDialog JDAdd = new frm_add_new_order(true,JFParentFrame,cnPur,"");
				JDAdd.show();
			
			}/*else if(srcObj=="modify"){
				if(total != 0){
					try{
							if(JTPurTable.getValueAt(JTPurTable.getSelectedRow(),JTPurTable.getSelectedColumn()) != null){
								JDialog JDEdit = new frm_add_edit_warehouse(false,JFParentFrame,cnPur,"SELECT * FROM tblWarehouse WHERE WarehouseID = " + JTPurTable.getValueAt(JTPurTable.getSelectedRow(),1));
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
				JDialog JDSearchRec = new FrmSearchWarehouse(JFParentFrame);
				JDSearchRec.show(true);
			//Print Record
			}else if(srcObj=="print"){
				if(total != 0){
					try{
							if(JTPurTable.getValueAt(JTPurTable.getSelectedRow(),JTPurTable.getSelectedColumn()) != null){
								clsPublicMethods PrintingClass = new clsPublicMethods();
								ResultSet rsPrint = stPur.executeQuery("SELECT * FROM tblWarehouse WHERE WarehouseID = " + JTPurTable.getValueAt(JTPurTable.getSelectedRow(),1));
								if(rsPrint.next()==true){
									String RecordToPrint = "";
									java.util.Date CurrentDate = new java.util.Date();
									SimpleDateFormat SDFDateFormatter = new SimpleDateFormat("MMM. dd, yyyy",Locale.getDefault());

									RecordToPrint += "                 W A R E H O U S E   R E C O R D\n";
									RecordToPrint += "                 " + SDFDateFormatter.format(CurrentDate).toString() + "\n\n\n";


									RecordToPrint += "____________________________________________________\n\n\n";

									RecordToPrint += " Warehouse ID: " + rsPrint.getString("WarehouseID") + "                 Warehouse Name: " + rsPrint.getString("WarehouseName") + "\n";
									RecordToPrint += "____________________________________________________\n";
									RecordToPrint += "_____________________________________________________\n\n";


									RecordToPrint += " Contact Person: " + rsPrint.getString("ContactPerson") + "\n";
									RecordToPrint += " Contact Title: " + rsPrint.getString("ContactTitle") + "\n";
									RecordToPrint += " CityTown: " + rsPrint.getString("CityTown") + "\n";
									RecordToPrint += " State/Province: " + rsPrint.getString("StateProvince") + "\n";
									RecordToPrint += " Zip Code: " + rsPrint.getString("ZipCode") + "\n";
									RecordToPrint += " Phone: " + rsPrint.getString("Phone") + "\n";
									RecordToPrint += " Fax: " + rsPrint.getString("Fax") + "\n\n";

									RecordToPrint += "______________________________________________________\n\n";

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
						if(JTPurTable.getValueAt(JTPurTable.getSelectedRow(),JTPurTable.getSelectedColumn()) != null){
							String ObjButtons[] = {"Yes","No"};
							int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete the selected record?","Delete Record",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,ObjButtons,ObjButtons[1]);
							if(PromptResult==0){
								stPur.execute("DELETE FROM tblWarehouse WHERE WarehouseIndex = " + JTPurTable.getValueAt(JTPurTable.getSelectedRow(),0));
								reloadRecord();
								JOptionPane.showMessageDialog(null,"Record has been successfully deleted.","Comfirm Delete",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}catch(Exception sqlE){
						if(sqlE.getMessage()!=null){
							JOptionPane.showMessageDialog(null,"You cannot delete this Warehouse because it already have an invoice transaction.\nIn order to delete this Warehouse, delete its invoice first.","Comfirm Delete",JOptionPane.WARNING_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null,"Please select a record in the list to deleted.","No Record Selected",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			
			}*/
			else if(srcObj=="status"){
				try{
					if(JTPurTable.getValueAt(JTPurTable.getSelectedRow(),JTPurTable.getSelectedColumn()) != null){
						JDialog JDEdit = new FrmUpdateStatus(JFParentFrame,cnPur,"SELECT orderid,productname,to_char(time, 'DD.MM.YYYY hh:mi AM') dt,currentstatus,comments FROM imsstatus WHERE orderid = '" + JTPurTable.getValueAt(JTPurTable.getSelectedRow(),0)+"'");
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
			else if(srcObj=="view"){
				if(JTPurTable.getValueAt(JTPurTable.getSelectedRow(),JTPurTable.getSelectedColumn()) != null){
					JDialog JDEdit = new FrmViewOrderDetails(JFParentFrame,cnPur,"SELECT orderid,product,quantity,supplierid,suppliername,suppliername,cost,to_char(orderdate, 'DD.MM.YYYY hh:mi AM') dt,status,suppliercity FROM imspurchase WHERE orderid = '" + JTPurTable.getValueAt(JTPurTable.getSelectedRow(),0)+"'");
					JDEdit.show();
				}
			}
			else if(srcObj=="reload"){
				reloadRecord();
			}
		}
	};
	
	public static  JTable CreateTable(){
		String ColumnHeaderName[] = {
			"OrderID","ProductName","Quantity","SupplierName","SupplierID","Cost","OrderDate","Status","LastUpdated"
		};
		try{
			rsPur = stPur.executeQuery(strSQL);
			total = 0;
			//Move to the last record
			rsPur.afterLast();
			//Get the current record position
			if(rsPur.previous())total = rsPur.getRow();
			//Move back to the first record;
			rsPur.beforeFirst();
			if(total != 0){
				Content = new String[total][9];
				while(rsPur.next()){
					Content[rowNum][0] = "" + rsPur.getString("orderid");
					Content[rowNum][1] = "" + rsPur.getString("product");
					Content[rowNum][2] = "" + rsPur.getString("quantity");
					Content[rowNum][3] = "" + rsPur.getString("supplierName");
					Content[rowNum][4] = "" + rsPur.getString("supplierid");
					Content[rowNum][5] = "" + rsPur.getString("cost");
					Content[rowNum][6] = "" + rsPur.getString("dt");
					Content[rowNum][7] = "" + rsPur.getString("status");
					Content[rowNum][8] = "" + rsPur.getString("last");
					rowNum++;
				}
			}else{
				Content = new String[0][9];
				Content[0][0] = " ";
				Content[0][1] = " ";
				Content[0][2] = " ";
				Content[0][3] = " ";
				Content[0][4] = " ";
				Content[0][5] = " ";
				Content[0][6] = " ";
				Content[0][7] = " ";
				Content[0][8] = " ";
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
		
		NewTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		NewTable.getColumnModel().getColumn(1).setPreferredWidth(110);
		NewTable.getColumnModel().getColumn(2).setPreferredWidth(55);
		NewTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		NewTable.getColumnModel().getColumn(4).setPreferredWidth(70);
		NewTable.getColumnModel().getColumn(5).setPreferredWidth(50);
		NewTable.getColumnModel().getColumn(6).setPreferredWidth(120);
		NewTable.getColumnModel().getColumn(7).setPreferredWidth(70);
		NewTable.getColumnModel().getColumn(8).setPreferredWidth(130);
		
		ColumnHeaderName=null;
		Content=null;

		rowNum = 0;

		return NewTable;
	}

	public static void reloadRecord(String srcSQL){
				strSQL = srcSQL;
				PurTableJSP.getViewport().remove(JTPurTable);
				JTPurTable=CreateTable();
				PurTableJSP.getViewport().add(JTPurTable);
				JPContainer.repaint();
	}

	public static void reloadRecord(){
				PurTableJSP.getViewport().remove(JTPurTable);
				JTPurTable=CreateTable();
				PurTableJSP.getViewport().add(JTPurTable);
				JPContainer.repaint();
	}

}