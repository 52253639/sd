package com.kingdee.eas.fdc.sellhouse.client;


import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.bo.BusinessObjectInfo;
import com.kingdee.bos.service.formula.api.FormulaGrammarVerifier;
import com.kingdee.bos.service.formula.api.FormulaVarInfoParser;
import com.kingdee.bos.service.formula.api.IVarInfo;
import com.kingdee.bos.service.formula.builder.FormulaBuilderPanelBean;
import com.kingdee.bos.service.formula.builder.TableModelVar;
import com.kingdee.bos.service.formula.builder.TableVarInfo;
import com.kingdee.eas.base.forewarn.DataType;
import com.kingdee.eas.base.forewarn.ForewarnConstant;
import com.kingdee.eas.base.forewarn.client.FormulaDialog;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class SHEFormulaDialog extends KDDialog {

	private static final String FORMULA_RESULT = "formula_result";

	public SHEFormulaDialog(Dialog owner,String title,boolean modal)
	{
		super(owner,title,modal);
	}


    public SHEFormulaDialog(Frame owner,String title,boolean modal)
    {
        super(owner,title,modal);
    }

	
	/*public PPMFormulaDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	public PPMFormulaDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}
	
	public KDTable getVarsTable(){
		return this.getTable();
	}
	
	public void initVarsTable(BusinessObjectInfo[] objs){
		if(objs == null) return;
		
		KDTable table = this.getTable();
		Object obj = this.getTable().getParent();
		FormulaBuilderPanelBean panel = (FormulaBuilderPanelBean)obj;
		if( ! (obj instanceof FormulaBuilderPanelBean)){
			return ;
		}
		table.checkParsed();
		for (int i = 0; i < objs.length; i++) {
			TableVarInfo newVar = new TableVarInfo("var1", "", "", "",IVarInfo.VAR_SCOPE_IN, "", TableVarInfo.BY_BUTTONACTION);
//			modelVars.addVar(newVar);
			// modelVars.fireTableDataChanged();
//			modelVars.fireDataChanged();
			IRow row = table.addRow();
			row.getCell(0).setValue(objs[i].getName());
			row.getCell(1).setValue(objs[i].getAlias());
			row.getCell(2).setValue(objs[i].getFullName());
			row.getCell(3).setValue("");
			row.getCell(4).setValue(IVarInfo.VAR_SCOPE_IN);
			row.getCell(4).setValue("");
			table.setRowCount(table.getRowCount()+1);
		}
	}*/
	
	private FormulaBuilderPanelBean panel;
    private final static int CONFIRM_ACTION = 1;
    private final static int CANCEL_ACTION = 0;
    private int closeType = CANCEL_ACTION;
  
    private final static String forewarnRes = 		"com.kingdee.eas.base.forewarn.client.Forewarn";

    protected void dialogInit()
    {
        super.dialogInit();
//		this.setSize(792,539);
//        this.setSize(1024,768);
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)(d.width*0.98) , (int)(d.height*0.95));
        
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout());
		panel = new FormulaBuilderPanelBean(false,true);
//        panel = new FormulaBuilderPanelBean();
		panel.setAlwaysRefOldVar(true);
//		panel.setSize((int)(d.height*0.95),0.35);
		KDButton confirmButton = new KDButton();
		KDButton cancelButton = new KDButton();
		confirmButton.setText(EASResource.getString(forewarnRes,"confirm"));
		confirmButton.addActionListener(new ActionListener()
		{
				public void actionPerformed(ActionEvent e)
				{
					confirmButton_actionPerformed(e);
				}
		});
		cancelButton.setText(EASResource.getString(forewarnRes,"cancel"));
		cancelButton.addActionListener(new ActionListener()
		{
				public void actionPerformed(ActionEvent e)
				{
					cancelButton_actionPerformed(e);
				}
		});
		KDSeparator separator = new KDSeparator();
		
		KDPanel dialogPanel = new KDPanel();
		this.getContentPane().add(dialogPanel, BorderLayout.CENTER);
		dialogPanel.setLayout(new KDLayout());
		dialogPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 792, 539));
		dialogPanel.add(panel, new KDLayout.Constraints(10, 10, 792 - 10 - 10, 539 - 10 - 10 - 21 -20, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
		dialogPanel.add(separator, new KDLayout.Constraints(0, 539 - 10 - 21 - 9 - 2, 792, 2, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
		dialogPanel.add(cancelButton, new KDLayout.Constraints(792 - 10 - 73, 539 - 10 - 21, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
		dialogPanel.add(confirmButton, new KDLayout.Constraints(792 - 10 - 73 - 3 - 79, 539 - 10 - 21, 73, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_RIGHT));
		this.getRootPane().setDefaultButton(confirmButton);
    }
    
	public void show()
	{
		setTableProperties();
		super.show();
	}

	protected void confirmButton_actionPerformed(ActionEvent e)
    {
    	String script = panel.getFormulaStr();
		IVarInfo[] varInfos = getVarInfos(script);
    	if(checkVars(varInfos) 
    		&& checkExpression(script) 
    		&& hasReturn(script) 
    		&& !checkSameVarAlias(varInfos))
    	{
			closeType = CONFIRM_ACTION;
			this.dispose();
    	}
    }
    
    private void setTableProperties()
    {
    	KDTable table = getTable();
    	int rowCount = table.getRowCount();
		table.getColumn(3).getStyleAttributes().setHided(true);
    	for(int i = 0; i < rowCount; i++)
    	{
    		String type = table.getRow(i).getCell(4).getValue().toString();
    		if(type.equalsIgnoreCase(EASResource.getString(forewarnRes, "varReturn")))
    		{
				table.getRow(i).getStyleAttributes().setLocked(true);
    		}
			else if(type.equalsIgnoreCase(EASResource.getString(forewarnRes, "varLocal")))
			{
				String dataType = table.getRow(i).getCell(2).getValue().toString();
				if(!isSimpleVar(dataType))
				{
					table.getRow(i).getStyleAttributes().setLocked(true);					
				}
			}
			else if(type.equalsIgnoreCase(EASResource.getString(forewarnRes, "varIn")))
			{
//				table.getRow(i).getStyleAttributes().setLocked(true);
			}
    	}
		setTableWidth();
    }
    
    private void setTableWidth()
    {
		KDTable table = getTable();
		int colSize = table.getColumnCount();
		int width = (792 - 10 - 10 - 30) / 7; 
		table.getColumn(0).setWidth(width);
		table.getColumn(1).setWidth(width);
		table.getColumn(2).setWidth(width * 2 - 20);
		table.getColumn(3).setWidth(0);
		table.getColumn(4).setWidth(width - 40);
		table.getColumn(5).setWidth(width * 2);
    }
    
    private boolean isSimpleVar(String dataType)
    {
		if(DataType.getEnum(dataType) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
    }

    protected void cancelButton_actionPerformed(ActionEvent e)
    {
        closeType = CANCEL_ACTION;
        this.dispose();
    }

    public String getFormula()
    {
        return panel.getFormulaStr();
    }
    
	public String getFormulaAlias()
	{
		return panel.getFormulaAliasStr();
	}

	public String getFormulaAliasNoResultStr()
	{
		return getMsgInfoAlias(panel.getFormulaAliasStr());
	}

    public IVarInfo[] getVarInfos(String script)
    {
        return new FormulaVarInfoParser().getFormulaVars(script);
    }

    public boolean isConfirm()
    {
		return this.closeType == CONFIRM_ACTION;			
    }

    public void setFormula(String formula)
    {
        panel.setFormulaStr(formula);
    }

    public void setMetaDataObjs(BusinessObjectInfo[] metaObjs)
    {
		IMetaDataLoader dataLoader = 
			MetaDataLoaderFactory.getRemoteMetaDataLoader();
        panel.setMetaDataObjs(metaObjs,dataLoader);
    }
    
    public KDPanel getPanel(){
    	return this.panel;
    }
    
    public void fillVarsTable(BusinessObjectInfo[] objs){
        if(objs == null) return;
		TableModelVar tmv = this.panel.getVarTableModel();
		
		TableVarInfo newVar = null;
		for (int i = 0; i < objs.length; i++) {
			newVar = new TableVarInfo(objs[i].getName(), objs[i].getAlias(), objs[i].getFullName(),"",IVarInfo.VAR_SCOPE_LOCAL, "", TableVarInfo.BY_BUTTONACTION);
			tmv.addVar(newVar);
		}
    	this.panel.getVarTableModel().fireDataChanged();
    }
    /**
     * Ìí¼Ó±äÁ¿
     * @param objs
     * @param varInfo
     */
    public void addVarsTable(List objs,BusinessObjectInfo[] bizObjs,IVarInfo[] varInfo){
    	TableModelVar tmv = this.panel.getVarTableModel();
    	TableVarInfo newVar = null;
    	if(varInfo !=null){
    		for(int j = 0; j < varInfo.length; j++){
//        		tmv.addVar((TableVarInfo)varInfo[j]);
        		newVar = new TableVarInfo(varInfo[j].getVarName(), varInfo[j].getVarAlias(), varInfo[j].getVarType(), varInfo[j].getVarInitval(),varInfo[j].getVarScope(),varInfo[j].getVarDesc(), TableVarInfo.BY_BUTTONACTION);
    			tmv.addVar(newVar);
        		
        	}
    	}else{
    		if(objs != null){
    			for (int i = 0; i < objs.size(); i++) {
        			String[] name = (String[])objs.get(i);
        			newVar = new TableVarInfo(name[0], name[1], name[2],"",IVarInfo.VAR_SCOPE_LOCAL, "", TableVarInfo.BY_BUTTONACTION);
        			tmv.addVar(newVar);
        		}
    		}
    		if(bizObjs != null){
    			for (int i = 0; i < bizObjs.length; i++) {
    				newVar = new TableVarInfo(bizObjs[i].getName(), bizObjs[i].getAlias(), bizObjs[i].getFullName(),"",IVarInfo.VAR_SCOPE_LOCAL, "", TableVarInfo.BY_BUTTONACTION);
    				tmv.addVar(newVar);
    			}
    		}
    	}
    	
    	this.panel.getVarTableModel().fireDataChanged();
    }
    
    public void fillVarsTable(String[][] objs){
        if(objs == null) return;
		TableModelVar tmv = this.panel.getVarTableModel();
		
		TableVarInfo newVar = null;
		for (int i = 0; i < objs.length; i++) {
			if(FORMULA_RESULT.equals(objs[i][0])){
				continue;
			}
			newVar = new TableVarInfo(objs[i][0], objs[i][1], objs[i][2],"",objs[i][3], "", TableVarInfo.BY_METASELECT);
			tmv.addVar(newVar);
		}
    	this.panel.getVarTableModel().fireDataChanged();
    }
    
	protected KDTable getTable()
    {
		return (KDTable)this.panel.getVarTableComponents()[0];
    }
    
	protected JButton getAddButton()
    {
		return (JButton)this.panel.getVarTableComponents()[1];
    }
    
	protected JButton getDelButton()
    {
		return (JButton)this.panel.getVarTableComponents()[2];
    }
    
	private boolean checkVars(IVarInfo[] varInfos)
	{
		if(varInfos != null)
		{
			int size = varInfos.length;
			for(int i=0;i < size;i++)
			{
				String varScope = varInfos[i].getVarScope();
				String varAlias = varInfos[i].getVarAlias();
				if(varScope.toLowerCase().equalsIgnoreCase(ForewarnConstant.IN))
				{
					DataType dataType = DataType.getEnum(varInfos[i].getVarType());
					if(dataType == null)
					{
						MsgBox.showWarning(
							this, 
							EASResource.getString(forewarnRes,"dataTypeWarning") + varInfos[i].getVarType());
						return false;							
					}
				}
				if(varAlias == null || varAlias.trim().equalsIgnoreCase(""))
				{
					MsgBox.showWarning(
						this, 
						EASResource.getString(forewarnRes,"varAliasIsNull"));
					return false;							
				}
			}
		}
		return true;
	}
	
	private boolean hasReturn(String script)
	{
		FormulaGrammarVerifier verifier = new FormulaGrammarVerifier();
		if(!verifier.hasSetFormulaResult(script))
		{
			MsgBox.showWarning(
				this, 
				EASResource.getString(forewarnRes,"returnFormulaResultWarning"));
			return false;		
		}
		return true;
	}
	
	private boolean checkExpression(String script)
	{
		FormulaGrammarVerifier verifier = new FormulaGrammarVerifier();
		boolean flag = verifier.isCompatibleWithBOSFormula(script);
		if(!flag)
		{
			MsgBox.showWarning(
				this, 
				EASResource.getString(forewarnRes,"formulaIsIllegal"));
			return false;				
		}
		
		return flag;
	}
	
	private boolean checkSameVarAlias(IVarInfo[] varInfos)
	{
		if(varInfos != null)
		{
			int size = varInfos.length;
			Set setAlias = new HashSet();
			for(int i=0;i < size;i++)
			{
				String varAlias = varInfos[i].getVarAlias();
				if(!setAlias.contains(varAlias))
				{
					setAlias.add(varAlias);
				}
				else
				{
					MsgBox.showWarning(
						this, 
						EASResource.getString(forewarnRes,"varAliasIsDuplicate"));
					return true;
				}
			}
		}
		return false;
	}
	
	protected String getMsgInfoAlias(String alias)
	{
		String formulaResult = 
			EASResource.getString(forewarnRes, "formulaResult");
		int index = alias.indexOf(formulaResult);
		if(index == 0)
		{
			int length = formulaResult.length();
			alias = alias.substring(length);
		}

		return alias;
	}
	
	
}
