package com.eshore.upsweb.controller;


import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.common.page.Pager;
import com.eshore.upsweb.model.PayLog;
import com.eshore.upsweb.service.PayLogService;
import com.eshore.upsweb.util.StringUtil;

@Controller
@RequestMapping("/payLog")
public class PayLogController {
	
	@Autowired
	PayLogService payLogService;
	PayLog payLog ;
	
	/*@RequestMapping(value="/listPayLog")
	public ModelAndView listPayLog(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("payLog/index");
		Integer pageNumber = 1;
		if(request.getParameter("pageNumber")!=null ){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		Pager pager = payLogService.findPage(10, pageNumber);
		modelAndView.addObject("page",pager);
		modelAndView.addObject("lastPageNumber",pager.getLastPageNumber());
		modelAndView.addObject("pagers", pager.getItems());
		modelAndView.addObject("totalCount",pager.getTotalCount());
		return modelAndView;
	}
*/
	
	
	@RequestMapping(value="/listPayLog")
	public ModelAndView listPayLog2(@RequestParam(defaultValue="") String payLogId ,@RequestParam(defaultValue="") String orderId,@RequestParam(defaultValue="") String payId,
			@RequestParam(defaultValue="") String payDate,@RequestParam(defaultValue="") String payAcctNo,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("payLog/index");
		payLog =  new PayLog();
		if(payLogId !=null && !payLogId.equals("") && StringUtil.isNumber(payLogId)){
			payLog.setPayLogId(Long.parseLong(payLogId));
		}
	//	payLog.setOrderId(Long.parseLong(orderId));
	//	payLog.setPayId(payId);
		Integer pageNumber = 1;
		Integer pageSize=Pager.PAGESIZE;
		if(request.getParameter("pageNumber")!=null ){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		if(request.getParameter("pageSize")!=null ){
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		Integer beginIndex  = 0;
		if(request.getParameter("pager.offset")!=null)
		beginIndex = Integer.parseInt(request.getParameter("pager.offset"));
		pageNumber = beginIndex/pageSize +1;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PayLog.class);
		//detachedCriteria.createAlias("PayLog", "pl");
		// 说明：（这个问题调试了很久，，，蛋疼。）模糊查询的字段如果不是string类型，那么只能用 Restrictions.sqlRestriction()
		//http://hi.baidu.com/piaokes/item/1921fe3291f59dd46c15e947 另外hibernate4 已经把hibernate.String 取消，改为如下type
//		detachedCriteria.add(Restrictions.eq("payLogId",id));
		detachedCriteria.add(Restrictions.sqlRestriction("PAY_LOG_ID like (?)", payLogId+"%", StandardBasicTypes.STRING));
		detachedCriteria.add(Restrictions.like("payId", payId+"%"));
	//	detachedCriteria.add(Restrictions.sqlRestriction("ORDER_ID like (?)", payLog.getOrderId()+"%",StandardBasicTypes.STRING));
		//detachedCriteria.add(Restrictions.like("payDate", payLog.getPayDate()));
	//	detachedCriteria.add(Restrictions.like("payAcctNo",payLog.getPayAcctNo()+"%"));
		Pager pager = payLogService.findPageByCriteria(detachedCriteria, pageSize, pageNumber);
		modelAndView.addObject("page",pager);
		modelAndView.addObject("lastPageNumber",pager.getLastPageNumber());
		modelAndView.addObject("pagers", pager.getItems());
		modelAndView.addObject("totalCount",pager.getTotalCount());
		modelAndView.addObject("payLog", payLog);
		return modelAndView;
	}

	
	
	public PayLogService getPayLogService() {
		return payLogService;
	}

	public void setPayLogService(PayLogService payLogService) {
		this.payLogService = payLogService;
	}
	
	public PayLog getPayLog() {
		return payLog;
	}

	public void setPayLog(PayLog payLog) {
		this.payLog = payLog;
	}


	
}
