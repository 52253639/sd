/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.TreeExpansionAdapter;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ImportCompayFieldsEnum;
import com.kingdee.eas.basedata.org.client.helper.OuF7SetCacheHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:进度报表查询范围界面
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleReportOrgQueryUI extends AbstractScheduleReportOrgQueryUI {

	private static final long serialVersionUID = -6344032136728603601L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportOrgQueryUI.class);

	public static final String RETURNDATA = "returnData";
	// 获取界面操作所有选中的节点
	public Map<String, DefaultKingdeeTreeNode> checkedMapForPublic = new HashMap<String,DefaultKingdeeTreeNode>();
	public static List<String> selectedKeyList = new ArrayList<String>();
	public static List<String> selectedValueForPublicList = new ArrayList<String>();
	private boolean isCancel = false;

	private OuF7SetCacheHelper setCacheHelper = new OuF7SetCacheHelper();

	// 树节点的子节点数据
	private Map<String, List<ScheduleReportOrgInfo>> allNodeChildrensMap = new HashMap<String, List<ScheduleReportOrgInfo>>();

	/**
	 * output class constructor
	 */
	public ScheduleReportOrgQueryUI() throws Exception {
		super();

		// 初始化控件属性
		initWidgetState();
	}

	/**
	 * @see com.kingdee.eas.framework.client.CoreUI#onLoad()
	 */
	@Override
	public void onLoad() throws Exception {
		/* TODO 自动生成方法存根 */
		super.onLoad();
		this.allCheckBtn.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		this.unAllCheckBtn.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		// 初始化工程树
		initProjectTree();
	}

	/**
	 * 描述：初始化控件属性
	 * @Author：adward_huang
	 * @CreateTime：2012-9-17
	 */
	private void initWidgetState() {
		this.btnCancel.setEnabled(true);
		this.btnConfirm.setEnabled(true);
		this.btnQuery.setEnabled(true);

		this.checkBlured.setSelected(true);

		initQueryTypeComboxState();

	}

	/**
	 * 描述：初始化搜索类别控件（名称和编码）
	 * @Author：adward_huang
	 * @CreateTime：2012-9-17
	 */
	private void initQueryTypeComboxState() {
		this.cbQueryField.addItem(ImportCompayFieldsEnum.NUMBER);
		this.cbQueryField.addItem(ImportCompayFieldsEnum.NAME);
		this.cbQueryField.setSelectedIndex(0);
	}

	/**
	 * 描述： 初始化工程树
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-17
	 */
	private void initProjectTree() throws Exception {

		TreeSelectionModel sm = new DefaultTreeSelectionModel();
		sm.setSelectionMode(2);
		this.kDOrgTree.setSelectionModel(sm);

		this.kDOrgTree.addTreeExpansionListener(new TreeExpansionAdapter() {
			public void treeCollapsed(TreeExpansionEvent event) {
				if (!(event.getPath().getLastPathComponent().equals(kDOrgTree.getModel().getRoot())))
					return;
				kDOrgTree.expandPath(new TreePath(kDOrgTree.getModel().getRoot()));
			}
		});

		buildProjectTree();
	}

	/**
	 * 描述：根据选择的Node计算出公共的parent Node Text
	 * @param parent
	 * @Author：adward_huang
	 * @CreateTime：2012-10-29
	 */
	private String getCheckedParent() throws Exception {
		
		// 获取界面操作所有选中的节点
		Map<String, DefaultKingdeeTreeNode> checkedMap = getCheckedTreeNodes();
		String name = "";
		
		if(checkedMap != null && checkedMap.size() == 1){
			name = checkedMap.entrySet().iterator().next().getValue().getText();
			return name;
		}
		
		List<Map.Entry<String, DefaultKingdeeTreeNode>> sortedMapByLevelList = ScheduleChartHelper.sortedMapByLevel(checkedMap);

		// level最小的节点
		DefaultKingdeeTreeNode minNode = sortedMapByLevelList.get(0).getValue();
		
		int minLevel = minNode.getLevel();
		int minSize = 1;
		
		// 记录最小level的个数
		for(int i = 1;i<sortedMapByLevelList.size();i++){
			Map.Entry<String, DefaultKingdeeTreeNode> map = sortedMapByLevelList.get(i);
			if( map.getValue().getLevel() > minLevel){
				break;
			}else{
				minSize ++;
			}
		}
		
		// 如果存在相同level的节点，并且该level在所选节点中最小，则返回空
		if(minSize > 1){
			return name;
		}

		// 如果所选择的最小level包含所有选择节点，则返回该节点的组织名称
		boolean flag = containsAllNodes(checkedMap, minNode);
		if(flag){
			name = minNode.getText();
		}
		return name;
	}

	/**
	 * 描述：是否包含所有选中的节点信息
	 * @param checkedMap
	 * @param node
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-29
	 */
	private boolean containsAllNodes(Map<String, DefaultKingdeeTreeNode> checkedMap, DefaultKingdeeTreeNode node) {

		Map<String, ScheduleReportOrgInfo> containsMap = new HashMap<String, ScheduleReportOrgInfo>();
		List<ScheduleReportOrgInfo> childrenList = allNodeChildrensMap.get(getNodeIdAndName(node)[0]);
		generateAllCheckedNodes(checkedMap, childrenList, containsMap);

		boolean flag = false;

		if (checkedMap.size() == containsMap.size()) {
			flag = true;
		}
		ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
		String id = org.getId().toString();
		if (org.getRelateOrg() != null) {
			id = org.getRelateOrg().getId().toString();
		}
		if (checkedMap.containsKey(id)) {
			if (checkedMap.size() <=containsMap.size() || (checkedMap.size() - 1) == containsMap.size()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 描述：迭代所有子节点，与选中节点进行对比，是否包含其中
	 * @param checkedMap
	 * @param childrenList
	 * @param containsMap
	 * @Author：adward_huang
	 * @CreateTime：2012-10-29
	 */
	private void generateAllCheckedNodes(Map<String, DefaultKingdeeTreeNode> checkedMap, List<ScheduleReportOrgInfo> childrenList,
			Map<String, ScheduleReportOrgInfo> containsMap) {
		List<ScheduleReportOrgInfo> subChildrenList = null;
		for (ScheduleReportOrgInfo org : childrenList) {
			String id = org.getId().toString();
			if (org.getRelateOrg() != null) {
				id = org.getRelateOrg().getId().toString();
			}
			if (checkedMap.containsKey(id)) {
				containsMap.put(org.getId().toString(), org);
			}

			if (allNodeChildrensMap.containsKey(org.getId().toString())) {
				subChildrenList = allNodeChildrensMap.get(org.getId().toString());
				generateAllCheckedNodes(checkedMap, subChildrenList, containsMap);
			}
		}
	}

	/**
	 * 描述：获取某节点的所有父节点信息
	 * @param node
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-10-29
	 */
	/*private List<DefaultKingdeeTreeNode> getAllParentsNode(DefaultKingdeeTreeNode node) {
		List<DefaultKingdeeTreeNode> list = new ArrayList<DefaultKingdeeTreeNode>();
		if (node == null) {
			return list;
		}
		list.add(node);
		if (node.getParent() == null) {
			return list;
		}
		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node.getParent();
		list.add(parent);
		while (parent.getParent() != null) {
			parent = (DefaultKingdeeTreeNode) parent.getParent();
			list.add(parent);
		}
		return list;
	}*/

	/**
	 * private方法修改为public方法，子类可以修改工程项目树的构建方式
	 * 原因：进度管理中工程项目的构建取当前组织上级财务组织下的所有工程项目，默认取的当前组织下的工程项目
	 * @throws Exception
	 */
	private void buildProjectTree() throws Exception {
		buildDefaultTree();
		this.kDOrgTree.repaint();
		this.kDOrgTree.setShowCheckBox(true);
		this.kDOrgTreeView.setShowControlPanel(false);
	}

	/**
	 * 描述：构建自定义组织树
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	protected void buildDefaultTree() throws Exception {
		buildDefaultReportTree();
	}

	/**
	 * 描述：获取选中的组织和工程
	 * @return
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-18
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	private Map<String, String> getCheckedNodes() throws Exception {
		Map<String, String> checkedNodes = new HashMap<String, String>();
		if (this.kDOrgTree.getModel() != null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.kDOrgTree.getModel().getRoot();

			if (root != null) {
				Enumeration enumOrg = root.breadthFirstEnumeration();
				while (enumOrg.hasMoreElements()) {
					DefaultKingdeeTreeNode objNode = (DefaultKingdeeTreeNode) enumOrg.nextElement();
					getCheckedNodeChildIds(objNode, checkedNodes);

				}
			}
		}
		return checkedNodes;
	}

	/**
	 * 描述：获取选中的组织和工程
	 * @return
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-18
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, DefaultKingdeeTreeNode> getCheckedTreeNodes() throws Exception {
		Map<String, DefaultKingdeeTreeNode> checkedNodes = new HashMap<String, DefaultKingdeeTreeNode>();
		if (this.kDOrgTree.getModel() != null) {
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.kDOrgTree.getModel().getRoot();

			if (root != null) {
				Enumeration enumOrg = root.breadthFirstEnumeration();
				while (enumOrg.hasMoreElements()) {
					DefaultKingdeeTreeNode objNode = (DefaultKingdeeTreeNode) enumOrg.nextElement();
					getCheckedNodeChildNodes(objNode, checkedNodes);
				}
			}
		}
		return checkedNodes;
	}

	/**
	 * 描述：获取选中的子节点信息
	 * @param node
	 * @param idMap
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	private void getCheckedNodeChildIds(DefaultKingdeeTreeNode node, Map<String, String> idMap) {
		if (node.isLeaf() && node.isChecked()) {
			if (node.getUserObject() instanceof ScheduleReportOrgInfo) {
				ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
				idMap.put(org.getId().toString(), org.getName());
			}
		} else if (!node.isLeaf() && node.isChecked()) {
			if (node.getUserObject() instanceof ScheduleReportOrgInfo) {
				ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
				idMap.put(org.getId().toString(), org.getName());
			}
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
				getCheckedNodeChildIds(child, idMap);
			}
		}
	}

	/**
	 * 描述：根据节点对象获取节点对应的ID
	 * @param node
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	private String[] getNodeIdAndName(DefaultKingdeeTreeNode node) {

		if (node == null) {
			return null;
		}
		String[] idAndName = new String[2];
		if (node.getUserObject() instanceof ScheduleReportOrgInfo) {
			ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
			idAndName[0] = org.getId().toString();
			idAndName[1] = org.getName();
		}
		return idAndName;
	}

	/**
	 * 描述：获取选中的子节点信息,包括ID和名称
	 * @param node
	 * @param idMap
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	private void getCheckedNodeChildNodes(DefaultKingdeeTreeNode node, Map<String, DefaultKingdeeTreeNode> nodeMap) {
		if (node.isChecked()) {
			setCheckedParentNode(node, nodeMap);
		} else if (!node.isLeaf() && node.isChecked()) {
			if (node.getUserObject() instanceof ScheduleReportOrgInfo) {
				ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
				FullOrgUnitInfo unit = org.getRelateOrg();
				if (unit != null) {
					nodeMap.put(unit.getId().toString(), node);
				} else {
					nodeMap.put(org.getId().toString(), node);
				}

			}
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
				getCheckedNodeChildNodes(child, nodeMap);
			}
		}
	}
	public Map<String, DefaultKingdeeTreeNode> getCheckedMapForPublic()throws Exception {
		return checkedMapForPublic;
	}
	
	public static List<String>  getselectedKeyList()throws Exception {
		return selectedKeyList;
	}
	public static List<String>  getselectedValueForPublicList()throws Exception {
		return selectedValueForPublicList;
	}
	public static void setselectedValueForPublicListClear()throws Exception {
		selectedValueForPublicList.clear();
	}
	/**
	 * 描述：获取界面选择节点的所有工程项目信息，如果存在已选中的父级节点，则将父级下面的所有工程信息加入，
	 *      如果不存在已选中的父级节点，则只包含自身或其下的所有节点
	 * @return
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	private Map<String, Map<String, String>> getCheckedNodesByParent() throws Exception {

		// 获取界面操作所有选中的节点
		Map<String, DefaultKingdeeTreeNode> checkedMap = getCheckedTreeNodes();
		checkedMapForPublic = getCheckedTreeNodes();
		/*// 根据选择的节点信息，过滤后剩下最顶级的节点或父级无选中节点
		Map<String, DefaultKingdeeTreeNode> resultNodeMap = new HashMap<String, DefaultKingdeeTreeNode>();

		Map<String, DefaultKingdeeTreeNode> checkedNodeMap = null;*/
		List<String> selectValueList = new ArrayList<String>();
		selectedKeyList.clear();
		selectedValueForPublicList.clear();
		for (Iterator<Map.Entry<String, DefaultKingdeeTreeNode>> iter = checkedMap.entrySet().iterator(); iter.hasNext();) {
			// 选中的节点信息
			Map.Entry<String, DefaultKingdeeTreeNode> map = iter.next();
			DefaultKingdeeTreeNode node = map.getValue();

			String[] nodeInfo = getNodeIdAndName(node);
			selectValueList.add(nodeInfo[1]);
			selectedValueForPublicList.add(nodeInfo[1]);
			selectedKeyList.add(nodeInfo[0]);
		}
		// 设置选中的工程项目和组织名称
		this.setSelectProAndOrgList(selectValueList);

		// 返回的结果集<parentId,<childId,name>>
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();

		Map<String, String> checkedNodeIdMap = null;
		for (Iterator<Map.Entry<String, DefaultKingdeeTreeNode>> iter = checkedMap.entrySet().iterator(); iter.hasNext();) {
			// 选中的节点信息
			Map.Entry<String, DefaultKingdeeTreeNode> map = iter.next();
			DefaultKingdeeTreeNode node = map.getValue();

			checkedNodeIdMap = new HashMap<String, String>();

			// 根据父节点获取所有的子节点数据
			getAllChildProjectNodeIds(node, checkedNodeIdMap);
			String[] nodeInfo = getNodeIdAndName(node);

			// 设置组织或项目的名称
			checkedNodeIdMap.put(FDCScheduleConstant.CHART_X_AXIS_NAME, nodeInfo[1]);
			
			// 设置排序编码
			String sortNumber = ScheduleChartHelper.getSortedNodeNumber(node);
			checkedNodeIdMap.put(FDCScheduleConstant.CHART_SELECT_SORTED_ORG, sortNumber);

			// 按最顶级选中节点来获取所包含的工程项目ID及其名称
			resultMap.put(nodeInfo[0], checkedNodeIdMap);
		}
		return resultMap;
	}

	/**
	 * 描述：获取所有子节点信息，只包含所有的工程项目子节点
	 * @param node
	 * @param idMap
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	private void getAllChildProjectNodeIds(DefaultKingdeeTreeNode node, Map<String, String> idMap) {
		if (node != null && node.isLeaf()) {
			if (node.getUserObject() instanceof ScheduleReportOrgInfo) {
				ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
				FullOrgUnitInfo unit = org.getRelateOrg();
				if (unit != null) {
					idMap.put(unit.getId().toString(), org.getName());
				} else {
					idMap.put(org.getId().toString(), org.getName());
				}
			}
		} else if (node != null && !node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
				getAllChildProjectNodeIds(child, idMap);
			}
		}
	}

	/**
	 * 描述：检测某个节点是否存在选中状态的父节点
	 * @param node
	 * @param nodeMap		size > 0 表示存在上级节点选中，并且上级选中节点信息存在于idMap中
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	@SuppressWarnings("unused")
	private void getCheckedParentNode(DefaultKingdeeTreeNode node, Map<String, DefaultKingdeeTreeNode> nodeMap, String nodeId,
			DefaultKingdeeTreeNode nodeSelf) {
		if (node != null) {
			DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node.getParent();
			if (parent != null) {
				// 如果父节点是选中状态，则判断之前是否有选中的父节点，有则覆盖，没有则增加
				if (parent.isChecked()) {
					if (!nodeMap.isEmpty() && nodeMap.size() > 0) {
						nodeMap.clear();
						setCheckedParentNode(parent, nodeMap);
					} else {
						setCheckedParentNode(parent, nodeMap);
					}
				}
				getCheckedParentNode(parent, nodeMap, nodeId, nodeSelf);
			}
		}

		if (nodeMap == null || nodeMap.isEmpty()) {
			nodeMap.put(nodeId, nodeSelf);
		}
	}

	protected void setCheckedParentNode(DefaultKingdeeTreeNode node, Map<String, DefaultKingdeeTreeNode> nodeMap) {
		if (node.getUserObject() instanceof ScheduleReportOrgInfo) {
			ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
			FullOrgUnitInfo unit = org.getRelateOrg();
			if (unit != null) {
				nodeMap.put(unit.getId().toString(), node);
			} else {
				nodeMap.put(org.getId().toString(), node);
			}
		}
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportOrgQueryUI#actionSearch_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {

		String value = this.queryFieldTxt.getText() == null ? null : this.queryFieldTxt.getText().trim();
		int field = ((ImportCompayFieldsEnum) this.cbQueryField.getSelectedItem()).getValue();
		boolean isBlured = this.checkBlured.isSelected();

		// 过滤树形节点
		filterTreeModelByNumberAndName(field, value, isBlured);

		this.kDOrgTree.setShowCheckBox(true);
	}

	private void filterTreeModelByNumberAndName(int field, String value, boolean isBlured) {

		// 过滤值不为空才进行过滤操作
		if (value != null && value.trim().length() > 0) {

			DefaultTreeModel model = (DefaultTreeModel) kDOrgTree.getModel();
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
			// 根据值和类型过滤树节点
			disableNodeByFilter(root, field, value, isBlured);
			//			OrgF7InnerUtils.clipTreeNodeByColor(root);
			model.setRoot(root);
			this.kDOrgTree.setShowCheckBox(true);
		}
	}

	/**
	* 描述：根据名称和编码过滤树节点
	* @param root
	* @param field
	* @param value
	* @param blured
	* @Author：adward_huang
	* @CreateTime：2012-9-19
	*/
	@SuppressWarnings("unchecked")
	private void disableNodeByFilter(DefaultKingdeeTreeNode root, int field, String value, boolean blured) {

		DefaultKingdeeTreeNode node = null;
		Enumeration nodeEnum = root.breadthFirstEnumeration();
		while (nodeEnum.hasMoreElements()) {
			node = (DefaultKingdeeTreeNode) nodeEnum.nextElement();

			Object obj = node.getUserObject();

			if (obj != null && obj instanceof ScheduleReportOrgInfo) {
				ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) node.getUserObject();
				String numberOrName = getNumberOrName(field, org);
				filterTreeNode(value, blured, node, numberOrName);
			}
		}
	}

	/**
	 * 描述：获取相关节点的名称或编码信息
	 * @param field
	 * @param info
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-19
	 */
	private String getNumberOrName(int field, ScheduleReportOrgInfo info) {
		String numberOrName = null;
		if (field == 1)
			numberOrName = info.getNumber();
		else if (field == 2) {
			numberOrName = info.getName();
		}
		return numberOrName;
	}

	/**
	 * 描述：过滤树节点
	 * @param value
	 * @param blured
	 * @param node
	 * @param numberOrName
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	private void filterTreeNode(String value, boolean blured, DefaultKingdeeTreeNode node, String numberOrName) {
		if (numberOrName == null) {
			disableTreeNode(node);
		} else if (blured) {
			if (numberOrName.indexOf(value) == -1) {
				disableTreeNode(node);
			}
		} else if (!(numberOrName.equalsIgnoreCase(value))) {
			disableTreeNode(node);
		}
		if (value == null) {
			displayTreeNode(node);
		}
	}

	/**
	 * 描述：隐藏树节点
	 * @param treeNode
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	public void disableTreeNode(DefaultKingdeeTreeNode treeNode) {
		treeNode.setTextColor(new Color(77, 77, 77));
		treeNode.setCheckBoxEnabled(false);
		treeNode.setCheckBoxVisible(false);
	}

	/**
	 * 描述：显示树节点
	 * @param treeNode
	 * @Author：adward_huang
	 * @CreateTime：2012-10-17
	 */
	public void displayTreeNode(DefaultKingdeeTreeNode treeNode) {
		treeNode.setTextColor(null);
		treeNode.setCheckBoxEnabled(true);
		treeNode.setCheckBoxVisible(true);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportOrgQueryUI#actionConfirm_actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {

		// 1.用户返回组织或工程项目的选择信息
		//		Map<String, Map<String, String>> map = getCheckedNodesByParent();

		isCancel = false;
		Map<String, Map<String, String>> map = getCheckedNodesByParent();
		if (map == null || map.isEmpty()) {
			FDCMsgBox.showWarning(this, "请选择组织或工程项目!");
			SysUtil.abort();
		}
		String showProjectName = getCheckedParent();
		Object mapObj = map;
		this.getUIContext().put(RETURNDATA, mapObj);
		this.getUIContext().put(FDCScheduleConstant.CHART_SELECT_PROJECT_NAME, showProjectName);
		super.actionConfirm_actionPerformed(e);
		destroyWindow();
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportOrgQueryUI#actionCancel_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		/* TODO 自动生成方法存根 */
		super.actionCancel_actionPerformed(e);
		isCancel = true;
		this.destroyWindow();
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportOrgQueryUI#actionAllCheck_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionAllCheck_actionPerformed(ActionEvent e) throws Exception {
		doAllSelAndAllClr(true);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportOrgQueryUI#actionUnAllCheck_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionUnAllCheck_actionPerformed(ActionEvent e) throws Exception {
		doAllSelAndAllClr(false);
	}

	/**
	 * 描述：全选和全清选择节点的所有子节点
	 * @param selected	true全选  false全清
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-28
	 */
	@SuppressWarnings("unchecked")
	private void doAllSelAndAllClr(boolean selected) throws Exception {

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.kDOrgTree.getModel().getRoot();
		if (this.kDOrgTree.getSelectionPath() != null) {
			root = (DefaultKingdeeTreeNode) this.kDOrgTree.getSelectionPath().getLastPathComponent();
		}
		if (root == null) {
			return;
		}
		Enumeration enu = root.breadthFirstEnumeration();
		DefaultKingdeeTreeNode node = null;
		Set allDelOrgIdSet = new HashSet();
		while (enu.hasMoreElements()) {
			node = (DefaultKingdeeTreeNode) enu.nextElement();
			if (canTheNodeBeSelected(node)) {
				node.setChecked(selected);
				Object obj = node.getUserObject();
				if (obj instanceof ScheduleReportOrgInfo) {
					ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) obj;
					allDelOrgIdSet.add(org.getId().toString());
				}
			}
		}
		if (!(selected)) {
			this.setCacheHelper.delOrgIdInAllOrgType(allDelOrgIdSet);
		}
		this.kDOrgTree.validate();
		this.kDOrgTree.repaint();
	}

	/**
	 * 描述：当前节点是否可选
	 * @param node
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-28
	 */
	private boolean canTheNodeBeSelected(DefaultKingdeeTreeNode node) {
		return ((node.isCheckBoxVisible()) && (node.isCheckBoxEnabled()));
	}

	public Object getData() {
		return getUIContext().get(RETURNDATA);
	}

	@SuppressWarnings("unchecked")
	public void setData(Object map) {
		getUIContext().put(RETURNDATA, map);
	}

	@SuppressWarnings("unchecked")
	public void setSelectProAndOrgList(List<String> list) {
		getUIContext().put(FDCScheduleConstant.CHART_SELECT_ORG_PRO, list);
	}

	@SuppressWarnings("unchecked")
	public List<String> getSelectProAndOrgList() {
		return (List<String>) getUIContext().get(FDCScheduleConstant.CHART_SELECT_ORG_PRO);
	}

	public String getProjectName() {
		return (String) getUIContext().get(FDCScheduleConstant.CHART_SELECT_PROJECT_NAME);
	}

	@SuppressWarnings("unchecked")
	public void setProjectName(String name) {
		getUIContext().put(FDCScheduleConstant.CHART_SELECT_PROJECT_NAME, name);
	}

	public boolean isCancel() {
		return isCancel;
	}

	/**
	 * 描述：构造进度报表自定义树
	 * @throws Exception 
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	private void buildDefaultReportTree() throws Exception {
		kDOrgTree.setModel(buildDefaultTreeModel());
		kDOrgTree.setShowPopMenuDefaultItem(false);
	}

	/**
	 * 描述：获取报表树的模型
	 * @return
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	public TreeModel buildDefaultTreeModel() throws Exception {

		ScheduleReportOrgInfo rootInfo = ScheduleChartHelper.getRootNodeInfo();

		KDTreeNode root = new KDTreeNode(rootInfo.getName());
		root.setUserObject(rootInfo);
		KingdeeTreeModel model = new KingdeeTreeModel(root);
		model.setRoot(root);
		ScheduleReportOrgInfo orgInfo = (ScheduleReportOrgInfo) root.getUserObject();
		List<ScheduleReportOrgInfo> allTreeNodeData = ScheduleChartHelper.getAllTreeNodeData(orgInfo);
		generateTreeNode(root, allTreeNodeData);
		return model;
	}

	/**
	 * 描述：遍历获取所有树节点
	 * @param node
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	private void generateTreeNode(DefaultMutableTreeNode node, List<ScheduleReportOrgInfo> orderedTreeNodeList) throws BOSException {
		ScheduleReportOrgInfo orgInfo = (ScheduleReportOrgInfo) node.getUserObject();
		List<ScheduleReportOrgInfo> childrenOrgs = ScheduleChartHelper.getChildrenOrgs(orgInfo, orderedTreeNodeList);
		allNodeChildrensMap.put(orgInfo.getId().toString(), childrenOrgs);
		ScheduleChartHelper.sortedListBySortNumber(childrenOrgs);
		for (int i = 0; i < childrenOrgs.size(); i++) {
			KDTreeNode child = new KDTreeNode(childrenOrgs.get(i).getName());
			child.setUserObject(childrenOrgs.get(i));
			node.add(child);
			generateTreeNode(child, orderedTreeNodeList);
		}
	}

}