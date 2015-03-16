/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;

/**
 * output class name
 */
public class QuotingPriceImportErrorInfoUI extends
		AbstractQuotingPriceImportErrorInfoUI {
	private static final Logger logger = CoreUIObject
			.getLogger(QuotingPriceImportErrorInfoUI.class);

	/**
	 * output class constructor
	 */
	public QuotingPriceImportErrorInfoUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnExportExcel_actionPerformed method
	 */
	protected void btnExportExcel_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		KDTable table = this.kDTable1;
		File tempFile = File.createTempFile("eastemp", ".xls");
		table.getIOManager().setTempFileDirection(tempFile.getPath());
		table.getIOManager().exportExcelToTempFile(false);
		tempFile.deleteOnExit();
	}

	/**
	 * output kDButton2_actionPerformed method
	 */
	protected void kDButton2_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		disposeUIWindow();
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		List errorInfoList = (List) getUIContext().get("errorInfoList");
		Object onlyDisplayDescription = getUIContext().get(
				"onlyDisplayDescription");
		KDTable table = this.kDTable1;
		table.checkParsed();
		NewListingEntryInfo entryInfo = null;
		if (onlyDisplayDescription != null) {
			table.getColumn("number").getStyleAttributes().setHided(true);
			table.getColumn("name").getStyleAttributes().setHided(true);
			table.getColumn("unit").getStyleAttributes().setHided(true);
			table.getColumn("description").setWidth(500);
		}
		for (int i = 0; i < errorInfoList.size(); i++) {
			HashMap errMap = (HashMap) errorInfoList.get(i);
			entryInfo = (NewListingEntryInfo) errMap.get("entryInfo");
			IRow row = table.addRow();
			row.getCell("seq").setValue(errMap.get("seq"));
			if (entryInfo != null) {
				row.getCell("number").setValue(entryInfo.getItemNumber());
				row.getCell("name").setValue(entryInfo.getItemName());
//				row.getCell("unit").setValue(entryInfo.getUnit());
			}
			row.getCell("description").setValue(
					errMap.get("errInfo").toString());
		}
		table.getStyleAttributes().setLocked(true);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

	}

}