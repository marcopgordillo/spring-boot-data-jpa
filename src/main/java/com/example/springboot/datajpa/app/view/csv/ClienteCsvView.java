package com.example.springboot.datajpa.app.view.csv;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

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

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
        response.setContentType(getContentType());
    }
}
