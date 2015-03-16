package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Container;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.fdc.tenancy.client.CooperateModeListUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.scm.common.client.GeneralF7TreeListUI;
import com.kingdee.eas.scm.common.client.IF7Provider;

public class F7CooperateModeListUI extends CooperateModeListUI implements
IF7Provider, TreeSelectionListener{

	public F7CooperateModeListUI() throws Exception {
		super();
	}

	// @add 保存了快速过滤信息
	private FilterInfo quickFilterInfo;

	// @add 查询主键
	private IMetaDataPK queryPK;

	// @add 树实体组件
	private IMetaDataPK treeBasePK;

	// @add 树BOSType
	private String treeBaseBOSType;

	// @add树和表的关联属性名
	private String propName;

	private FilterInfo defaultFilterInfo;

	private boolean isMultiSelect = false;

	private String filterCUId = null;

	private String queryName;

	private OrgUnitCollection bizOrgCollection;

	private DefaultKingdeeTreeNode currentNode = null;

	public void onLoad() throws Exception {
		super.onLoad();
		this.treeView.setShowControlPanel(false);
	}

	public Object getData() {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("creator.number"));
        sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("lastUpdateUser.name"));
        sic.add(new SelectorItemInfo("id"));
		if (this.isMultiSelect) {
			Object[] keyValues = getSelectedIdValues().toArray();
			if (keyValues != null && keyValues.length > 0) {
				try {
					ICoreBase iBiz = getBizInterface();

					Set ids = new HashSet(keyValues.length * 2);
					for (int i = 0, c = keyValues.length; i < c; i++) {
						ids.add(keyValues[i]);
					}
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();

					filter.getFilterItems().add(
							new FilterItemInfo("id", ids, CompareType.INCLUDE));
					view.setFilter(filter);
					view.setSelector(sic);

					SorterItemCollection sorter = new SorterItemCollection();
					sorter.add(new SorterItemInfo("number"));
					view.setSorter(sorter);
					CoreBaseCollection coll = iBiz.getCollection(view);
					return coll.toArray();

				} catch (Exception er) {
					super.handUIException(er);
				}

			}
		} else {
			String keyValue = getSelectedKeyValue();
			if (keyValue != null) {
				try {
					ICoreBase iBiz = getBizInterface();
					ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
					return iBiz.getValue(pk, sic);
				} catch (Exception er) {
					super.handUIException(er);
				}
			}
		}
		return null;
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (isOrderForClickTableHead() && e.getClickCount() != 2) {
			super.tblMain_tableClicked(e);
		}
		if (e.getClickCount() == 2) {
			Container c = getParent();
			while (c != null && !c.getClass().equals(GeneralF7TreeListUI.class)) {
				c = c.getParent();
			}
			if (this.getData() != null)
				((GeneralF7TreeListUI) c).clickOKBtn();
		}
	}

	public void setAssociatePropertyName(String propName) {
		this.propName = propName;
	}

	public FilterInfo getQuickFilterInfo() {
		return quickFilterInfo;
	}

	public void setFilterCU(String queryName, OrgUnitCollection bizOrgCollection) {
		this.queryName = queryName;
		this.bizOrgCollection = bizOrgCollection;
	}

	public void setFilterCUID(String cuId) {
		this.filterCUId = cuId;
	}

	public void setFilterInfo(FilterInfo defaultFilterInfo,
			FilterInfo quickFilterInfo) {
		this.defaultFilterInfo = defaultFilterInfo;
		this.quickFilterInfo = quickFilterInfo;
	}

	public void setMultiSelect(boolean isMulti) {
		this.isMultiSelect = isMulti;
	}

	public void setQueryPK(IMetaDataPK pk) {
		this.queryPK = pk;
	}

	public void setQuickFilterInfo(FilterInfo quickFilterInfo) {
		this.quickFilterInfo = quickFilterInfo;
	}

	public void setTreeBaseBOSType(String type) {
		this.treeBaseBOSType = type;
	}

	public void valueChanged(TreeSelectionEvent e) {

	}

	protected FilterInfo getDefaultFilterForQuery() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
		return filter;
	}
	

	 public EntityViewInfo getDefaultQuery(){
	        EntityViewInfo evi = new EntityViewInfo();
	        FilterInfo tmpFilterInfo;
	        try {
	            tmpFilterInfo = (FilterInfo)defaultFilterInfo.clone();
	            if(quickFilterInfo != null){
	            	tmpFilterInfo.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
	                tmpFilterInfo.mergeFilter(quickFilterInfo,"and");
	            }
	            tmpFilterInfo.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
	            evi.setFilter(tmpFilterInfo);
	            evi.getSorter().clear();
	            evi.getSorter().addObjectCollection(this.mainQuery.getSorter());
	        } catch (Exception e) {
	            super.handUIException(e);
	        }
	        return evi;
	    }
		
		protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
			if (quickFilterInfo != null) {
				super.mainQuery = getDefaultQuery();
			} else {
				KDTreeNode treeNode = getSelectedTreeNode();
				if (treeNode == null) {
					return;
				}
				if (mainQuery.getFilter() == null) {
					mainQuery.setFilter(new FilterInfo());
				}

				// 移除上一次的过滤字段项
				FilterItemCollection col = removeQueryFilterAndSorter(mainQuery);

				mainQuery.getFilter().remove("maskString");

				if (treeNode.getUserObject() instanceof TreeBaseInfo) {
					col.add(new FilterItemInfo(getLongNumberFieldName(),
							((TreeBaseInfo) treeNode.getUserObject())
									.getLongNumber()));
					col.add(new FilterItemInfo(getLongNumberFieldName(),
							((TreeBaseInfo) treeNode.getUserObject())
									.getLongNumber()
									+ "!%", CompareType.LIKE));

					mainQuery.getFilter().setMaskString("#0 or #1");
				}
				try {
					if (getDefaultFilterForQuery() != null
							&& getDefaultFilterForQuery().size() > 0) {
						if (this.mainQuery.getFilter() != null
								&& this.mainQuery.getFilter().getFilterItems()
										.size() > 0) {

							this.mainQuery.getFilter().mergeFilter(
									getDefaultFilterForQuery(), "AND");
						} else {
							this.mainQuery.setFilter(getDefaultFilterForQuery());
						}
					}
					this.getEntityViewInfo(this.mainQuery);
					if (this.getDefaultEntityViewInfo() != null)
						MergeCommonQuery(this.getDefaultEntityViewInfo());
				} catch (Exception ex) {
					this.handleException(ex);
					this.abort();
				}

				if (getCurrentOrder() != null) {
					mainQuery.getSorter().add(getCurrentOrder());
				} else {
					mainQuery.getSorter().add(
							new SorterItemInfo(getLongNumberFieldName()));
				}
			}
			super.tblMain_doRequestRowSet(e);
		}
}
