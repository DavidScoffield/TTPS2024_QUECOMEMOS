package dao.impl;

import dao.interf.ClientDAO;
import model.Client;

public class ClientDAOHibernateJPA extends GenericDAOHibernateJPA<Client>
    implements ClientDAO {

  public ClientDAOHibernateJPA() {
    super(Client.class);
  }

}
