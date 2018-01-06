package com.lebaoxun.game.data.model;

import java.util.ArrayList;
import java.util.List;

public class GameRoomRound {
	
	private List<Integer> listCard;
	/**
	 * 成员数
	 */
	private List<GameRoomMember> members;
	/**
	 * 打牌日志
	 */
	private List<GameLog> logs;
	/**
	 * 每局标识 是否结束 0=未开始，1=进行中，2=已结束
	 */
	private Integer flag = 0;
	
	public GameRoomRound(List<GameRoomMember> members,List<Integer> listCard) {
		// TODO Auto-generated constructor stub
		this.members = new ArrayList<GameRoomMember>();
		for(GameRoomMember member: members){
			this.members.add(((GameRoomMember)member.clone()).reset());
		}
		this.logs = new ArrayList<GameLog>();
		this.flag = 0;
		this.listCard = listCard;
	}
	
	public List<GameRoomMember> getMembers() {
		return members;
	}
	public void setMembers(List<GameRoomMember> members) {
		this.members = members;
	}
	public List<GameLog> getLogs() {
		return logs;
	}
	public void setLogs(List<GameLog> logs) {
		this.logs = logs;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public List<Integer> getListCard() {
		return listCard;
	}

	public void setListCard(List<Integer> listCard) {
		this.listCard = listCard;
	}
}
