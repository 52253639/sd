/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryInfo;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InviteTenderPlanningEditUI extends AbstractInviteTenderPlanningEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteTenderPlanningEditUI.class);
    public InviteTenderPlanningEditUI() throws Exception
    {
        super();
    }
    public void storeFields()
	{
		super.storeFields();
		storeEntry();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		loadEntry();
//		loadOther();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
		
		super.loadFields();
	}
	
	protected void initControl() {
		super.initControl();
	}
	
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtScope);
		FDCClientVerifyHelper.verifyEmpty(this, txtPayMethod);
	}
	
	protected void loadEntry(){
		InviteTenderPlanningEntryCollection col=editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtEntry.removeRows();
		for(int i=0;i<col.size();i++){
			InviteTenderPlanningEntryInfo entry=col.get(i);
			IRow row=this.kdtEntry.addRow();
			row.setUserObject(entry);
			row.getCell("content").setValue(entry.getContent());
			row.getCell("time").setValue(entry.getTime());
			try {
				row.getCell("attach").setValue(loadAttachment(entry.getId().toString()));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}	
	protected void storeEntry(){
    	editData.getEntry().clear();
    	for(int i=0;i<this.kdtEntry.getRowCount();i++){
    		IRow row = this.kdtEntry.getRow(i);
    		InviteTenderPlanningEntryInfo entry=(InviteTenderPlanningEntryInfo) row.getUserObject();
    		entry.setSeq(i);
    		entry.setContent((String)row.getCell("content").getValue());
    		entry.setTime((Date)row.getCell("time").getValue());
    		editData.getEntry().add(entry);
    	}
    }
	protected ICoreBase getBizInterface() throws Exception {
		return InviteTenderPlanningFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("entry.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.*"));
		
		
		return sic;
	}
	protected void attachListeners() {

	}

	protected void detachListeners() {

	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/InviteTenderPlanning";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.InviteTenderPlanningPrintQuery");
	}
	
	protected BaseInviteInfo createNewDate() {
		InviteTenderPlanningInfo info=new InviteTenderPlanningInfo();
		createBaseInvite(info);
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new InviteTenderPlanningEntryInfo();
	}
	protected void closeDeleteAttachment(){
		if(editData!=null){
			for(int i=0;i<this.editData.getEntry().size();i++){
				try {
					/*if(!InviteTenderPlanningEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getEntry().get(i).getId().toString()))){
					}*/
					if(!BaseInviteEditUI.checkExist("T_INV_InviteTPlanningEntry", this.editData.getEntry().get(i).getId().toString())) {
						this.deleteAttachment(this.editData.getEntry().get(i).getId().toString());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
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
		InviteTenderPlanningPrintDataProvider dataPvd = new InviteTenderPlanningPrintDataProvider(
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
		InviteTenderPlanningPrintDataProvider dataPvd = new InviteTenderPlanningPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
}