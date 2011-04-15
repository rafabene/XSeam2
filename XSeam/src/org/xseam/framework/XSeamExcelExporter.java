package org.xseam.framework;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.excel.exporter.ExcelExporter;

@Name("xSeamExcelExporter")
public class XSeamExcelExporter {
    
    
    public void export(ActionEvent e){
        FacesContext fCtx = FacesContext.getCurrentInstance();
        
        String clientId = e.getComponent().getClientId(fCtx);
        int position = clientId.lastIndexOf(":"); 
        String container = clientId.substring(0, position);
        String tableId = fCtx.getExternalContext().getRequestParameterMap().get("tableId");
        
        ExcelExporter excelExporter = new ExcelExporter();
        excelExporter.export(container + ":" + tableId);
    }

}
