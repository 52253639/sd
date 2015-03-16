/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceFactory;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellOrderFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettleMentFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */

public class SaleBalanceListUI extends AbstractSaleBalanceListUI {

	private static final Logger logger = CoreUIObject.getLogger(SaleBalanceListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private Set idSet = new HashSet();
	private SellProjectInfo sellProject = null;
	private CompanyOrgUnitInfo companyInfo = null;
	private PeriodInfo companyCurrentPeriod = null;
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		getAllSellProjectIds((TreeNode) this.treeMain.getModel().getRoot());
	}

	protected String[] getLocateNames() {
		String[] localNames = new String[6];
		localNames[0] = "operateType";
		localNames[1] = "startDate";
		localNames[2] = "endDate";
		localNames[3] = "remark";
		localNames[4] = "creator.name";
		localNames[5] = "balanceDate";
		return localNames;
	}

	public void actionSaleBalance_actionPerformed(ActionEvent e) throws Exception {
		
		/**
		 * 月结前，检测该项目是否已推盘，没有推盘不给月结；
		 * 推盘了检测推盘时间是否在月结日期以后，是的话不给推盘。
		 * */
		if (sellProject == null) {
			MsgBox.showInfo("还没选取到销售项目，请检查。");
			return;
		} else {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id", sellProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			if (SellOrderFactory.getRemoteInstance().exists(filter)) {
				filter.getFilterItems().add(new FilterItemInfo("orderDate", new Date(), CompareType.GREATER));
				if (SellOrderFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo(sellProject.getName() + " 还没到推盘时间，请检查。");
					return;
				} else {
					UIContext context = new UIContext(ui);
					context.put("sellProject", sellProject);
					context.put("sellProjectIdSet", idSet);
					context.put(UIContext.OWNER, SaleBalanceListUI.this);
					try {
						IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SaleBalanceEditUI.class.getName(), context, null, OprtState.ADDNEW);
						window.show();
					} catch (UIException e1) {
						this.handleException(e1);
					}
					finally{
						DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
						if (node == null) {
							return;
						}
						this.execQuery();
					}
				}

			} else {
				MsgBox.showInfo(sellProject.getName() + " 还没推盘，请检查。");
				return;
			}
		}

	}

	public void actionUnSaleBalance_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if(id==null) return;		
		try{
			SaleBalanceInfo saleBalInfo = SaleBalanceFactory.getRemoteInstance().getSaleBalanceInfo(new ObjectUuidPK(BOSUuid.read(id)));
			sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProject.getId().toString())));
			if(saleBalInfo!=null) {	
				if(saleBalInfo.getOperateType().equals(SaleBalanceTypeEnum.UnBalance)){
					MsgBox.showInfo("已经反月结，不能再反月结。");
					return;
				}
				if(sellProject.getBalanceEndDate()==null){
					MsgBox.showInfo("上次月结没有反写销售项目的月结截止日期。");
					return;
				}
				if(saleBalInfo.getStartDate()==null){
					MsgBox.showInfo("上次月结的月结起始日期更新失败。");
					return;
				}
				if(saleBalInfo.getEndDate()==null){
					MsgBox.showInfo("上次月结的月结结束日期更新失败。");
					return;
				}
				String dateStr1= FDCDateHelper.formatDate2(sellProject.getBalanceEndDate());
				String dateStr2= FDCDateHelper.formatDate2(saleBalInfo.getEndDate());
				if(!dateStr1.equals(dateStr2)){
					MsgBox.showInfo("不能反月结此月结，请先反月结     月结结束日期为  "+dateStr1+" 当期的月结。");
					return;
				}				
				if(MsgBox.showConfirm2("确认要反月结？")==MsgBox.YES){									
					SellProjectInfo sellProInfo = saleBalInfo.getSellProject();
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("*");
					sels.add("salePeriod.*");
					sels.add("saleNowPeriod.*");
					sellProInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProInfo.getId()),sels);
					companyInfo = SysContext.getSysContext().getCurrentFIUnit();
					//公司对应的当前期间
					companyCurrentPeriod = SystemStatusCtrolUtils
					.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
					//项目对应的当前期间
					PeriodInfo projectPeriod = sellProInfo.getSaleNowPeriod();
					//如果公司对应的期间大于等于项目对应的期间，那么不能进行反月结
					//期间比较
					if(companyCurrentPeriod.getPeriodNumber()>=projectPeriod.getPeriodNumber())
					{
						MsgBox.showInfo("对应财务组织在对应期间已经月结，请先在期末结账处进行反月结，再进行本操作");
						this.abort();
					}
					//如果项目的当前期间为启用期间那么也不能进行反月结
					if(sellProInfo.getSaleNowTerm().equals(sellProInfo.getSaleTerm()))
					{
						MsgBox.showInfo("当前期间为启用期间，不能进行反月结");
						this.abort();
					}
					//前一个期间
					PeriodInfo beforePeriod = PeriodUtils.getPrePeriodInfo(projectPeriod);
					if(beforePeriod!=null)
					{
						sellProInfo.setSaleNowTerm(new Integer(beforePeriod.getNumber()).toString());
						sellProInfo.setSaleNowPeriod(beforePeriod);
						SelectorItemCollection sels2 = new SelectorItemCollection();
						sels2.add("saleNowTerm");
						sels2.add("saleNowPeriod.id");
						SellProjectFactory.getRemoteInstance().updatePartial(sellProInfo, sels2);
					}
					SettleMentFacadeFactory.getRemoteInstance().dealAntiSaleBalance(new ObjectUuidPK(BOSUuid.read(id)));					
					saleBalInfo.setOperateType(SaleBalanceTypeEnum.UnBalance);
					SaleBalanceFactory.getRemoteInstance().update(new ObjectUuidPK(BOSUuid.read(id)),saleBalInfo);
					MsgBox.showInfo("反月结成功！");
					this.refreshList();
				}
			}
		}catch(Exception ee) {
			this.handleException(ee);
			this.abort();
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionSaleBalance.setVisible(true);
		this.actionUnSaleBalance.setVisible(true);
		
		this.actionSaleBalance.setEnabled(false);
		this.actionUnSaleBalance.setEnabled(false);

		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);		
		this.actionInitDataBld.setVisible(false);
		this.actionInitDataPty.setVisible(false);
		//this.actionInitDataBld.setEnabled(true);
		//this.actionInitDataPty.setEnabled(true);
		this.btnBalance.setText("月结");
		this.btnUnBalance.setText("反月结");		
	}

	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output class constructor
	 */
	public SaleBalanceListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			sellProject = (SellProjectInfo) node.getUserObject();
		}
		super.prepareUIContext(uiContext, e);
	}

	private void getAllSellProjectIds(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) thisNode.getUserObject();
			if (sellProject != null) {
				idSet.add(sellProject.getId().toString());
			}
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSellProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (node.getUserObject() instanceof SellProjectInfo) {
			sellProject = (SellProjectInfo) node.getUserObject();
			if (this.saleOrg.isIsBizUnit()) {
				this.actionSaleBalance.setEnabled(true);
				this.actionUnSaleBalance.setEnabled(true);
			}
		} else {
			this.actionSaleBalance.setEnabled(false);
			this.actionUnSaleBalance.setEnabled(false);
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			return;
		}
		if (id == null) {
			return;
		}
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			return;
		}
		if (id == null) {
			return;
		}
		super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {

		return null;
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SaleBalanceFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return SaleBalanceEditUI.class.getName();
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	
	public void actionInitDataBld_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DayInitDataBldListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	
	public void actionInitDataPty_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DayInitDataPtyListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	
	
}