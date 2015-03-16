package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.kingdee.bos.ctrl.bibench.platform.ui.util.IconUtil;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.EditorFactory.ImageEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Border;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.master.auxacct.AssistantHGInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;

public class TableTestUI extends CoreUI {

	public TableTestUI() throws Exception {
		super();
	}
	
	
	private KDTable tblMain ;
	private KDTextField textField;
	private KDWorkButton button;
	
	
	public void onLoad() throws Exception {
		super.onLoad();		
	
		if(tblMain==null) {
			tblMain = new KDTable();
			textField = new KDTextField();
			button = new KDWorkButton();
			textField.setBounds(20, 10, 400, 30);
			this.add(textField);
			button.setBounds(500, 10, 100, 30);
			button.setText("»∑∂®");
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					String className = textField.getText();
					if(className!=null && !className.trim().equals(""))
						addPropertyInfoIntoRow(className);
					
				}	
			});
			this.add(button);
			
		
			
			IColumn col = tblMain.addColumn();
			IRow headRow = tblMain.addHeadRow();
			col.setKey("testCol");
			col.setWidth(300);
			headRow.getCell("testCol").setValue("≤‚ ‘¡–");
			tblMain.setVisible(true);
			tblMain.addRow();
			tblMain.addRow();tblMain.addRow();tblMain.addRow();
			tblMain.getStyleAttributes().setLocked(true);
	        this.setBounds(new Rectangle(10, 10, 1013, 629));
	        this.setLayout(null);
	        tblMain.setBounds(new Rectangle(10, 50, 996, 400));
	        this.add(tblMain);
	        
			Ball ball = new Ball(50,50);		
			this.add(ball);

			Ball ball2 = new Ball(100,100);
			this.add(ball2);

			
			
			tblMain.addKDTMouseListener(new KDTMouseListener(){
				public void tableClicked(KDTMouseEvent e) {
	                try {
	                    tblMain_tableClicked(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
				}
			});
			
		}
		
		
	}
	
	
	private void tblMain_tableClicked(KDTMouseEvent e) throws Exception	{
		if(e.getRowIndex()<0 || e.getColIndex()<0) return;
		


		Rectangle rect = this.tblMain.positionOfCell(e.getRowIndex(), e.getColIndex());
		Ball ball = new Ball(rect.x,rect.y);
		//Ball ball = new Ball(10,10);
		this.add(ball);		
		
		//ICell cell = this.tblMain.getCell(e.getRowIndex(), e.getColIndex());
		//cell.sete
		

	}
	
	
	private void addPropertyInfoIntoRow(String className){
		Class classObject = null;
		try {
			classObject = Class.forName(className);

			//classObject.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//new AssistantHGInfo()
		PropertyCollection proColl;
		try {
			proColl = CommerceHelper.getAllPropertysOfEntity((IObjectValue)classObject.newInstance());
			for(int i=0;i<proColl.size();i++) {
				PropertyInfo proInfo = proColl.get(i);
				
				IRow row = this.tblMain.addRow();
				row.getCell("testCol").setValue(proInfo.getName() + " : " + proInfo.getAlias());
				
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	
	public static void addHideMenuItem(final CoreUIObject uiObject,KDMenu kdMenu) {
		kdMenu.add(new AbstractHidedMenuItem("ctrl shift F12"){
			public void action_actionPerformed() {
				try {
					//CommerceHelper.openTheUIWindow(uiObject, TableTestUI.class.getName(), null);
					IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.MODEL);
					UIContext uiContext = new UIContext(uiObject);
					IUIWindow wnd = fy.create("com.kingdee.eas.fdc.market.client.TableTestUI",uiContext);
					//IUIWindow wnd = fy.create("com.kingdee.eas.fdc.basedata.client.AmusementUI",uiContext);
					wnd.show();
				} catch (UIException e) {
					SysUtil.abort(e);
				}				
			}
		});
		
	}
	
	
	
	
	public class Ball extends JComponent {
		private int xLoc;
		private int yLoc;
		
		public Ball(int x,int y) {
			xLoc = x;
			yLoc = y;
			this.setBounds(new Rectangle(xLoc, yLoc, xLoc+20, yLoc+20));
			this.setOpaque(true);
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.RED);			
			g.fillRect(xLoc, yLoc, 20, 20);
		}

		public synchronized void addMouseListener(MouseListener l) {
			super.addMouseListener(l);
			Color thisColor = this.getGraphics().getColor();
			this.getGraphics().setColor(thisColor.equals(Color.red)?Color.black:Color.red);
		}
		
	}
	
	
	
	
	
	
	
	
}
