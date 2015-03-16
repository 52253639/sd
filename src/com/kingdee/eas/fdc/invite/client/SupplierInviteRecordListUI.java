/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 供应商投标记录 序时薄界面
 */
public class SupplierInviteRecordListUI extends AbstractSupplierInviteRecordListUI
{
	private static final Logger logger = CoreUIObject.getLogger(SupplierInviteRecordListUI.class);
	public SupplierInviteRecordListUI() throws Exception
	{
		super();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkInvitePorjectSelected();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1) {
			MsgBox.showWarning("请选择一条招标立项");
		}
		String inviteProjectId = tblMain.getRow(rowIndex).getCell(COL_ID).getValue().toString();
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", inviteProjectId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
		
		if( SupplierInviteRecordFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning("存在未审批投标记录，无法新增");
			SysUtil.abort();
		}		
		
		super.actionAddNew_actionPerformed(e);
	}
	
	protected void refreshBillListTable(String paramId) throws BOSException {
		getBillListTable().removeRows();
		if(paramId==null) return;
		SupplierInviteRecordCollection recordCols = getBillList(paramId);
		if (recordCols != null) {
			Iterator iter = recordCols.iterator();
			while (iter.hasNext()) {
				SupplierInviteRecordInfo info = (SupplierInviteRecordInfo) iter.next();

				if(info != null){
					if(info.isIsMultiple()){
						for(int i=0;i<info.getEntry().size();i++){
							IRow row = getBillListTable().addRow();
							row.getCell(COL_ID).setValue(info.getId());
							row.getCell(COL_NUMBER).setValue(info.getNumber());
							row.getCell(COL_STATE).setValue(info.getState());

							if (info.getRespDept() != null) {row.getCell(COL_RESPDEPT).setValue(info.getRespDept().getName());
							}
							if (info.getCreator() != null) {
								row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
							}
							row.getCell(COL_CREATEDATE).setValue(info.getCreateTime());
							if (info.getAuditor() != null) {
								row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
							}
							row.getCell(COL_AUDITDATE).setValue(info.getAuditTime());
							
							row.getCell("openDate").setValue(info.getOpenDate());
							row.getCell("place").setValue(info.getPlace());
							if(info.getSupplier()!=null){
								row.getCell("supplier").setValue(info.getSupplier().getName());
							}
							
							row.getCell("returnDate").setValue(info.getReturnDate());
							row.getCell("times").setValue(Integer.valueOf(info.getTimes()));
							row.getCell("remark").setValue(info.getRemark());
							
							row.getCell("seq").setValue("第"+info.getEntry().get(i).getSeq()+"标段");
							row.getCell("price").setValue(info.getEntry().get(i).getPrice());
						}
					}else{
						IRow row = getBillListTable().addRow();
						row.getCell(COL_ID).setValue(info.getId());
						row.getCell(COL_NUMBER).setValue(info.getNumber());
						row.getCell(COL_STATE).setValue(info.getState());

						if (info.getRespDept() != null) {row.getCell(COL_RESPDEPT).setValue(info.getRespDept().getName());
						}
						if (info.getCreator() != null) {
							row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
						}
						row.getCell(COL_CREATEDATE).setValue(info.getCreateTime());
						if (info.getAuditor() != null) {
							row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
						}
						row.getCell(COL_AUDITDATE).setValue(info.getAuditTime());
						
						row.getCell("openDate").setValue(info.getOpenDate());
						row.getCell("place").setValue(info.getPlace());
						if(info.getSupplier()!=null){
							row.getCell("supplier").setValue(info.getSupplier().getName());
						}
						
						row.getCell("returnDate").setValue(info.getReturnDate());
						row.getCell("times").setValue(Integer.valueOf(info.getTimes()));
						row.getCell("remark").setValue(info.getRemark());
						
						row.getCell("price").setValue(info.getPrice());
					}
				}
			}
			mergerTable(this.getBillListTable(),new String[]{"id"},new String[]{COL_NUMBER,COL_STATE,COL_CREATOR,COL_CREATEDATE,COL_AUDITOR,COL_AUDITDATE
				,"openDate","place","supplier","returnDate","times","remark"});
		}
	}
	private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	static SupplierInviteRecordCollection getBillList(String paramId)throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("respDept.name"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));
		view.getSelector().add(new SelectorItemInfo("supplier.name"));
		view.getSelector().add(new SelectorItemInfo("entry.*"));
		
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("times"));
		sort.add(new SorterItemInfo("supplier.number"));
		sort.add(new SorterItemInfo("entry.seq"));
		view.setSorter(sort);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", paramId));

		view.setFilter(filter);

		SupplierInviteRecordCollection clarifyCol = SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);

		return clarifyCol;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierInviteRecordFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SupplierInviteRecordEditUI.class.getName();
	}
	protected String getTitle() {
		return "投标记录";
	}
	public void onLoad() throws Exception {
		super.onLoad();
		FDCHelper.formatTableDate(this.tblBaseInvite, "openDate");
		FDCHelper.formatTableDate(this.tblBaseInvite, "returnDate");
		this.tblBaseInvite.getColumn("price").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.tblBaseInvite.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		String[] fields=new String[] {"supplier", "price", "times"};
		KDTableHelper.setSortedColumn(tblBaseInvite,fields);
	}
	protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		sels.add("times");
		sels.add("inviteProject.id");
		sels.add("supplier.id");
		SupplierInviteRecordInfo info=(SupplierInviteRecordInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
		
		FDCBillStateEnum state = info.getState();
		
		if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
		int times=this.getTimes(info,true);
    	if(times!=info.getTimes()){
    		FDCMsgBox.showWarning(this,"请先删除最新回标次数投标记录！");
			SysUtil.abort();
    	}
	}
	protected int getTimes(SupplierInviteRecordInfo info,boolean isDelete) throws BOSException{
		if(info.getInviteProject()!=null&&info.getSupplier()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", info.getInviteProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("supplier.id", info.getSupplier().getId().toString()));
			if(info.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(),CompareType.NOTEQUALS));
			}
			if(!isDelete&&info.getCreateTime()!=null){
				filter.getFilterItems().add(new FilterItemInfo("createTime", info.getCreateTime(),CompareType.LESS));
			}
			view.setFilter(filter);
			SupplierInviteRecordCollection col=SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);
			return col.size()+1;
		}else{
			return 1;
		}
    }
}