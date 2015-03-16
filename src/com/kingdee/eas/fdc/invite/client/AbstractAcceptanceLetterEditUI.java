/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractAcceptanceLetterEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractAcceptanceLetterEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCreateContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSupplier;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contQuantity;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteForm;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLowestPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLowerPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMiddlePrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHigherPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHighestPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLowestPriceUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLowerPriceUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMiddlePriceUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHigherPriceUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contHighestPriceUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSupplier;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteProject;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInviteAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtQuantity;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboInviteForm;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLowestPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLowerPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMiddlePrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHigherPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHighestPrice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLowestPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLowerPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMiddlePriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHigherPriceUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtHighestPriceUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteMode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInviteProjectOrg;
    protected com.kingdee.eas.fdc.invite.AcceptanceLetterInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractAcceptanceLetterEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractAcceptanceLetterEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsCreateContract = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contSupplier = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contQuantity = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteForm = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLowestPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLowerPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMiddlePrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHigherPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHighestPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLowestPriceUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLowerPriceUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMiddlePriceUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHigherPriceUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contHighestPriceUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.conInviteMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtSupplier = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInviteProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInviteAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtQuantity = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboInviteForm = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtLowestPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtLowerPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMiddlePrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtHigherPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtHighestPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtLowestPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtLowerPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMiddlePriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtHigherPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtHighestPriceUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtInviteMode = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInviteProjectOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.chkIsCreateContract.setName("chkIsCreateContract");
        this.contSupplier.setName("contSupplier");
        this.contInviteProject.setName("contInviteProject");
        this.contInviteAmount.setName("contInviteAmount");
        this.contQuantity.setName("contQuantity");
        this.contInviteForm.setName("contInviteForm");
        this.contLowestPrice.setName("contLowestPrice");
        this.contLowerPrice.setName("contLowerPrice");
        this.contMiddlePrice.setName("contMiddlePrice");
        this.contHigherPrice.setName("contHigherPrice");
        this.contHighestPrice.setName("contHighestPrice");
        this.contLowestPriceUnit.setName("contLowestPriceUnit");
        this.contLowerPriceUnit.setName("contLowerPriceUnit");
        this.contMiddlePriceUnit.setName("contMiddlePriceUnit");
        this.contHigherPriceUnit.setName("contHigherPriceUnit");
        this.contHighestPriceUnit.setName("contHighestPriceUnit");
        this.kDLabel1.setName("kDLabel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.conInviteMode.setName("conInviteMode");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.prmtSupplier.setName("prmtSupplier");
        this.prmtInviteProject.setName("prmtInviteProject");
        this.txtInviteAmount.setName("txtInviteAmount");
        this.txtQuantity.setName("txtQuantity");
        this.comboInviteForm.setName("comboInviteForm");
        this.txtLowestPrice.setName("txtLowestPrice");
        this.txtLowerPrice.setName("txtLowerPrice");
        this.txtMiddlePrice.setName("txtMiddlePrice");
        this.txtHigherPrice.setName("txtHigherPrice");
        this.txtHighestPrice.setName("txtHighestPrice");
        this.prmtLowestPriceUnit.setName("prmtLowestPriceUnit");
        this.prmtLowerPriceUnit.setName("prmtLowerPriceUnit");
        this.prmtMiddlePriceUnit.setName("prmtMiddlePriceUnit");
        this.prmtHigherPriceUnit.setName("prmtHigherPriceUnit");
        this.prmtHighestPriceUnit.setName("prmtHighestPriceUnit");
        this.txtDescription.setName("txtDescription");
        this.prmtInviteMode.setName("prmtInviteMode");
        this.txtInviteProjectOrg.setName("txtInviteProjectOrg");
        // CoreUI		
        this.setMinimumSize(new Dimension(650,475));		
        this.setPreferredSize(new Dimension(650,475));
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);		
        this.contAuditor.setEnabled(false);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);		
        this.contAuditTime.setEnabled(false);
        // chkIsCreateContract		
        this.chkIsCreateContract.setText(resHelper.getString("chkIsCreateContract.text"));		
        this.chkIsCreateContract.setEnabled(false);
        // contSupplier		
        this.contSupplier.setBoundLabelText(resHelper.getString("contSupplier.boundLabelText"));		
        this.contSupplier.setBoundLabelLength(100);		
        this.contSupplier.setBoundLabelUnderline(true);
        // contInviteProject		
        this.contInviteProject.setBoundLabelText(resHelper.getString("contInviteProject.boundLabelText"));		
        this.contInviteProject.setBoundLabelLength(100);		
        this.contInviteProject.setBoundLabelUnderline(true);
        // contInviteAmount		
        this.contInviteAmount.setBoundLabelText(resHelper.getString("contInviteAmount.boundLabelText"));		
        this.contInviteAmount.setBoundLabelLength(100);		
        this.contInviteAmount.setBoundLabelUnderline(true);		
        this.contInviteAmount.setEnabled(false);
        // contQuantity		
        this.contQuantity.setBoundLabelText(resHelper.getString("contQuantity.boundLabelText"));		
        this.contQuantity.setBoundLabelLength(100);		
        this.contQuantity.setBoundLabelUnderline(true);		
        this.contQuantity.setEnabled(false);
        // contInviteForm		
        this.contInviteForm.setBoundLabelText(resHelper.getString("contInviteForm.boundLabelText"));		
        this.contInviteForm.setBoundLabelLength(100);		
        this.contInviteForm.setBoundLabelUnderline(true);		
        this.contInviteForm.setEnabled(false);
        // contLowestPrice		
        this.contLowestPrice.setBoundLabelText(resHelper.getString("contLowestPrice.boundLabelText"));		
        this.contLowestPrice.setBoundLabelLength(100);		
        this.contLowestPrice.setBoundLabelUnderline(true);		
        this.contLowestPrice.setEnabled(false);
        // contLowerPrice		
        this.contLowerPrice.setBoundLabelText(resHelper.getString("contLowerPrice.boundLabelText"));		
        this.contLowerPrice.setBoundLabelLength(100);		
        this.contLowerPrice.setBoundLabelUnderline(true);		
        this.contLowerPrice.setEnabled(false);
        // contMiddlePrice		
        this.contMiddlePrice.setBoundLabelText(resHelper.getString("contMiddlePrice.boundLabelText"));		
        this.contMiddlePrice.setBoundLabelLength(100);		
        this.contMiddlePrice.setBoundLabelUnderline(true);		
        this.contMiddlePrice.setEnabled(false);
        // contHigherPrice		
        this.contHigherPrice.setBoundLabelText(resHelper.getString("contHigherPrice.boundLabelText"));		
        this.contHigherPrice.setBoundLabelLength(100);		
        this.contHigherPrice.setBoundLabelUnderline(true);		
        this.contHigherPrice.setEnabled(false);
        // contHighestPrice		
        this.contHighestPrice.setBoundLabelText(resHelper.getString("contHighestPrice.boundLabelText"));		
        this.contHighestPrice.setBoundLabelLength(100);		
        this.contHighestPrice.setBoundLabelUnderline(true);		
        this.contHighestPrice.setEnabled(false);
        // contLowestPriceUnit		
        this.contLowestPriceUnit.setBoundLabelText(resHelper.getString("contLowestPriceUnit.boundLabelText"));		
        this.contLowestPriceUnit.setBoundLabelLength(100);		
        this.contLowestPriceUnit.setBoundLabelUnderline(true);		
        this.contLowestPriceUnit.setEnabled(false);
        // contLowerPriceUnit		
        this.contLowerPriceUnit.setBoundLabelText(resHelper.getString("contLowerPriceUnit.boundLabelText"));		
        this.contLowerPriceUnit.setBoundLabelLength(100);		
        this.contLowerPriceUnit.setBoundLabelUnderline(true);		
        this.contLowerPriceUnit.setEnabled(false);
        // contMiddlePriceUnit		
        this.contMiddlePriceUnit.setBoundLabelText(resHelper.getString("contMiddlePriceUnit.boundLabelText"));		
        this.contMiddlePriceUnit.setBoundLabelLength(100);		
        this.contMiddlePriceUnit.setBoundLabelUnderline(true);		
        this.contMiddlePriceUnit.setEnabled(false);
        // contHigherPriceUnit		
        this.contHigherPriceUnit.setBoundLabelText(resHelper.getString("contHigherPriceUnit.boundLabelText"));		
        this.contHigherPriceUnit.setBoundLabelLength(100);		
        this.contHigherPriceUnit.setBoundLabelUnderline(true);		
        this.contHigherPriceUnit.setEnabled(false);
        // contHighestPriceUnit		
        this.contHighestPriceUnit.setBoundLabelText(resHelper.getString("contHighestPriceUnit.boundLabelText"));		
        this.contHighestPriceUnit.setBoundLabelLength(100);		
        this.contHighestPriceUnit.setBoundLabelUnderline(true);		
        this.contHighestPriceUnit.setEnabled(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDScrollPane1
        // conInviteMode		
        this.conInviteMode.setBoundLabelText(resHelper.getString("conInviteMode.boundLabelText"));		
        this.conInviteMode.setBoundLabelUnderline(true);		
        this.conInviteMode.setBoundLabelLength(100);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setDisplayFormat("$name$");		
        this.prmtCreator.setEditFormat("$number$");		
        this.prmtCreator.setCommitFormat("$number$");
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$number$");		
        this.prmtAuditor.setCommitFormat("$number$");
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // prmtSupplier		
        this.prmtSupplier.setRequired(true);		
        this.prmtSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
        // prmtInviteProject
        // txtInviteAmount		
        this.txtInviteAmount.setEnabled(false);
        // txtQuantity		
        this.txtQuantity.setEnabled(false);
        // comboInviteForm		
        this.comboInviteForm.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.InviteFormEnum").toArray());		
        this.comboInviteForm.setEnabled(false);
        // txtLowestPrice		
        this.txtLowestPrice.setEnabled(false);
        // txtLowerPrice		
        this.txtLowerPrice.setEnabled(false);
        // txtMiddlePrice		
        this.txtMiddlePrice.setEnabled(false);
        // txtHigherPrice		
        this.txtHigherPrice.setEnabled(false);
        // txtHighestPrice		
        this.txtHighestPrice.setEnabled(false);
        // prmtLowestPriceUnit		
        this.prmtLowestPriceUnit.setEnabled(false);
        // prmtLowerPriceUnit		
        this.prmtLowerPriceUnit.setEnabled(false);
        // prmtMiddlePriceUnit		
        this.prmtMiddlePriceUnit.setEnabled(false);
        // prmtHigherPriceUnit		
        this.prmtHigherPriceUnit.setEnabled(false);
        // prmtHighestPriceUnit		
        this.prmtHighestPriceUnit.setEnabled(false);
        // txtDescription
        // prmtInviteMode		
        this.prmtInviteMode.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteModeQuery");		
        this.prmtInviteMode.setDisplayFormat("$name$");		
        this.prmtInviteMode.setEditFormat("$number$");		
        this.prmtInviteMode.setCommitFormat("$number$");		
        this.prmtInviteMode.setEnabled(false);
        // txtInviteProjectOrg		
        this.txtInviteProjectOrg.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 650, 505));
        this.setLayout(null);
        kDSeparator6.setBounds(new Rectangle(10, 408, 625, 10));
        this.add(kDSeparator6, null);
        contCreator.setBounds(new Rectangle(10, 423, 270, 19));
        this.add(contCreator, null);
        contCreateTime.setBounds(new Rectangle(10, 453, 270, 19));
        this.add(contCreateTime, null);
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, null);
        contAuditor.setBounds(new Rectangle(365, 425, 270, 19));
        this.add(contAuditor, null);
        contAuditTime.setBounds(new Rectangle(365, 455, 270, 19));
        this.add(contAuditTime, null);
        chkIsCreateContract.setBounds(new Rectangle(365, 119, 140, 19));
        this.add(chkIsCreateContract, null);
        contSupplier.setBounds(new Rectangle(10, 38, 270, 19));
        this.add(contSupplier, null);
        contInviteProject.setBounds(new Rectangle(365, 10, 270, 19));
        this.add(contInviteProject, null);
        contInviteAmount.setBounds(new Rectangle(365, 38, 270, 19));
        this.add(contInviteAmount, null);
        contQuantity.setBounds(new Rectangle(365, 68, 270, 19));
        this.add(contQuantity, null);
        contInviteForm.setBounds(new Rectangle(10, 68, 270, 19));
        this.add(contInviteForm, null);
        contLowestPrice.setBounds(new Rectangle(10, 373, 270, 19));
        this.add(contLowestPrice, null);
        contLowerPrice.setBounds(new Rectangle(10, 343, 270, 19));
        this.add(contLowerPrice, null);
        contMiddlePrice.setBounds(new Rectangle(10, 313, 270, 19));
        this.add(contMiddlePrice, null);
        contHigherPrice.setBounds(new Rectangle(10, 283, 270, 19));
        this.add(contHigherPrice, null);
        contHighestPrice.setBounds(new Rectangle(10, 253, 270, 19));
        this.add(contHighestPrice, null);
        contLowestPriceUnit.setBounds(new Rectangle(365, 374, 270, 19));
        this.add(contLowestPriceUnit, null);
        contLowerPriceUnit.setBounds(new Rectangle(365, 344, 270, 19));
        this.add(contLowerPriceUnit, null);
        contMiddlePriceUnit.setBounds(new Rectangle(365, 314, 270, 19));
        this.add(contMiddlePriceUnit, null);
        contHigherPriceUnit.setBounds(new Rectangle(365, 284, 270, 19));
        this.add(contHigherPriceUnit, null);
        contHighestPriceUnit.setBounds(new Rectangle(365, 254, 270, 19));
        this.add(contHighestPriceUnit, null);
        kDLabel1.setBounds(new Rectangle(10, 119, 100, 19));
        this.add(kDLabel1, null);
        kDScrollPane1.setBounds(new Rectangle(10, 149, 625, 84));
        this.add(kDScrollPane1, null);
        conInviteMode.setBounds(new Rectangle(10, 96, 270, 19));
        this.add(conInviteMode, null);
        kDLabelContainer1.setBounds(new Rectangle(366, 96, 270, 19));
        this.add(kDLabelContainer1, null);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contSupplier
        contSupplier.setBoundEditor(prmtSupplier);
        //contInviteProject
        contInviteProject.setBoundEditor(prmtInviteProject);
        //contInviteAmount
        contInviteAmount.setBoundEditor(txtInviteAmount);
        //contQuantity
        contQuantity.setBoundEditor(txtQuantity);
        //contInviteForm
        contInviteForm.setBoundEditor(comboInviteForm);
        //contLowestPrice
        contLowestPrice.setBoundEditor(txtLowestPrice);
        //contLowerPrice
        contLowerPrice.setBoundEditor(txtLowerPrice);
        //contMiddlePrice
        contMiddlePrice.setBoundEditor(txtMiddlePrice);
        //contHigherPrice
        contHigherPrice.setBoundEditor(txtHigherPrice);
        //contHighestPrice
        contHighestPrice.setBoundEditor(txtHighestPrice);
        //contLowestPriceUnit
        contLowestPriceUnit.setBoundEditor(prmtLowestPriceUnit);
        //contLowerPriceUnit
        contLowerPriceUnit.setBoundEditor(prmtLowerPriceUnit);
        //contMiddlePriceUnit
        contMiddlePriceUnit.setBoundEditor(prmtMiddlePriceUnit);
        //contHigherPriceUnit
        contHigherPriceUnit.setBoundEditor(prmtHigherPriceUnit);
        //contHighestPriceUnit
        contHighestPriceUnit.setBoundEditor(prmtHighestPriceUnit);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //conInviteMode
        conInviteMode.setBoundEditor(prmtInviteMode);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtInviteProjectOrg);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isCreateContract", boolean.class, this.chkIsCreateContract, "selected");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtSupplier, "data");
		dataBinder.registerBinding("inviteProject", com.kingdee.eas.fdc.invite.InviteProjectInfo.class, this.prmtInviteProject, "data");
		dataBinder.registerBinding("inviteAmount", java.math.BigDecimal.class, this.txtInviteAmount, "value");
		dataBinder.registerBinding("quantity", java.math.BigDecimal.class, this.txtQuantity, "value");
		dataBinder.registerBinding("inviteForm", com.kingdee.eas.fdc.invite.InviteFormEnum.class, this.comboInviteForm, "selectedItem");
		dataBinder.registerBinding("lowestPrice", java.math.BigDecimal.class, this.txtLowestPrice, "value");
		dataBinder.registerBinding("lowerPrice", java.math.BigDecimal.class, this.txtLowerPrice, "value");
		dataBinder.registerBinding("middlePrice", java.math.BigDecimal.class, this.txtMiddlePrice, "value");
		dataBinder.registerBinding("higherPrice", java.math.BigDecimal.class, this.txtHigherPrice, "value");
		dataBinder.registerBinding("highestPrice", java.math.BigDecimal.class, this.txtHighestPrice, "value");
		dataBinder.registerBinding("lowestPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtLowestPriceUnit, "data");
		dataBinder.registerBinding("lowerPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtLowerPriceUnit, "data");
		dataBinder.registerBinding("middlePriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtMiddlePriceUnit, "data");
		dataBinder.registerBinding("higherPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtHigherPriceUnit, "data");
		dataBinder.registerBinding("highestPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtHighestPriceUnit, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("inviteProject.inviteMode", com.kingdee.eas.fdc.invite.InviteModeInfo.class, this.prmtInviteMode, "data");
		dataBinder.registerBinding("inviteProject.orgUnit.name", String.class, this.txtInviteProjectOrg, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.AcceptanceLetterEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.AcceptanceLetterInfo)ov;
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
		getValidateHelper().registerBindProperty("isCreateContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("quantity", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteForm", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowestPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lowerPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("middlePriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("higherPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("highestPriceUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.inviteMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("inviteProject.orgUnit.name", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isCreateContract"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("supplier.*"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("inviteAmount"));
        sic.add(new SelectorItemInfo("quantity"));
        sic.add(new SelectorItemInfo("inviteForm"));
        sic.add(new SelectorItemInfo("lowestPrice"));
        sic.add(new SelectorItemInfo("lowerPrice"));
        sic.add(new SelectorItemInfo("middlePrice"));
        sic.add(new SelectorItemInfo("higherPrice"));
        sic.add(new SelectorItemInfo("highestPrice"));
        sic.add(new SelectorItemInfo("lowestPriceUnit.*"));
        sic.add(new SelectorItemInfo("lowerPriceUnit.*"));
        sic.add(new SelectorItemInfo("middlePriceUnit.*"));
        sic.add(new SelectorItemInfo("higherPriceUnit.*"));
        sic.add(new SelectorItemInfo("highestPriceUnit.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("inviteProject.inviteMode"));
        sic.add(new SelectorItemInfo("inviteProject.orgUnit.name"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "AcceptanceLetterEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}