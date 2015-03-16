/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermItemCollection;
import com.kingdee.eas.base.permission.PermItemFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.client.ContractCostSplitListUI;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitFactory;
import com.kingdee.eas.fdc.finance.IFDCProDepSplit;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FDCProDepSplitListUI extends AbstractFDCProDepSplitListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCProDepSplitListUI.class);

	private Set authorizedOrgs = null;

	/**
	 * output class constructor
	 */
	public FDCProDepSplitListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void loadFields() {
		// TODO Auto-generated method stub
		super.loadFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUIStatus();
	}

	public void onShow() throws Exception {
		super.onShow();
		drawColorPanel();
	}

	/**
	 * 绘制颜色板
	 * 
	 * @author sxhong Date 2006-11-23
	 */
	protected void drawColorPanel() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(10);
		// 将每个颜色放到一个Lable内
		pnlColor.setLayout(flowLayout);
		drawALogo("全部拆分", Color.YELLOW);
		drawALogo("未拆分", Color.WHITE);
		drawALogo("原始单据重新汇总", Color.RED);
	}

	/**
	 * 绘制颜色板内的一个Logo
	 * 
	 * @author sxhong Date 2006-11-23
	 * @param name
	 * @param color
	 */
	protected void drawALogo(String name, Color color) {
		if (color == null) {
			color = pnlColor.getBackground();
		}
		KDLabel lable = new KDLabel(name);
		KDLabel colorLable = new KDLabel();
		Dimension d = new Dimension(40, 10);
		colorLable.setPreferredSize(d);
		colorLable.setOpaque(true);
		colorLable.setBackground(color);
		pnlColor.add(lable);
		pnlColor.add(colorLable);

	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// super.prepareUIContext(uiContext, e);
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		uiContext.put("ID", row.getCell("id").getValue());
		uiContext.put("planID", row.getCell("fdcProDep.id").getValue());
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
	}

	/**
	 * output treeProject_valueChanged method
	 */
	protected void treeProject_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeProject_valueChanged(e);
	}

	protected void checkBeforeAddNew(ActionEvent e) {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		String planId = (String) row.getCell("fdcProDep.id").getValue();
		try {
			boolean isConAllSplit = ((IFDCProDepSplit) getBizInterface())
					.isConAllSplit(planId);
			if (!isConAllSplit) {
				MsgBox.showWarning(this, "付款计划中存在未完全拆分的合同，不能进行计划拆分！");
				abort();
			}
		} catch (BOSException e1) {
			handUIException(e1);
			abort();
		} catch (Exception e1) {
			handUIException(e1);
			abort();
		}
	}

	protected FilterInfo getTreeFilter() throws Exception {

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		// 获得当前用户下的组织范围ids
		authorizedOrgs = (Set) ActionCache
				.get("ContractListBaseUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					new ObjectUuidPK(SysContext.getSysContext()
							.getCurrentUserInfo().getId()), OrgType.CostCenter,
					null, null, null);
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
		}
		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();
				String orgUnitLongNumber = null;
				if (orgUnit != null
						&& id.toString().equals(orgUnit.getId().toString())) {
					orgUnitLongNumber = orgUnit.getLongNumber();
				} else {
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory
							.getRemoteInstance().getFullOrgUnitInfo(
									new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				filterItems.add(new FilterItemInfo("orgUnit.longNumber",
						orgUnitLongNumber + "%", CompareType.LIKE));
				filterItems.add(new FilterItemInfo("orgUnit.isCostOrgUnit",
						Boolean.TRUE));
				filterItems.add(new FilterItemInfo("orgUnit.id",
						authorizedOrgs, CompareType.INCLUDE));
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}
		}
		// 是最新计划，则不能是被打回、修订中、已修订状态
		FilterInfo stateFilter = new FilterInfo();
		// stateFilter.getFilterItems().add(
		// new FilterItemInfo("fdcProDep.state",
		// FDCBillStateEnum.BACK_VALUE, CompareType.NOTEQUALS));
		// stateFilter.getFilterItems()
		// .add(
		// new FilterItemInfo("fdcProDep.state",
		// FDCBillStateEnum.REVISING_VALUE,
		// CompareType.NOTEQUALS));
		// stateFilter.getFilterItems().add(
		// new FilterItemInfo("fdcProDep.state",
		// FDCBillStateEnum.REVISE_VALUE, CompareType.NOTEQUALS));
		// 原始单据是审批状态
		stateFilter.getFilterItems().add(
				new FilterItemInfo("fdcProDep.state",
						FDCBillStateEnum.AUDITTED_VALUE));
		stateFilter.getFilterItems().add(
				new FilterItemInfo("fdcProDep.state",
						FDCBillStateEnum.PUBLISH_VALUE));
		stateFilter.setMaskString("#0 or #1");
		filter.mergeFilter(stateFilter, "AND");
		return filter;
	}

	protected void execQuery() {
		super.execQuery();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			Boolean isReSum = (Boolean) row.getCell("isReSum").getValue();
			BizEnumValueDTO fde = (BizEnumValueDTO) row.getCell("splitState")
					.getValue();
			if (!CostSplitStateEnum.NOSPLIT_VALUE.equals(fde.getValue())
					&& isReSum != null && isReSum.booleanValue()) {
				row.getStyleAttributes().setBackground(Color.RED);
			} else {
				if (CostSplitStateEnum.ALLSPLIT_VALUE.equals(fde.getValue())) {
					row.getStyleAttributes().setBackground(Color.YELLOW);
				}
			}
		}
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row.getCell("id").getValue() == null) {
			MsgBox.showWarning(this, "计划未拆分，不能查看");
			SysUtil.abort();
		}

		String planId = (String) row.getCell("fdcProDep.id").getValue();
		if (planId == null) {
			MsgBox.showWarning(this, "拆分的计划不存在");
		} else {
			if (checkIsChanged(planId)) {
				int slt = MsgBox
						.showConfirm2(this, "所拆分的项目滚动付款计划已经重新汇总，需要重新拆分");
				if (slt == MsgBox.OK) {
					String id = (String) row.getCell("id").getValue();
					reSplit(BOSUuid.read(id));
				}
			} else {
				super.actionView_actionPerformed(e);
			}
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row.getCell("id").getValue() == null) {
			MsgBox.showWarning(this, "计划未拆分，不能修改");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row.getCell("id").getValue() == null) {
			MsgBox.showWarning(this, "计划未拆分，不能删除");
			SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row.getCell("id").getValue() == null) {
			MsgBox.showWarning(this, "计划未拆分，不能查看附件");
			SysUtil.abort();
		}
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// // 方法一，旧模式
		// // 查找权限项
		// PermItemCollection piCol = PermItemFactory
		// .getRemoteInstance()
		// .getPermItemCollection("select id where objectType is not null");
		// if (piCol != null && piCol.size() > 0) {
		// Set idSet = new HashSet();
		// int size = piCol.size() > 10000 ? 10000 : piCol.size();
		// for (int i = 0; i < size; i++) {
		// idSet.add(piCol.get(i).getId().toString());
		// }
		// // 查找绑定了权限项的菜单，输出到控制台
		// FDCSQLBuilder builder = new FDCSQLBuilder();
		// builder.appendSql("select * from T_BAS_SysMenuItem where ");
		// // 使用添加参数模式
		// builder.appendParam("FFunction", idSet.toArray());
		// IRowSet rs = builder.executeQuery();
		// while (rs.next()) {
		// System.out.println(rs.getString("FID"));
		// }
		// }

		// 方法二，使用in select 模式
		// Date begin = new Date();
		// FDCSQLBuilder builder = new FDCSQLBuilder();
		//builder.appendSql("select * from T_BAS_SysMenuItem where FFunction in "
		// );
		// // 将参数替换成select语句
		// builder.appendSql(
		// "(select Fid from T_PM_PermItem where FObjectType is not null) ");
		// IRowSet rs = builder.executeQuery();
		// Date end = new Date();
		// System.out.println("用时"+(end.getTime()-begin.getTime())+"毫秒");
		// while (rs.next()) {
		// System.out.println(rs.getString("FID"));
		// }
		super.actionUnAudit_actionPerformed(e);
	}
	
	/**
	 * 未拆分不存在id，不能进工作流
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		if (id == null) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotInstance"));
		} else {
			super.actionViewDoProccess_actionPerformed(e);
		}
	}

	protected String getEditUIName() {
		return FDCProDepSplitEditUI.class.getName();
	}

	protected String getBillStatePropertyName() {
		return "state";
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCProDepSplitFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {
		((IFDCProDepSplit) getRemoteInterface()).audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		((IFDCProDepSplit) getRemoteInterface()).unAudit(ids);
	}

	// 业务系统可重载实现下面方法，返回合适的字符串数组（表格列名），来达到定位的目的。
	protected String[] getLocateNames() {
		String[] locateNames = new String[2];
		locateNames[0] = "fdcProDep.number";
		locateNames[1] = "fdcProDep.name";
		return locateNames;
	}

	private void initUIStatus() {
		actionAddNew.setVisible(false);
		actionEdit.setVisible(false);
		actionSplit.setEnabled(true);
		btnAuditResult.setVisible(false);
	}

	protected void updateButtonStatus() {
		// 如果是虚体成本中心，则不能增、删、改
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			// actionRemove.setEnabled(false);
			// actionRemove.setVisible(false);
			// menuEdit.setVisible(false);
		}
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			// 审批反审批
			BizEnumValueDTO state = (BizEnumValueDTO) row.getCell("state")
					.getValue();
			if (state != null
					&& state.getValue()
							.equals(FDCBillStateEnum.SUBMITTED_VALUE)) {
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
				actionRemove.setEnabled(true);
				actionSplit.setEnabled(true);
			} else if (state != null
					&& state.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
				actionRemove.setEnabled(false);
				actionSplit.setEnabled(false);
			} else if (state != null
					&& state.getValue().equals(FDCBillStateEnum.SAVED_VALUE)) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(false);
				actionRemove.setEnabled(true);
				actionSplit.setEnabled(true);
			} else {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(false);
				actionRemove.setEnabled(false);
				actionSplit.setEnabled(true);
			}
			// 删除修改

		}
	}

	/**
	 * 先判断是否拆分过<br>
	 * 未拆分则新增<br>
	 * 拆分过则判断被拆分的计划是否重新汇总过<br>
	 * 汇总过则重新新增<br>
	 * 未汇总过则修改
	 */
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row.getCell("id").getValue() == null) {
			actionAddNew_actionPerformed(null);
		} else {
			String planId = (String) row.getCell("fdcProDep.id").getValue();
			if (planId == null) {
				MsgBox.showWarning(this, "拆分的计划不存在");
			} else {
				if (checkIsChanged(planId)) {
					int slt = MsgBox.showConfirm2(this,
							"所拆分的项目滚动付款计划已经重新汇总，需要重新拆分");
					if (slt == 0) {
						String id = (String) row.getCell("id").getValue();
						reSplit(BOSUuid.read(id));
					}
				} else {
					actionEdit_actionPerformed(null);
				}
			}
		}
	}

	/**
	 * 检查对应的项目滚动付款计划是否重新汇总过<br>
	 * 如已经重新汇总过，需要重新拆分
	 * 
	 * @param planId
	 */
	private boolean checkIsChanged(String planId) {
		boolean isChanged = false;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("isReSum");
		try {
			FDCProDepConPayPlanInfo plan = FDCProDepConPayPlanFactory
					.getRemoteInstance().getFDCProDepConPayPlanInfo(
							new ObjectUuidPK(planId), sic);
			isChanged = plan.isIsReSum();
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
		return isChanged;
	}

	/**
	 * 项目滚动计划重新汇总过时，进行重新拆分<br>
	 * 先删除原拆分<br>
	 * 再将项目滚动计划设为未重新汇总状态<br>
	 * 然后新增拆分
	 * 
	 * @param id
	 * @param planId
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws Exception
	 */
	private void reSplit(BOSUuid id) throws EASBizException, BOSException,
			Exception {
		((IFDCProDepSplit) getBizInterface()).reSplit(id);
		// 新增拆分
		actionAddNew_actionPerformed(null);
	}

}