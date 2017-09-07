package wang.wincent.winapp.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.wincent.winapp.base.annotation.SysLog;
import wang.wincent.winapp.base.entity.SysDictEntity;
import wang.wincent.winapp.base.service.SysDictService;
import wang.wincent.winapp.common.utils.Result;


/**
 * 数据字典
 *
 * @author Wincent.Wang
 * @blog   http://wincent.wang
 * @email  wangwincent@163.com
 * @date   2017/8/30
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends AbstractController {

	@Autowired
	private SysDictService sysDictService;
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping("/list")
	public List<SysDictEntity> list() {
		return sysDictService.listDict();
	}
	
	/**
	 * 树形列表
	 * @return
	 */
	@RequestMapping("/select")
	public List<SysDictEntity> select() {
		return sysDictService.listNotDict();
	}
	
	/**
	 * 新增字典
	 * @param macro
	 * @return
	 */
	@SysLog("新增字典")
	@RequestMapping("/save")
	public Result save(@RequestBody SysDictEntity macro) {
		sysDictService.saveDict(macro);
		return Result.ok();
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public Result info(@RequestBody Long id) {
		sysDictService.getObjectById(id);
		return Result.ok();
	}
	
	/**
	 * 修改字典
	 * @param macro
	 * @return
	 */
	@SysLog("修改字典")
	@RequestMapping("/update")
	public Result update(@RequestBody SysDictEntity macro) {
		 sysDictService.updateDict(macro);
		return Result.ok();
	}
	
	/**
	 * 删除字典
	 * @param id
	 * @return
	 */
	@SysLog("删除字典")
	@RequestMapping("/remove")
	public Result batchRemove(@RequestBody Long[] id) {
		sysDictService.batchRemove(id);
		return Result.ok();
	}
	
}
