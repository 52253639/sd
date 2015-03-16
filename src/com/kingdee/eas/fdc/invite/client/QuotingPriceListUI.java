/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.JFileChooser;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.syncUI.ThreadPool;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.base.multiapprove.IMultiApprove;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 投标报价评审 序时薄
 */
public class QuotingPriceListUI extends AbstractQuotingPriceListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(QuotingPriceListUI.class);
    private CommonQueryDialog dialog ;
    private boolean isHandlerTable=false;
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectedKeyValue(this.tblMain);
	     super.actionQuery_actionPerformed(e);
	     if (id != null) {
				int i = 0;
				for (i = 0; i < tblMain.getRowCount(); i++) {
					IRow row = tblMain.getRow(i);
					if (row.getCell("id").getValue().equals(id)) {
						break;
					}
				}
				this.tblMain.getSelectManager().select(i, 0);
				if(tblMain.getRowCount()>0)
				displayBillByInvite(genBillQueryView(id));
				return;
			}
	  
	}
	
	public void actionInviteExecuteInfo_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInviteExecuteInfo_actionPerformed(e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex == -1){
			FDCMsgBox.showError("请先选择招标清单！");
			abort();
		}
		String  id = null;
		if(tblMain.getCell(rowIndex,"id").getValue() != null){
			id = tblMain.getCell(rowIndex,"id").getValue().toString();
		}
		if(id == null){
			return ;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select finviteProjectId from t_inv_newlisting where fid = ?");
		builder.addParam(id);
		RowSet rs = builder.executeQuery();
		String prjId = null;
		while(rs.next()){
			prjId = rs.getString("finviteProjectId");
		}
		
		if(prjId == null){
			return ;
		}
		UIContext uiContext = new UIContext(this);

		uiContext.put(UIContext.ID, null);
		uiContext.put("INVITE_PROJECT", prjId);
		uiContext.put("LIST_UI",
				"com.kingdee.eas.fdc.invite.client.QuotingPriceListUI");
		uiContext.put("INVITEPROJECT_NAME", null);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(InviteAllInformationUI.class.getName(), uiContext,
						null, OprtState.ADDNEW);
		uiWindow.show();
		
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
	
		
		FilterInfo filter = null;
		try {
			filter = this.getMainFilter();
			if(this.getDialog()!=null){
				FilterInfo commFilter = this.getDialog().getCommonFilter();
				if(filter!=null&&commFilter!=null)
					filter.mergeFilter(commFilter, "and");
			}else{
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution!=null&&querySolution.getEntityViewInfo()!=null)
		        {
		            EntityViewInfo ev=Util.getInnerFilterInfo(querySolution);
		            if(ev.getFilter()!=null){
		            	filter.mergeFilter(ev.getFilter(), "and");
		            }
		        }
			}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		viewInfo.setFilter(filter);
		
		
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}


	private String UI_CLASS_PARAM = null;
	/**
	 * output class constructor
	 */
	public QuotingPriceListUI() throws Exception {
		super();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		if(UI_CLASS_PARAM == null)
		{
			IObjectPK orgPK = getOrgPK(this.actionEdit);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
							"inv_quotingPrice_metaPrice");
		}
		
		String id = getSelectedKeyValue(this.tblMain);
		if(id != null)
		{
			//检测招标清单的招标立项已经生成存在评标结果报审，新增投标人
			IObjectPK pk = new ObjectUuidPK(id);
			NewListingInfo listInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(pk);
			
			FilterInfo filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("inviteProject", listInfo.getInviteProject().getId().toString()));
			
			if(AppraiseResultFactory.getRemoteInstance().exists(filter))
			{
				MsgBox.showWarning("所选招标清单所关联的招标立项已经存在评标结果报审，不能执行此操作。");
				return;
			}
		}
		
		editPrice(QuotingPriceTypeEnum.original);
	}
	//导入修正价
	public void actionImportModifyPrice_actionPerformed(ActionEvent e) throws Exception
    {	
		if(UI_CLASS_PARAM == null)
		{
			IObjectPK orgPK = getOrgPK(this.actionImportModifyPrice);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
							"inv_quotingPrice_editPrice");
		}
		
		String id = getSelectedKeyValue(this.tblMain);
		if(id != null)
		{
			//检测招标清单的招标立项已经生成存在评标结果报审，新增投标人
			IObjectPK pk = new ObjectUuidPK(id);
			NewListingInfo listInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(pk);
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject", listInfo.getInviteProject().getId().toString()));
			
			if(AppraiseResultFactory.getRemoteInstance().exists(filter))
			{
				MsgBox.showWarning("所选招标清单所关联的招标立项已经存在评标结果报审，不能执行此操作。");
				return;
			}
		}
		
		editPrice(QuotingPriceTypeEnum.modify);
    }
	
	public void editPrice(QuotingPriceTypeEnum type) throws Exception{
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()),
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit()
						.getId().toString()), "inv_quotingPrice_editPrice");
		String id = getSelectedKeyValue();
		if (id == null) {
			MsgBox.showWarning("请先选择投标人!");
			return;
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put("inviteListingId", this.getInviteListingId());
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		NewQuotingPriceInfo info = NewQuotingPriceFactory.getRemoteInstance()
				.getNewQuotingPriceInfo(new ObjectUuidPK(id));
		if (info.getStatus().equals(QuotingPriceStatusEnum.Evaluated)
				|| info.getStatus().equals(QuotingPriceStatusEnum.ImportBase)) {
			MsgBox.showWarning("已参与评标的报价不能修改!");
			return;
		}
		uiContext.put("quotingPriceInfo", info);
		uiContext.put("quotingPriceType", type);
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
				.create(getEditUIName(), uiContext, null, "EDIT1");
		uiWindow.show();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectedKeyValue(this.tblMain);
		fillTable();
		if (id != null) {
			int i = 0;
			for (i = 0; i < tblMain.getRowCount(); i++) {
				IRow row = tblMain.getRow(i);
				if (row.getCell("id").getValue().equals(id)) {
					break;
				}
			}
			this.tblMain.getSelectManager().select(i, 0);
			displayBillByInvite(genBillQueryView(id));
			return;
		}
	}

	public void refreshQuotingPriceListByInviteListingRow() {
		try {
			String id = getSelectedKeyValue(this.tblMain);
			displayBillByInvite(genBillQueryView(id));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		if(UI_CLASS_PARAM == null)
		{
			IObjectPK orgPK = getOrgPK(this.actionRemove);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
							"inv_quotingPrice_delete");
		}
		
		String idTmp = getSelectedKeyValue(this.tblMain);
		if(idTmp != null)
		{
			//检测招标清单的招标立项已经生成存在评标结果报审，新增投标人
			IObjectPK pk = new ObjectUuidPK(idTmp);
			NewListingInfo listInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(pk);
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject", listInfo.getInviteProject().getId().toString()));
			
			if(AppraiseResultFactory.getRemoteInstance().exists(filter))
			{
				MsgBox.showWarning("所选招标清单所关联的招标立项已经存在评标结果报审，不能执行此操作。");
				return;
			}
		}
		
		int[] rowIndexArray = KDTableUtil.getSelectedRows(this.tblDetail);
		if (rowIndexArray.length == 0) {
			MsgBox.showWarning("请先选择投标人!");
			return;
		} else {
			if (confirmRemove()) {
				ObjectUuidPK[] uuidPK = new ObjectUuidPK[rowIndexArray.length];
				Set quotingIdSet = new HashSet();
				for (int i = 0; i < rowIndexArray.length; i++) {
					IRow row = this.tblDetail.getRow(rowIndexArray[i]);
					String quotingId = row.getCell("id").getValue().toString();
					uuidPK[i] = new ObjectUuidPK(quotingId);
					quotingIdSet.add(quotingId);
					clearSupplierNewsListingEntries(quotingId, ((NewQuotingPriceInfo) row.getUserObject()).getSupplier().getId().toString());
				}
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("quotingContent", quotingIdSet, CompareType.INCLUDE));
				if (RefPriceFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showWarning("投标人报价已经导入到数据库,不能删除!");
					return;
				}
				NewQuotingPriceFactory.getRemoteInstance().delete(uuidPK);
				
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("quotingPrice", quotingIdSet, CompareType.INCLUDE));
				NewListingValueFactory.getRemoteInstance().delete(filter);
				String id = getSelectedKeyValue(this.tblMain);
				displayBillByInvite(genBillQueryView(id));
			}
		}
		
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(UI_CLASS_PARAM == null)
		{
			IObjectPK orgPK = getOrgPK(this.actionAddNew);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
					"inv_quotingPrice_addNew");
		}
		
		String id = getSelectedKeyValue(this.tblMain);
		if (id == null) {
			MsgBox.showWarning("请先选择招标清单！");
			return;
		}
		
		if(id != null)
		{
			//检测招标清单的招标立项已经生成存在评标结果报审，新增投标人
			IObjectPK pk = new ObjectUuidPK(id);
			NewListingInfo listInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(pk);
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject", listInfo.getInviteProject().getId().toString()));
			
			if(AppraiseResultFactory.getRemoteInstance().exists(filter))
			{
				MsgBox.showWarning("所选招标清单所关联的招标立项已经存在评标结果报审，不能执行此操作。");
				return;
			}
		}
		super.actionAddNew_actionPerformed(e);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("inviteListingId", this.getInviteListingId());
		uiContext.put("inviteListingName", this.getSelectedNameValue(this.tblMain));
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NewQuotingPriceFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		
		this.dialog = getDialog();
		this.btnQuery.setVisible(true);
		
		this.tblMain.addKDTDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				String state = (String) getUIContext().get("UIClassParam");
				/***
				 * 报价评审序时薄和报价导入序时薄是一个界面
				 * 所以要加入判断
				 * 如果是报价导入的界面，则隐藏报价状态
				 */
				if (!"ASSESS".equals(state)){
					tblMain.getColumn("inviteAuditState").getStyleAttributes().setHided(true);
				}
			}
			
		});
		
		
		String state = (String) this.getUIContext().get("UIClassParam");
		if ("ASSESS".equals(state)) {
			actionAnalyseListUI_actionPerformed(null);
		} else {
			actionImportListUI_actionPerformed(null);
		}
		// 以上代码是为“权限项”而加

		super.onLoad();

		// 在ui设计页面隐藏不掉的按钮，菜单
		this.MenuItemAttachment.setVisible(false);
		this.btnAttachment.setVisible(false);
//		this.btnAuditResult.setVisible(false);

		if ("ASSESS".equals(state)) {
			this.btnChooseSupplier.setVisible(false);
			this.btnImportModifyPrice.setVisible(false);
			this.btnImportOriginalPrice.setVisible(false);
			this.actionExportPrice.setVisible(false);
			this.actionAddNew.setVisible(false);
			this.actionRemove.setVisible(false);
			this.actionEdit.setVisible(false);
			this.menuEdit.setVisible(false);
			this.setUITitle("报价评审序时簿");
			initReportExportBtn();
		} 
		else if("EXPORT".equals(state)){
			/***
			 * 报价清单导出
			 */
			this.actionAddNew.setVisible(false);
			this.actionRemove.setVisible(false);
			this.actionEdit.setVisible(false);
			this.actionImportModifyPrice.setVisible(false);
			this.menuEdit.setVisible(false);
			this.setUITitle("招标清单导出");
			this.actionAssess.setVisible(false);
			this.actionSelectBidder.setVisible(false);
			this.actionCompositor.setVisible(false);
			this.actionKeyItemSet.setVisible(false);
			this.actionQuotingPriceGather.setVisible(false);
			this.actionUnBid.setVisible(false);
			this.setActionAuditResultState(false);
			this.menuBiz.setVisible(false);
			this.menuExport.setVisible(false);
			this.actionAuditResult.setVisible(false);
			this.actionExportPrice.setVisible(true);
			this.actionExportPrice.setEnabled(true);
			
			this.splitBill.add(this.contMainBill, "bottom");
			this.splitBill.setDividerLocation(1.0);
			this.splitBill.setDividerSize(0);
			
			this.actionView.setVisible(false);
			this.menuView.setVisible(false);
			this.menuViewModify.setVisible(false);
						
		}
		else {
			this.actionAssess.setVisible(false);
			this.actionSelectBidder.setVisible(false);
			this.actionCompositor.setVisible(false);
			this.actionKeyItemSet.setVisible(false);
			this.actionQuotingPriceGather.setVisible(false);
			this.menuBiz.setVisible(false);
			this.menuExport.setVisible(false);
			this.actionExportPrice.setVisible(false);
			this.actionUnBid.setVisible(false);
			this.actionAuditResult.setVisible(false);
			this.actionInviteExecuteInfo.setVisible(false);

		}

		KDTable table = getBillListTable();
		table.setEditable(false);
		table.getStyleAttributes().setLocked(true);

		KDMenuItem item = null;

		this.kDMenu1.setIcon(EASResource.getIcon("imgTbtn_view"));

		item = new KDMenuItem();
		item.setAction(this.actionView);
		item.setText("修正报价");
		item.setAccelerator(null);
		item.setUserObject(QuotingPriceTypeEnum.modify);
		this.menuViewModify.setUserObject(QuotingPriceTypeEnum.modify);
		this.menuViewModify.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_view"));
		this.btnView.addAssistMenuItem(item);

		item = new KDMenuItem();
		item.setAction(this.actionView);
		item.setText("原始报价");
		item.setAccelerator(null);
		item.setUserObject(QuotingPriceTypeEnum.original);
		this.menuItemView.setUserObject(QuotingPriceTypeEnum.original);
		this.menuItemView.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_view"));
		this.btnView.addAssistMenuItem(item);

		this.kDMenu2.setIcon(EASResource.getIcon("imgTbtn_edit"));

		item = new KDMenuItem();
		item.setAction(this.actionEdit);
		item.setText(this.btnImportModifyPrice.getText());
		item.setAccelerator(null);
		item.setUserObject(QuotingPriceTypeEnum.modify);
		this.menuEditModify.setUserObject(QuotingPriceTypeEnum.modify);
		this.menuEditModify.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.menuEdit.add(item);

		item = new KDMenuItem();
		item.setAction(this.actionEdit);
		item.setText(this.btnImportOriginalPrice.getText());
		item.setAccelerator(null);
		item.setUserObject(QuotingPriceTypeEnum.original);
		this.menuItemEdit.setUserObject(QuotingPriceTypeEnum.original);
		this.menuItemEdit.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.menuEdit.add(item);
		this.kDMenu2.setVisible(false);
		this.btnEdit.setVisible(false);

		this.tblMain.getColumn("createTime").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd");
		this.actionKeyItemSet.setVisible(false);
		this.btnKeyItemSet.setVisible(false);
		
		this.menuItemSwitchView.setVisible(false);
		
		this.actionUnBid.setVisible(false);
		
	}

	public void initReportExportBtn() {
		KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(this.actionQuotingPriceGather);
		menuItem1.setText("经济标评审汇总表");
		menuItem1.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnReportExport.addAssistMenuItem(menuItem1);

		KDMenuItem menuItem4 = new KDMenuItem();
		menuItem4.setAction(this.actionKeyItemAnalyse);
		menuItem4.setText("关键子目分析表");
		menuItem4.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnReportExport.addAssistMenuItem(menuItem4);

		KDMenuItem menuItem2 = new KDMenuItem();
		menuItem2.setAction(this.actionPriceModify);
		menuItem2.setText("综合单价修正明细表");
		menuItem2.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnReportExport.addAssistMenuItem(menuItem2);

		KDMenuItem menuItem3 = new KDMenuItem();
		menuItem3.setAction(this.actionOverRangeQuotingPrice);
		menuItem3.setText("超出范围综合单价明细表");
		menuItem3.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnReportExport.addAssistMenuItem(menuItem3);

		KDMenuItem menuItem5 = new KDMenuItem();
		menuItem5.setAction(this.actionQuotingPriceSum);
		menuItem5.setText("报价明细汇总表");
		menuItem5.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnReportExport.addAssistMenuItem(menuItem5);
		this.actionQuotingPriceSum.setEnabled(true);
		menuItem5.setEnabled(true);
	}

	// 经济标评审汇总表
	public void actionQuotingPriceGather_actionPerformed(ActionEvent e)
			throws Exception {
		if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			IObjectPK orgPK = getOrgPK(this.actionQuotingPriceGather);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
			"inv_quotingPriceAnalyse_reportExport");
		}

		try {
			String inviteListingId = this.getInviteListingId();
			if (inviteListingId == null) {
				MsgBox.showWarning("请选择招标清单!");
				return;
			}
			UIContext uiContext = new UIContext(ui);
			uiContext.put("inviteListingId", inviteListingId);
			IUIWindow window;
			window = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					QuotingPriceGatherUI.class.getName(), uiContext);
			window.show();
		} catch (UIException ex) {
			ex.printStackTrace();
		}
	}

	// 超出范围综合单价明细表
	public void actionOverRangeQuotingPrice_actionPerformed(ActionEvent e)
			throws Exception {

		if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			IObjectPK orgPK = getOrgPK(this.actionOverRangeQuotingPrice);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
			"inv_quotingPriceAnalyse_reportExport");
		}
		
		try {
			String id = this.getSelectedKeyValue();
			if (id == null) {
				MsgBox.showWarning("请选择投标人!");
				return;
			}
			UIContext uiContext = new UIContext(ui);
			uiContext.put("quotingPriceContentId", id);
			uiContext.put("inviteListingId", this.getInviteListingId());
			IUIWindow window;
			window = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					QuotingPriceOverRangeUI.class.getName(), uiContext);

			window.show();
		} catch (UIException ex) {
			ex.printStackTrace();
		}
	}

	// 关键子目分析表
	public void actionKeyItemAnalyse_actionPerformed(ActionEvent e)
			throws Exception {
		
		if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			IObjectPK orgPK = getOrgPK(this.actionKeyItemAnalyse);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
			"inv_quotingPriceAnalyse_reportExport");
		}
		
		try {
			String inviteListingId = this.getInviteListingId();
			if (inviteListingId == null) {
				MsgBox.showWarning("请选择招标清单!");
				return;
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("inviteListingId", inviteListingId);
			IUIWindow window;
			window = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					KeyItemAnalyseUI.class.getName(), uiContext);
			window.show();
		} catch (UIException ex) {
			ex.printStackTrace();
		}
	}

	// 综合单价修正明细表

	public void actionPriceModify_actionPerformed(ActionEvent e)
			throws Exception {
		if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			IObjectPK orgPK = getOrgPK(this.actionPriceModify);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
			"inv_quotingPriceAnalyse_reportExport");
		}
		
		try {
			String id = this.getSelectedKeyValue();
			if (id == null) {
				MsgBox.showWarning("请选择投标人!");
				return;
			}
			UIContext uiContext = new UIContext(ui);
			uiContext.put("quotingPriceContentId", id);
			uiContext.put("inviteListingId", this.getInviteListingId());
			IUIWindow window;
			window = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
					QuotingPriceModifyAnalyseUI.class.getName(), uiContext);

			window.show();
		} catch (UIException ex) {
			ex.printStackTrace();
		}
	}

	public final static String resourcePath = "com.kingdee.eas.fdc.invite.FDCInviteResource";

	protected void displayBillByInvite(EntityViewInfo view) throws BOSException {
		KDTable table = getBillListTable();
		table.removeRows();
		table.checkParsed();
		String state = (String) this.getUIContext().get("UIClassParam");
		if ("EXPORT".equals(state)) {
			table.getColumn("hasEffected").getStyleAttributes().setHided(true);
		}
		NewQuotingPriceCollection didderCollection = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
		for (Iterator iter = didderCollection.iterator(); iter.hasNext();) {
			NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) iter.next();
			IRow row = table.addRow();
			row.getCell("id").setValue(quoting.getId().toString());
			row.getCell("supplier.number").setValue(quoting.getSupplier().getNumber());
			row.getCell("supplier.name").setValue(quoting.getSupplier().getName());
			if (quoting.getListing().getCurProject() != null)
				row.getCell("inviteListing.name").setValue(quoting.getListing().getCurProject().getName());
			row.getCell("status").setValue(quoting.getStatus());
			ICell priceCell = row.getCell("totoalPrice");
			priceCell.setValue(quoting.getTotoalPrice() + "");
			priceCell.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			priceCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			row.getCell("creator.name").setValue(quoting.getCreator().getName());
			row.getCell("creator.createTime").setValue(quoting.getCreateTime());
			row.getCell("creator.createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
			row.getCell("hasEffected").setValue(Boolean.valueOf(quoting.isHasEffected()));
			row.setUserObject(quoting);
		}
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		if (didderCollection.size() > 0) {
			table.getSelectManager().select(0, 0);
		}
	}
	public String getKeyFieldNameForWF()
    {
        return "appraise.id";
    }
	
	public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	String fieldName = getQueryFieldNameBindingWf();
    	if(getSelectedFieldValues(fieldName).size()>0){
    		super.actionViewDoProccess_actionPerformed(e);
    	} 
    }
	

	protected void refresh(ActionEvent e) throws Exception {
		fillTable();
	}

	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection collection = new SelectorItemCollection();
		collection.add(new SelectorItemInfo("*"));
		collection.add(new SelectorItemInfo("creator.*"));
		collection.add(new SelectorItemInfo("supplier.*"));
		collection.add(new SelectorItemInfo("listing.*"));
		collection.add(new SelectorItemInfo("listing.curProject.*"));
		collection.add(new SelectorItemInfo("inviteAuditState"));
		return collection;
	}

	protected String getEditUIName() {
		return NewQuotingPriceEditUI.class.getName();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		if(!(e.getSource() instanceof KDMenuItem))
			return;
		if(UI_CLASS_PARAM == null)
		{
			QuotingPriceTypeEnum type = (QuotingPriceTypeEnum) ((KDMenuItem) e.getSource()).getUserObject();
			if(type.equals(QuotingPriceTypeEnum.original))
			{
				IObjectPK orgPK = getOrgPK(this.actionView);
				PermissionFactory.getRemoteInstance().checkFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPrice_viewMeta");
			}
			else if(type.equals(QuotingPriceTypeEnum.modify))
			{
				IObjectPK orgPK = getOrgPK(this.actionView);
				PermissionFactory.getRemoteInstance().checkFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPrice_viewEdit");
			}
		}
		else if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			QuotingPriceTypeEnum type = (QuotingPriceTypeEnum) ((KDMenuItem) e.getSource()).getUserObject();
			if(type.equals(QuotingPriceTypeEnum.original))
			{
				IObjectPK orgPK = getOrgPK(this.actionView);
				PermissionFactory.getRemoteInstance().checkFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_viewMeta");
			}
			else if(type.equals(QuotingPriceTypeEnum.modify))
			{
				IObjectPK orgPK = getOrgPK(this.actionView);
				PermissionFactory.getRemoteInstance().checkFunctionPermission(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_viewEdit");
			}
		}
		
		String id = getSelectedKeyValue();
		if (id == null) {
			MsgBox.showWarning("请先选择投标人!");
			return;
		}
		String tmpId = getSelectedKeyValue(this.tblMain);
		if(tmpId != null)
		{
			//检测招标清单的招标立项已经生成存在评标结果报审，新增投标人
//			IObjectPK pk = new ObjectUuidPK(tmpId);
//			NewListingInfo listInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(pk);
//			
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("inviteProject", listInfo.getInviteProject().getId().toString()));
//			
//			if(AppraiseResultFactory.getRemoteInstance().exists(filter))
//			{
//				MsgBox.showWarning("所选招标清单所关联的招标立项已经存在评标结果报审，不能执行此操作。");
//				return;
//			}
		}
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("inviteListingId", this.getInviteListingId());
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		QuotingPriceTypeEnum type = QuotingPriceTypeEnum.modify;
		if (e != null && e.getSource() instanceof KDMenuItem) {
			type = (QuotingPriceTypeEnum) ((KDMenuItem) e.getSource())
					.getUserObject();
		}
		uiContext.put("quotingPriceType", type);
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
				.create(getEditUIName(), uiContext, null, "VIEW1");
		uiWindow.show();
	}

	protected void tblDetail_tableSelectChanged(KDTSelectEvent e) throws Exception {
		String id = getSelectedKeyValue();
		if (id == null) {
			return;
		}
		
		NewQuotingPriceInfo info = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceInfo(new ObjectUuidPK(id));
		String state = (String) this.getUIContext().get("UIClassParam");
		if ("ASSESS".equals(state)) {
			if (info.isHasEffected()) {
				this.actionOverRangeQuotingPrice.setEnabled(true);
			} else {
				this.actionOverRangeQuotingPrice.setEnabled(false);
			}
		} else {
			if (QuotingPriceStatusEnum.Evaluated.equals(info.getStatus())
					||QuotingPriceStatusEnum.ImportToDB.equals(info.getStatus())
					||QuotingPriceStatusEnum.ImportBase.equals(info.getStatus())) {
				this.actionRemove.setEnabled(false);
				this.btnImportModifyPrice.setEnabled(false);
				this.actionImportModifyPrice.setEnabled(false);
				this.btnImportOriginalPrice.setEnabled(false);
				this.actionEdit.setEnabled(false);
			} 
			else if(QuotingPriceStatusEnum.NoImportPrice.equals(info.getStatus()))
			{
				this.btnImportModifyPrice.setEnabled(false);
				this.actionImportModifyPrice.setEnabled(false);				
				this.btnImportOriginalPrice.setEnabled(true);
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}
			else if(QuotingPriceStatusEnum.ImportedPrice.equals(info.getStatus()))
			{
				this.btnImportModifyPrice.setEnabled(true);
				this.actionImportModifyPrice.setEnabled(true);
				this.btnImportOriginalPrice.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(true);
			}
		}
		
		// 只有最新版本招标清单的“选择投标人”，“导入原始报价”，“导入修正报价”按钮才可用 Added By Owen_wen 2010-11-11
		int selectedRowIdx = this.getMainTable().getSelectManager().getActiveRowIndex();
		IRow row  = this.getMainTable().getRow(selectedRowIdx);
		boolean isLastVersion = ((Boolean)row.getCell("isLastVersion").getValue()).booleanValue();
		if (!isLastVersion){
			this.actionImportModifyPrice.setEnabled(false);
			this.btnImportOriginalPrice.setEnabled(false);
		}
	}

	public void tblDetail_tableClicked(KDTMouseEvent e) {
		try {
			if (e.getType() == 1 && e.getClickCount() == 2) {
				actionView_actionPerformed(null);
			}
		} catch (Exception e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
	}

	public void actionAssess_actionPerformed(ActionEvent e) throws Exception {

		if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			IObjectPK orgPK = getOrgPK(this.actionAssess);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
					"inv_quotingPriceAnalyse_assess");
		}
		
		UIContext uiContext = new UIContext(ui);
		if(this.getInviteListingId() == null){
			FDCMsgBox.showWarning("请选择相应的招标清单!");
			return;
		}
		uiContext.put("inviteListingId", this.getInviteListingId());
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(NewQuoteAnalyseUI.class.getName(), uiContext, null,
						"View");
		uiWindow.show();
	}

	// 报价排名
	public void actionCompositor_actionPerformed(ActionEvent e)
			throws Exception {
		if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			IObjectPK orgPK = getOrgPK(this.actionCompositor);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
					"inv_quotingPriceAnalyse_compositor");
		}
		
		String inviteListingId = this.getInviteListingId();
		if (inviteListingId == null) {
			return;
		}
		QuotingCompositorUI.showUI(this, inviteListingId);
	}

	public void actionSelectBidder_actionPerformed(ActionEvent e)
			throws Exception {
		if(UI_CLASS_PARAM.equals("ASSESS"))
		{
			IObjectPK orgPK = getOrgPK(this.actionSelectBidder);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
					"inv_quotingPriceAnalyse_selectQuotingPrice");
		}
		
		super.actionSelectBidder_actionPerformed(e);
		String inviteListingId = this.getInviteListingId();
		if (inviteListingId == null) {
			return;
		}
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("inviteProject.id");
		sic.add("inviteProject.econInviteDate");
		
		// deleted by msb 2010/04/27
		// NewListingInfo list =
		// NewListingFactory.getRemoteInstance().getNewListingInfo(new
		// ObjectUuidPK(inviteListingId),sic);
		// if(list.getInviteProject()!=null&&list.getInviteProject().getEconInviteDate()!=null){
		// java.util.Date sqlTime= FDCCommonServerHelper.getServerTime();
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// logger.debug("ServerTime:"+sqlTime);
		// logger.debug("ServerTime:EconInviteDate"+sdf.format(sqlTime)+":"+sdf.format(list.getInviteProject().getEconInviteDate()));
		//			
		// if(sdf.parse(sdf.format(sqlTime)).compareTo(sdf.parse(sdf.format(list.getInviteProject().getEconInviteDate())))<0){
		// throw new
		// InviteProjectException(InviteProjectException.CANSELECTQUOINGPRICE);
		// }
		//				
		// }
		
		BidderSelectUI.showUI(this, inviteListingId);
	}

	protected void initWorkButton() {
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		super.initWorkButton();

		String state = (String) this.getUIContext().get("UIClassParam");
		this.menuWorkFlow.setVisible(true);
		if ("ASSESS".equals(state)) {
			this.actionWorkFlowG.setVisible(true);
			this.actionWorkFlowG.setEnabled(true);
		} else {
			this.actionWorkFlowG.setVisible(false);
			this.actionWorkFlowG.setEnabled(false);
		}
		this.btnChooseSupplier.setIcon(btnAddNew.getIcon());
		this.menuItemAddNew.setText(this.btnChooseSupplier.getText());
		this.menuItemAddNew.setToolTipText(this.btnChooseSupplier.getToolTipText());
		this.btnImportModifyPrice.setIcon(EASResource.getIcon("imgTbtn_importexcel"));		
		this.btnImportOriginalPrice.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		
		this.menuItemExportPrice.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.menuBiz.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.menuItemExportPrice.setVisible(true);
		this.menuItemExportPrice.setEnabled(true);	
		
		this.btnExportPrice.setIcon(EASResource.getIcon("imgTbtn_output"));		
		this.btnAssess.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		this.btnCompositor.setIcon(EASResource.getIcon("imgTbtn_sortorder"));
		this.btnSelectBidder.setIcon(EASResource
				.getIcon("imgTbtn_selectcompany"));
		this.menuItemAssess.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		this.menuItemCompesitor.setIcon(EASResource
				.getIcon("imgTbtn_sortorder"));
		this.menuItemSelectBidder.setIcon(EASResource
				.getIcon("imgTbtn_selectcompany"));

		this.menuItemItemSetting
				.setIcon(EASResource.getIcon("imgTbtn_setting"));
		this.menuExport.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.kDMenuItem5.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.kDMenuItem6.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.kDMenuItem7.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.kDMenuItem8.setIcon(EASResource.getIcon("imgTbtn_output"));
	}

	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeOrg_valueChanged(e);
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeOrg
				.getLastSelectedPathComponent();
		if (orgNode != null
				&& orgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			if (SysContext.getSysContext().getCurrentOrgUnit().getId()
					.toString().equals(orgId)) {
				this.actionAddNew.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionSelectBidder.setEnabled(true);
				this.actionCompositor.setEnabled(true);
				this.actionAssess.setEnabled(true);
				this.actionKeyItemSet.setEnabled(true);
				this.actionUnBid.setEnabled(true);
			} else {
				this.actionAddNew.setEnabled(false);
				this.actionRemove.setEnabled(false);
				this.actionSelectBidder.setEnabled(false);
				this.actionCompositor.setEnabled(false);
				this.actionAssess.setEnabled(false);
				this.actionKeyItemSet.setEnabled(false);
				this.actionUnBid.setEnabled(false);
			}
		}
	}

	protected EntityViewInfo genBillQueryView(String contractId) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("listing.id", contractId));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("number"));
		SelectorItemCollection selectors = genBillQuerySelector();
		view.getSelector().addObjectCollection(selectors);
		return view;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getType() == 1 && e.getClickCount() == 2) {
			String id = getSelectedKeyValue(this.tblMain);
			if (id == null) {
				return;
			}
			UIContext context = new UIContext(ui);
			context.put(UIContext.ID, getSelectedKeyValue(this.tblMain));
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
					.create(NewListingEditUI.class.getName(), context, null,
							OprtState.VIEW);
			window.show();
		}
	}
	
	private void setThisAuditResultButton() throws Exception
    {
    	//if (!this.actionAuditResult.isVisible() || //comment by gongyin,070830,不能这样玩
    	if (this.tblMain.getSelectManager().getActiveRowIndex() < 0) {
			return;
		}

        //add by gzb 客户端已经注销
        if(SysContext.getSysContext().getSessionID()== null) {
            return;
        }
        
        String state = (String) this.getUIContext().get("UIClassParam");
		if (!"ASSESS".equals(state)) {
			setAuditResultButtonState(false);
			return;
		}

        String id = getSelectedKeyValue(tblMain);
        if (!StringUtils.isEmpty(id)) {
            IMultiApprove iMultiApprove = MultiApproveFactory.getRemoteInstance();
            setAuditResultButtonState(iMultiApprove.hasApproveResult(id));
        } else {
        	setAuditResultButtonState(false);
        }
    }
    protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception
    {
		if (e.getSelectBlock() == null)
			return;
		getBillListTable().removeRows();
		KDTSelectBlock selectBlock = e.getSelectBlock();
		int top = selectBlock.getTop();
		if(top>=0){
			String contractId = (String) getMainTable().getCell(top, getKeyFieldName()).getValue();
			displayBillByInvite(genBillQueryView(contractId));
			setThisAuditResultButton();
		}
		
		// 只有最新版本招标清单的“选择投标人”，“导入原始报价”，“导入修正报价”，“选择报价”，“报价评审”按钮才可用 Added By Owen_wen 2010-11-11
		int selectedRowIdx = this.getMainTable().getSelectManager().getActiveRowIndex();
		
		// 点输入过滤条件查询，当没有结果集返回时，selectedRowIdx会为-1，需要判断
		if (selectedRowIdx == -1) return;
		else {
			IRow row  = this.getMainTable().getRow(selectedRowIdx);
			boolean isLastVersion = ((Boolean)row.getCell("isLastVersion").getValue()).booleanValue();
			this.btnChooseSupplier.setEnabled(isLastVersion);
			this.actionSelectBidder.setEnabled(isLastVersion);
			this.actionAssess.setEnabled(isLastVersion);
		}
    }
	protected String getSelectedKeyValue()
    {
		return super.getSelectedKeyValue();   
    }

	public void actionKeyItemSet_actionPerformed(ActionEvent e)
			throws Exception {

		}

	/**
     * output actionExportPrice_actionPerformed method
     */
    public void actionExportPrice_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	if(UI_CLASS_PARAM != null && UI_CLASS_PARAM.equals("EXPORT"))
		{
			IObjectPK orgPK = getOrgPK(this.actionExportPrice);
			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()), orgPK,
					"inv_listingExport_export");
		}
    	
    	this.checkSelected();
		String listid = this.getInviteListingId();
		NewListingInfo listing = NewQuotingPriceEditUI.getInviteListing(listid);
		KDTabbedPane tabbedPnlTable = new KDTabbedPane();
		loadPages(tabbedPnlTable,listing);
		
		if (tabbedPnlTable.getTabCount() <= 2) {
			MsgBox.showWarning("没有可导出的数据");
            return;
        }
		File zipFile = null;
		if ((zipFile = NewQuotingPriceEditUI.fileDialog("导出",listing.getName(), JFileChooser.SAVE_DIALOG,this)) == null)
			return;
		if (!zipFile.getName().endsWith(".fdc"))
			zipFile = new File(zipFile.getAbsolutePath() + listing.getName()+".fdc");
		NewQuotingPriceEditUI.exportQuoting(zipFile,tabbedPnlTable,new ArrayList(25),new HashMap(5),listid);
		showBarInfo("成功导出报价");

        }
    /**
	 * 功能描述:在状态栏显示信息
	 */
	protected void showBarInfo(String info) {
		setMessageText(info);
		setNextMessageText(info);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
    }
    
    public static void loadPages(final KDTabbedPane tabbedPnlTable,NewListingInfo info) {
		tabbedPnlTable.removeAll();
		NewListingPageCollection coll = info.getPages();
		for (int i = 0; i < coll.size(); i++) {
			NewListingPageInfo page = (NewListingPageInfo) coll.get(i);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			table.setName(pageHead.getName());
			//initTable(table, page);
			table.setUserObject(page);
			NewQuotingPriceEditUI.setTableColumn(table,page);
			NewQuotingPriceEditUI.setTableRow(table, page, true);
			table.getTreeColumn().setDepth(NewQuotingPriceEditUI.getMaxLevel(table));
			NewQuotingPriceEditUI.loadTableData(table, page, null, null);
			NewQuotingPriceEditUI.setUnionData(table,tabbedPnlTable);
			NewQuotingPriceEditUI.setTableLock(table, true);
			tabbedPnlTable.add(table, pageHead.getName());
		}
		NewQuotingPriceEditUI.initTotalPageTable(tabbedPnlTable,info);
		NewQuotingPriceEditUI.initPageDesTable(tabbedPnlTable);
		InviteHelper.setAotuHeight(tabbedPnlTable);
    }
	

	public void actionImportListUI_actionPerformed(ActionEvent e)
			throws Exception {
		IObjectPK orgPK = getOrgPK(this.actionImportListUI);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPrice_view");
	}

	public void actionAnalyseListUI_actionPerformed(ActionEvent e)
			throws Exception {
		IObjectPK orgPK = getOrgPK(this.actionAnalyseListUI);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_view");
	}

	protected String getEditUIModal() {
		// return UIFactoryName.NEWWIN;
		// 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		} else {
			return UIFactoryName.NEWTAB;
		}
	}

	//报价明细汇总表
	public void actionQuotingPriceSum_actionPerformed(ActionEvent e)
			throws Exception {

		IObjectPK orgPK = getOrgPK(this.actionAnalyseListUI);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
				"inv_quotingPriceAnalyse_reportExport");
		String inviteListingId = this.getInviteListingId();
		if (inviteListingId == null) {
			MsgBox.showWarning("请选择招标清单!");
			return;
		}
		UIContext uiContext = new UIContext(ui);
		uiContext.put("inviteListingId", this.getInviteListingId());
		IUIWindow window;
		window = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
				QuotingPriceSumUI.class.getName(), uiContext);

		window.show();
	}
	public void actionUnBid_actionPerformed(ActionEvent e) throws Exception
    {
		String quotingId = getSelectedDetailId();
		if(quotingId==null||quotingId.length()==0){
			MsgBox.showInfo("没有选择报价！");
			abort();
		}
		NewQuotingPriceInfo quoting = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceInfo(new ObjectUuidPK(quotingId));
		QuotingCompositorUI.unBid(this,quoting);
    }
	/**
	 * 在查询执行之前构造出文件类型的过滤条件
	 * 其中指设置为枚举的值而不是枚举本身
	 */
	protected void beforeExcutQuery(EntityViewInfo ev) {
		UI_CLASS_PARAM = (String) getUIContext().get("UIClassParam");
		super.beforeExcutQuery(ev);
	}

	protected boolean isCanOrderTable() {
		return true;
	}

	protected boolean isOrderForClickTableHead() {
		return true;
	}
	
    public void myQueryAction() throws Exception{
    	  IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory.getRemoteInstance();
	       String  cureQueryName=(getQueryInfo(this.mainQueryPK)).getFullName();

	        // String orgID = this.getOrgUnitInfo().getId().toString();
	        // String userID = this.getUserInfo().getId().toString();
	        if (!isPerformDefaultQuery(iQuery, cureQueryName))
	        {
	            // 是否显示通用过滤框。
	            if (!isFirstDefaultQuery())
	            {
	                if (dialog == null)
	                {
	                    dialog = initCommonQueryDialog();
	                }else
	                {   //为了解决BT242496父窗口传递问题 2007-11-1
	                	if (this.getUIWindow() == null)
	                    {
	                        dialog.setOwner((Component) getUIContext().get(UIContext.OWNERWINDOW));
	                    }
	                    else
	                    {
	                        dialog.setOwner(this);
	                    }
	                }

	                boolean isShowDialog=ThreadPool.isShowDialog();
	                if (this.actionQuery.isDaemonRun())//||isShowDialog)
	                {
	                	ThreadPool.destroyMonitor();
	                }
	                if(isSupportDynQuery())
	                {
	                	dialog.setDynQueryVisible(true);
	                }

	                if (dialog.show())
	                {
	                	if (!isHandlerTable)
	                	{
	                		this.isHandlerTable=true;
	                	}
	                	if (this.actionQuery.isDaemonRun()||isShowDialog)
	                    {
	                	  ThreadPool.showProgressDialog(this,this.getUITitle());
	                    }
	                	tHelper.setDialog(dialog);
	                    if (this.mainQuery.getSorter() != null)
	                    {
	                        this.mainQuery.getSorter().clear();
	                    }
	                    this.mainQuery = getEntityViewInfo(dialog.getEntityViewInfoResult());

	                    if(UtilRequest.isPrepare("ActionQuery",this)) {
	                    	prepareQuery(null).callHandler();
	                    }
	                   

	                    setMainOrgForCommonQueryDialog(dialog);
	                    if(this.orgContextManager != null)
	                    {
	                    	this.mainQuery.getFilter().mergeFilter(this.orgContextManager.changeQueryContext(this.getUIContext()),"and");
	                    }
	                    if(isSupportDynQuery())
	                    {
	                    	handleDynamicQuery(Util.getDynSelector(mainQuery));
	                    }
	                   
	                    FilterInfo filter = null;
	            		
	            		DefaultKingdeeTreeNode node = getSelectedTreeNode(this.treeOrg);
	            		if(node != null){
	            			filter = new FilterInfo();		
	            			OrgStructureInfo orgInfo = (OrgStructureInfo) node.getUserObject();
	            			if(orgInfo.isIsLeaf()){
	            				
	            				filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber",orgInfo.getLongNumber()));
	            				
	            			}else{
	            				
	            				filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber",orgInfo.getLongNumber(),CompareType.LIKE));
	            			}
	            			
	            			this.mainQuery.getFilter().mergeFilter(filter, "and");
	            	      }
	            		node = getSelectedTreeNode(this.treeInviteType);
	            		if(node != null){
	            			
	            			filter = new FilterInfo();	
	            			if(!(node.getUserObject() instanceof String)){
	            				
	            				InviteTypeInfo inviteType = (InviteTypeInfo) node.getUserObject();
	            				if(inviteType.isIsLeaf()){
	            					
	            					filter.getFilterItems().add(new FilterItemInfo("inviteType.longNumber",inviteType.getLongNumber()));
	            				}else{
	            					
	            					filter.getFilterItems().add(new FilterItemInfo("inviteType.longNumber",inviteType.getLongNumber(),CompareType.LIKE));
	            				}
	            			}
	            			this.mainQuery.getFilter().mergeFilter(filter, "and");
	            		}
	            		
	                    
	                    tblMain.removeRows(false);
	                    doQuery(dialog);
	                    tblMain.refresh();
	                }
	                else
	                {
	                	tHelper.setDialog(dialog);
	                    SysUtil.abort();
	                }
	            }
	        }
		
    	
    }
    
    protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree)
	{
		if(selectTree.getLastSelectedPathComponent() != null)
		{
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null ;
	}

	/** 
	 * 根据报价ID，供应商ID，先清除库中该报价下的清单数据（NewListingValue）。
	 * 甲方对应的NewListingEntry不需要清除。
	 * @Author：owen_wen
	 * @CreateTime：2011-11-6
	 */
	private void clearSupplierNewsListingEntries(String quotingPriceId, String supplierId) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.id", quotingPriceId));
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.supplier.id", supplierId));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		NewListingValueCollection newListingValues = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
		Set newListingEntryIdSet = new HashSet();
		for (int i = 0; i < newListingValues.size(); i++) {
			newListingEntryIdSet.add(newListingValues.get(i).getEntry().getId().toString());
		}

		if (!newListingEntryIdSet.isEmpty()) {
			filter.getFilterItems().clear();
			filter.getFilterItems().add(new FilterItemInfo("id", newListingEntryIdSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("head.headType.isDefined", new Integer(1))); // 非供应商定义页签不需要清除
			NewListingEntryFactory.getRemoteInstance().delete(filter); // 删除当前供应商对应的清单分录，进而也删除了清单数据
		}
	}
	
}