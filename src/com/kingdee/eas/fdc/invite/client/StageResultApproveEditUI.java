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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.StageResultApproveEntryCollection;
import com.kingdee.eas.fdc.invite.StageResultApproveEntryFactory;
import com.kingdee.eas.fdc.invite.StageResultApproveEntryInfo;
import com.kingdee.eas.fdc.invite.StageResultApproveFactory;
import com.kingdee.eas.fdc.invite.StageResultApproveInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class StageResultApproveEditUI extends AbstractStageResultApproveEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(StageResultApproveEditUI.class);
    public StageResultApproveEditUI() throws Exception
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
		StageResultApproveEntryCollection col=editData.getEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtEntry.removeRows();
		for(int i=0;i<col.size();i++){
			StageResultApproveEntryInfo entry=col.get(i);
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
    		StageResultApproveEntryInfo entry=(StageResultApproveEntryInfo) row.getUserObject();
    		entry.setSeq(i);
    		entry.setContent((String)row.getCell("content").getValue());
    		entry.setTime((Date)row.getCell("time").getValue());
    		editData.getEntry().add(entry);
    	}
    }
	protected ICoreBase getBizInterface() throws Exception {
		return StageResultApproveFactory.getRemoteInstance();
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
		return "/bim/fdc/invite/StageResultApproveForPrint";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.StageResultApproveForPrintQuery");
	}
	
	protected BaseInviteInfo createNewDate() {
		StageResultApproveInfo info=new StageResultApproveInfo();
		createBaseInvite(info);
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new StageResultApproveEntryInfo();
	}
	protected void closeDeleteAttachment(){
		if(editData!=null){
			for(int i=0;i<this.editData.getEntry().size();i++){
				try {
					/*if(!StageResultApproveEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getEntry().get(i).getId().toString()))){
					}*/
					if(!BaseInviteEditUI.checkExist("T_INV_StageResultApproveEntry", this.editData.getEntry().get(i).getId().toString())) {
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
	protected void initControl() {
		super.initControl();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		if(this.editData.getInviteProject()!=null){
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",this.editData.getInviteProject().getId().toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id","'null'"));
		}
		this.prmtResult.setEntityViewInfo(view);
	}

}