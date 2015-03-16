package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.im.InputMethodRequests;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.CellTextRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMultiLangArea;
import com.kingdee.bos.ctrl.swing.KDTextAreaCtrl;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCNumberConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceGatherCollection;
import com.kingdee.eas.fdc.invite.QuotingPriceGatherFactory;
import com.kingdee.eas.fdc.invite.QuotingPriceGatherInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 经济标评审汇总表
 */
public class QuotingPriceGatherUI extends AbstractQuotingPriceGatherUI {
	private static final Logger logger = CoreUIObject
			.getLogger(QuotingPriceGatherUI.class);
	/**
	 *
	 */
	private static final long serialVersionUID = -8272639914544383403L;

	private NewListingInfo listingInfo;

	private UserInfo userInfo;

	private QuotingPriceGatherInfo gatherInfo;

	private NewQuotingPriceCollection priceContentCollection;

	// 目标成本
	private BigDecimal totalPrice;

	// 参考总价
	private BigDecimal preferenceTotalPrice;

	private String description = "";
    
	static class myComboBox extends KDComboBox{
		private static final long serialVersionUID = -8692681333135475572L;

		public InputMethodRequests getInputMethodRequests() {
			return null;
		}
	}
	private KDComboBox areaCombobox = new myComboBox();
	{
		areaCombobox.addItem("建筑面积");
		areaCombobox.addItem("可售面积");
		areaCombobox.addItem("景观面积");
	}

	/**
	 * output class constructor
	 */
	public QuotingPriceGatherUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		init();
		// JMenuItem myItem = new JMenuItem();
		// myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
		// Event.CTRL_MASK+Event.SHIFT_MASK));
		// myItem.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// try {
		// TestRunner.run(TestAll.suite());
		// } catch (RemoteException e1) {
		// e1.printStackTrace();
		// } catch (ServiceException e1) {
		// e1.printStackTrace();
		// }
		// }
		//
		// });
		// this.menuHelp.add(myItem);
		// myItem.setVisible(false);
	}

	private void init() throws Exception {
		initPrivateData();
		initTable();
		loadDataToTable();
	}

	private NewQuotingPriceCollection getQuotingPrice() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		String inviteListingId = (String) this.getUIContext().get("inviteListingId");
		filter.getFilterItems().add(new FilterItemInfo("listing.id", inviteListingId));
		filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE));
		NewQuotingPriceCollection quotingPrices = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
		return quotingPrices;
	}

	public void initPrivateData() throws EASBizException, BOSException {
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		if (inviteListingId == null)
			return;
		listingInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(inviteListingId));
		// 参考总价
		preferenceTotalPrice = listingInfo.getTotalAmount();
		userInfo = SysContext.getSysContext().getCurrentUserInfo();
		priceContentCollection = getQuotingPrice();
		if (this.priceContentCollection.size() == 0) {
			MsgBox.showInfo("没有选择报价!");
			this.abort();
		}
	}

	public void initTable() {
		KDTable table = this.kDTable1;
		ActionMap actionMap = table.getActionMap();
		actionMap.remove(KDTAction.PASTE);
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		table.checkParsed();
		table.getColumn(0).setWidth(150);
		table.getColumn(1).setWidth(25);
		table.getColumn(2).setWidth(25);
		table.getColumn(3).setWidth(150);
		table.getColumn(5).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn(5).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
		table.getColumn(6).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getColumn(6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
		table.getColumn(7).getStyleAttributes().setNumberFormat("#,##0.00%;-#,##0.00%");
		table.getColumn(7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐

		IRow row = null;
		// 招标产品或服务名称
		row = table.addRow();
		row.getCell(0).setValue("招标产品或服务名称");
		table.getMergeManager().mergeBlock(0, 1, 0, 7);
		row.getStyleAttributes().setLocked(true);

		// 建筑面积
		row = table.addRow();
		// row.getCell(0).setValue("建筑面积123");
		areaCombobox.setEditable(true);
		row.getCell(0).setEditor(new KDTDefaultCellEditor(areaCombobox));
		// row.getCell(0).getStyleAttributes().setLocked(true);
		table.getCell(1, 1).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getCell(1, 1).setEditor(getNumberEditor());
		table.getMergeManager().mergeBlock(1, 1, 1, 7);

		// 目标成本
		row = table.addRow();
		row.getCell(0).setValue("目标成本（元）");
		row.getCell(0).getStyleAttributes().setLocked(true);
		table.getMergeManager().mergeBlock(2, 1, 2, 7);
		table.getCell(2, 1).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getCell(1, 1).setEditor(getNumberEditor());
		table.getCell(2, 1).setEditor(getNumberEditor());

		// 参考价
		row = table.addRow();
		row.getCell(0).setValue("参考价（元）");
		table.getMergeManager().mergeBlock(3, 1, 3, 7);
		table.getCell(3, 1).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		table.getCell(3, 1).setEditor(getNumberEditor());
		row.getStyleAttributes().setLocked(true);

		// 废标情况
		row = table.addRow();
		row.getCell(0).setValue("废标情况");
		row.getCell(0).getStyleAttributes().setLocked(true);
		row.getCell(1).setValue("无");
		row.getCell(1).getStyleAttributes().setLocked(true);
		row.getCell(2).setValue(Boolean.TRUE);
		row.getCell(3).getStyleAttributes().setLocked(true);

		table.getMergeManager().mergeBlock(4, 3, 4, 7);

		row = table.addRow();
		row.getCell(0).getStyleAttributes().setLocked(true);
		row.getCell(1).setValue("有");
		row.getCell(1).getStyleAttributes().setLocked(true);
		row.getCell(2).setValue(Boolean.FALSE);
		row.getCell(3).setValue("废标人名称");
		row.getCell(3).getStyleAttributes().setLocked(true);
		row.getCell(4).getStyleAttributes().setLocked(true);
		row.getCell(4).getStyleAttributes().setBackground(new Color(218, 202, 200));
		KDTextField field = new KDTextField();
		field.setMaxLength(65);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(field);
		row.getCell(4).setEditor(editor);
		table.getMergeManager().mergeBlock(4, 0, 5, 0);
		table.getMergeManager().mergeBlock(5, 4, 5, 7);

		// 合理经济标评排序
		row = table.addRow();
		row.getStyleAttributes().setLocked(true);
		row.getCell(0).setValue("合理经济标排序");
		row.getCell(1).setValue("排序");
		table.getMergeManager().mergeBlock(6, 1, 6, 2);
		row.getCell(3).setValue("投标人名称");
		row.getCell(4).setValue("投标人编号");
		row.getCell(5).setValue("投标总价（元）");
		row.getCell(6).setValue("单方造价（元）");
		row.getCell(7).setValue("百分比");

		// table.getMergeManager().mergeBlock(6, 0, 8, 0);
		table.addKDTEditListener(new KDTEditListenerDIY(table));
	}

	private static ICellEditor getNumberEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setPrecision(2);
		formattedTextField.setRemoveingZeroInDispaly(false);
		formattedTextField.setRemoveingZeroInEdit(false);
		formattedTextField.setNegatived(false);
		formattedTextField.setMaximumValue(FDCNumberConstants.MAX_VALUE);
		formattedTextField.setMinimumValue(FDCNumberConstants.MIN_VALUE);
		formattedTextField.setHorizontalAlignment(SwingConstants.LEFT);
		formattedTextField.setSupportedEmpty(true);
		return new KDTDefaultCellEditor(formattedTextField);
	}

	public void loadDataToTable() throws BOSException {
		if (listingInfo == null) {
			return;
		}
		final KDTable table = this.kDTable1;
		table.getCell(0, 1).setValue(listingInfo.getName());
		String areaType = "";
		// 参考价,即总造价
		EntityViewInfo entityInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("inviteListing.id", listingInfo.getId().toString());
		filterInfo.appendFilterItem("creator.id", userInfo.getId().toString());
		entityInfo.setFilter(filterInfo);
		entityInfo.getSelector().add("*");
		QuotingPriceGatherCollection gatherCollection = QuotingPriceGatherFactory.getRemoteInstance().getQuotingPriceGatherCollection(entityInfo);
		if (gatherCollection.size() > 0) {
			gatherInfo = gatherCollection.get(0);
			// 目标成本
			totalPrice = gatherInfo.getAimCost();
			ICell cell = table.getCell(1, 1);
			cell.setEditor(getNumberEditor());
			cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			if (gatherInfo.getBuildArea() >= 0) {
				cell.setValue((gatherInfo.getBuildArea() + ""));
			} else {
				cell.setValue("");
			}
			if (gatherInfo.isStatus()) {
				table.getCell(4, 2).setValue(Boolean.FALSE);
				table.getCell(5, 2).setValue(Boolean.TRUE);
				table.getCell(5, 4).setValue(gatherInfo.getGatherName());
				table.getCell(5, 4).getStyleAttributes().setLocked(false);
				table.getCell(5, 4).getStyleAttributes().setBackground(table.getCell(0, 0).getStyleAttributes().getBackground());
			}
			areaType = gatherInfo.getAreaType();
		}
		IRow row = null;
		// 招标产品或服务名称
		if (priceContentCollection != null) {
			List list = Arrays.asList(priceContentCollection.toArray());
			Collections.sort(list, new CoreBaseCollectionComparator("totoalPrice"));
			BigDecimal minTotalPrice = null;
			for (int i = 0; i < list.size(); i++) {
				NewQuotingPriceInfo info = (NewQuotingPriceInfo) list.get(i);
				if (info.getTotoalPrice().doubleValue() != 0) {
					minTotalPrice = info.getTotoalPrice();
					break;
				}
			}
			for (int i = 7; i < table.getRowCount(); /* i++ */) {
				table.removeRow(i);
				/* i--; */
			}

			for (int i = 0; i < list.size(); i++) {
				NewQuotingPriceInfo info = (NewQuotingPriceInfo) list.get(i);
				row = table.addRow();
				row.getStyleAttributes().setLocked(true);
				row.getCell(1).setValue((i + 1) + "");
				table.getMergeManager().mergeBlock(7 + i, 1, 7 + i, 2);
				row.getCell(3).setValue(info.getSupplier().getName());
				row.getCell(4).setValue(info.getSupplier().getNumber());
				row.getCell(5).setValue(info.getTotoalPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
				if (gatherInfo != null && gatherInfo.getBuildArea() > 0 ) {
					row.getCell(6).setValue(info.getTotoalPrice().doubleValue() / gatherInfo.getBuildArea() + "");
				}
				if (minTotalPrice != null) {
					row.getCell(7).setValue(info.getTotoalPrice().doubleValue() / minTotalPrice.doubleValue() + "");
				}
			}
			table.getMergeManager().mergeBlock(6, 0, table.getRowCount() - 1, 0);
		}

		row = table.addRow();
		// KDTable mytable=new KDTable(2,0,1);
		// row =mytable.getRow(0);
		// this.add(mytable);
		row.setHeight(300);
		table.getMergeManager().mergeBlock(table.getRowCount() - 1, 1, table.getRowCount() - 1, table.getColumnCount() - 1);
		ICell cell = null;
		cell = row.getCell(0);
		cell.getStyleAttributes().setLocked(true);
		cell.setValue("情况说明");
		cell = row.getCell(1);

		final KDBizMultiLangArea textField = new KDBizMultiLangArea();
		
		textField.setMaxLength(1000);
		textField.setAutoscrolls(true);
		textField.setEditable(true);
		textField.setToolTipText("Alt+Enter换行");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(textField);
		cell.setEditor(editor);
		cell.getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
		cell.getStyleAttributes().setWrapText(true);
		
		if (gatherInfo != null /* && gatherInfo.isStatus() */) {
			description = gatherInfo.getDescription();
			// table.getCell(table.getRowCount() - 1,
			// 2).getStyleAttributes().setLocked(false);
			cell.setValue(description);
		}
		// else {
		// cell.getStyleAttributes().setLocked(true);
		// cell.getStyleAttributes().setBackground(new Color(218, 202, 200));
		// }

		// 面积类型
		if (areaType != null && areaType.trim().length() > 0) {
			kDTable1.getCell(1, 0).setValue(areaType);
		} else {
			kDTable1.getCell(1, 0).setValue("建筑面积");
		}
		// 目标成本
		if (totalPrice != null && !totalPrice.equals(FDCNumberHelper._ONE)) {
			kDTable1.getCell(2, 1).setValue(totalPrice);
		}
		// 参考总价
		if (preferenceTotalPrice != null && preferenceTotalPrice.signum() != 0) {
			kDTable1.getCell(3, 1).setValue(preferenceTotalPrice);
		}

		// TODO add two field in entity
//		row = kDTable1.addRow();
//		row.getCell(0).setValue("成本评标人");
//		row.getCell(4).setValue("成本负责人");
//		int lastRowInd = row.getRowIndex();
//		kDTable1.getMergeManager().mergeBlock(lastRowInd, 1, lastRowInd, 3);
//		kDTable1.getMergeManager().mergeBlock(lastRowInd, 5, lastRowInd, 7);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (gatherInfo == null) {
			gatherInfo = new QuotingPriceGatherInfo();
			gatherInfo.setInviteListing(listingInfo);
			gatherInfo.setCreator(userInfo);
		}

		gatherInfo.setStatus(((Boolean) kDTable1.getCell(5, 2).getValue()).booleanValue());
		if (gatherInfo.isStatus()) {
			String gatherName = (String) this.kDTable1.getCell(5, 4).getValue();
			gatherInfo.setGatherName(gatherName);
		}
		description = (String) kDTable1.getCell(kDTable1.getRowCount() - 1, 1).getValue();
		gatherInfo.setDescription(description);
		// Object value = this.kDTable1.getCell(1, 1).getValue();
		// if (value instanceof String) {
		// gatherInfo.setBuildArea(Float.parseFloat((String) value));
		// } else if (value instanceof BigDecimal) {
		// gatherInfo.setBuildArea(((BigDecimal) value).floatValue());
		// } else if (value instanceof Float) {
		// gatherInfo.setBuildArea(((Float) value).floatValue());
		// } else {
		// gatherInfo.setBuildArea(0);
		// }
		// 如果没有输入，写入负值
		if(kDTable1.getCell(1, 1).getValue() == null || kDTable1.getCell(1, 1).getValue().toString().trim().equals(""))
			gatherInfo.setBuildArea(FDCNumberHelper._ONE.intValue());
		else
			gatherInfo.setBuildArea(InviteHelper.convertToBigDecimal(kDTable1.getCell(1, 1).getValue()).floatValue());

		// 面积类型
		gatherInfo.setAreaType((String) this.kDTable1.getCell(1, 0).getValue());
		// 目标成本
		// 如果没有输入，写入负值
		if(kDTable1.getCell(2, 1).getValue() == null || kDTable1.getCell(2, 1).getValue().toString().trim().equals(""))
			gatherInfo.setAimCost(FDCNumberHelper._ONE);
		else
			gatherInfo.setAimCost(FDCNumberHelper.toBigDecimal(kDTable1.getCell(2,
				1).getValue()));

		if (gatherInfo.getId() == null) {
			IObjectPK pk = QuotingPriceGatherFactory.getRemoteInstance().addnew(gatherInfo);
			gatherInfo.setId(BOSUuid.read(pk.toString()));
			showBarInfo("保存成功");
		} else {
			QuotingPriceGatherFactory.getRemoteInstance().update(new ObjectUuidPK(gatherInfo.getId()), gatherInfo);
			showBarInfo("保存成功");
		}
		// loadDataToTable();

	}

	protected void showBarInfo(String info) {
		// TODO 自动生成方法存根
		setMessageText(info);
		setNextMessageText(info);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = this.kDTable1;
		File tempFile = File.createTempFile("eastemp", ".xls");
		table.getIOManager().setTempFileDirection(tempFile.getPath());
		table.getIOManager().exportExcelToTempFile(false);
		tempFile.deleteOnExit();
	}

	class KDTEditListenerDIY implements KDTEditListener {
		private KDTable table;

		// ICell cell = null;

		public KDTEditListenerDIY(KDTable table) {
			this.table = table;
			// cell=table.getCell(table.getRowCount() - 1, 1);
		}

		public void editStarting(KDTEditEvent e) {
		}

		public void editStarted(KDTEditEvent e) {
		}

		public void editValueChanged(KDTEditEvent e) {
			int col = e.getColIndex();
			int row = e.getRowIndex();
			boolean edit = false;
			if ((row == 5 && col == 2) || (row == 4 && col == 2)) {
				if (row == 4 && col == 2) {
					Object obj = e.getValue();
					if (obj instanceof Boolean) {
						Boolean value = (Boolean) obj;
						if (value.booleanValue()) {
							table.getCell(5, 2).setValue(Boolean.FALSE);
							edit = false;
						} else {
							table.getCell(5, 2).setValue(Boolean.TRUE);
							edit = true;
						}
					}
				} else if (row == 5 && col == 2) {
					Object obj = e.getValue();
					if (obj instanceof Boolean) {
						Boolean value = (Boolean) obj;
						edit = value.booleanValue();
						if (value.booleanValue()) {
							table.getCell(4, 2).setValue(Boolean.FALSE);
						} else {
							table.getCell(4, 2).setValue(Boolean.TRUE);
						}
					}
				}

				if (edit) {
					table.getCell(5, 4).getStyleAttributes().setLocked(false);
					table.getCell(5, 4).getStyleAttributes().setBackground(table.getCell(0, 0).getStyleAttributes().getBackground());
					// table.getCell(table.getRowCount() - 1,
					// 1).getStyleAttributes().setLocked(false);
					// table.getCell(table.getRowCount() - 1,
					// 1).getStyleAttributes().setBackground(table.getCell(0,
					// 0).getStyleAttributes().getBackground());
				} else {
					Color color = new Color(218, 202, 200);
					table.getCell(5, 4).getStyleAttributes().setLocked(true);
					table.getCell(5, 4).setValue("");
					table.getCell(5, 4).getStyleAttributes().setBackground(color);
					// table.getCell(table.getRowCount() - 1,
					// 1).getStyleAttributes().setLocked(true);
					// table.getCell(table.getRowCount() - 1, 1).setValue("");
					// table.getCell(table.getRowCount() - 1,
					// 1).getStyleAttributes().setBackground(color);
				}
			}
		}

		public void editStopping(KDTEditEvent e) {
		}

		public void editStopped(KDTEditEvent e) {
			int col = e.getColIndex();
			int row = e.getRowIndex();
			if (row == 2 && col == 1) {
				Object value = table.getCell(2, 1).getValue();
				if (value != null && value.toString().trim().length() > 0)
					if (value instanceof String || value instanceof BigDecimal || value instanceof Double || value instanceof Long) {
						totalPrice = new BigDecimal(value.toString());
					}
			} else if (row == 1 && col == 1) {
				refreshOrderTable(table);
			}
		}

		public void editCanceled(KDTEditEvent e) {
		}

	}

	public void refreshOrderTable(KDTable table) {
		ICell cell = table.getCell(1, 1);
		if (cell.getValue() != null && cell.getValue().toString().length() > 0) {
			BigDecimal area = new BigDecimal(cell.getValue().toString());
			if (area.floatValue() > 0)
				for (int i = 7; i < table.getRowCount() - 1; i++) {
					IRow row = table.getRow(i);
					ICell totalPriceCell = row.getCell(5);
					BigDecimal totalPrice = (BigDecimal) totalPriceCell.getValue();
					row.getCell(6).setValue(totalPrice.divide(area, BigDecimal.ROUND_HALF_UP));
				}
		} else {
			for (int i = 7; i < table.getRowCount() - 1; i++) {
				IRow row = table.getRow(i);
				row.getCell(6).setValue("");
			}
		}

	}

	public void prePrint_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.setPrintTitle(kDTable1, "经济标评审汇总表", 0);
		kDTable1.getPrintManager().printPreview();
	}

	public void printAction_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.setPrintTitle(kDTable1, "经济标评审汇总表", 0);
		kDTable1.getPrintManager().print();
	}

	public void refreshAction_actionPerformed(ActionEvent e) throws Exception {
		loadDataToTable();
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = this.kDTable1;
		File tempFile = File.createTempFile("eastemp", ".xls");
		table.getIOManager().setTempFileDirection(tempFile.getPath());
		table.getIOManager().exportExcelToTempFile(false);
		tempFile.deleteOnExit();
	}
}
