/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProgrammingContractF7UI extends AbstractProgrammingContractF7UI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingContractF7UI.class);
    private boolean isCancel = true;

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}
    /**
     * output class constructor
     */
    public ProgrammingContractF7UI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		menuBar.setVisible(false);
		toolBar.setVisible(false);
		super.onLoad();
		tblMain.getColumn("sortNumber").getStyleAttributes().setHided(true);
		tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("controlAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("balance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("controlBalance").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		btnConfirm.setEnabled(true);
		btnExit.setEnabled(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		Boolean isDisplay=true;
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISDISPLAYPCAMOUNT", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISDISPLAYPCAMOUNT")!=null){
				isDisplay=Boolean.parseBoolean(hmAllParam.get("FDC_ISDISPLAYPCAMOUNT").toString());
			}else{
				isDisplay=true;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		tblMain.getColumn("amount").getStyleAttributes().setHided(!isDisplay);
		tblMain.getColumn("balance").getStyleAttributes().setHided(!isDisplay);
	}
    
    public void onShow() throws Exception {
		super.onShow();
		setTableDepth();
	}
    
    protected void setTableDepth(){
    	tblMain.addKDTMouseListener(new KDTSortManager(tblMain));
		tblMain.getSortMange().setSortAuto(false);
		List rows = this.tblMain.getBody().getRows();
        Collections.sort(rows, new TableCellComparator(tblMain.getColumnIndex("sortNumber"), KDTSortManager.SORT_ASCEND));
    	int maxLevel = 0;
		int[] levelArray = new int[tblMain.getRowCount()];
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank+name.toString().trim());
			}
			if(((Boolean)row.getCell("isWTCiting").getValue()).booleanValue()){
				row.getStyleAttributes().setBackground(new java.awt.Color(128,255,128));
			}
			if(((Boolean)row.getCell("isCiting").getValue()).booleanValue()){
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
			}
		}

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		tblMain.getColumn("level").getStyleAttributes().setHided(true);
		this.tblMain.setRefresh(true);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    private String setNameIndent(int level){
		StringBuffer blank = new StringBuffer("");
		for(int i = level ; i > 1 ; i--){
			blank.append("        ");
		}
		return blank.toString();
	}
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		Map map = this.getUIContext();
		if(map.get("view") != null){
			EntityViewInfo entityView = (EntityViewInfo)map.get("view");
			EntityViewInfo viewInfo = this.getMainQuery();
			viewInfo.setFilter(entityView.getFilter());
		}
	}
	
    
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				confirm();
				setCancel(false);
			}
		}
    }
    
    private String getSelectedId() {
		int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
		if (actRowIdx < 0) {
			return null;
		} else if (tblMain.getCell(actRowIdx, "id").getValue() != null) {
			return tblMain.getCell(actRowIdx, "id").getValue().toString();
		} else {
			return null;
		}
	}
    
    public ProgrammingContractInfo getData() throws Exception {
    	ProgrammingContractInfo info = null;
    	String id = getSelectedId();
    	if(id == null) 
    		return null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSelector().add("longNumber");
		view.getSelector().add("amount");
		view.getSelector().add("number");
		view.getSelector().add("controlAmount");
		view.getSelector().add("balance");
		view.getSelector().add("controlBalance");
		view.getSelector().add("project.id");
		view.getSelector().add("project.isEnabled");
		view.getSelector().add("contractType.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", id));
		view.setFilter(filter);
		info = (ProgrammingContractInfo) ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view).get(0);
		disposeUIWindow();
		return info;
	}
    
    private void confirm() throws Exception {
		checkSelected();
		getData();
		setCancel(true);
	}

	public void checkSelected() {
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0 || tblMain.getSelectManager().getActiveRowIndex() < 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
//		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
//		int level = new Integer(tblMain.getCell(rowIndex, "level").getValue().toString()).intValue();
//		if(rowIndex < tblMain.getRowCount() - 1){
//			int level_next = new Integer(tblMain.getCell(rowIndex+1, "level").getValue().toString()).intValue();
//			if (level < level_next) {
//				MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
//				SysUtil.abort();
//			}
//		}
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		confirm();
		setCancel(false);
	}
	
	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
		setCancel(true);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

}