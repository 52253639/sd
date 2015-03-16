/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.semantic.design.client.action.Refresh;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;

/**
 * output class name
 */
public class CommerceChangeNewListUI extends AbstractCommerceChangeNewListUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 724095972203270746L;
	private static final Logger logger = CoreUIObject
			.getLogger(CommerceChangeNewListUI.class);

	private boolean isNew = false;
	private SellProjectInfo sellProjectInfo = null;
	private boolean isShowAll=false;
	protected Map permit=new HashMap();
	protected boolean isControl=SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo());
	/**
	 * output class constructor
	 */
	public CommerceChangeNewListUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void initTree() throws Exception {
		if(this.getUIContext().get("filter")==null){
			//this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(
			//	actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			this.treeMain.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
		}else{
			this.toolBar.setVisible(false);
			this.treeView.setVisible(false);
		}
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (isOrderForClickTableHead() && e.getType() == 0 && e.getButton() == 1 && e.getClickCount() == 1) {
			super.tblMain_tableClicked(e);
		}
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			IRow row = this.tblMain.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.CommerceChangeNewEditUI";
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, idStr);
				try {
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(uiClassName, uiContext,
							null, OprtState.VIEW);
					uiWindow.show();
				} catch (UIException e1) {
					logger.error(e1.getMessage() + "打开户型界面失败！");
				}

			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (this.tblMain.getColumn("createTime") != null) {
			this.tblMain.getColumn("createTime").getStyleAttributes()
					.setNumberFormat("yyyy-mm-dd");
		}

		setButtonState(false);

		this.btnShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		this.btnJoin.setIcon(EASResource.getIcon("imgTbtn_citecompany"));
		this.btnStop.setIcon(EASResource
				.getIcon("imgTbtn_releasebymdandsessin"));
		this.btnActive.setIcon(EASResource.getIcon("imgTbtn_distribute"));

		this.btnAddTrack.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.btnWriterPaper.setIcon(EASResource.getIcon("imgTbtn_addcredence"));
		this.btnToTransaction.setIcon(EASResource.getIcon("imgTbtn_deliverto"));

		if (this.tblMain.getColumn("probability") != null) {
			this.tblMain.getColumn("probability").getStyleAttributes()
					.setNumberFormat("#,##0");
		}

		KDMenuItem item = null;

		item = new KDMenuItem();
		item.setAction(this.actionToTransaction);
		item.setText("转预定");
		item.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_editbatch"));
		this.btnToTransaction.addAssistMenuItem(item);

		item = new KDMenuItem();
		item.setAction(this.actionToPurchase);
		item.setText("转认购");
		item.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_editbatch"));
		this.btnToTransaction.addAssistMenuItem(item);

		item = new KDMenuItem();
		item.setAction(this.actionToSign);
		item.setText("转签约");
		item.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_editbatch"));
		this.btnToTransaction.addAssistMenuItem(item);
		this.actionToPurchase.setEnabled(true);
		this.actionToSign.setEnabled(true);
		this.actionToTransaction.setEnabled(true);
		
		this.btnToTransaction.setVisible(false);
		//wyh
//		this.actionEdit.setEnabled(false);
//		this.actionRemove.setEnabled(false);
//		this.btnEdit.setVisible(false);
//		this.btnRemove.setVisible(false);
//		this.menuItemEdit.setVisible(false);
		this.menuItemRemove.setVisible(false);
		
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
		
		this.actionAddNew.setVisible(false);
		this.btnShowAll.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
		
		if(this.getUIContext().get("filter")!=null){
			this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		}
		if(SysContext.getSysContext().getCurrentUserInfo()!=null&&
				SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			KDWorkButton update=new KDWorkButton();
			update.setText("数据更新");
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
				private void update_actionPerformed(ActionEvent e) throws EASBizException, BOSException {
					SelectorItemCollection sic = new SelectorItemCollection();
		            sic.add(new SelectorItemInfo("newLevel.*"));
		            sic.add(new SelectorItemInfo("classify.*"));
		            sic.add(new SelectorItemInfo("commerceChanceStage.*"));
		            sic.add(new SelectorItemInfo("trackDate"));
		            sic.add(new SelectorItemInfo("trackContent"));
		            
		            SelectorItemCollection updatesic = new SelectorItemCollection();
		            updatesic.add("classify.*");
		            
					for(int i = 0 ; i < tblMain.getRowCount();i++){
						IRow row = tblMain.getRow(i);
					    String id = row.getCell("id").getValue().toString();
			            CommerceChanceInfo cct=CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(id));
			    		CommerceChanceTrackInfo	lastTrackInfo = SHEManageHelper.getLastCommerceChanceTrack(null,cct);
			    		
			    		if(lastTrackInfo!=null){
		    				cct.setNewLevel(lastTrackInfo.getCommerceLevel());
			    			cct.setClassify(lastTrackInfo.getClassify());
			    			cct.setCommerceChanceStage(lastTrackInfo.getCommerceChanceStage());
			    			cct.setTrackDate(lastTrackInfo.getTrackDate());
			    			cct.setTrackContent(lastTrackInfo.getTrackContent());
			    			CommerceChanceFactory.getRemoteInstance().updatePartial(cct, sic);
			    			
			    			String filterStr = "'"+CRMConstants.COMMERCECHANCE_STAGE_PAIHAO+"'" +
							","+"'"+CRMConstants.COMMERCECHANCE_STAGE_BOOKING+"'" +
									","+"'"+CRMConstants.COMMERCECHANCE_STAGE_PURCHASE+"'" +
											","+"'"+CRMConstants.COMMERCECHANCE_STAGE_SIGN+"'" +
													","+"'"+CRMConstants.COMMERCECHANCE_STAGE_QUITROOM+"'" +
															","+"'"+CRMConstants.COMMERCECHANCE_STAGE_CHANGENAME+"'";
			    			
			    			CommerceChanceTrackCollection col=CommerceChanceTrackFactory.getRemoteInstance().getCommerceChanceTrackCollection("select * from where commerceChance.id='"+id+"' and commerceChanceStage.id in("+filterStr+") and classify.id is null");
			    			for(int j=0;j<col.size();j++){
			    				col.get(j).setClassify(lastTrackInfo.getClassify());
			    				CommerceChanceTrackFactory.getRemoteInstance().updatePartial(col.get(j), updatesic);
			    			}
		    			}
					}
				}
	        });
			this.toolBar.add(update);
		}
	}

	private void setButtonState(boolean result) {
		this.actionStop.setEnabled(result);
		this.actionActive.setEnabled(result);
		this.actionShare.setEnabled(result);
		this.actionJoin.setEnabled(result);

		this.actionAddTrack.setEnabled(result);
		this.actionWritePaper.setEnabled(result);
		this.btnToTransaction.setEnabled(result);
	}

	public void onShow() throws Exception {
		super.onShow();

		if (this.isSellOrg()) {
//			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			setButtonState(true);

			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node != null) {
				if (node.getUserObject() instanceof OrgStructureInfo) {
					setButtonState(false);
				}

			}
		} else {
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			setButtonState(false);
		}

	}

	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
//		return UIFactoryName.NEWTAB;
		return UIFactoryName.MODEL;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node
						.getUserObject();
				this.sellProjectInfo = sellProject;
				this.setButtonState(true);
			} else {
				this.setButtonState(false);
			}
		}
		this.execQuery();
	}

	protected String getEditUIName() {
		return CommerceChangeNewEditUI.class.getName();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try {
			if(this.getUIContext().get("filter")==null){
				if (node != null) {
					if (node.getUserObject() instanceof SellProjectInfo) {
						SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
						if(!isControl){
							Set saleMan= SHEManageHelper.getPermitSaleManSet(sellProject,permit);
							filter.getFilterItems().add(new FilterItemInfo("saleMan.id", saleMan,CompareType.INNER));
						}
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
						
						if(isShowAll){
							filter.getFilterItems().add(new FilterItemInfo("commerceSrc", CommerceSrcEnum.TRANSACTION_VALUE));
							filter.getFilterItems().add(new FilterItemInfo("status", CommerceChangeNewStatusEnum.TRANSACTION_VALUE));
							filter.getFilterItems().add(new FilterItemInfo("id", "select FCommerceChanceID from T_MAR_QuestionPaperAnswer where FSellProjectID='"+sellProject.getId()+"'",CompareType.NOTINNER));
						}
					} else if (node.getUserObject() instanceof OrgStructureInfo) {
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'nullnull'",CompareType.INNER));
					}
				}else{
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'"));
				}
			}else{
				if (this.getUIContext().get("filter") != null) {
					filter.mergeFilter((FilterInfo) this.getUIContext().get("filter"), "and");
				}
			}
			viewInfo = (EntityViewInfo) mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	/**
	 * 满足条件的置业顾问集合,不包含共享
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
//	private String getUserIdSet()throws BOSException, EASBizException{
//		StringBuffer idSet = new StringBuffer();
//		EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(false);
//		UserCollection userColl = UserFactory.getRemoteInstance().getUserCollection(view);
//		if(userColl != null && userColl.size() > 0){
//			for(int i=0;i<userColl.size();i++){
//				idSet.append("'");
//				idSet.append(userColl.get(i).getId().toString());
//				idSet.append("'");
//				if(i!=userColl.size()-1){
//					idSet.append(",");
//				}
//			}
//		}
//		//添加自己
//		UserInfo currentInfo = SysContext.getSysContext().getCurrentUserInfo();
//		idSet.append(",'"+currentInfo.getId().toString()+"'");
//		return idSet.toString();
//	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node
						.getUserObject();
				this.sellProjectInfo = sellProject;
				uiContext.put("sellProject", sellProject);
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				uiContext.put("sellProject", null);
				if (this.isNew) {
					FDCMsgBox.showWarning(this, "请选择项目!");
					this.abort();

				}
			}
		}

	}

	protected void refresh(ActionEvent e) throws Exception {
		// super.refresh(e);
		this.tblMain.removeRows();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected String[] getLocateNames() {
		String[] locateNames = new String[2];
		locateNames[0] = "customer.name";
		locateNames[1] = "phone";
		return locateNames;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		isNew = true;
		isExistChild();
		super.actionAddNew_actionPerformed(e);
	}

	private void isExistChild() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		int number = 0;
		if (node != null) {
			number = node.getChildCount();
		}

		if (number > 0) {
			FDCMsgBox.showWarning(this, "只能在末级项目下新增商机!");
			this.abort();
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.isNew = false;
		checkStatus("update");
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.isNew = false;
		checkStatus("delete");
		super.actionRemove_actionPerformed(e);
	}

	public void actionStop_actionPerformed(ActionEvent e) throws Exception {
		super.actionStop_actionPerformed(e);

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		if (selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		IRow row = this.tblMain.getRow(selectRows[0]);
		if (row == null) {
			return;
		}

		String status = row.getCell("status").getValue().toString();
		if (status.equals(CommerceChangeNewStatusEnum.CLOSE.getAlias())) {
			FDCMsgBox.showWarning(this, "商机已经关闭，不能终止!");
			this.abort();
		} else if (status.equals(CommerceChangeNewStatusEnum.END.getAlias())) {
			this.alertMsg("商机已经是终止状态，请不要重复操作!");
		}
		try {
			CommerceChanceInfo model = new CommerceChanceInfo();
			model.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
			model.setStatus(CommerceChangeNewStatusEnum.END);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("id"));
			selector.add(new SelectorItemInfo("status"));
			CommerceChanceFactory.getRemoteInstance().updatePartial(model,
					selector);
			FDCMsgBox.showWarning(this, "商机终止成功!");
			this.abort();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		this.execQuery();

	}

	public void actionShare_actionPerformed(ActionEvent e) throws Exception {
		checkStatus("share");
		super.actionShare_actionPerformed(e);

		int selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if (selectRow == -1) {
			this.alertMsg("请先选择记录!");
		}
		IRow row = this.tblMain.getRow(selectRow);

		String id = row.getCell("id").getValue().toString();
		UIContext uiContext = new UIContext(this);
		uiContext.put("CommerceChanceID", id);
		uiContext.put("CommerceChanceProject", getSellProject());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CommerceChanceShareUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
	}

	public void actionJoin_actionPerformed(ActionEvent e) throws Exception {
		super.actionJoin_actionPerformed(e);

		int selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if (selectRow == -1) {
			this.alertMsg("请先选择记录!");
		}
		IRow row = this.tblMain.getRow(selectRow);

		String id = row.getCell("id").getValue().toString();

		
		getSellProject();
		UIContext uiContext = new UIContext(this);
		uiContext.put("CommerceChanceID", id);
		uiContext.put("CommerceChanceProject", getSellProject());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CommerceChanceJoinUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		this.execQuery();
	}
	
	
	private SellProjectInfo getSellProject(){
		
		SellProjectInfo sellInfo = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node != null) {
		
			DefaultKingdeeTreeNode nodeTemp = getParentNode(node);
			if(nodeTemp!=null){
				sellInfo = (SellProjectInfo)nodeTemp.getUserObject();
			}
		}
		
		return sellInfo;
	}

	private DefaultKingdeeTreeNode getParentNode(DefaultKingdeeTreeNode node) {
		if (node == null) {
			return null;
		}

		if (node.getParent() != null) {

			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) node
					.getParent();
			if (treeNode.getUserObject() instanceof SellProjectInfo) {
				return getParentNode(treeNode);
			} else {
				return node;
			}

		}

		return null;
	}

	public void actionActive_actionPerformed(ActionEvent e) throws Exception {
		super.actionActive_actionPerformed(e);
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		if (selectRows.length < 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		IRow row = this.tblMain.getRow(selectRows[0]);
		if (row == null) {
			return;
		}

		String status = row.getCell("status").getValue().toString();
		if (status.equals(CommerceChangeNewStatusEnum.ACTIVE.getAlias())) {
			this.alertMsg("商机已经激活，请不要重复操作!");
		}
		if(!isControl()){
			if (!isDuty()) {
				this.alertMsg("不是营销团队负责人，不能进行此操作!");
			}
		}
		try {
			CommerceChanceInfo model = new CommerceChanceInfo();
			model.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
			model.setStatus(CommerceChangeNewStatusEnum.ACTIVE);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("id"));
			selector.add(new SelectorItemInfo("status"));
			CommerceChanceFactory.getRemoteInstance().updatePartial(model,
					selector);
			FDCMsgBox.showWarning(this, "商机激活成功!");
			this.abort();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		this.execQuery();
	}
	
	private boolean isControl(){
		boolean result = false;
		MarketUnitControlInfo  info = null;
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		FullOrgUnitInfo unit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		try {
			info = MarketUnitControlFactory.getRemoteInstance().getMarketUnitControlInfo("select id where controler.id='"+user.getId().toString()+"' and orgUnit.id='"+unit.getId().toString()+"'");
		} catch (EASBizException e) {
			logger.error(e.getMessage()+"在管控人员中无法查到此人的信息!");
		} catch (BOSException e) {
			logger.error(e.getMessage()+"在管控人员中无法查到此人的信息!");
		}
		
		if(info!=null && info.getId()!=null){
			result = true;
		}
		return result;
	}

//	protected void initListener() {
//		super.initListener();
//
//		this.tblMain.getDataRequestManager().addDataFillListener(
//				new KDTDataFillListener() {
//					public void afterDataFill(KDTDataRequestEvent e) {
//						tblMain_afterDataFill(e);
//					}
//				});
//	}
//
//	private void tblMain_afterDataFill(KDTDataRequestEvent e) {
//
//		// 取得当前页的第一行
//		int sr = e.getFirstRow();
//		if (e.getFirstRow() > 0) {
//			sr = sr - 1;
//		}
//		// 取得当前页的最后一行
//		int lr = e.getLastRow();
//
//		StringBuffer sb = new StringBuffer();
//		sb.append("(");
//		for (int i = sr; i <= lr; i++) {
//			IRow row = tblMain.getRow(i);
//			if (row == null||row.getCell("status").getValue().toString().equals(CommerceChangeNewStatusEnum.CLOSE.getAlias())) {
//				continue;
//			}
//			if (row.getCell("effectiveDate").getValue() != null) {
//				try {
//					Map detailSet= RoomDisplaySetting.getNewProjectSet(null,row.getCell("projectId").getValue().toString());
//					int day=0;
//					if(detailSet!=null){
//						day=Integer.parseInt(detailSet.get(SHEParamConstant.T2_COMMERCECHANCE_DAYS).toString()); 
//					}
//					Date effdate = (Date) row.getCell("effectiveDate").getValue();
//					Date nowDate = FDCDateHelper.getDayBegin(FDCCommonServerHelper.getServerTime());
//					if (FDCDateHelper.getDiffDays(effdate,nowDate)>day) {
//						String idStr = row.getCell("id").getValue().toString();
//						sb.append("'" + idStr + "'");
//						sb.append(",");
//					}
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//		if(sb.lastIndexOf(",")>0){
//			sb.deleteCharAt(sb.lastIndexOf(","));
//			sb.append(")");
//			try {
//				FDCSQLBuilder sql = new FDCSQLBuilder();
//				String sqlStr = "update T_SHE_CommerceChance set FStatus='close' where fid in "
//						+ sb.toString() + "";
//				sql.appendSql(sqlStr);
//				sql.executeUpdate();
//			} catch (BOSException e1) {
//				logger.error(e1.getMessage() + "更新商机状态失败!");
//			}
//		}
//	}

	public void actionAddTrack_actionPerformed(ActionEvent e) throws Exception {
		checkStatus("track");
		super.actionAddTrack_actionPerformed(e);

		int selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if (selectRow == -1) {
			this.alertMsg("请先选择记录!");
		}
		IRow row = this.tblMain.getRow(selectRow);
		CommerceChanceInfo commInfo =CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(row.getCell("id").getValue().toString()));
		UIContext uiContext = new UIContext(this);
		uiContext.put("sellProject", this.sellProjectInfo);
		uiContext.put("SelectCommerceChance", commInfo);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CommerceChanceTrackEditUI.class.getName(), uiContext,
						null, "ADDNEW");
		uiWindow.show();
	}

	public void actionWritePaper_actionPerformed(ActionEvent e)
			throws Exception {
		checkStatus("paper");
//		QuestionPaperAnswerInfo info = isExtisPaper();
//		if (info != null) {
//			UIContext uiContext = new UIContext(this);
//			uiContext.put(UIContext.ID, info.getId());
//			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
//					.create(QuestionPaperAnswerEditUI.class.getName(),
//							uiContext, null, OprtState.EDIT);
//			uiWindow.show();
//		} else {
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", this.sellProjectInfo);
			uiContext.put("isFromCommerceChance", getCommerce());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(QuestionPaperAnswerEditUI.class.getName(),
							uiContext, null, "ADDNEW");
			uiWindow.show();
//		}

		super.actionWritePaper_actionPerformed(e);
	}

	private CommerceChanceInfo getCommerce() {

		CommerceChanceInfo info = new CommerceChanceInfo();
		int number = KDTableUtil.getSelectedRow(this.tblMain);
		IRow row = this.tblMain.getRow(number);
		if (row != null) {
			info.setId(BOSUuid.read(row.getCell("id").getValue().toString()));
			if (row.getCell("name").getValue() != null) {
				info.setName(row.getCell("name").getValue().toString());
			}
//			if (row.getCell("number").getValue() != null) {
//				info.setNumber(row.getCell("number").getValue().toString());
//			}
		}
		return info;
	}

	private QuestionPaperAnswerInfo isExtisPaper() {

		QuestionPaperAnswerInfo info = null;

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		if (selectRows != null && selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		IRow row = this.tblMain.getRow(selectRows[0]);

		String id = row.getCell("id").getValue().toString();
		if (id != null && !"".equals(id)) {
			try {
				info = QuestionPaperAnswerFactory.getRemoteInstance()
						.getQuestionPaperAnswerInfo(
								"select id,name,number where commerceChance.id='"
										+ id + "' and sellProject.id='"
										+ sellProjectInfo.getId().toString()
										+ "'");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}

		return info;
	}

	private void checkStatus(String type) {

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		if (selectRows != null && selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}

		IRow row = this.tblMain.getRow(selectRows[0]);
		if (row == null) {
			return;
		}

		String status = row.getCell("status").getValue().toString();
		if (status.equals(CommerceChangeNewStatusEnum.CLOSE.getAlias())) {
			if (type.equals("update")) {
				alertMsg("商机已经关闭，不能修改!");
			} else if (type.equals("delete")) {
				alertMsg("商机已经关闭，不能删除!");
			} else if (type.equals("track")) {
				alertMsg("商机已经关闭，不能新增跟进!");
			} else if (type.equals("toTransaction")) {
				alertMsg("商机已经关闭，不能转交易!");
			} else if (type.equals("share")) {
				alertMsg("商机已经关闭，不能共享!");
			} else if (type.equals("paper")) {

				alertMsg("商机已经关闭，不能填写问卷!");
			}
		} else if (status.equals(CommerceChangeNewStatusEnum.END.getAlias())) {
			if (type.equals("update")) {
				alertMsg("商机已经终止，不能修改!");
			} else if (type.equals("delete")) {

				alertMsg("商机已经终止，不能删除!");
			} else if (type.equals("track")) {
				alertMsg("商机已经终止，不能新增跟进!");
			} else if (type.equals("toTransaction")) {
				alertMsg("商机已经终止，不能转交易!");
			} else if (type.equals("paper")) {
				alertMsg("商机已经终止，不能填写问卷!");
			}
		} else if (status.equals(CommerceChangeNewStatusEnum.TRANSACTION
				.getAlias())) {
			if (type.equals("update")) {
				alertMsg("商机已经成交，不能修改!");
			} else if (type.equals("delete")) {
				alertMsg("商机已经成交，不能删除!");
			}
//			else if (type.equals("track")) {
//				alertMsg("商机已经成交，不能新增跟进!");
//			} 
			else if (type.equals("toTransaction")) {
				alertMsg("商机已经成交，不能转交易!");
			} 
//			else if (type.equals("paper")) {
//				alertMsg("商机已经成交，不能填写问卷!");
//			}
		} else if (status.equals(CommerceChangeNewStatusEnum.ACTIVE.getAlias())) {
			if (type.equals("delete")) {
				String id = row.getCell("id").getValue().toString();
				try {
					CommerceChanceInfo res = CommerceChanceFactory
							.getRemoteInstance()
							.getCommerceChanceInfo(
									"select id,questionPaperAnser.id,questionPaperAnser.name,questionPaperAnser.number where id='"
											+ id + "'");
					if (res.getQuestionPaperAnser() != null
							&& res.getQuestionPaperAnser().size() > 0) {
						this.alertMsg("商机已经填写问卷,不能删除!");
					} else {
						boolean answ = CommerceChanceTrackFactory
								.getRemoteInstance().exists(
										"select id ,name where commerceChance.id='"
												+ id + "'");

						if (answ) {
							this.alertMsg("商机已经跟进,不能删除!");
						}
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void alertMsg(String msg) {
		FDCMsgBox.showWarning(this, msg);
		this.abort();
	}

	/**
	 * 判断当前用户是否营销团队中的负责人
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private boolean isDuty() throws BOSException, EASBizException {
		boolean isDuty = false;
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		MarketingUnitMemberCollection marketCol = MarketingUnitFactory
				.getRemoteInstance().getMarketUnitCollMember(user);

		for (int j = 0; j < marketCol.size(); j++) {
			MarketingUnitMemberInfo memInfo = marketCol.get(j);
			UserInfo userInfo = memInfo.getMember();
			if (userInfo.getId().toString().equals(user.getId().toString())
					&& memInfo.isIsDuty()) {
				isDuty = true;
			}
		}
		return isDuty;
	}

	public void actionToPurchase_actionPerformed(ActionEvent e)
			throws Exception {
		checkStatus("toTransaction");
		isToTransaction();
		super.actionToPurchase_actionPerformed(e);
		int selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if (selectRow == -1) {
			this.alertMsg("请先选择记录!");
		}
		IRow row = this.tblMain.getRow(selectRow);

		String id = row.getCell("id").getValue().toString();

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("sellProject.*");
		sels.add("customer.*");
		sels.add("customer.certificateType.*");
		CommerceChanceInfo res = CommerceChanceFactory.getRemoteInstance()
				.getCommerceChanceInfo(new ObjectUuidPK(id), sels);
		List customer = new ArrayList();
		customer.add(res.getCustomer());
		IUIWindow uiWindow  = SHEManageHelper.toTransactionBillForCommerceChance(res.getId(),res.getSellProject(), this, PurchaseManageEditUI.class.getName(),customer,res);
		
		PurchaseManageEditUI cusEditUI = (PurchaseManageEditUI) uiWindow.getUIObject();
		
		boolean cus = false;
		if(cusEditUI.getUIContext().get(PurchaseManageEditUI.KEY_SUBMITTED_DATA)!=null){
			cus = ((Boolean) cusEditUI.getUIContext().get(
					PurchaseManageEditUI.KEY_SUBMITTED_DATA)).booleanValue();
		}
			if (cus) {
			//updateToTransaction("toPur",id);
				CommerceChanceFactory.getRemoteInstance().updateToTransaction("toPur",id);
			this.execQuery();
		}
	}

	private void isToTransaction() {
		boolean isToPre = false;
		boolean isToPur = false;
		boolean isToSign = false;
		this.tblMain.checkParsed();
		int selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if (selectRow == -1) {
			this.alertMsg("请先选择记录!");
		}
		IRow row = this.tblMain.getRow(selectRow);
		if (row.getCell("isToPre").getValue() != null) {
			isToPre = ((Boolean) row.getCell("isToPre").getValue())
					.booleanValue();
		}
		if (row.getCell("isToPur").getValue() != null) {

			isToPur = ((Boolean) row.getCell("isToPur").getValue())
					.booleanValue();
		}
		if (row.getCell("isToSign").getValue() != null) {
			isToSign = ((Boolean) row.getCell("isToSign").getValue())
					.booleanValue();
		}
		
		if(isToPre){
			this.alertMsg("已转预订,请检查!");
		}
		if(isToPur){
			this.alertMsg("已转认购,请检查!");
		}
		if(isToSign){
			this.alertMsg("已转签约,请检查!");
		}
	}

	public void actionToSign_actionPerformed(ActionEvent e) throws Exception {
		checkStatus("toTransaction");
		isToTransaction();
		super.actionToSign_actionPerformed(e);
		int selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if (selectRow == -1) {
			this.alertMsg("请先选择记录!");
		}
		IRow row = this.tblMain.getRow(selectRow);

		String id = row.getCell("id").getValue().toString();

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("sellProject.*");
		sels.add("customer.*");
		sels.add("customer.certificateType.*");
		CommerceChanceInfo res = CommerceChanceFactory.getRemoteInstance()
				.getCommerceChanceInfo(new ObjectUuidPK(id), sels);
		List customer = new ArrayList();
		customer.add(res.getCustomer());
		IUIWindow uiWindow = SHEManageHelper.toTransactionBillForCommerceChance(res.getId(),res.getSellProject(), this, SignManageEditUI.class.getName(),customer,res);
		
		SignManageEditUI cusEditUI = (SignManageEditUI) uiWindow.getUIObject();
		
		boolean cus = false;
		
		if(cusEditUI.getUIContext().get(SignManageEditUI.KEY_SUBMITTED_DATA)!=null){
			cus = ((Boolean) cusEditUI.getUIContext().get(
					SignManageEditUI.KEY_SUBMITTED_DATA)).booleanValue();
		}
		
		if (cus) {
		//	updateToTransaction("toSign",id);
			CommerceChanceFactory.getRemoteInstance().updateToTransaction("toSign",id);
			this.execQuery();
		}
	}

//	private void updateToTransaction(String string,String id) {
//		
//		SelectorItemCollection selector = new SelectorItemCollection();
//		if(string.equals("toSign")){
//			selector.add(new SelectorItemInfo("isToSign"));
//		}else if(string.equals("toPre")){
//			selector.add(new SelectorItemInfo("isToPre"));
//		}else if(string.equals("toPur")){
//			selector.add(new SelectorItemInfo("isToPur"));
//		}
//		selector.add(new SelectorItemInfo("status"));
//		CommerceChanceInfo model = new CommerceChanceInfo();
//		if(string.equals("toSign")){
//			model.setIsToSign(true);
//		}else if(string.equals("toPre")){
//			model.setIsToPre(true);
//		}else if(string.equals("toPur")){
//			model.setIsToPur(true);
//		}
//		model.setStatus(CommerceChangeNewStatusEnum.TRANSACTION);
//		model.setId(BOSUuid.read(id));
//		try {
//			CommerceChanceFactory.getRemoteInstance().updatePartial(model, selector);
//		} catch (EASBizException e) {
//			logger.error("转交易失败!"+e.getMessage());
//		} catch (BOSException e) {
//			logger.error("转交易失败!"+e.getMessage());
//		}
//		
//	}

	public void actionToTransaction_actionPerformed(ActionEvent e)
			throws Exception {
		checkStatus("toTransaction");
		isToTransaction();
		super.actionToTransaction_actionPerformed(e);
		int selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if (selectRow == -1) {
			this.alertMsg("请先选择记录!");
		}
		IRow row = this.tblMain.getRow(selectRow);

		String id = row.getCell("id").getValue().toString();

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("sellProject.*");
		sels.add("customer.*");
		sels.add("customer.certificateType.*");
		CommerceChanceInfo res = CommerceChanceFactory.getRemoteInstance()
				.getCommerceChanceInfo(new ObjectUuidPK(id), sels);
		List customer = new ArrayList();
		customer.add(res.getCustomer());
		IUIWindow uiWindow  = SHEManageHelper.toTransactionBillForCommerceChance(res.getId(),res.getSellProject(), this, PrePurchaseManageEditUI.class.getName(),customer,res);
		PrePurchaseManageEditUI cusEditUI = (PrePurchaseManageEditUI) uiWindow.getUIObject();
		boolean cus = false;
		if(cusEditUI.getUIContext().get(PrePurchaseManageEditUI.KEY_SUBMITTED_DATA)!=null){
			cus = ((Boolean) cusEditUI.getUIContext().get(
					PrePurchaseManageEditUI.KEY_SUBMITTED_DATA)).booleanValue();
		}
		if (cus) {
			//updateToTransaction("toPre",id);
			CommerceChanceFactory.getRemoteInstance().updateToTransaction("toPre",id);
			this.execQuery();
		}
		
	}
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		String strSolutionName = "eas.fdc.sellhouse.commerceChance";
        DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        DatataskParameter param = new DatataskParameter();
        String solutionName = strSolutionName;
        param.solutionName = solutionName;
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        task.invoke(paramList, DatataskMode.ImpMode, true);
        this.refresh(null);
	}

	protected void btnShowAll_actionPerformed(ActionEvent e) throws Exception {
		if(isShowAll){
			this.btnShowAll.setText("显示无问卷交易补登");
			isShowAll=false;
		}else{
			this.btnShowAll.setText("隐藏无问卷交易补登");
			isShowAll=true;
		}
		this.refresh(null);
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		try
		{
			commonQueryDialog.addUserPanel(new CommerceChanceFilterUI());
		}
		catch(Exception e)
		{
			super.handUIException(e);
		}
		return commonQueryDialog;
	}
	 protected boolean initDefaultFilter() {
		   if(this.getUIContext().get("filter")==null){
			   return true;
		   }else{
			   return false;
		   }
	   }
}