package com.kingdee.eas.fdc.invite.markesupplier.uitl;
import java.awt.Component;
import java.awt.Container;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.fm.common.NumberValueVerifyRule;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * BOS客户端UI验证工具类 提供对元数据属性编辑验证为true时的补充
 * 参考：资金管理客户端通用类修改而来 
 * @see #com.kingdee.eas.fm.common.client.FMClientVerifyHelper
 */

public class ClientVerifyHelper {

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
			if (tblMain.getCell(rowIndex, colName)!=null&&tblMain.getCell(rowIndex, colName).getValue() == null) {
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
    public static void verifyInput(CoreUIObject ui, String resourcePath, KDTable kdtEntries, IRow row, int i, String msg)
    {
        if(FMHelper.isEmpty(row.getCell(i).getValue()))
        {
            kdtEntries.getEditManager().editCellAt(row.getRowIndex(), i);
            MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
            SysUtil.abort();
        }
    }

    public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key)
    {
        IRow row = null;
        for(int j = 0; j < kdtEntries.getRowCount(); j++)
        {
            row = kdtEntries.getRow(j);
            verifyInput(ui, kdtEntries, row, key);
        }

    }

    public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key, boolean ifEntries)
    {
        IRow row = null;
        for(int j = 0; j < kdtEntries.getRowCount(); j++)
        {
            row = kdtEntries.getRow(j);
            if(ifEntries)
                verifyInput(ui, kdtEntries, row, key, true);
            else
                verifyInput(ui, kdtEntries, row, key);
        }

    }

    public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, IRow row, String key)
    {
        int colIndex = kdtEntries.getColumnIndex(key);
        if(FMHelper.isEmpty(row.getCell(key).getValue()))
        {
            kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
            String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
            String msg = headValue + " " + EASResource.getString(path, "CanNotBeNull");
            msg = msg.replaceAll("#", " " + headValue + " ");
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, IRow row, String key, boolean ifEntries)
    {
        int colIndex = kdtEntries.getColumnIndex(key);
        if(FMHelper.isEmpty(row.getCell(key).getValue()))
        {
            kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
            String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
            String entries = EASResource.getString(path, "entries");
            String msg = entries + headValue + " " + EASResource.getString(path, "CanNotBeNull");
            msg = msg.replaceAll("#", " " + headValue + " ");
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyInputIsZero(CoreUIObject ui, KDTable kdtEntries, IRow row, String key)
    {
        int colIndex = kdtEntries.getColumnIndex(key);
        Object obj = row.getCell(key).getValue();
        if(!FMHelper.isEmpty(obj) && (obj instanceof BigDecimal) && FMHelper.isZERO((BigDecimal)obj))
        {
            kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
            String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
            String msg = headValue + " " + EASResource.getString(path, "CanNotBeZero");
            msg = msg.replaceAll("#", " " + headValue + " ");
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyInputIsMaxValue(CoreUIObject ui, KDTable kdtEntries, IRow row, String key)
    {
        int colIndex = kdtEntries.getColumnIndex(key);
        Object obj = row.getCell(key).getValue();
        if(!FMHelper.isEmpty(obj) && (obj instanceof BigDecimal) && ((BigDecimal)obj).compareTo(MAX_VALUE) == 1)
        {
            kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
            String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
            String msg = headValue + " " + EASResource.getString(path, "CanNotBeZero");
            msg = msg.replaceAll("#", " " + headValue + " ");
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, KDTable kdtEntries)
    {
        if(kdtEntries == null || kdtEntries.getRowCount() < 1)
        {
            MsgBox.showWarning(ui, EASResource.getString(path, "EntryCanNotBeNull"));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDTextField txtNumber, String msg)
    {
        String txt = txtNumber.getText();
        if(txt == null || txt.trim().equals(""))
        {
            txtNumber.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(txtNumber));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, KDTextField txtNumber)
    {
        verifyEmpty(ui, null, txtNumber, null);
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, JFormattedTextField txtField, String msg)
    {
        String txt = txtField.getText();
        if(txt == null || txt.trim().equals(""))
        {
            txtField.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(txtField));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, JFormattedTextField txtNumber)
    {
        verifyEmpty(ui, null, txtNumber, null);
    }

    public static String getMessage(Component component)
    {
        String text = getCompLabelText(component);
        return text + EASResource.getString(path, "CanNotBeNull");
    }

    private static String getCompLabelText(Component component)
    {
        String text = "";
        if(component.getParent() instanceof KDLabelContainer)
            text = ((KDLabelContainer)component.getParent()).getBoundLabelText();
        else
        if(component instanceof KDTextArea)
        {
            Container cont = component.getParent();
            if(cont != null)
            {
                Container cont2 = cont.getParent();
                if((cont2 instanceof KDScrollPane) && (cont2.getParent() instanceof KDLabelContainer))
                    text = ((KDLabelContainer)cont2.getParent()).getBoundLabelText();
            }
        }
        return text;
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDBizPromptBox bizBox, String msg)
    {
        Object content = bizBox.getData();
        if(content == null)
        {
            bizBox.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(bizBox));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, KDBizPromptBox bizBox)
    {
        verifyEmpty(ui, null, bizBox, null);
    }

    public static void verifyEmpty(CoreUIObject ui, KDComboBox comboBox)
    {
        verifyEmpty(ui, null, comboBox, null);
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDMultiLangBox multLangBox, String msg)
    {
        Object content = multLangBox.getSelectedItem();
        if(content == null || content.toString().trim().length() <= 0)
        {
            multLangBox.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(multLangBox));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, KDMultiLangBox multLangBox)
    {
        verifyEmpty(ui, null, multLangBox, null);
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDComboBox comboBox, String msg)
    {
        Object content = comboBox.getSelectedItem();
        if(content == null)
        {
            comboBox.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(comboBox));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDDatePicker datePicker, String msg)
    {
        String content = datePicker.getText();
        if(content == null || content.equals(""))
        {
            datePicker.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(datePicker));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, KDDatePicker datePicker)
    {
        verifyEmpty(ui, null, datePicker, null);
    }

    public static void verifyDateFromTo(CoreUIObject ui, String resourcePath, Date dateFrom, Date dateTo, String msg)
    {
        dateFrom = DateTimeUtils.truncateDate(dateFrom);
        dateTo = DateTimeUtils.truncateDate(dateTo);
        if(dateFrom.compareTo(dateTo) == 0)
            dateTo = DateHelper.getNextDay(dateTo);
        if(dateFrom.compareTo(dateTo) > 0)
        {
            MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDFormattedTextField txtNumber, String msg)
    {
        Object txt = txtNumber.getNumberValue();
        if(txt == null)
        {
            txtNumber.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(txtNumber));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, KDTextArea txtArea, String msg)
    {
        String txt = txtArea.getText();
        if(txt == null || txt.trim().length() <= 0)
        {
            txtArea.requestFocus(true);
            MsgBox.showWarning(ui, resourcePath != null && msg != null ? EASResource.getString(resourcePath, msg) : getMessage(txtArea));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, KDTextArea txtArea, String name)
    {
        String txt = txtArea.getText();
        if(txt == null || txt.trim().length() <= 0)
        {
            txtArea.requestFocus(true);
            MsgBox.showWarning(name + EASResource.getString(path, "CanNotBeNull"));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, KDTextArea txtArea)
    {
        verifyEmpty(ui, null, txtArea, null);
    }

    public static void verifyEmpty(CoreUIObject ui, KDFormattedTextField txtNumber)
    {
        verifyEmpty(ui, null, txtNumber, null);
    }

    public static void checkStringLength(Component ui, String resourcePath, IRow row, String colName, int length)
    {
        String name = (String)row.getCell(colName).getValue();
        if(name != null && name.length() > length)
        {
            MsgBox.showWarning(ui, EASResource.getString(resourcePath, "StringLengthGreaterthan") + length + ":" + "\n\n" + name);
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, Object value, String msg)
    {
        if(FMHelper.isEmpty(value))
        {
            MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
            SysUtil.abort();
        }
    }

    public static void verifyEmpty(CoreUIObject ui, String resourcePath, Object value, String msg, String ctrlName)
    {
        if(FMHelper.isEmpty(value))
        {
            MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
            setComponentFocus(ui, ctrlName);
            SysUtil.abort();
        }
    }

    public static void setComponentFocus(JComponent ui, String componentName)
    {
        Component comps[] = ui.getComponents();
        if(comps != null && comps.length > 0)
        {
            for(int i = 0; i < comps.length; i++)
            {
                if((comps[i] instanceof KDPanel) || (comps[i] instanceof KDScrollPane) || (comps[i] instanceof KDSplitPane) || (comps[i] instanceof KDTabbedPane) || (comps[i] instanceof KDLabelContainer) || (comps[i] instanceof KDContainer) || (comps[i] instanceof KDPanel) || (comps[i] instanceof JPanel))
                {
                    setComponentFocus((JComponent)comps[i], componentName);
                    continue;
                }
                if(comps[i] != null && comps[i].getName() != null && comps[i].getName().equals(componentName))
                    comps[i].requestFocus();
            }

        }
    }

    public static void verifyEmptyAndNoZero(CoreUIObject ui, String resourcePath, Object value, String msg)
    {
        if(value == null)
        {
            MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
            SysUtil.abort();
        }
        if((value instanceof BigDecimal) && ((BigDecimal)value).compareTo(FMConstants.ZERO) == 0)
        {
            MsgBox.showWarning(ui, EASResource.getString(resourcePath, msg));
            SysUtil.abort();
        }
    }

    /**
     * 检查控件不等于零（金额数字字段）
     * @param ui
     * @param txt
     */
    public static void verifyEmptyAndNoZero(CoreUIObject ui, KDFormattedTextField txt)
    {
        verifyEmpty(ui, txt);
        BigDecimal value = txt.getBigDecimalValue();
        if(value.compareTo(FMConstants.ZERO) == 0)
        {
            txt.requestFocus(true);
            String msg = getCompLabelText(txt) + EASResource.getString(path, "CanNotBeZero");
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyBeginEndDateRel(CoreUIObject ui, Date beginDate, Date endDate)
    {
        if(beginDate != null && endDate != null && beginDate.after(endDate))
        {
            MsgBox.showWarning(ui, EASResource.getString(path, "beginDateNotGreaterEndDate"));
            SysUtil.abort();
        }
    }

    public static void verifyBeginEndDateRel(CoreUIObject ui, KDDatePicker pkBeginDate, KDDatePicker pkEndDate)
    {
        if(pkBeginDate == null || pkEndDate == null)
            return;
        if(pkBeginDate.getValue() == null || pkEndDate.getValue() == null)
        {
            MsgBox.showWarning(ui, EASResource.getString(path, "pkDateIsNull"));
            SysUtil.abort();
        }
        Date beginDate = DateTimeUtils.truncateDate((Date)pkBeginDate.getValue());
        Date endDate = DateTimeUtils.truncateDate((Date)pkEndDate.getValue());
        verifyBeginEndDateRel(ui, beginDate, endDate);
    }

    public static void verifyAGreaterThanB(CoreUIObject ui, KDFormattedTextField txtA, KDFormattedTextField txtB)
    {
        verifyEmpty(ui, txtA);
        verifyEmpty(ui, txtB);
        BigDecimal a = txtA.getBigDecimalValue();
        BigDecimal b = txtB.getBigDecimalValue();
        if(a.compareTo(b) < 1)
        {
            txtA.requestFocus(true);
            String aText = getCompLabelText(txtA);
            String bText = getCompLabelText(txtB);
            String msg = MessageFormat.format(EASResource.getString(path, "greaterThan"), new Object[] {
                aText, bText
            });
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyAGreaterThanEqualsB(CoreUIObject ui, KDFormattedTextField txtA, KDFormattedTextField txtB)
    {
        verifyEmpty(ui, txtA);
        verifyEmpty(ui, txtB);
        BigDecimal a = txtA.getBigDecimalValue();
        BigDecimal b = txtB.getBigDecimalValue();
        if(a.compareTo(b) == -1)
        {
            txtA.requestFocus(true);
            String aText = getCompLabelText(txtA);
            String bText = getCompLabelText(txtB);
            String msg = MessageFormat.format(EASResource.getString(path, "greaterThanEquals"), new Object[] {
                aText, bText
            });
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyGreaterThan(CoreUIObject ui, KDFormattedTextField txt, BigDecimal num)
    {
        verifyEmpty(ui, txt);
        BigDecimal value = txt.getBigDecimalValue();
        if(value.compareTo(num) < 1)
        {
            txt.requestFocus(true);
            String Text = getCompLabelText(txt);
            String msg = MessageFormat.format(EASResource.getString(path, "greaterThan"), new Object[] {
                Text, num.toString()
            });
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyGreaterThanEqual(CoreUIObject ui, KDFormattedTextField txt, BigDecimal num)
    {
        verifyEmpty(ui, txt);
        BigDecimal value = txt.getBigDecimalValue();
        if(value.compareTo(num) == -1)
        {
            txt.requestFocus(true);
            String Text = getCompLabelText(txt);
            String msg = MessageFormat.format(EASResource.getString(path, "greaterThanEquals"), new Object[] {
                Text, num.toString()
            });
            MsgBox.showWarning(ui, msg);
            SysUtil.abort();
        }
    }

    public static void verifyRequire(CoreUIObject ui)
    {
        Component comps[] = ui.getComponents();
        for(int i = 0; i < comps.length; i++)
        {
            Component comp = comps[i];
            if(!(comp instanceof KDLabelContainer))
                continue;
            KDLabelContainer ct = (KDLabelContainer)comp;
            JComponent editor = ct.getBoundEditor();
            KDComboBox txtEditor;
            if(editor instanceof KDTextField)
            {
//                txtEditor = (KDTextField)editor;
                if(((KDTextField)editor).isRequired())
                    verifyEmpty(ui, (KDTextField)editor);
                continue;
            }
            if(editor instanceof KDComboBox)
            {
                txtEditor = (KDComboBox)editor;
                if(txtEditor.isRequired())
                    verifyEmpty(ui, txtEditor);
                continue;
            }
            if(editor instanceof KDDatePicker)
            {
//                txtEditor = (KDDatePicker)editor;
                if(((KDDatePicker)editor).isRequired())
                    verifyEmpty(ui, (KDDatePicker)editor);
                continue;
            }
            if(editor instanceof KDBizPromptBox)
            {
//                txtEditor = (KDBizPromptBox)editor;
                if(((KDBizPromptBox)editor).isRequired())
                    verifyEmpty(ui, (KDBizPromptBox)editor);
                continue;
            }
            if(editor instanceof KDFormattedTextField)
            {
//                txtEditor = (KDFormattedTextField)editor;
                if(((KDFormattedTextField)editor).isRequired())
                    verifyEmpty(ui, (KDFormattedTextField)editor);
                continue;
            }
            if(!(editor instanceof KDBizMultiLangBox))
                continue;
//            txtEditor = (KDBizMultiLangBox)editor;
            if(((KDBizMultiLangBox)editor).isRequired())
                verifyEmpty(ui, (KDBizMultiLangBox)editor);
        }

    }

    public static void verifyTableMaxMinValue(CoreUIObject ui, KDTable table, NumberValueVerifyRule rule)
    {
        int rowCount = table.getRowCount();
        int colCount = table.getColumnCount();
        IRow headRow = table.getHeadRow(0);
        String resPath = "com.kingdee.eas.fm.common.client.FMCommonClientResource";
        String resNameMax = "exceedMaxValue";
        String resNameMin = "lessMinValue";
        for(int i = 0; i < rowCount; i++)
        {
            for(int j = 0; j < colCount; j++)
            {
                if(table.getColumn(j).getStyleAttributes().isHided())
                    continue;
                Object obj = table.getCell(i, j).getValue();
                if(obj == null || !(obj instanceof BigDecimal))
                    continue;
                BigDecimal numValue = (BigDecimal)obj;
                String colCapital = (String)headRow.getCell(j).getValue();
                if(numValue.compareTo(rule.getMaxValue()) > 0)
                {
                    String message = FMClientHelper.formatMessage("com.kingdee.eas.fm.common.client.FMCommonClientResource", "exceedMaxValue", new Object[] {
                        String.valueOf(i + 1), colCapital, rule.getMaxValue()
                    });
                    MsgBox.showWarning(ui, message);
                    SysUtil.abort();
                }
                if(numValue.compareTo(rule.getMinValue()) < 0)
                {
                    String message = FMClientHelper.formatMessage("com.kingdee.eas.fm.common.client.FMCommonClientResource", "lessMinValue", new Object[] {
                        String.valueOf(i + 1), colCapital, rule.getMinValue()
                    });
                    MsgBox.showWarning(ui, message);
                    SysUtil.abort();
                }
            }

        }

    }

    public static Set getAllComponents(Container container)
    {
        Component components[] = container.getComponents();
        Set compSet = new HashSet();
        int i = 0;
        for (int n = components.length; i < n; i++) {
            Component comp = components[i];
            compSet.add(comp);
            if((comp instanceof KDPanel) || (comp instanceof KDScrollPane) || (comp instanceof KDSplitPane) || (comp instanceof KDTabbedPane) || (comp instanceof KDLabelContainer) || (comp instanceof KDContainer) || (comp instanceof JPanel))
                compSet.addAll(getAllComponents((Container)comp));
        }

        return compSet;
    }

    
    /**
     * 检查UI界面中编辑控件设置属性requestFocus为TRUE时
     * @param ui 界面对象
     */
    public static void verifyUIControlEmpty(CoreUI ui) {
        Set allLeafComponents = getAllComponents(ui);
        Iterator it = allLeafComponents.iterator();
        do {
            if(!it.hasNext())
                break;
            
            Component comp = (Component)it.next();
            if (comp instanceof IKDTextComponent) {
            	
                IKDTextComponent txtComp = (IKDTextComponent)comp;
                if(txtComp.isRequired())
                    if(comp instanceof KDTextField)
                        verifyEmpty(ui, (KDTextField)comp);
                    else if(comp instanceof KDBizPromptBox)
                        verifyEmpty(ui, (KDBizPromptBox)comp);
                    else if(comp instanceof KDComboBox)
                        verifyEmpty(ui, (KDComboBox)comp);
                    else if(comp instanceof KDMultiLangBox)
                        verifyEmpty(ui, (KDMultiLangBox)comp);
                    else if(comp instanceof KDDatePicker)
                        verifyEmpty(ui, (KDDatePicker)comp);
                    else if(comp instanceof KDFormattedTextField)
                        verifyEmpty(ui, (KDFormattedTextField)comp);
                    else if(comp instanceof KDTextArea)
                        verifyEmpty(ui, (KDTextArea)comp);
            }
        } while(true);
    }

    public static boolean verifyEmptyXOR(Object a, Object b)
    {
        return !verifyEmptyOR(a, b);
    }

    public static boolean verifyEmptyXOR(Object a, Object b, Object c)
    {
        return !verifyEmptyOR(a, b, c);
    }

    public static boolean verifyEmptyOR(Object a, Object b)
    {
        return a == null && b == null || a != null && b != null;
    }

    public static boolean verifyEmptyOR(Object a, Object b, Object c)
    {
        return a == null && b == null && c == null || a != null && b != null && c != null;
    }

    public static void verifyRelateAccountBankAsCurrency(CoreUIObject ui, AccountBankInfo acctInfo, CurrencyInfo currency)
        throws EASBizException, BOSException
    {
        if(acctInfo == null)
        {
            MsgBox.showWarning(ui, EASResource.getString(path, "bankAccount") + EASResource.getString(path, "CanNotBeNull"));
            SysUtil.abort();
        }
        if(currency == null)
        {
            MsgBox.showWarning(ui, EASResource.getString(path, "currency") + EASResource.getString(path, "CanNotBeNull"));
            SysUtil.abort();
        }
        CurrencyInfo acctCurrency = acctInfo.getCurrency();
        if(acctCurrency == null || acctCurrency.getId() == null)
        {
            SelectorItemCollection sic = new SelectorItemCollection();
            sic.add(new SelectorItemInfo("id"));
            sic.add(new SelectorItemInfo("name"));
            sic.add(new SelectorItemInfo("number"));
            sic.add(new SelectorItemInfo("currency.id"));
            sic.add(new SelectorItemInfo("currency.name"));
            sic.add(new SelectorItemInfo("currency.number"));
            acctInfo = AccountBankFactory.getRemoteInstance().getAccountBankInfo(new ObjectUuidPK(acctInfo.getId()), sic);
            acctCurrency = acctInfo.getCurrency();
        }
        if(acctCurrency == null)
            return;
        if(!acctCurrency.getId().toString().equals(currency.getId().toString()))
        {
            MsgBox.showWarning(ui, EASResource.getString(path, "acctCurrencyNotBillCurrency"));
            SysUtil.abort();
        }
    }

    private static String path = "com.kingdee.eas.fm.common.FMResource";
    private static final BigDecimal MAX_VALUE = new BigDecimal("9999999999999.99");

}
