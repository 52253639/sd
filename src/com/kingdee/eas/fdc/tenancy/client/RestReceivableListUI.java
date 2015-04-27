package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.reportone.r1.print.designer.gui.ScriptViewer.Action;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.IRestReceivable;
import com.kingdee.eas.fdc.tenancy.RestReceivableFactory;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class RestReceivableListUI extends AbstractRestReceivableListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RestReceivableListUI.class);
	// 组织单元
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public RestReceivableListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// 是否销售实体的新增、编辑、删除控制
		if (this.saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionEdit.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false); 
			//yangfan add
			actionMAudit.setEnabled(false);
			actionImport.setEnabled(false);
		}
		tblMain.getColumn("auditDate").getStyleAttributes().setHided(true);
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.actionAudit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_audit"));
		//yangfan add
		this.actionMAudit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_audit"));
		this.actionImport.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_input"));
		
		this.actionUnAudit.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_unaudit"));

		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);

		this.toolBar.remove(this.btnAuditResult);
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showInfo(this, "请先选中行！");
			return;
		}
		RestReceivableInfo editData = getSelectedData();
		if(TenancyBillStateEnum.Audited.equals(editData.getBillState())){
			MsgBox.showInfo(this,"单据已审核，不能编辑！");
			abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showInfo(this, "请先选中行！");
			return;
		}
		RestReceivableInfo editData = getSelectedData();
		
		if(TenancyBillStateEnum.Audited.equals(editData.getBillState())){
			MsgBox.showInfo(this,"单据已审核，不能删除！");
			abort();
		}
		boolean flag = true;
		if(editData.getOtherPayList() != null){
			for(int i=0;i<editData.getOtherPayList().size();i++){
				if(editData.getOtherPayList().get(i).getActRevAmount() != null && editData.getOtherPayList().get(i).getActRevAmount().compareTo(FDCHelper.ZERO)>0){
					flag = false;
					break;
				} 
			}
		}
		if(!flag){
			MsgBox.showInfo(this, "存在已经收款的分录，无法删除");
			abort();
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 描述：审批
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showInfo(this, "请先选中行！");
			return;
		}
		if(getSelectedIdValues() != null){
			IRestReceivable restRevDao = (IRestReceivable) getBizInterface();
			RestReceivableInfo editData = getSelectedData();
			if(TenancyBillStateEnum.Saved.equals(editData.getBillState())){
				MsgBox.showInfo(this, "请先提交单据！");
				SysUtil.abort();
			}else if(TenancyBillStateEnum.Submitted.equals(editData.getBillState())){
				restRevDao.passAudit(null, editData);
				MsgBox.showInfo(this, "审批成功！");
				this.actionRefresh_actionPerformed(e);
			}else{
				MsgBox.showInfo(this, "当前单据状态不能审批！");
				SysUtil.abort();
			}
		}
	}

	/**
	 * 描述：导入 yangfan add
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		String strSolutionName ="eas.fdc.tenancy.RestReceivable";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		String solutionName = strSolutionName;
		param.solutionName = solutionName;
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, DatataskMode.UPDATE, true);
	}

	/**
	 * 描述：批量审批 yangfan add
	 */
	public void actionMAudit_actionPerformed(ActionEvent e) throws Exception {
		if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showInfo(this, "请先选中行！");
			return;
		}
		
		if(getSelectedIdValues() != null){
			ArrayList id=getSelectedIdValues();
			IRestReceivable restRevDao = (IRestReceivable) getBizInterface();
			int suss=0;
			for(int i=0;i<id.size();i++){
				IObjectPK pk = new ObjectUuidPK(id.get(i).toString());
				RestReceivableInfo editData = restRevDao.getRestReceivableInfo(pk);
				if(TenancyBillStateEnum.Submitted.equals(editData.getBillState())){
					restRevDao.passAudit(null, editData);
					suss=suss+1;
					this.actionRefresh_actionPerformed(e);
				}
			}
			if(id.size()>0){
				MsgBox.showInfo(this, suss+"条单据批量审批成功！");
				this.actionRefresh_actionPerformed(e);
			}
		}
	}

	/**
	 * 描述：反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showInfo(this, "请先选中行！");
			return;
		}
		if(getSelectedIdValues() != null){
			IRestReceivable restRevDao = (IRestReceivable) getBizInterface();
			RestReceivableInfo editData = getSelectedData();
			if(TenancyBillStateEnum.Audited.equals(editData.getBillState())){
				boolean flag = true;
				if(editData.getOtherPayList().size() > 0){
					for(int i=0;i<editData.getOtherPayList().size();i++){
						if(editData.getOtherPayList().get(i).getActRevAmount() != null && editData.getOtherPayList().get(i).getActRevAmount().compareTo(FDCHelper.ZERO)>0){
							flag = false;
							break;
						}
					}
				}
				if(flag){
					restRevDao.unpassAudit(null, editData);
					MsgBox.showInfo(this, "反审批成功！");
					this.actionRefresh_actionPerformed(e);
				}else{
//					int i = MsgBox.showConfirm2(this, "已存在收款单，是否确定要反审批");
//					if(i == 0){
//						restRevDao.unpassAudit(null, editData);
//						MsgBox.showInfo(this, "反审批成功！");
//						this.actionRefresh_actionPerformed(e);
//					}
					FDCMsgBox.showError("已存在收款单，不能反审批");
//					if(i == 0){
//						restRevDao.unpassAudit(null, editData);
//						MsgBox.showInfo(this, "反审批成功！");
//						this.actionRefresh_actionPerformed(e);
//					}
					abort();
				}
				
			}else{
				MsgBox.showInfo(this, "当前单据状态不能反审批！");
				SysUtil.abort();
			}
		}
	}

	private RestReceivableInfo getSelectedData()throws Exception{
		IRestReceivable restRevDao = (IRestReceivable) getBizInterface();

		IObjectPK pk = new ObjectUuidPK(getSelectedKeyValue());
		RestReceivableInfo editData = restRevDao.getRestReceivableInfo(pk);
		return editData;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return RestReceivableFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		RestReceivableInfo objectValue = new RestReceivableInfo();
		return objectValue;
	}

}