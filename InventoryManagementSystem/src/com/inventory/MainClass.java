package com.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class MainClass extends JFrame implements WindowListener {
	
		JPanel panel;
		JDesktopPane desk = new JDesktopPane(){
			ImageIcon icon = new ImageIcon("images/business.jpg");
			Image image = icon.getImage();

			Image newimage = image.getScaledInstance(1400, 650, Image.SCALE_SMOOTH);

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(newimage, 0, 0, this);
        }};
		
		JLabel StatusLabel = new JLabel("Inventory Management system",JLabel.CENTER);
		JLabel BusinessTitleLabel = new JLabel();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		String StrBusinessTitle;
		String DBDriver = "oracle.jdbc.driver.OracleDriver";
		String DBSource = "jdbc:oracle:thin:@localhost:1521/xe";
	    String DBUserName = "system";
		String DBPassword = "vikas";
		
		Connection con;
		
		FrmCustomer  FormCustomer;
		FrmSupplier  FormSupplier;
		FrmRevenueRecord  FormRevenueRecord;
		FrmWarehouse FormWarehouse;
		FrmProduct   FormProduct;
		FrmInvoice   FormInvoice;
		FrmSales 	 FormSales;
		
		FrmSplash FormSplash = new FrmSplash();
		
		Thread ThFormSplash = new Thread(FormSplash);
		
		public MainClass(){
			
			loadSplashScreen();
			
			FormSplash.dispose();
			
			StatusLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			
			StrBusinessTitle = "Business Title";
			
			BusinessTitleLabel.setText(StrBusinessTitle);
			BusinessTitleLabel.setHorizontalAlignment(JLabel.LEFT);
			BusinessTitleLabel.setForeground(new Color(166, 0, 0));
			
			addWindowListener(this);
			
			//Add image here
			desk.setBackground(Color.gray);
			desk.setBorder(BorderFactory.createEmptyBorder());
			
			panel = new JPanel(new BorderLayout());
			panel.setBackground(Color.gray);
			panel.setBorder(BorderFactory.createLoweredBevelBorder());
			panel.add(new JScrollPane(desk), BorderLayout.CENTER);
			
			getContentPane().add(CreateJToolBar(),BorderLayout.PAGE_START);
			getContentPane().add(panel,BorderLayout.CENTER);
			getContentPane().add(StatusLabel,BorderLayout.PAGE_END);
			
			setJMenuBar(CreateJMenuBar());
			setExtendedState(this.MAXIMIZED_BOTH);
			
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setIconImage(new ImageIcon("images/appicon.png").getImage());
			setLocation(0,0);
			setSize(screen);
			setResizable(true);

			setVisible(true);
			
			
			try{
				Class.forName(DBDriver);
				con = DriverManager.getConnection(DBSource,DBUserName ,DBPassword);
			}catch(ClassNotFoundException e)  {
	 			System.err.println("Failed to load driver");
	 			e.printStackTrace();
	 			System.exit(1);
	 		}
	 		catch(SQLException e){
	 			System.err.println("Unable to connect");
	 			e.printStackTrace();
	 			System.exit(1);
	 		}
			
		}
		
		private void loadSplashScreen() {
			ThFormSplash.start();
		}

		protected JMenuBar CreateJMenuBar() {
			
			JMenuBar NewJMenuBar = new JMenuBar();
			
			JMenu fileMenu = new JMenu("File");
			fileMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
			fileMenu.setMnemonic('F');
			fileMenu.setBackground(new Color(255,255,255));
			NewJMenuBar.add(fileMenu);
			
					JMenuItem ItmLockApp = new JMenuItem("lock Application");
					ItmLockApp.setFont(new Font("Dialog", Font.PLAIN, 12));
					ItmLockApp.setMnemonic('L');
					ItmLockApp.setIcon(new ImageIcon("images/lockapplication.png"));
					ItmLockApp.setAccelerator(
							KeyStroke.getKeyStroke(
								KeyEvent.VK_L,ActionEvent.CTRL_MASK
								)
						);
					ItmLockApp.setActionCommand("lockapp");
					ItmLockApp.addActionListener(JMenuActionListener);
					ItmLockApp.setBackground(new Color(255,255,255));
					
					
					JMenuItem ItmLoggOff = new JMenuItem("Logg Off");
					ItmLoggOff.setFont(new Font("Dialog", Font.PLAIN, 12));
					ItmLoggOff.setMnemonic('O');
					ItmLoggOff.setIcon(new ImageIcon("images/loggoff.png"));
					ItmLoggOff.setAccelerator(
							KeyStroke.getKeyStroke(
								KeyEvent.VK_O,ActionEvent.CTRL_MASK
								)
						);
					ItmLoggOff.setActionCommand("loggoff");
					ItmLoggOff.addActionListener(JMenuActionListener);
					ItmLoggOff.setBackground(new Color(255,255,255));
					
					JMenuItem ItmExit = new JMenuItem("Exit");
					ItmExit.setFont(new Font("Dialog", Font.PLAIN, 12));
					ItmExit.setMnemonic('E');
					ItmExit.setIcon(new ImageIcon("images/exit.png"));
					ItmExit.setAccelerator(
							KeyStroke.getKeyStroke(
								KeyEvent.VK_E,ActionEvent.CTRL_MASK
								)
						);
					ItmExit.setActionCommand("exit");
					ItmExit.addActionListener(JMenuActionListener);
					ItmExit.setBackground(new Color(255,255,255));
					
					fileMenu.add(ItmLockApp);
					fileMenu.addSeparator();
					fileMenu.add(ItmLoggOff);
					fileMenu.add(ItmExit);
			
			JMenu recordsMenu = new JMenu("Records");
			recordsMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
			recordsMenu.setMnemonic('R');
			recordsMenu.setBackground(new Color(255, 255, 255));
			NewJMenuBar.add(recordsMenu);
			
					JMenuItem itmCustomers = new JMenuItem("Customers");
					itmCustomers.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmCustomers.setMnemonic('C');
					itmCustomers.setIcon(new ImageIcon("images/customer.png"));  
					itmCustomers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
					itmCustomers.setActionCommand("cus");
					itmCustomers.addActionListener(JMenuActionListener);
					itmCustomers.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmCustomers);
					
					JMenuItem itmSupplier = new JMenuItem("Supplier");
					itmSupplier.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmSupplier.setMnemonic('S');
					itmSupplier.setIcon(new ImageIcon("images/supplier.png"));
					itmSupplier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
					itmSupplier.setActionCommand("sup");
					itmSupplier.addActionListener(JMenuActionListener);
					itmSupplier.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmSupplier);
					
					JMenuItem itmSalesRep = new JMenuItem("SalesRep");
					itmSalesRep.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmSalesRep.setMnemonic('R');
					itmSalesRep.setIcon(new ImageIcon("images/salesrep.png"));
					itmSalesRep.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
					itmSalesRep.setActionCommand("salesrep");
					itmSalesRep.addActionListener(JMenuActionListener);
					itmSalesRep.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmSalesRep);
					
					JMenuItem itmWarehouse = new JMenuItem("Warehouse");
					itmWarehouse.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmWarehouse.setMnemonic('W');
					itmWarehouse.setIcon(new ImageIcon("images/warehouse.png"));
					itmWarehouse.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
					itmWarehouse.setActionCommand("wareh");
					itmWarehouse.addActionListener(JMenuActionListener);
					itmWarehouse.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmWarehouse);
					
					JMenuItem itmProducts = new JMenuItem("Products");
					itmProducts.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmProducts.setMnemonic('P');
					itmProducts.setIcon(new ImageIcon("images/product.png"));
					itmProducts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
					itmProducts.setActionCommand("prod");
					itmProducts.addActionListener(JMenuActionListener);
					itmProducts.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmProducts);
					
					JMenuItem itmCategories = new JMenuItem("Categories");
					itmCategories.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmCategories.setMnemonic('T');
					itmCategories.setIcon(new ImageIcon("images/categories.png"));
					itmCategories.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
					itmCategories.setActionCommand("categ");
					itmCategories.addActionListener(JMenuActionListener);
					itmCategories.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmCategories);
					
					JMenuItem itmStockAdj = new JMenuItem("Stock Adjustment");
					itmStockAdj.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmStockAdj.setMnemonic('A');
					itmStockAdj.setIcon(new ImageIcon("images/adjustment.png"));
					itmStockAdj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
					itmStockAdj.setActionCommand("stockadj");
					itmStockAdj.addActionListener(JMenuActionListener);
					itmStockAdj.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmStockAdj);
					
					JMenuItem itmInvoice = new JMenuItem("Invoices");
					itmInvoice.setFont(new Font("Dialog", Font.PLAIN, 12));
					itmInvoice.setMnemonic('I');
					itmInvoice.setIcon(new ImageIcon("images/invoice.png"));
					itmInvoice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
					itmInvoice.setActionCommand("invoice");
					itmInvoice.addActionListener(JMenuActionListener);
					itmInvoice.setBackground(new Color(255, 255, 255));
					
					recordsMenu.add(itmInvoice);
					
					JMenuItem ItmPO = new JMenuItem("Purchase Orders");
					ItmPO.setFont(new Font("Dialog", Font.PLAIN, 12));
					ItmPO.setMnemonic('O');
					ItmPO.setIcon(new ImageIcon("images/purchaseorder.png"));
					ItmPO.setAccelerator(
						KeyStroke.getKeyStroke(
								KeyEvent.VK_O,ActionEvent.CTRL_MASK
							)
						);
					ItmPO.setActionCommand("PO");
					ItmPO.addActionListener(JMenuActionListener);
					ItmPO.setBackground(new Color(255,255,255));

					recordsMenu.add(ItmPO);

					
					JMenuItem ItmRecieve = new JMenuItem("Purchase Receipt");
					ItmRecieve.setFont(new Font("Dialog", Font.PLAIN, 12));
					ItmRecieve.setMnemonic('R');
					ItmRecieve.setIcon(new ImageIcon("images/recieve.png"));
					ItmRecieve.setAccelerator(
						KeyStroke.getKeyStroke(
								KeyEvent.VK_R,ActionEvent.CTRL_MASK
							)
						);
					ItmRecieve.setActionCommand("preceipt");
					ItmRecieve.addActionListener(JMenuActionListener);
					ItmRecieve.setBackground(new Color(255,255,255));

					recordsMenu.add(ItmRecieve);

					
					JMenuItem ItmExpense = new JMenuItem("Expenses");
					ItmExpense.setFont(new Font("Dialog", Font.PLAIN, 12));
					ItmExpense.setMnemonic('E');
					ItmExpense.setIcon(new ImageIcon("images/expense.png"));
					ItmExpense.setAccelerator(
						KeyStroke.getKeyStroke(
								KeyEvent.VK_E,ActionEvent.CTRL_MASK
							)
						);
					ItmExpense.setActionCommand("expense");
					ItmExpense.addActionListener(JMenuActionListener);
					ItmExpense.setBackground(new Color(255,255,255));

					recordsMenu.add(ItmExpense);
			
			JMenu processMenu = new JMenu("Process");
			processMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
			processMenu.setMnemonic('P');
			processMenu.setBackground(new Color(255,255,255));
			NewJMenuBar.add(processMenu);
			
				JMenuItem itmNewInvoice = new JMenuItem("New Invoice");
				itmNewInvoice.setFont(new Font("Dialog", Font.PLAIN, 12));
				itmNewInvoice.setMnemonic('I');
				itmNewInvoice.setIcon(new ImageIcon("images/newinvoice.png"));
				itmNewInvoice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,ActionEvent.CTRL_MASK));
				itmNewInvoice.setActionCommand("newinv");
				itmNewInvoice.addActionListener(JMenuActionListener);
				itmNewInvoice.setBackground(new Color(255,255,255));

				processMenu.add(itmNewInvoice);
				
				JMenuItem ItmNewPO = new JMenuItem("New Purchase Order");
				ItmNewPO.setFont(new Font("Dialog", Font.PLAIN, 12));
				ItmNewPO.setMnemonic('P');
				ItmNewPO.setIcon(new ImageIcon("images/newpurchaseorder.png"));
				ItmNewPO.setAccelerator(
					KeyStroke.getKeyStroke(
							KeyEvent.VK_F2,ActionEvent.CTRL_MASK
						)
					);
				ItmNewPO.setActionCommand("newPO");
				ItmNewPO.addActionListener(JMenuActionListener);
				ItmNewPO.setBackground(new Color(255,255,255));

				processMenu.add(ItmNewPO);

				
				JMenuItem ItmNewRecieve = new JMenuItem("New Purchase Receipt");
				ItmNewRecieve.setFont(new Font("Dialog", Font.PLAIN, 12));
				ItmNewRecieve.setMnemonic('E');
				ItmNewRecieve.setIcon(new ImageIcon("images/newrecieve.png"));
				ItmNewRecieve.setAccelerator(
					KeyStroke.getKeyStroke(
							KeyEvent.VK_F4,ActionEvent.CTRL_MASK
						)
					);
				ItmNewRecieve.setActionCommand("newpreceipt");
				ItmNewRecieve.addActionListener(JMenuActionListener);
				ItmNewRecieve.setBackground(new Color(255,255,255));

				processMenu.add(ItmNewRecieve);

				
				JMenuItem ItmNewExpense = new JMenuItem("New Expense");
				ItmNewExpense.setFont(new Font("Dialog", Font.PLAIN, 12));
				ItmNewExpense.setMnemonic('E');
				ItmNewExpense.setIcon(new ImageIcon("images/newexpense.png"));
				ItmNewExpense.setAccelerator(
					KeyStroke.getKeyStroke(
							KeyEvent.VK_F3,ActionEvent.CTRL_MASK
						)
					);
				ItmNewExpense.setActionCommand("newexpense");
				ItmNewExpense.addActionListener(JMenuActionListener);
				ItmNewExpense.setBackground(new Color(255,255,255));

				processMenu.add(ItmNewExpense);
			
			JMenu reportsMenu = new JMenu("Reports");
			reportsMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
			reportsMenu.setMnemonic('t');
			reportsMenu.setBackground(new Color(255,255,255));
			NewJMenuBar.add(reportsMenu);
			
			
			JMenu systemMenu = new JMenu("System");
			systemMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
			systemMenu.setMnemonic('S');
			systemMenu.setBackground(new Color(255,255,255));
			NewJMenuBar.add(systemMenu);
			
			JMenu MnuWin = new JMenu("Window");
			MnuWin.setFont(new Font("Dialog", Font.PLAIN, 12));
			MnuWin.setMnemonic('W');
			MnuWin.setBackground(new Color(255,255,255));
			NewJMenuBar.add(MnuWin);
			
			JMenu MnuHelp = new JMenu("Help");
			MnuHelp.setFont(new Font("Dialog", Font.PLAIN, 12));
			MnuHelp.setMnemonic('H');
			MnuHelp.setBackground(new Color(255,255,255));
			NewJMenuBar.add(MnuHelp);
			
			NewJMenuBar.setBackground(new Color(255,255,255));
			return NewJMenuBar;
			
		}

		protected JToolBar CreateJToolBar() {
			JToolBar NewJToolBar = new JToolBar("Toolbar");

			NewJToolBar.setMargin(new Insets(0,0,0,0));

			NewJToolBar.add(CreateJToolbarButton("Customers Record","images/customer.png","toolCus"));
			NewJToolBar.add(CreateJToolbarButton("Suppliers Record","images/supplier.png","toolSup"));
			NewJToolBar.add(CreateJToolbarButton("Revenue Record","images/SalesRep.png","toolSalesrep"));
			NewJToolBar.add(CreateJToolbarButton("Warehouse Record","images/Warehouse.png","toolWareh"));
			NewJToolBar.addSeparator();
			NewJToolBar.add(CreateJToolbarButton("Products Record","images/product.png","toolProd"));
			NewJToolBar.add(CreateJToolbarButton("Categories Record","images/categories.png","toolCat"));
			NewJToolBar.add(CreateJToolbarButton("Stock Adjustment Record","images/adjustment.png","toolStockAdj"));
			NewJToolBar.addSeparator();
			NewJToolBar.add(CreateJToolbarButton("Invoices Record","images/invoice.png","toolInv"));
			NewJToolBar.add(CreateJToolbarButton("New Sale Order","images/purchaseorder.png","toolSale"));
			NewJToolBar.add(CreateJToolbarButton("Purchase Receipt Record","images/recieve.png","toolRecieve"));
			NewJToolBar.add(CreateJToolbarButton("Expenses Record","images/expense.png","toolExpense"));
			NewJToolBar.addSeparator();
			NewJToolBar.add(CreateJToolbarButton("New Invoice","images/newinvoice.png","toolNewInv"));
			NewJToolBar.add(CreateJToolbarButton("New Purchase Order","images/newpurchaseorder.png","toolNewPur"));
			NewJToolBar.add(CreateJToolbarButton("New Purchase Receipt","images/newrecieve.png","toolNewRecieveStock"));
			NewJToolBar.add(CreateJToolbarButton("New Expense","images/newexpense.png","toolNewExpense"));
			NewJToolBar.addSeparator();
			NewJToolBar.add(CreateJToolbarButton("Business Setup","images/businesssetup.png","toolBussSet"));
			NewJToolBar.add(CreateJToolbarButton("Security Option","images/security.png","toolSecOpt"));
			NewJToolBar.add(CreateJToolbarButton("Lock Application","images/lockapplication.png","toolLockApp"));
			NewJToolBar.addSeparator();
			NewJToolBar.add(BusinessTitleLabel);
			NewJToolBar.addSeparator();
			
			return NewJToolBar;
		}
		
		protected JButton CreateJToolbarButton(String srcToolTipText,String srcImageLocation,String srcActionCommand){
			JButton NewJButton = new JButton(new ImageIcon(srcImageLocation));

			NewJButton.setActionCommand(srcActionCommand);
			NewJButton.setToolTipText(srcToolTipText);
			NewJButton.addActionListener(JToolBarActionListener);

			return NewJButton;
		}
		
		ActionListener JMenuActionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String srcObject = e.getActionCommand();
				if(srcObject=="cus"){
					try{
						loadCustomerForm();
					}catch(SQLException sqle){
					}
				}else if(srcObject=="sup"){
					try{
						loadSupplierForm();
					}catch(SQLException sqle){
					}
				}else if(srcObject=="salesrep"){
					try{
						loadSalesRepForm();
					}catch(SQLException sqle){
					}
				}else if(srcObject=="wareh"){
					try{
						loadWarehouseForm();
					}catch(SQLException sqle){
					}
				}
				else if(srcObject=="invoice"){
					try{
						loadWarehouseForm();
					}catch(SQLException sqle){
					}
				}
				
				else if(srcObject=="loggoff"){

				}else if(srcObject=="exit"){
					UnloadWindow();
				}
			}
		};
		
		ActionListener JToolBarActionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				String srcObject = e.getActionCommand();
				if(srcObject=="toolCus"){
					try{
						loadCustomerForm();
					}catch(SQLException sqle){
					}
				}else if(srcObject=="toolSup"){
					try{
						loadSupplierForm();
					}catch(SQLException sqle){
					}
				}else if(srcObject=="toolSalesrep"){
					try{
						loadSalesRepForm();
					}catch(SQLException sqle){
					}
				}else if(srcObject=="toolWareh"){
					try{
						loadWarehouseForm();
					}catch(SQLException sqle){
					}
				}else if(srcObject=="toolProd"){
					try{
						loadProductForm();
					}catch(SQLException sqle){
					}
				}
				else if(srcObject=="toolInv"){
					try {
						loadInvoiceForm();
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
				
				}
				else if(srcObject=="toolSale"){
					try {
						loadNewSaleForm();
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
				
				}
			}
		};
		
		protected boolean isLoaded(String FormTitle) {

			JInternalFrame Form[] = desk.getAllFrames();
			for (int i = 0; i < Form.length; i++) {
				if (Form[i].getTitle().equalsIgnoreCase (FormTitle)) {
					Form[i].show ();
					try{
						Form[i].setIcon(false);
						Form[i].setSelected(true);
					}catch(PropertyVetoException e){
					}
					return true;
				}
			}
			return false;

		}
		
		protected void loadNewSaleForm() throws SQLException {
			boolean AlreadyLoaded = isLoaded("New Sale");
			if(AlreadyLoaded==false){
				FormSales = new FrmSales(con,this);
				desk.add(FormSales);
				
				FormSales.setVisible(true);
				FormSales.show();
				try{
					FormSales.setIcon(false);
					FormSales.setSelected(true);
				}catch(PropertyVetoException e){
				}
				
			}else{
				try{
					FormSales.setIcon(false);
					FormSales.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}

		}

		protected void loadProductForm() throws SQLException{
			
			boolean AlreadyLoaded = isLoaded("Product List");
			if(AlreadyLoaded==false){
				FormProduct = new FrmProduct(con,this);
				desk.add(FormProduct);
				
				FormProduct.setVisible(true);
				FormProduct.show();
				try{
					FormProduct.setIcon(false);
					FormProduct.setSelected(true);
				}catch(PropertyVetoException e){
				}
				
			}else{
				try{
					FormProduct.setIcon(false);
					FormProduct.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}

		}

		protected void loadInvoiceForm() throws SQLException{
			
			boolean AlreadyLoaded = isLoaded("Invoice");
			if(AlreadyLoaded==false){
				FormInvoice = new FrmInvoice(con,this);
				desk.add(FormInvoice);
				
				FormInvoice.setVisible(true);
				FormInvoice.show();
				try{
					FormInvoice.setIcon(false);
					FormInvoice.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}else{
				try{
					FormInvoice.setIcon(false);
					FormInvoice.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}
		}

		@Override
		public void windowActivated(WindowEvent e) {
			
		}


		protected void UnloadWindow(){
			String ObjButtons[] = {"Yes","No"};
			int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Inventory System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
			if(PromptResult==0){
				System.out.println(
					"\n\n" +
					" ------------------------------------------------------------\n\n" +
					" THANK YOU FOR USING INVENTORY MANAGEMENT SYSTEM\n\n" +
					" ------------------------------------------------------------\n\n" +
					"\n\n"
					);
				System.exit(0);
			}
		}

		protected void loadWarehouseForm() throws SQLException {
			boolean AlreadyLoaded = isLoaded("Warehouse Records");
			if(AlreadyLoaded==false){
				FormWarehouse = new FrmWarehouse(con,this);
				desk.add(FormWarehouse);
				
				FormWarehouse.setVisible(true);
				FormWarehouse.show();
				try {
					FormWarehouse.setIcon(false);
					FormWarehouse.setSelected(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

		protected void loadSalesRepForm() throws SQLException{
			boolean AlreadyLoaded = isLoaded("SalesRep Records");
			if(AlreadyLoaded==false){
				FormRevenueRecord = new FrmRevenueRecord(con,this);
				desk.add(FormRevenueRecord);
				
				FormRevenueRecord.setVisible(true);
				FormRevenueRecord.show();
				try{
					FormRevenueRecord.setIcon(false);
					FormRevenueRecord.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}else{
				try{
					FormRevenueRecord.setIcon(false);
					FormRevenueRecord.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}
			
		}

		protected void loadSupplierForm() throws SQLException{
			boolean AlreadyLoaded = isLoaded("Supplier Records");
			if(AlreadyLoaded==false){
				FormSupplier = new FrmSupplier(con,this);
				desk.add(FormSupplier);

				FormSupplier.setVisible(true);
				FormSupplier.show();
				try{
					FormSupplier.setIcon(false);
					FormSupplier.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}else{
				try{
					FormSupplier.setIcon(false);
					FormSupplier.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}
			
		}

		protected void loadCustomerForm() throws SQLException{
			
			boolean AlreadyLoaded = isLoaded("Customer Records");
			if(AlreadyLoaded==false){
				FormCustomer = new FrmCustomer(con,this);
				desk.add(FormCustomer);

				FormCustomer.setVisible(true);
				FormCustomer.show();
				try{
					FormCustomer.setIcon(false);
					FormCustomer.setSelected(true);
				}catch(PropertyVetoException e){
				}
				
			}else{
				try{
					FormCustomer.setIcon(false);
					FormCustomer.setSelected(true);
				}catch(PropertyVetoException e){
				}
			}
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosing(WindowEvent e) {
			UnloadWindow();
			
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public static void main(String[] args) {
			
			new MainClass();
		}
		
}
