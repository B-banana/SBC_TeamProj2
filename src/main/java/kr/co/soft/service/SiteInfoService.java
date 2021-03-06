package kr.co.soft.service;

import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.soft.bean.UserBean;
import kr.co.soft.dao.SiteInfoDao;

@Service
public class SiteInfoService {

	// UserController
	@Resource(name = "loginUserBean")
	private UserBean loginUserBean;

	@Autowired
	private SiteInfoDao siteInfoDao;

	public String userCount() {

		return formatter(siteInfoDao.userCount());
	}

	// 유저수 쉼표 처리
	private String formatter(int userCount) {

		DecimalFormat ft = new DecimalFormat("###,###");

		String result = ft.format(userCount);

		return result;

	}

	// 오늘자 주문 총 수
	public int getTodayOrderCount() {

		return siteInfoDao.getTodayOrderCount();

	}

	// 오늘 매출 액

	public int getTotal_amount() {

		return siteInfoDao.getTotal_amount();
	}

}
