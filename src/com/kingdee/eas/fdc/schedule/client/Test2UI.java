/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;

/**
 * output class name
 */
public class Test2UI extends AbstractTest2UI
{
    private static final Logger logger = CoreUIObject.getLogger(Test2UI.class);
    
    /**
     * output class constructor
     */
    public Test2UI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		cbSign.addItem("¡Ì");
		cbSign.addItem("¡ñ");
		cbSign.addItem("¡ö");
		cbSign.addItem("¡ø");
		cbSign.addItem("¡ò");
		cbSign.addItem("¡ô");

		btnAdd.setIcon(EASResource.getIcon("imgTbtn_addmapping"));
		btnDel.setIcon(EASResource.getIcon("imgTbtn_deletemapping"));
		kDContainer1.addButton(btnAdd);
		kDContainer1.addButton(btnDel);
		actionAddRule.setEnabled(true);
		actionDelRule.setEnabled(true);
		
		KDComboBox operator = new KDComboBox();
		operator.addItem("+");
		operator.addItem("-");
		operator.addItem("¡Á");
		operator.addItem("¡Â");
		operator.addItem("=");
		tblMain.checkParsed();
		tblMain.getColumn("column2").setEditor(
				new KDTDefaultCellEditor(operator));
	}

    public void actionAddRule_actionPerformed(ActionEvent e) throws Exception {
		tblMain.addRow();
	}

	public void actionDelRule_actionPerformed(ActionEvent e) throws Exception {
		tblMain.removeRow(KDTableUtil.getSelectedRow(tblMain));
	}
}