/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.invite.BaseInviteEntryInfo;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteCalibrationEntrysCollection;
import com.kingdee.eas.fdc.invite.InviteCalibrationEntrysFactory;
import com.kingdee.eas.fdc.invite.InviteCalibrationEntrysInfo;
import com.kingdee.eas.fdc.invite.InviteCalibrationFactory;
import com.kingdee.eas.fdc.invite.InviteCalibrationInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class InviteCalibrationEditUI extends AbstractInviteCalibrationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteCalibrationEditUI.class);
    public InviteCalibrationEditUI() throws Exception
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
	protected void loadEntry(){
		InviteCalibrationEntrysCollection col=editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtEntry.removeRows();
		for(int i=0;i<col.size();i++){
			InviteCalibrationEntrysInfo entry=col.get(i);
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
    		InviteCalibrationEntrysInfo entry=(InviteCalibrationEntrysInfo) row.getUserObject();
    		entry.setSeq(i);
    		entry.setContent((String)row.getCell("content").getValue());
    		entry.setTime((Date)row.getCell("time").getValue());
    		editData.getEntry().add(entry);
    	}
    }
	protected ICoreBase getBizInterface() throws Exception {
		return InviteCalibrationFactory.getRemoteInstance();
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
		return "/bim/fdc/invite/InviteCalibrationForPrint";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.InviteCalibrationForPrintQuery");
	}
	
	protected BaseInviteInfo createNewDate() {
		InviteCalibrationInfo info=new InviteCalibrationInfo();
		createBaseInvite(info);
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new InviteCalibrationEntrysInfo();
	}
	protected void closeDeleteAttachment(){
		if(editData!=null){
			for(int i=0;i<this.editData.getEntry().size();i++){
				try {
					/*if(!InviteCalibrationEntrysFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getEntry().get(i).getId().toString()))){
					}*/
					if(!BaseInviteEditUI.checkExist("T_INV_InviteCalibrationEntrys", this.editData.getEntry().get(i).getId().toString())) {
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
}