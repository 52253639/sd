/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.Action;
import javax.swing.JButton;

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
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.person.IPerson;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.scm.common.ExpenseItemCollection;
import com.kingdee.eas.basedata.scm.common.ExpenseItemFactory;
import com.kingdee.eas.basedata.scm.common.ExpenseItemInfo;
import com.kingdee.eas.basedata.scm.common.ExpenseTypeFactory;
import com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo;
import com.kingdee.eas.basedata.scm.common.IExpenseType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.market.MarketChargePaymentEntryFactory;
import com.kingdee.eas.fdc.market.MarketManageChargeCollection;
import com.kingdee.eas.fdc.market.MarketManageChargeInfo;
import com.kingdee.eas.fdc.market.MarketManageCustomerCollection;
import com.kingdee.eas.fdc.market.MarketManageCustomerInfo;
import com.kingdee.eas.fdc.market.MarketManageEffectCollection;
import com.kingdee.eas.fdc.market.MarketManageEffectInfo;
import com.kingdee.eas.fdc.market.MarketManageEntryCollection;
import com.kingdee.eas.fdc.market.MarketManageEntryInfo;
import com.kingdee.eas.fdc.market.MarketManageInfo;
import com.kingdee.eas.fdc.market.MarketManageMediaCollection;
import com.kingdee.eas.fdc.market.MarketManageMediaInfo;
import com.kingdee.eas.fdc.market.MarketTypeFactory;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.fdc.market.MediaCollection;
import com.kingdee.eas.fdc.market.MediaFactory;
import com.kingdee.eas.fdc.market.MediaInfo;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.fdc.market.MovementPlanE2Collection;
import com.kingdee.eas.fdc.market.MovementPlanE2Info;
import com.kingdee.eas.fdc.market.MovementPlanE3Collection;
import com.kingdee.eas.fdc.market.MovementPlanE3Info;
import com.kingdee.eas.fdc.market.MovementPlanE4Collection;
import com.kingdee.eas.fdc.market.MovementPlanE4Info;
import com.kingdee.eas.fdc.market.MovementPlanE5Collection;
import com.kingdee.eas.fdc.market.MovementPlanE5Factory;
import com.kingdee.eas.fdc.market.MovementPlanE5Info;
import com.kingdee.eas.fdc.market.MovementPlanE6Collection;
import com.kingdee.eas.fdc.market.MovementPlanE6Info;
import com.kingdee.eas.fdc.market.MovementPlanFactory;
import com.kingdee.eas.fdc.market.MovementPlanInfo;
import com.kingdee.eas.fdc.market.SchemeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeCollection;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCCustomerHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketManageEditUI extends AbstractMarketManageEditUI {

	private static final Logger logger = CoreUIObject.getLogger(MarketManageEditUI.class);

	private SellProjectInfo projectInfo = null;

	private MarketTypeInfo marketTypeInfo = null;

	private CoreBaseCollection corChargePayColl = null;

	private MarketManageEntryCollection entryCol = null;

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		if (this.kdtEntrys.getRowCount() < 1) {
			return;
		}
		super.kdtEntrys_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		if (rowIndex < 0) {
			return;
		}
		if (colIndex < 0) {
			return;
		}
		IRow row = this.kdtEntrys.getRow(rowIndex);
		if (colIndex == row.getCell("costNumber").getColumnIndex()) {
			ExpenseItemInfo chargeItemInfo = (ExpenseItemInfo) row.getCell("costNumber").getValue();
			if (chargeItemInfo == null || chargeItemInfo.getId() == null) {
				return;
			}
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				if (i != e.getRowIndex()) {
					if (this.kdtEntrys.getRow(i).getCell("costNumber").getValue() instanceof ExpenseItemInfo) {
						ExpenseItemInfo media = (ExpenseItemInfo) this.kdtEntrys.getRow(i).getCell("costNumber").getValue();
						if (media.getId().equals(chargeItemInfo.getId())) {
							row.getCell("costNumber").setValue(null);
							MsgBox.showInfo("该费用已添加，不能重复添加。");
							return;
						}
					}
				}
			}
			if (chargeItemInfo != null) {
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("*");
				sel.add("expenseType.*");
				chargeItemInfo = ExpenseItemFactory.getRemoteInstance().getExpenseItemInfo(new ObjectUuidPK(BOSUuid.read(chargeItemInfo.getId().toString())), sel);
				ExpenseTypeInfo chargeTypeInfo = chargeItemInfo.getExpenseType();
				if (chargeTypeInfo != null) {
					row.getCell("costName").setValue(chargeTypeInfo.getName());
				}
			}
		}

	}

	public boolean checkBeforeWindowClosing() {
		return true;
	}

	protected void kdtMedia_editStopped(KDTEditEvent e) throws Exception {
		if (this.kdtMedia.getRowCount() < 1) {
			return;
		}
		super.kdtMedia_editStopped(e);
		IRow row = this.kdtMedia.getRow(e.getRowIndex());
		if (e.getColIndex() == this.kdtMedia.getColumnIndex("mediaType")) {
			MediaInfo info = (MediaInfo) row.getCell("mediaType").getValue();
			if (info == null || info.getId() == null) {
				return;
			}
			for (int i = 0; i < kdtMedia.getRowCount(); i++) {
				if (i != e.getRowIndex()) {
					if (this.kdtMedia.getRow(i).getCell("mediaType").getValue() instanceof MediaInfo) {
						MediaInfo media = (MediaInfo) this.kdtMedia.getRow(i).getCell("mediaType").getValue();
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

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("*");

		sel.add("entrys.*");
		sel.add("entrys.costNumber.*");
		sel.add("entrys.costNumber.expenseType.*");

		sel.add("marketPlan.number");
		sel.add("marketPlan.name");

		sel.add("sellProject.number");
		sel.add("sellProject.name");

		sel.add("markettype.number");
		sel.add("markettype.name");

		sel.add("media.number");
		sel.add("media.name");

		sel.add("Charge.*");
		sel.add("Charge.customer.*");

		sel.add("Customer.*");
		sel.add("Customer.fdcCustomer.*");

		sel.add("Effect.*");

		sel.add("Media.*");
		sel.add("Media.mediaType.*");
		sel.add("Media.mediaType.mediaType.*");

		return sel;
	}

	/**
	 * output class constructor
	 */
	public MarketManageEditUI() throws Exception {
		super();
		final KDBizPromptBox kdtE2_chargeType_PromptBox = new KDBizPromptBox();
		kdtE2_chargeType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.F7ExpenseItemQuery");
		kdtE2_chargeType_PromptBox.setVisible(true);
		kdtE2_chargeType_PromptBox.setEditable(true);
		kdtE2_chargeType_PromptBox.setDisplayFormat("$name$");
		kdtE2_chargeType_PromptBox.setEditFormat("$number$");
		kdtE2_chargeType_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtE2_chargeType_CellEditor = new KDTDefaultCellEditor(kdtE2_chargeType_PromptBox);
		this.kdtEntrys.getColumn("costNumber").setEditor(kdtE2_chargeType_CellEditor);
		ObjectValueRender kdtE2_chargeType_OVR = new ObjectValueRender();
		kdtE2_chargeType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEntrys.getColumn("costNumber").setRenderer(kdtE2_chargeType_OVR);

		final KDBizPromptBox kdtCharge_customer_PromptBox = new KDBizPromptBox();
		kdtCharge_customer_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.PersonQuery");
		kdtCharge_customer_PromptBox.setVisible(true);
		kdtCharge_customer_PromptBox.setEditable(true);
		kdtCharge_customer_PromptBox.setDisplayFormat("$name$");
		kdtCharge_customer_PromptBox.setEditFormat("$number$");
		kdtCharge_customer_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtCharge_customer_CellEditor = new KDTDefaultCellEditor(kdtCharge_customer_PromptBox);
		this.kdtCharge.getColumn("customer").setEditor(kdtCharge_customer_CellEditor);
		ObjectValueRender kdtCharge_customer_OVR = new ObjectValueRender();
		kdtCharge_customer_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtCharge.getColumn("customer").setRenderer(kdtCharge_customer_OVR);
		this.prmtmarketPlan.setEntityViewInfo(new EntityViewInfo("Where state = '4AUDITTED'"));

		final KDBizPromptBox kdtE6_mediaType_PromptBox = new KDBizPromptBox();
		kdtE6_mediaType_PromptBox.setQueryInfo("com.kingdee.eas.fdc.market.app.MediaQuery");
		kdtE6_mediaType_PromptBox.setVisible(true);
		kdtE6_mediaType_PromptBox.setEditable(true);
		kdtE6_mediaType_PromptBox.setDisplayFormat("$name$");
		kdtE6_mediaType_PromptBox.setEditFormat("$number$");
		kdtE6_mediaType_PromptBox.setCommitFormat("$number$");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		kdtE6_mediaType_PromptBox.setEntityViewInfo(view);
		KDTDefaultCellEditor kdtE6_mediaType_CellEditor = new KDTDefaultCellEditor(kdtE6_mediaType_PromptBox);
		this.kdtMedia.getColumn("mediaType").setEditor(kdtE6_mediaType_CellEditor);
		ObjectValueRender kdtE6_mediaType_OVR = new ObjectValueRender();
		kdtE6_mediaType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtMedia.getColumn("mediaType").setRenderer(kdtE6_mediaType_OVR);

	}

	public void actionAddMedia_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddMedia_actionPerformed(e);
		this.kdtMedia.addRow();
	}

	public void actionDelMedia_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelMedia_actionPerformed(e);
		if (kdtMedia.getRowCount() <= 0) {
			MsgBox.showInfo("没有分录！");
			return;
		}
		int[] selectRows = getSelectedRows(kdtMedia);
		kdtMedia.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		if (selectRows.length < 1) {
			MsgBox.showInfo("没有选中分录,无法删除！");
			return;
		}
		if (MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Delete")))) {
			for (int i = selectRows.length - 1; i >= 0; i--) {
				kdtMedia.removeRow(selectRows[i]);
			}
			if (this.kdtMedia.getRowCount() >= 0) {
				this.kdtMedia.getSelectManager().select(0, 0);
			}
		}

	}

	public void actionAddCharge_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddCharge_actionPerformed(e);
		this.kdtCharge.addRow();
	}

	public void actionDelCharge_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelCharge_actionPerformed(e);
		if (kdtCharge.getRowCount() <= 0) {
			MsgBox.showInfo("没有分录！");
			return;
		}
		int[] selectRows = getSelectedRows(kdtCharge);
		kdtCharge.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		if (selectRows.length < 1) {
			MsgBox.showInfo("没有选中分录,无法删除！");
			return;
		}
		if (MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Delete")))) {
			for (int i = selectRows.length - 1; i >= 0; i--) {
				kdtCharge.removeRow(selectRows[i]);
			}
			if (this.kdtCharge.getRowCount() >= 0) {
				this.kdtCharge.getSelectManager().select(0, 0);
			}
		}

	}

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
			for (int i = 0; i < kdtCustomer.getRowCount(); i++) {
				IRow row2 = kdtCustomer.getRow(i);
				FDCCustomerInfo info = (FDCCustomerInfo) row2.getCell("fdcCustomer").getUserObject();
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
					this.kdtCustomer.checkParsed();
					row = this.kdtCustomer.addRow(j);
					// 显示客户信息
					showTblCustomer(row, customerInfo);
				}
			}
		}

	}

	public void actionDelCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelCustomer_actionPerformed(e);
		if (kdtCustomer.getRowCount() <= 0) {
			MsgBox.showInfo("没有分录！");
			return;
		}
		int[] selectRows = getSelectedRows(kdtCustomer);
		kdtCustomer.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		if (selectRows.length < 1) {
			MsgBox.showInfo("没有选中分录,无法删除！");
			return;
		}
		if (MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Delete")))) {
			for (int i = selectRows.length - 1; i >= 0; i--) {
				kdtCustomer.removeRow(selectRows[i]);
			}
			if (this.kdtCustomer.getRowCount() >= 0) {
				this.kdtCustomer.getSelectManager().select(0, 0);
			}
		}
	}

	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = getSelectedRows(this.kdtEntrys);
		for (int i = selectRows.length - 1; i >= 0; i--) {
			IRow row = kdtEntrys.getRow(selectRows[i]);
			if (row.getUserObject() instanceof MarketManageEntryInfo) {
				MsgBox.showInfo("已预算费用不能删除");
				return;
			}
		}
		super.actionRemoveLine_actionPerformed(e);
	}

	public void actionPayment_actionPerformed(ActionEvent e) throws Exception {
		super.actionPayment_actionPerformed(e);
		MarketManageInfo info = (MarketManageInfo) this.editData;
		if (info == null) {
			return;
		}
		try {
			UIContext uiContext = new UIContext(ui);
			getEntysBeforePayment();
			MarketManageEntryCollection chargeCol = entryCol;
			uiContext.put("chargeCol", chargeCol);
			uiContext.put(UIContext.OWNER, this);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MarketChargePaymentListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
			Map uiMap = uiWindow.getUIObject().getUIContext();
			if (uiMap != null) {
				corChargePayColl.clear();
				MarketManageEntryCollection chargePayCol = (MarketManageEntryCollection) uiMap.get("payCol");
				corChargePayColl = (CoreBaseCollection) uiMap.get("corChargePayColl");
				if (chargePayCol != null && chargePayCol.size() > 0) {
					loadEntrys(chargePayCol);
				}
			}

		} catch (UIException ex) {
			handleException(ex);
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
		// 设置需要统计的的列
		setTableToSumField(this.kdtEntrys, new String[] { "butgetAmount", "amount", "payment" });
		if (editData != null && editData.getSellProject() != null) {
			BOSUuid projId = null;
			SellProjectInfo sellInfo = null;
			try {
				if (STATUS_ADDNEW.equals(getOprtState())) {
					sellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(projectInfo.getId()));
				} else {
					sellInfo = (SellProjectInfo) prmtsellProject.getData();
				}
				projId = sellInfo.getOrgUnit().getId();
				FullOrgUnitInfo costOrg = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(projId));
				txtorgName.setText(costOrg.getDisplayName());
				kdtCustomer.getBody().removeRows();
				BOSUuid marketID = editData.getId();
				EntityViewInfo view = new EntityViewInfo(" Where marketManage = '" + marketID + "'");
				view.setSelector(getCustomerSelectors());
				TrackRecordCollection coll = TrackRecordFactory.getRemoteInstance().getTrackRecordCollection(view);
				setKdtCustomer(coll);
				MarketManageInfo info = (MarketManageInfo) this.editData;
				if (info != null) {
					loadEntrys(info.getEntrys());
					loadCharges(info.getCharge());
					loadCustomers(info.getCustomer());
					loadMedia(info.getMedia());
					this.setkdtEffect(editData.getMarketPlan()); // 给页签实际效果赋值
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fillActPaymentEffect();
				fillDealAmountEffect();
			} catch (BOSException e) {
				handleException(e);
				e.printStackTrace();
			} catch (SQLException e) {
				handleException(e);
				e.printStackTrace();
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
		}
	}

	private void loadMedia(MarketManageMediaCollection medias) {

		this.kdtMedia.removeRows();
		this.kdtMedia.checkParsed();
		for (int i = 0; i < medias.size(); i++) {
			MarketManageMediaInfo info = medias.get(i);
			if (info != null) {
				IRow row = kdtMedia.addRow();
				row.setUserObject(info);
				MediaInfo mediaInfo = info.getMediaType();
				if (mediaInfo != null) {
					row.getCell("mediaType").setValue(mediaInfo);
					row.getCell("contractMan").setValue(mediaInfo.getContractMan());
					row.getCell("prometionInfo").setValue(mediaInfo.getPreferentialMsg());
					MediaTypeInfo mtInfo = mediaInfo.getMediaType();
					if (mtInfo != null) {
						row.getCell("mediaCategory").setValue(mtInfo.getName());
					}
				}
			}
		}
		if (medias.size() > 0) {
			this.kdtMedia.setRowCount(medias.size());
		}

	}

	private void loadEffects(MarketManageEffectCollection effects) {
		this.kdtEffect.removeRows();
		this.kdtEffect.checkParsed();
		for (int i = 0; i < effects.size(); i++) {
			MarketManageEffectInfo info = effects.get(i);
			if (info != null) {
				IRow row = this.kdtEffect.addRow();
				row.setUserObject(info);
				row.getCell("predictName").setValue(info.getPredictName());
				if (info.getPlanValue() > 0) {
					row.getCell("planValue").setValue(new Integer(info.getPlanValue()));
				}
				if (info.getFactValue() != null) {
					row.getCell("factValue").setValue(info.getFactValue());
				}
			}
		}
		if (effects.size() > 0) {
			this.kdtEffect.setRowCount(effects.size());
		}
	}

	private void loadCustomers(MarketManageCustomerCollection customers) {
		this.kdtCustomer.removeRows();
		this.kdtCustomer.checkParsed();
		for (int i = 0; i < customers.size(); i++) {
			MarketManageCustomerInfo info = customers.get(i);
			if (info != null) {
				FDCCustomerInfo customerInfo = info.getFdcCustomer();
				if (customerInfo != null) {
					IRow row = kdtCustomer.addRow();
					row.setUserObject(info);
					row.getCell("fdcCustomer").setValue(customerInfo.getName());
					row.getCell("fdcCustomer").setUserObject(customerInfo);
					row.getCell("number").setValue(info.getNumber());
					row.getCell("name").setValue(info.getName());
					row.getCell("sex").setValue(info.getSex());
					row.getCell("phone").setValue(info.getPhone());
					row.getCell("email").setValue(info.getEmail());
					row.getCell("address").setValue(info.getAddress());
				}
			}
		}
		if (customers.size() > 0) {
			this.kdtCustomer.setRowCount(customers.size());
		}
	}

	private void loadCharges(MarketManageChargeCollection charges) {
		this.kdtCharge.removeRows();
		this.kdtCharge.checkParsed();
		for (int i = 0; i < charges.size(); i++) {
			MarketManageChargeInfo info = charges.get(i);
			if (info != null) {
				PersonInfo personInfo = info.getCustomer();
				if (personInfo != null) {
					IRow row = kdtCharge.addRow();
					row.setUserObject(info);
					row.getCell("customer").setValue(personInfo);
					row.getCell("person").setValue(info.getPerson());
					row.getCell("beginDate").setValue(info.getBeginDate());
					row.getCell("endDate").setValue(info.getEndDate());
					row.getCell("activeTime").setValue(info.getActiveTime());
					row.getCell("content").setValue(info.getContent());
					row.getCell("completion").setValue(info.getCompletion());
				}
			}
		}
		if (charges.size() > 0) {
			this.kdtCharge.setRowCount(charges.size());
		}
	}

	private void loadEntrys(MarketManageEntryCollection entrys) {
		this.kdtEntrys.removeRows();
		this.kdtEntrys.checkParsed();
		BigDecimal planAmount = FDCHelper.ZERO;
		BigDecimal factAmount = FDCHelper.ZERO;
		BigDecimal paymentAmount = FDCHelper.ZERO;

		for (int i = 0; i < entrys.size(); i++) {
			MarketManageEntryInfo entryInfo = entrys.get(i);
			if (entryInfo != null) {
				ExpenseItemInfo expenseItemInfo = entryInfo.getCostNumber();
				if (expenseItemInfo != null) {
					IRow row = this.kdtEntrys.addRow();
					row.getStyleAttributes().setLocked(true);
					row.setUserObject(entryInfo);
					row.getCell("costNumber").setValue(expenseItemInfo);
					ExpenseTypeInfo expenseTypeInfo = expenseItemInfo.getExpenseType();
					if (expenseTypeInfo != null) {
						row.getCell("costName").setValue(expenseTypeInfo.getName());
					}
					row.getCell("butgetAmount").setValue(FDCHelper.toBigDecimal(entryInfo.getButgetAmount()));
					row.getCell("amount").setValue(FDCHelper.toBigDecimal(entryInfo.getAmount()));
					row.getCell("amount").getStyleAttributes().setLocked(false);
					row.getCell("payment").setValue(FDCHelper.toBigDecimal(entryInfo.getPayment()));
					row.getCell("remark").setValue(entryInfo.getRemark());
					row.getCell("remark").getStyleAttributes().setLocked(false);
					paymentAmount = paymentAmount.add(FDCHelper.toBigDecimal(entryInfo.getPayment()));
					planAmount = planAmount.add(FDCHelper.toBigDecimal(entryInfo.getButgetAmount()));
					factAmount = factAmount.add(FDCHelper.toBigDecimal(entryInfo.getAmount()));
				}
			}
		}
		if (entrys.size() > 0) {
			this.kdtEntrys.setRowCount(entrys.size());
			this.txtplanTotalMoney.setValue(planAmount);
			this.txtfactTotalMoney.setValue(factAmount);
			this.txtFactPayment.setValue(paymentAmount);
			SHEHelper.setTextFormat(this.txtplanTotalMoney);
			SHEHelper.setTextFormat(this.txtfactTotalMoney);
			SHEHelper.setTextFormat(this.txtFactPayment);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		MarketManageInfo info = this.editData;
		if (info != null) {
			info.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));

			info.getEntrys().clear();
			info.getEntrys().addCollection(getEntysFromKDTEntrys());

			info.getCharge().clear();
			info.getCharge().addCollection(getChargesFromKDTCharge());

			info.getCustomer().clear();
			info.getCustomer().addCollection(getCustomersFromKDTCustomer());

			info.getEffect().clear();
			info.getEffect().addCollection(getEffectsFromKDTEffect());

			info.getMedia().clear();
			info.getMedia().addCollection(getMediaFromKDTMedia());

		}
	}

	private MarketManageMediaCollection getMediaFromKDTMedia() {
		MarketManageMediaCollection col = new MarketManageMediaCollection();
		for (int i = 0; i < this.kdtMedia.getRowCount(); i++) {
			IRow row = this.kdtMedia.getRow(i);
			if (row != null) {
				MarketManageMediaInfo info = null;
				if (row.getUserObject() instanceof MarketManageEffectInfo) {
					info = (MarketManageMediaInfo) row.getUserObject();
				} else {
					info = new MarketManageMediaInfo();
				}
				if (row.getCell("mediaType").getValue() instanceof MediaInfo) {
					MediaInfo mediaInfo = (MediaInfo) row.getCell("mediaType").getValue();
					if (mediaInfo != null) {
						info.setMediaType(mediaInfo);
					}
				}
				col.add(info);
			}
		}
		return col;
	}

	private MarketManageEffectCollection getEffectsFromKDTEffect() {
		MarketManageEffectCollection col = new MarketManageEffectCollection();
		for (int i = 0; i < this.kdtEffect.getRowCount(); i++) {
			IRow row = this.kdtEffect.getRow(i);
			if (row != null) {
				MarketManageEffectInfo info = null;
				if (row.getUserObject() instanceof MarketManageEffectInfo) {
					info = (MarketManageEffectInfo) row.getUserObject();
				} else {
					info = new MarketManageEffectInfo();
				}
				if (row.getCell("predictName").getValue() != null) {
					info.setPredictName(row.getCell("predictName").getValue().toString());
				}
				BigDecimal planValue = FDCHelper.toBigDecimal(row.getCell("planValue").getValue());
				if (planValue != null) {
					info.setPlanValue(planValue.intValue());
				}
				if (row.getCell("factValue").getValue() != null) {
					info.setFactValue(row.getCell("factValue").getValue().toString());
				}
				col.add(info);
			}
		}
		return col;
	}

	private MarketManageCustomerCollection getCustomersFromKDTCustomer() {
		MarketManageCustomerCollection col = new MarketManageCustomerCollection();
		for (int i = 0; i < this.kdtCustomer.getRowCount(); i++) {
			IRow row = this.kdtCustomer.getRow(i);
			if (row != null) {
				MarketManageCustomerInfo info = null;
				if (row.getUserObject() instanceof MarketManageCustomerInfo) {
					info = (MarketManageCustomerInfo) row.getUserObject();
				} else {
					info = new MarketManageCustomerInfo();
				}
				if (row.getCell("fdcCustomer").getUserObject() instanceof FDCCustomerInfo) {
					FDCCustomerInfo customerInfo = (FDCCustomerInfo) row.getCell("fdcCustomer").getUserObject();
					if (customerInfo != null) {
						info.setFdcCustomer(customerInfo);
					}
				}
				if (row.getCell("number").getValue() != null) {
					info.setNumber(row.getCell("number").getValue().toString());
				}
				if (row.getCell("name").getValue() != null) {
					info.setName(row.getCell("name").getValue().toString());
				}
				if (row.getCell("sex").getValue() != null) {
					info.setSex(row.getCell("sex").getValue().toString());
				}

				if (row.getCell("phone").getValue() != null) {
					info.setPhone(row.getCell("phone").getValue().toString());
				}
				if (row.getCell("email").getValue() != null) {
					info.setEmail(row.getCell("email").getValue().toString());
				}
				if (row.getCell("address").getValue() != null) {
					info.setAddress(row.getCell("address").getValue().toString());
				}
				col.add(info);
			}
		}
		return col;
	}

	private MarketManageChargeCollection getChargesFromKDTCharge() {
		MarketManageChargeCollection col = new MarketManageChargeCollection();
		for (int i = 0; i < this.kdtCharge.getRowCount(); i++) {
			IRow row = this.kdtCharge.getRow(i);
			if (row != null) {
				MarketManageChargeInfo chargeInfo = null;
				if (row.getUserObject() instanceof MarketManageChargeInfo) {
					chargeInfo = (MarketManageChargeInfo) row.getUserObject();
				} else {
					chargeInfo = new MarketManageChargeInfo();
				}
				if (row.getCell("person").getValue() != null) {
					chargeInfo.setPerson(row.getCell("person").getValue().toString());
				}
				chargeInfo.setBeginDate((Date) row.getCell("beginDate").getValue());
				chargeInfo.setEndDate((Date) row.getCell("endDate").getValue());
				if (row.getCell("activeTime").getValue() != null) {
					chargeInfo.setActiveTime(row.getCell("activeTime").getValue().toString());
				}
				if (row.getCell("content").getValue() != null) {
					chargeInfo.setContent(row.getCell("content").getValue().toString());
				}
				if (row.getCell("completion").getValue() != null) {
					chargeInfo.setCompletion(row.getCell("completion").getValue().toString());
				}
				if (row.getCell("customer").getValue() instanceof PersonInfo) {
					PersonInfo personInfo = (PersonInfo) row.getCell("customer").getValue();
					if (personInfo != null) {
						chargeInfo.setCustomer(personInfo);
					}
				}
				col.add(chargeInfo);
			}
		}
		return col;
	}

	private MarketManageEntryCollection getEntysFromKDTEntrys() {
		MarketManageEntryCollection col = new MarketManageEntryCollection();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if (row != null) {
				MarketManageEntryInfo info = null;
				if (row.getUserObject() instanceof MarketManageEntryInfo) {
					info = (MarketManageEntryInfo) row.getUserObject();
				} else {
					info = new MarketManageEntryInfo();
				}
				if (info != null) {
					info.setButgetAmount(FDCHelper.toBigDecimal(row.getCell("butgetAmount").getValue()));
					info.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
					info.setPayment(FDCHelper.toBigDecimal(row.getCell("payment").getValue()));
					if (row.getCell("costNumber").getValue() instanceof ExpenseItemInfo) {
						ExpenseItemInfo expenseItemInfo = (ExpenseItemInfo) row.getCell("costNumber").getValue();
						if (expenseItemInfo != null) {
							info.setCostNumber(expenseItemInfo);
							if (row.getCell("costName").getValue() != null) {
								info.setCostName(row.getCell("costName").getValue().toString());
							}
						}
					}
					if (row.getCell("remark").getValue() != null) {
						info.setRemark(row.getCell("remark").getValue().toString());
					}
					col.add(info);
				}
			}
		}
		return col;
	}

	private void getEntysBeforePayment() {
		MarketManageEntryCollection col = new MarketManageEntryCollection();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if (row != null) {
				MarketManageEntryInfo info = null;
				if (row.getUserObject() instanceof MarketManageEntryInfo) {
					info = (MarketManageEntryInfo) row.getUserObject();
				} else {
					MsgBox.showInfo("有新增费用未保存，请先保存！");
					SysUtil.abort();

				}
				if (info != null) {
					info.setButgetAmount(FDCHelper.toBigDecimal(row.getCell("butgetAmount").getValue()));
					info.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
					info.setPayment(FDCHelper.toBigDecimal(row.getCell("payment").getValue()));
					if (row.getCell("costNumber").getValue() instanceof ExpenseTypeInfo) {
						ExpenseItemInfo expenseItemInfo = (ExpenseItemInfo) row.getCell("costNumber").getValue();
						if (expenseItemInfo != null) {
							info.setCostNumber(expenseItemInfo);
							if (row.getCell("costName").getValue() != null) {
								info.setCostName(row.getCell("costName").getValue().toString());
							}
						}
					}
					if (row.getCell("remark").getValue() != null) {
						info.setRemark(row.getCell("remark").getValue().toString());
					}
					col.add(info);
				}
			}
		}
		entryCol = col;
	}

	public SelectorItemCollection getCustomerSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("head.*");
		return sic;
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("营销活动编号不能为空！");
			this.abort();
		}
		if (this.txtname.getText() == null || this.txtname.getText().trim().equals("")) {
			MsgBox.showInfo("营销活动名称不能为空！");
			return;
		}
		MovementPlanInfo planInfo = (MovementPlanInfo) this.prmtmarketPlan.getValue();
		if (planInfo == null) {
			MsgBox.showInfo("关联活动方案不能为空！");
			return;
		}
		if (this.schemeType.getSelectedItem() == null) {
			MsgBox.showInfo("方案类型不能为空！");
			return;
		}
		Date beginDate = (Date) this.pkbeginDate.getValue();
		if (beginDate == null) {
			MsgBox.showInfo("开始日期不能为空！");
			return;
		}
		Date endDate = (Date) this.pkendDate.getValue();
		if (endDate == null) {
			MsgBox.showInfo("结束日期不能为空！");
			return;
		}
		if (beginDate != null && endDate != null) {
			if (endDate.before(beginDate)) {
				MsgBox.showInfo("结束日期不能小于开始日期！");
				return;
			}
		}
		if (this.txtmovemntTheme.getText() == null || this.txtmovemntTheme.getText().trim().equals("")) {
			MsgBox.showInfo("活动主题不能为空！");
			return;
		}
		if (corChargePayColl != null && corChargePayColl.size() > 0) {
			MarketChargePaymentEntryFactory.getRemoteInstance().addnew(corChargePayColl);
		}
		super.actionSave_actionPerformed(e);
		this.editData=null;
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("营销活动编号不能为空！");
			this.abort();
		}
		if (this.txtname.getText() == null || this.txtname.getText().trim().equals("")) {
			MsgBox.showInfo("营销活动名称不能为空！");
			return;
		}
		MovementPlanInfo planInfo = (MovementPlanInfo) this.prmtmarketPlan.getValue();
		if (planInfo == null) {
			MsgBox.showInfo("关联活动方案不能为空！");
			return;
		}
		if (this.schemeType.getSelectedItem() == null) {
			MsgBox.showInfo("方案类型不能为空！");
			return;
		}
		Date beginDate = (Date) this.pkbeginDate.getValue();
		if (beginDate == null) {
			MsgBox.showInfo("开始日期不能为空！");
			return;
		}
		Date endDate = (Date) this.pkendDate.getValue();
		if (endDate == null) {
			MsgBox.showInfo("结束日期不能为空！");
			return;
		}
		if (beginDate != null && endDate != null) {
			if (endDate.before(beginDate)) {
				MsgBox.showInfo("结束日期不能小于开始日期！");
				return;
			}
		}
		if (this.txtmovemntTheme.getText() == null || this.txtmovemntTheme.getText().trim().equals("")) {
			MsgBox.showInfo("活动主题不能为空！");
			return;
		}
		if (corChargePayColl != null && corChargePayColl.size() > 0) {
			MarketChargePaymentEntryFactory.getRemoteInstance().addnew(corChargePayColl);
		}
		super.actionSubmit_actionPerformed(e);
		this.editData=null;
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntrys.addRow();
		row.getStyleAttributes().setLocked(true);
		row.getCell("costNumber").getStyleAttributes().setLocked(false);
		row.getCell("butgetAmount").setValue(FDCHelper.ZERO);
		row.getCell("amount").getStyleAttributes().setLocked(false);
		row.getCell("remark").getStyleAttributes().setLocked(false);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	private void showTblCustomer(IRow row, FDCCustomerInfo customerInfo) {
		// 这里设置客户分录里的属性
		row.getCell("fdcCustomer").setValue(customerInfo.getName());
		row.getCell("fdcCustomer").setUserObject(customerInfo);
		row.getCell("name").setValue(customerInfo.getName());
		row.getCell("number").setValue(customerInfo.getNumber());
		row.getCell("sex").setValue(customerInfo.getSex());
		row.getCell("phone").setValue(customerInfo.getPhone());
		row.getCell("address").setValue(customerInfo.getAddress());
		row.getCell("email").setValue(customerInfo.getMailAddress());
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
				// KDTSelectBlock block = table.getSelectManager().get(i);
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
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.MarketManageFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.market.MarketManageEntryInfo();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.MarketManageInfo objectValue = new com.kingdee.eas.fdc.market.MarketManageInfo();
		objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));

		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setBizDate(SHEHelper.getServerDate2());
		projectInfo = (SellProjectInfo) getUIContext().get("project");
		objectValue.setSellProject(projectInfo);
		marketTypeInfo = (MarketTypeInfo) getUIContext().get("marketType");
		objectValue.setMarkettype(marketTypeInfo);
		return objectValue;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initToolBar();
		initUI();
		FDCTableHelper.enableAutoAddLine(this.kdtEntrys);
		prmtmarketPlanSetEntityView();
		this.txtNumber.setRequired(true);
		this.txtname.setRequired(true);
		this.prmtmarketPlan.setRequired(true);
		this.schemeType.setRequired(true);
		this.pkbeginDate.setRequired(true);
		this.pkendDate.setRequired(true);
		this.txtmovemntTheme.setRequired(true);

		corChargePayColl = new CoreBaseCollection();
		SHEHelper.setTextFormat(this.txtplanTotalMoney);
		SHEHelper.setTextFormat(this.txtfactTotalMoney);
		SHEHelper.setTextFormat(this.txtFactPayment);

		this.kdtCharge.getColumn("beginDate").setEditor(createDateCellEditor());
		this.kdtCharge.getColumn("beginDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.kdtCharge.getColumn("endDate").setEditor(createDateCellEditor());
		this.kdtCharge.getColumn("endDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");

	}

	protected MediaCollection getMediaCollection() throws Exception {
		MediaCollection col = new MediaCollection();
		for (int i = 0; i < this.kdtMedia.getRowCount(); i++) {
			IRow row = this.kdtMedia.getRow(i);
			if (row != null && row.getCell("mediaType").getValue() != null) {
				MediaInfo info = (MediaInfo) row.getCell("mediaType").getValue();
				col.add(info);
			}
		}
		return col;
	}

	protected ExpenseItemCollection getExpenseTypeCollection() throws Exception {
		ExpenseItemCollection col = new ExpenseItemCollection();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if (row != null && row.getCell("costNumber").getValue() != null) {
				ExpenseItemInfo info = (ExpenseItemInfo) row.getCell("costNumber").getValue();
				col.add(info);
			}
		}
		return col;
	}

	private KDTDefaultCellEditor createDateCellEditor() {
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		return dateEditor;
	}

	private KDWorkButton initWorkBtn1(Action action, String iconName, KDContainer parentContainer, String text) {
		action.putValue(Action.SMALL_ICON, EASResource.getIcon(iconName));
		KDWorkButton btn = new KDWorkButton();
		btn = (KDWorkButton) parentContainer.add(action);
		btn.setToolTipText(text);
		btn.setText(text);
		return btn;
	}

	private void prmtmarketPlanSetEntityView() {
		SellProjectInfo sellproject = (SellProjectInfo) this.prmtsellProject.getValue();
		if (sellproject == null) {
			return;
		}
		MarketTypeInfo mmtype = (MarketTypeInfo) this.prmtmarkettype.getValue();
		if (mmtype == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		String ctrlUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		if (ctrlUnitId == null || "".equals(ctrlUnitId)) {
			return;
		}
		filter.getFilterItems().add(new FilterItemInfo("CU.id", ctrlUnitId));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellproject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("mmType.id", mmtype.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("schemeType", SchemeTypeEnum.INDEPENDENTSCHEME_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("schemeType", SchemeTypeEnum.PLANSCHEME_VALUE));
		filter.setMaskString("#0 and #1 and #2 and ((#3 and #4) or #5 )");
		this.prmtmarketPlan.setQueryInfo("com.kingdee.eas.fdc.market.app.f7MovementPlanQuery");
		this.prmtmarketPlan.getEntityViewInfo().setFilter(filter);
	}

	private void initToolBar() throws Exception {

		this.prmtmarkettype.setEnabled(false);

		// 设置增加分录按钮不可见
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.belongSystem.setEnabled(false);

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

		this.kdtEntrys.getColumn("butgetAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kdtEntrys.getColumn("butgetAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("butgetAmount").getStyleAttributes().setLocked(true);

		this.kdtEntrys.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kdtEntrys.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("amount").getStyleAttributes().setLocked(false);

		this.kdtEntrys.getColumn("payment").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kdtEntrys.getColumn("payment").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.kdtEffect.getColumn("planValue").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.kdtEffect.getColumn("planValue").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.kdtEffect.getColumn("factValue").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.kdtEffect.getColumn("factValue").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

	}

	private void initUI() throws Exception {
		JButton btnAddEntrys = KDEntrys.add(actionAddLine);
		JButton btnDelEntrys = KDEntrys.add(actionRemoveLine);
		JButton btnPayment = KDEntrys.add(actionPayment);
		btnAddEntrys.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddEntrys.setToolTipText("增加分录");
		btnDelEntrys.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelEntrys.setToolTipText("删除分录");
		btnPayment.setIcon(EASResource.getIcon(Action.SMALL_ICON));
		btnPayment.setToolTipText("付款");

		JButton btnAddMedia = KDMedia.add(actionAddMedia);
		JButton btnDelMedia = KDMedia.add(actionDelMedia);
		btnAddMedia.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddMedia.setToolTipText("增加分录");
		btnAddMedia.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelMedia.setToolTipText("删除分录");

		JButton btnAddCharge = KDCharge.add(actionAddCharge);
		JButton btnDelCharge = KDCharge.add(actionDelCharge);
		btnAddCharge.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddCharge.setToolTipText("增加分录");
		btnDelCharge.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelCharge.setToolTipText("删除分录");

		JButton btnAddCustomer = KDCustomer.add(actionAddCustomer);
		JButton btnDelCustomer = KDCustomer.add(actionDelCustomer);
		btnAddCustomer.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddCustomer.setToolTipText("增加分录");
		btnDelCustomer.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelCustomer.setToolTipText("删除分录");
	}

	/**
	 * 
	 * 描述：关联活动方案F7变化事件，分别给表头字段和分录赋值，分别赋予相关的值
	 * 
	 * @author:Administrator
	 * @see com.kingdee.eas.fdc.market.client.AbstractMarketManageEditUI#prmtmarketPlan_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent)
	 */
	protected void prmtmarketPlan_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.prmtmarketPlan_dataChanged(e);
		if (prmtmarketPlan.getData() != null) {
			MovementPlanInfo planInfo = (MovementPlanInfo) prmtmarketPlan.getData();
			this.setTableHead(planInfo);// 给表头字段赋值
			if (planInfo.getId() != null) {
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("E2.*");
				sel.add("E2.chargeType.*");
				sel.add("E2.chargeType.expenseType.*");
				sel.add("E3.*");
				sel.add("E3.fdcCustomer.*");
				sel.add("E4.*");
				sel.add("E4.responsiblePers.*");
				sel.add("E5.*");
				sel.add("E6.*");
				sel.add("E6.mediaType.*");
				sel.add("E6.mediaType.mediaType.*");
				planInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(planInfo.getId().toString())), sel);
				// this.setKdtEntrys(lRow, planInfo);// 给新增加的行赋值 费用列表
				// this.setKdtCustomer(lRow,planInfo);// 给新增加的行赋值 人员列表
				// this.setKdtCharge(lRow, planInfo); // 给新增加的行赋值 活动方案
				this.setEntryE2s(planInfo.getE2());// 费用列表
				this.setEntryE3s(planInfo.getE3());// 人员列表
				this.setEntryE4s(planInfo.getE4());// 活动方案
				this.setEntryE5s(planInfo.getE5());// 效果列表
				this.setEntryE6s(planInfo.getE6());// 媒体列表
			}
		}
	}

	private void setEntryE6s(MovementPlanE6Collection e6s) throws Exception {
		this.kdtMedia.removeRows();
		this.kdtMedia.checkParsed();
		for (int i = 0; i < e6s.size(); i++) {
			MovementPlanE6Info e6Info = e6s.get(i);
			if (e6Info != null) {
				IRow lRow = kdtMedia.addRow();
				MediaInfo mediaInfo = e6Info.getMediaType();
				if (mediaInfo != null) {
					lRow.getCell("mediaType").setValue(mediaInfo);
					lRow.getCell("contractMan").setValue(mediaInfo.getContractMan());
					lRow.getCell("prometionInfo").setValue(mediaInfo.getPreferentialMsg());
					MediaTypeInfo mtInfo = mediaInfo.getMediaType();
					if (mtInfo != null) {
						lRow.getCell("mediaCategory").setValue(mtInfo.getName());
					}
				}
			}

		}
		if (e6s.size() > 0) {
			this.kdtMedia.setRowCount(e6s.size());
		}
	}

	private void setEntryE5s(MovementPlanE5Collection e5s) throws Exception {
		this.kdtEffect.removeRows();
		this.kdtEffect.checkParsed();
		for (int i = 0; i < e5s.size(); i++) {
			MovementPlanE5Info e5Info = e5s.get(i);
			if (e5Info != null) {
				IRow lRow = kdtEffect.addRow();
				lRow.getCell("predictName").setValue(e5Info.getPredictName());
				lRow.getCell("planValue").setValue(new Integer(e5Info.getPredictValue()));
			}
		}
		if (e5s.size() > 0) {
			this.kdtEffect.setRowCount(e5s.size());
		}
	}

	private void setEntryE4s(MovementPlanE4Collection e4s) throws Exception {
		this.kdtCharge.removeRows();
		this.kdtCharge.checkParsed();
		for (int i = 0; i < e4s.size(); i++) {
			MovementPlanE4Info e4Info = e4s.get(i);
			if (e4Info != null) {
				PersonInfo personInfo = e4Info.getResponsiblePers();
				if (personInfo != null) {
					IRow lRow = kdtCharge.addRow();
					lRow.setUserObject(e4Info);
					lRow.getCell("customer").setValue(personInfo);
					lRow.getCell("person").setValue(e4Info.getPersons());
					lRow.getCell("beginDate").setValue(e4Info.getStartTime());
					lRow.getCell("endDate").setValue(e4Info.getEndTime());
					lRow.getCell("activeTime").setValue(e4Info.getActiveTime());
					lRow.getCell("content").setValue(e4Info.getContent());

				}
			}
		}
		if (e4s.size() > 0) {
			this.kdtCharge.setRowCount(e4s.size());
		}

	}

	private void setEntryE3s(MovementPlanE3Collection e3s) throws Exception {
		this.kdtCustomer.removeRows();
		this.kdtCustomer.checkParsed();
		for (int i = 0; i < e3s.size(); i++) {
			MovementPlanE3Info e3Info = e3s.get(i);
			if (e3Info != null) {
				FDCCustomerInfo info = e3Info.getFdcCustomer();
				if (info != null) {
					IRow lRow = kdtCustomer.addRow();
					lRow.setUserObject(e3Info);
					lRow.getCell("fdcCustomer").setValue(info);
					lRow.getCell("fdcCustomer").setUserObject(info);
					lRow.getCell("number").setValue(info.getNumber());
					lRow.getCell("name").setValue(info.getName());
					lRow.getCell("sex").setValue(info.getSex());
					lRow.getCell("phone").setValue(info.getPhone());
					lRow.getCell("email").setValue(info.getEmail());
					lRow.getCell("address").setValue(info.getAddress());
				}
			}
		}
		if (e3s.size() > 0) {
			this.kdtCustomer.setRowCount(e3s.size());
		}
	}

	private void setEntryE2s(MovementPlanE2Collection e2s) throws Exception {
		this.kdtEntrys.removeRows();
		this.kdtEntrys.checkParsed();
		for (int i = 0; i < e2s.size(); i++) {
			MovementPlanE2Info e2 = e2s.get(i);
			if (e2 != null) {
				ExpenseItemInfo expenseItemInfo = e2.getChargeType();
				if (expenseItemInfo != null) {
					IRow lRow = kdtEntrys.addRow();
					lRow.getStyleAttributes().setLocked(true);
					lRow.setUserObject(e2);
					lRow.getCell("costNumber").setValue(expenseItemInfo);
					ExpenseTypeInfo expenseTypeInfo = expenseItemInfo.getExpenseType();
					if (expenseTypeInfo != null) {
						lRow.getCell("costName").setValue(expenseTypeInfo.getName());
					}
					lRow.getCell("butgetAmount").setValue(e2.getMoney());
					lRow.getCell("amount").setValue(e2.getMoney());
					lRow.getCell("amount").getStyleAttributes().setLocked(false);
					lRow.getCell("payment").setValue(FDCHelper.ZERO);
					lRow.getCell("remark").getStyleAttributes().setLocked(false);
				}
			}
		}
		if (e2s.size() > 0) {
			this.kdtEntrys.setRowCount(e2s.size());
		}
	}

	private void fillDealAmountEffect() throws BOSException, SQLException, Exception {
		if (this.editData == null || this.editData.getId() == null) {
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select      ");
		builder.appendSql(" count(distinct Purchase.FID) as PurchaseCount,     ");
		builder.appendSql(" sum(ISNULL(Purchase.FDealAmount,0)) as DealAmount    ");
		builder.appendSql(" from T_SHE_TrackRecord TrackRecord inner join      ");
		builder.appendSql(" T_MAR_MarketManage MarketManage on TrackRecord.FMarketManageID=MarketManage.FID      ");
		builder.appendSql(" inner join T_SHE_Purchase Purchase on TrackRecord.FRelationContract=Purchase.FID      ");
		builder.appendSql(" where TrackRecord.FTrackResult='S30'     ");
		appendStringFilterSql(builder, "MarketManage.FID", this.editData.getId().toString());
		BigDecimal dealAmount = FDCHelper.ZERO;
		BigDecimal PurchaseCount = FDCHelper.ZERO;
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("DealAmount") != null && tableSet.getString("DealAmount") != "") {
				dealAmount = FDCHelper.toBigDecimal(tableSet.getString("DealAmount"));
			}
			if (tableSet.getString("PurchaseCount") != null && tableSet.getString("PurchaseCount") != "") {
				PurchaseCount = FDCHelper.toBigDecimal(tableSet.getString("PurchaseCount"));
			}
		}
		if (this.kdtEffect.getRowCount() > 3) {
			this.kdtEffect.getRow(this.kdtEffect.getRowCount() - 3).getCell("factValue").setValue(PurchaseCount);
			this.kdtEffect.getRow(this.kdtEffect.getRowCount() - 2).getCell("factValue").setValue(dealAmount);
		}
	}

	private void fillActPaymentEffect() throws BOSException, SQLException, Exception {
		if (this.editData == null || this.editData.getId() == null) {
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select      ");
		builder.appendSql(" sum(ISNULL(payListEntry.FActPayAmount,0)) as ActPayAmount      ");
		builder.appendSql(" from T_SHE_TrackRecord TrackRecord inner join      ");
		builder.appendSql(" T_MAR_MarketManage MarketManage on TrackRecord.FMarketManageID=MarketManage.FID      ");
		builder.appendSql(" inner join T_SHE_Purchase Purchase on TrackRecord.FRelationContract=Purchase.FID      ");
		builder.appendSql(" left join T_SHE_PurchasePayListEntry payListEntry on payListEntry.FHeadID = Purchase.FID      ");
		builder.appendSql(" where TrackRecord.FTrackResult='S30'     ");
		appendStringFilterSql(builder, "MarketManage.FID", this.editData.getId().toString());

		BigDecimal actPayAmount = FDCHelper.ZERO;
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			if (tableSet.getString("ActPayAmount") != null && tableSet.getString("ActPayAmount") != "") {
				actPayAmount = FDCHelper.toBigDecimal(tableSet.getString("ActPayAmount"));
			}
		}
		if (this.kdtEffect.getRowCount() > 3) {
			this.kdtEffect.getRow(this.kdtEffect.getRowCount() - 1).getCell("factValue").setValue(actPayAmount);
		}

	}

	private void appendStringFilterSql(FDCSQLBuilder builder, String proName, String proValue) throws Exception {

		if (proValue != null) {
			builder.appendSql(" and " + proName + " = ? ");
			builder.addParam(proValue);
		}

	}

	/**
	 * 
	 * 描述：<方法描述>给关联活动表头字段赋值
	 * 
	 * @param <参数描述>MovementPlanInfo 活动方案Info
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	private void setTableHead(MovementPlanInfo planInfo) throws Exception {
		this.txtplanTotalMoney.setValue(planInfo.getTotalMoney());
		this.txtfactTotalMoney.setValue(planInfo.getTotalMoney());
		this.schemeType.setSelectedItem(planInfo.getSchemeType());
		this.belongSystem.setSelectedItem(planInfo.getBelongSystem());
		this.pkbeginDate.setValue(planInfo.getBeginDate());
		this.pkendDate.setValue(planInfo.getEndDate());
		this.txtmovemntTheme.setText(planInfo.getMovementTheme());
		this.prmtmarkettype.setValue(MarketTypeFactory.getRemoteInstance().getMarketTypeInfo(new ObjectUuidPK(planInfo.getMmType().getId())));
	}

	/**
	 * 
	 * 描述：<方法描述>给分录实际费用赋值 ，根据关联活动方案赋值
	 * 
	 * @param <参数描述>
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	private void setKdtEntrys(IRow lRow, MovementPlanInfo planInfo) throws Exception {
		for (int i = 0; i < planInfo.getE2().size(); i++) {
			lRow = kdtEntrys.addRow();
			IExpenseType im = ExpenseTypeFactory.getRemoteInstance();
			if (planInfo.getE2().get(i).getChargeType() == null) {
				break;
			}
			ObjectUuidPK pk = new ObjectUuidPK(planInfo.getE2().get(i).getChargeType().getId());
			com.kingdee.eas.basedata.scm.common.ExpenseTypeInfo info = im.getExpenseTypeInfo(pk);
			lRow.getCell("costNumber").setValue(info);
			lRow.getCell("costName").setValue(info.getName());
			lRow.getCell("butgetAmount").setValue(planInfo.getE2().get(i).getMoney());
			lRow.getCell("amount").setValue(planInfo.getE2().get(i).getMoney());
		}
	}

	/**
	 * 
	 * 描述：<方法描述>给分录实际参见人员赋值 ，根据关联活动方案赋值
	 * 
	 * @param <参数描述>
	 * @return <返回值描述>
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	private void setKdtCustomer(TrackRecordCollection coll) {
		for (int i = 0; i < coll.size(); i++) {
			IRow lRow = kdtCustomer.addRow();
			FDCCustomerInfo info = coll.get(i).getHead();
			lRow.getCell("fdcCustomer").setValue(info);
			lRow.getCell("number").setValue(info.getNumber());
			lRow.getCell("name").setValue(info.getName());
			lRow.getCell("sex").setValue(info.getSex());
			lRow.getCell("phone").setValue(info.getPhone());
			lRow.getCell("email").setValue(info.getEmail());
			lRow.getCell("address").setValue(info.getAddress());
		}
	}

	/**
	 * 
	 * 描述：<方法描述>给分录实际详细步骤赋值 ，根据关联活动方案赋值
	 * 
	 * @param <参数描述>
	 * @return <返回值描述>
	 * 
	 * @author:ZhangFeng 创建时间：2009-4-30
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	private void setKdtCharge(IRow lRow, MovementPlanInfo planInfo) throws Exception {
		for (int i = 0; i < planInfo.getE4().size(); i++) {
			lRow = kdtCharge.addRow();
			IPerson im1 = PersonFactory.getRemoteInstance();
			if (planInfo.getE4().get(i).getResponsiblePers() == null) {
				break;
			}
			ObjectUuidPK pk1 = new ObjectUuidPK(planInfo.getE4().get(i).getResponsiblePers().getId());
			PersonInfo info = im1.getPersonInfo(pk1);
			lRow.getCell("customer").setValue(info);
			lRow.getCell("person").setValue(planInfo.getE4().get(i).getPersons());
			lRow.getCell("beginDate").setValue(planInfo.getE4().get(i).getStartTime());
			lRow.getCell("endDate").setValue(planInfo.getE4().get(i).getEndTime());
			lRow.getCell("activeTime").setValue(planInfo.getE4().get(i).getActiveTime());
			lRow.getCell("content").setValue(planInfo.getE4().get(i).getContent());
		}
	}

	/**
	 * 
	 * 描述：<方法描述>给分录实际效果赋值 ，根据关联活动方案赋值
	 * 
	 * @param <参数描述>
	 * @return <返回值描述>
	 * 
	 * @author:ZhangFeng 创建时间：2009-8-17
	 *                   <p>
	 * 
	 * @see <相关的类>
	 */
	private void setkdtEffect(MovementPlanInfo planInfo) throws Exception {
		this.kdtEffect.getBody().removeRows();
		ReceptionTypeCollection collection = ReceptionTypeFactory.getRemoteInstance().getReceptionTypeCollection();
		int collSize = collection.size();
		List li = new ArrayList();
		IRow lRow;

		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("E5.*");
		planInfo = MovementPlanFactory.getRemoteInstance().getMovementPlanInfo(new ObjectUuidPK(BOSUuid.read(planInfo.getId().toString())), sel);

		for (int i = 0; i < planInfo.getE5().size(); i++) {
			lRow = kdtEffect.addRow();
			BOSUuid id = planInfo.getE5().get(i).getId();
			MovementPlanE5Info e5Info = MovementPlanE5Factory.getRemoteInstance().getMovementPlanE5Info("WHERE id = '" + id + "'");
			lRow.getCell("predictName").setValue(e5Info.getPredictName());
			lRow.getCell("planValue").setValue(new Integer(e5Info.getPredictValue()));

			if (collSize > i) {
				ReceptionTypeInfo info = ReceptionTypeFactory.getRemoteInstance().getReceptionTypeInfo("WHERE name = '" + e5Info.getPredictName() + "'");
				TrackRecordCollection coll = TrackRecordFactory.getRemoteInstance().getTrackRecordCollection(
						"WHERE receptionType = '" + info.getId() + "' AND marketManage = '" + editData.getId() + "'");
				lRow.getCell("factValue").setValue(String.valueOf(coll.size()));
			}

		}

	}

	/**
	 * 描述： 数据保存之前, 校验数据合法性
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.framework.client.EditUI#verifyInput(java.awt.event.ActionEvent)
	 */
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		// super.beforeStoreFields(e);
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(this.txtNumber.getText())) {
			com.kingdee.eas.util.client.MsgBox.showWarning("活动方案编码不能为空 ！");
			this.abort();
		}
		verifyInputNotNull(txtname, "活动方案名称不能为空 ！");
		verifyF7InputNotNull(prmtmarketPlan, "关联活动方案不能为空 ！");
		verifyKDComboBoxInputNotNull(schemeType, "所属系统不能为空 ！");
	}

	private void verifyInputNotNull(KDTextField txt, String msg) {
		if (!FDCCustomerHelper.verifyInputNotNull(txt, msg)) {
			this.abort();
		}
	}

	private void verifyF7InputNotNull(KDBizPromptBox f7box, String msg) {
		Object obj = f7box.getValue();
		if (obj == null) {
			MsgBox.showWarning(this, msg);
			SysUtil.abort();
		}
	}

	private void verifyKDComboBoxInputNotNull(KDComboBox kDComboBox, String msg) {
		Object obj = kDComboBox.getSelectedItem();
		if (obj == null) {
			MsgBox.showWarning(this, msg);
			SysUtil.abort();
		}
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   	
}