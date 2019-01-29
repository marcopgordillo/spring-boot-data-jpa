package com.example.springboot.datajpa.app.view.csv;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//@Component("listar.csv")
@Component("listar") //Es la unica vista con este nombre no es necesario la extension
public class ClienteCsvView extends AbstractView {

    public ClienteCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
        response.setContentType(getContentType());
        response.setCharacterEncoding("UTF-8");

        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");

        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(),  CsvPreference.EXCEL_PREFERENCE);

        String[] header = {"id", "nombre", "apellido", "email", "dayOfBirth"};
        beanWriter.writeHeader(header);

        for (Cliente cliente : clientes) {
            beanWriter.write(cliente, header);
        }

        beanWriter.close();

    }
}
