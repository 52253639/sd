/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AddToSysCustomerUI extends AbstractAddToSysCustomerUI
{
    private static final Logger logger = CoreUIObject.getLogger(AddToSysCustomerUI.class);
    
    public void onLoad() throws Exception {
    	initTable(this.tblAllNotSame,false);
    	initTable(this.tblIsToSys,false);
    	initTable(this.tblAllSame,true);
    	initTable(this.tblCNSame,true);
		super.onLoad();
		refresh();
	}
    public AddToSysCustomerUI() throws Exception
    {
        super();
    }
    protected void ckbAllSelect_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
		for(int i=0;i<this.tblAllSame.getRowCount();i++){
			this.tblAllSame.getRow(i).getCell("isToSys").setValue(new Boolean(this.ckbAllSelect.isSelected()));
			if(this.ckbAllSelect.isSelected()){
				this.tblAllSame.getRow(i).getStyleAttributes().setBackground(Color.PINK);
				FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblAllSame.getRow(i).getCell("sheId").getUserObject());
				CustomerInfo info=((CustomerInfo)this.tblAllSame.getRow(i).getCell("sysId").getUserObject());
				fdcinfo.setSysCustomer(info);
			}else{
				this.tblAllSame.getRow(i).getStyleAttributes().setBackground(Color.WHITE);
				FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblAllSame.getRow(i).getCell("sheId").getUserObject());
				fdcinfo.setSysCustomer(null);
			}
		}
    }
    protected void ckbAllSelectTwo_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    	for(int i=0;i<this.tblCNSame.getRowCount();i++){
			this.tblCNSame.getRow(i).getCell("isToSys").setValue(new Boolean(this.ckbAllSelectTwo.isSelected()));
			if(this.ckbAllSelectTwo.isSelected()){
				this.tblCNSame.getRow(i).getStyleAttributes().setBackground(Color.PINK);
				FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblCNSame.getRow(i).getCell("sheId").getUserObject());
				CustomerInfo info=((CustomerInfo)this.tblCNSame.getRow(i).getCell("sysId").getUserObject());
				fdcinfo.setSysCustomer(info);
			}else{
				this.tblCNSame.getRow(i).getStyleAttributes().setBackground(Color.WHITE);
				FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblCNSame.getRow(i).getCell("sheId").getUserObject());
				fdcinfo.setSysCustomer(null);
			}
    	}
    }
    protected CSSPGroupInfo getCSSPGroup() throws EASBizException, BOSException{
    	CSSPGroupInfo groupInfo = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("name", "房地产客户"));
		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		
		CSSPGroupCollection sheGroupCol = CSSPGroupFactory.getRemoteInstance().getCSSPGroupCollection(view);
		
		if(sheGroupCol.isEmpty()){
			CtrlUnitInfo cu = new CtrlUnitInfo();
			cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
			
			CSSPGroupStandardInfo strd = new CSSPGroupStandardInfo();
			strd.setId(BOSUuid.create(strd.getBOSType()));
			strd.setNumber("fdccusGstrd");
			strd.setName("房地产客户分类标准");
			strd.setType(1);
			strd.setIsBasic(StandardTypeEnum.defaultStandard);
			strd.setCU(cu);
			
			CSSPGroupStandardFactory.getRemoteInstance().addnew(strd);
			
			CSSPGroupInfo gr = new CSSPGroupInfo();
			gr.setDeletedStatus(DeletedStatusEnum.NORMAL);
			gr.setId(BOSUuid.create(gr.getBOSType()));
			gr.setNumber("fdccusG");
			gr.setName("房地产客户");
			gr.setCU(cu);
			gr.setGroupStandard(strd);
			
			CSSPGroupFactory.getRemoteInstance().addnew(gr);
			
			groupInfo = gr;
		}else{
			groupInfo = sheGroupCol.get(0);
		}
		
		return groupInfo;
    }
    protected void updateCusGroup(CustomerInfo customer,CSSPGroupInfo groupInfo) throws EASBizException, BOSException{
    	SelectorItemCollection updateCus = new SelectorItemCollection();
		updateCus.add("browseGroup.*");
		updateCus.add("customerGroupDetails.*");
		
    	customer.setBrowseGroup(groupInfo);
		CustomerGroupDetailInfo Gdinfo = new CustomerGroupDetailInfo();
		Gdinfo.setCustomerGroup(groupInfo);
		Gdinfo.setCustomerGroupFullName(groupInfo.getName());
		Gdinfo.setCustomerGroupStandard(groupInfo.getGroupStandard());
		customer.getCustomerGroupDetails().add(Gdinfo);
		
		CustomerFactory.getRemoteInstance().updatePartial(customer, updateCus);
    }
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if (FDCMsgBox.showConfirm2New(this, "请确认是否同步主客户?") == MsgBox.YES) {
    		Window win = SwingUtilities.getWindowAncestor(this);
	        LongTimeDialog dialog = null;
	        if(win instanceof Frame) 
	        	dialog = new LongTimeDialog((Frame)win);
	        else
    	       if(win instanceof Dialog)
    	           dialog = new LongTimeDialog((Dialog)win);
    	       
    	       dialog.setLongTimeTask(new ILongTimeTask() {
    	    	   public Object exec()throws Exception{
    	    		   int count=0;
    	        	   SelectorItemCollection sel=new SelectorItemCollection();
    	   			   sel.add("sysCustomer");
    	   			   
    	   			   CSSPGroupInfo groupInfo=getCSSPGroup();
    	   			   for(int i=0;i<tblAllSame.getRowCount();i++){
    	   				   if(((Boolean)tblAllSame.getRow(i).getCell("isToSys").getValue()).booleanValue()){
    	   					   FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)tblAllSame.getRow(i).getCell("sheId").getUserObject());
    	   					   FDCMainCustomerFactory.getRemoteInstance().updatePartial(fdcinfo, sel);
    	   					   
    	   					   CustomerInfo info=((CustomerInfo)tblAllSame.getRow(i).getCell("sysId").getUserObject());
    	   					   updateCusGroup(info,groupInfo);
    						
    	   					   count++;
    	   				   }
	   				   }
    	        	   return "成功同步"+count+"条记录！";
    	           }
    	           public void afterExec(Object result)throws Exception{
    	        	   FDCMsgBox.showInfo(result.toString());
    	           }
    	       }
    	       );
    	    dialog.show();
    	    this.refresh();
    	}
    }
    protected void btnYesTow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if (FDCMsgBox.showConfirm2New(this, "请确认是否同步主客户?") == MsgBox.YES) {
    		Window win = SwingUtilities.getWindowAncestor(this);
	        LongTimeDialog dialog = null;
	        if(win instanceof Frame) 
	        	dialog = new LongTimeDialog((Frame)win);
	        else
    	       if(win instanceof Dialog)
    	           dialog = new LongTimeDialog((Dialog)win);
    	       
    	       dialog.setLongTimeTask(new ILongTimeTask() {
    	    	   public Object exec()throws Exception{
    	    		   int count=0;
    	        	   SelectorItemCollection sel=new SelectorItemCollection();
    	   			   sel.add("sysCustomer");
    	   			   
    	   			   CSSPGroupInfo groupInfo=getCSSPGroup();
    	   			   for(int i=0;i<tblCNSame.getRowCount();i++){
    	   				   if(((Boolean)tblCNSame.getRow(i).getCell("isToSys").getValue()).booleanValue()){
    	   					   FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)tblCNSame.getRow(i).getCell("sheId").getUserObject());
    	   					   FDCMainCustomerFactory.getRemoteInstance().updatePartial(fdcinfo, sel);
    	   					   
    	   					   CustomerInfo info=((CustomerInfo)tblCNSame.getRow(i).getCell("sysId").getUserObject());
    	   					   updateCusGroup(info,groupInfo);
 	   					   
    	   					   count++;
    	   				   }
	   				   }
    	        	   return "成功同步"+count+"条记录！";
    	           }
    	           public void afterExec(Object result)throws Exception{
    	        	   FDCMsgBox.showInfo(result.toString());
    	           }
    	       }
    	       );
    	    dialog.show();
    	    this.refresh();
    	}
    }
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.destroyWindow();
    }
    protected void btnNoTow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.destroyWindow();
    }
    protected void initTable(KDTable table,boolean isHasSysTable){
    	table.checkParsed();
    	table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	table.getStyleAttributes().setLocked(true);
    	if(isHasSysTable){
    		table.getColumn("isToSys").getStyleAttributes().setLocked(false);
    		KDCheckBox checkBox = new KDCheckBox();
    		ICellEditor checkBoxEditor = new KDTDefaultCellEditor(checkBox);
    		table.getColumn("isToSys").setEditor(checkBoxEditor);
    	}
    	String[] fields=new String[table.getColumnCount()];
		for(int i=0;i<table.getColumnCount();i++){
			fields[i]=table.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(table,fields);
    }
    protected void refresh() throws BOSException{
    	this.tblAllNotSame.removeRows();
    	this.tblAllSame.removeRows();
    	this.tblCNSame.removeRows();
    	this.tblIsToSys.removeRows();
    	
    	OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
    	FilterInfo filter = new FilterInfo();
    	EntityViewInfo view=new EntityViewInfo();
    	
    	SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("mainCustomer.sysCustomer");
		sel.add("mainCustomer.sysCustomer.name");
		sel.add("mainCustomer.sysCustomer.number");
		sel.add("mainCustomer.sysCustomer.simpleName");
		sel.add("number");
		sel.add("name");
		sel.add("phone");
		sel.add("propertyConsultant.name");
		sel.add("certificateNumber");
		
		filter.getFilterItems().add(new FilterItemInfo("createUnit.id", orgUnit.getId()));
		view.setFilter(filter);
		view.setSelector(sel);
		SHECustomerCollection col=SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		for(int i=0;i<col.size();i++){
			SHECustomerInfo info=col.get(i);
			if(info.getMainCustomer()==null)continue;
			if(info.getMainCustomer().getSysCustomer()!=null){
				IRow row=this.tblIsToSys.addRow();
				addSHERow(row,info);
				addSysRow(row,info.getMainCustomer().getSysCustomer());
			}else if(info.getName()==null||info.getCertificateNumber()==null||"".equals(info.getName().trim())||"".equals(info.getCertificateNumber().trim())){
				IRow row=this.tblAllNotSame.addRow();
				addSHERow(row,info);
			}else{
				getCustomerInfo(info.getName(),info.getCertificateNumber(),info);
			}
		}
    }
    protected void addSHERow(IRow row,SHECustomerInfo info){
    	row.getCell("sheId").setValue(info.getId());
    	row.getCell("sheId").setUserObject(info.getMainCustomer());
    	row.getCell("sheNumber").setValue(info.getNumber());
    	row.getCell("sheName").setValue(info.getName());
    	row.getCell("sheCertificateNumber").setValue(info.getCertificateNumber());
    	row.getCell("shePhone").setValue(info.getPhone());
    	row.getCell("sheSaleMan").setValue(info.getPropertyConsultant().getName());
    }
    protected void addSysRow(IRow row,CustomerInfo info){
    	row.getCell("isToSys").setValue(Boolean.FALSE);
    	row.getCell("sysId").setValue(info.getId());
    	row.getCell("sysId").setUserObject(info);
    	row.getCell("sysNumber").setValue(info.getNumber());
    	row.getCell("sysName").setValue(info.getName());
    	row.getCell("sysSimpleName").setValue(info.getSimpleName());
    }
    protected void getCustomerInfo(String name,String certificateNumber,SHECustomerInfo sheInfo) throws BOSException{
    	SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("number");
		sel.add("name");
		sel.add("simpleName");
		FilterInfo filter = new FilterInfo();
    	EntityViewInfo view=new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("number", certificateNumber));
		view.setFilter(filter);
		view.setSelector(sel);
		CustomerCollection col=CustomerFactory.getRemoteInstance().getCustomerCollection(view);
		if(col.size()>0){
			CustomerInfo info=col.get(0);
			IRow row=null;
			if(name.equals(info.getName().trim())){
				row=this.tblAllSame.addRow();
			}else{
				row=this.tblCNSame.addRow();
			}
			addSHERow(row,sheInfo);
			addSysRow(row,info);
		}else{
			IRow row=this.tblAllNotSame.addRow();
			addSHERow(row,sheInfo);
		}
    }
	protected void tblAllSame_editStopped(KDTEditEvent e) throws Exception {
		if(((Boolean)this.tblAllSame.getRow(e.getRowIndex()).getCell("isToSys").getValue()).booleanValue()){
			this.tblAllSame.getRow(e.getRowIndex()).getStyleAttributes().setBackground(Color.PINK);
			FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblAllSame.getRow(e.getRowIndex()).getCell("sheId").getUserObject());
			CustomerInfo info=((CustomerInfo)this.tblAllSame.getRow(e.getRowIndex()).getCell("sysId").getUserObject());
			fdcinfo.setSysCustomer(info);
		}else{
			this.tblAllSame.getRow(e.getRowIndex()).getStyleAttributes().setBackground(Color.WHITE);
			FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblAllSame.getRow(e.getRowIndex()).getCell("sheId").getUserObject());
			fdcinfo.setSysCustomer(null);
		}
	}
	protected void tblCNSame_editStopped(KDTEditEvent e) throws Exception {
		if(((Boolean)this.tblCNSame.getRow(e.getRowIndex()).getCell("isToSys").getValue()).booleanValue()){
			this.tblCNSame.getRow(e.getRowIndex()).getStyleAttributes().setBackground(Color.PINK);
			FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblCNSame.getRow(e.getRowIndex()).getCell("sheId").getUserObject());
			CustomerInfo info=((CustomerInfo)this.tblCNSame.getRow(e.getRowIndex()).getCell("sysId").getUserObject());
			fdcinfo.setSysCustomer(info);
		}else{
			this.tblCNSame.getRow(e.getRowIndex()).getStyleAttributes().setBackground(Color.WHITE);
			FDCMainCustomerInfo fdcinfo=((FDCMainCustomerInfo)this.tblCNSame.getRow(e.getRowIndex()).getCell("sheId").getUserObject());
			fdcinfo.setSysCustomer(null);
		}
	}
    
}