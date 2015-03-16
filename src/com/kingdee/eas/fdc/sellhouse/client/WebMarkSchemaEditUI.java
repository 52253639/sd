/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.WebMarkFieldFactory;
import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection;
import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo;
import com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection;
import com.kingdee.eas.fdc.sellhouse.WebMarkProcessFactory;
import com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo;
import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaFactory;
import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class WebMarkSchemaEditUI extends AbstractWebMarkSchemaEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(WebMarkSchemaEditUI.class);

	/**
	 * output class constructor
	 */
	public WebMarkSchemaEditUI() throws Exception {
		super();
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("functionEntrys.id"));
		sic.add(new SelectorItemInfo("functionEntrys.*"));
		sic.add(new SelectorItemInfo("functionEntrys.functionName"));
		sic.add(new SelectorItemInfo("isEnabled"));
		return sic;
	}

	/*
	 * xwb 重载单据进行方案与步骤的数据填写。
	 */
	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();

		int lCurRow = getCurRow();
		if (lCurRow > -1)
			storeValue(lCurRow);

		// super.storeFields();
	}

	protected IObjectValue createNewData() {
		kdtProcessEntrys.removeRows();
		WebMarkSchemaInfo schemaInfo = new WebMarkSchemaInfo();
		schemaInfo.setIsEnabled(isEnabled);
		return schemaInfo;
	}

	public void onLoad() throws Exception {
		this.kdtFunctionEntrys.checkParsed();
		this.kdtProcessEntrys.checkParsed();
		super.onLoad();
		initSellProject();
		initProcessEntrys();
		// if (this.kdtFunctionEntrys.getRowCount() > 0) {
		// fillKdtProcessEntrys(0);
		// }
		setBtnStatus();
		storeFields();
		initOldData(editData);
		WebMarkSchemaInfo info = (WebMarkSchemaInfo) getDataObject();
		if (OprtState.VIEW.equals(getOprtState())) {
			actionCancel.setEnabled(info.isIsEnabled());
			actionCancelCancel.setEnabled(!info.isIsEnabled());
		}
		
		this.actionInsertLine.setVisible(false);
		this.actionInsertLine.setEnabled(false);
		
		this.prmtSellProject.setRequired(true);
		
	}

	private void setBtnStatus() {
		this.actionAddLine.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_addline"));
		this.actionInsertLine.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_insert"));
		this.actionDelLine.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_deleteline"));

		// this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.SellProjectQuery");
	}
	
	private void initSellProject(){
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", org.getId().toString(),
						CompareType.EQUALS));
		
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isForSHE", Boolean.TRUE,
						CompareType.EQUALS));
		/*UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		if(user!=null){
			filterInfo.getFilterItems().add(
					new FilterItemInfo("creator.id", user.getId().toString(),
							CompareType.EQUALS));
			
		}*/
		String queryInfo = "com.kingdee.eas.fdc.sellhouse.app.SellProjectForSHESellProjectQuery";
		SHEHelper.initF7(this.prmtSellProject, queryInfo, filterInfo);
	}

	// 分录表的增插删
	/**
	 * output actionAddLine_actionPerformed method
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtFunctionEntrys.isFocusOwner()) {
			this.kdtFunctionEntrys.checkParsed();
			int index = -1;
			index = this.kdtFunctionEntrys.getRowCount();
			if (index != -1) {
				kdtFunctionEntrys.addRow(index);
			} else {
				kdtFunctionEntrys.addRow();
			}
		} else if (this.kdtProcessEntrys.isFocusOwner()) {
			this.kdtProcessEntrys.checkParsed();
			int activeIndex = this.kdtFunctionEntrys.getSelectManager().getActiveRowIndex();
			IRow row = this.kdtFunctionEntrys.getRow(activeIndex);
			if(row==null){
				FDCMsgBox.showWarning(this,"请先选择功能名称!");
				SysUtil.abort();
			}
			if(row.getCell("functionEntrys.functionName").getValue()==null){
				FDCMsgBox.showWarning(this,"功能名称不能为空!");
				SysUtil.abort();
			}
			int index = -1;
			index = this.kdtProcessEntrys.getRowCount();

			if (index != -1) {
				kdtProcessEntrys.addRow(index);
			} else {
				kdtProcessEntrys.addRow();
			}
		}
	}

	/**
	 * output actionInsertLine_actionPerformed method
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.kdtFunctionEntrys.isFocusOwner()) {
			this.kdtFunctionEntrys.checkParsed();
			int i = -1;
			i = this.kdtFunctionEntrys.getSelectManager().getActiveRowIndex();
			if (i == -1) {
				MsgBox
						.showError(com.kingdee.eas.util.client.EASResource
								.getString(
										"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
										"Selected_Insert"));
				return;
			}
			kdtFunctionEntrys.addRow(i);
			storeValue(i + 1);
			fillKdtProcessEntrys(i + 1);

		} else if (this.kdtProcessEntrys.isFocusOwner()) {
			this.kdtFunctionEntrys.checkParsed();
			int i = -1;
			i = this.kdtProcessEntrys.getSelectManager().getActiveRowIndex();
			if (i == -1) {
				MsgBox
						.showError(com.kingdee.eas.util.client.EASResource
								.getString(
										"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
										"Selected_Insert"));
				return;
			}
			kdtProcessEntrys.addRow(i);
		}
		
	}

	/**
	 * output actionDelLine_actionPerformed method
	 */
	public void actionDelLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtFunctionEntrys.isFocusOwner()) {
			int i = -1;
			i = this.kdtFunctionEntrys.getSelectManager().getActiveRowIndex();
			if (i == -1) {
				MsgBox
						.showError(com.kingdee.eas.util.client.EASResource
								.getString(
										"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
										"Selected_Delete"));
				return;
			}

			/*
			 * 删除功能分录行的时候，检查：如有步骤数据则不能删除
			 */
			int activeRowIndex = kdtFunctionEntrys.getSelectManager()
					.getActiveRowIndex();
			if (activeRowIndex < 0 || this.kdtProcessEntrys.getRowCount() > 0){
				FDCMsgBox.showWarning(this,"已有步骤，不能删除！");
				SysUtil.abort();
			}
				

			// 删除

			this.kdtFunctionEntrys.removeRow(i);
			WebMarkFunctionCollection functionList = (WebMarkFunctionCollection) this.kdtFunctionEntrys
					.getUserObject();
			if (functionList != null) {
				if (i >= 0 && i < functionList.size()) {
					functionList.removeObject(i);
					// kdtProcessEntrys.setUserObject(null);
					fillKdtProcessEntrys(0);
				}
			}
		} else if (this.kdtProcessEntrys.isFocusOwner()) {
			int i = -1;
			i = this.kdtProcessEntrys.getSelectManager().getActiveRowIndex();
			if (i == -1) {
				MsgBox
						.showError(com.kingdee.eas.util.client.EASResource
								.getString(
										"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
										"Selected_Delete"));
				return;
			}

			/*
			 * 删除步骤分录行的时候，检查：如有字段关联数据则不能删除
			 */
			int activeRowIndex = kdtProcessEntrys.getSelectManager()
					.getActiveRowIndex();
			String processID = (String) this.kdtProcessEntrys.getCell(
					activeRowIndex, "id").getValue();
			if (activeRowIndex < 0 || hasFieldRelationData(processID))
				SysUtil.abort();

			// 删除
			// 删除步骤
			WebMarkFunctionInfo function = (WebMarkFunctionInfo) this.kdtProcessEntrys
					.getUserObject();
			if (function != null) {
				WebMarkProcessCollection entrys = function.getProcessEntrys();
				if (i >= 0 && i < entrys.size())
					entrys.removeObject(i);
			}
			this.kdtProcessEntrys.removeRow(i);
		}
	}

	/**
	 * output kdtFunctionEntrys_tableSelectChanged method
	 */
	protected void kdtFunctionEntrys_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// System.out.println(22);
		super.kdtFunctionEntrys_tableSelectChanged(e);
		// 存之前功能所属步骤
		storeValue(getRowIdx(e));
		int rowIndex = e.getSelectBlock().getBeginRow();
		fillKdtProcessEntrys(rowIndex);
	}

	private int getRowIdx(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		int lRe = -99;
		try {
			lRe = e.getPrevSelectBlock().getBeginRow();
		} catch (Exception ex) {
			ex = null;
		} finally {
			return lRe;
		}
	}

	private int getCurRow() {
		int lRe = -99;
		try {
			lRe = kdtFunctionEntrys.getSelectManager().getActiveRowIndex();
		} catch (Exception ex) {
			ex = null;
		} finally {
			return lRe;
		}
	}

	/**
	 * output kdtProcessEntrys_tableClicked method
	 */
	protected void kdtProcessEntrys_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.kdtProcessEntrys_tableClicked(e);
	}

	/**
	 * output kdtProcessEntrys_editStopped method
	 */
	protected void kdtProcessEntrys_editStopped(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e)
			throws Exception {
		super.kdtProcessEntrys_editStopped(e);
		// storeValue();
	}

	/**
	 * 
	 */
	private void storeValue(int storeRow) {
		// System.out.println(storeRow);
		if (storeRow == -99 || storeRow == -1)
			return;
		// int
		// storeRow=this.kdtFunctionEntrys.getSelectManager().getActiveRowIndex();
		WebMarkFunctionCollection functionList = (WebMarkFunctionCollection) this.kdtFunctionEntrys
				.getUserObject();
		if ((WebMarkFunctionInfo) this.kdtProcessEntrys.getUserObject() == null) {
			// storeFields();
			this.kdtProcessEntrys
					.setUserObject((WebMarkFunctionInfo) functionList
							.get(storeRow));
		}
		WebMarkFunctionInfo function = (WebMarkFunctionInfo) this.kdtProcessEntrys
				.getUserObject();
		
		/**
		 * 当function为空的时候，如果不做非空判断的话
		 * 删除function的时候就会报错
		 * by renliang at 2010-11-21
		 */
		if(function!=null){
			WebMarkProcessCollection entrys = (WebMarkProcessCollection) function.getProcessEntrys();
		    entrys.clear();
			for (int i = 0; i < this.kdtProcessEntrys.getRowCount(); i++) {
				WebMarkProcessInfo entry = new WebMarkProcessInfo();
				entry.setParent(function);

				Object content = this.kdtProcessEntrys.getCell(i, "id").getValue();
				if (content != null)
					entry.setId(BOSUuid.read((String) content));
				content = this.kdtProcessEntrys.getCell(i, "processName")
						.getValue();
				entry.setProcessName((String) content);
				content = this.kdtProcessEntrys.getCell(i, "url").getValue();
				entry.setUrl((String) content);
				entry.setSeq((short) i);
				entrys.add(entry);
			}
			// 更新 功能(userobject)List的functionINFO
			functionList.set(storeRow, function);
		}
	}

	private String getFuncID(WebMarkFunctionInfo function) {
		String id = "";
		try {
			id = function.getId().toString();
		} catch (Exception ex) {
			ex = null;
		} finally {
			return id;
		}
	}

	private void fillKdtProcessEntrys(int rowIndex) throws Exception {
		kdtProcessEntrys.removeRows();
		if (rowIndex == -1)
			return;
		WebMarkFunctionCollection functionList = (WebMarkFunctionCollection) this.kdtFunctionEntrys
				.getUserObject();
		WebMarkFunctionInfo function = functionList.get(rowIndex);
		this.kdtProcessEntrys.setUserObject(function);
		if (function == null)
			return;
		WebMarkProcessCollection entrys = function.getProcessEntrys();
		if (entrys.size() == 0
				&& (STATUS_EDIT.equals(this.getOprtState()) || STATUS_VIEW
						.equals(this.getOprtState()))) {
			// 处理保存后记录展现不了问题
			// initProcessEntrys();
			String id = getFuncID(function);
			if (!id.equals(""))
				entrys = getProcessEntrys(id);
		}
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			WebMarkProcessInfo process = (WebMarkProcessInfo) iter.next();
			IRow valueRow = this.kdtProcessEntrys.addRow();
			if (process.getId() != null) {
				valueRow.getCell("id").setValue(process.getId().toString());
			}
			valueRow.getCell("processName").setValue(process.getProcessName());
			valueRow.getCell("url").setValue(process.getUrl());
		}
		if (this.getOprtState().equals("VIEW")) {
			this.kdtProcessEntrys.getStyleAttributes().setLocked(true);
		}
	}

	// 若有字段关联则为true，否则为false
	private boolean hasFieldRelationData(String processID) {
		if (processID == null)
			return false;
		// 此处增加检查是否有字段关联数据存在的代码
		String oql = "select parent where parent= '" + processID + "'";
		boolean hasData = false;
		try {
			hasData = WebMarkFieldFactory.getRemoteInstance().exists(oql);
		} catch (EASBizException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return hasData;
	}

	private void initProcessEntrys() throws Exception {
		WebMarkFunctionCollection functionList = (WebMarkFunctionCollection) this.kdtFunctionEntrys
				.getUserObject();
		for (Iterator iter = functionList.iterator(); iter.hasNext();) {
			WebMarkFunctionInfo function = (WebMarkFunctionInfo) iter.next();
			if (function.getId() != null) {
				// String oql = "select * where parent= '"
				// + function.getId().toString() + "' order by seq";
				// WebMarkProcessCollection entrys = WebMarkProcessFactory
				// .getRemoteInstance().getWebMarkProcessCollection(oql);
				WebMarkProcessCollection entrys = getProcessEntrys(function
						.getId().toString());
				function.getProcessEntrys().addCollection(entrys);
			}
		}
	}

	private WebMarkProcessCollection getProcessEntrys(String id)
			throws Exception {
		String oql = "select * where parent= '" + id.toString()
				+ "' order by seq";
		WebMarkProcessCollection entrys = WebMarkProcessFactory
				.getRemoteInstance().getWebMarkProcessCollection(oql);
		return entrys;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.client.EditUI#getBizInterface()
	 */
	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return WebMarkSchemaFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return bizName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected void afterSubmitAddNew() {
		super.afterSubmitAddNew();
		kdtProcessEntrys.removeRows();
		storeFields();
		initOldData(editData);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		
		super.verifyInput(e);
		
		//添加项目是否为空的校验
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSellProject);
		
		//增加功能名称不能为空和不能重复的判断
		for(int i=0;i<kdtFunctionEntrys.getRowCount();i++){
			if(kdtFunctionEntrys.getRow(i)!=null){
    		IRow row = kdtFunctionEntrys.getRow(i);
    		for(int j=i+1;j<kdtFunctionEntrys.getRowCount();j++){
    			if(kdtFunctionEntrys.getRow(j)!=null){
    				IRow r = kdtFunctionEntrys.getRow(j);
    				if(row.getCell("functionEntrys.functionName").getValue()==null || r.getCell("functionEntrys.functionName").getValue()==null){
    				   MsgBox.showWarning(this, "功能名称不能为空！");
      				   SysUtil.abort();
    				}
    			   if(row.getCell("functionEntrys.functionName").getValue().toString().trim().equals(r.getCell("functionEntrys.functionName").getValue().toString().trim())){
    				   MsgBox.showWarning(this, "功能名称不能重复！");
    				   SysUtil.abort();
    			   }
    			}
    		  }
		   }
		}
		
		//增加步骤名称不能重复的判断 xiaoao_liu
		for(int i=0;i<kdtProcessEntrys.getRowCount();i++){
			if(kdtProcessEntrys.getRow(i)!=null){
    		IRow row = kdtProcessEntrys.getRow(i);
    		for(int j=i+1;j<kdtProcessEntrys.getRowCount();j++){
    			if(kdtProcessEntrys.getRow(j)!=null){
    				IRow r = kdtProcessEntrys.getRow(j);
    				if(row.getCell("processName").getValue()==null || r.getCell("processName").getValue()==null){
    				   MsgBox.showWarning(this, "步骤名称不能为空！");
      				   SysUtil.abort();
    				}
    			   if(row.getCell("processName").getValue().toString().trim().equals(r.getCell("processName").getValue().toString().trim())){
    				   MsgBox.showWarning(this, "步骤名称不能重复！");
    				   SysUtil.abort();
    			   }
    			}
    		  }
		   }
		}
	}
}