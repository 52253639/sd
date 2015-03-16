/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class RangeSelectUI extends AbstractRangeSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(RangeSelectUI.class);

    public RangeSelectUI() throws Exception
    {
        super();
    }
    
    protected void inOnload() throws Exception {
		// super.inOnload();
	}
    
    private void initControl() {
    	this.btnPrint.setEnabled(true);
		this.btnPrintPreview.setEnabled(true);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
    }
    
    public void initTable()
    {
    	this.tblMain.checkParsed();
    	KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		this.tblMain.getColumn("item").setEditor(checkBox);
    }
    
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initControl();
    	initTable();
    	ProjectArchivesEntryInfo projectArchInfo = (ProjectArchivesEntryInfo)this.getUIContext().get("projectArchInfo");
    	this.comboxRange.setSelectedItem(projectArchInfo.getScopeType());  	
    }
    
    protected void comboxRange_itemStateChanged(ItemEvent e) throws Exception {
    	ScopeTypeEnum scope = (ScopeTypeEnum)this.comboxRange.getSelectedItem();
    	ProjectArchivesEntryInfo projectArchInfo = (ProjectArchivesEntryInfo)this.getUIContext().get("projectArchInfo");
    	ArchivesEntryCollection archColl = projectArchInfo.getArchivesEntrys();
    	List nameList = new ArrayList();
    	for(int i=0;i<archColl.size();i++)
    	{
    		ArchivesEntryInfo archInfo = archColl.get(i);
    		nameList.add(archInfo.getName());
    	}
    	if(ScopeTypeEnum.AllScope.equals(scope))
    	{
    		this.tblMain.setVisible(false);
    	}else
    	{
    		this.tblMain.removeRows();
    		String sql = "select fid,fname_l2,fnumber from "+projectArchInfo.getName();
    		IRowSet sellSet = SQLExecutorFactory.getRemoteInstance(sql)
			.executeSQL();
    		List idList = new ArrayList();
			while(sellSet.next())
			{
				String projectArchID = sellSet.getString("fid");
				String name = sellSet.getString("fname_l2");
				String number = sellSet.getString("fnumber");
				if(!StringUtils.isEmpty(projectArchID))
				{
					this.tblMain.setVisible(true);
					IRow row = this.tblMain.addRow();
					if(TenancyClientHelper.isInclude(name, nameList))
					{
						row.getCell("item").setValue(new Boolean(true));
					}else
					{
						row.getCell("item").setValue(new Boolean(false));
					}					
					row.getCell("name").setValue(name);
					row.getCell("number").setValue(number);
					row.getCell("id").setValue(projectArchID);
				}
				
			}
    	}
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void btnConfim_actionPerformed(ActionEvent e) throws Exception {
    	super.btnConfim_actionPerformed(e);
    	//SellProjectInfo sellProjectInfo = (SellProjectInfo)this.getUIContext().get("sellProjectInfo");
    	ProjectArchivesEntryInfo projectArchInfo = (ProjectArchivesEntryInfo)this.getUIContext().get("projectArchInfo");
    	ArchivesEntryCollection archColl = new ArchivesEntryCollection();
    	for(int i=0;i<tblMain.getRowCount();i++)
    	{
    		IRow row = tblMain.getRow(i);   		
    		if(row.getCell("item").getValue().equals(new Boolean(true)))
    		{
    			ArchivesEntryInfo archInfo = new ArchivesEntryInfo();
    			String name = (String)row.getCell("name").getValue();
    			String number = (String)row.getCell("number").getValue();
    			String id = (String)row.getCell("id").getValue();
    			archInfo.setName(name);
    			archInfo.setNumber(number);
    			//因为没有加档案ID字段。先暂时把值赋给description以后来改
    			archInfo.setDescription(id);
    			archColl.add(archInfo);
    		}  		
    	}
    	projectArchInfo.getArchivesEntrys().clear();
		projectArchInfo.getArchivesEntrys().addCollection(archColl);
		projectArchInfo.setScopeType((ScopeTypeEnum)this.comboxRange.getSelectedItem());
    	ProjectArchivesEntryFactory.getRemoteInstance().submit(projectArchInfo);
    	this.destroyWindow();
    }
    
    public static void showUI(IUIObject ui, String projectArchID,SellProjectInfo sellProjectInfo)
	throws EASBizException, BOSException {
    	UIContext uiContext = new UIContext(ui);
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("archivesEntrys.*");
		ProjectArchivesEntryInfo projectArchInfo = ProjectArchivesEntryFactory.getRemoteInstance().getProjectArchivesEntryInfo(
				new ObjectUuidPK(BOSUuid.read(projectArchID)), sels);
		uiContext.put("projectArchID", projectArchID);
		uiContext.put("projectArchInfo", projectArchInfo);
		uiContext.put("sellProjectInfo", sellProjectInfo);
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(RangeSelectUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
    }

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}