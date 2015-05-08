/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.BizTypeFactory;
import com.kingdee.eas.fdc.schedule.BizTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 业务类型基础资料<br>
 * 本页面包含序时簿和编辑界面的功能，可以在此增删改查数据<br>
 * 这都拜常旭的BT需求所赐
 * 
 * @author emanon
 */
public class BizTypeListUI extends AbstractBizTypeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BizTypeListUI.class);

	// 用于保存所有改变了的行，用于保存时使用，这样可以不用一次保存全部行
	Map changedRow = null;
	// 一个行标志，当标志为new时改行新增后还没保存，可直接删除
	private static final String NEW = "new";

	/**
	 * output class constructor
	 */
	public BizTypeListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		changedRow = new HashMap();
		actionSave.setEnabled(true);
		btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
	}

	/**
	 * 初始化表格<br>
	 * 1、由于是列表界面，所以先解锁、设置可读写权限<br>
	 * 2、设置实模式取数和单元格选择模式<br>
	 * 3、设置单元格编辑器
	 */
	private void initTable() {
		tblMain.getStyleAttributes().setLocked(false);
		tblMain.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_CELL_SELECT);

		KDTextField txtNum = new KDTextField();
		txtNum.setMaxLength(40);
		txtNum.setRequired(true);
		tblMain.getColumn("number").setEditor(new KDTDefaultCellEditor(txtNum));
		tblMain.getColumn("number").setRequired(true);

		KDTextField txtName = new KDTextField();
		txtName.setMaxLength(80);
		txtName.setRequired(true);
		tblMain.getColumn("name").setEditor(new KDTDefaultCellEditor(txtName));
		tblMain.getColumn("name").setRequired(true);

		KDTextField txtDesc = new KDTextField();
		txtDesc.setMaxLength(200);
		tblMain.getColumn("description").setEditor(
				new KDTDefaultCellEditor(txtDesc));

		KDDatePicker pkCreateTime = new KDDatePicker();
		tblMain.getColumn("createTime").setEditor(
				new KDTDefaultCellEditor(pkCreateTime));
	}

	/**
	 * 加载info到row
	 * 
	 * @param row
	 * @param info
	 */
	protected void loadRow(IRow row, BizTypeInfo info) {
		if (row == null || info == null) {
			return;
		}
		if (info.getId() != null) {
			row.getCell("id").setValue(info.getId().toString());
		}
		row.getCell("number").setValue(info.getNumber());
		row.getCell("name").setValue(info.getName());
		row.getCell("description").setValue(info.getDescription());
		if (info.getCreator() != null) {
			row.getCell("creator.id").setValue(
					info.getCreator().getId().toString());
		}
		row.getCell("createTime").setValue(info.getCreateTime());
	}

	/**
	 * 保存row到info
	 * 
	 * @param row
	 * @param info
	 */
	protected BizTypeInfo storeRow(IRow row) {
		if (row == null) {
			return null;
		}
		String id = (String) row.getCell("id").getValue();
		String number = (String) row.getCell("number").getValue();
		String name = (String) row.getCell("name").getValue();
		String desc = (String) row.getCell("description").getValue();
		String creator = (String) row.getCell("creator.id").getValue();
		Timestamp createTime = (Timestamp) row.getCell("createTime").getValue();

		if (FDCHelper.isEmpty(number) && FDCHelper.isEmpty(name)) {
			return null;
		} else if (FDCHelper.isEmpty(number)) {
			MsgBox.showWarning(this, "第" + (row.getRowIndex() + 1) + "编码不能为空");
			SysUtil.abort();
		} else if (FDCHelper.isEmpty(name)) {
			MsgBox.showWarning(this, "第" + (row.getRowIndex() + 1) + "名称不能为空");
			SysUtil.abort();
		}

		BizTypeInfo info = new BizTypeInfo();
		if (!FDCHelper.isEmpty(id)) {
			info.setId(BOSUuid.read(id));
		}
		info.setNumber(number);
		info.setName(name);
		info.setDescription(desc);
		info.setCreateTime(createTime);
		if (!FDCHelper.isEmpty(creator)) {
			UserInfo user = new UserInfo();
			user.setId(BOSUuid.read(creator));
			info.setCreator(user);
		}
		return info;
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		BizTypeInfo info = (BizTypeInfo) createNewData();
		IRow row = tblMain.addRow();
		// 添加一个new标志，在删除的时候，如果有这个标志，不用再到数据库删除
		row.setUserObject(NEW);
		loadRow(row, info);
	}

	/**
	 * 保存界面修改
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (changedRow != null) {
			CoreBaseCollection col = new CoreBaseCollection();
			Iterator it = changedRow.entrySet().iterator();
			while (it.hasNext()) {
				IRow row = (IRow) ((Map.Entry) it.next()).getValue();
				BizTypeInfo info = storeRow(row);
				if (info != null) {
					col.add(info);
				}
			}
			getBizInterface().save(col);
		}
		changedRow = new HashMap();
		refresh(null);
	}

	/**
	 * 还没保存的行直接删除
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		IRow selectedRow = KDTableUtil.getSelectedRow(tblMain);
		if (FDCHelper.isEmpty(selectedRow.getUserObject())) {
			super.actionRemove_actionPerformed(e);
		} else if (NEW.equals(selectedRow.getUserObject())) {
			// 顺便还要删除changeRow里面的行，不然还会保存
			changedRow.remove(selectedRow.getCell("id").getValue());
			tblMain.removeRow(selectedRow.getRowIndex());
		}
	}

	/**
	 * 双击不触发查看
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	/**
	 * 改变一行，将此行保存到Set，用于界面保存时只保存已改变的行，防止大量io操作
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object value = e.getValue();
		oldValue = oldValue == null ? "" : oldValue;
		value = value == null ? "" : value;
		if (!oldValue.equals(value)) {
			int numIndex = tblMain.getColumnIndex("number");
			int nameIndex = tblMain.getColumnIndex("name");
			int eIndex = e.getColIndex();

			if (numIndex == eIndex || nameIndex == eIndex) {
				// 校验是否重复
				int rowIndex = e.getRowIndex();
				for (int i = 0; i < tblMain.getRowCount(); i++) {
					if (i != rowIndex) {
						Object obj = tblMain.getCell(i, eIndex).getValue();
						if (value.equals(obj)) {
							if (numIndex == eIndex) {
								MsgBox.showWarning(this, "编码重复，请重新输入!");
							} else if (nameIndex == eIndex) {
								MsgBox.showWarning(this, "名称重复，请重新输入!");
							}
							tblMain.getCell(rowIndex, eIndex).setValue(null);
							return;
						}
					}
				}
			}
			String pk = tblMain.getRow(e.getRowIndex()).getCell("id")
					.getValue().toString();
			changedRow.put(pk, tblMain.getRow(e.getRowIndex()));
		}
	}

	/**
	 * output getBizInterface method
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return BizTypeFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected IObjectValue createNewData() {
		BizTypeInfo objectValue = new BizTypeInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator(getUserInfo());
		return objectValue;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.name"));
		return sic;
	}

}