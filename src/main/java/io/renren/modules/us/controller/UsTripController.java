package io.renren.modules.us.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.UsTripLocationEntity;
import io.renren.modules.us.param.UsTripParam;
import io.renren.modules.us.service.UsTripService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fss
 * @date 2018-09-07 13:26:30
 */
@RestController
@RequestMapping("/api/trip")
@Api("出行接口")
public class UsTripController {
	@Autowired
	private UsTripService usTripService;
	@Autowired
	private UsSessionUtil sessionUtil;

	/*@PostMapping("historyAdd")
	@ApiOperation("收藏/出发地目的地历史记录保存接口")
	public R historyAdd(@RequestBody UsTripParam usTriprParam) throws ParseException {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);
		String userId = sessionUtil.getUserId(usTriprParam.getSession());
		UsTripLocationEntity tripLocation = usTripService.selectByType(usTriprParam, userId);
		if (tripLocation != null) {
			tripLocation.setIsDeleted("0");
			usTripService.updateById(tripLocation);
		} else {
			UsTripLocationEntity trip = new UsTripLocationEntity();
			trip.setId(UsIdUtil.generateId());
			trip.setUserId(userId);
			trip.setCity(usTriprParam.getCity());
			trip.setName(usTriprParam.getName());
			trip.setType(usTriprParam.getType());
			trip.setIsFavourite("1");
			trip.setAmapLatitude(usTriprParam.getAmap_latitude());
			trip.setAmapLongitude(usTriprParam.getAmap_longitude());
			trip.setCreateDate(new Date());
			usTripService.insert(trip);
		}
		return R.ok();
	}*/

	@PostMapping("historyList")
	@ApiOperation("出发地目的地历史记录展示接口")
	public R historyList(@RequestBody UsTripParam usTriprParam) {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);

		String userId = sessionUtil.getUserId(usTriprParam.getSession());

		Map<String, Object> params = new HashMap<>();

		String pageNo = (usTriprParam.getPageNo() == null || "".equals(usTriprParam.getPageNo())) ? "1"
				: usTriprParam.getPageNo();
		String pageSize = (usTriprParam.getPageSize() == null || "".equals(usTriprParam.getPageSize())) ? "10"
				: usTriprParam.getPageSize();
		params.put("userId", userId);
		params.put("type", usTriprParam.getType());
		params.put("limit", pageSize);
		params.put("page", pageNo);

		Page<Map> page = usTripService.historyList(params);
		return R.ok(page);
	}

	@PostMapping("collectList")
	@ApiOperation("收藏展示接口")
	public R collectList(@RequestBody UsTripParam usTriprParam) {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);

		String userId = sessionUtil.getUserId(usTriprParam.getSession());

		Map<String, Object> params = new HashMap<>();

		String pageNo = (usTriprParam.getPageNo() == null || "".equals(usTriprParam.getPageNo())) ? "1"
				: usTriprParam.getPageNo();
		String pageSize = (usTriprParam.getPageSize() == null || "".equals(usTriprParam.getPageSize())) ? "10"
				: usTriprParam.getPageSize();
		params.put("userId", userId);
		params.put("type", usTriprParam.getType());
		params.put("limit", pageSize);
		params.put("page", pageNo);

		PageUtils page = usTripService.collectList(params);
		return R.ok(page);
	}


	@PostMapping("collectDel")
	@ApiOperation("收藏取消接口")
	public R collectDel(@RequestBody UsTripParam usTriprParam) throws ParseException {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);
		String userId = sessionUtil.getUserId(usTriprParam.getSession());
		UsTripLocationEntity tripLocation = usTripService.selectByType(usTriprParam, userId);
		if (tripLocation != null) {
			tripLocation.setIsDeleted("1");
			usTripService.updateById(tripLocation);
		}

		return R.ok();
	}

	@PostMapping("historyEmpty")
	@ApiOperation("出发地目的地历史记录清空")
	public R historyEmpty(@RequestBody UsTripParam usTriprParam) throws ParseException {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);
		String userId = sessionUtil.getUserId(usTriprParam.getSession());
		String type=usTriprParam.getType();
		usTripService.deleteHistory(type,userId);
		
		return R.ok();
	}

	@PostMapping("homeList")
	@ApiOperation("家/公司展示接口")
	public R homeList(@RequestBody UsTripParam usTriprParam) {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);

		String userId = sessionUtil.getUserId(usTriprParam.getSession());

		Map<String, Object> params = new HashMap<>();

		String pageNo = (usTriprParam.getPageNo() == null || "".equals(usTriprParam.getPageNo())) ? "1"
				: usTriprParam.getPageNo();
		String pageSize = (usTriprParam.getPageSize() == null || "".equals(usTriprParam.getPageSize())) ? "10"
				: usTriprParam.getPageSize();
		params.put("userId", userId);
		params.put("type", usTriprParam.getType());
		params.put("limit", pageSize);
		params.put("page", pageNo);

		PageUtils page = usTripService.list(params);
		return R.ok(page);
	}


	@PostMapping("homeAdd")
	@ApiOperation("家/公司保存接口")
	public R homeAdd(@RequestBody UsTripParam usTriprParam) throws ParseException {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);

		String userId = sessionUtil.getUserId(usTriprParam.getSession());
		UsTripLocationEntity tripLocation = new UsTripLocationEntity();
		tripLocation.setId(UsIdUtil.generateId());
		tripLocation.setUserId(userId);
		tripLocation.setCity(usTriprParam.getCity());
		tripLocation.setName(usTriprParam.getName());
		tripLocation.setType(usTriprParam.getType());
		tripLocation.setIsFrequent("1");
		tripLocation.setAmapLatitude(usTriprParam.getAmap_latitude());
		tripLocation.setAmapLongitude(usTriprParam.getAmap_longitude());
		tripLocation.setCreateDate(new Date());

		usTripService.insert(tripLocation);
		return R.ok();
	}

	@PostMapping("homeUpdate")
	@ApiOperation("家/公司修改接口")
	public R homeUpdate(@RequestBody UsTripParam usTriprParam) throws ParseException {
		// 表单校验
		ValidatorUtils.validateEntity(usTriprParam);
		// 修改
		UsTripLocationEntity tripLocation = usTripService.selectById(usTriprParam.getId());
		tripLocation.setCity(usTriprParam.getCity());
		tripLocation.setName(usTriprParam.getName());
		tripLocation.setAmapLatitude(usTriprParam.getAmap_latitude());
		tripLocation.setAmapLongitude(usTriprParam.getAmap_longitude());
		usTripService.updateById(tripLocation);	 
		return R.ok();
	}

}
