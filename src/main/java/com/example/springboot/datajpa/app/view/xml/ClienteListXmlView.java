package com.example.springboot.datajpa.app.view.xml;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView {

    private final Jaxb2Marshaller marshaller;

    public ClienteListXmlView(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        model.remove("titulo");
        model.remove("page");

        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");

        model.remove("clientes");

        model.put("clienteList", new ClienteList(clientes.getContent()));


        super.renderMergedOutputModel(model, request, response);
    }
}
