package com.testing91.dao;


import com.testing91.domain.OrderInfo;
import com.testing91.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private JdbcTemplate jdbcT;

    public void setJdbcT(JdbcTemplate jdbcT) {
        this.jdbcT = jdbcT;
    }

    public List<OrderInfo> findAll() {


        String sql = "select * from order_info;";

        ParameterizedRowMapper<OrderInfo> mapper = new ParameterizedRowMapper<OrderInfo>() {

            public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setId(Integer.parseInt(rs.getString("id")));
                orderInfo.setOrderName(rs.getString("orderName"));
                orderInfo.setOrderDesc(rs.getString("orderDesc"));
                orderInfo.setOrderStatus(rs.getString("status"));
                orderInfo.setApplyTime(rs.getTimestamp("applyTime"));
                return orderInfo;
            }
        };
        return jdbcT.query(sql, mapper);

    }

    public int findCount(String status) {
        String sqlCount = "select count(*) from order_info";
        if (StringUtils.isNotBlank(status)) {
            sqlCount += " where status='" + status + "'";
        }
        int count = jdbcT.queryForInt(sqlCount);
        return count;
    }

    public PageUtil<OrderInfo> find(String status, String currentPage) {

        PageUtil<OrderInfo> pager = new PageUtil<OrderInfo>(100, currentPage);
        String sqlCount = "select count(*) from order_info";
        if (StringUtils.isNotBlank(status)) {
            sqlCount += " where status='" + status + "'";
        }
        int count = jdbcT.queryForInt(sqlCount);
        pager.setRowCount(count);
        StringBuilder sb = new StringBuilder();

        sb.append(" select * from order_info where id <= ( ");
        sb.append(" select id from order_info ");
        if (StringUtils.isNotBlank(status)) {
            sb.append(" where status='" + status + "'");
        }

        sb.append(" order by id desc limit ");
        sb.append((pager.getCurrentPage() - 1) * pager.getPageSize());

        sb.append(" , 1 )  ");
        if (StringUtils.isNotBlank(status)) {
            sb.append("and status='" + status + "'");
        }
        sb.append(" order by id desc limit ");
        sb.append(pager.getPageSize());
        ParameterizedRowMapper<OrderInfo> mapper = new ParameterizedRowMapper<OrderInfo>() {

            public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setId(Integer.parseInt(rs.getString("id")));
                orderInfo.setOrderName(rs.getString("orderName"));
                orderInfo.setOrderDesc(rs.getString("orderDesc"));
                orderInfo.setOrderStatus(rs.getString("status"));
                orderInfo.setApplyTime(rs.getTimestamp("applyTime"));

                return orderInfo;
            }
        };
        pager.setArrayList(jdbcT.query(sb.toString(), mapper));
        return pager;
    }

    public void update(int id, String status) {
        String sql = "update order_info set status = ? WHERE id = ?";
        jdbcT.update(sql, status, id);
    }

    public void save(OrderInfo orderInfo) {
        String sql = "insert into order_info(orderName,orderDesc,status,applyTime) values(?,?,?,?)";
        jdbcT.update(sql, orderInfo.getOrderName(),orderInfo.getOrderDesc(),orderInfo.getOrderStatus(),orderInfo.getApplyTime());
    }

    public List<String> getIpaddress(String status) {
        String sql = "select ipaddress from server_info where status = ?";
        List<String> listIpaddress = new ArrayList<String>();
        listIpaddress = jdbcT.queryForList(sql, new Object[]{status}, String.class);
        return listIpaddress;
    }

}
