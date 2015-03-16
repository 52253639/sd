/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AddToSysSupplierUI extends AbstractAddToSysSupplierUI
{
    private static final Logger logger = CoreUIObject.getLogger(AddToSysSupplierUI.class);
    public AddToSysSupplierUI() throws Exception
    {
        super();
    }
    public double dice(List<char[]> bigram1, List<char[]> bigram2)
    {
        List<char[]> copy = new ArrayList<char[]>(bigram2);
        int matches = 0;
        for (int i = bigram1.size(); --i >= 0;)
        {
            char[] bigram = bigram1.get(i);
            for (int j = copy.size(); --j >= 0;)
            {
                char[] toMatch = copy.get(j);
                if (bigram[0] == toMatch[0] && bigram[1] == toMatch[1])
                {
                    copy.remove(j);
                    matches += 2;
                    break;
                }
            }
        }
        return (double) matches / (bigram1.size() + bigram2.size());
    }
    public List<char[]> bigram(String input)
    {
        ArrayList<char[]> bigram = new ArrayList<char[]>();
        for (int i = 0; i < input.length() - 1; i++)
        {
            char[] chars = new char[2];
            chars[0] = input.charAt(i);
            chars[1] = input.charAt(i+1);
            bigram.add(chars);
        }
        return bigram;
    }
    public void onLoad() throws Exception {
    	initTable(this.tblAllNotSame,false);
    	initTable(this.tblIsToSys,false);
    	initTable(this.tblCNSame,true);
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
    	EntityViewInfo view=new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("sysSupplier.id", null,CompareType.NOTEQUALS));
    	
    	SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("sysSupplier.name");
		sel.add("sysSupplier.number");
		sel.add("sysSupplier.adminCU.name");
		sel.add("number");
		sel.add("name");
		sel.add("adminCU.name");
		
		view.setFilter(filter);
		view.setSelector(sel);
		SupplierStockCollection col=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection(view);
		for(int i=0;i<col.size();i++){
			SupplierStockInfo info=col.get(i);
			IRow row=this.tblIsToSys.addRow();
			addSupplierRow(row,info);
			if(info.getSysSupplier()!=null){
				addSysRow(row,info.getSysSupplier());
			}
		}
	}
    protected void initTable(KDTable table,boolean isHasSysTable){
    	table.checkParsed();
    	table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	table.getStyleAttributes().setLocked(true);
    	if(isHasSysTable){
    		table.getColumn("isToSys").getStyleAttributes().setLocked(false);
    		KDCheckBox checkBox = new KDCheckBox();
    		checkBox.setSelected(false);
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
    	this.tblCNSame.removeRows();
    	this.tblIsToSys.removeRows();
    	
    	Set<String> supplierId=new HashSet<String>();
    	Set<String> sysSupplierId=new HashSet<String>();
    	
    	FilterInfo filter = new FilterInfo();
    	EntityViewInfo view=new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("sysSupplier.id", null,CompareType.NOTEQUALS));
    	
    	SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("sysSupplier.name");
		sel.add("sysSupplier.number");
		sel.add("sysSupplier.adminCU.name");
		sel.add("number");
		sel.add("name");
		sel.add("adminCU.name");
		sel.add("browseGroup.id");
		sel.add("supplierGroupDetails.supplierGroup.id");
		
		view.setFilter(filter);
		view.setSelector(sel);
		SupplierStockCollection col=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection(view);
		for(int i=0;i<col.size();i++){
			SupplierStockInfo info=col.get(i);
			if(info.getSysSupplier()!=null){
				supplierId.add(info.getId().toString());
				sysSupplierId.add(info.getSysSupplier().getId().toString());
				IRow row=this.tblIsToSys.addRow();
				addSupplierRow(row,info);
				addSysRow(row,info.getSysSupplier());
			}
		}
		
		filter = new FilterInfo();
    	view=new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("sysSupplier.id", null));
    	if(this.txtName.getText()!=null&&!"".equals(this.txtName.getText())){
    		filter.getFilterItems().add(new FilterItemInfo("name", "%"+this.txtName.getText()+"%",CompareType.LIKE));
    	}
    	view.setFilter(filter);
    	view.setSelector(sel);
    	view.getSorter().add(new SorterItemInfo("name"));
    	col=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection(view);
    	
		for(int i=0;i<col.size();i++){
			SupplierStockInfo info=col.get(i);
			if(info.getName()==null||"".equals(info.getName().trim())) continue;
			
			double value=0;
	    	if(this.txtAmount.getBigDecimalValue()!=null){
	    		value=this.txtAmount.getBigDecimalValue().divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP).doubleValue();
	    	}
	    	
			SupplierInfo matchInfo=null;
			FilterInfo sysFilter = new FilterInfo();
	    	EntityViewInfo sysView=new EntityViewInfo();
//	    	if(sysSupplierId.size()>0){
//	    		sysFilter.getFilterItems().add(new FilterItemInfo("id", sysSupplierId,CompareType.NOTINCLUDE));
//	    	}
	    	sysView.setFilter(sysFilter);
	    	sysView.setSelector(sel);
	    	SupplierCollection sysCol=SupplierFactory.getRemoteInstance().getSupplierCollection(sysView);
	    	
			for(int j=0;j<sysCol.size();j++){
				double match=dice(bigram(info.getName()),bigram(sysCol.get(j).getName()));
				if(match==1){
					matchInfo=sysCol.get(j);
					break;
				}
				if(match>=value){
					matchInfo=sysCol.get(j);
					value=match;
				}
			}
			if(matchInfo!=null){
				sysSupplierId.add(matchInfo.getId().toString());
				supplierId.add(info.getId().toString());
				IRow row=this.tblCNSame.addRow();
				row.getCell("isToSys").setValue(Boolean.FALSE);
				addSupplierRow(row,info);
				addSysRow(row,matchInfo);
			}
		}
		filter = new FilterInfo();
    	view=new EntityViewInfo();
    	filter.getFilterItems().add(new FilterItemInfo("sysSupplier.id", null));
    	if(supplierId.size()>0){
    		filter.getFilterItems().add(new FilterItemInfo("id", supplierId,CompareType.NOTINCLUDE));
    	}
    	view.setFilter(filter);
    	view.setSelector(sel);
    	col=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection(view);
		for(int i=0;i<col.size();i++){
			SupplierStockInfo info=col.get(i);
			IRow row=this.tblAllNotSame.addRow();
			addSupplierRow(row,info);
		}
    }
    protected void addSupplierRow(IRow row,SupplierStockInfo info){
    	row.getCell("supplierId").setValue(info.getId());
    	row.getCell("supplierId").setValue(info.getId());
    	row.getCell("supplierId").setUserObject(info);
    	row.getCell("number").setValue(info.getNumber());
    	row.getCell("name").setValue(info.getName());
    	row.getCell("orgUnit").setValue(info.getAdminCU().getName());
    }
    protected void addSysRow(IRow row,SupplierInfo info){
    	row.getCell("sysId").setValue(info.getId());
    	row.getCell("sysId").setUserObject(info);
    	row.getCell("sysNumber").setValue(info.getNumber());
    	row.getCell("sysName").setValue(info.getName());
    	row.getCell("sysOrgUnit").setValue(info.getAdminCU().getName());
    }
	protected void tblCNSame_editStopped(KDTEditEvent e) throws Exception {
		if(((Boolean)this.tblCNSame.getRow(e.getRowIndex()).getCell("isToSys").getValue()).booleanValue()){
			this.tblCNSame.getRow(e.getRowIndex()).getStyleAttributes().setBackground(Color.PINK);
			SupplierStockInfo fdcinfo=((SupplierStockInfo)this.tblCNSame.getRow(e.getRowIndex()).getCell("supplierId").getUserObject());
			SupplierInfo info=((SupplierInfo)this.tblCNSame.getRow(e.getRowIndex()).getCell("sysId").getUserObject());
			fdcinfo.setSysSupplier(info);
		}else{
			this.tblCNSame.getRow(e.getRowIndex()).getStyleAttributes().setBackground(Color.WHITE);
			SupplierStockInfo fdcinfo=((SupplierStockInfo)this.tblCNSame.getRow(e.getRowIndex()).getCell("supplierId").getUserObject());
			fdcinfo.setSysSupplier(null);
		}
	}
	protected void ckbAllSelectTwo_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    	for(int i=0;i<this.tblCNSame.getRowCount();i++){
			this.tblCNSame.getRow(i).getCell("isToSys").setValue(new Boolean(this.ckbAllSelectTwo.isSelected()));
			if(this.ckbAllSelectTwo.isSelected()){
				this.tblCNSame.getRow(i).getStyleAttributes().setBackground(Color.PINK);
				SupplierStockInfo fdcinfo=((SupplierStockInfo)this.tblCNSame.getRow(i).getCell("supplierId").getUserObject());
				SupplierInfo info=((SupplierInfo)this.tblCNSame.getRow(i).getCell("sysId").getUserObject());
				fdcinfo.setSysSupplier(info);
			}else{
				this.tblCNSame.getRow(i).getStyleAttributes().setBackground(Color.WHITE);
				SupplierStockInfo fdcinfo=((SupplierStockInfo)this.tblCNSame.getRow(i).getCell("supplierId").getUserObject());
				fdcinfo.setSysSupplier(null);
			}
    	}
    }
	protected void btnNoTow_actionPerformed(java.awt.event.ActionEvent e) throws Exception{
	    this.destroyWindow();
	}
	protected void btnYesTow_actionPerformed(java.awt.event.ActionEvent e) throws Exception{
    	if (FDCMsgBox.showConfirm2New(this, "请确认是否同步主供应商?") == MsgBox.YES) {
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
    	   			   sel.add("sysSupplier");
    	   			   
    	   			   CSSPGroupInfo groupInfo=getCSSPGroup();
    	   			   for(int i=0;i<tblCNSame.getRowCount();i++){
    	   				   if(((Boolean)tblCNSame.getRow(i).getCell("isToSys").getValue()).booleanValue()){
    	   					   SupplierInfo info=((SupplierInfo)tblCNSame.getRow(i).getCell("sysId").getUserObject());
    	   					   updateSupplierGroup(info,groupInfo);
    	   					   
    	   					   SupplierStockInfo fdcinfo=((SupplierStockInfo)tblCNSame.getRow(i).getCell("supplierId").getUserObject());
    	   					   SupplierStockFactory.getRemoteInstance().updatePartial(fdcinfo, sel);
  	   					   
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
	protected CSSPGroupInfo getCSSPGroup() throws EASBizException, BOSException{
    	CSSPGroupInfo groupInfo = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("name", "房地产供应商"));
		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		
		CSSPGroupCollection sheGroupCol = CSSPGroupFactory.getRemoteInstance().getCSSPGroupCollection(view);
		
		if(sheGroupCol.isEmpty()){
			CtrlUnitInfo cu = new CtrlUnitInfo();
			cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
			
			CSSPGroupStandardInfo strd = new CSSPGroupStandardInfo();
			strd.setId(BOSUuid.create(strd.getBOSType()));
			strd.setNumber("fdcsupplierGstrd");
			strd.setName("房地产供应商分类标准");
			strd.setType(2);
			strd.setIsBasic(StandardTypeEnum.defaultStandard);
			strd.setCU(cu);
			
			CSSPGroupStandardFactory.getRemoteInstance().addnew(strd);
			
			CSSPGroupInfo gr = new CSSPGroupInfo();
			gr.setDeletedStatus(DeletedStatusEnum.NORMAL);
			gr.setId(BOSUuid.create(gr.getBOSType()));
			gr.setNumber("fdcsupplierG");
			gr.setName("房地产供应商");
			gr.setCU(cu);
			gr.setGroupStandard(strd);
			
			CSSPGroupFactory.getRemoteInstance().addnew(gr);
			
			groupInfo = gr;
		}else{
			groupInfo = sheGroupCol.get(0);
		}
		
		return groupInfo;
    }
    protected void updateSupplierGroup(SupplierInfo supplier,CSSPGroupInfo groupInfo) throws EASBizException, BOSException{
    	if(supplier==null) return;
    	SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("browseGroup.id");
		sel.add("supplierGroupDetails.supplierGroup.id");
		
		supplier=SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(supplier.getId()),sel);
		if(supplier.getBrowseGroup()!=null&&supplier.getBrowseGroup().getId().toString().equals(groupInfo.getId().toString())){
			return;
		}
		for(int i=0;i<supplier.getSupplierGroupDetails().size();i++){
			if(supplier.getSupplierGroupDetails().get(i).getSupplierGroup()!=null
					&&supplier.getSupplierGroupDetails().get(i).getSupplierGroup().getId().toString().equals(groupInfo.getId().toString())){
				return;
			}
		}
		SupplierGroupDetailInfo Gdinfo = new SupplierGroupDetailInfo();
		FDCSQLBuilder fdcSB = new FDCSQLBuilder();
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer idsql = new StringBuffer();
		idsql.append("update t_bd_supplier set fBrowseGroupid ='"+groupInfo.getId().toString()+"' where fid = '").append(supplier.getId().toString()).append("'");
		fdcSB.addBatch(idsql.toString());
		
		idsql = new StringBuffer();
		
		idsql.append(" insert into t_bd_suppliergroupdetail(FID, FSUPPLIERGROUPSTANDARDID, FSUPPLIERGROUPID, FSUPPLIERID, FSUPPLIERGROUPFULLNAME)");
		idsql.append(" values ('"+BOSUuid.create(Gdinfo.getBOSType())+"', '"+groupInfo.getGroupStandard().getId().toString()+"', '"+groupInfo.getId().toString()+"', '"+supplier.getId().toString()+"', N'"+groupInfo.getName()+"')");
		fdcSB.addBatch(idsql.toString());
		
		fdcSB.executeBatch();
    }
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		Window win = SwingUtilities.getWindowAncestor(this);
	       LongTimeDialog dialog = null;
	       if(win instanceof Frame)
	           dialog = new LongTimeDialog((Frame)win);
	       else
	       if(win instanceof Dialog)
	           dialog = new LongTimeDialog((Dialog)win);
	       dialog.setLongTimeTask(new ILongTimeTask() {
	           public Object exec()
	               throws Exception
	           {
	        	   refresh();
	               return new Boolean(true);
	           }
	           public void afterExec(Object result)
	               throws Exception
	           {
	           }
	       }
	       );
	       dialog.show();
	 }
}