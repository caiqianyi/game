package com.lebaoxun.game.data.model;

import java.io.Serializable;
import java.util.List;

public class GameRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8705002137006458809L;
	
	private Integer roomNo;
	private String password;
	private Integer gameId;
	private Integer[] optionIds;
	/**
	 * 成员数
	 */
	private List<GameRoomMember> members;
	/**
	 * 每把游戏数据
	 */
	private List<GameRoomRound> gameRoomRound;
	
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer[] getOptionIds() {
		return optionIds;
	}
	public void setOptionIds(Integer[] optionIds) {
		this.optionIds = optionIds;
	}
	public Integer getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(Integer roomNo) {
		this.roomNo = roomNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<GameRoomRound> getGameRoomRound() {
		return gameRoomRound;
	}
	public void setGameRoomRound(List<GameRoomRound> gameRoomRound) {
		this.gameRoomRound = gameRoomRound;
	}
	public List<GameRoomMember> getMembers() {
		return members;
	}
	public void setMembers(List<GameRoomMember> members) {
		this.members = members;
	}
}
