/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideFactory;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @(#)							
* 版权：		金蝶国际软件集团有限公司版权所有<p> 描述： 标准任务指引
 * 
 * @author 车忠伟
 * @version EAS7.0
 * @createDate 2011-8-7
 * @see
 */
public class StandardTaskGuideNewListUI extends AbstractStandardTaskGuideNewListUI {
	private static final Logger logger = CoreUIObject.getLogger(StandardTaskGuideNewListUI.class);

	/**
	 * output class constructor
	 */
	public StandardTaskGuideNewListUI() throws Exception {
		super();
	}

	/**
	 * 初始化按钮
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnPlanLead.setEnabled(true);
		this.btnPlanLead.setVisible(true);
		this.btnView.setEnabled(true);

		this.btnPlanLead.setIcon(EASResource.getIcon("imgTbtn_input"));

	}

	/**
	 * 本方法主要实现查询后将名称列设置单元格树<br>
	 * 1、取得原名称<br>
	 * 2、取得树级次和是否明细节点<br>
	 * 3、构建单元格树节点并set到单元格<br>
	 * 4、最后添加所有任务行
	 * 
	 * @description 查看数据
	 * @author 车忠伟
	 * @createDate 2011-8-7
	 * @version EAS7.0
	 * @see
	 */

	protected void execQuery() {
		super.execQuery();
		fullTable();
		addFirstRow();
	}

	public void fullTable() {
		int count = tblMain.getRowCount();
		for (int i = 0; i < count; i++) {
			// 取得原名称
			IRow row = this.tblMain.getRow(i);
			String value = (String) row.getCell("name").getValue();
			// 取得树级次和是否明细节点
			boolean isLeaf = ((Boolean) row.getCell("isLeaf").getValue()).booleanValue();
			int level = ((Integer) row.getCell("level").getValue()).intValue();
			CellTreeNode treeNode = new CellTreeNode();
			treeNode.setValue(value);// 显示的值，此处是‘任务名称’字符串
			treeNode.setTreeLevel(level);// 级次，从0开始，此处为任务的树级次
			treeNode.setHasChildren(!isLeaf);
			treeNode.isCollapse();
			treeNode.addClickListener(new NodeClickListener() {
				public void doClick(CellTreeNode source, ICell cell, int type) {
					tblMain.revalidate();
				}
			});

			row.getStyleAttributes().setLocked(true);
			row.getCell("name").getStyleAttributes().setLocked(false);
			row.getCell("name").setValue(treeNode);// 设置前面构建的单元格树到表
		}
		// 最后添加所有任务行
		// IRow indexRow = tblMain.addRow(0);
		// indexRow.getCell("name").setValue("所有任务");
		// indexRow.getCell("level").setValue(new Integer(0));
		// indexRow.getCell("isLeaf").setValue(new Boolean(false));

//		tblMain.setRowCount(tblMain.getBody().getRows().size());
	}

	/**
	 * @description 添加所有任务行
	 * @author 车忠伟
	 * @createDate 2011-8-7
	 * @version EAS7.0
	 * @see
	 */
	public void addFirstRow() {
		IRow indexRow = tblMain.addRow(0);
		int rowsIndex = indexRow.getRowIndex();
		if (rowsIndex == 0) {
			KDTableUtil.setSelectedRow(tblMain, 0);
			this.btnView.setEnabled(true);
		}
		indexRow.getCell("name").setValue("所有任务");
		tblMain.setRowCount(tblMain.getBody().getRows().size());

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		int rowNO = tblMain.getSelectManager().getActiveRowIndex();
		if (rowNO > 0) {// 是否选中行
			IRow row = this.tblMain.getRow(rowNO);
			getUIContext().put("upId", row.getCell("id").getValue().toString());
			// findHasParentAllNumber(row);

		} else {
			getUIContext().put("upId", "upid");
			// findAllNumber();
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * // * @description 找出有上一级的同级的所有编码 // * @author 车忠伟 // * @createDate
	 * 2011-8-17 // * @version EAS7.0 // * @see //
	 */
	// public void findHasParentAllNumber(IRow row)throws Exception{
	//if(row.getCell("id").getValue()!=null&&"".equals(row.getCell("id").getValue
	// ().toString().trim())){
	// EntityViewInfo view = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// filter.appendFilterItem("parent.id",
	// row.getCell("id").getValue().toString().trim());
	// view.setFilter(filter);
	// RESchTemplateTaskCollection templateTaskCollection =
	// RESchTemplateTaskFactory
	// .getRemoteInstance().getRESchTemplateTaskCollection(view);
	// String[] numberArray = new String[templateTaskCollection.size()];
	// for(int temp = 0; temp < templateTaskCollection.size(); temp ++){
	// RESchTemplateTaskInfo schTemplateTaskInfo =
	// (RESchTemplateTaskInfo)templateTaskCollection.get(temp);
	// numberArray[temp] = schTemplateTaskInfo.getNumber().toString();
	// }
	// this.getUIContext().put("NUMBERARRAY", numberArray);
	// }
	// }
	// /**
	// * @description 找出级次为1的所有编码
	// * @author 车忠伟
	// * @createDate 2011-8-17
	// * @version EAS7.0
	// * @see
	// */
	// public void findAllNumber() throws Exception {
	// EntityViewInfo view = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// filter.appendFilterItem("level", new Integer(1));
	// view.setFilter(filter);
	// StandardTaskGuideCollection collection = StandardTaskGuideFactory
	// .getRemoteInstance().getStandardTaskGuideCollection(view);
	// String[] numberArray = new String[collection.size()];
	// for (int temp = 0; temp < collection.size(); temp++) {
	// StandardTaskGuideInfo schTemplateTaskInfo = (StandardTaskGuideInfo)
	// collection
	// .get(temp);
	// numberArray[temp] = schTemplateTaskInfo.getNumber().toString();
	// }
	// this.getUIContext().put("NUMBERARRAY", numberArray);
	// }
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// int rowNO = tblMain.getSelectManager().getActiveRowIndex();
		// if (rowNO > 0) {
		// IRow row = this.tblMain.getRow(rowNO);
		// String guideID = row.getCell("id").getValue().toString();
		// int level = ((Integer) row.getCell("level").getValue()).intValue();
		// // if (!checkRecommend(guideID)) {
		// // FDCMsgBox.showWarning("该标准指引文档已经被引用，不许修改");
		// // SysUtil.abort();
		// // }
		// if (level == 1) {
		// findAllNumber();
		// } else {
		// StandardTaskGuideInfo info = StandardTaskGuideFactory
		// .getRemoteInstance().getStandardTaskGuideInfo(
		// new ObjectStringPK(guideID));
		// String parentID = info.getParent().getId().toString();
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.appendFilterItem("parent.id", parentID);
		// filter.getFilterItems().add(
		// new FilterItemInfo("id", info.getId().toString(),
		// CompareType.NOTEQUALS));
		// view.setFilter(filter);
		// StandardTaskGuideCollection collection = StandardTaskGuideFactory
		// .getRemoteInstance().getStandardTaskGuideCollection(
		// view);
		// String[] numberArray = new String[collection.size()];
		// for (int temp = 0; temp < collection.size(); temp++) {
		// StandardTaskGuideInfo taskInfo = (StandardTaskGuideInfo) collection
		// .get(temp);
		// numberArray[temp] = taskInfo.getNumber().toString();
		// }
		// this.getUIContext().put("NUMBERARRAY", numberArray);
		// this.getUIContext().put("parent", info.getParent());
		//
		// }
		// }
		int rowNO = tblMain.getSelectManager().getActiveRowIndex();
		if (rowNO == 0) {
			FDCMsgBox.showWarning("你选中了所有任务所在行，该行不能修改，请重新选择");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * @description 屏蔽点击表头，禁止以某列排序
	 * @author 车忠伟
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	protected boolean isCanOrderTable() {
		return false;
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int rowNo = tblMain.getSelectManager().getActiveRowIndex();
		if (rowNo == 0) {// 选择虚拟行

			FDCMsgBox.showWarning("你选择的是第0行，该行没有数据");
			SysUtil.abort();
		}
		super.actionView_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {

		int rows[] = KDTableUtil.getSelectedRows(tblMain);
		IObjectPK[] pk = new IObjectPK[rows.length];
		boolean isNeedShowConfirm = true;
		StandardTaskGuideInfo taskguideInfo = null;
		for (int i = 0; i < rows.length; i++) {
			if (rows[i] == 0) {
				FDCMsgBox.showWarning("你选中了所有任务所在行，该行不能删除，请重新选择");
				SysUtil.abort();
			}
			if (isNeedShowConfirm && !confirmRemove()) {// 是否删除
				SysUtil.abort();
			}
			isNeedShowConfirm = false;
			IRow row = this.tblMain.getRow(rows[i]);
			String guideID = row.getCell("id").getValue().toString().trim();
			taskguideInfo = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(new ObjectStringPK(guideID));
			pk[i] = new ObjectUuidPK(guideID);
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.appendFilterItem("standardTask.id", guideID);
//			view.setFilter(filter);
//			FDCScheduleTaskCollection scheduleTaskCollection = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
//			if (scheduleTaskCollection != null && scheduleTaskCollection.size() > 0) {
//				FDCMsgBox.showWarning("该标准指引文档已经被引用，不允许删除");
//				SysUtil.abort();
//			}
//			 if (!taskguideInfo.isIsLeaf()) {
//				FDCMsgBox.showWarning("当前记录下存在子集，不允许删除");
//				SysUtil.abort();
//			}

		} 
		if (pk.length > 0) {
			StandardTaskGuideFactory.getRemoteInstance().delete(pk);
		}
		
		
//		if (pk.length > 0) {
//			for (int j = 0; j < pk.length; j++) {
//				String pkId = pk[j].toString().trim();
//				deleteImg(pkId);
//				deleteEntry(pkId);
//			}
//			StandardTaskGuideFactory.getRemoteInstance().delete(pk);
//		}
//
//		if (hasChild(taskguideInfo)) {// 是否有子节点，若没有则将isLeaf设为true
//			String parentId = taskguideInfo.getParent().getId().toString();
//			// StandardTaskGuideInfo parentInfo = taskguideInfo.getParent();
//			if (parentId != null || !"".equals(parentId)) {
//				StandardTaskGuideInfo parentInfo = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(new ObjectStringPK(parentId));
//				parentInfo.setIsLeaf(true);
//				CoreBaseCollection corebasecollection = new CoreBaseCollection();
//				corebasecollection.add(parentInfo);
//				StandardTaskGuideFactory.getRemoteInstance().update(corebasecollection);
//			}
//		}
		this.refreshList();
	}

	/**
	 * 
	 * @description 根据ID删除图片
	 * @author 车忠伟
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */
	public void deleteImg(String id) throws Exception {
		if (FDCAttachmentUtil.getAttachmentsInfo(id) != null) {
			AttachmentCollection collection = FDCAttachmentUtil.getAttachmentsInfo(id);
			if (collection.size() > 0) {
				AttachmentInfo info = collection.get(0);
				FDCAttachmentUtil.deleteAtt(id, info.getId().toString());
			}

		}
	}

	/**
	 * 
	 * @description 删除分录关联附件
	 * @author 车忠伟
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */
	public void deleteEntry(String id) throws Exception {
		StandardTaskGuideInfo info = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(new ObjectStringPK(id));
		if (info.getEntrys().size() > 0) {
				for (int i = 0; i < info.getEntrys().size(); i++) {
				StandardTaskGuideEntryInfo entryInfo = info.getEntrys().get(i);
				String entryId = entryInfo.getId().toString();
				if (FDCAttachmentUtil.getAttachmentsInfo(entryId) != null) {
					AttachmentCollection collection = FDCAttachmentUtil.getAttachmentsInfo(entryId);
					if (collection.size() > 0) {
						AttachmentInfo attachmentInfo = collection.get(0);
						FDCAttachmentUtil.deleteAtt(entryId, attachmentInfo.getId().toString());
					}
				}
			}
		}
	}

	/**
	 * 
	 * @description 判断是否有子节点
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public boolean hasChild(StandardTaskGuideInfo guideInfo) throws Exception {
		boolean child = false;
		if (guideInfo.getParent() != null) {
			StandardTaskGuideInfo parentInfo = guideInfo.getParent();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId()));
			view.setFilter(filter);
			StandardTaskGuideCollection collection = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
			if (collection.size() > 0) {
				child = false;
			} else {
				child = true;
			}
		}

		return child;
	}

	/**
	 * @description 该方法是在删除是判断是否有下一级任务时，是否连下一级任务一起删除
	 * @author 车忠伟
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	protected boolean statementRemove() {
		return MsgBox.isYes(MsgBox.showConfirm2(this, "你选择的删除的任务中有下级任务，是否确定删除？"));
	}

	/**
	 * @description 是否被其他功能引用
	 * @author 车忠伟
	 * @createDate 2011-8-15
	 * @version EAS7.0
	 * @see
	 */
	public boolean checkRecommend(String strID) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("standardTask.id", strID);
		view.setFilter(filter);
		RESchTemplateTaskCollection schTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
		if (schTemplateTaskCollection.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @description 导入计划模板
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public void actionPlanLead_actionPerformed(ActionEvent e) throws Exception {
		// 先删除再导入 modify by duhongming
		if (this.tblMain.getRowCount() > 1) {
			FDCMsgBox.showInfo(this, "已存在标准任务指引，不允许导入!");
			SysUtil.abort();
		} 
		
		refresh(null);
			// 界面跳转
			StandardTaskGuideNewListUI standatrdUI = this;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, getUIContext());
			uiContext.put("standatrdUI", standatrdUI);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(RESchTemplateTaskListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
	}

	/**
	 * 
	 * @description 数据导入
	 * @author 车忠伟
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public void templateImport(String templateID) throws Exception {
		if (templateID != null) {// 不为空
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("template.id", templateID));
			view.setFilter(filter);
			RESchTemplateTaskCollection collection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
			StandardTaskGuideInfo guideInfo = null;
			/** 获得模板下数据对象,属性拷贝 **/
			Map map = new HashMap();
			RESchTemplateTaskCollection col = new RESchTemplateTaskCollection();
			for (Iterator it = collection.iterator(); it.hasNext();) {
				RESchTemplateTaskInfo taskInfo = (RESchTemplateTaskInfo) it.next();
				if (taskInfo.getParent() == null) {
					guideInfo = copyProperties(taskInfo);
					StandardTaskGuideFactory.getRemoteInstance().addnew(guideInfo);
					// key=第一级的模板任务ID，value=第一级的标准任务ID
					map.put(taskInfo.getId().toString(), guideInfo.getId().toString());
				} else {
					// 放入模板任务具有上级的集合
					col.add(taskInfo);
				}
			}
			addNewData(col, map);
			refresh(null);
			
		}
	}

	/**
	 * 
	 * @description 数据导入(递归)
	 * @author 车忠伟 modify by duhongming 主要对递归的逻辑进行了修改
	 * @createDate 2011-8-9
	 * @version EAS7.0
	 * @see
	 */
	public void addNewData(RESchTemplateTaskCollection col, Map map) throws Exception {
		RESchTemplateTaskCollection col2 = new RESchTemplateTaskCollection();
		Map tempMap = new HashMap();
		if (map.size() > 0) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				RESchTemplateTaskInfo taskInfo = (RESchTemplateTaskInfo) it.next();
				RESchTemplateTaskInfo parent = taskInfo.getParent();
				if (map.containsKey(parent.getId().toString())) {
					String guideParentID = (String) map.get(parent.getId().toString());
					StandardTaskGuideInfo taskGuideInfo = copyProperties(taskInfo);
					taskGuideInfo.setParent(StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(new ObjectStringPK(guideParentID)));
					tempMap.put(taskInfo.getId().toString(), taskGuideInfo.getId().toString());
					StandardTaskGuideFactory.getRemoteInstance().addnew(taskGuideInfo);
				} else {
					col2.add(taskInfo);
				}
			}
			map = tempMap;
			addNewData(col2, map);
		}
	}

	private StandardTaskGuideInfo copyProperties(RESchTemplateTaskInfo taskInfo) {
		StandardTaskGuideInfo guideInfo = new StandardTaskGuideInfo();
		guideInfo.setId(BOSUuid.create((new StandardTaskGuideInfo()).getBOSType()));
		guideInfo.setName(taskInfo.getName());
		guideInfo.setLongNumber(taskInfo.getLongNumber());
		guideInfo.setNumber(taskInfo.getNumber());
		guideInfo.setLevel(taskInfo.getLevel());
		guideInfo.setIsLeaf(taskInfo.isIsLeaf());
		guideInfo.setTaskType(taskInfo.getTaskType());
		guideInfo.setDescription(taskInfo.getDescription());
		return guideInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return StandardTaskGuideFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return StandardTaskGuideNewEditNewUI.class.getName();
	}


	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
//		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.windowTitle = "标准任务指引";
		this.btnPrint.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnView.setEnabled(true);
		// addFirstRow();
		// fullTable();

		// 刷新页面
		refresh(null);

		canOprate();
	}

	/**
	 * @description 判断当前组织是否是集团，只有集团才能进行增、删、改
	 * @author 车忠伟
	 * @createDate 2011-09-06
	 * @param
	 * @return
	 * 
	 * @version EAS6.0
	 * @see
	 */
	private void canOprate() {
		if (!("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))) {
			this.btnAddNew.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.btnPrint.setEnabled(false);
			this.btnPrintPreview.setEnabled(false);
			this.menuItemPrint.setEnabled(false);
			this.menuItemPrintPreview.setEnabled(false);
			btnPlanLead.setEnabled(false);
			
		}
	}

	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		tblMain.removeRow(0);
		super.actionLocate_actionPerformed(e);
		addFirstRow();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected String[] getLocateNames() {
		String locateNames[] = new String[2];
		locateNames[1] = "longNumber";
		locateNames[0] = "name";
		return locateNames;
	}
	
    

}