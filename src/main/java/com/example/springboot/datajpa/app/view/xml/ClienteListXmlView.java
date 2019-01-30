package com.example.springboot.datajpa.app.view.xml;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView {


    @Autowired
    public ClienteListXmlView(Jaxb2Marshaller marshaller) {
        super(marshaller);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

//        model.remove("titulo");
//        model.remove("page");

//        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
        List<Cliente> clientes = (List<Cliente>) model.get("clientes");

        model.remove("clientes");

//        model.put("clienteList", new ClienteList(clientes.getContent()));
        model.put("clienteList", new ClienteList(clientes));


        super.renderMergedOutputModel(model, request, response);
    }
}
