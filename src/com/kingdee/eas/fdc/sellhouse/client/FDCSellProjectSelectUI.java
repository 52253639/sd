package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public final class FDCSellProjectSelectUI extends ShareProjectListUI {
	public boolean isCanceled = false;

	private Boolean isMultiSelect = Boolean.FALSE;

	public FDCSellProjectSelectUI() throws Exception {
		super();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		if (isMultiSelect.booleanValue()) {
			SellProjectCollection projects = new SellProjectCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) {
				IRow row = tblMain.getRow(i);
				if (row == null) {
					MsgBox.showInfo("请正确选择项目！");
					return;
				}else{
				String id = row.getCell("id").getValue().toString();
				
				SellProjectInfo sellProject = querySellProjectInfo(id);
				projects.add(sellProject);
				}
			}
			if (projects.size() == 0) {
				MsgBox.showInfo("请正确选择项目！");
				return;
			} else {
				this.getUIContext().put("sellProject", projects);
				this.isCanceled = false;
				this.destroyWindow();
			}
		} else {
			String id = "";
			int activeRowIndex = this.tblMain.getSelectManager()
					.getActiveRowIndex();
			IRow row = tblMain.getRow(activeRowIndex);
			if (row == null) {
				MsgBox.showInfo("请正确选择项目！");
				return;
			}else{
				id = row.getCell("id").getValue().toString();
				SellProjectInfo sellProject = querySellProjectInfo(id);
				this.getUIContext().put("sellProject", sellProject);
				this.isCanceled = false;
				this.destroyWindow();
			}
		}
	}

	protected void btnNo_actionPerformed(ActionEvent e)throws Exception {
		isCanceled = true;
		super.btnNo_actionPerformed(e);
	}

	/**
	 * 返回多个项目的值
	 * 
	 * @return
	 * @author youzhen_qin
	 * @throws UuidException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public Object[] getReturnValueArray() throws EASBizException, BOSException,
			UuidException {
		Object obj[] = null;
		if (isMultiSelect.booleanValue()) {
			SellProjectCollection projects = new SellProjectCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) {
				IRow row = tblMain.getRow(i);
				if (row == null) {
					MsgBox.showInfo("请正确选择项目！");
					return null;
				}else{
				String id = row.getCell("id").getValue().toString();
				
				SellProjectInfo sellProject = querySellProjectInfo(id);
				projects.add(sellProject);
				}
			}
			if (projects.size() == 0) {
				MsgBox.showInfo("请正确选择项目！");
				return null;
			} else {
				obj = projects.toArray();
			}
		} else {

		}
		return obj;
	}

	/**
	 * 返回单个项目的值
	 * 
	 * @return
	 * @author youzhen_qin
	 * @throws UuidException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public Object getReturnValue() throws EASBizException, BOSException,
			UuidException {
		Object obj = null;
		if (!this.isMultiSelect.booleanValue()) {
			int activeRowIndex = this.tblMain.getSelectManager()
					.getActiveRowIndex();
			IRow row = tblMain.getRow(activeRowIndex);
			if (row == null) {
				MsgBox.showInfo("请正确选择项目！");
				return null;
			}else{
				String id = "";
				id = row.getCell("id").getValue().toString();
				SellProjectInfo sellProject = querySellProjectInfo(id);
				obj = sellProject;
			}
		} else {

		}
		return obj;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		isMultiSelect = (Boolean) this.getUIContext().get("isMultiSelect");
	}

	public static SellProjectInfo querySellProjectInfo(String id)
			throws EASBizException, BOSException, UuidException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("id");
		sels.add("name");
		sels.add("number");
		sels.add("simpleName");
		sels.add("description");
		SellProjectInfo sellProjectInfo = SellProjectFactory
				.getRemoteInstance().getSellProjectInfo(
						new ObjectUuidPK(BOSUuid.read(id)), sels);
		return sellProjectInfo;
	}
}
