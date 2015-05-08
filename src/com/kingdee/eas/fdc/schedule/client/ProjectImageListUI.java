/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.encoders.SunJPEGEncoderAdapter;
import com.kingdee.bos.ctrl.freechart.util.PngEncoder;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FileNameExtensionFilter;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.schedule.ProjectImageCollection;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectImageFactory;
import com.kingdee.eas.fdc.schedule.ProjectImageInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.image.ImageSelectChangedLisener;
import com.kingdee.eas.fdc.schedule.image.KDDynamicShowImagePanel;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ProjectImageListUI extends AbstractProjectImageListUI implements ImageSelectChangedLisener {
	private static final Logger logger = CoreUIObject.getLogger(ProjectImageListUI.class);
	// private static final Action actionDownloadImage = null;
	// 缩略图map中的KEY值与原始图片的ID的对应关系即 Map imgIds<Integer,BOSUUid>
	private Map imgIds = null;
	// 缩略图Map中的key值，与界面两个列表中对应的形象进度单据的ID的对应关系Map imgBillIds<Integer,BOSUuid>
	private Map imgNames = null;
	private Map imgBillIds = null;
	// 单据ID，对应的缩略图Map中的key值的对应关系Map billIdImage<BOSUuid,Integer>
	private Map billIdImage = null;
	// 缩略图
	private Map imgs = null;
	// id对应大图
	private Map originalImg = null;

	KDDynamicShowImagePanel myPanel = null;
	// 分辨具体Table tblMain1 tblMain2
	private int tabTemp = 2;

	public ProjectImageListUI() throws Exception {
		super();
	}

	protected void initWorkButton() {
		super.initWorkButton();

		btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);

		menuItemCancel.setVisible(false);
		menuItemCancelCancel.setVisible(false);

		btnCreateTo.setVisible(false);
		btnTraceDown.setVisible(false);
		btnTraceUp.setVisible(false);

		btnClose.setVisible(false);
		btnUnClose.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);

		menuItemCopyTo.setVisible(false);
		menuItemCreateTo.setVisible(false);
		// 取消审批功能
		btnAudit.setVisible(false);
		btnUnAudit.setVisible(false);
		btnWorkFlowG.setVisible(false);

		btnRefresh.setVisible(false);
		btnLocate.setVisible(false);
		btnAttachment.setVisible(false);
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);

		tblMain.setVisible(false);
		btnEdit.setVisible(true);
		btnEdit.setEnabled(true);
		// 新增图片下载
		btnDownloadImage.setEnabled(true);
		btnDownloadImage.setIcon(EASResource.getIcon("imgTbtn_showpicture"));
		treeMain.expandOnLevel(10);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ProjectImageInfo proInfo = new ProjectImageInfo();
		proInfo = tabSelectView();
		objView(proInfo, "view");
		refresh(e);
		treeMain_valueChanged(null);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ProjectImageInfo proInfo = new ProjectImageInfo();
		proInfo = tabSelectView();
		objView(proInfo, "edit");
		refresh(e);
		treeMain_valueChanged(null);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		verifyProject();
		super.actionAddNew_actionPerformed(e);
		treeMain_valueChanged(null);
	}

	/**
	 * 显示编辑UI
	 * 
	 * @author 王维强 2011-08-11
	 * @param info
	 * @throws Exception
	 */
	private void objView(ProjectImageInfo info, String state) throws Exception {
		if (tabTemp == 2) {
			tblMain = tblMain2;
		} else {
			tblMain = tblMain1;
		}
		String id = tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
		String editUIName = getEditUIName();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, id);
		if (info != null)
			uiContext.put("CurProject", info.getProject());
		/*
		 * 创建UI对象并显示
		 */
		IUIWindow uiWindow = this.getUIWindow();
		String newWindowOprtState = OprtState.VIEW;
		if (state.equals("edit")) {
			newWindowOprtState = OprtState.EDIT;
		}
		IUIWindow AdjustUiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(editUIName, uiContext, null, newWindowOprtState);

		if (AdjustUiWindow != null) {
			AdjustUiWindow.show();
		}
		if (isDoRefresh(uiWindow)) {
			if (UtilRequest.isPrepare("ActionRefresh", this))
				prepareRefresh(null).callHandler();
			isModify = true;
			setLocatePre(false);

			setLocatePre(true);
		}
	}

	/**
	 * 选择对应Table列表查看
	 * 
	 * @author 王维强 2011-08-12
	 * @return
	 */
	private ProjectImageInfo tabSelectView() {
		int actRowIdx = 0;
		if (tabTemp == 2) {
			actRowIdx = tblMain2.getSelectManager().getActiveRowIndex();
		} else {
			actRowIdx = tblMain1.getSelectManager().getActiveRowIndex();
		}
		if (actRowIdx < 0) {
			return null;
		}
		int actRowReqInx = this.tblMain2.getSelectManager().getActiveRowIndex();
		if (actRowReqInx < 0) {
			return null;
		}
		ProjectImageInfo proInfo = null;
		if (tblMain2.getRow(this.tblMain2.getSelectManager().getActiveRowIndex()) != null
				&& tblMain2.getRow(this.tblMain2.getSelectManager().getActiveRowIndex()).getUserObject() != null) {
			proInfo = (ProjectImageInfo) tblMain2.getRow(this.tblMain2.getSelectManager().getActiveRowIndex()).getUserObject();
		} else if (tblMain1.getRow(this.tblMain1.getSelectManager().getActiveRowIndex()) != null
				&& tblMain1.getRow(this.tblMain1.getSelectManager().getActiveRowIndex()).getUserObject() != null) {
			proInfo = (ProjectImageInfo) tblMain1.getRow(this.tblMain1.getSelectManager().getActiveRowIndex()).getUserObject();
		}

		return proInfo;
	}

	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 关键证照点击事件
	 * 
	 * @author 王维强 2011-08-13
	 * @param e
	 * @throws Exception
	 */
	protected void tblMain1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		tabTemp = 1;
		if (e.getClickCount() == 2) {
			actionView_actionPerformed(null);
		}
	}

	/**
	 * 形象进度点击事件
	 * 
	 * @author 王维强 2011-08-13
	 * @param e
	 * @throws Exception
	 */
	protected void tblMain2_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		tabTemp = 2;
		if (e.getClickCount() == 2) {
			actionEdit_actionPerformed(null);
		}
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	protected void tblMain1_tableSelectChanged(KDTSelectEvent e) throws Exception {
		tabTemp = 1;
		tblMain = tblMain1;
		int id = tblMain.getSelectManager().getActiveRowIndex();
		tblMain2.getSelectManager().removeAll();
		String uuid = (String) tblMain.getCell(id, "id").getValue();
		
		/* modified by zhaoqin for R140925-0004 on 2014/11/12 */
		initImagePanel();
		//locateSelectedImage(BOSUuid.read(uuid));
	}
	/**
	 * 验证明细工程项目
	 */
	protected void verifyProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo project = (CurProjectInfo) node.getUserObject();
			if (!project.isIsLeaf()) {
				FDCMsgBox.showWarning(this, "请选择明细工程项目!");
				SysUtil.abort();
			}
		}
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	protected void tblMain2_tableSelectChanged(KDTSelectEvent e) throws Exception {
		tabTemp = 2;
		tblMain = tblMain2;
		int id = tblMain.getSelectManager().getActiveRowIndex();
		tblMain1.getSelectManager().removeAll();
		String uuid = (String) tblMain.getCell(id, "id").getValue();
		
		/* modified by zhaoqin for R140925-0004 on 2014/11/12 */
		initImagePanel();
		//locateSelectedImage(BOSUuid.read(uuid));
	}

	/**
	 * 工程项目树选择事件 output treeMain_valueChanged method
	 * 
	 * @throws Exception
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		
		/* modified by zhaoqin for R140925-0004 on 2014/11/12 start */
		//Set prjIDs = new HashSet();
		CurProjectInfo projectInfo = null;
		if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			projectInfo = (CurProjectInfo) node.getUserObject();
			//if (projectInfo.isIsLeaf()) {
				//prjIDs.add(((CurProjectInfo) projectInfo).getId().toString());
				//initImagePanel();
			//}
			/* modified by zhaoqin for R140925-0004 on 2014/11/12 end */
			this.refresh(null);
		} else {
			tblMain2.removeRows();
			tblMain1.removeRows();
			if (myPanel != null) {
				myPanel.clearAll();
			}
			return;
		}
		// 清空当前table
		tblMain2.removeRows();
		tblMain1.removeRows();
		// 查询数据
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("createTime");
		sic.add("isKeyCert");
		sic.add("relateTask.id");
		view.setSelector(sic);
		// 根据工程ID过滤条件
		FilterInfo filterInfo = new FilterInfo();
		if (projectInfo.isIsLeaf()) {
			//filterInfo.getFilterItems().add(new FilterItemInfo("project.id", prjIDs, CompareType.INCLUDE));
			filterInfo.getFilterItems().add(new FilterItemInfo("project.id", projectInfo.getId().toString(), CompareType.EQUALS));
		} else {
			filterInfo.getFilterItems().add(
			new FilterItemInfo("project.longNumber", projectInfo.getNumber().toString() + "%", CompareType.LIKE));
		}
		view.setFilter(filterInfo);
		// 根据创建时间倒序排序
		SorterItemCollection sort = new SorterItemCollection();
		SorterItemInfo sort1 = new SorterItemInfo("createTime");
		sort1.setSortType(SortType.DESCEND);
		sort.add(sort1);
		view.setSorter(sort);

		setTableValue(view);
		
		/* modified by zhaoqin for R140925-0004 on 2014/11/12 */
		if(tblMain2.getRowCount() > 0) {
			tblMain2.getSelectManager().select(0, 0);
		} else if(tblMain1.getRowCount() > 0) {
			tblMain1.getSelectManager().select(0, 0);
		} else {
			if (myPanel != null) {
				myPanel.clearAll();
			}
		}
	}

	/**
	 * 根据部门树选择设置table值
	 * 
	 * @author 王维强 2011-08-15
	 * @param view
	 * @throws BOSException
	 */
	private void setTableValue(EntityViewInfo view) throws BOSException {
		ProjectImageCollection col = ProjectImageFactory.getRemoteInstance().getProjectImageCollection(view);
		if (col != null && col.size() > 0) {
			for (int i = 0; i < col.size(); i++) {
				ProjectImageInfo imgInfo = col.get(i);
				if (!imgInfo.isIsKeyCert()) {
					IRow row = tblMain2.addRow();
					row.getCell("id").setValue(imgInfo.getId().toString());
					if (null != imgInfo.getNumber()) {
						row.getCell("number").setValue(imgInfo.getNumber().toString());
					}
					row.getCell("name").setValue(imgInfo.getName());
					row.getCell("createTime").setValue(FDCHelper.FORMAT_DAY.format(imgInfo.getCreateTime()));
					if(imgInfo.getRelateTask()!=null){
						row.getCell("relateTask").setValue(imgInfo.getRelateTask().getId());
					}
					
				} else {
					IRow row1 = tblMain1.addRow();
					row1.getCell("id").setValue(imgInfo.getId().toString());
					if (null != imgInfo.getNumber()) {
						row1.getCell("number").setValue(imgInfo.getNumber().toString());
					}
					row1.getCell("name").setValue(imgInfo.getName());
					row1.getCell("createTime").setValue(FDCHelper.FORMAT_DAY.format(imgInfo.getCreateTime()));
					if(imgInfo.getRelateTask()!=null){
						row1.getCell("relateTask").setValue(imgInfo.getRelateTask().getId());
					}
					
				}
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectImageFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ProjectImageEditUI.class.getName();
	}

	protected String getProjectFieldName() {
		return "project.name";
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected FilterInfo getMainFilter() {
		// 做工程项目隔离及部门隔离
		// String currentOrgId =
		// SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		Set prjIdSet = getProjectLeafsOfNode(node);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", prjIdSet, CompareType.INCLUDE));
		// filter.getFilterItems().add(new
		// FilterItemInfo("adminDept.id",currentOrgId));
		return filter;
	}

	protected boolean isTableTreeRow() {
		return false;
	}

	/**
	 * 获取选择节点的所有下级明细工程项目
	 * 
	 * @return
	 */
	protected Set getProjectLeafsOfNode(DefaultKingdeeTreeNode node) {
		Set idSet = new HashSet();
		if (node != null && node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo prj = (CurProjectInfo) node.getUserObject();
			if (prj.isIsLeaf()) {
				idSet.add(prj.getId().toString());
				return idSet;
			}
		}
		// 直接遍历树去到节点可以保证一致性
		if (node != null) {
			Enumeration childrens = node.depthFirstEnumeration();
			while (childrens.hasMoreElements()) {
				DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode) childrens.nextElement();
				if (childNode.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo prj = (CurProjectInfo) childNode.getUserObject();
					if (prj.isIsLeaf()) {
						idSet.add(prj.getId().toString());
					}
				}
			}
		}

		if (idSet.size() == 0) {
			// 如果选择的节点下没有任何明细的工程项目节点则随便加个节点保证过滤不出来任何数据
			idSet.add(OrgConstants.DEF_CU_ID);
		}
		return idSet;
	}

	/**
	 * 重写删除事件
	 * 
	 * @author 王维强 2011-08-12
	 * @param e
	 * @throws Exception
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		Object id = null;
		if (!confirmRemove()) {
			return;
		}
		if (tabTemp == 2) {
			tblMain = tblMain2;
		} else {
			tblMain = tblMain1;
		}
		id = this.tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
		/*
		 * try { String relateTask =
		 * this.tblMain.getRow(tblMain.getSelectManager
		 * ().getActiveRowIndex()).getCell("relateTask").getValue().toString();
		 * if (relateTask != null && !relateTask.equals("")) { EntityViewInfo
		 * view = new EntityViewInfo(); FilterInfo filter = new FilterInfo();
		 * filter.appendFilterItem("id", relateTask); view.setFilter(filter);
		 * view.getSelector().add(new SelectorItemInfo("*"));
		 * view.getSelector().add(new SelectorItemInfo("schedule.*"));
		 * FDCScheduleTaskCollection fDCScheduleTaskCollection =
		 * FDCScheduleTaskFactory
		 * .getRemoteInstance().getFDCScheduleTaskCollection(view); if
		 * (ScheduleStateEnum.AUDITTED ==
		 * fDCScheduleTaskCollection.get(0).getSchedule().getState()) {
		 * FDCMsgBox.showInfo("提示：相关任务已审批，不允许删除"); return; } } } catch
		 * (Exception e1) { e1.printStackTrace(); } finally {
		 * 
		 * }
		 */
		
		
		ProjectImageFactory.getRemoteInstance().delete(new ObjectUuidPK(id.toString()));
		treeMain_valueChanged(null);
		
	}

	/**
	 * 判断当前所选table和row
	 * 
	 * @author 王维强 2011-08-15
	 */
	public void checkSelected() {
		if (tabTemp == 2) {
			tblMain = tblMain2;
		} else {
			tblMain = tblMain1;
		}
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	protected void checkBillState(ScheduleStateEnum state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		ProjectImageCollection coll = ProjectImageFactory.getRemoteInstance().getProjectImageCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ProjectImageInfo element = (ProjectImageInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());

			if (!element.getState().equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}

	protected void checkBillStateForRemove(ScheduleStateEnum state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		ProjectImageCollection coll = ProjectImageFactory.getRemoteInstance().getProjectImageCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ProjectImageInfo element = (ProjectImageInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			// 审批状态已取消
			/*
			 * if (element.getState().equals(state)) { MsgBox.showWarning(this,
			 * "单据处于审批状态，不能删除！"); abort(); }
			 */

		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		actionAuditResult.setVisible(false);
		btnEdit.setVisible(true);
		btnEdit.setEnabled(true);
		this.actionEdit.setVisible(true);
		this.actionEdit.setEnabled(true);
		tblMain2.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		// tblMain2.getIndexColumn().getStyleAttributes().setHided(true);
		tblMain2.getHeadRow(0).getStyleAttributes().setHided(true);
		tblMain1.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain1.getHeadRow(0).getStyleAttributes().setHided(true);
		// tblMain1.getIndexColumn().getStyleAttributes().setHided(true);
		myPanel = new KDDynamicShowImagePanel();
		this.btnAttachment.setVisible(false);
	}

	protected File loadFile(ProjectImageEntryInfo entry) {
		File file = null;
		byte[] b = entry.getFile();
		if (b != null) {
			try {
				file = File.createTempFile("KDTF-" + entry.getName(), ".jpg");
				FileOutputStream fos = new FileOutputStream(file);

				fos.write(b);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return file;
	}

	private void initImagePanel() throws IOException, FileNotFoundException {
		// myPanel = new KDDynamicShowImagePanel();
		URL imgUrl = getClass().getResource("/com/kingdee/eas/fdc/schedule/client/resource/loading.gif");
		BufferedImage loading = ImageIO.read(imgUrl);
		imgUrl = getClass().getResource("/com/kingdee/eas/fdc/schedule/client/resource/pointer.gif");
		BufferedImage img_pointer = ImageIO.read(imgUrl);
		imgContainer.getContentPane().setLayout(new KDLayout());
		imgContainer.getContentPane().putClientProperty("OriginalBounds", new Rectangle(6, 5, 482, 606));

		myPanel.setBounds(new Rectangle(5, 3, 470, 600));
		imgContainer.getContentPane().add(
				myPanel,
				new KDLayout.Constraints(5, 3, 470, 600, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM
						| KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
		myPanel.setLoading(loading);
		myPanel.setPointer(img_pointer);
		// myPanel.removeAll();
		Map images = this.getImages();
		myPanel.setImgageData(images);
		myPanel.addImageChangeLisener(this);
		
		if (images != null) {
			/**
			 * 界面加载后，最后调用此线程，把图像定位到第一张图片上
			 * 
			 */
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					myPanel.moveTo(1);
				}
			});
		}
	}

	/**
	 * 需要重写getSeletor，只获取最需要的数据 关于图片 考虑性能问题，不获取大图，只获取小图
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("entries.id"));
		sic.add(new SelectorItemInfo("relateTask"));
		return sic;
	}

	/**
	 * 获取原始大图片
	 */
	public BufferedImage getOriginalImg(int i) {
		BOSUuid uuid = null;
		if (imgIds != null) {
			uuid = (BOSUuid) imgIds.get(new Integer(i));
		}
		if (originalImg.containsKey(uuid)) {
			return (BufferedImage) originalImg.get(uuid);
		}
		byte[] imgByte = null;
		try {
			imgByte = ProjectImageEntryFactory.getRemoteInstance().getOriginalImageById(uuid);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		ByteArrayInputStream in = new ByteArrayInputStream(imgByte);
		BufferedImage img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * TODO 修改本方法c 通过缩略的key值，找到原始图片的ID 然后
		 * 调用ProjectImageEntryFactory.getRemoteInstance
		 * .getOriginalImageById(BOSUuid) 然后封装成BufferedImage，返回
		 */
		originalImg.put(uuid, img);
		return img;

	}

	/**
	 * 验证项目缩略图是否为空
	 * 
	 * @param id
	 * @return
	 */
	public boolean verifyImg(BOSUuid id) {
		if (imgBillIds != null) {
			BOSUuid uid = null;
			Set set = imgBillIds.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Object key = it.next();
				uid = (BOSUuid) imgBillIds.get(key);
				if (uid.equals(id)) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * 创建清空Map
	 */
	public void createMap() {
		if (imgs == null) {
			imgs = new HashMap();
		} else {
			imgs.clear();
		}
		if (imgIds == null) {
			imgIds = new HashMap();
		} else {
			imgIds.clear();
		}
		if (imgNames == null) {
			imgNames = new HashMap();
			myPanel.setImgNames(imgNames);
		} else {
			imgNames.clear();
		}
		if (imgBillIds == null) {
			imgBillIds = new HashMap();
		} else {
			imgBillIds.clear();
		}
		if (billIdImage == null) {
			billIdImage = new HashMap();
		} else {
			billIdImage.clear();
		}
		if (originalImg == null) {
			originalImg = new HashMap();
		} else {
			originalImg.clear();
		}

	}

	/**
	 * 这里获取选中项目的所有图片的缩略图
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Map getImages() throws FileNotFoundException, IOException {
		/* modified by zhaoqin for R140925-0004 on 2014/11/12 start */
		//DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(tblMain.getRowCount() == 0)
			return null;
		int id = tblMain.getSelectManager().getActiveRowIndex();
		String prjImgId = (String) tblMain.getCell(id, "id").getValue();
		//Object obj = null;
		//Set prjIDs = new HashSet();
		//if (node != null) {
		if(!StringUtils.isEmpty(prjImgId)) {
			//obj = node.getUserObject();
			// 判断节点非空时是否是明细工程项目
			//if (obj instanceof CurProjectInfo) {
				//prjIDs.add(((CurProjectInfo) obj).getId().toString());
				// new Map();
				// 加载对象
				createMap();
				// 获取缩略图
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				// sic.add("file");
				sic.add("fileName");
				sic.add("smallFile");
				view.setSelector(sic);
				FilterInfo filter = new FilterInfo();
				//filter.getFilterItems().add(new FilterItemInfo("parent.project.id", prjIDs, CompareType.INCLUDE));
				filter.getFilterItems().add(new FilterItemInfo("parent.id", prjImgId));
				/* modified by zhaoqin for R140925-0004 on 2014/11/12 end */
				
				view.setFilter(filter);
				// 按时间倒序排列
				SorterItemCollection sort = new SorterItemCollection();
				SorterItemInfo sort1 = new SorterItemInfo("parent.lastUpdateTime");
				sort1.setSortType(SortType.DESCEND);
				SorterItemInfo sort2 = new SorterItemInfo("seq");
				sort2.setSortType(SortType.DESCEND);
				sort.add(sort1);
				sort.add(sort2);
				view.setSorter(sort);
				try {
					ProjectImageEntryCollection imgEntrys = ProjectImageEntryFactory.getRemoteInstance().getProjectImageEntryCollection(view);
					// 遍历初始化缩略图相关数据
					if (imgEntrys != null && imgEntrys.size() > 0) {
						for (int i = 0; i < imgEntrys.size(); i++) {
							ProjectImageEntryInfo imgInfo = imgEntrys.get(i);
							byte[] file = imgInfo.getSmallFile();
							if (file == null) {
								continue;
							}
							ByteArrayInputStream in = new ByteArrayInputStream(file);
							BufferedImage img = ImageIO.read(in);
							if (img == null) {
								continue;
							}
							Integer index = new Integer(i + 1);
							imgs.put(index, img);
							imgIds.put(index, imgInfo.getId());
							imgNames.put(index, imgInfo.getFileName());
							BOSUuid parentID = imgInfo.getParent().getId();
							imgBillIds.put(index, parentID);
							if (!billIdImage.containsKey(parentID)) {
								billIdImage.put(parentID, index);
							}
						}
					}

				} catch (BOSException e1) {
					e1.printStackTrace();
				}

				return imgs;
			//}
		}
		return null;
		/*
		 * TODO 1、需要完成从服务器读取数据[需要考虑RPC，一定调用所有信息]，需要注意排序规则，按照上传日期 倒序排列。
		 * 2、将返回的数据放入Map imgs<Integer,BufferedImage>
		 * 3、另外，还需要缓存上面缩略图map中的KEY值与原始图片的ID的对应关系即 Map
		 * imgIds<Integer,BOSUUid>,方便根据序号，获取原始图片 需要实现 getOriginalImg(int i)的方法
		 * 4、还需要缓存上面的缩略图Map中的key值，与界面两个列表中对应的形象进度单据的ID的对应关系Map
		 * imgBillIds<Integer,BOSUuid>,方便图片变化之后，定位到相对应的单据
		 * 需要实现imageSelectChanged(int i)的方法
		 * 5、由于一张单据上，会有多张图片，每个单据选中时，还需要触发图片展示控件定位到相应的图片
		 * ，故，还需要缓存，单据Id和本单据的第一张图片的key值得对应关系 相应的实现：locateSelectedImage(BOSUuid)
		 */

	}

	/***
	 * 实现这里的方法，可以实现选择了某一图片之后，动态标识[形象进度，或者关键证照列表]中的某一行数据被选中
	 */
	public void imageSelectChanged(int i) {
		/* modified by zhaoqin for R140925-0004 on 2014/11/12 */
		if(true)
			return;
		
		String uuid = "";
		if (imgBillIds.get(Integer.valueOf(i)) != null) {
			uuid = imgBillIds.get(Integer.valueOf(i)).toString();
		}
		if (tblMain2.getRowCount() > 0) {
			for (int j = 0; j < tblMain2.getRowCount(); j++) {
				IRow row = tblMain2.getRow(j);
				String id = row.getCell("id").getValue().toString();
				if (id.equals(uuid)) {
					MouseListener[] mouseListeners = tblMain2.getMouseListeners();
					EventListener[] selectListeners = tblMain2.getListeners(KDTSelectListener.class);
					// 先循环删除事件，选择图片后在加载，避免落入死循环
					// 删除鼠标监听事件
					for (int k = 0; k < mouseListeners.length; k++) {
						tblMain2.removeMouseListener(mouseListeners[k]);
					}
					// 删除列表选择事件
					for (int k = 0; k < selectListeners.length; k++) {
						tblMain2.removeKDTSelectListener((KDTSelectListener) selectListeners[k]);
					}
					// 选择图片
					tblMain2.getSelectManager().select(j, 0);
					tblMain1.getSelectManager().removeAll();
					// 加载鼠标监听事件
					for (int k = 0; k < mouseListeners.length; k++) {
						tblMain2.addMouseListener(mouseListeners[k]);
					}
					// 加载列表选择事件
					for (int k = 0; k < selectListeners.length; k++) {
						tblMain2.addKDTSelectListener((KDTSelectListener) selectListeners[k]);
					}
					return;
				}
			}
		}
		if (tblMain1.getRowCount() > 0) {
			for (int j = 0; j < tblMain1.getRowCount(); j++) {
				IRow row = tblMain1.getRow(j);
				String id = row.getCell("id").getValue().toString();
				if (id.equals(uuid)) {
					MouseListener[] mouseListeners = tblMain1.getMouseListeners();
					EventListener[] selectListeners = tblMain1.getListeners(KDTSelectListener.class);
					// 先循环删除事件，选择图片后在加载，避免落入死循环
					// 删除鼠标监听事件
					for (int k = 0; k < mouseListeners.length; k++) {
						tblMain1.removeMouseListener(mouseListeners[k]);
					}
					// 删除列表选择事件
					for (int k = 0; k < selectListeners.length; k++) {
						tblMain1.removeKDTSelectListener((KDTSelectListener) selectListeners[k]);
					}
					// 选择图片
					tblMain1.getSelectManager().select(j, 0);
					tblMain2.getSelectManager().removeAll();
					// 加载鼠标监听事件
					for (int k = 0; k < mouseListeners.length; k++) {
						tblMain1.addMouseListener(mouseListeners[k]);
					}
					// 加载列表选择事件
					for (int k = 0; k < selectListeners.length; k++) {
						tblMain1.addKDTSelectListener((KDTSelectListener) selectListeners[k]);
					}
					return;
				}
			}
		}

		/*
		 * TODO BOSUuid uid = imgBillIds.get(Integer.valueOf(i))
		 * 定位可参考listUiHelper
		 * .selectPreRow(tblMain2,allIdList,selectKeyValue,selectIndex
		 * ,getKeyFieldName()); 需要特别注意，由于列表的选中，也会调用图片的选中事件，一定要注意循环调用，最后落入死循环
		 */

	}

	/**
	 * 当选中表格的某一行时，调用此方法
	 * 
	 * @param i
	 */
	public void locateSelectedImage(BOSUuid id) {
		boolean m = verifyImg(id);
		if (m == true) {
			if (billIdImage != null) {
				Integer index = (Integer) billIdImage.get(id);
				myPanel.moveTo(index.intValue());
				getOriginalImg(index.intValue());
			}
		} else {
			SysUtil.abort();
		}

		/*
		 * TODO 需要在两个表格上，增加selectChange事件，调用此方法 通过BOSUuid查找对应的图片，并让图片控件调用到某一图片
		 * 需要通过Map billIdImagesInt<BOSUuid ,Integer> 最后调用 myPanel.moveTo(1);进行定位
		 * imageSelectChanged(int)需要特别注意，由于列表的选中，也会调用图片的选中事件，一定要注意循环调用，最后落入死循环
		 */

	}

	/**
	 * 图片下载事件
	 * 
	 * @author 王维强 2011-08-12
	 * @param e
	 * @throws Exception
	 */
	public void actionDownImage_actionPerformed(ActionEvent e) throws Exception {
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		// 单选
		fc.setMultiSelectionEnabled(false);
		// 2种格式
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("png", new String[] { "png" });
		FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("jpg", new String[] { "jpg" });
		fc.setFileFilter(pngFilter);
		fc.setFileFilter(jpgFilter);
		KDTable sourceTable = null;
		if(tblMain1.getSelectManager().getActiveRowIndex() != -1){
			sourceTable = tblMain1;
		}else if(tblMain2.getSelectManager().getActiveRowIndex() != -1){
			sourceTable = tblMain2;
		}
		if (sourceTable == null || sourceTable.getSelectManager().getActiveRowIndex() < 0) {
			return;
		}
		String uuid = sourceTable.getCell(sourceTable.getSelectManager().getActiveRowIndex(), "id").getValue().toString();
		int index = -1;
		if (billIdImage != null && billIdImage.get(BOSUuid.read(uuid)) != null) {
			index = (Integer) billIdImage.get(BOSUuid.read(uuid));
		}
		String newFile = "";
		if(index != -1){
			newFile = imgNames.get(index).toString();
		} else {
			return;
		}
		fc.setSelectedFile(new File(newFile));
		int retVal = fc.showSaveDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return;
		}

		// 得到图片
		BufferedImage img = (BufferedImage) myPanel.getBigIMG();
		if (img == null) {
			return;
		}

		File chooseFile = fc.getSelectedFile();
		 String imgName = chooseFile.getPath();
		
		// 判断选择了jpg或者png
		if (fc.getFileFilter().equals(jpgFilter)) {
			if (!imgName.endsWith("jpg")) {
				imgName += ".jpg";
			}
			File saveIMG = new File(imgName);
			// 写入jpg
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			new SunJPEGEncoderAdapter().encode(img, fos);
			bos.close();
			
		} else {
			if (!imgName.endsWith("png")) {
				imgName += ".png";
			}
			File saveIMG = new File(newFile);
			// 写入png
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] pngEncode = new PngEncoder(img).pngEncode();
			bos.write(pngEncode);
			bos.close();
		
		}
		
	}

	/**
 * 
 */

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		this.getUIContext().put("CurProject", treeNode.getUserObject());
	}
}