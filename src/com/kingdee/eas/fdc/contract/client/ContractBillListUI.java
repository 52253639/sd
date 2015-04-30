/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.BOSUIContext;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PaymentManageRelateBillUI;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.FrameWorkException;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * 描述:合同叙事簿界面
 * 
 * @author liupd date:2006-7-25
 *         <p>
 * @version EAS5.1.3
 */
public class ContractBillListUI extends AbstractContractBillListUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractBillListUI.class);

	private int viewType = 0;//合同编辑信息
	private Map contentMap = new HashMap();
	private Map attachMap = new HashMap();
	// 再次归档时更新合同编号
	private boolean isUpdateConNo = false;
	// 合同，变更审批后上传附件
    private boolean canUploadForAudited = false;
	/**
	 * 最后审批人，最后审批时间
	 */
	Map auditMap = new HashMap();
	//房地产业务系统正文管理是否启用审批笔迹留痕功能
	boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
	//补充合同可以选择提交状态的主合同
	private boolean isSupply = false;
	/**
	 * output class constructor
	 */
	public ContractBillListUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		if(isUseWriteMark){
			this.btnAddContent.setEnabled(false);
			this.menuItemAddContent.setEnabled(false);
		}else{
			this.btnAddContent.setEnabled(true);
			this.menuItemAddContent.setEnabled(true);
		}
		
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				boolean isMainOrder = false;
				SorterItemCollection sortItems = mainQuery.getSorter();
				if(sortItems!=null && sortItems.size()>0){
		        	for(Iterator it=sortItems.iterator();it.hasNext();){
		        		SorterItemInfo sortItem = (SorterItemInfo)it.next();
		        		if(sortItem.getPropertyName().equals("mainContractNumber")){
		        			isMainOrder = true;
		        			break;
		        		}
		        	}
		        }
				if(attachMap==null){
					attachMap=new HashMap();
				}
				if(auditMap==null){
					auditMap=new HashMap();
				}
				if(contentMap==null){
					contentMap=new HashMap();
				}
				String preNumber = null;
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					String idkey = row.getCell("id").getValue().toString();
					if(contentMap.containsKey(idkey)){
						row.getCell("content").setValue(Boolean.TRUE);
					}
					else{
						row.getCell("content").setValue(Boolean.FALSE);
					}
					if(attachMap.containsKey(idkey)){
						row.getCell("attachment").setValue(Boolean.TRUE);
					}
					else{
						row.getCell("attachment").setValue(Boolean.FALSE);
					}
					if(auditMap.containsKey(idkey)){
						MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);
						//如果有多级审批，如此编码就会导致最后审批人和最后审批时间数据部正确,BUG,by Cassiel_peng
//						if(row.getCell("auditor").getValue()==null){
//							row.getCell("auditor").setValue(info.getCreator().getName());
//							row.getCell("auditTime").setValue(info.getCreateTime());
//						}
					}
					if(isMainOrder){
//							 node = new CellTreeNode();
						String number = "";
						if(row.getCell("mainContractNumber")!=null){
							if (row.getCell("mainContractNumber").getValue() != null) {
								number = row.getCell("mainContractNumber").getValue().toString();
							}
							if (!number.equals(preNumber)) {
								row.setTreeLevel(0);
							} else  if (!"".equals(number)){//
								row.setTreeLevel(1);
								tblMain.getTreeColumn().setDepth(2);
								row.getCell("number").setValue("   " + row.getCell("number").getValue());
							}
							preNumber = number;
							
							//tblMain.getColumn("mainContractNumber").getStyleAttributes().setHided(true);
						}
					}
				}
			}
		});
				
		super.onLoad();
		actionStore.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_archive"));
		actionAntiStore.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_undistribute"));
		
//		this.btnStore.setIcon(EASResource.getIcon("imgTbtn_archive"));
/*		kDSplitPane2.add(new KDLabel(""), "bottom");
//		this.kDSplitPane2.setDividerLocation(getHeight());
		kDSplitPane2.setDividerLocation(1.0);
		this.kDSplitPane2.setDividerSize(0);
		kDScrollPane2.validate();*/
		kDSplitPane2.add(contContrList, "bottom");
		kDSplitPane2.setDividerLocation(1.0);
		actionConMove.setEnabled(true);
		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
		actionProjectAttachment.setVisible(false);
		actionProjectAttachment.setEnabled(false);
		
		this.btnEditPerson.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnEditAuditDate.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnEditContractType.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnUnProgram.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		if(this.getUIContext().get("filter")!=null){
			this.toolBar.setVisible(false);
			this.pnlLeftTree.setVisible(false);
			mainQuery=new EntityViewInfo();
			mainQuery.setFilter((FilterInfo) this.getUIContext().get("filter"));
			execQuery();
		}
		this.actionExportData.setVisible(false);
	}
	protected void treeSelectChange() throws Exception {
		if(this.getUIContext().get("filter")!=null){
			return;
		}
		super.treeSelectChange();
	}
	public void buildProjectTree() throws Exception {
		if(this.getUIContext().get("filter")!=null){
			return;
		}
		super.buildProjectTree();
	}
	public void buildContractTypeTree() throws Exception {
		if(this.getUIContext().get("filter")!=null){
			return;
		}
		super.buildContractTypeTree();
	}
	protected void updateButtonStatus() {
		// TODO Auto-generated method stub
		super.updateButtonStatus();
		
//		放开，在虚体可以增删改
		actionAddNew.setEnabled(true);
		actionEdit.setEnabled(true);
		actionRemove.setEnabled(true);
		actionAddNew.setVisible(true);
		actionEdit.setVisible(true);
		actionRemove.setVisible(true);
		actionAttachment.setEnabled(true);
		actionAttachment.setVisible(true);
		menuEdit.setVisible(true);
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);

	}
	
	public void refreshListForOrder() throws Exception
    {
        SorterItemCollection sortItems = this.mainQuery.getSorter();
        if(sortItems!=null && sortItems.size()>0){
        	for(Iterator it=sortItems.iterator();it.hasNext();){
        		SorterItemInfo sortItem = (SorterItemInfo)it.next();
        		if(sortItem.getPropertyName().equals("mainContractNumber")){
        			SorterItemInfo cloneSort = (SorterItemInfo)sortItem.clone();
        			SorterItemInfo newSort = new SorterItemInfo();
        			newSort.setPropertyName("contractPropert");
        			newSort.setSortType(SortType.ASCEND);
        			this.mainQuery.getSorter().clear();
        			this.mainQuery.getSorter().add(cloneSort);
        			this.mainQuery.getSorter().add(newSort);
        			break;
        		}
        	}
        }
        super.refreshListForOrder();
    }

	/**
	 * output tblMain_tableSelectChanged method
	 */
protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);	
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()!= -1) {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
			if((tblMain.getSelectManager().getBlocks().size() > 1) 
				||(e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() >0)){
				actionRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			}else{
				if(Boolean.TRUE.equals(row.getCell("isRespite").getValue())){
					actionRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}else{
					actionRespite.setEnabled(true);
					actionCancelRespite.setEnabled(false);
				}
			}
			if(row.getCell("state").getValue().equals("合计")) return;
			if (row.getCell("state") != null) {		
				if(FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell("state").getValue()).getValue().toString())){
					if((e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() ==0)&&
							(tblMain.getSelectManager().getBlocks().size()== 1)){
						actionCancelRespite.setEnabled(true);
						actionRespite.setEnabled(false);
					}
					
					if(!((Boolean)row.getCell("isArchived").getValue()).booleanValue()){
						actionStore.setEnabled(true);
						actionAntiStore.setEnabled(false);
					}else{
						actionAntiStore.setEnabled(true);
						actionStore.setEnabled(false);
					}
				}else{
					actionStore.setEnabled(false);
					actionAntiStore.setEnabled(false);
				}
			}
		}
	}

	public void actionStore_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		HashMap hm = new UIContext(this);
		hm.put("isUpdateConNo", Boolean.valueOf(isUpdateConNo));
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()== -1) {
			//提示选中
		}else{
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
			
			if(row.getCell("id")!=null){
				hm.put("id", row.getCell("id").getValue().toString());
			}
			if(row.getCell("number")!=null){
				hm.put("number", row.getCell("number").getValue().toString());
			}
			/*
			 * 把当前选中的工程项目和合同类型传给EditUI
			 */
//			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
//			BOSUuid typeId = null;
//			if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
//
//				typeId = ((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject()).getId();
//			}
//			hm.put("projectId", projId);
//			hm.put("contractTypeId", typeId);
			
		}	
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.contract.client.ContractBillStoreUI", hm, null,null);
		uiWindow.show();
		actionRefresh_actionPerformed(e);	
	}
	
	//审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		获取用户选择的块
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			if(isRelatedTask()){
				for(int i = 0 ;i<idList.size();i++){
					String idKey = idList.get(i).toString();
					ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(idKey));
					ContractClientUtils.checkConRelatedTaskSubmit(contract);
				}
			}
			
			
			super.actionAudit_actionPerformed(e);
	
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
	}
	//合同提交审批时是否必须与计划任务进行关联 2010-08-09
	private boolean isRelatedTask(){
		boolean isRealtedTask = false;
		String cuID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isRealtedTask = FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_RELATEDTASK);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isRealtedTask;
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientHelper.checkAuditor(getSelectedIdValues(), "t_con_ContractBill");
		
		//R110603-0148:如果存在变更审批单，则不允许反审批
	    if (ContractUtil.hasChangeAuditBill(null, getSelectedIdValues())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasChangeAuditBill"));
			this.abort();
    	}
    	
    	//R110603-0148:如果存在变更指令单，则不允许反审批
    	if (ContractUtil.hasContractChangeBill(null, getSelectedIdValues())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasContractChangeBill"));
			this.abort();
    	}
    	
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionUnAudit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionRemove_actionPerformed(e);
		// 维护框架合约是否引用字段
	}
	
	/**
	 * 检查合同是否关联框架合约
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre() throws Exception {
		String billId = null;
		String selectedKeyValue = getSelectedKeyValue();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		builder.appendSql("select fprogrammingContract from T_CON_CONTRACTBILL where 1=1 and ");
		builder.appendParam("fid", selectedKeyValue);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				billId=rowSet.getString("fprogrammingContract");
			}
		}
		return billId;
	}

	
	private void checkConRelatedTaskDelUnAudit() throws BOSException, SQLException{
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		
		if(selectedIdValues!=null&&selectedIdValues.size()==0){
			return;
		}
		
		Set contractIds = FDCHelper.getSetByList(selectedIdValues);
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendParam("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID",contractIds.toArray());
		builder.appendSql(" group by fid ");
		IRowSet rowSet = builder.executeQuery();
		Set ids = new HashSet();
		while(rowSet.next()){
			int count = rowSet.getInt("jishu");
			if(count >0){
				String id = rowSet.getString("fid");
				ids.add(id);
			}
		}
		boolean flag = false;
		if(ids.size()>0){
			builder.clear();
			builder.appendParam("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID  ",ids.toArray());
			IRowSet _rowSet = builder.executeQuery();
			while(_rowSet.next()){
				int _count = _rowSet.getInt("jishu");
				if(_count > 0){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			FDCMsgBox.showInfo("合同已被任务关联，不能执行此操作。");
			SysUtil.abort();
		}
		
	}
	
	//反归档
    public void actionAntiStore_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	
    	checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");
    	
    	List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
    	
		IContractBill icb = ContractBillFactory.getRemoteInstance();
		boolean flag = false;
		flag = icb.contractBillAntiStore(selectedIdValues);
		if (flag) {				
			FDCClientUtils.showOprtOK(this);
			actionRefresh_actionPerformed(e);	
		}
    }

    /**
     * 支持定位功能，定位字段有：合同编号，合同名称，合同类型，签约时间，状态，签约乙方，合同原币金额，本位币金额
     * Modified by Owen_wen 2010-09-06
     */
	protected String[] getLocateNames()
    {
		return new String[] {IFWEntityStruct.dataBase_Number, IFWEntityStruct.dataBase_Name, "contractType.name", 
				"signDate", "state", "partB.name", "originalAmount", "amount",};
    }
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		if(viewType==0){
			return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();
		}else{
			return getContractUIName() ;
		}
	}
	
	protected String getContractUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractFullInfoUI.class.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		objectValue.setCurProject((CurProjectInfo) getProjSelectedTreeNode().getUserObject());
		if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
			objectValue.setContractType((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject());
		}
		return objectValue;
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return (ICoreBillBase) ContractBillFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select bill.fcontractpropert from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on  bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=entry.fcontent and main.fstate <> '4AUDITTED' ");
	    builder.appendSql("where ");
	    builder.appendParam("bill.fid", FDCHelper.list2Set(ids).toArray());
	    builder.appendSql(" and bill.fcontractpropert='SUPPLY' ");
	    builder.appendSql(" and entry.fdetail='对应主合同编码' ");
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()==1){
	    	rs.next();
	    	String prop = rs.getString("fcontractpropert");
	    	if("SUPPLY".equals(prop)){
	    		FDCMsgBox.showWarning(this,"您所选择的数据中存在主合同未审批的补充合同，不能进行此操作!");
		    	this.abort();
	    	}
	    }
	        
		ContractBillFactory.getRemoteInstance().audit(ids);
	}
	
	/**
	 * 如果为非单独计算补充合同，则不允许反审批
	 * 
	 * @param billId
	 */
	private int unLongContract(List ids) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(1) from t_con_contractbillentry entry");
		builder.appendSql(" inner join t_con_contractbill con on con.fid = entry.fparentid   ");
		builder.appendSql(" inner join t_con_contractbillentry entry2 on entry2.fparentid = entry.fparentid ");
		builder.appendSql("  where entry.frowkey = 'am' and");
		builder.appendParam(" entry2.fcontent", FDCHelper.list2Set(ids).toArray());
		try {
			RowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				return rowSet.getInt(1);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	protected void unAudit(List ids) throws Exception {
		// 如果为非单独计算补充合同，则不允许反审批
		int unLongContract = unLongContract(ids);
		if (unLongContract == 1) {
			FDCMsgBox.showWarning("合同已关联补充合同不能进行此操作");
			SysUtil.abort();
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select * from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on  main.fid=entry.fcontent ");
	    builder.appendSql("where");
	    builder.appendParam("main.fid", FDCHelper.list2Set(ids).toArray());
	    if(isSupply){
	    	builder.appendSql(" and bill.fstate='4AUDITTED'");
	    }
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()>0){
			FDCMsgBox.showWarning(this, "您所选择的数据存在被补充合同引用的合同，不能进行此操作!");
			this.abort();
		}
	    
	    builder.clear();
	    builder.appendSql("select count(fid) from t_con_contractbill where ");
	    builder.appendParam("FMainContractID", FDCHelper.list2Set(ids).toArray());
	    rs = builder.executeQuery();
	    if(rs!= null && rs.size() > 0 && rs.next() && rs.getInt(1) > 0){
	    	FDCMsgBox.showWarning(this, "您所选择的数据存在被战略子合同引用的合同，不能进行此操作!");
			this.abort();
	    }
		ContractBillFactory.getRemoteInstance().unAudit(ids);
	}

	protected KDTable getBillListTable() {

		return getMainTable();
	}

	protected void initTable() {
		super.initTable();
		
		//freezeMainTableColumn();
		FDCHelper.formatTableNumber(getMainTable(), "srcAmount");
		FDCHelper.formatTableNumber(getMainTable(), "amount");
//		FDCHelper.formatTableNumber(getMainTable(), "exRate");
		getMainTable().getColumn("exRate").getStyleAttributes().setHided(true);
		//FDCHelper.formatTableNumber(getMainTable(), "localAmount");
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
	}

	protected ArrayList getExportParam() {
		
		DatataskParameter param = new DatataskParameter();
		param.solutionName = "eas.fdc.contract.fdccontractbill";
		param.alias = "合同单据";
		param.datataskMode = DatataskMode.ExpMode;
		//param.putContextParam("ids",FDCHelper.list2Set(idList));
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
		
	}
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
//		FilterInfo filter = this.mainQuery.getFilter();
//		if(filter != null){
//			boolean flag = false;
//			for(Iterator it=filter.getFilterItems().iterator();it.hasNext();){
//				FilterItemInfo info = (FilterItemInfo)it.next();
//				if(info.getPropertyName().equals("id")){
//					info.setCompareValue(FDCHelper.list2Set(idList));
//					info.setCompareType(CompareType.INCLUDE);
//					flag = true;
//					break;
//				}
//			}
//			if(flag==false){
//				filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
//				filter.setMaskString("( "+filter.getMaskString()+" ) and #"+(filter.getFilterItems().size()-1));
//			}
//			
//		}
		ArrayList para = getExportParam();
        if (para == null || para.size() <= 0)
        {
            throw new FrameWorkException(FrameWorkException.EXPORTDATAPARANULL);// "请设置正确的引出参数。"此处需要修改，请在EASBizException中定义此异常信息

        }

        Object tmp = para.get(0);
        if (tmp instanceof DatataskParameter)
        {
            DatataskParameter dp = (DatataskParameter) tmp;
            IMetaDataPK contractExportQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillForExportQuery");
            EntityViewInfo view = new EntityViewInfo();
            //view.getSelector().add("*");
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
            view.setFilter(filter);
            dp.putContextParam("mainQueryPK", contractExportQueryPK);
            dp.putContextParam("mainQuery", view);
        }

        DatataskCaller dc = new DatataskCaller();
        dc.setParentComponent(this);
        dc.invoke(para, DatataskMode.ExpMode);
    }

	/**
	 * 
	 * 描述：为当前单据的新增、编辑、查看准备Context
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext, java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {

			/*
			 * 把当前选中的工程项目和合同类型传给EditUI
			 */
			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
			BOSUuid typeId = null;
			if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
				if(getTypeSelectedTreeNode().getUserObject() instanceof ContractTypeInfo){
					typeId = ((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject()).getId();
				}
			}
			uiContext.put("projectId", projId);
			
			if(typeId!=null)
			{
				try {
					ContractTypeInfo contractTypeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(typeId));
					
					if(contractTypeInfo.getLongNumber().indexOf("07YX")>=0)
					{
						MsgBox.showWarning("营销类合同必须由立项单发起，不允许直接新增！");
						SysUtil.abort();
					}
					else
					{
						uiContext.put("contractTypeId", typeId);
					}
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
				
			}
			else
				uiContext.put("contractTypeId", null);
		}
	}

	protected boolean isHasBillTable() {
		return false;
	}

	/**
	 * 
	 * 描述：检查是否有关联对象
	 * @author:liupd
	 * 创建时间：2006-8-26 <p>
	 */
	protected void checkRef(String id) throws Exception {
		ContractClientUtils.checkContractBillRef(this, id);

	}

	/**
	 * 工程附件
	 */
	public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception {
		//根据新需求在点击工程附件的时候即使没选中行值选中了工程也需要能添加附件 fengYJ 2008-12-16
		//		checkSelected();
		super.actionProjectAttachment_actionPerformed(e);

		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String contractId = this.getSelectedKeyValue();
		if(contractId!=null){
			ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			CurProjectInfo curProject = contract.getCurProject();
			if (curProject == null) {
				return;
			}
			acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
			
		//update by david_yang 2011.03.29
		}else if(getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo){
			CurProjectInfo curProject = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
			if (curProject == null) {
				return;
			}
			acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
		}else{
			MsgBox.showWarning("请选择工程项目进行添加附件！");
		}
	}
	
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//    	super.actionAttachment_actionPerformed(e);
    	checkSelected(tblMain);
    	boolean isEdit=false;
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    	String boID = this.getSelectedKeyValue();
    	if (boID == null)
    	{
    		return;
    	}
    	if(getBillStatePropertyName()!=null){
    		int rowIdx=tblMain.getSelectManager().getActiveRowIndex();
    		ICell cell =tblMain.getCell(rowIdx, getBillStatePropertyName());
    		Object obj=cell.getValue();
    		isEdit=ContractClientUtils.canUploadAttaForAudited(obj, canUploadForAudited);
    	}
    	//add by david_yang PT043562 2011.03.29
    	 if(isEdit){
         	//合同
 	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
 	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
 			if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.ContractBillListUI")){
 				String uiName = "com.kingdee.eas.fdc.contract.client.ContractBillEditUI";
 				boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
 	    				new ObjectUuidPK(userId),
 	    				new ObjectUuidPK(orgId),
 	    				new MetaDataPK(uiName),
 	    				new MetaDataPK("ActionAttamentCtrl") );
 				//如果未启用参数则有权限的用户才能进行附件维护,如果已经启用了参数则制单人等于当前用户且有权限才能进行 维护
 	        	if(hasFunctionPermission){
 	        		boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
 	        		if(creatorCtrl){
 	        			//制单人要等于当前用户才行
 	        			FDCSQLBuilder builder=new FDCSQLBuilder();
 	        			builder.appendSql("select 1 from T_Con_ContractBill where fid=? and fcreatorId=?");
 	        			builder.addParam(boID);
 	        			builder.addParam(userId);
 	        			if(!builder.isExist()){
 	        				isEdit=false;
 	        			}
 	        		}
 	        	}else{
 	        		isEdit=false;
 	        	}
 	        }
    	}
    	acm.showAttachmentListUIByBoID(boID,this,isEdit);
    	this.refreshList();
    }

	/**
	 * 查看正文
	 */
	public void actionViewContent_actionPerformed(ActionEvent arg0) throws Exception {
			this.checkSelected();
			ContractBillInfo billInfo=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
			if(isUseWriteMark&&billInfo!=null&&billInfo instanceof FDCBillInfo){//参数值为是，并且基类要是FDCBill.
				//序时簿界面的操作状态默认地为VIEW,但我们要能够使得在序时簿界面上(假设单据状态合适)能够添加、修改、删除正文就不能直接传递一个 getOprtState()状态 
				ForWriteMarkHelper.isUseWriteMarkForListUI(billInfo,OprtState.EDIT,this);
			}else{
				ContractClientUtils.viewContent(this, this.getSelectedKeyValue());
			}
		}
	
	/**
	 * 合同执行信息
	 */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    	viewType=-1;
    	try {
			actionView_actionPerformed(e);
		} catch (Exception e1) {
			throw e1;
		}finally{
			viewType=0;
		}    	
    }
    //允许审批后添加合同正文  by cassiel_peng
    private boolean isAddContentAfterAudited() {
		boolean returnVal=false;
		try {
			returnVal=FDCUtils.getDefaultFDCParamByKey(null, null,FDCConstants.FDC_PARAM_ADDCONTENTAUDITED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}
	/**
	 * 添加正文
	 */
	public void actionAddContent_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String selectedKeyValue = this.getSelectedKeyValue();
		/*ContractBillInfo contract = new ContractBillInfo();
		String selectedKeyValue = this.getSelectedKeyValue();
		contract.setId(BOSUuid.read(selectedKeyValue));
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", selectedKeyValue);
		filter.appendFilterItem("state", FDCBillStateEnum.CANCEL_VALUE);
		if(ContractBillFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning(this, "合同已终止，不能添加正文！");
			SysUtil.abort();
		}*/
		//已审批的合同不能添加正文   by cassiel_peng 2009-11-06
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("state");
		ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(selectedKeyValue)),selector);
		if(contractBill!=null&&contractBill.getState()!=null&&FDCBillStateEnum.CANCEL.equals(contractBill.getState())){
			MsgBox.showWarning(this, "合同已终止，不能添加正文！");
			SysUtil.abort();
		}
		if(!isAddContentAfterAudited()){
			if(contractBill!=null&&contractBill.getState()!=null&&FDCBillStateEnum.AUDITTED.equals(contractBill.getState())){
				MsgBox.showWarning(this, "合同已审批，不能添加正文！");
				SysUtil.abort();
			}
		}
		
		ContractContentCollection coll = ContractContentFactory.getRemoteInstance().getContractContentCollection("select id,filetype where contract='" + contractBill.getId().toString() + "'");
		if (coll != null && coll.size() > 0) {
			//我晕！还说一个合同如果有两个正文就用第二个覆盖第一个，覆盖个鬼！by cassiel_peng
			/*if (MsgBox.showConfirm2(this, ContractClientUtils.getRes("hasContent")) == 2) {
				return;
			}*/
//			MsgBox.showWarning("已经存在一个正文，再次添加正文默认地第一个正文！");
		}
		File file = this.chooseFileByDialog();
		if (file == null) {
			return;
		}
		
		String fullname = file.getName();
		String extName = StringUtil4File.getExtendedFileName(fullname);
		for(int i=0;i<coll.size();i++){
			if(fullname.equals(((ContractContentInfo)coll.get(i)).getFileType())){
				MsgBox.showWarning(fullname+" 已经存在，不允许添加！");
				return;
			}
		}
		if(!file.canRead()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		byte[] content = null;
		try {
			content = FileGetter.getBytesFromFile(file);
		} catch (IOException ex) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		
		ContractContentInfo contentInfo = new ContractContentInfo();
		contentInfo.setVersion(new BigDecimal("1.0"));
		contentInfo.setContract(contractBill);
		contentInfo.setFileType(fullname);
		contentInfo.setContentFile(content);
		ContractContentFactory.getRemoteInstance().addnew(contentInfo);
		super.actionAddContent_actionPerformed(e);
	}
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		if(idList.size()>1){
			FDCMsgBox.showWarning("请选择一条记录进行操作！");
			SysUtil.abort();
		}
		String selectedKeyValue = getSelectedKeyValue();
//		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
//			FDCMsgBox.showWarning("已在工作流处理中，请稍后再试！");
//			SysUtil.abort();
//		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		sic.add("curProject.isWholeAgeStage");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(selectedKeyValue), sic);
		if(contractBillInfo.getCurProject().isIsWholeAgeStage()){
			FDCMsgBox.showWarning("请进行跨期合同合约规划拆分操作！");
			SysUtil.abort();
		}
		if(contractBillInfo.getContractPropert().equals(ContractPropertyEnum.SUPPLY)){
			FDCMsgBox.showWarning("请选择主合同进行合约规划关联！");
			SysUtil.abort();
		}
		boolean isRealToProgramming =FDCUtils.getDefaultFDCParamByKey(null,contractBillInfo.getOrgUnit().getId().toString(), "FDC900_CON_OLDCONTRACTRTOPROGRAMMING");
		if(isRealToProgramming){
			if(!contractBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
				FDCMsgBox.showWarning("合同未审批，不能进行合约规划关联！");
				SysUtil.abort();
			}
		}else{
			FDCMsgBox.showWarning("审批后合同关联合约规划参数未启用，不能进行合约规划关联！");
			SysUtil.abort();
		}
		
		ProgrammingContractInfo pc = (ProgrammingContractInfo) contractBillInfo.getProgrammingContract();
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("contractBillInfo", contractBillInfo);
		uiContext.put("isWholeAgeStage", new Boolean(contractBillInfo.getCurProject().isIsWholeAgeStage()));
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {
			Object object = uiWindow.getUIObject().getUIContext().get("cancel");
			if (object != null) {
				return;
			} else {
				String param = null;
				SelectorItemCollection sict = new SelectorItemCollection();
				sict.add("*");
				sict.add("fullOrgUnit.*");
				ObjectUuidPK pk = new ObjectUuidPK(contractBillInfo.getOrgUnit().getId());
				param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", pk);
				if (!com.kingdee.util.StringUtils.isEmpty(param)) {
					if(pc!=null&&pc.getId().toString().equals(contractBillInfo.getProgrammingContract().getId().toString())){
						return;
					}
//					int i = Integer.parseInt(param);
//					switch (i) {
//					case 0:
//						// 严格控制时
//						if (contractBillInfo.getProgrammingContract() != null) {
//							if (contractBillInfo.getAmount().compareTo(FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance()))) > 0) {
//								FDCMsgBox.showWarning(this, "合同签约金额超过关联的合约控制余额！");
//								SysUtil.abort();
//							}
//						} else {
//							FDCMsgBox.showWarning(this, "未关联框架合约！");
//							SysUtil.abort();
//						}
//						break;
//					case 1:
//						// 提示控制时
//						if (contractBillInfo.getProgrammingContract() != null) {
//							if (contractBillInfo.getAmount().compareTo(FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance())) > 0) {
//								if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否关联？") == FDCMsgBox.CANCEL) {
//									SysUtil.abort();
//								}
//							}
//						} else {
//							FDCMsgBox.showWarning(this, "未关联框架合约！");
//							SysUtil.abort();
//						}
//						break;
//					case 2:
//						// 不控制时
//						if (contractBillInfo.getProgrammingContract() != null) {
//							if (contractBillInfo.getAmount().compareTo(
//									FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance())) > 0) {
//								if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否关联？") == FDCMsgBox.CANCEL) {
//									SysUtil.abort();
//								}
//							}
//						}
//						break;
//					}
					ContractBillFactory.getRemoteInstance().synReUpdateProgramming(contractBillInfo.getId().toString(), pc);
					SelectorItemCollection sicz = new SelectorItemCollection();
					sicz.add("programmingContract");
					ContractBillFactory.getRemoteInstance().updatePartial(contractBillInfo, sicz);
					ContractBillFactory.getRemoteInstance().synUpdateProgramming(contractBillInfo.getId().toString(), contractBillInfo.getProgrammingContract());
					FDCMsgBox.showInfo("关联框架合约成功！");
					this.refresh(null);
				}
			}
		}
	}
	public void actionUnProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		if(idList.size()>1){
			FDCMsgBox.showWarning("请选择一条记录进行操作！");
			SysUtil.abort();
		}
		String selectedKeyValue = getSelectedKeyValue();
//		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
//			FDCMsgBox.showWarning(this,"已在工作流处理中，请稍后再试！");
//			SysUtil.abort();
//		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("contractPropert");
		sic.add("programmingContract.*");
		sic.add("curProject.isWholeAgeStage");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(selectedKeyValue), sic);
		if(contractBillInfo.getCurProject().isIsWholeAgeStage()){
			FDCMsgBox.showWarning("请进行跨期合同合约规划拆分删除操作！");
			SysUtil.abort();
		}
		if(contractBillInfo.getProgrammingContract()==null){
			FDCMsgBox.showWarning(this,"请选择关联过合约规划记录进行取消操作！");
			SysUtil.abort();
		}
		if(contractBillInfo.getContractPropert().equals(ContractPropertyEnum.SUPPLY)){
			FDCMsgBox.showWarning(this,"请选择主合同进行取消操作！");
			SysUtil.abort();
		}else{
			ContractBillFactory.getRemoteInstance().synReUpdateProgramming(contractBillInfo.getId().toString(), contractBillInfo.getProgrammingContract());
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("programmingContract");
			contractBillInfo.setProgrammingContract(null);
			ContractBillFactory.getRemoteInstance().updatePartial(contractBillInfo, sel);
			FDCMsgBox.showInfo(this,"操作成功！");
			this.refresh(null);
		}
	}
	private File chooseFileByDialog() {
		File retFile = null;
		int retVal;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);

		retVal = fc.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > StringUtil4File.FILE_BYTES_LIMIT_SINGLE) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		}
		return retFile;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		/**
		 * 开放引入引出的菜单
		 */
		this.menuItemExportData.setVisible(true);
		this.menuItemExportData.setEnabled(true);
		
		this.menuItemImportData.setVisible(true);
		this.menuItemImportData.setEnabled(true);
		
		this.btnAddContent.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.menuItemAddContent.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.btnConMove.setIcon(FDCClientHelper.ICON_MOVE);
		this.menuItemConMove.setIcon(FDCClientHelper.ICON_MOVE);
		
		this.menuItemViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.btnViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
		
		actionRespite.setVisible(true);
		actionRespite.setEnabled(true);
		actionCancelRespite.setVisible(true);
		actionCancelRespite.setEnabled(true);
	}

	protected FilterInfo getTreeSelectChangeFilter() {

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		return filter;
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
//		this.setOprtState("addnew");
//		int rowIndex = this.tblMain.getSelectManager().get().getBeginRow();
//		String id = this.tblMain.getCell(rowIndex,"id").getValue().toString().trim();
//		try {
//			pubFireVOChangeListener(id);
//		} catch (Throwable e1) {
//			// TODO 自动生成 catch 块
//			e1.printStackTrace();
//		}
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		//如果编码规则中定义了合同类型,则新增是必须选择合同类型的明细节点
//		ContractClientUtils.checkCodingRuleForContract(this, getTypeSelectedTreeNode());

		DefaultKingdeeTreeNode node=getProjSelectedTreeNode();
		if (node.getUserObject() instanceof CurProjectInfo) {
			if(((CurProjectInfo)node.getUserObject()).isIsWholeAgeStage()){
				FDCMsgBox.showInfo(this, "您选择的是跨期合同新增，请注意！");
			}
		}
		super.actionAddNew_actionPerformed(e);
	}

	
	/**
	 * 作废
	 * @param ids
	 * @throws Exception
	 */
	protected void cancel(List ids) throws Exception {
		IObjectPK[] pkArray = FDCHelper.idListToPKArray(ids);
		ContractBillFactory.getRemoteInstance().cancel(pkArray);
	}

	/**
	 * 生效
	 * @param ids
	 * @throws Exception
	 */
	protected void cancelCancel(List ids) throws Exception {
		IObjectPK[] pkArray = FDCHelper.idListToPKArray(ids);

		ContractBillFactory.getRemoteInstance().cancelCancel(pkArray);
	}
	
	public void actionConMove_actionPerformed(ActionEvent e) throws Exception {
		super.actionConMove_actionPerformed(e);
		
		checkSelected();
		checkBillState(new String[]{FDCBillStateEnum.AUDITTED_VALUE}, "selectRightRowForConMove");
		
		if(ContractMoveEditUI.showMe(getSelectedKeyValue(), this)) {
			refreshList();
		}
		
	}
	
    /**
     * 
     * 描述：修改前检查
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     * @throws Exception 
     */
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
		
		//CoreBillBaseInfo billInfo = getRemoteInterface().getCoreBillBaseInfo(new ObjectUuidPK(getSelectedKeyValue(getBillListTable())));
		String selectedKeyValue = getSelectedKeyValue();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(getBillStatePropertyName());
		selector.add("splitState");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(selectedKeyValue), selector);		
	    
	    String billState = contractBillInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(billState.equals(states[i])) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
		
		//该合同已经进行了拆分，不能进行修改
		if(contractBillInfo.getSplitState()!=null && !CostSplitStateEnum.NOSPLIT.equals(contractBillInfo.getSplitState())){
			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
			SysUtil.abort();
		}
    }
    
	protected void checkBeforeCancel() throws Exception {
		super.checkBeforeCancel();
		
		String selectedKeyValue = getSelectedKeyValue();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasSettled");
		selector.add("state");
		ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectStringPK(selectedKeyValue), selector);
		if(contractBillInfo.getState()==FDCBillStateEnum.SUBMITTED || contractBillInfo.getState()==FDCBillStateEnum.AUDITTING){
			//R101224-250提交状态的可以直接终止
		}else{
			//审批状态的只有结算了才可能终止
			if(contractBillInfo.getState()==FDCBillStateEnum.AUDITTED&&!contractBillInfo.isHasSettled()) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("hasNotSettled"));
				SysUtil.abort();
			}
		}
		
	}
	

	//增加：可以对提交、审批中的合同进行终止的状态
	protected String[] getBillStateEnum() {
    	return new String[]{FDCBillStateEnum.AUDITTED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE,FDCBillStateEnum.AUDITTING_VALUE};
	}
	

	// 关联合约查看功能
	public void actionHeYue_actionPerformed(ActionEvent e) throws Exception {
		BOSUIContext uiCtx = new BOSUIContext();

		IUIWindow uiWindow = null;
		uiCtx.put(UIContext.ID, getSelectedKeyValue());
		// com.kingdee.eas.fdc.contract.client.HeYueDanEditUI
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(HeYueDanEditUI.class.getName(), uiCtx, null, OprtState.VIEW);
		uiWindow.show();
		super.actionHeYue_actionPerformed(e);
	}
	

	//设置查询器
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		
		queryExecutor.option().isAutoIgnoreZero = false;
		
		return queryExecutor;
	}
	
	//执行查询
	protected void execQuery() {

		super.execQuery();
		//格式化表格的脚
		FDCClientUtils.fmtFootNumber(tblMain, new String[]{"originalAmount","amount"});
	}

	//在填充Table前处理数据
    public void onGetRowSet(IRowSet rowSet) {

    	super.onGetRowSet(rowSet);
		try {
			rowSet.beforeFirst();

			Set contractIds = new HashSet() ;
			while (rowSet.next()) {
				String id  = rowSet.getString("id");
				contractIds.add(id);					
			}
			Map retValue = ContractBillFactory.getRemoteInstance().getOtherInfo(contractIds);
	    	contentMap = (Map)retValue.get("contentMap");
	    	attachMap = (Map) retValue.get("attachMap");
//	    	auditMap = (Map) retValue.get("auditMap");
		}catch(Exception e){
			this.handUIException(e);
		}finally{
			try {
				rowSet.beforeFirst();
			} catch (SQLException e) {
				this.handUIException(e);
			}
		}
/*    	在getOtherInfo内进行统一处理 by sxhong 2009-05-07 15:24:37
    	contentMap = (Map) ActionCache.get("ContractBillListUIHandler.contentMap");
    	attachMap = (Map) ActionCache.get("ContractBillListUIHandler.attachMap");
    	auditMap = (Map) ActionCache.get("ContractBillListUIHandler.auditMap");
    	
    	if(contentMap==null || attachMap==null){
    		contentMap = new HashMap();
    		attachMap = new HashMap();
    		
			try {
				rowSet.beforeFirst();
	
				Set contractIds = new HashSet() ;
				while (rowSet.next()) {
					String id  = rowSet.getString("id");
					contractIds.add(id);					
				}
				
				auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
				
				if(contractIds.size()>0){
					*//**
					 * 填充正文列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
					 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
					 * 如果该合同有正文附件就进行标记，否则不标记
					 *//*
					EntityViewInfo viewContent = new EntityViewInfo();
					FilterInfo filterContent = new FilterInfo();
					viewContent.getSelector().clear();
					viewContent.getSelector().add("contract.id");
					filterContent.getFilterItems().add(new FilterItemInfo("contract.id", contractIds, CompareType.INCLUDE));
					viewContent.setFilter(filterContent);
					ContractContentCollection colContent = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewContent);				
					for(int j = 0; j < colContent.size(); ++j){
						contentMap.put(colContent.get(j).getContract().getId().toString(),Boolean.TRUE);
					}						
					
					*//**
					 * 填充附件列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
					 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
					 * 如果该合同有业务相关附件就进行标记，否则不标记
					 *//*
					EntityViewInfo viewAttachment = new EntityViewInfo();
					FilterInfo filterAttachment = new FilterInfo();
					viewContent.getSelector().clear();
					viewAttachment.getSelector().add("boID");
					filterAttachment.getFilterItems().add(new FilterItemInfo("boID", contractIds, CompareType.INCLUDE));
					viewAttachment.setFilter(filterAttachment);
					BoAttchAssoCollection colAttachment = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewAttachment);
						
					for(int j = 0; j < colAttachment.size(); ++j){
						attachMap.put(colAttachment.get(j).getBoID().toString(),Boolean.TRUE);
					}			
					//具体填充在afterDataFill
				}
				rowSet.beforeFirst();
			} catch (Exception e) {
				handUIException(e);
			}
    	}*/
    	
    }
    
	/**
     * 设置是否显示合计行
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }

	protected void fetchInitData() throws Exception {
		
		super.fetchInitData();
		// 成本中心级参数
		Map paramMap = FDCUtils.getDefaultFDCParam(null, orgUnit.getId().toString());
		if(paramMap.get(FDCConstants.FDC_PARAM_UPDATECONTRACTNO)!=null){
			//与集团一样值
			isUpdateConNo = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPDATECONTRACTNO).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL)!=null){
			canUploadForAudited = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_SELECTSUPPLY)!=null){
			isSupply = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_SELECTSUPPLY).toString()).booleanValue();
		}
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}
	
	
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return true;
    }
	
	public boolean isPrepareActionSave() {
    	return true;
    }
		
    /**
     * EditUI提供的初始化handler参数方法
     */
    protected RequestContext PrepareHandlerParam(RequestContext request)
    {
        return super.PrepareHandlerParam(request);
    }
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception{
//		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
//		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if (getImportParam() != null)
        {
            task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
        }
        actionRefresh_actionPerformed(e);
		
	}
	protected ArrayList getImportParam()
    {	
		
		DatataskParameter param = new DatataskParameter();
		Hashtable hs = new Hashtable();
		param.setContextParam(hs);//
        param.solutionName = getSolutionName();      
        param.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
	protected String getSolutionName(){
    	//return "eas.fdc.contract.fdccontractbill";
		return "eas.fdc.contract.ContractBillImport";
    }
    
    protected String getDatataskAlias(){
    	//return "合同单据";
    	return "合同录入";
    } 
	public void actionEditPerson_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditPersonUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
	public void actionEditAuditDate_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditAuditDateUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
	public void actionEditContractType_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditContractTypeUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
		
	}
}
