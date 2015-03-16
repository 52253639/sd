package com.kingdee.eas.fdc.invite.client;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.ContractTypeF7UI;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectListUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingEditUI;
import com.kingdee.eas.util.client.ExceptionHandler;

public class F7ProjectSelector implements KDPromptSelector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -252259816390366387L;
	
	protected IUIWindow f7UI;
	protected CoreUIObject ui;
	protected boolean noOrgIsolation;
	
	public F7ProjectSelector(CoreUIObject ui) throws Exception {
		super();
		this.ui = ui;
	}
	public F7ProjectSelector(CoreUIObject ui,boolean noOrgIsolation) throws Exception {
		super();
		this.ui = ui;
		this.noOrgIsolation=noOrgIsolation;
	}
	public void show() {
		
		UIContext context = new UIContext(ui);
		IUIFactory uiFactory = null;
		context.put("noOrgIsolation", Boolean.valueOf(noOrgIsolation));
        try {
            uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
            f7UI = uiFactory.create(ProjectF7UI.class.getName(), context, null, OprtState.VIEW);
            f7UI.show();
            
        } catch (BOSException ex) {
           ExceptionHandler.handle(ui, ex);
        }

	}

	public boolean isCanceled() {
		ProjectF7UI f7UIObject = (ProjectF7UI) f7UI.getUIObject();

        return f7UIObject.isCancel();
	}

	public Object getData() {

		try {
			ProjectF7UI f7UIObject = (ProjectF7UI) f7UI.getUIObject();
			
			return f7UIObject.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  null;
	}

}
