/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.CheckNodeInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class CheckNodeListUI extends AbstractCheckNodeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CheckNodeListUI.class);
    
    /**
     * output class constructor
     */
    public CheckNodeListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
		//批量删除多行
		if (SysContext.getSysContext().getCurrentOrgUnit() != null
				//判断当前组织是否为所在管理单元。
				//&& OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()))
				&& OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))
		{
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
		}else{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    }
    
	public void onShow() throws Exception {
		super.onShow();
		getMainTable().setColumnMoveable(false);
		// 隐藏启用禁用按钮
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnLocate.setVisible(false);

	}
    
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

  
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	verifyRefSch();
        super.actionRemove_actionPerformed(e);
    }
	private void verifyRefSch() {
		FDCScheduleTaskCollection cols = FDCScheduleBaseHelper.getFDCSchTask();
		Map newMap = new HashMap();
		for (int i = 0; i < cols.size(); i++) {
			FDCScheduleTaskInfo tasks = cols.get(i);
//			newMap.put(taskNode.getId().toString(), taskNode);
			//name控制
			if (tasks.isIsCheckNode()) /* modified by zhaoqin on 2014/06/20 */
				newMap.put(tasks.getName().toString(), tasks);
		}
		if (newMap.isEmpty()) {
			return;
		}
		ArrayList alist = this.tblMain.getSelectManager().getBlocks();
		int top = -1;
		int bottom = -1;
		if(alist != null){			
			top = ((KDTSelectBlock)alist.get(0)).getTop();
			bottom = ((KDTSelectBlock)alist.get(0)).getBottom();
		}
		Map msgMap = new HashMap();
		if( top != -1 && bottom != -1){
			int rowIndex = top;
			if (rowIndex < 0) {
				return;
			}
			for(int j=top ; j < bottom + 1; j++ ){
				String key=this.tblMain.getRow(j).getCell("name").getValue().toString();
	             if(newMap.containsKey(key)){
	  				msgMap.put(key, newMap.get(key));
	              }
			}
		}
			
//		GlobalTaskNodeCollection nodes = FDCScheduleBaseHelper.getGlobalTaskNode();
//	    GlobalTaskNodeInfo task = null;
//	    int size = nodes.size();
//		for (int i = 0; i < size; i++) {
//             task = nodes.get(i);
//             //name控制
//             if(newMap.containsKey(task.getName().toString())){
// 				newMap.remove(task.getName().toString());
//             }
////			if (StringUtils.isEmpty(task.getSrcGroupNode())) {
////				continue;
////			} else {
////				newMap.remove(task.getSrcGroupNode());
////			}
//		}
		
		if (!msgMap.isEmpty()) {
			StringBuffer errMsg = new StringBuffer();
			Set set = msgMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				FDCScheduleTaskInfo reinfo =(FDCScheduleTaskInfo) ((Entry) it.next()).getValue();
				errMsg.append("考核节点  ");
				errMsg.append(reinfo.getName());
				errMsg.append(" 已被主项计划 ");
//				errMsg.append(reinfo.getTemplate().getNumber());
				errMsg.append(reinfo.getSchedule().getVersionName());
				errMsg.append(" 引用！");
				errMsg.append("\n");
			}
			FDCMsgBox.showDetailAndOK(this, "当前考核节点已被主项计划引用，不可删除!", errMsg.toString(), MsgBox.ICONERROR);
			abort();
		}
		newMap.clear();
	}
    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
    	String id = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	CheckNodeInfo  info  = null;
    	try {
			info = CheckNodeFactory.getRemoteInstance().getCheckNodeInfo(new ObjectUuidPK(BOSUuid.read(id)));
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		} catch (UuidException e) {
			handUIException(e);
		}
    	return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CheckNodeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CheckNodeEditUI.class.getName();
	}

}