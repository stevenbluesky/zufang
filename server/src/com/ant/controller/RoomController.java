package com.ant.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ant.util.StringUtil;
import com.isurpass.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ant.business.Device;
import com.ant.business.Room;
import com.ant.vo.DeviceVo;
import com.ant.vo.PersonVo;
import com.ant.vo.RoomVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiDeviceService;
import com.isurpass.service.HiRoomService;

/**
 * 房间
 * @author aijian
 *
 */
@Controller
@RequestMapping(value="/service/room")
@Scope(value = "prototype")
public class RoomController extends BaseController{

	private static Log log = LogFactory.getLog(RoomController.class);
	
	@Resource
	private HiRoomService hiRoomService;
	
	@Resource
	private HiDeviceService hiDeviceService;
	
	/**
	  * @Description: 新增房间
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/saveRoom")
	public @ResponseBody Map<String , Object> saveRoom(@ModelAttribute("room")Room room){
		try{
			if(room.getDistrictId() == null || room.getRoomType() == null || 
					StringUtil.checkNull(room.getRoomName()) || StringUtil.checkNull(room.getBuild()) || 
					StringUtil.checkNull(room.getUnit()) || StringUtil.checkNull(room.getFloor())){
				throw new BusinessException(getRB().getString("xiaoquetc1"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			hiRoomService.saveRoom(room, sessionPersonVo);
			
			return createResponse(1 , getRB().getString("jaddsuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 修改房间
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/updateRoom")
	public @ResponseBody Map<String , Object> updateRoom(@ModelAttribute("room")Room room){
		try{
			if(room.getId() == null || room.getRoomType() == null || 
					StringUtil.checkNull(room.getRoomName()) || StringUtil.checkNull(room.getBuild()) || 
					StringUtil.checkNull(room.getUnit()) || StringUtil.checkNull(room.getFloor())){
				throw new BusinessException(getRB().getString("idetc1"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			hiRoomService.updateRoom(room, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jsave") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 删除房间
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/deleteRoom")
	public @ResponseBody Map<String , Object> deleteRoom(@ModelAttribute("room")Room room){
		try{
			if(room.getId() == null){
				throw new BusinessException(getRB().getString("aid"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			hiRoomService.deleteRoom(room.getId(), sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("drsccg") , null);
		}
		catch (Exception e)
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 分页查询房间
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("roomVo")RoomVo roomVo){
		try{
			if(roomVo.getDistrictId() == null || roomVo.getPage() == null || roomVo.getRows() == null){
				throw new BusinessException(getRB().getString("acom"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			roomVo.setPersonId(sessionPersonVo.getId());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			BasicScroll scroll = new BasicScroll(roomVo.getPage(), roomVo.getRows());
			List<RoomVo> lst = hiRoomService.findPage(roomVo, scroll,idList);
			
			return createResponse(1 , getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 查询房间
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findRoom")
	public @ResponseBody Map<String , Object> findRoom(@ModelAttribute("roomVo")RoomVo roomVo){
		try{
			if(roomVo.getDistrictId() == null ){
				throw new BusinessException(getRB().getString("xiaoquid"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			roomVo.setPersonId(sessionPersonVo.getId());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<Room> list = hiRoomService.findRoom(roomVo,idList);
			return createResponse(1 , getRB().getString("jquerysuccess") , list);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 根据id查询房间信息
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findRoomById")
	public @ResponseBody Map<String , Object> findRoomById(@ModelAttribute("roomVo")RoomVo roomVo){
		try{
			if(roomVo.getId() == null){
				throw new BusinessException(getRB().getString("id"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			Room dbRoom = hiRoomService.findById(roomVo.getId());
			Room.checkSecondAdminRole(dbRoom, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jquerysuccess") , dbRoom);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 根据小区id查询栋
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findBuildByDistrictId")
	public @ResponseBody Map<String , Object> findBuildByDistrictId(@ModelAttribute("roomVo")RoomVo roomVo){
		try{
			if(roomVo.getDistrictId() == null){
				throw new BusinessException(getRB().getString("xiaoquid"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			List<Map> buildList = hiRoomService.findBuildByDistrictId(sessionPersonVo.getId(), roomVo.getDistrictId());
			
			return createResponse(1 , getRB().getString("jquerysuccess") , buildList);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 根据栋查询单元
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findUnitByBuild")
	public @ResponseBody Map<String , Object> findUnitByBuild(@ModelAttribute("roomVo")RoomVo roomVo){
		try{
			if(roomVo.getDistrictId() == null || StringUtil.checkNull(roomVo.getBuild())){
				throw new BusinessException(getRB().getString("xiaoquiddong"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			List<Map> unitList = hiRoomService.findUnitByBuild(sessionPersonVo.getId(), roomVo.getDistrictId(),roomVo.getBuild());
			
			return createResponse(1 , getRB().getString("jquerysuccess") , unitList);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 根据单元查询楼
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findFloorByUnit")
	public @ResponseBody Map<String , Object> findFloorByUnit(@ModelAttribute("roomVo")RoomVo roomVo){
		try{
			if(roomVo.getDistrictId() == null || StringUtil.checkNull(roomVo.getBuild()) || 
					StringUtil.checkNull(roomVo.getUnit())){
				throw new BusinessException(getRB().getString("xiaoquiddongdanyuan"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			List<Map> unitList = hiRoomService.findFloorByUnit(sessionPersonVo.getId(), roomVo.getDistrictId(),roomVo.getBuild(),roomVo.getUnit());
			
			return createResponse(1 , getRB().getString("jquerysuccess") , unitList);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 绑定设备
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/bindDevice")
	public @ResponseBody Map<String , Object> bindDevice(@ModelAttribute("device")Device device){
		try{
			String deviceIds = StringUtil.objectToString(request.getParameter("deviceIds"));
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(device.getBindRoomId() == null || StringUtil.checkNull(deviceIds)){
				throw new BusinessException(getRB().getString("bindingroomiddeviceid"));
			}
			hiDeviceService.bind(device.getBindRoomId(), deviceIds, sessionPersonVo,getRB());
			
			String watermeterid = StringUtil.objectToString(request.getParameter("watermeterid"));
			String watermetersubtype = StringUtil.objectToString(request.getParameter("watermetersubtype"));
			
			if ( watermeterid != null && watermeterid.length() > 0 )
			{
				JSONArray wmid = JSONArray.parseArray(watermeterid);
				JSONArray wmst = JSONArray.parseArray(watermetersubtype);
				
				for ( int i = 0 ; i < wmid.size() ; i ++ )
					hiDeviceService.updateDeviceSubtype(wmid.getLong(i), wmst.getInteger(i));
			}
			
			return createResponse(1 , getRB().getString("bindingsuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 解绑设备
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/unBindDevice")
	public @ResponseBody Map<String , Object> unBindDevice(@ModelAttribute("device")Device device){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(device.getBindRoomId() == null || device.getId() == null){
				throw new BusinessException(getRB().getString("bindingroomiddeviceid"));
			}
			hiDeviceService.unBind(device.getBindRoomId(), device.getId(), sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("ubboundsuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}

	/**
	  * @Description: 查询绑定的设备
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findBindDevice")
	public @ResponseBody Map<String , Object> findBindDevice(@ModelAttribute("room")Room room){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(room.getId() == null){
				throw new BusinessException(getRB().getString("roomidcnbb"));
			}
			Room dbRoom = hiRoomService.findById(room.getId());
			Room.checkSecondAdminRole(dbRoom, sessionPersonVo,getRB());
			
			List<DeviceVo> list = hiDeviceService.findBindDeviceVo(room.getId());
			
			return createResponse(1 , getRB().getString("jquerysuccess"),list);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 查询未绑定的设备
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findUnBindDevice")
	public @ResponseBody Map<String , Object> findUnBindDevice(@ModelAttribute("room")Room room){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(room.getId() == null){
				throw new BusinessException(getRB().getString("roomidcnbb"));
			}
			Room dbRoom = hiRoomService.findById(room.getId());
			Room.checkSecondAdminRole(dbRoom, sessionPersonVo,getRB());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<DeviceVo> list = hiDeviceService.findUnBindDevice(sessionPersonVo.getId(), room.getId(),idList);
			
			return createResponse(1 , getRB().getString("jquerysuccess"),list);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 授权房间
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/grant")
	public @ResponseBody Map<String , Object> grant(@ModelAttribute("room")Room room){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(room.getId() == null || StringUtil.checkNull(room.getGrantUserName()) || StringUtil.checkNull(room.getGrantRealName()) || 
					room.getGrantBeginDate() == null || room.getGrantEndDate() == null){
				throw new BusinessException(getRB().getString("r1"));
			}
			
			hiRoomService.grant(room.getId(), room.getGrantUserName(), room.getGrantRealName(),room.getGrantBeginDate(),room.getGrantEndDate(), sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("authsuccess"),null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 解授权房间
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/unGrant")
	public @ResponseBody Map<String , Object> unGrant(@ModelAttribute("room")Room room){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(room.getId() == null ){
				throw new BusinessException(getRB().getString("roomidetc"));
			}
			
			hiRoomService.unGrant(room.getId(),sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("withdrawauth"),null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	
	/**
	  * @Description: 查询授权的租客
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findGrantRealName")
	public @ResponseBody Map<String , Object> findGrantRealName(@ModelAttribute("room")Room room){
		try{
			PersonVo sessionPersonVo = super.getCurrentUser();
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<Map> list = hiRoomService.findGrantRealName(sessionPersonVo.getId(),idList);
			
			return createResponse(1 , getRB().getString("jquerysuccess"),list);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
}
