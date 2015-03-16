/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.InviteMonthPlanInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeFactory;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class InviteProjectListUI extends AbstractInviteProjectListUI {
	private static final Logger logger = CoreUIObject.getLogger(InviteProjectListUI.class);

	public InviteProjectListUI() throws Exception {
		super();
	}
	protected String getEditUIName() {
		return InviteProjectEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteProjectFactory.getRemoteInstance();
	}
	public KDTable getBillListTable() {
		return this.tblMain;
	}
	
	 public void actionRelate_actionPerformed(ActionEvent e) throws Exception
    {
		 this.checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		
		IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InviteProjectModifyUI.class.getName(),
				uiContext, null, OprtState.EDIT);
		ui.show(); 
    }
	 
	 public void tblMain_tableSelectChanged(KDTSelectEvent e) {
		 IRow row = FDCTableHelper.getSelectedRow(tblMain);
		 String prjId = (String) row.getCell("id").getValue();
		 if(row != null) {
			try {
				InviteProjectInfo project = InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(new ObjectUuidPK(prjId));
				BOSUuid modeId = project.getInvitePurchaseMode().getId();
				InvitePurchaseModeInfo mode = InvitePurchaseModeFactory.getRemoteInstance().getInvitePurchaseModeInfo(new ObjectUuidPK(modeId));
				if(mode !=null) {
					btnRelate.setEnabled(true);
				}else {
					btnRelate.setEnabled(false);
				}
				
			} catch (EASBizException e1) {
				this.handleException(e1);
			} catch (BOSException e1) {
				this.handleException(e1);
			}
		 }else {
			 btnRelate.setEnabled(true);
		 }
		 
		 try {
			super.tblMain_tableSelectChanged(e);
		} catch (Exception e1) {
			this.handleException(e1);
		}
	 }
	public void onLoad() throws Exception {
		super.onLoad();
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("数据升级（工程项目名称）");
			this.toolBar.add(btnUpdate);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnUpdate_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
	}
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (FDCMsgBox.showConfirm2(this,"是否数据升级？") != MsgBox.OK) {
			return;
		}
		int m=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fid from T_INV_InviteProject where fcurProjectName is null");
		IRowSet rs = sqlBuilder.executeQuery();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("entries.project.name");
		sel.add("entries.project.id");
		SelectorItemCollection updatesel=new SelectorItemCollection();
		updatesel.add("curProjectName");
		while(rs.next()){
			InviteProjectInfo inviteProject=InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(new ObjectUuidPK(rs.getString("fid")),sel);
			Set curProject=new HashSet();
			String curProjectName="";
			for(int i=0;i<inviteProject.getEntries().size();i++){
				CurProjectInfo info =inviteProject.getEntries().get(i).getProject();
				if(info==null) continue;
				if(!curProject.contains(info.getId())){
					curProjectName=curProjectName+info.getName()+",";
					curProject.add(info.getId());
				}
			}
			if(curProjectName.indexOf(",")>0){
				curProjectName=curProjectName.substring(0, curProjectName.lastIndexOf(","));
			}else{
				curProjectName=null;
			}
			inviteProject.setCurProjectName(curProjectName);
			InviteProjectFactory.getRemoteInstance().updatePartial(inviteProject, updatesel);
			m++;
		}
		FDCMsgBox.showInfo(this,m+"条招标立项成功升级！");
	}
}