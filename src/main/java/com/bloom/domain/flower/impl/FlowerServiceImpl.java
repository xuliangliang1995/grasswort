package com.bloom.domain.flower.impl;
import java.util.Date;
/**
 * 花朵er
 */
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.testng.Assert;

import com.bloom.dao.ext.FlowerExtDao;
import com.bloom.dao.po.Flower;
import com.bloom.dao.po.FlowerExample;
import com.bloom.domain.flower.FlowerService;
import com.bloom.domain.flower.meta.FlowerStar;
import com.bloom.domain.gardener.general.LoginCheckUtil;
import com.bloom.exception.FlowBreakException;
@Service
public class FlowerServiceImpl implements FlowerService {
	@Resource
	private FlowerExtDao flowerExtDao;

	@Override
	@Transactional
	public Flower create(HttpServletRequest request, Flower flower) {
		Date now = new Date();
		int gardenerId = LoginCheckUtil.loginGardenerId(request);
		Assert.assertTrue(StringUtils.hasText(flower.getName()),"花儿不能没有名字！");
		flower.setGardenerId(gardenerId);
		flower.setStar(FlowerStar.Star_1.value());
		flower.setCt(now);
		flower.setUt(now);
		flowerExtDao.insert(flower);
		return flower;
	}

	@Override
	@Transactional
	public void deleteById(HttpServletRequest request, Integer id) {
		int gardenerId = LoginCheckUtil.loginGardenerId(request);
		Flower flower = this.findById(id)
				.orElseThrow(() -> new FlowBreakException("您要操作的资源不存在或已被删除！"));
		Assert.assertEquals(flower.getGardenerId().intValue(), gardenerId,"操作权限不足！");
		flowerExtDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public Flower edit(HttpServletRequest request, Flower flower) {
		Assert.assertNotNull(flower.getId(),"资源指定不明确！");
		
		Flower targetFlower = this.findById(flower.getId())
				.orElseThrow(() -> new FlowBreakException("您要编辑的资源不存在或已被删除！"));
		
		int gardenerId = LoginCheckUtil.loginGardenerId(request);
		Assert.assertEquals(targetFlower.getGardenerId().intValue(), gardenerId, "操作权限不足！");
		
		flower.setGardenerId(targetFlower.getGardenerId());
		flowerExtDao.updateByPrimaryKeySelective(flower);
		
		return this.findById(flower.getId()).get();
	}

	@Override
	public Optional<Flower> findById(Integer id) {
		Assert.assertNotNull(id,"资源指定不明确");
		return Optional.ofNullable(
				flowerExtDao.selectByPrimaryKey(id)
				);
	}

	@Override
	public List<Flower> findFlowerByGardener(Integer gardenerId) {
		Assert.assertNotNull(gardenerId,"资源指定不明确");
		FlowerExample example = new FlowerExample();
		example.createCriteria().andGardenerIdEqualTo(gardenerId);
		return flowerExtDao.selectByExample(example);
	}

}