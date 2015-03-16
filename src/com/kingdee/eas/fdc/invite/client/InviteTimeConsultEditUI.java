/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.BaseInviteEntryInfo;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteTimeConsultFactory;
import com.kingdee.eas.fdc.invite.InviteTimeConsultInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InviteTimeConsultEditUI extends AbstractInviteTimeConsultEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteTimeConsultEditUI.class);
    public InviteTimeConsultEditUI() throws Exception
    {
        super();
    }
    public void storeFields()
	{
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
//		loadOther();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
		
		super.loadFields();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteTimeConsultFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.*"));
		return sic;
	}
	
	protected void verifyInputForSave(ActionEvent e) throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSchedule);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtReason);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtArrangements);
	}
	
	protected void attachListeners() {

	}

	protected void detachListeners() {

	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/InviteTimeConsult";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.InviteTimeConsultPrintQuery");
	}
	protected BaseInviteInfo createNewDate() {
		InviteTimeConsultInfo info=new InviteTimeConsultInfo();
		createBaseInvite(info);
		info.setTimes(1);
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return null;
	}
	protected void closeDeleteAttachment() {
		
	}
	protected void initControl() {
		super.initControl();
		SpinnerNumberModel times = new SpinnerNumberModel(editData.getTimes(),1,9999,1);
		this.spTimes.setModel(times);
		

		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
	}
	
	public final static String INVITEPROJECTID="inviteProjectID";
	private HashMap getProviderDataMap() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put(BaseInviteEditUI.INVITEPROJECTID, this.editData.getInviteProject().getId().toString());
		
		return map;
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		InviteTimeConsultPrintDataProvider dataPvd = new InviteTimeConsultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		InviteTimeConsultPrintDataProvider dataPvd = new InviteTimeConsultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
}