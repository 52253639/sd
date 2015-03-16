/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.market.MediaTypeFactory;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.fdc.market.QuestionTypeInfo;
import com.kingdee.eas.fdc.market.StoreSubjectClassFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MediaTypeEditUI extends AbstractMediaTypeEditUI {

	private static final Logger logger = CoreUIObject.getLogger(MediaTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public MediaTypeEditUI() throws Exception {
		super();
	}

	private void checkNullAndDump() throws Exception {
		MediaTypeInfo info = (MediaTypeInfo) this.editData;
		if (info == null) {
			SysUtil.abort();
		}
		MediaTypeInfo parentInfo = info.getParent();
		String number = this.txtNumber.getText().trim();
		if (number == null ||("").equals( number) ) {
			MsgBox.showInfo("编码不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (parentInfo != null && parentInfo.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
			}
			if (MediaTypeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下编码不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("number", number));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
				}
				if (MediaTypeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下编码不能重复！");
					SysUtil.abort();
				}
			}

		}
		String name = this.txtName.getText().toString().trim();
		if (name == null || ("").equals(name)) {
			MsgBox.showInfo("名称不能为空！");
			SysUtil.abort();
		}
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("name", name));
			if (parentInfo != null && parentInfo.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
			}
			if (MediaTypeFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("相同级次下名称不能重复！");
				SysUtil.abort();
			}
		} else if (this.oprtState.equals(OprtState.EDIT)) {
			if (info != null && info.getId() != null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("name", name));
				if (parentInfo != null && parentInfo.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("parent.id", parentInfo.getId().toString()));
				}
				if (MediaTypeFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo("相同级次下名称不能重复！");
					SysUtil.abort();
				}
			}

		}

	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		checkNullAndDump();
		super.actionSave_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkNullAndDump();
		super.actionSubmit_actionPerformed(e);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUIStatus();
	}
	/**
	 * @author tim_gao
	 * @description 初始状态
	 * @date 2010-11-2
	 */
	protected void initUIStatus(){
		this.setSize(300, 270);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCopy.setVisible(false);
		this.txtName.setRequired(true);
		
		this.txtNumber.setRequired(true);
		
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ICoreBase getBizInterface() throws Exception {

		return MediaTypeFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add(new SelectorItemInfo("longNumber"));
		selectors.add(new SelectorItemInfo("parent.*"));
		return selectors;
	}

	/**
	 * output setDataObject method
	 */
	public void setDataObject(IObjectValue dataObject) {
		if (STATUS_ADDNEW.equals(getOprtState())) {
			dataObject.put("parent", getUIContext().get(UIContext.PARENTNODE));
		}
		super.setDataObject(dataObject);
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		MediaTypeInfo objectValue = new MediaTypeInfo();
		MediaTypeInfo mediaTypeInfo = (MediaTypeInfo) this.getUIContext().get("mediaType");
		if (mediaTypeInfo != null) {
			objectValue.setParent(mediaTypeInfo);
		}
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return objectValue;
	}
}