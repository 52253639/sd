/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Arrays;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgCollection;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgFactory;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:报表组织视图维护
 * @author adward_huang  date:2012-10-16
 * @version EAS6.1
 */
public class ScheduleReportTreeMaintenanceUI extends AbstractScheduleReportTreeMaintenanceUI {

	private static final long serialVersionUID = 6817881287428224769L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportTreeMaintenanceUI.class);

	private static final String NUMBER = "number";// 编码
	private static final String NAME = "name";// 名称
	private static final String PARENT_NUMBER = "parentNumber";//父编码
	private static final String PARENT_NAME = "parentName";// 父名称
	private static final String REMARK = "remark";// 备注
	private static final String TREE_CELL = "treeCell";// 伸缩单元格

	// 新增行
	private KDWorkButton addRowBtn;

	// 删除行
	private KDWorkButton deleteRowBtn;

	// 插入行
	private KDWorkButton insertRowBtn;

	/*	// 上移行
		private KDWorkButton preRemoveRowBtn;
		
		// 下移行
		private KDWorkButton nextRemoveRowBtn;*/

	// 缓存改变的节点信息，包括新增，修改的节点信息
	private Map<String, ScheduleReportOrgInfo> cacheUpdateTreeNodeMap = new HashMap<String, ScheduleReportOrgInfo>();

	// 缓存从数据库中加载的节点信息，包括第一次从数据库加载的所有节点信息
	private Map<String, ScheduleReportOrgInfo> cacheLoadTreeNodeMap = new HashMap<String, ScheduleReportOrgInfo>();

	// 缓存改变的节点信息，包括删除的节点信息
	private Map<String, ScheduleReportOrgInfo> cacheDeleteTreeNodeMap = new HashMap<String, ScheduleReportOrgInfo>();

	// 缓存父节点的子节点个数信息
	private Map<String, Integer> cacheChildrenSize = new HashMap<String, Integer>();

	// 是否为集团组织
	private boolean isGroupOrg = false;

	// 基于表格树的操作类型
	private int actionType;

	// 表格的编辑状态  
	private boolean isEditable = false;

	public ScheduleReportTreeMaintenanceUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		isGroupOrg = isGroupOrg();
		if (isGroupOrg) {
			super.onLoad();
			initUIState();
			initPanel();
		} else {
			MsgBox.showInfo("请切换到集团组织下进行报表组织视图维护操作！");
			return;
		}
	}

	/**
	 * 描述：判断当前组织是否为集团
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	private boolean isGroupOrg() {
		String curOrgID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();

		boolean isGroup = false;
		// 如果当前组织为集团
		if (FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID.equals(curOrgID)) {
			isGroup = true;
			// 检测是否存在集团节点，如果不存在则添加，存在则不添加
			addDefaultGroupTreeNode();

		}
		return isGroup;
	}

	/**
	 * 描述：初始化界面
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author：adward_huang
	 * @CreateTime：2012-10-12
	 */
	private void initPanel() throws EASBizException, BOSException {
		addBtnOnContainer();
		fillTable();
	}

	/**
	 * 描述：添加按钮及事件
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	private void addBtnOnContainer() {
		addRowBtn = new KDWorkButton("增加行");
		deleteRowBtn = new KDWorkButton("删除行");
		insertRowBtn = new KDWorkButton("插入行");
		//		preRemoveRowBtn = new KDWorkButton("上移行");
		//		nextRemoveRowBtn = new KDWorkButton("下移行");
		this.addRowBtn.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.deleteRowBtn.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.insertRowBtn.setIcon(EASResource.getIcon("imgTbtn_insert"));
		//		this.preRemoveRowBtn.setIcon(EASResource.getIcon("imgTree_derivation"));
		//		this.nextRemoveRowBtn.setIcon(EASResource.getIcon("imgTree_import"));
		addRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_ADD;
				// 增加行操作
				addRowData();
			}
		});

		deleteRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_DELETE;
				// 删除行操作
				deleteRowData();
			}
		});

		insertRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_INSERT;
				// 插入行操作
				insertRowData();
			}
		});

		/*preRemoveRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_INSERT;
				// 上移行操作
				preRemoveRowData();
			}
		});
		
		nextRemoveRowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionType = FDCScheduleConstant.ACTION_TYPE_INSERT;
				// 下移行操作
				nextRemoveRowData();
			}
		});*/

		//		tableContainer.addButton(preRemoveRowBtn);
		//		tableContainer.addButton(nextRemoveRowBtn);
		tableContainer.addButton(addRowBtn);
		tableContainer.addButton(deleteRowBtn);
		tableContainer.addButton(insertRowBtn);
	}

	/**
	 * 描述：初始化界面组建状态
	 * @Author：adward_huang
	 * @CreateTime：2012-10-12
	 */
	private void initUIState() {
		this.saveBtn.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.updateBtn.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.refreshBtn.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		this.treeTable.setEditable(true);
		this.isEditable = false;
		this.treeTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	/**
	 * 描述：填充表格数据
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-10-25
	 */
	public void fillTable() throws EASBizException, BOSException {
		KDTable table = this.treeTable;
		table.checkParsed();
		table.removeRows();
		treeTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		// 获取根节点数据
		ScheduleReportOrgInfo rootInfo = ScheduleChartHelper.getRootNodeInfo();

		// 加载根节点
		addRootNode(table, rootInfo);

		// 获取所有树形数据
		List<ScheduleReportOrgInfo> allTreeNodeData = ScheduleChartHelper.getAllTreeNodeData(rootInfo);

		// 初始化缓存数据
		initCacheData(rootInfo, allTreeNodeData);

		// 加载除根节点外的所有树形数据
		generateTreeNode(table, rootInfo, allTreeNodeData);

		// 初始化单元格的可编辑状态
		setEditColumn(!isEditable);
	}

	/**
	 * 描述：设置表格单元格编辑状态
	 * @param flag		false：可编辑状态  true:不可编辑状态
	 * @Author：adward_huang
	 * @CreateTime：2012-10-28
	 */
	private void setEditColumn(boolean flag) {

		int rowCount = this.treeTable.getRowCount();
		IRow row = null;
		for (int i = 0; i < rowCount; i++) {
			row = treeTable.getRow(i);
			row.getCell(TREE_CELL).getStyleAttributes().setLocked(false);
			row.getCell(NUMBER).getStyleAttributes().setLocked(flag);
			row.getCell(NAME).getStyleAttributes().setLocked(flag);
			row.getCell(REMARK).getStyleAttributes().setLocked(flag);
			row.getCell(PARENT_NUMBER).getStyleAttributes().setLocked(true);
			row.getCell(PARENT_NAME).getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * 描述：初始化缓存数据
	 * @param rootInfo
	 * @param allTreeNodeData
	 * @Author：adward_huang
	 * @CreateTime：2012-10-25
	 */
	private void initCacheData(ScheduleReportOrgInfo rootInfo, List<ScheduleReportOrgInfo> allTreeNodeData) {

		Map<String, ScheduleReportOrgInfo> allDataMap = ScheduleChartHelper.getAllDataMap(allTreeNodeData);
		allDataMap.put(rootInfo.getId().toString(), rootInfo);
		// 缓存从数据库中加载的节点信息
		cacheLoadTreeNodeMap.putAll(allDataMap);

		for (ScheduleReportOrgInfo org : allTreeNodeData) {
			if (org.getParent() != null) {
				String parentID = org.getParent().getId().toString();
				// 如果存在子节点，则加1
				if (cacheChildrenSize.containsKey(parentID)) {
					int size = cacheChildrenSize.get(parentID);
					cacheChildrenSize.put(parentID, size + 1);
				} else {
					cacheChildrenSize.put(parentID, 1);
				}
			}
		}
	}

	/**
	 * 描述：遍历获取所有树节点
	 * @param node
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	private void generateTreeNode(KDTable table, ScheduleReportOrgInfo orgInfo, List<ScheduleReportOrgInfo> orderedTreeNodeList)
			throws BOSException {
		List<ScheduleReportOrgInfo> childrenOrgs = ScheduleChartHelper.getChildrenOrgs(orgInfo, orderedTreeNodeList);
		ScheduleChartHelper.sortedListBySortNumber(childrenOrgs);
		ScheduleReportOrgInfo child = null;
		for (int i = 0; i < childrenOrgs.size(); i++) {
			child = childrenOrgs.get(i);
			IRow row = table.addRow();
			row.setUserObject(child);
			if (child.getParent() != null && cacheLoadTreeNodeMap.containsKey(child.getParent().getId().toString())) {
				child.setParent(cacheLoadTreeNodeMap.get(child.getParent().getId().toString()));
			}
			loadRow(row);
			generateTreeNode(table, child, orderedTreeNodeList);
		}
	}

	/**
	 * 描述：添加根节点
	 * @Author：adward_huang
	 * @CreateTime：2012-10-25
	 */
	private void addRootNode(KDTable table, ScheduleReportOrgInfo root) {
		IRow row = table.addRow();
		row.setUserObject(root);
		root.setParent(null);
		loadRow(row);
	}

	/**
	 * 描述：添加行数据
	 * @Author：adward_huang
	 * @CreateTime：2012-10-13
	 */
	private void addRowData() {
		int index = treeTable.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			MsgBox.showWarning("请选择表格行数据!");
			return;
		}

		ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
		Object parentNumber = treeTable.getRow(index).getCell(NUMBER).getValue();
		Object parentName = treeTable.getRow(index).getCell(NAME).getValue();
		if (FDCHelper.isEmpty(parentNumber) || FDCHelper.isEmpty(parentName)) {
			MsgBox.showWarning("请填写父节点的编码和名称后再添加!");
			return;
		}

		ScheduleReportOrgInfo parent = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();
		parent.setNumber(parentNumber.toString());
		parent.setName(parentName.toString());
		org.setParent(parent);
		org.setLevel(parent.getLevel() + 1);
		BOSUuid uuid = getScheduleReportBOSUuid();
		org.setId(uuid);

		//		int addIndex = getChildrenSize(parent);
		int addIndex = getAllChildrenSize(parent);
		cacheChildrenSize.put(parent.getId().toString(), addIndex + 1);
		IRow row = treeTable.addRow(index + 1 + addIndex);
		org.setSortNumber(addIndex + 1);
		row.setUserObject(org);
		loadRow(row);
	}

	/**
	 * 描述：检查是否存在子节点信息
	 * @param parent
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-15
	 */
	private boolean hasChildren(ScheduleReportOrgInfo parent) {
		BOSUuid parentId = parent.getId();
		int rowCount = treeTable.getRowCount();

		boolean flag = false;
		IRow row = null;
		ScheduleReportOrgInfo org = null;
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// 如果存在有父节点ID等于其ID，则存在子节点，返回true
			if (org != null && org.getParent() != null && org.getParent().getId().toString().equals(parentId.toString())) {
				flag = true;
				break;
			}
		}

		return flag;
	}

	/**
	 * 描述：获取子节点索引
	 * @param parent
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-25
	 */
	private List<Integer> getAllChildrenIndex(ScheduleReportOrgInfo parent) {
		int rowCount = treeTable.getRowCount();

		IRow row = null;
		ScheduleReportOrgInfo org = null;
		List<Integer> indexList = new ArrayList<Integer>();
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// 如果存在有父节点ID等于其ID，则存在子节点，返回true
			if (org != null && org.getParent() != null && org.getParent().getId().toString().equals(parent.getId().toString())) {
				indexList.add(i);
			}
		}
		return indexList;
	}

	/**
	 * 描述：获取节点的索引
	 * @param parent
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-15
	 */
	@SuppressWarnings("unused")
	private int getNodeIndex(ScheduleReportOrgInfo node) {
		BOSUuid nodeId = node.getId();
		int rowCount = treeTable.getRowCount();

		IRow row = null;
		ScheduleReportOrgInfo org = null;
		int index = 0;
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// 如果存在有父节点ID等于其ID，则存在子节点，返回true
			if (org != null && org.getId().toString().equals(nodeId.toString())) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * 描述：修改该节点下面所有节点的排序数字
	 * @param orgInfo
	 * @param curIndex
	 * @param type		正数则代表是增加操作，负数则代表删除操作
	 * @Author：adward_huang
	 * @CreateTime：2012-10-24
	 */
	private void updateChildrenSortNumber(ScheduleReportOrgInfo orgInfo, int curIndex, int type) {

		IRow row = null;
		ScheduleReportOrgInfo org = null;
		// 获取所有子节点索引
		List<Integer> childrenIndexList = getAllChildrenIndex(orgInfo);
		for (Integer index : childrenIndexList) {

			if (curIndex > index) {
				continue;
			}

			// 插入和添加基于行操作的索引不同，故在插入时，跳过当前行的计算
			if (actionType == FDCScheduleConstant.ACTION_TYPE_INSERT) {
				if (curIndex == index) {
					continue;
				}
			}

			row = treeTable.getRow(index);
			org = (ScheduleReportOrgInfo) row.getUserObject();

			if (type > 0) {
				org.setSortNumber(org.getSortNumber() + 1);
			} else {
				org.setSortNumber(org.getSortNumber() - 1);
			}
		}
	}

	/**
	 * 描述：检查是否存在相同的节点信息
	 * @param parent
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-15
	 */
	private boolean existSameNode(ScheduleReportOrgInfo info, List<String> list, ScheduleReportOrgInfo curOrg) {

		int rowCount = treeTable.getRowCount();
		boolean flag = false;
		IRow row = null;
		ScheduleReportOrgInfo org = null;
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// 1.判断关联属性是否引用同一个组织
			if (org.getRelateOrg() != null && info.getRelateOrg() != null) {
				if (org.getRelateOrg().getId().toString().equals(info.getRelateOrg().getId().toString())) {
					if (!info.getRelateOrg().getId().toString().equals(org.getRelateOrg().getId().toString())) {
						list.add(org.getName());
						flag = true;
						continue;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 描述：判断是否存在重复的节点信息
	 * @param sb
	 * @param duplicateIndex
	 * @param org
	 * @param dataMap
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-15
	 */
	@SuppressWarnings("unused")
	private int hasDuplicateNode(StringBuffer sb, int duplicateIndex, ScheduleReportOrgInfo org, Map<String, ScheduleReportOrgInfo> dataMap) {
		ScheduleReportOrgInfo info = null;

		for (Iterator<Map.Entry<String, ScheduleReportOrgInfo>> iter = dataMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, ScheduleReportOrgInfo> map = iter.next();
			info = map.getValue();
			// 判断关联属性是否引用同一个组织
			if (org.getRelateOrg() != null && info.getRelateOrg() != null) {
				if (org.getRelateOrg().getId().toString().equals(info.getRelateOrg().getId().toString())) {
					duplicateIndex++;
					if (duplicateIndex > 1) {
						sb.append(",").append(org.getName());
					} else {
						sb.append(org.getName());
					}
					continue;
				}
			}
			// 判断是否编码相同
			if (org.getNumber().equals(info.getNumber())) {
				duplicateIndex++;
				if (duplicateIndex > 1) {
					sb.append(",").append(org.getName());
				} else {
					sb.append(org.getName());
				}
				continue;
			}

		}
		return duplicateIndex;
	}

	/**
	 * 描述：删除行数据
	 * @Author：adward_huang
	 * @CreateTime：2012-10-13
	 */
	private void deleteRowData() {
		int index = treeTable.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			MsgBox.showWarning("请选择表格行数据，进行删除!");
			return;
		}
		ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();
		if (hasChildren(org)) {
			MsgBox.showWarning("该组织存在下属组织，不能进行删除!");
			return;
		}

		if (org.getRelateOrg() != null
				&& org.getRelateOrg().getId().toString().equals(FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID)) {
			MsgBox.showWarning("集团组织不能进行删除操作!");
			return;
		}
		int deleteSize = getChildrenSize(org.getParent());
		cacheChildrenSize.put(org.getParent().getId().toString(), deleteSize - 1);

		IRow row = treeTable.removeRow(index);
		if (row != null) {
			updateChildrenSortNumber(org.getParent(), index, -1);
			// 如果该数据存在于加载数据集中，则缓存该条已删除的信息
			if (cacheLoadTreeNodeMap.containsKey(org.getId().toString())) {
				cacheDeleteTreeNodeMap.put(org.getId().toString(), org);
			}
			MsgBox.showWarning("删除成功!");
		}
	}

	/**
	 * 描述：插入行数据
	 * @Author：adward_huang
	 * @CreateTime：2012-10-13
	 */
	private void insertRowData() {
		int index = treeTable.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			MsgBox.showWarning("请选择表格行数据!");
			return;
		}

		if (index == 0) {
			MsgBox.showWarning("不能插入与集团平级的组织，请进行 增加行 操作!");
			return;
		}

		ScheduleReportOrgInfo currentOrg = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();

		ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
		ScheduleReportOrgInfo parent = currentOrg.getParent();
		org.setParent(parent);
		org.setLevel(currentOrg.getLevel());
		BOSUuid uuid = getScheduleReportBOSUuid();
		org.setId(uuid);

		int childrenSize = getChildrenSize(parent);
		cacheChildrenSize.put(parent.getId().toString(), childrenSize + 1);
		IRow row = treeTable.addRow(index);
		int sortNumber = getInsertRowSortNumber(parent, index);
		org.setSortNumber(sortNumber);
		row.setUserObject(org);
		loadRow(row);
		// 修改同级该节点下面节点的排序数字
		updateChildrenSortNumber(parent, index, 1);
	}

	/**
	 * 描述：根据插入的行数据获取其排序编码
	 * @param parent
	 * @param curIndex
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-25
	 */
	private int getInsertRowSortNumber(ScheduleReportOrgInfo parent, int curIndex) {
		List<Integer> childrenIndex = getAllChildrenIndex(parent);
		int number = 0;
		for (Integer index : childrenIndex) {
			if (curIndex > index) {
				number++;
			}
		}
		return number + 1;
	}

	/**
	 * 描述：获取parent下的子节点数
	 * @param parent
	 * @Author：adward_huang
	 * @CreateTime：2012-10-24
	 */
	private int getChildrenSize(ScheduleReportOrgInfo parent) {
		// 获取缓存中父节点下子节点数
		Integer childrenSize = cacheChildrenSize.get(parent.getId().toString());
		int addIndex = 0;
		if (childrenSize != null && childrenSize.intValue() > 0) {
			addIndex = childrenSize.intValue();
		}
		return addIndex;
	}

	/**
	 * 描述：获取parent下的子节点数
	 * @param parent
	 * @Author：adward_huang
	 * @CreateTime：2012-10-24
	 */
	private int getAllChildrenSize(ScheduleReportOrgInfo parent) {

		List<ScheduleReportOrgInfo> childrenList = new ArrayList<ScheduleReportOrgInfo>();
		getAllChildrenByParent(parent, childrenList);

		return childrenList.size();
	}

	/**
	 * 描述：获取该节点下所有子节点
	 * @param parent
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-29
	 */
	private void getAllChildrenByParent(ScheduleReportOrgInfo parent, List<ScheduleReportOrgInfo> childrenList) {
		IRow row = null;
		ScheduleReportOrgInfo org = null;
		int rowCount = treeTable.getRowCount();
		for (int i = 1; i < rowCount; i++) {
			row = treeTable.getRow(i);
			org = (ScheduleReportOrgInfo) row.getUserObject();
			// 如果存在有父节点ID等于其ID，则存在子节点，返回true
			if (org != null && org.getParent() != null && org.getParent().getId().toString().equals(parent.getId().toString())) {
				childrenList.add(org);
				getAllChildrenByParent(org, childrenList);
			}
		}
	}

	/**
	 * 描述：根据ScheduleReportOrgInfo实体的BOSTYPE创建BOSUuid
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-13
	 */
	private BOSUuid getScheduleReportBOSUuid() {
		ScheduleReportOrgInfo info = new ScheduleReportOrgInfo();
		return BOSUuid.create(info.getBOSType());
	}

	/**
	 * 描述：对名称进行F7操作加载
	 * @Author：adward_huang
	 * @CreateTime：2012-10-13
	 */
	@SuppressWarnings("serial")
	private void addF7CellEdit() {

		KDBizPromptBox prmtTask = new KDBizPromptBox() {

			@SuppressWarnings("unchecked")
			@Override
			protected Object popupSelector(Object param) {
				Object obj = super.popupSelector(param);

				int index = treeTable.getSelectManager().getActiveRowIndex();
				Object curObj = treeTable.getRow(index).getUserObject();
				// 如果是取消操作则返回
				if (KDPromptBox.DEFAULTVALUE == obj) {
					return curObj;
				}

				ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();

				if (org.getRelateOrg() != null
						&& FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID.equals(org.getRelateOrg().getId().toString())) {
					MsgBox.showWarning("集团不能修改!");
					return org;
				}

				if (org.getName() != null && hasChildren(org)) {
					MsgBox.showWarning("该组织存在下属组织，不能重新指定组织!");
					return org;
				}

				// 重新选择组织时，需要将之前的组织删除
				if (cacheLoadTreeNodeMap.containsKey(org.getId().toString())) {
					cacheDeleteTreeNodeMap.put(org.getId().toString(), org);
				}

				List<Map.Entry<String, DefaultKingdeeTreeNode>> checkedNodes = null;
				try {
					checkedNodes = (List<Map.Entry<String, DefaultKingdeeTreeNode>>) obj;
				} catch (Exception e) {// 此处异常为取消操作时抛出，在此返回空对象
					e.printStackTrace();
					logger.error("F7控件选择的数据异常，请重新刷新界面再操作!");
					return null;
				}

				Object first = null;

				// 是否存在形同节点，存在则返回Null,不存在则返回没有重复的节点信息
				boolean flag = false;
				if (checkedNodes != null && !checkedNodes.isEmpty()) {
					IRow selectRow = treeTable.getRow(index);
					ScheduleReportOrgInfo selectOrg = (ScheduleReportOrgInfo) selectRow.getUserObject();
					first = getScheduleReportOrg(checkedNodes.get(0).getValue(), selectOrg);

					flag = addTableTreeNode(checkedNodes, selectOrg);
					if (!flag) {
						selectRow.getCell(NUMBER).setValue(((ScheduleReportOrgInfo) first).getNumber());
						selectRow.setUserObject(first);
					}
				}
				return flag ? curObj : first;
			}
		};
		// F7组织树弹出窗体
		F7ScheduleReportTreeMaintenaceDialog scheduleReportDialog = new F7ScheduleReportTreeMaintenaceDialog(this);
		prmtTask.setSelector(scheduleReportDialog);

		prmtTask.setHistoryRecordEnabled(false);
		ICellEditor editor = new KDTDefaultCellEditor(prmtTask);
		treeTable.getColumn(NAME).setEditor(editor);
	}

	/**
	 * 描述：添加表格树节点
	 * @param checkedNodes
	 * @param curNode
	 * @Author：adward_huang
	 * @CreateTime：2012-10-15
	 */
	private boolean addTableTreeNode(List<Map.Entry<String, DefaultKingdeeTreeNode>> checkedNodes, ScheduleReportOrgInfo curNode) {

		int size = checkedNodes.size();
		List<String> existNameList = new ArrayList<String>();
		boolean exist = false;
		int index = treeTable.getSelectManager().getActiveRowIndex();
		ScheduleReportOrgInfo curOrg = (ScheduleReportOrgInfo) treeTable.getRow(index).getUserObject();
		for (int i = 0; i < size; i++) {
			ScheduleReportOrgInfo org = getScheduleReportOrg(checkedNodes.get(i).getValue(), curNode);

			if (!exist) {
				exist = existSameNode(org, existNameList, curOrg);
				if (exist) {
					break;
				}
			}
			if (i != 0 && org != null) {
				int addIndex = getChildrenSize(org.getParent());
				cacheChildrenSize.put(org.getParent().getId().toString(), addIndex + 1);
				// 如果是插入行操作，则排序码根据所选节点自增
				if (actionType == FDCScheduleConstant.ACTION_TYPE_INSERT) {
					org.setSortNumber(curNode.getSortNumber() + i);
				} else {
					// 如果是新增行操作，则排序码根据子节点大小在最后增加
					org.setSortNumber(addIndex);
				}
				IRow row = treeTable.addRow(index + i);
				row.setUserObject(org);
				loadRow(row);
				// 修改同级该节点下面节点的排序数字
				updateChildrenSortNumber(org.getParent(), index + i, 1);
			}
		}

		if (exist) {
			MsgBox.showWarning("不能添加重复的组织信息，名称依次为：" + Arrays.toString(existNameList.toArray()) + "，请选择其他组织！");
		}
		return exist;
	}

	/**
	 * 描述：将树节点信息封装为进度报表对象
	 * @param node
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-15
	 */
	private ScheduleReportOrgInfo getScheduleReportOrg(DefaultKingdeeTreeNode node, ScheduleReportOrgInfo selectOrg) {
		ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
		if (node == null) {
			return org;
		}
		Object obj = node.getUserObject();
		String name = node.getText();
		BOSUuid unitId = null;
		int level = selectOrg.getLevel();
		String number = null;

		// 如果节点是工程项目
		if ((obj != null) && (obj instanceof CurProjectInfo)) {
			CurProjectInfo info = (CurProjectInfo) obj;
			unitId = info.getId();
			number = info.getNumber();
		}

		// 如果节点是组织结构
		if ((obj != null) && (obj instanceof OrgStructureInfo)) {
			OrgStructureInfo info = (OrgStructureInfo) obj;
			unitId = info.getId();
			number = info.getUnit().getNumber();
		}

		// 如果节点是组织单元
		if ((obj != null) && (obj instanceof FullOrgUnitInfo)) {
			FullOrgUnitInfo info = (FullOrgUnitInfo) obj;

			unitId = info.getId();
			number = info.getNumber();
		}
		FullOrgUnitInfo unit = new FullOrgUnitInfo();
		unit.setId(unitId);
		unit.setName(name);
		unit.setNumber(number);
		org.setRelateOrg(unit);

		BOSUuid uuid = getScheduleReportBOSUuid();
		org.setId(uuid);
		org.setName(name);
		org.setNumber(number);
		if (selectOrg.getParent() != null) {
			org.setLongNumber(getNodeLongNumber(selectOrg.getParent().getLongNumber(), number));
		} else {
			org.setLongNumber(number);
		}
		org.setLevel(level);
		org.setSortNumber(selectOrg.getSortNumber());
		org.setRelateOrg(unit);
		org.setParent(selectOrg.getParent());
		return org;
	}

	/**
	 * 描述：按照长编码对组织结构树进行过滤
	 * @param orgCollection
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-13
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleReportOrgInfo> orderedOrgTreeNode1(ScheduleReportOrgCollection orgCollection) {
		List<ScheduleReportOrgInfo> list = new ArrayList<ScheduleReportOrgInfo>();
		for (Iterator iter = orgCollection.iterator(); iter.hasNext();) {
			ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) iter.next();
			list.add(org);
			// 缓存从数据库中加载的节点信息
			cacheLoadTreeNodeMap.put(org.getId().toString(), org);
			if (org.getParent() != null) {
				String parentID = org.getParent().getId().toString();
				// 如果存在子节点，则加1
				if (cacheChildrenSize.containsKey(parentID)) {
					int size = cacheChildrenSize.get(parentID);
					cacheChildrenSize.put(parentID, size + 1);
				} else {
					cacheChildrenSize.put(parentID, 1);
				}
			}
		}
		Collections.sort(list, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ScheduleReportOrgInfo source = (ScheduleReportOrgInfo) arg0;
				ScheduleReportOrgInfo target = (ScheduleReportOrgInfo) arg1;
				int result = 0;
				if (source.getLongNumber() != null && target.getLongNumber() != null) {
					if (source.getLevel() == target.getLevel()) {
						result = String.valueOf(source.getSortNumber()).compareTo(String.valueOf(target.getSortNumber()));
					} else {
						result = source.getLongNumber().compareTo(target.getLongNumber());
					}
					return result;
				}
				return result;
			}
		});
		return list;
	}

	/**
	 * 描述：加载表格行数据
	 * @param row
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	protected void loadRow(IRow row) {
		ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) row.getUserObject();
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setTreeLevel(org.getLevel());// 级次，从1开始，此处为任务的树级次
		treeNode.setHasChildren(!org.isIsLeaf());
		treeNode.isCollapse();
		treeNode.addClickListener(new NodeClickListener() {
			public void doClick(CellTreeNode source, ICell cell, int type) {
				treeTable.revalidate();
			}
		});
		row.getCell(TREE_CELL).setValue(treeNode);
		//		row.getCell(TREE_CELL).getStyleAttributes().setLocked(false);
		row.getCell(NUMBER).setValue(org.getNumber());
		//		row.getCell(NUMBER).getStyleAttributes().setLocked(false);
		//		row.getCell(NAME).getStyleAttributes().setLocked(false);

		row.getCell(NAME).setValue(org.getName());
		ScheduleReportOrgInfo parent = org.getParent();
		if (parent != null) {
			row.getCell(PARENT_NAME).setValue(parent.getName());
			row.getCell(PARENT_NUMBER).setValue(parent.getNumber());
		} else {
			row.getCell(PARENT_NAME).setValue("");
			row.getCell(PARENT_NUMBER).setValue("");
		}
		row.getCell(REMARK).setValue(org.getRemark());
		//		row.getCell(REMARK).getStyleAttributes().setLocked(false);
		if (org.getLevel() != 0) {
			addF7CellEdit();
		} else {
			row.setEditor(null);
			row.getCell(NAME).setEditor(null);

		}

		// 将新增行数据加入缓存
		cacheUpdateTreeNodeMap.put(org.getId().toString(), org);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeMaintenanceUI#actionSave_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (isGroupOrg) {
			int rowCount = this.treeTable.getRowCount();
			// 是否保存成功
			boolean flag = true;
			List<ScheduleReportOrgInfo> updateList = new ArrayList<ScheduleReportOrgInfo>();

			if (rowCount > 1) {
				IRow row = null;
				ScheduleReportOrgInfo org = null;
				Object name = null;
				Object number = null;
				Object remark = null;
				// 集团信息不进行修改，故0行不取
				for (int i = 1; i < rowCount; i++) {
					row = treeTable.getRow(i);
					org = (ScheduleReportOrgInfo) row.getUserObject();
					number = row.getCell(NUMBER).getValue();
					name = row.getCell(NAME).getValue();
					remark = row.getCell(REMARK).getValue();
					if (FDCHelper.isEmpty(number) || FDCHelper.isEmpty(name)) {
						flag = false;
						continue;
					}
					if (FDCHelper.isEmpty(remark)) {
						remark = "";
					}
					org.setName(name.toString());
					org.setNumber(number.toString());
					org.setRemark(remark.toString());
					org.setLongNumber(getNodeLongNumber(org.getParent().getLongNumber(), org.getNumber()));
					updateList.add(org);
					//	ScheduleReportOrgFactory.getRemoteInstance().save(getScheduleReportBOSUuidPK(org.getId()),org);
				}

			}
			if (!flag) {
				MsgBox.showInfo("存在为名称或编码为空的数据，请完善信息再保存!");
				return;
			}
			List<ScheduleReportOrgInfo> deleteList = new ArrayList<ScheduleReportOrgInfo>();
			if (!cacheDeleteTreeNodeMap.isEmpty()) {
				for (Iterator<Map.Entry<String, ScheduleReportOrgInfo>> iter = cacheDeleteTreeNodeMap.entrySet().iterator(); iter.hasNext();) {
					ScheduleReportOrgInfo info = iter.next().getValue();
					deleteList.add(info);
				}
			}

			if (!updateList.isEmpty() || !deleteList.isEmpty()) {
				ScheduleReportOrgFactory.getRemoteInstance().updateScheduleReportData(updateList, deleteList);
			}
			if (flag) {
				fillTable();
				cacheUpdateTreeNodeMap.clear();
				cacheDeleteTreeNodeMap.clear();
				MsgBox.showInfo("保存成功!");
			}
		}
	}

	/**
	 * 描述：获取节点的长编码
	 * @param parentLongNumber
	 * @param longNumber
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-12
	 */
	private String getNodeLongNumber(String parentLongNumber, String longNumber) {
		if (parentLongNumber == null) {
			return longNumber;
		}
		return parentLongNumber + FDCScheduleConstant.REPORT_TREE_SEPARATOR + longNumber;
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeMaintenanceUI#actionUpdate_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (isGroupOrg) {
			if (!isEditable) {
				isEditable = true;
				setEditColumn(false);
			}
		}

	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportTreeMaintenanceUI#actioRefresh_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actioRefresh_actionPerformed(ActionEvent e) throws Exception {
		if (isGroupOrg) {
			clearCacheData();
			fillTable();
		}
	}

	/**
	 * 描述：清空缓存数据
	 * @Author：adward_huang
	 * @CreateTime：2012-10-24
	 */
	private void clearCacheData() {
		cacheLoadTreeNodeMap.clear();
		cacheUpdateTreeNodeMap.clear();
		cacheDeleteTreeNodeMap.clear();
		cacheChildrenSize.clear();
	}

	/**
	 * 描述：添加集团组织
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	private void addDefaultGroupTreeNode() {

		try {
			boolean flag = ScheduleReportOrgFactory.getRemoteInstance().checkDefaultGroupOrg();
			if (!flag) {
				String number = FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID;
				String name = FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NAME;
				String remark = "集团节点，不能修改和删除!";

				ScheduleReportOrgInfo org = new ScheduleReportOrgInfo();
				org.setName(name.toString());
				org.setNumber(FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NUMBER);
				org.setRemark(remark == null ? "" : remark.toString());
				org.setLongNumber(FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NUMBER);
				org.setLevel(0);
				org.setParent(null);
				org.setDisplayName(name);
				org.setIsLeaf(false);
				org.setSortNumber(0);
				FullOrgUnitInfo unit = new FullOrgUnitInfo();
				unit.setId(BOSUuid.read(number));
				unit.setName(name);
				org.setRelateOrg(unit);
				ScheduleReportOrgFactory.getRemoteInstance().save(new ObjectUuidPK(org.getId()), org);
			}
		} catch (EASBizException e) {
			this.handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			this.handUIExceptionAndAbort(e);
		}
	}
}