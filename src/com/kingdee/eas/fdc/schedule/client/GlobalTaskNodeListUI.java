/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.fd2.gui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeFactory;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.util.UuidException;


/**
 * 
 * @(#)						
 * 版权：金蝶国际软件集团有限公司版权所有 描述： 集团管控节点序时簿界面
 * 
 * @author 刘新桐
 * @version EAS7.0.5
 * @createDate 2013-4-27
 * @see
 */
public class GlobalTaskNodeListUI extends AbstractGlobalTaskNodeListUI
{
	private static final Logger logger = CoreUIObject.getLogger(GlobalTaskNodeListUI.class);
	
    
    public GlobalTaskNodeListUI() throws Exception
    {
        super();
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
//        checkSelected();
    	super.actionAddNew_actionPerformed(e);
        
    }


    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {   	
    	verifyRefRESchTemplate();
		super.actionRemove_actionPerformed(e);
    }
    
    protected FDCDataBaseInfo getBaseDataInfo() {
    	String id = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
    	GlobalTaskNodeInfo  info  = null;
    	try {
			info = GlobalTaskNodeFactory.getRemoteInstance().getGlobalTaskNodeInfo(new ObjectUuidPK(BOSUuid.read(id)));
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
		return GlobalTaskNodeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {		
		return GlobalTaskNodeEditUI.class.getName();
	}
	
	public void onLoad() throws Exception {
		this.tblMain.checkParsed();
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
		for(int i = 0;i < tblMain.getColumnCount();i ++){
			tblMain.getColumn(i).setMergeable(false);
		}		

		this.tblMain.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
		super.onLoad();
//		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		//批量删除多行
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
}
	
	//去掉排序功能
//	protected boolean isCanOrderTable() {
//		return false;
//	}
	
	public void onShow() throws Exception {
		super.onShow();
		getMainTable().setColumnMoveable(false);
		// 隐藏启用禁用按钮
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);

	}
	
	protected void afterTableFillData(KDTDataRequestEvent e) {	
        super.afterTableFillData(e);               
 
	}
	

	protected boolean isSystemDefaultData(int activeRowIndex) {
		return false;
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		//增加这些事件，他们编辑有新增按钮。
		if(e.getActionCommand().contains("AddNew")||e.getActionCommand().contains("View")||e.getActionCommand().contains("Double Clicked")||e.getActionCommand().contains("Edit")){
			//取出存在编码
			uiContext.put("GTNNodes", getExistNode());
		}
	}
	
	/**
	 * 如果存在编码行不为空，增加到存在编码列表
	 */
	private List getExistNode() {
		List<String> numberList = new ArrayList<String>();
		for(int i=0;i<tblMain.getRowCount();i++){
			if(tblMain.getRow(i).getCell("number").getValue()!=null){
				numberList.add(tblMain.getRow(i).getCell("number").getValue().toString());
			}
		}
		return numberList;
	}
	
	private void verifyRefRESchTemplate() {
		RESchTemplateTaskCollection cols = FDCScheduleBaseHelper.getRESchTemplateTask();
		Map newMap = new HashMap();
		for (int i = 0; i < cols.size(); i++) {
			RESchTemplateTaskInfo tasks = cols.get(i);
//			newMap.put(taskNode.getId().toString(), taskNode);
			//name控制
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
		
		/* modified by zhaoqin for BT829256 on 2014/06/09 */
		if (!msgMap.isEmpty()) {
			StringBuffer errMsg = new StringBuffer();
			Set set = msgMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				RESchTemplateTaskInfo reinfo =(RESchTemplateTaskInfo) ((Entry) it.next()).getValue();
				errMsg.append("模板任务 ");
				errMsg.append(reinfo.getName());
				errMsg.append(" 已被主项计划模板 ");
//				errMsg.append(reinfo.getTemplate().getNumber());
				errMsg.append(reinfo.getTemplate().getName());
				errMsg.append(" 引用！");
				errMsg.append("\n");
			}
			FDCMsgBox.showDetailAndOK(this, "当前模板任务已被主项计划模板引用，不可删除!", errMsg.toString(), MsgBox.ICONERROR);
			abort();
		}
		newMap.clear();
	}
	public void beforeActionPerformed(ActionEvent e) {
		super.beforeActionPerformed(e);
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(!e.getActionCommand().contains("AddNew")&&!e.getActionCommand().contains("Print")&&!e.getActionCommand().contains("PrintPreview")){
			if(e.getActionCommand().contains("Edit")&&tblMain.getRow(rowIndex).getUserObject() != null){
				FDCMsgBox.showError("当前行不能执行此操作！");
				abort();
			}else if(e.getActionCommand().contains("Remove")&&tblMain.getRow(rowIndex).getUserObject()!= null){
				FDCMsgBox.showError("当前行不能执行此操作！");
				abort();
			}else if(e.getActionCommand().contains("Double Clicked")&&tblMain.getRow(rowIndex).getUserObject()!= null){
				FDCMsgBox.showError("当前行不能执行此操作！");
				abort();
			}
		}
	}
}