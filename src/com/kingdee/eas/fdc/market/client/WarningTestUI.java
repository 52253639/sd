/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Style;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.DataType;
import com.kingdee.bos.metadata.entity.EntityObjectCollection;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.LinkPropertyInfo;
import com.kingdee.bos.metadata.entity.OwnPropertyInfo;
import com.kingdee.bos.metadata.entity.PropertyCollection;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.Administrator;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.propertymgmt.client.FDCPDTTemplate;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.ModifyCustomerNameUI;
import com.kingdee.eas.fi.arap.tools.PDTemplete;
import com.kingdee.eas.fi.arap.tools.Templete;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.Uuid;
import com.kingdee.util.enums.Enum;

/**
 * output class name
 */
public class WarningTestUI extends AbstractWarningTestUI
{
    private static final String TO_PDM = "导出PDM";
	private static final Logger logger = CoreUIObject.getLogger(WarningTestUI.class);
    
    /**
     * output class constructor
     */
    public WarningTestUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}


	public void onLoad() throws Exception {
		super.onLoad();
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);		
		
		IRow addRow = this.tblUIList.addRow();
			addRow.getCell("name").setValue("后台ksql查询");
			addRow.getCell("ui").setValue("com.kingdee.eas.fm.common.client.FMIsqlUI");
		addRow = this.tblUIList.addRow();
			addRow.getCell("name").setValue("性能监控");
			addRow.getCell("ui").setValue("com.kingdee.bos.workflow.monitor.client.MonitorCenterUI");
		addRow = this.tblUIList.addRow();
			addRow.getCell("name").setValue("成交分析");
			addRow.getCell("ui").setValue("com.kingdee.eas.fdc.market.client.RptBarginOnAnalyseBIUI");
		addRow = this.tblUIList.addRow();
			addRow.getCell("name").setValue("自娱自乐");
			addRow.getCell("ui").setValue("com.kingdee.eas.fdc.basedata.client.AmusementUI");
		
		addRow = this.tblUIList.addRow();
			addRow.getCell("name").setValue(TO_PDM);
			addRow.getCell("ui").setValue("com.kingdee.eas.fdc.");
			
		this.tblUIList.getStyleAttributes().setLocked(true);
			
		addRow = this.tblUIList.addRow();
			addRow.getCell("name").setValue("自定义UI");
			addRow.getCell("name").getStyleAttributes().setLocked(true);
			addRow.getCell("ui").setValue("com.kingdee.eas.fdc.××.client.××UI");
			addRow.getCell("ui").getStyleAttributes().setLocked(false);
	}
	

	
	protected void btnUpdatePurchase_actionPerformed(ActionEvent e)
			throws Exception {
		
		ModifyCustomerNameUI.updatePurchaseCustomerInfo(null);
		
		
		
		
	}
	
	
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType()!=1)  return;
		if(e.getRowIndex()<0 || e.getColIndex()<0) return;
		
		
		//this.kdButton.setForeground(this.kdButton.getForeground().equals(Color.WHITE)?Color.BLACK:Color.WHITE);
		//this.kdButton.setBackground(this.kdButton.getBackground().equals(Color.BLACK)?Color.BLACK:Color.RED);
		
		
//		Ball ball = new Ball(e.getX(),e.getY());
//		this.add(ball);
//		Rectangle rect = this.tblMain.positionOfCell(e.getRowIndex(), e.getColIndex());
		
//		this.kdButton.setLocation((int)rect.getX(),(int)rect.getY()+(int)this.tblMain.getLocationOnScreen().getY());

		IRow row = this.tblMain.getRow(e.getRowIndex());
/*		ImageEditor imgEditor = new EditorFactory.ImageEditor();
		imgEditor.setText("11111");
		row.getCell("id").setEditor(imgEditor);
		row.getCell("id").setValue("OK");*/
		
//		JPanel jpanel = new JPanel();
//		ICellEditor comboEditor = new KDTDefaultCellEditor(jpanel);    imgTable_fzz
		
		
		
        final Image imgAsst = EASResource.getImage("wf_waitevent_timer_running");
        ObjectValueRender accRender = new ObjectValueRender() {        	
            public void draw(Graphics graphics, Shape clip, Object object, Style cellStyle, Object extObject) {
                super.draw(graphics, clip, object, cellStyle, null);
                Rectangle bounds = clip.getBounds();
				graphics.drawImage(imgAsst, bounds.x + bounds.width - 20,bounds.y, null);

            }
        };  
        row.getCell("id").setRenderer(accRender);
        
		
		
		
	}
	
	
	protected void btnShowEntityPropertys_actionPerformed(ActionEvent e)
			throws Exception {
		String className = this.txtEntityClassName.getText();
		if(FDCHelper.isEmpty(className)) {
			MsgBox.showWarning(this, "类名为空，无法查询。此类名必须以*Info结尾的。");
			return;
		}
		
		Class classObject = null;
		try{
			classObject = Class.forName(className.trim());
		}catch (ClassNotFoundException ee) {
			MsgBox.showError(this, "查无此类！");
			return;
		}
		
		Object objectInfo = classObject.newInstance();
		//new AssistantHGInfo()
		if(objectInfo instanceof IObjectValue) {
			PropertyCollection proColl = CommerceHelper.getAllPropertysOfEntity((IObjectValue)objectInfo);
			for(int i=0;i<proColl.size();i++) {
				PropertyInfo proInfo = proColl.get(i);				
				IRow row = this.tblMain.addRow();
				row.getCell("message").setValue(proInfo.getName() + " : " + proInfo.getAlias());
				
			
				//ICellEditor 
				row.getCell("message").setEditor(null);
				

			}
		}else{
			MsgBox.showError("请先输入类名！", "类名为空，无法查询。此类名必须以*Info结尾的。");
		}
		
		
	}
	
	
	protected void tblUIList_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType()!=1)  return;
		if(e.getRowIndex()<0 || e.getColIndex()<0) return;
		if(e.getClickCount()!=2) return;
		
		IRow clickRow = this.tblUIList.getRow(e.getRowIndex());
		String className = (String)clickRow.getCell("ui").getValue();
		
		String title = (String) clickRow.getCell("name").getValue();		
		if(TO_PDM.equals(title)){
			toPDM(className);
			return;
		}
		
		if(FDCHelper.isEmpty(className)) {
			MsgBox.showWarning(this, "类名为空，无法查询。此类名必须以*UI结尾的。");
			return;
		}
		
		Class classObject = null;
		try{
			classObject = Class.forName(className.trim());
		}catch (ClassNotFoundException ee) {
			MsgBox.showError(this, "查无此类！");
			return;
		}
		
		BOSUuid tempId = null;
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(className.trim().equalsIgnoreCase("com.kingdee.eas.fm.common.client.FMIsqlUI")) {			
			tempId = userInfo.getId();
			userInfo.setId(BOSUuid.read(Administrator.ID));			
			//SysContext.getSysContext().setCurrentUserInfo(userInfo);			
		}
		

		IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		UIContext uiContext = new UIContext(this);
		IUIWindow wnd = fy.create(classObject.getName(),uiContext);
		wnd.show();
		
		if(className.trim().equalsIgnoreCase("com.kingdee.eas.fm.common.client.FMIsqlUI")) {
			userInfo.setId(tempId);
			//SysContext.getSysContext().setCurrentUserInfo(userInfo);	
		}
		
	}

	
	
	private void toPDM(final String className) {
		final String fileName = showPropertiesSelectDlg(this);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LongTimeDialog longTimeDialog = UITools.getDialog(WarningTestUI.this);
				longTimeDialog.setLongTimeTask(new ILongTimeTask() {
					public void afterExec(Object arg0) throws Exception {
						UITools.showMsg(WarningTestUI.this, "生成文件完成地址！", false);
					}

					public Object exec() throws Exception {
						if (fileName != null)
							toPDMs(fileName, className);
						return "";
					}
				});
				longTimeDialog.show();
			}
		});
	}
	
	private void toPDMs(String filename, String filter) throws IOException {
		BufferedReader buffReader = new BufferedReader(new FileReader(filename));
		String line = "";
		
		EntityObjectCollection eoc = new EntityObjectCollection();
		
		while ((line = buffReader.readLine()) != null) {
			if (line.length() > 13 && line.indexOf("=com.kingdee.eas.") == 8) {
				BOSObjectType type = BOSObjectType.create(line.substring(0, 8));
				IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
				EntityObjectInfo entityObjectInfo = loader.getEntity(type);
				if (entityObjectInfo == null)
					continue;
				
				eoc.add(entityObjectInfo);
			}
		}
		if (buffReader != null) {
			buffReader.close();
		}
		
        Uuid uuid = Uuid.create();
        
        Templete t = new FDCPDTTemplate();
        t.setFileId(uuid.toString());
        t.setFileName("easDataModel_");

        writeFile(filename+".aaaa",t.format(eoc,filter));
	}

    public void writeFile(String outPath,String str) throws IOException {
        File file = new File(outPath);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")); //设置输出接口按中文编码
//        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(str);
        writer.close();
    }
	
	private static String defFilter = "com.kingdee.eas.fdc.sellhouse;";
	
	public static void addHideMenuItem(final CoreUIObject uiObject,KDMenu kdMenu) {
		kdMenu.add(new AbstractHidedMenuItem("ctrl shift alt F12"){
			public void action_actionPerformed() {
				try {
					//CommerceHelper.openTheUIWindow(uiObject, TableTestUI.class.getName(), null);
					IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
					UIContext uiContext = new UIContext(uiObject);
					IUIWindow wnd = fy.create("com.kingdee.eas.fdc.market.client.WarningTestUI",uiContext);
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
	
	
	
	
	
	
	
	/**
	 * 从实体影射表中直接导出对于的表结构说明文档
	 */
	protected void btnExportEntityTable_actionPerformed(ActionEvent e) throws Exception {
		final String fileName = showPropertiesSelectDlg(this);
		
		SwingUtilities.invokeLater(new Runnable()	{
			public void run()		{
				LongTimeDialog longTimeDialog = UITools.getDialog(WarningTestUI.this);
				longTimeDialog.setLongTimeTask(new ILongTimeTask()		{
					public void afterExec(Object arg0)	throws Exception	{
						UITools.showMsg(WarningTestUI.this, "生成文件完成地址！"+fileName+".txt", false);
					}
					public Object exec() throws Exception	{
						if(fileName!=null) ReadAndWriteToTxt(fileName);
						return "";
					}
					
				});
				longTimeDialog.show();
			}
		});
		
		
		
	}
	
	
	
	//打开property属性的文本文件, 生成文件直接放在原文件目录下
	private String showPropertiesSelectDlg(CoreUIObject ui) {
		KDFileChooser chsFile = new KDFileChooser();
		String Key_File = "Key_File";
		SimpleFileFilter Filter_Txt = new SimpleFileFilter("properties", "Properties TXT" + LanguageManager.getLangMessage(Key_File, WizzardIO.class, "操作失败"));
		chsFile.addChoosableFileFilter(Filter_Txt);
		int ret = chsFile.showOpenDialog(ui);
		if (ret != JFileChooser.APPROVE_OPTION)
			SysUtil.abort();

		File file = chsFile.getSelectedFile();
		String fileName = file.getAbsolutePath();
		return fileName;
	}
	
	
    private void ReadAndWriteToTxt(String filename) throws IOException {
    	PrintWriter outPrint = new PrintWriter(new BufferedWriter(new FileWriter(filename + ".txt", false)));
        try    {    	
        	BufferedReader buffReader = new BufferedReader(new FileReader(filename));
        	String line = "";  
        	while((line=buffReader.readLine())!=null){
        		if(line.length()>13 && line.indexOf("=com.kingdee.eas.")==8){
             		BOSObjectType type=BOSObjectType.create(line.substring(0, 8));
             		String className = line.substring(9);
/*        			        			
        			Class classObject = null;
        			try{
        				classObject = Class.forName(className.trim()+"Info");
        			}catch (ClassNotFoundException ee) {
        				outPrint.println("查无此类:"+className.trim()+"Info"+"！");
        				return;
        			}*/
        			
        			String dataType="";
    				IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
    		        EntityObjectInfo entityObjectInfo = loader.getEntity(type);
    		        if(entityObjectInfo==null) continue;
    		        
    		        PropertyCollection assitantHGProperties = entityObjectInfo.getInheritedNoDuplicatedProperties();
    		        
    		        //outPrint.println("className:"+className);
    		        String tableName = entityObjectInfo.getTable()==null?"":entityObjectInfo.getTable().getFullName();
    		        tableName = tableName.substring(tableName.lastIndexOf(".")+1);
    		        outPrint.println(tableName+"\t\t"+entityObjectInfo.getAlias()+"\t\t"+ className);
    		        //outPrint.println("InheritedNoDuplicatedProperties:");
    		        outPrint.println("字段名\t\t描述\t\t类型");
    				for(int i=0;i<assitantHGProperties.size();i++) {
    					PropertyInfo proInfo = assitantHGProperties.get(i);
    					if(proInfo==null) continue;
    					String mapKeyStr = "";
    					if(proInfo.getMappingField() != null){
        					mapKeyStr = proInfo.getMappingField().getName();
    					}
    					if (proInfo instanceof OwnPropertyInfo) {
    						OwnPropertyInfo new_name = (OwnPropertyInfo)proInfo;
    						DataType dt = new_name.getDataType();
    						dataType=dt.getAlias();
    						
    						if(dt==DataType.ENUM){
    							String cn=new_name.getMetaDataRef();
    							String value=":+"+cn+"\t\t值：";

//    							try{
//									Class c2=Class.forName(cn);
//									Method m=c2.getMethod("getEnumList",null);
//									List list=(List)m.invoke(null,null);        								
//									for(int j=0;j<list.size();j++){
//										Enum enums=(Enum)list.get(j);
//										value=value+enums.getName()+"\t\t"+enums.getAlias(new Locale("l2"))+"\t\t";
//									}
//									dataType+=value;
//    							}catch(Exception eee){
//    								eee.printStackTrace();
//    							}
    						}
    					}else if(proInfo instanceof LinkPropertyInfo){
    						LinkPropertyInfo new_name = (LinkPropertyInfo)proInfo;
    						dataType=new_name.getRelationship().getChildObject(entityObjectInfo).getObjectValueClass();
    					}
    					
    					outPrint.println(mapKeyStr + "\t\t"	+ proInfo.getAlias()+"\t\t"+dataType);
    				} 
        			
        		}
				outPrint.println("\n\n");
        	}
        	outPrint.close();
        	if(buffReader!=null){
        		buffReader.close();
        	}
        	
        } catch (Exception ex)    {
        	outPrint.close();
            this.handleException(ex);
        }

      }
	
	
	
	protected void btnShowMetaInfo_actionPerformed(ActionEvent e) throws Exception {
		String bosTypeName = this.kDTextField1.getText();
		if(bosTypeName!=null && !bosTypeName.trim().equals("") && bosTypeName.length()==8) {
     		BOSObjectType type=BOSObjectType.create(bosTypeName);
			IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
	        EntityObjectInfo entityObjectInfo = loader.getEntity(type);
	        if(entityObjectInfo==null) return;
	        
	        String tableName = entityObjectInfo.getTable()==null?"":entityObjectInfo.getTable().getFullName();
	        MsgBox.showInfo(tableName + " ; " + entityObjectInfo.getAlias());
		}
		
		
	}
    
    
    
	
	

}