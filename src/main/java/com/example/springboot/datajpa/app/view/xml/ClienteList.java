package com.example.springboot.datajpa.app.view.xml;

import com.example.springboot.datajpa.app.models.entity.Cliente;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "clientes")
public class ClienteList {

    @XmlElement(name = "cliente")
    List<Cliente> clientes;

    public ClienteList() {}

    public ClienteList(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
