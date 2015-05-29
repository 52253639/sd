package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 责任部门数据改变Lisenter，用于校验只能输入最明细节点
 * @author liupd
 *
 */
public class RespDeptDataChangeLisenter implements DataChangeListener {

	private CoreUIObject ui;
	private boolean isMustSelectLeaf;
	
	public RespDeptDataChangeLisenter(CoreUIObject ui) {
		super();
		this.setUi(ui);
	}

	 public RespDeptDataChangeLisenter(CoreUIObject ui, boolean isMustSelectLeaf)
	  {
	    setUi(ui);
	    this.isMustSelectLeaf = isMustSelectLeaf;
	  }
	 
	public void dataChanged(DataChangeEvent eventObj) {
		KDBizPromptBox box = (KDBizPromptBox)eventObj.getSource();
	    if ((box.getData() instanceof TreeBaseInfo))
	    {
	      TreeBaseInfo info = (TreeBaseInfo)box.getData();

	      if (info != null) {
	        SelectorItemCollection selectors = new SelectorItemCollection();
	        selectors.add("id");
	        selectors.add("number");
	        selectors.add("longNumber");
	        selectors.add("name");
	        selectors.add("isLeaf");
	        try {
	          info = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(info.getId()), selectors);
	        } catch (Exception e) {
	          ExceptionHandler.handle(e);
	        }
	        if ((this.isMustSelectLeaf) && (!info.isIsLeaf())) {
	          box.setUserObject(null);
	          box.setText(null);
	          MsgBox.showWarning(getUi(), FDCClientUtils.getRes("selectLeaf"));
	        }
	      }
	    }
		
	}


	private void setUi(CoreUIObject ui) {
		this.ui = ui;
	}


	private CoreUIObject getUi() {
		return ui;
	}

}
