package org.idempiere.component;


import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;
import org.compiere.modelWMS.MDocType;
import org.compiere.util.Env;
import org.eevolution.model.MWMArea;
import org.eevolution.model.MWMAreaType;
import org.eevolution.model.MWMDefinition;
import org.eevolution.model.MWMInOutBound;
import org.eevolution.model.MWMInOutBoundLine;
import org.eevolution.model.MWMInOutBoundLineMA;
import org.eevolution.model.MWMRule;
import org.eevolution.model.MWMSection;
import org.eevolution.model.MWMSectionDetail;
import org.eevolution.model.MWMSectionType;
import org.eevolution.model.MWMStrategy;
import org.eevolution.model.MWMStrategyDetail;
import org.idempiere.model.*;

public class WMS_ModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		 if (tableName.equals(MWMArea.Table_Name)) {
		     return MWMArea.class;
		     
		   } else if (tableName.equals(MWMAreaType.Table_Name)) {
				 return MWMAreaType.class;
				     
		   } else if (tableName.equals(MWMDefinition.Table_Name)) {
				 return MWMDefinition.class;
				     
		   } else if (tableName.equals(MWMInOutBound.Table_Name)) {
			     return MWMInOutBound.class;
			     
		   } else if (tableName.equals(MWMInOutBoundLine.Table_Name)) {
			     return MWMInOutBoundLine.class;
			     
		   } else if (tableName.equals(MWMInOutBoundLineMA.Table_Name)) {
			     return MWMInOutBoundLineMA.class;
			     
		   } else if (tableName.equals(MWMRule.Table_Name)) {
			     return MWMRule.class;
			     
		   } else if (tableName.equals(MWMSection.Table_Name)) {
			     return MWMSection.class;

		   } else if (tableName.equals(MWMSectionDetail.Table_Name)) {
			     return MWMSectionDetail.class;

		   } else if (tableName.equals(MWMSectionType.Table_Name)) {
			     return MWMSectionType.class;
			     
		   } else if (tableName.equals(MWMStrategy.Table_Name)) {
			     return MWMStrategy.class;
			     
		   } else if (tableName.equals(MWMStrategyDetail.Table_Name)) {
			     return MWMStrategyDetail.class;

		   } else if (tableName.equals(MDDOrderLine.Table_Name)) {
			     return MDDOrderLine.class;

		   } else if (tableName.equals(MDocType.Table_Name)) {
			     return MDocType.class;

		   } else 	   
			   return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		 if (tableName.equals(MWMArea.Table_Name)) {
		     return new MWMArea(Env.getCtx(), Record_ID, trxName);
		     
		   } else if (tableName.equals(MWMAreaType.Table_Name)) {
				 return new MWMAreaType(Env.getCtx(), Record_ID, trxName);
				     
		   } else if (tableName.equals(MWMDefinition.Table_Name)) {
				 return new MWMDefinition(Env.getCtx(), Record_ID, trxName);
				     
		   } else if (tableName.equals(MWMInOutBound.Table_Name)) {
			     return new MWMInOutBound(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MWMInOutBoundLine.Table_Name)) {
			     return new MWMInOutBoundLine(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MWMInOutBoundLineMA.Table_Name)) {
			     return new MWMInOutBoundLineMA(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MWMRule.Table_Name)) {
			     return new MWMRule(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MWMSection.Table_Name)) {
			     return new MWMSection(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MWMSectionDetail.Table_Name)) {
			     return new MWMSectionDetail(Env.getCtx(), Record_ID, trxName);

		   } else if (tableName.equals(MWMSectionType.Table_Name)) {
			     return new MWMSectionType(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MWMStrategy.Table_Name)) {
			     return new MWMStrategy(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MWMStrategyDetail.Table_Name)) {
			     return new MWMStrategyDetail(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MDDOrderLine.Table_Name)) {
			     return new MDDOrderLine(Env.getCtx(), Record_ID, trxName);
			     
		   } else if (tableName.equals(MDocType.Table_Name)) {
			     return new MDocType(Env.getCtx(), Record_ID, trxName);
			     
		   }
		   return null;
	}
	
	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		 if (tableName.equals(MWMArea.Table_Name)) {
		     return new MWMArea(Env.getCtx(), rs, trxName);
		     
		   } else if (tableName.equals(MWMAreaType.Table_Name)) {
				 return new MWMAreaType(Env.getCtx(), rs, trxName);
				     
		   } else if (tableName.equals(MWMDefinition.Table_Name)) {
				 return new MWMDefinition(Env.getCtx(), rs, trxName);
				     
		   } else if (tableName.equals(MWMInOutBound.Table_Name)) {
			     return new MWMInOutBound(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MWMInOutBoundLine.Table_Name)) {
			     return new MWMInOutBoundLine(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MWMInOutBoundLineMA.Table_Name)) {
			     return new MWMInOutBoundLineMA(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MWMRule.Table_Name)) {
			     return new MWMRule(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MWMSection.Table_Name)) {
			     return new MWMSection(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MWMSectionDetail.Table_Name)) {
			     return new MWMSectionDetail(Env.getCtx(), rs, trxName);

		   } else if (tableName.equals(MWMSectionType.Table_Name)) {
			     return new MWMSectionType(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MWMStrategy.Table_Name)) {
			     return new MWMStrategy(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MWMStrategyDetail.Table_Name)) {
			     return new MWMStrategyDetail(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MDDOrderLine.Table_Name)) {
			     return new MDDOrderLine(Env.getCtx(), rs, trxName);
			     
		   } else if (tableName.equals(MDocType.Table_Name)) {
			     return new MDocType(Env.getCtx(), rs, trxName);
			     
		   }
		   return null;
	}

}
