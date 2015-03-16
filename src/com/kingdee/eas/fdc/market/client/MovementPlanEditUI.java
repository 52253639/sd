/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.scm.common.ExpenseItemCollection;
import com.kingdee.eas.basedata.scm.common.ExpenseItemFactory;
import com.kingdee.eas.basedata.scm.common.ExpenseItemInfo;
import com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.customerservice.client.CmsServiceDataProvider;
import com.kingdee.eas.fdc.customerservice.client.SpecialSrvDealDataProvider;
import com.kingdee.eas.fdc.market.MarketManageFactory;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.fdc.market.MediaCollection;
import com.kingdee.eas.fdc.market.MediaFactory;
import com.kingdee.eas.fdc.market.MediaInfo;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.fdc.market.MovementPlanFactory;
import com.kingdee.eas.fdc.market.MovementPlanInfo;
import com.kingdee.eas.fdc.market.app.MovementPlanDataProvider;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeCollection;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCCustomerHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
//import com.kingdee.eas.fdc.basedata.util.client.RENoteDataProvider;
/**
 * output class name
 */
public class MovementPlanEditUI extends AbstractMovementPlanEditUI {

	private static final Logger logger = CoreUIObject.getLogger(MovementPlanEditUI.class);
	private SellProjectInfo projectInfo = null;

	protected void verifyInput(ActionEvent e) throws Exception {

	}

	public boolean checkBeforeWindowClosing() {
		return true;
	}

	protected void kdtE2_tableClicked(KDTMouseEvent e) throws Exception {
		if (this.kdtE2.getRowCount() < 1) {
			return;
		}
		if (e.getColIndex() != this.kdtE2.getColumnIndex("money")) {
			return;
		}
	}

	/**
	 * output class constructor
	 */
	public MovementPlanEditUI() throws Exception {
		super();
		final KDBizPromptBox kdtE2_chargeType_PromptBox = new KDBizPromptBox();
		kdtE2_chargeType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.F7ExpenseItemQuery");
		kdtE2_chargeType_PromptBox.setVisible(true);
		kdtE2_chargeType_PromptBox.setEditable(true);
		kdtE2_chargeType_PromptBox.setDisplayFormat("$name$");
		kdtE2_chargeType_PromptBox.setEditFormat("$number$");
		kdtE2_chargeType_PromptBox.setCommitFormat("$number$");

		KDTDefaultCellEditor kdtE2_chargeType_CellEditor = new KDTDefaultCellEditor(kdtE2_chargeType_PromptBox);
		this.kdtE2.getColumn("chargeType").setEditor(kdtE2_chargeType_CellEditor);
		ObjectValueRender kdtE2_chargeType_OVR = new ObjectValueRender();
		kdtE2_chargeType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtE2.getColumn("chargeType").setRenderer(kdtE2_chargeType_OVR);

		final KDBizPromptBox kdtE4_responsiblePers_PromptBox = new KDBizPromptBox();
		kdtE4_responsiblePers_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.PersonQuery");
		kdtE4_responsiblePers_PromptBox.setVisible(true);
		kdtE4_responsiblePers_PromptBox.setEditable(true);
		kdtE4_responsiblePers_PromptBox.setDisplayFormat("$number$");
		kdtE4_responsiblePers_PromptBox.setEditFormat("$number$");
		kdtE4_responsiblePers_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtE4_responsiblePers_CellEditor = new KDTDefaultCellEditor(kdtE4_responsiblePers_PromptBox);
		this.kdtE4.getColumn("responsiblePers").setEditor(kdtE4_responsiblePers_CellEditor);
		ObjectValueRender kdtE4_responsiblePers_OVR = new ObjectValueRender();
		kdtE4_responsiblePers_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtE4.getColumn("responsiblePers").setRenderer(kdtE4_responsiblePers_OVR);

		final KDBizPromptBox kdtE6_mediaType_PromptBox = new KDBizPromptBox();
		kdtE6_mediaType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.MediaQuery");
		kdtE6_mediaType_PromptBox.setVisible(true);
		kdtE6_mediaType_PromptBox.setEditable(true);
		kdtE6_mediaType_PromptBox.setDisplayFormat("$number$");
		kdtE6_mediaType_PromptBox.setEditFormat("$number$");
		kdtE6_mediaType_PromptBox.setCommitFormat("$number$");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		kdtE6_mediaType_PromptBox.setEntityViewInfo(view);
		KDTDefaultCellEditor kdtE6_mediaType_CellEditor = new KDTDefaultCellEditor(kdtE6_mediaType_PromptBox);
		this.kdtE6.getColumn("mediaType").setEditor(kdtE6_mediaType_CellEditor);
		ObjectValueRender kdtE6_mediaType_OVR = new ObjectValueRender();
		kdtE6_mediaType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtE6.getColumn("mediaType").setRenderer(kdtE6_mediaType_OVR);

	}

	protected ExpenseItemCollection getExpenseTypeCollection() throws Exception {
		ExpenseItemCollection col = new ExpenseItemCollection();
		for (int i = 0; i < this.kdtE2.getRowCount(); i++) {
			IRow row = this.kdtE2.getRow(i);
			if (row != null && row.getCell("chargeType").getValue() != null) {
				ExpenseItemInfo info = (ExpenseItemInfo) row.getCell("chargeType").getValue();
				col.add(info);
			}
		}
		return col;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (this.isSelect.getSelectState() == 32) {
			this.charge.setLayout(new BorderLayout(0, 0));
			com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel) com.kingdee.eas.framework.client.multiDetail.HMDUtils
					.buildDetail(this, dataBinder, kdtE2, new com.kingdee.eas.fdc.market.MovementPlanE2Info(), null, false);
			this.charge.add(kdtE2_detailPanel, BorderLayout.CENTER);
		} else {
			totalMoney.setEnabled(true);
			this.charge.removeAll();
			this.charge.add(kdtE2);
		}
		initToolBar();
		initEntryE5Value();
		FDCTableHelper.enableAutoAddLine(this.kdtE2);

		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		SHEHelper.setTextFormat(this.totalMoney);
		this.schemeType.setRequired(true);
		this.belongSystem.setRequired(true);
		this.movementTheme.setRequired(true);
		this.beginDate.setRequired(true);
		this.endDate.setRequired(true);
		this.txtDescription.setMaxLength(80);
		String isCopy = (String) this.getUIContext().get("isCopy");
		if (isCopy != null && isCopy != "") {
			this.txtNumber.setText("");
			this.txtName.setText("");
			this.editData.setId(null);
			this.oprtState = OprtState.ADDNEW;
			for (int i = 0; i < this.editData.getE2().size(); i++) {
				this.editData.getE2().get(i).setId(null);
			}
			for (int i = 0; i < this.editData.getE3().size(); i++) {
				this.editData.getE3().get(i).setId(null);
			}
			for (int i = 0; i < this.editData.getE4().size(); i++) {
				this.editData.getE4().get(i).setId(null);
			}
			for (int i = 0; i < this.editData.getE5().size(); i++) {
				this.editData.getE5().get(i).setId(null);
			}
			for (int i = 0; i < this.editData.getE6().size(); i++) {
				this.editData.getE6().get(i).setId(null);
			}
		}
		initOldData(this.editData);

	}
	protected void initOldData(IObjectValue dataObject) {
		//super.initOldData(dataObject);
	}

	private void initToolBar() throws Exception {
		// totalMoney.setEnabled(true);
		this.mmType.setEnabled(false);
		// 设置按钮图标
		this.kDWorkButton1.setIcon(EASResource.getIcon("imgTbtn_userview"));
		this.kDWorkButton2.setIcon(EASResource.getIcon("imgTbtn_logoutuser"));
		// 设置按钮为可用
		this.kDWorkButton1.setEnabled(true);
		this.kDWorkButton2.setEnabled(true);
		// 设置增加分录按钮不可见
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		// 设置其余按钮不可见
		this.btnTraceUp.setVisible(false);
		this.btnTraceDown.setVisible(false);
		this.btnCreateFrom.setVisible(false);
		this.btnCreateTo.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.menuBiz.setVisible(false);// 业务
		this.menuItemCopyFrom.setVisible(false);// 编辑--复制生成
		this.menuItemCreateFrom.setVisible(false);// 编辑--关联生成
		this.btnCreateFrom.setVisible(false);// 关联生成
		this.btnWorkFlowG.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.menuTool.setVisible(false);// 工具
		this.btnMultiapprove.setVisible(false);// 多级审批不可见
		custom.removeAll();
		custom.add(kdtE3);
		custom.add(kDWorkButton1, null);
		custom.add(kDWorkButton2, null);

		this.kdtE2.getColumn("money").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kdtE2.getColumn("money").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.kdtE5.getColumn("predictValue").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.kdtE5.getColumn("predictValue").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

	}

	private void initEntryE5Value() throws Exception {
		if (kdtE5.getRowCount() > 0)
			return;
		ReceptionTypeCollection coll = ReceptionTypeFactory.getRemoteInstance().getReceptionTypeCollection();
		for (int i = 0; i < coll.size(); i++) {
			ReceptionTypeInfo info = coll.get(i);
			IRow iRow = this.kdtE5.addRow();
			iRow.getCell("predictName").setValue(info.getName());
		}
		String[] str = { "成交数", "成交金额", "收款额" };
		for (int i = 0; i < str.length; i++) {
			IRow iRow = this.kdtE5.addRow();
			iRow.getCell("predictName").setValue(str[i]);
		}
	}

	/**
	 * 
	 * 描述：装载数据，判断是新增或修改，根据关联活动方案赋值
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.bos.ui.face.IUIObject#loadFields()
	 */
	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData, this.getOprtState(), txtNumber);// 配置启用编码规则
		setTableToSumField(kdtE2, new String[] { "money" });
		if (editData != null && editData.getSellProject() != null) {
			BOSUuid projId = null;
			SellProjectInfo sellInfo = null;
			try {
				if (STATUS_ADDNEW.equals(getOprtState())) {
					sellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(projectInfo.getId()));
				} else {
					sellInfo = (SellProjectInfo) sellProject.getData();
				}
				projId = sellInfo.getOrgUnit().getId();
				FullOrgUnitInfo costOrg = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(projId));
				orgName.setText(costOrg.getDisplayName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		MovementPlanInfo info = (MovementPlanInfo) this.editData;
		if (info != null) {
			info.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		}
		super.storeFields();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (isSelect.getSelectState() != 32) {
			kdtE2.removeRows();
		}
		verifyExitNumberOrName(e);
		verifyDate(e);
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyExitNumberOrName(e);
		verifyDate(e);
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.MovementPlanFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtE2;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.market.MovementPlanEntryInfo();
	}

	/**
	 * 
	 * 描述：<方法描述>初始化表格数据行
	 * 
	 * @param <参数描述>需要初始化的表格
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	public static int[] getSelectedRows(KDTable table) {
		if (table.getSelectManager().size() == 0) {
			return new int[0];
		} else {
			TreeSet set = new TreeSet();
			for (int i = 0; i < table.getSelectManager().size(); i++) {
				IBlock block = KDTBlock.change(table, table.getSelectManager().get(i));
				for (int j = block.getTop(); j <= block.getBottom(); j++) {
					set.add(new Integer(j));
				}
			}
			int[] rows = new int[set.size()];
			Iterator iter = set.iterator();
			int k = 0;
			while (iter.hasNext()) {
				rows[k++] = ((Integer) iter.next()).intValue();
			}

			return rows;
		}
	}

	/**
	 * 
	 * 描述：删除实际参见人员按钮事件
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.market.client.AbstractMarketManageEditUI#actionDelInsider_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionDelCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelCustomer_actionPerformed(e);

		if (kdtE3.getRowCount() <= 0)
			return;
		int[] selectRows = getSelectedRows(kdtE3);
		kdtE3.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		for (int i = selectRows.length - 1; i >= 0; i--) {
			kdtE3.removeRow(selectRows[i]);
		}
		if (this.kdtE3.getRowCount() >= 0) {
			this.kdtE3.getSelectManager().select(0, 0);
		}
	}

	/**
	 * 
	 * 描述：在分录实际参加人员列表里添加客户
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.market.client.AbstractMarketManageEditUI#actionAddInsider_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddCustomer_actionPerformed(e);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery")));

		EntityViewInfo v = new EntityViewInfo();
		FilterInfo f = new FilterInfo();
		f.getFilterItems().add(new FilterItemInfo("salesman.name", SysContext.getSysContext().getCurrentUserInfo().getName()));
		v.setFilter(f);
		dlg.setEntityViewInfo(v);

		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		if (object != null && object.length > 0) {
			IRow row = null;
			List list = new ArrayList();
			for (int i = 0; i < kdtE3.getRowCount(); i++) {
				IRow row2 = kdtE3.getRow(i);
				FDCCustomerInfo info = (FDCCustomerInfo) row2.getCell("customerId").getValue();
				if (info != null) {
					list.add(info.getId().toString());
				}
			}
			for (int j = 0; j < object.length; j++) {
				FDCCustomerInfo customerInfo = (FDCCustomerInfo) object[j];
				if (TenancyClientHelper.isInclude(customerInfo.getId().toString(), list)) {
					MsgBox.showInfo("该客户已经存在不要重复添加！");
					return;
				} else {
					this.kdtE3.checkParsed();
					row = this.kdtE3.addRow(j);
					// 显示客户信息
					showTblCustomer(row, customerInfo);
				}
			}
		}

	}

	/**
	 * 
	 * 描述：<方法描述>给分录赋值
	 * 
	 * @param <参数描述>
	 * @return <返回值描述>
	 * 
	 * @author:ZhangFeng 创建时间：2009-2-20
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	private void showTblCustomer(IRow row, FDCCustomerInfo customerInfo) {
		// 这里设置客户分录里的属性
		row.getCell("customerId").setValue(customerInfo);
		row.getCell("name").setValue(customerInfo.getName());
		row.getCell("number").setValue(customerInfo.getNumber());
		row.getCell("phone").setValue(customerInfo.getPhone());
		row.getCell("sex").setValue(customerInfo.getSex());
		row.getCell("email").setValue(customerInfo.getEmail());
		row.getCell("address").setValue(customerInfo.getMailAddress());
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.MovementPlanInfo objectValue = new com.kingdee.eas.fdc.market.MovementPlanInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setBizDate(SHEHelper.getServerDate2());
		projectInfo = (SellProjectInfo) getUIContext().get("project");
		objectValue.setSellProject(projectInfo);
		MarketTypeInfo marketTypeInfo = (MarketTypeInfo) getUIContext().get("marketType");
		objectValue.setMmType(marketTypeInfo);
		this.paneBIZLayerControl20.setEnabledAt(0, false);
		return objectValue;
	}

	/**
	 * 描述： 数据保存之前, 校验数据合法性
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.framework.client.EditUI#verifyInput(java.awt.event.ActionEvent)
	 */
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
	}

	protected void verifyExitNumberOrName(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(this.txtNumber.getText())) {
			com.kingdee.eas.util.client.MsgBox.showWarning("活动方案编号不能为空 ！");
			this.abort();
		}
		verifyInputNotNull(txtName, "活动方案名称不能为空 ！");
		verifyInputNotNull(movementTheme, "活动方案主题不能为空 ！");
		verifyKDComboBoxInputNotNull(schemeType, "方案类型不能为空 ！");
		verifyKDComboBoxInputNotNull(belongSystem, "所属系统不能为空 ！");

		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", this.txtNumber.getText().trim()));
			if (MovementPlanFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showWarning("活动方案编号不能重复！");
				SysUtil.abort();
			}
			FilterInfo nameFilter = new FilterInfo();
			nameFilter.getFilterItems().add(new FilterItemInfo("name", this.txtName.getText().trim()));
			if (MovementPlanFactory.getRemoteInstance().exists(nameFilter)) {
				MsgBox.showWarning("活动方案名称不能重复！");
				SysUtil.abort();
			}
		}

	}

	protected void verifyDate(ActionEvent e) throws Exception {
		Date startDate = (Date) this.beginDate.getValue();
		if (startDate == null) {
			MsgBox.showWarning("开始日期不能为空！");
			SysUtil.abort();
		}
		Date endDate = (Date) this.endDate.getValue();
		if (endDate == null) {
			MsgBox.showWarning("结束日期不能为空！");
			SysUtil.abort();
		}
		if (startDate != null && endDate != null) {
			if (endDate.before(startDate)) {
				MsgBox.showWarning("结束日期不能小于开始日期！");
				SysUtil.abort();
			}
			// Date todayDate = new Date();
			// if (!todayDate.before(endDate)) {
			// MsgBox.showWarning("结束日期不能小于当前日期！");
			// SysUtil.abort();
			// }
		}
	}

	private void verifyInputNotNull(KDTextField txt, String msg) {
		if (!FDCCustomerHelper.verifyInputNotNull(txt, msg)) {
			this.abort();
		}

	}

	private void verifyKDComboBoxInputNotNull(KDComboBox f7box, String msg) {
		Object obj = f7box.getSelectedItem();
		if (obj == null) {
			MsgBox.showWarning(this, msg);
			SysUtil.abort();
		}
	}

	private void verifyF7InputNotNull(KDBizPromptBox f7box, String msg) {
		Object obj = f7box.getValue();
		if (obj == null) {
			MsgBox.showWarning(this, msg);
			SysUtil.abort();
		}
	}

	/**
	 * 
	 * 描述：是否明细费用事件
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.market.client.AbstractMovementPlanEditUI#isSelect_actionPerformed(java.awt.event.ActionEvent)
	 */
	protected void isSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception {

		super.isSelect_actionPerformed(e);

		if (isSelect.getSelectState() == 32) {
			this.paneBIZLayerControl20.setEnabledAt(0, true);
			charge.setLayout(new BorderLayout(0, 0));
			com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel) com.kingdee.eas.framework.client.multiDetail.HMDUtils
					.buildDetail(this, dataBinder, kdtE2, new com.kingdee.eas.fdc.market.MovementPlanE2Info(), null, false);
			charge.add(kdtE2_detailPanel, BorderLayout.CENTER);
			this.totalMoney.setEditable(false);
		} else {
			totalMoney.setEnabled(true);
			this.paneBIZLayerControl20.setEnabledAt(0, false);
			this.charge.removeAll();
			this.charge.add(kdtE2);
			this.totalMoney.setEditable(true);
		}
	}

	public void kdtE2_Changed(int rowIndex, int colIndex) throws Exception {
		if ("chargeType".equalsIgnoreCase(kdtE2.getColumn(colIndex).getKey())) {
			IRow row = this.kdtE2.getRow(rowIndex);
			ExpenseItemInfo info = (ExpenseItemInfo) row.getCell("chargeType").getValue();
			if (info == null || info.getId() == null) {
				return;
			}
			for (int i = 0; i < kdtE2.getRowCount(); i++) {
				if (i != rowIndex) {
					if (this.kdtE2.getRow(i).getCell("chargeType").getValue() instanceof ExpenseItemInfo) {
						ExpenseItemInfo chargeItemInfo = (ExpenseItemInfo) this.kdtE2.getRow(i).getCell("chargeType").getValue();
						if (info.getId().equals(chargeItemInfo.getId())) {
							row.getCell("chargeType").setValue(null);
							MsgBox.showInfo("该费用已添加，不能重复添加。");
							return;
						}
					}
				}
			}
			if (info != null) {
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("*");
				sel.add("expenseType.*");
				info = ExpenseItemFactory.getRemoteInstance().getExpenseItemInfo(new ObjectUuidPK(BOSUuid.read(info.getId().toString())), sel);
				ExpenseTypeInfo chargeTypeInfo = info.getExpenseType();
				if (chargeTypeInfo != null) {
					row.getCell("chargeParent").setValue(chargeTypeInfo.getName());
				}

			}
		}
	}

	protected MediaCollection getMediaCollection() throws Exception {
		MediaCollection col = new MediaCollection();
		for (int i = 0; i < this.kdtE6.getRowCount(); i++) {
			IRow row = this.kdtE6.getRow(i);
			if (row != null && row.getCell("mediaType").getValue() != null) {
				MediaInfo info = (MediaInfo) row.getCell("mediaType").getValue();
				col.add(info);
			}
		}
		return col;
	}

	protected void kdtE6_editStopped(KDTEditEvent e) throws Exception {
		if (this.kdtE6.getRowCount() < 1) {
			return;
		}
		super.kdtE6_editStopped(e);
		IRow row = this.kdtE6.getRow(e.getRowIndex());
		if (e.getColIndex() == this.kdtE6.getColumnIndex("mediaType")) {
			MediaInfo info = (MediaInfo) row.getCell("mediaType").getValue();
			if (info == null || info.getId() == null) {
				return;
			}
			for (int i = 0; i < kdtE6.getRowCount(); i++) {
				if (i != e.getRowIndex()) {
					if (this.kdtE6.getRow(i).getCell("mediaType").getValue() instanceof MediaInfo) {
						MediaInfo media = (MediaInfo) this.kdtE6.getRow(i).getCell("mediaType").getValue();
						if (media.getId().equals(info.getId())) {
							row.getCell("mediaType").setValue(null);
							MsgBox.showInfo("该媒体已是合作媒体，不能重复添加。");
							return;
						}
					}
				}
			}

			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			sel.add("mediaType.*");
			info = MediaFactory.getRemoteInstance().getMediaInfo(new ObjectUuidPK(BOSUuid.read(info.getId().toString())), sel);
			MediaTypeInfo typeInfo = info.getMediaType();
			if (typeInfo != null) {
				row.getCell("mediaCategory").setValue(typeInfo.getName());
			}
			row.getCell("contractMan").setValue(info.getContractMan());
			row.getCell("prometionInfo").setValue(info.getPreferentialMsg());
		}
	}

	protected void kdtE2_editStopped(KDTEditEvent e) throws Exception {
		if (this.kdtE2.getRowCount() < 1) {
			return;
		}
		super.kdtE2_editStopped(e);	
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    } 
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()!=null){
    		String id = this.editData.getId().toString();
        	MovementPlanInfo movementPlanInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(id)));
    		FDCBillStateEnum billState = movementPlanInfo.getState();
    		if (FDCBillStateEnum.AUDITTED.equals(billState)) {
    			MsgBox.showInfo("该单据已经是审核状态，不能修改！");
    			return;
    		}
    	}
    	super.actionEdit_actionPerformed(e);
    }
    
    
	public void actionPrintPreview_actionPerformed(ActionEvent e)throws Exception {
		if (this.editData == null || this.editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印！");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
//			List idList = new ArrayList();
//			idList.add(receId);
			
/**			
			//支持审批流中的套打功能
			RENoteDataProvider data = new RENoteDataProvider(receId);
//			MovementPlanDataProvider data = new MovementPlanDataProvider(idList,new MetaDataPK("com.kingdee.eas.fdc.market.app.MovementPlanQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/market/movementPlan",
					data, javax.swing.SwingUtilities.getWindowAncestor(this));
//			super.actionPrintPreview_actionPerformed(e);
 */
		}
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
//			List idList = new ArrayList();
//			idList.add(receId);
/**			
			RENoteDataProvider data = new RENoteDataProvider(receId);
//			MovementPlanDataProvider data = new MovementPlanDataProvider(idList,new MetaDataPK("com.kingdee.eas.fdc.market.app.MovementPlanQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/market/movementPlan",
					data, javax.swing.SwingUtilities.getWindowAncestor(this));
//			super.actionPrint_actionPerformed(e);

 */
		}
	}
}