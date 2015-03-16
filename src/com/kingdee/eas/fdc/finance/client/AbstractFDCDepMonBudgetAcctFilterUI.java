/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractFDCDepMonBudgetAcctFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractFDCDepMonBudgetAcctFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRespPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEditor;
    protected com.kingdee.bos.ctrl.swing.KDButton btnOK;
    protected com.kingdee.bos.ctrl.swing.KDButton btnNO;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRespPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEditor;
    /**
     * output class constructor
     */
    public AbstractFDCDepMonBudgetAcctFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractFDCDepMonBudgetAcctFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contRespDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRespPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnOK = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnNO = new com.kingdee.bos.ctrl.swing.KDButton();
        this.prmtRespDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtRespPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtEditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRespDept.setName("contRespDept");
        this.contRespPerson.setName("contRespPerson");
        this.contCreator.setName("contCreator");
        this.contEditor.setName("contEditor");
        this.btnOK.setName("btnOK");
        this.btnNO.setName("btnNO");
        this.prmtRespDept.setName("prmtRespDept");
        this.prmtRespPerson.setName("prmtRespPerson");
        this.prmtCreator.setName("prmtCreator");
        this.prmtEditor.setName("prmtEditor");
        // CustomerQueryPanel
        // contRespDept		
        this.contRespDept.setBoundLabelText(resHelper.getString("contRespDept.boundLabelText"));		
        this.contRespDept.setBoundLabelUnderline(true);		
        this.contRespDept.setBoundLabelLength(100);
        // contRespPerson		
        this.contRespPerson.setBoundLabelText(resHelper.getString("contRespPerson.boundLabelText"));		
        this.contRespPerson.setBoundLabelLength(100);		
        this.contRespPerson.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contEditor		
        this.contEditor.setBoundLabelText(resHelper.getString("contEditor.boundLabelText"));		
        this.contEditor.setBoundLabelLength(100);		
        this.contEditor.setBoundLabelUnderline(true);
        // btnOK		
        this.btnOK.setText(resHelper.getString("btnOK.text"));
        this.btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnOK_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnNO		
        this.btnNO.setText(resHelper.getString("btnNO.text"));
        this.btnNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnNO_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // prmtRespDept		
        this.prmtRespDept.setDisplayFormat("$name$");		
        this.prmtRespDept.setEditFormat("$number$");		
        this.prmtRespDept.setCommitFormat("$number$");		
        this.prmtRespDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");
        this.prmtRespDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRespDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtRespPerson		
        this.prmtRespPerson.setDisplayFormat("$name$");		
        this.prmtRespPerson.setEditFormat("$number$");		
        this.prmtRespPerson.setCommitFormat("$number$");		
        this.prmtRespPerson.setDefaultF7UIName("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtRespPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
        this.prmtRespPerson.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRespPerson_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtCreator		
        this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtCreator.setDefaultF7UIName("com.kingdee.eas.base.permission.app.F7UserQuery");
        this.prmtCreator.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCreator_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtEditor		
        this.prmtEditor.setDefaultF7UIName("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtEditor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
        this.prmtEditor.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtEditor_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 400, 200));
        this.setLayout(null);
        contRespDept.setBounds(new Rectangle(53, 23, 270, 19));
        this.add(contRespDept, null);
        contRespPerson.setBounds(new Rectangle(53, 48, 270, 19));
        this.add(contRespPerson, null);
        contCreator.setBounds(new Rectangle(53, 74, 270, 19));
        this.add(contCreator, null);
        contEditor.setBounds(new Rectangle(53, 100, 270, 19));
        this.add(contEditor, null);
        btnOK.setBounds(new Rectangle(178, 141, 73, 21));
        this.add(btnOK, null);
        btnNO.setBounds(new Rectangle(265, 141, 73, 21));
        this.add(btnNO, null);
        //contRespDept
        contRespDept.setBoundEditor(prmtRespDept);
        //contRespPerson
        contRespPerson.setBoundEditor(prmtRespPerson);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contEditor
        contEditor.setBoundEditor(prmtEditor);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.finance.app.FDCDepMonBudgetAcctFilterUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnNO_actionPerformed method
     */
    protected void btnNO_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output prmtRespDept_dataChanged method
     */
    protected void prmtRespDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtRespPerson_dataChanged method
     */
    protected void prmtRespPerson_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCreator_dataChanged method
     */
    protected void prmtCreator_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtEditor_dataChanged method
     */
    protected void prmtEditor_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.finance.client", "FDCDepMonBudgetAcctFilterUI");
    }




}