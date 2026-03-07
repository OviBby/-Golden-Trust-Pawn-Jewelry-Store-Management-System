package lk.ijse.jewelryshop.bo.custom.impl;

import lk.ijse.jewelryshop.bo.custom.OrderBO;
import lk.ijse.jewelryshop.dao.DaoFactoty;
import lk.ijse.jewelryshop.dao.Impl.OrderDAO;
import lk.ijse.jewelryshop.dao.custom.CustomerDAOImpl;
import lk.ijse.jewelryshop.dbconnection.DBConnection;
import lk.ijse.jewelryshop.dto.ItemDTO;
import lk.ijse.jewelryshop.dto.OrderDTO;
import lk.ijse.jewelryshop.entity.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DaoFactoty.getInstance().getDAO(DaoFactoty.DaoTypes.ORDER);

    @Override
    public String generateId() throws SQLException {
        return orderDAO.generateId();
    }

    @Override
    public String getCustomer(String nic) throws SQLException {
        return orderDAO.getCustomer(nic);
    }

    @Override
    public List<ItemDTO> getItem(String itemId) throws SQLException {
        return orderDAO.getItem(itemId);
    }

    @Override
    public boolean save(OrderDTO dto) throws SQLException {
        Order order = new Order(
                dto.getOrderId(),
                dto.getItem_id(),
                dto.getDescription(),
                dto.getWeight(),
                dto.getQty(),
                dto.getUprice(),
                dto.getPrice()
        );
        order.setDate(dto.getDate());
        order.setCustomername(dto.getCustomername());
        order.setNic(dto.getNic());
        return orderDAO.save(order);
    }

    @Override
    public ArrayList<OrderDTO> getAll() throws SQLException {
        ArrayList<Order> list = orderDAO.getAll();
        ArrayList<OrderDTO> listDTO = new ArrayList<>();
        for (Order order : list) {
            listDTO.add(new OrderDTO(
                    order.getOrderId(),
                    order.getItem_id(),
                    order.getDescription(),
                    order.getWeight(),
                    order.getQty(),
                    order.getUprice(),
                    order.getPrice()
            ));
        }
        return listDTO;
    }

    @Override
    public void printReport() throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = CustomerDAOImpl.class.getResourceAsStream("/report/order_A4.jrxml");
        if (inputStream == null) {
            throw new RuntimeException("Report file not found: /report/order_A4.jrxml");
        }
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}