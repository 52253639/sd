/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.market.MediaFactory;
import com.kingdee.eas.fdc.market.MediaTypeFactory;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.fdc.market.QuestionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ProofOfPaymentFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.ProofOfPaymentEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MediaTypeListUI extends AbstractMediaTypeListUI {

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String id = this.getSelectedKeyValue();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("mediaType.id", id));
		if (MediaFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("该媒体分类已被媒体引用，不能删除！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}

	private static final Logger logger = CoreUIObject.getLogger(MediaTypeListUI.class);

	/**
	 * output class constructor
	 */
	public MediaTypeListUI() throws Exception {
		super();
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (orgNode == null) {
			return;
		}
		if (orgNode.getUserObject() != null && orgNode.getUserObject() instanceof MediaTypeInfo) {
			MediaTypeInfo mediaTypeInfo = (MediaTypeInfo) orgNode.getUserObject();
			if (mediaTypeInfo != null) {
				getUIContext().put("mediaType", mediaTypeInfo);
			}
		}
		this.tblMain.removeRows();
		this.execQuery();

	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			viewInfo = new EntityViewInfo();
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();

			String ctrlUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			if (ctrlUnitId != null && ctrlUnitId != "") {
				filter.getFilterItems().add(new FilterItemInfo("CU.id", ctrlUnitId));
			}
			if (orgNode != null && orgNode.getUserObject() instanceof MediaTypeInfo) {
				MediaTypeInfo mediaType = (MediaTypeInfo) orgNode.getUserObject();
				if (mediaType != null) {
					String longNumber = mediaType.getLongNumber();
					filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "%", CompareType.LIKE));
				}
			}
			viewInfo.setFilter(filter);
			viewInfo.getSorter().add(new SorterItemInfo("level"));
			viewInfo.getSorter().add(new SorterItemInfo("name"));
		} catch (Exception e) {
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return MediaTypeFactory.getRemoteInstance();
	}

	protected String getLongNumberFieldName() {
		return "longNumber";
	}

	/**
	 * output getRootName method
	 */
	protected String getRootName() {
		return "媒体分类";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		MediaTypeInfo info = new MediaTypeInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MediaTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MediaTypeEditUI.class.getName();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}
}