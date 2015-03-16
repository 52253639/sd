/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.ISHERevBill;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.ISpecialDiscount;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountCollection;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SpecialDiscountListUI extends AbstractSpecialDiscountListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialDiscountListUI.class);
    protected SellProjectInfo sellProject = null;
    protected BuildingInfo building = null;
    protected BuildingUnitInfo buildUnit = null;
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    protected UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
    protected boolean isControl=SHEManageHelper.isControl(null, currentUserInfo);
    private static final String CANTEDIT = "cantEdit";
    protected Map permit=new HashMap();
    
    /**
     * output class constructor
     */
    public SpecialDiscountListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		initControl();
		
		if(SysContext.getSysContext().getCurrentUserInfo()!=null&&
				SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			KDWorkButton update=new KDWorkButton();
			update.setText("分录更新");
			update.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
	                beforeActionPerformed(e);
	                try {
	                	update_actionPerformed(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                    afterActionPerformed(e);
	                }
	            }
				private void update_actionPerformed(ActionEvent e) throws EASBizException, BOSException, SQLException {
					ISpecialDiscount rb=SpecialDiscountFactory.getRemoteInstance();
					SpecialDiscountCollection col=rb.getSpecialDiscountCollection("select *,room.* from");
					for(int i=0;i<col.size();i++){
						SpecialDiscountInfo info=col.get(i);
						if(info.getEntrys().size()!=0){
							continue;
						}
						SpecialDiscountEntryInfo entry=new SpecialDiscountEntryInfo();
						entry.setSeq(1);
						entry.setRoom(info.getRoom());
						entry.setBuildingPrice(info.getBuildingPrice());
						entry.setBuildingArea(info.getBuildingArea());
						entry.setStandardTotalAmount(info.getStandardTotalAmount());
						entry.setDiscountAmount(info.getDiscountAmount());
						entry.setDiscountAfAmount(info.getDiscountAfAmount());
						entry.setDiscountAfBPrice(info.getDiscountAfBPrice());
						entry.setDiscountPercent(info.getDiscountPercent());
						if(info.getBasePercent()!=null&&info.getBasePercent().compareTo(FDCHelper.ZERO)>0){
							entry.setBasePercent(info.getBasePercent());
						}
						entry.setBaseStandardPrice(info.getBaseStandardPrice());
						
						BigDecimal baseDiscountAmount=FDCHelper.subtract(info.getBaseStandardPrice(), info.getDiscountAfAmount());
						if(baseDiscountAmount!=null&&baseDiscountAmount.compareTo(FDCHelper.ZERO)>0){
							entry.setBaseDiscountAmount(baseDiscountAmount);
						}
						entry.setPayType(info.getPayType());
						
						entry.setSubManagerAgio(FDCHelper.subtract(info.getDiscountPercent(), info.getRoom().getManagerAgio()));
						entry.setSubSalesDirectorAgio(FDCHelper.subtract(info.getDiscountPercent(), info.getRoom().getSalesDirectorAgio()));
						entry.setSubSceneManagerAgio(FDCHelper.subtract(info.getDiscountPercent(), info.getRoom().getSceneManagerAgio()));
						entry.setParent(info);
						
						SpecialDiscountEntryFactory.getRemoteInstance().addnew(entry);
					}
				}
	        });
			this.toolBar.add(update);
		}
	}
    
    protected void initTree() throws Exception
	{
    	this.kDTree.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	this.kDTree.expandAllNodes(true, (TreeNode) this.kDTree.getModel().getRoot());
    }
    protected void initControl(){
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.kDTree.setSelectionRow(0);
    	this.actionTraceUp.setVisible(false);//上查
    	this.actionTraceDown.setVisible(false);//下查
    	this.actionCreateTo.setVisible(false);//关联生成
    	this.actionAttachment.setVisible(false);
    	
    	this.tblMain.getColumn("entrys.discountAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	this.tblMain.getColumn("entrys.discountPercent").getStyleAttributes().setNumberFormat("#,##0.0000;-#,##0.0000");
    	this.tblMain.getColumn("entrys.discountAfAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	this.tblMain.getColumn("entrys.discountAfBPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	
    	ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
		this.tblMain.getColumn("entrys.discountPercent").setRenderer(render_scale);
    }
	protected void kDTree_valueChanged(TreeSelectionEvent e) throws Exception {
		super.kDTree_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		building=null;
		buildUnit=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//项目
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}else if (node.getUserObject() instanceof BuildingInfo){ 
			// 楼栋
			if(node.getUserObject() != null){
				building=(BuildingInfo)node.getUserObject();
				sellProject = building.getSellProject();
			}
		}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
			// 单元
			if(node.getUserObject() != null){
				buildUnit=(BuildingUnitInfo)node.getUserObject();
				building=buildUnit.getBuilding();
				sellProject = buildUnit.getBuilding().getSellProject();
			}
		}
		
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
				this.actionAddNew.setEnabled(false);
			}else if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		this.refresh(null);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo){
					//项目
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllSellProjectCollection(null,sellProject),CompareType.INCLUDE));	
				}else if (node.getUserObject() instanceof BuildingInfo){ 
					// 楼栋
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
					// 单元
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
				} else if (node.getUserObject() instanceof OrgStructureInfo){
					//组织
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'",CompareType.INNER));
				}
				if (!(node.getUserObject() instanceof OrgStructureInfo)&&!isControl){
					filter.getFilterItems().add(new FilterItemInfo("creator.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
				}
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
		uiContext.put("building", building);
		uiContext.put("buildUnit", buildUnit);
	}
    
	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractSpecialDiscountListUI#auditAction_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void auditAction_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			SpecialDiscountFactory.getRemoteInstance().audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	
    public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("bizState");
    	return ((SpecialDiscountInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getBizState();
    }

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractSpecialDiscountListUI#unAuditAction_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void unAuditAction_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			SpecialDiscountFactory.getRemoteInstance().unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractSpecialDiscountListUI#actionRemove_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove("CANREMOVE");
		super.actionRemove_actionPerformed(e);
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.framework.client.ListUI#actionEdit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
	}
    protected void checkBeforeEditOrRemove(String warning) throws Exception{
    	checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum bizState = getBizState(id);
		
		if (bizState != null
				&& (bizState == FDCBillStateEnum.AUDITTING || bizState == FDCBillStateEnum.AUDITTED )) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo objectValue = new com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo();
		
        return objectValue;
    }
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
}