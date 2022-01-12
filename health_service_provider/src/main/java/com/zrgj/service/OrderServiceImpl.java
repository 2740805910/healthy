package com.zrgj.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zrgj.constant.MessageConstant;
import com.zrgj.dao.MemberDao;
import com.zrgj.dao.OrderDao;
import com.zrgj.dao.OrderSettingDao;
import com.zrgj.entity.Result;
import com.zrgj.pojo.Member;
import com.zrgj.pojo.Order;
import com.zrgj.pojo.OrderSetting;
import com.zrgj.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;


    @Override
    public Result order(Map map) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
                  String orderDate=(String) map.get("orderDate");

                   Date  date =DateUtils.parseString2Date(orderDate);
                   OrderSetting orderSetting =orderSettingDao.findByorderDate(date);

                   if(orderSetting==null){
                       return  new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
                   }


        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
         int  number  =orderSetting.getNumber();//可预约人数
           int reservations=orderSetting.getReservations();//已预约人数
            if(reservations>=number){
                //约满
                return  new Result(false,MessageConstant.ORDER_FULL);
            }


        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
                    String telephone=(String) map.get("telephone");
                    Member member =memberDao.findByTelephone(telephone);
                //判断会员是否存在
                 if(member!=null){
                     Integer memberId=member.getId();//获取会员ID
                     int setmealId=  Integer.parseInt((String)map.get("setmealId")) ;
                     Order  order=new Order(memberId,date,null,null,setmealId);
                     List<Order> orders =orderDao.findByCondtion(order);
                     if(order!=null && orders.size()>0){
                         //已经预约，提示不能重复预约
                         return  new Result(false,MessageConstant.HAS_ORDERED);
                     }

                 }

                  //可以预约
            orderSetting.setReservations(orderSetting.getReservations()+1);//已预约人数加1
             orderSettingDao.editReservationsByOrderDate(orderSetting); //修改Reservations数据




        //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
                 if(member==null){
                     //该用户不是会员，保存会员
                     member=new Member();
                     member.setName((String) map.get("name"));
                     member.setPhoneNumber(telephone);
                     member.setSex((String) map.get("sex"));
                     member.setRegTime(new Date());
                     member.setIdCard((String) map.get("idCard"));
                    //保存会员
                     memberDao.add( member);
                 }


        //5、预约成功，更新当日的已预约人数
                Order  order= new Order(member.getId(), date,
                        (String) map.get("orderType"),
                        Order.ORDERSTATUS_NO,
                        Integer.parseInt ((String)map.get("setmealId")));
           orderDao.add(order);





        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }
}
