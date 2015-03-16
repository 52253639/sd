/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.RoomJoinNoticeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinNoticeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class RoomJoinNoticeUI extends AbstractRoomJoinNoticeUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomJoinNoticeUI.class);

	/**
	 * output class constructor
	 */
	public RoomJoinNoticeUI() throws Exception {
		super();
	}

	protected IObjectValue createNewData() {
		RoomJoinNoticeInfo noticeInfo = new RoomJoinNoticeInfo();
		noticeInfo.setProcessDate(new Date());
		return noticeInfo;
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		this.actionAuditResult.setVisible(false);
		this.actionAuditResult.setEnabled(false);
		this.actionAttachment.setVisible(false);
		this.actionAttachment.setEnabled(false);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAuditResult.setVisible(false);
		this.actionAuditResult.setEnabled(false);
		this.actionAttachment.setVisible(false);
		this.actionAttachment.setEnabled(false);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getId() == null){
			FDCMsgBox.showInfo("请先保存通知");
			SysUtil.abort();
		}
		RoomJoinNoticePrintDataProvider data = new RoomJoinNoticePrintDataProvider(this.editData.getId().toString(), 
				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.RoomJoinNoticePrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/postSaleService", data, javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if(this.editData.getId() == null){
			FDCMsgBox.showInfo("请先保存通知");
			SysUtil.abort();
		}
		RoomJoinNoticePrintDataProvider data = new RoomJoinNoticePrintDataProvider(this.editData.getId().toString(),
				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.RoomJoinNoticePrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/postSaleService", data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomJoinNoticeFactory.getRemoteInstance();
	}
	
	protected KDTable getDetailTable() {
		return null;
	}

}