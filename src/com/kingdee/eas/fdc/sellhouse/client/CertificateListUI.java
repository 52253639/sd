/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CertificateFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class CertificateListUI extends AbstractCertificateListUI {

	private Map certifacateMap = new HashMap() {
		{
			put("001", "001");
			put("002", "002");
			put("003", "003");
			put("004", "004");
			put("005", "005");
			put("006", "006");
		}
	};
	/**
	 * 
	 */
	private static final long serialVersionUID = -8124781401056724366L;
	private static final Logger logger = CoreUIObject
			.getLogger(CertificateListUI.class);

	/**
	 * output class constructor
	 */
	public CertificateListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected ICoreBase getBizInterface() throws Exception {

		return CertificateFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CertificateEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected FDCDataBaseInfo getBaseDataInfo() {

		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.actionLocate.setVisible(false);
		this.actionQuery.setVisible(false);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		String number = getSelectNumber();
		if(!certifacateMap.containsKey(number)){
			try {
				ArrayList list = getSelectedIdValues();
				CertificateFactory.getRemoteInstance().isDeleteCertificate(
						list.get(0).toString());
			} catch (BOSException ex) {
				logger.error(ex.getMessage());
				FDCMsgBox.showWarning(this, "单据已经被引用，不能删除!");
				SysUtil.abort();
			}
		}
		
		super.actionRemove_actionPerformed(e);
	}

	private String getSelectNumber() {
		String number = "";
		int num = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(num);
		if(row==null){
			return number;
		}
		
		if(row.getCell("number").getValue()!=null){
			number = row.getCell("number").getValue().toString();
		}
		
		return number;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String number = getSelectNumber();
		if(!certifacateMap.containsKey(number)){
			try {
				ArrayList list = getSelectedIdValues();
				CertificateFactory.getRemoteInstance().isDeleteCertificate(
						list.get(0).toString());
			} catch (BOSException ex) {
				logger.error(ex.getMessage());
				FDCMsgBox.showWarning(this, "单据已经被引用，不能修改!");
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
		
	}

	public void onShow() throws Exception {
		super.onShow();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setEnabled(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);

	}
}
