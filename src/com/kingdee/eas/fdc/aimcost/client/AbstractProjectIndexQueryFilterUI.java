/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractProjectIndexQueryFilterUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractProjectIndexQueryFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPrjoect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDateFrom;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkWholeProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStage;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStage;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conApportion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApportion;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProjectType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProject;
    /**
     * output class constructor
     */
    public AbstractProjectIndexQueryFilterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractProjectIndexQueryFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPrjoect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDateFrom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkWholeProject = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.prmtProductType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contStage = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboStage = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.conApportion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtApportion = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProjectType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCompany.setName("contCompany");
        this.contPrjoect.setName("contPrjoect");
        this.contDateFrom.setName("contDateFrom");
        this.chkWholeProject.setName("chkWholeProject");
        this.prmtProductType.setName("prmtProductType");
        this.contStage.setName("contStage");
        this.comboStage.setName("comboStage");
        this.conApportion.setName("conApportion");
        this.prmtApportion.setName("prmtApportion");
        this.prmtProjectType.setName("prmtProjectType");
        this.prmtProject.setName("prmtProject");
        // CustomerQueryPanel
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // contPrjoect		
        this.contPrjoect.setBoundLabelText(resHelper.getString("contPrjoect.boundLabelText"));		
        this.contPrjoect.setBoundLabelLength(100);		
        this.contPrjoect.setBoundLabelUnderline(true);
        // contDateFrom		
        this.contDateFrom.setBoundLabelText(resHelper.getString("contDateFrom.boundLabelText"));		
        this.contDateFrom.setBoundLabelLength(100);		
        this.contDateFrom.setBoundLabelUnderline(true);
        // chkWholeProject		
        this.chkWholeProject.setText(resHelper.getString("chkWholeProject.text"));
        this.chkWholeProject.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    chkWholeProject_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProductType		
        this.prmtProductType.setDisplayFormat("$name$");		
        this.prmtProductType.setEditFormat("$number$");		
        this.prmtProductType.setCommitFormat("$number$");		
        this.prmtProductType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");
        // contStage		
        this.contStage.setBoundLabelText(resHelper.getString("contStage.boundLabelText"));		
        this.contStage.setBoundLabelLength(100);		
        this.contStage.setBoundLabelUnderline(true);
        // comboStage		
        this.comboStage.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ProjectStageEnum").toArray());
        // conApportion		
        this.conApportion.setBoundLabelText(resHelper.getString("conApportion.boundLabelText"));		
        this.conApportion.setBoundLabelLength(100);		
        this.conApportion.setBoundLabelUnderline(true);
        // prmtApportion		
        this.prmtApportion.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ApportionTypeQuery");		
        this.prmtApportion.setCommitFormat("$number$");
        // prmtProjectType		
        this.prmtProjectType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectTypeQuery");		
        this.prmtProjectType.setCommitFormat("$name$");		
        this.prmtProjectType.setRequired(true);
        this.prmtProjectType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtProjectType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtProject		
        this.prmtProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");		
        this.prmtProject.setCommitFormat("$name$");		
        this.prmtProject.setRequired(true);
        this.prmtProject.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProject_willShow(e);
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
        contCompany.setBounds(new Rectangle(10, 10, 345, 21));
        this.add(contCompany, null);
        contPrjoect.setBounds(new Rectangle(10, 41, 345, 19));
        this.add(contPrjoect, null);
        contDateFrom.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contDateFrom, null);
        chkWholeProject.setBounds(new Rectangle(287, 70, 99, 19));
        this.add(chkWholeProject, null);
        contStage.setBounds(new Rectangle(10, 130, 345, 19));
        this.add(contStage, null);
        conApportion.setBounds(new Rectangle(10, 99, 345, 19));
        this.add(conApportion, null);
        //contCompany
        contCompany.setBoundEditor(prmtProjectType);
        //contPrjoect
        contPrjoect.setBoundEditor(prmtProject);
        //contDateFrom
        contDateFrom.setBoundEditor(prmtProductType);
        //contStage
        contStage.setBoundEditor(comboStage);
        //conApportion
        conApportion.setBoundEditor(prmtApportion);

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
	    return "com.kingdee.eas.fdc.aimcost.app.ProjectIndexQueryFilterUIHandler";
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
     * output chkWholeProject_stateChanged method
     */
    protected void chkWholeProject_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProjectType_dataChanged method
     */
    protected void prmtProjectType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtProject_willShow method
     */
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }


    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "ProjectIndexQueryFilterUI");
    }




}