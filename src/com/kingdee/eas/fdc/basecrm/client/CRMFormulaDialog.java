package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Dialog;

import javax.swing.JButton;

import com.kingdee.eas.base.forewarn.client.FormulaDialog;

public class CRMFormulaDialog extends FormulaDialog {

	public CRMFormulaDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	
	public JButton getAddButton() {
		return super.getAddButton();
	}
	
	
	public JButton getDelButton() {
		return super.getDelButton();
	}
	

	
	
	
}
