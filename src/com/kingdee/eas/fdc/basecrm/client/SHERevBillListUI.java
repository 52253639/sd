/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.syncUI.ThreadPool;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.Administrator;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.ISHERevBill;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.GatherTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.MakeInvoiceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveGatherEditUI;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveGatherFilterListUI;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.fdc.tenancy.client.RoomIntentFilterUI;
import com.kingdee.eas.fm.common.EnvUtils;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.uisidebar.UISideBarFactory;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SHERevBillListUI extends AbstractSHERevBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHERevBillListUI.class);
    private boolean isSheOrg = false;
    
    private CustomerQueryPanel filterUI = null;
	
	private CommonQueryDialog commonQueryDialog = null;
    
    /**
     * output class constructor
     */
    public SHERevBillListUI() throws Exception
    {
        super();
    }
 
    
    public void onLoad() throws Exception {
       	super.onLoad();
       	
       	this.actionUnAudit.setEnabled(true);
       	this.actionCreateTo.setVisible(false);
       	this.actionTraceDown.setVisible(false);
       	this.actionTraceUp.setVisible(false);
       	//this.actionWorkFlowG.setVisible(false);
       	this.actionWorkflowList.setVisible(false);
       	//this.actionAuditResult.setVisible(false);
       	this.actionMakeInvoice.setEnabled(true);
       	this.actionCreateAllUp.setEnabled(true);
       	this.acctionRecycle.setEnabled(true);
       	
       	this.kDTree.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		
//		this.kDTree.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad));
		this.kDTree.expandAllNodes(true, (TreeNode) this.kDTree.getModel().getRoot());
		this.kDTree.setSelectionNode((DefaultKingdeeTreeNode)this.kDTree.getModel().getRoot());
       	    	       	
		this.menuHelp.add(new AbstractHidedMenuItem("ctrl shift alt F12"){
			public void action_actionPerformed() {
				try {
					BOSUuid tempId = null;
					UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
					tempId = userInfo.getId();
					userInfo.setId(BOSUuid.read(Administrator.ID));		
					
					IUIFactory fy = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
					UIContext uiContext = new UIContext(this);
					IUIWindow wnd = fy.create("com.kingdee.eas.fm.common.client.FMIsqlUI",uiContext);
					wnd.show();
					
					userInfo.setId(tempId);
				} catch (UIException e) {
					SysUtil.abort(e);
				}				
			}
		});
		
		
		//非售楼组织不能操作
		isSheOrg = FDCSysContext.getInstance().checkIsSHEOrg(); 
		if(!isSheOrg) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
		
		this.tblMain.getColumn("createTime").getStyleAttributes().setHided(false);
		
		if((Set)this.getUIContext().get("IDSET")!=null){
			kDTree.setVisible(false);
			kDSplitPane1.setDividerLocation(0);
			if(isSheOrg) {
				this.actionAddNew.setEnabled(true);
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionCancel.setEnabled(true);
				this.actionCancelCancel.setEnabled(true);
			}
		}
		this.actionAddNew.setVisible(false);
		this.actionMakeInvoice.setVisible(false);
		this.acctionRecycle.setVisible(false);
		
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		
		if(SysContext.getSysContext().getCurrentUserInfo()!=null&&
				SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			KDWorkButton update=new KDWorkButton();
			update.setText("收款款项更新");
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
					ISHERevBill rb=SHERevBillFactory.getRemoteInstance();
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("entrys.moneyDefine.id");
					sel.add("entrys.moneyDefine.name");
					
					SelectorItemCollection usel=new SelectorItemCollection();
					usel.add("moneyDefine");
					
					FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
					sqlBuilder.appendSql("select entry.fparentid from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill bill on bill.fid =entry.fparentid left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId ");
					sqlBuilder.appendSql("where bill.fmoneyDefine is null and md.fname_l2 is not null and entry.fparentid is not null group by entry.fparentid ");
					IRowSet rs = sqlBuilder.executeQuery();
					while(rs.next()){
						Set moneyDefineSet=new HashSet();
						String moneyDefine="";
						SHERevBillInfo info=rb.getSHERevBillInfo(new ObjectUuidPK(rs.getString("fparentid")),sel);
						for(int i=0;i<info.getEntrys().size();i++){
							if(info.getEntrys().get(i).getMoneyDefine()!=null){
				        		if(!moneyDefineSet.contains(info.getEntrys().get(i).getMoneyDefine().getId())){
				        			moneyDefineSet.add(info.getEntrys().get(i).getMoneyDefine().getId());
				        			moneyDefine=moneyDefine+info.getEntrys().get(i).getMoneyDefine().getName()+";";
				        		}
				        	}
						}
						if(!moneyDefine.equals("")){
							info.setMoneyDefine(moneyDefine);
							rb.updatePartial(info, usel);
						}
					}
				}
	        });
			this.toolBar.add(update);
		}
    }
    
    protected void handleDynamicQuery(SelectorItemCollection sic)
    		throws Exception {
    	super.handleDynamicQuery(sic);
    }
    
    public boolean handleDynQuery(EntityViewInfo ev, boolean returnVal)
    		throws Exception {
    	return super.handleDynQuery(ev, returnVal);
    }
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
/*		CommonQueryDialog comDialog = this.getDialog();
		comDialog = this.initCommonQueryDialog();
		comDialog.init();

		KDTable kdtable = comDialog.getCommonFilterPanel().getKdtTable();
		setCommonFilterPanelFilter(kdtable);*/
    	
    	super.actionQuery_actionPerformed(e);
    }        
    
   
    
    //设置通用过滤漏斗中的过滤条件， （存在问题是，第一次打开时无法设置过滤，所以只适用于initDefaultFilter()=true的情况）
    private void setCommonFilterPanelFilter(final KDTable kdtable){		
		kdtable.addKDTEditListener(new KDTEditListener() {
			public void editValueChanged(KDTEditEvent e) {
			}
			public void editStopping(KDTEditEvent e) {
			}
			public void editStopped(KDTEditEvent e) {
			}
			public void editStarting(KDTEditEvent e) {
				int rowIndex = e.getRowIndex();
				int colIndex = e.getColIndex();
				if(colIndex==3) {
					ICell crCell =kdtable.getCell(rowIndex, 3);
					if(crCell.getEditor()==null) return;
					Component comp = crCell.getEditor().getComponent(); 
					if(comp!=null && comp instanceof KDBizPromptBox) {
						KDBizPromptBox prmtBox = (KDBizPromptBox)comp;
						String queryInfo = prmtBox.getQueryInfo();
						if(queryInfo!=null && queryInfo.endsWith("F7RoomQuery") && prmtBox.getEntityViewInfo()==null) {
							DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)kDTree.getModel().getRoot();
							Map spIdMap = FDCTreeHelper.getAllObjectIdMap(thisNode, "SellProject");
							
							EntityViewInfo viewInfo = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							viewInfo.setFilter(filter);
							filter.getFilterItems().add((new FilterItemInfo("sellProject.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER)));
							prmtBox.setEntityViewInfo(viewInfo);
						}
					}
				}
			}
			public void editStarted(KDTEditEvent e) {
			}
			public void editCanceled(KDTEditEvent e) {
			}
		});
    }
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.kDTree.getLastSelectedPathComponent();
    	if(thisNode!=null) { 
	    	if(thisNode.getUserObject() instanceof SellProjectInfo){
	    		SellProjectInfo spInfo = (SellProjectInfo)thisNode.getUserObject();
	    		uiContext.put("SellProjectInfo", spInfo);
	    	}
    	}
    	super.prepareUIContext(uiContext, e);
    }
    
    
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SHERevBillFactory.getRemoteInstance();
    }


    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.basecrm.SHERevBillInfo objectValue = new com.kingdee.eas.fdc.basecrm.SHERevBillInfo();
		
        return objectValue;
    }

    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	List idList = this.getSelectedIdValues();
    	if(idList.size()>0) {
    		for(int i=0;i<idList.size();i++) {
    			String idStr = (String)idList.get(i);
    			SHERevBillInfo revBillInfo = SHERevBillFactory.getRemoteInstance()
    						.getSHERevBillInfo("select state,revBillType,entrys.* where id = '"+idStr+"' ");
        		if(!revBillInfo.getState().equals(FDCBillStateEnum.SUBMITTED) && !revBillInfo.getState().equals(FDCBillStateEnum.AUDITTING)){
        			FDCMsgBox.showInfo("该单据非提交或审批中状态，禁止审批！");
        			return;
        		}
        		if(revBillInfo.getRevBillType().equals(RevBillTypeEnum.gathering)){
        			for(int j=0;j<revBillInfo.getEntrys().size();j++){
        				if(revBillInfo.getEntrys().get(j).getInvoiceType()==null){
        					FDCMsgBox.showInfo("该单据开票状态为空,禁止审批！");
        					return;
        				}
        			}
        		}
    			SHERevBillFactory.getRemoteInstance().audit(BOSUuid.read(idStr));
    		}
    		
    		FDCMsgBox.showInfo("审批成功！");
    		this.refreshList();
    	}
    }

    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	List idList = this.getSelectedIdValues();
    	if(idList.size()>0) {
    		for(int i=0;i<idList.size();i++) {
    			String idStr = (String)idList.get(i);
    			SHERevBillInfo revBillInfo = SHERevBillFactory.getRemoteInstance()
    						.getSHERevBillInfo("select state where id = '"+idStr+"' ");
        		if(!revBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
        			FDCMsgBox.showInfo("该单据非已审批状态，禁止反审批！");
        			return;
        		}
    			SHERevBillFactory.getRemoteInstance().unAudit(idList);
    		}
    		FDCMsgBox.showInfo("反审批成功！");
    		this.refreshList();
    	}
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAddNew_actionPerformed(e);
    	this.refreshList();
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	String idStr = this.getSelectedKeyValue();
    	if(idStr==null) return;
    	SHERevBillInfo revInfo = SHERevBillFactory.getRemoteInstance()
    						.getSHERevBillInfo("select state,relateBizType,revBillType where id = '"+idStr+"'");
    	if(revInfo!=null && revInfo.getState()!=null && !revInfo.getState().equals(FDCBillStateEnum.SAVED)
    			&& !revInfo.getState().equals(FDCBillStateEnum.SUBMITTED)){
    		FDCMsgBox.showWarning("选择单据非保存或提交状态，禁止修改！");
    		return;
    	}else{
    		if(revInfo.getRelateBizType()!=null && revInfo.getRelateBizType().equals(RelatBizType.Change)){
        		FDCMsgBox.showWarning("此单据由变更单生成，禁止修改！");
        		return;    			
    		}    		
    	}
    	if(revInfo.isIsTansCreate()) {
    		FDCMsgBox.showWarning("此单据属于变更业务财务处理，禁止修改！");
    		return;    	
		}
    	if(revInfo.getRevBillType()!=null && revInfo.getRevBillType().equals(RevBillTypeEnum.gathering)) {
	    	boolean isQuitOrTrans = SHERevBillEntryFactory.getRemoteInstance().exists(" where hasRefundmentAmount > 0 and hasTransferAmount >0 and parent.id = '"+idStr+"' ");
	    	if(isQuitOrTrans){
	    		FDCMsgBox.showWarning("此收款单据已发生退款和转款，禁止修改！");
	    		return;    		
	    	}
    	}
    	
    	super.actionEdit_actionPerformed(e);
    	this.refreshList();
    }
    
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	String idStr = this.getSelectedKeyValue();
    	if(idStr==null) return;
    	SHERevBillInfo revInfo = SHERevBillFactory.getRemoteInstance().getSHERevBillInfo("select revAmount,state,relateBizType,isTansCreate where id = '"+idStr+"'");
    	if(revInfo.getRelateBizType()!=null ){
    		if(revInfo.getRelateBizType().equals(RelatBizType.Change)) {
	    		FDCMsgBox.showWarning("此单据由变更单生成，禁止删除！");
	    		return;    	
    		}
    		if(revInfo.isIsTansCreate()) {
	    		FDCMsgBox.showWarning("此单据属于变更业务财务处理，禁止删除！");
	    		return;    	
    		}
    		if(revInfo.getState()!=null && !revInfo.getState().equals(FDCBillStateEnum.SUBMITTED)
    				&&!revInfo.getState().equals(FDCBillStateEnum.SAVED)) {
	    		FDCMsgBox.showWarning("此单据非保存或提交状态，禁止删除！");
	    		return;    	
    		} 
    	}
    	if(revInfo.isIsGathered()&&revInfo.getRevAmount()!=null&&revInfo.getRevAmount().compareTo(FDCHelper.ZERO)!=0){
    		FDCMsgBox.showWarning("此单据已经生产出纳汇总单，禁止删除！");
    		return;    	
    	}
    	super.actionRemove_actionPerformed(e);
    	this.refreshList();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
    		EntityViewInfo viewInfo) {
    	FilterInfo filter = new FilterInfo();
    	DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)this.kDTree.getLastSelectedPathComponent();
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
    	if(thisNode!=null) {  
	    	if(thisNode.getUserObject() instanceof SellProjectInfo){
	    		if(isSheOrg && thisNode.isLeaf()){
		    		this.actionAddNew.setEnabled(true);
		    		this.actionEdit.setEnabled(true);
		    		this.actionRemove.setEnabled(true);
	    		}
	    		SellProjectInfo spInfo = (SellProjectInfo)thisNode.getUserObject();
	    		filter.getFilterItems().add(new FilterItemInfo("sellProject.longNumber",spInfo.getLongNumber() + "%",CompareType.LIKE));
	    	}else{
	    		Map spIdMap = FDCTreeHelper.getAllObjectIdMap(thisNode, "SellProject");
	    		if(spIdMap.size()>0)
	    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
	    		else
	    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null));
	    	}
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null));
    	}
    	if((Set)this.getUIContext().get("IDSET")!=null){
    		Set idSet = (Set)this.getUIContext().get("IDSET");
    		if(idSet.size()>0)
    			filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
    	}
		viewInfo = (EntityViewInfo)this.mainQuery.clone();
		if(viewInfo.getFilter()==null)
			viewInfo.setFilter(filter);
		else{
			try {
				viewInfo.getFilter().mergeFilter(filter,"AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		//把按id排序去掉    想让它按序号排序，居然没有效果， 暂时放下
/*		SorterItemInfo idSorter = new SorterItemInfo("id");
		viewInfo.getSorter().remove(idSorter);
		
		IQueryExecutor exec = super.getQueryExecutor(queryPK, viewInfo);
		//exec.option().isAutoIgnoreZero = false;
*/		
		
    	return super.getQueryExecutor(queryPK, viewInfo);
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true; 
    }
    
    
    protected FilterInfo getDefaultFilterForQuery() {
    	//FrameWorkUtils.getF7FilterInfoByAuthorizedOrg(com.kingdee.eas.basedata.org.OrgType.getEnum("Sale"),"saleOrgUnit.id",true);
    	//基类的这个方法比较牛
    	return null;
    }
 
    
    protected void kDTree_valueChanged(TreeSelectionEvent e) throws Exception {
    	this.execQuery();
    }
    
    
    
    public void actionMakeInvoice_actionPerformed(ActionEvent e)
    		throws Exception {
    	ArrayList idList = this.getSelectedFieldValues("id");
    	if(idList==null || idList.size()==0)	return;
    	
    	String idsStr = "";
    	for (int i = 0; i < idList.size(); i++) {
    		idsStr += ",'"+idList.get(i)+"'";
		}
    	SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
    							.getSHERevBillEntryCollection("select *,moneyDefine.name,parent.*,parent.room.name,parent.room.number " +
    									"where parent.id in ("+idsStr.substring(1)+") ");
    	
    	SHERevCustEntryCollection custEntryColl = SHERevCustEntryFactory.getRemoteInstance()
    							.getSHERevCustEntryCollection("select sheCustomer.* where head.id in ("+idsStr.substring(1)+")");
    	List custList = new ArrayList();
    	for (int i = 0; i < custEntryColl.size(); i++) {
    		custList.add(custEntryColl.get(i).getSheCustomer());
		}
    	
    	UIContext uiContext = new UIContext(this);   	
    	uiContext.put("revBillColl", revEntryColl);
    	uiContext.put("customer", custList.toArray());
    	if(revEntryColl.size()>0){
    		RoomInfo tempRoom = null;
    		for (int i = 0; i < revEntryColl.size(); i++) {
    			RoomInfo currRoom = revEntryColl.get(i).getParent().getRoom(); 
    			if(tempRoom==null)
    				 tempRoom = currRoom;
    			else if(!tempRoom.getId().equals(currRoom.getId())){
    				tempRoom = null;
    				break;
    			}
			}
    		if(tempRoom!=null)	uiContext.put("Room", tempRoom);    	
    	}
    		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
							.create(MakeInvoiceEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    public void actionRecycle_actionPerformed(ActionEvent e) throws Exception {
    	ArrayList idList = this.getSelectedIdValues();
    	if(idList==null || idList.size()==0)	return;
    	
    	String idsStr = "";
    	for (int i = 0; i < idList.size(); i++) {
			idsStr += ",'"+idList.get(i)+"'";
		}
    	boolean exitNumBoolean = SHERevBillEntryFactory.getRemoteInstance().exists("where invoiceNumber != null and parent.id in ("+idsStr.substring(1)+") ");
    	if(exitNumBoolean) {
			ChequeDetailEntryFactory.getRemoteInstance().clearInvoice(idList.toArray(), true);
			FDCMsgBox.showInfo("清除票据成功 ！");
			this.refreshList();
    	}else{
			FDCMsgBox.showInfo("还未开票，不用清除 ！");
			this.refreshList();    		
    	}
    }
    
    public void actionCreateAllUp_actionPerformed(ActionEvent e)
    		throws Exception {
    	UIContext uiContext = new UIContext(this);
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.kDTree.getLastSelectedPathComponent();
//		
		if(node.getUserObject() instanceof SellProjectInfo){
//			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();
//			if(sellProjectInfo!=null){
//				uiContext.put("sellProject", sellProjectInfo);
//				
//		    	IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
//				IUIWindow curDialog = uiFactory.create(ReceiveGatherFilterListUI.class.getName(),uiContext, null);
//				curDialog.show();
//			}
		}else{
			FDCMsgBox.showWarning(this,"必须选择项目节点 ！");
			return;
		}
    	SHEManageHelper.checkSelected(this,this.tblMain);
    	ArrayList idList = this.getSelectedFieldValues("id");
    	if(idList==null || idList.size()==0)	return;
    	
    	String idsStr = "";
    	for (int i = 0; i < idList.size(); i++) {
    		idsStr += ",'"+idList.get(i)+"'";
		}
    	
    	Set revTypeSet = new HashSet();
    	Set idSet = new HashSet();
    	Set sellproject = new HashSet();
    	Set accountBank=new HashSet();
    	Set revAccount=new HashSet();
    	Set settlement=new HashSet();
    	SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
    							.getSHERevBillEntryCollection("select parent.settlementType,parent.isTansCreate,parent.isGathered,parent.accountBank.*,parent.revAccount.*,parent.state,parent.revBillType,parent.sellProject.* " +
    									"where parent.id in ("+idsStr.substring(1)+") ");
    	
    	for(int i=0;i<revEntryColl.size();i++){
    		idSet.add(revEntryColl.get(i).getId().toString());
    		sellproject.add(revEntryColl.get(i).getParent().getSellProject().getId());
    		revTypeSet.add(revEntryColl.get(i).getParent().getRevBillType());
    		if(revEntryColl.get(i).getParent().getAccountBank()!=null){
    			accountBank.add(revEntryColl.get(i).getParent().getAccountBank().getId());
    		}else{
    			accountBank.add("null");
    		}
    		if(revEntryColl.get(i).getParent().getRevAccount()!=null){
    			revAccount.add(revEntryColl.get(i).getParent().getRevAccount().getId());
    		}else{
    			revAccount.add("null");
    		}
    		if(revEntryColl.get(i).getParent().getSettlementType()!=null){
    			settlement.add(revEntryColl.get(i).getParent().getSettlementType().getId());
    		}else{
    			settlement.add("null");
    		}
    		
    		if(revEntryColl.get(i).getParent().isIsGathered()){
    			MsgBox.showWarning(this,"不能汇总已生成出纳汇总单的收款单！");
    			this.abort();
    		}
    		if(revEntryColl.get(i).getParent().isIsTansCreate()){
    			MsgBox.showWarning(this,"不能汇总变更业务财务处理的收款单！");
    			this.abort();
    		}
    		if(!revEntryColl.get(i).getParent().getState().equals(FDCBillStateEnum.AUDITTED)){
    			MsgBox.showWarning(this,"请选择单据状态为已审批的收款单进行汇总！");
    			this.abort();
    		}
    	}
    	if(revTypeSet.size()>1)
		{
			MsgBox.showWarning(this,"不能同时汇总收款单据类型不同的收款单！");
			this.abort();
		}
    	if(sellproject.size()>1)
		{
			MsgBox.showWarning(this,"不能同时汇总不同项目的收款单！");
			this.abort();
		}
    	if(accountBank.size()>1){
    		MsgBox.showWarning(this,"不能同时汇总不同收款账户的收款单！");
			this.abort();
    	}
    	if(accountBank.size()==1&&accountBank.contains("null")){
    		if(revAccount.size()>1){
    			MsgBox.showWarning(this,"收款账户为空时，不能同时汇总不同收款科目的收款单！");
    			this.abort();
    		}
    	}
    	SHERevBillInfo sheRevInfo = revEntryColl.get(0).getParent();
		RevBillTypeEnum revType = sheRevInfo.getRevBillType();
    	if(RevBillTypeEnum.gathering.equals(revType))
		{
			uiContext.put("gatherType", GatherTypeEnum.ReceiveGather);
			uiContext.put("revBillType",RevBillTypeEnum.gathering);
		}else if(RevBillTypeEnum.refundment.equals(revType))
		{
			uiContext.put("gatherType",GatherTypeEnum.RefumentGather);
			uiContext.put("revBillType", RevBillTypeEnum.refundment);
		}else if(RevBillTypeEnum.transfer.equals(revType))
		{
			uiContext.put("gatherType", GatherTypeEnum.BillGather);
			uiContext.put("revBillType",RevBillTypeEnum.transfer );
		}
    	uiContext.put("revEntryIDSet", idSet);
		uiContext.put("sellProject", revEntryColl.get(0).getParent().getSellProject());
		
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow curDialog = uiFactory.create(ReceiveGatherEditUI.class.getName(),uiContext,null, OprtState.ADDNEW);
		curDialog.show();
		
		this.refresh(null);
    }    
    protected String getEditUIName()
    {
        return NewSHERevBillEditUI.class.getName();
    }
    protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
    private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SHERevBillFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected boolean initDefaultFilter() {
		return true;
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowFilter(true);
		return commonQueryDialog;
	}
	 protected void afterTableFillData(KDTDataRequestEvent e) {
		 super.afterTableFillData(e);
		 for(int i = 0 ; i < tblMain.getRowCount();i++){
		    IRow row = tblMain.getRow(i);
		    if(row.getCell("isTansCreate").getValue()!=null&&((Boolean)row.getCell("isTansCreate").getValue()).booleanValue()){
		    	row.getCell("isGathered").getStyleAttributes().setBackground(Color.GRAY);
		    }
	   	 }
	 }
	 public void actionImport_actionPerformed(ActionEvent e) throws Exception {
	       DatataskCaller task = new DatataskCaller();
	       task.setParentComponent(this);
	       DatataskParameter param = new DatataskParameter();
	       param.solutionName = "eas.fdc.sellhouse.sheRevBill";
	       ArrayList paramList = new ArrayList();
	       paramList.add(param);
	       task.invoke(paramList, DatataskMode.UPDATE, true);
	       this.refresh(null);
	 }
	 public void actionMultiSubmit_actionPerformed(ActionEvent e) throws Exception {
		   checkSelected();
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
	        	   ArrayList id = getSelectedIdValues();
	        	   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   NewSHERevBillEditUI ui=(NewSHERevBillEditUI) UIFactoryHelper.initUIObject(NewSHERevBillEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   FDCBillStateEnum state = ((SHERevBillInfo)ui.getEditData()).getState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(FDCBillStateEnum.SUBMITTED.equals(state)||FDCBillStateEnum.SAVED.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
        			   ui.verifyInput(null);
        			   ui.runSubmit();
        			   ui.destroyWindow();
        		   }
	               return new Boolean(true);
	           }
	           public void afterExec(Object result)
	               throws Exception
	           {
	        	   FDCMsgBox.showWarning("操作成功！");
	           }
	       }
	);
	       dialog.show();
		   this.refresh(null);
	   }
}