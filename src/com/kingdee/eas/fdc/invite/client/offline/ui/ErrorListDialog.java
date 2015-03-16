package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;

public class ErrorListDialog extends AbstractErrorListDialog {
	public ErrorListDialog(JFrame frame) {
		super(frame);
		// TODO 自动生成构造函数存根
		
	}
	public void setErrorList(List errList){
		table.removeRows();
		for(Iterator it=errList.iterator();it.hasNext();){
			HashMap errMap = (HashMap)it.next();
			IRow row = table.addRow();
			row.getCell("errormsg").setValue(errMap.get("errInfo"));
			KDTableHelper.autoFitRowHeight(table,row.getRowIndex(),5);
		}
	}
	public void OK() {
		// TODO 自动生成方法存根
		this.dispose();
	}

}
