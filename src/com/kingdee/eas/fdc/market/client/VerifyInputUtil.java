package com.kingdee.eas.fdc.market.client;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 录入校验帮助类
 * 
 * @author zhoubin
 * 
 */
public class VerifyInputUtil {

	private static Logger logger = Logger.getLogger(VerifyInputUtil.class);

	/**
	 * 校验数据为空，可校验文本框、F7
	 * 
	 * @param ui
	 *            UI的name，可传null或者this等
	 * @param comp
	 *            控件的name，如：txtNumber
	 * @param name
	 *            要提示为空的信息，如“编码”
	 */
	public static void verifyNull(CoreUIObject ui, Component comp, String name) {
		if (comp == null)
			return;

		if (comp instanceof KDTextField) {
			KDTextField ctrl = (KDTextField) comp;
			String text = ctrl.getText();
			if (text == null || "".equals(text.trim())) {
				abort(ui, comp, name + "不能为空！");
			}
		} else if (comp instanceof KDBizPromptBox) {
			KDBizPromptBox ctrl = (KDBizPromptBox) comp;
			Object obj = ctrl.getData();
			if (obj != null) {
				if (obj instanceof Object[]) {
					Object array[] = (Object[]) obj;
					if (array.length < 1 || array[0] == null) {
						abort(ui, comp, name + "不能为空！");
					}
				}
			} else {
				abort(ui, comp, name + "不能为空！");
			}
		} else if (comp instanceof KDFormattedTextField) {
			KDFormattedTextField ctrl = (KDFormattedTextField) comp;
			if (ctrl.getBigDecimalValue() == null) {
				abort(ui, comp, name + "不能为空！");
			}
		} else if (comp instanceof KDDatePicker) {
			KDDatePicker ctrl = (KDDatePicker) comp;
			if (ctrl.getValue() == null) {
				abort(ui, comp, name + "不能为空！");
			}
		} else if (comp instanceof KDComboBox) {
			KDComboBox ctrl = (KDComboBox) comp;
			if (ctrl.getSelectedItem() == null) {
				abort(ui, comp, name + "不能为空！");
			}
		}
	}

	/**
	 * 校验表格中一个单元格数据是否为空
	 * 
	 * @param ui
	 *            UI的name，可传null或者this等
	 * @param tblMain
	 *            表格name
	 * @param rowIndex
	 *            行号
	 * @param colName
	 *            列名称
	 */
	public static void verifyKDTCellNull(CoreUIObject ui, KDTable tblMain,
			int rowIndex, String colName) {
		if (tblMain == null)
			return;

		if (tblMain.getRowCount() < 1) {
			abort(ui, tblMain, "请增加行！");
		} else {
			if (tblMain.getCell(rowIndex, colName).getValue() == null) {
				tblMain.getSelectManager().select(rowIndex,
						tblMain.getColumnIndex(colName), rowIndex,
						tblMain.getColumnIndex(colName));
				abort(ui, null, tblMain.getHead().getRow(0).getCell(
						tblMain.getColumnIndex(colName)).getValue()
						+ "不能为空！");
			}
		}
	}

	/**
	 * 校验表格中一个单元格数据是否为空
	 * 
	 * @param ui
	 *            UI的name，可传null或者this等
	 * @param tblMain
	 *            表格name
	 * @param colName
	 *            列名称
	 */
	public static void verifyKDTColumnNull(CoreUIObject ui, KDTable tblMain,
			String colName) {
		if (tblMain == null)
			return;

		if (tblMain.getRowCount() < 1) {
			abort(ui, tblMain, "请增加行！");
		} else {
			for (int i = 0, size = tblMain.getRowCount(); i < size; i++) {
				if (tblMain.getCell(i, colName).getValue() == null) {
					tblMain.getSelectManager().select(i,
							tblMain.getColumnIndex(colName), i,
							tblMain.getColumnIndex(colName));
					abort(ui, null, tblMain.getHead().getRow(0).getCell(
							tblMain.getColumnIndex(colName)).getValue()
							+ "不能为空！");
				}
			}
		}
	}


	/**
	 * 提示信息并中断，comp获得焦点
	 * 
	 * @param ui
	 *            UI的name，可传null或者this等
	 * @param comp
	 *            控件的name，如：txtNumber
	 * @param name
	 *            要提示为空的信息，如“编码”
	 */
	public static void abort(CoreUIObject ui, Component comp, String errorString) {
		MsgBox.showError(ui, errorString);
		if (comp != null)
			comp.requestFocus();
		SysUtil.abort();
	}
}
