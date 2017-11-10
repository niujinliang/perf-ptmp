package com.testing91.controller;

import javax.servlet.http.HttpServletRequest;

import com.testing91.dao.OrderDao;
import com.testing91.domain.OrderInfo;
import com.testing91.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderDao orderDao;
    private static List<byte[]> list;
    static{
        list = new ArrayList<byte[]>();
    }
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String status = request.getParameter("orderStatus");
        if ("undelivery".equals(request.getParameter("oper"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            String oper = "undelivery";
            orderDao.update(id, oper);
            status = "";
        }
        /***
         * 需要考虑查询list为空的情况
         */
        PageUtil<OrderInfo> list = orderDao.find(status, request.getParameter("currentPage"));

        URLBuilder urlBuilder = new URLBuilder();
        urlBuilder.fromRequest(request);

        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("pager", list);

        modelAndView.addObject("urlBuilder", urlBuilder);
        return modelAndView;
    }

    @RequestMapping("/orderDashboard")
    public ModelAndView orderDashboard() {
        int undelivery = orderDao.findCount("undelivery");
        int deliveried = orderDao.findCount("deliveried");
        ModelAndView modelAndView = new ModelAndView("/orderDashboard");
        modelAndView.addObject("undelivery", undelivery);
        modelAndView.addObject("deliveried", deliveried);

        return modelAndView;
    }

    public static Date timeStampToData(Timestamp tt) {
        return new Date(tt.getTime());
    }

    @RequestMapping("/addOrder")
    public ModelAndView addServer(HttpServletRequest request) {
        String orderName = request.getParameter("orderName");
        String orderDesc = request.getParameter("orderDesc");
        String orderStatus = request.getParameter("orderStatus");

//        int messageLength = 1024 * 1024;
//        byte[] b = new byte[messageLength];
//        List<byte[]> list1 = new ArrayList<byte[]>();
//        list1.add(b);
//        try {
//          Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if (StringUtils.isNotBlank(orderName) && StringUtils.isNotBlank(orderDesc) && StringUtils.isNotBlank(orderStatus)) {
            OrderInfo orderInfo = new OrderInfo(orderName, orderDesc, orderStatus, new Timestamp(System.currentTimeMillis()));
            orderDao.save(orderInfo);
            return new ModelAndView("redirect:/index");
        }

        ModelAndView modelAndView = new ModelAndView("/addOrder");
        modelAndView.addObject("currentUrl", request.getRequestURL());
        return modelAndView;
    }

    @RequestMapping("/release")
    public ModelAndView releaseServer(HttpServletRequest request) {
        String ipAddress = request.getParameter("ipaddress");

        if (StringUtils.isNotBlank(ipAddress)) {

            return new ModelAndView("redirect:/index");
        }

        ModelAndView modelAndView = new ModelAndView("/addServer");
        modelAndView.addObject("currentUrl", request.getRequestURL());

        return modelAndView;
    }

}
