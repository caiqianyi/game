package com.lebaoxun.phz.game.core.cal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caiqianyi.commons.exception.I18nMessageException;
import com.google.gson.Gson;
import com.lebaoxun.game.data.model.GameRoomMember;
import com.lebaoxun.game.data.model.GameRoomRound;
import com.lebaoxun.phz.game.core.config.PhzPaixing;

/**
 * 0=禁止操作，1=出牌，2=摸牌 >2 提示 3=胡，4=跑，5=偎，6=碰，7=吃
 * 1=自摸，提 2=放炮，跑
 * @author DELL
 *
 */
public class PhzGameRound {
	
	private static Logger logger = LoggerFactory.getLogger(PhzGameRound.class);
	
	protected GameRoomRound gameRoomRound;
	
	public final static char[] card_blank = new char[]{0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0};
	
	private int initCardCount = 20;//初始牌数 
	private int minHuxi = 15;//最小胡息 
	private int putCardOffset = 0;//抓牌位置
	
	public PhzGameRound(int initCardCount,int minHuxi) {
		// TODO Auto-generated constructor stub
		this.initCardCount = initCardCount;
		this.minHuxi = minHuxi;
	}
	
	/**
	 * 判断谁出牌
	 * @return
	 */
	public int checkWhoPutOffCard(){
		List<GameRoomMember> members = gameRoomRound.getMembers();
		int banker_i = -1;
		for(int i=0;i<members.size();i++){
			GameRoomMember member = members.get(i);
			if(member.getFlag().equals(1)){
				banker_i = i;
			}
			if(member.getHandleFlag() > 0){
				return i;
			}
		}
		if(banker_i > -1){
			members.get(banker_i).setHandleFlag(1);
			return banker_i;
		}
		throw new I18nMessageException("-1","游戏出错了，没有出牌玩家");
	}
	
	/**
	 * 将牌落地
	 * @param member
	 * @param cardPoints
	 * @param paixing
	 */
	public void addAlreadyCards(GameRoomMember member,List<Integer> cardPoints,PhzPaixing paixing){
		/*List<Integer> cards = member.getCards();
		Map<Integer, List<Integer>> alreadyCards = member.getAlreadyCards();
		cards.removeAll(cardPoints);
		alreadyCards.put(paixing.getValue(), cardPoints);*/
	}
	
	/**
	 * 初始牌
	 */
	public GameRoomRound initCard(List<GameRoomMember> members) {
		List<Integer> listCard = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            for (int k = 0; k < 4; k++) {
            	listCard.add(i);
            }
        }
        shuffleTheCards(listCard);//洗牌
        
        this.gameRoomRound = new GameRoomRound(members,listCard);
        Collection<GameRoomMember> players = gameRoomRound.getMembers();
        for(GameRoomMember member: players){//初始化牌
        	member.setCards(new char[20]);
			member.setAlreadyCards(new char[20]);
		}
        dealingTheCards(players);
        return gameRoomRound;
    }
	
	void dealingTheCards(Collection<GameRoomMember> members) {
		// TODO Auto-generated method stub
		if(members.size() != 3){
			throw new I18nMessageException("-1","房间人数不对");
		}
		/**
		 * 六胡抢初始发牌 庄家15张，其余人14张
		 */
		for(int i=0;i<this.initCardCount+1;i++){
			for(GameRoomMember member: members){
				if(i==14 && member.getFlag() == 0){
					continue;
				}
				Integer card = this.gameRoomRound.getListCard().get(putCardOffset);
				this.putCardOffset++;
				++member.getCards()[card];
			}
		}
		
		checkWhoPutOffCard();
		logger.debug("members={}",new Gson().toJson(members));
	}
	
	/**
	 * 洗牌
	 * @param listCard
	 */
	public void shuffleTheCards(List<Integer> listCard) {
        Collections.shuffle(listCard);
        Collections.shuffle(listCard);
    }
	
	/**
	 * 摸牌
	 */
	public String pickCard(){
		int idex = checkWhoPutOffCard();
		List<GameRoomMember> members = gameRoomRound.getMembers();
		GameRoomMember self = members.get(idex);
		int handleFlag = self.getHandleFlag();
		if(handleFlag == 2){
			Integer cur_card = this.gameRoomRound.getListCard().get(putCardOffset);
			char cards[] = self.getCards();
			
			if(PhzLib.check_hu(cards, cur_card, minHuxi)) return idex+"_3_1";//自摸
			
			String otherHandleFlagStr = checkOtherCard(idex, cur_card);
			String ohs[] = otherHandleFlagStr.split("\\_");
			int i = Integer.parseInt(ohs[0]), otherHandleFlag = Integer.parseInt(ohs[1]);
			
			if(PhzLib.check_gang(cards, cur_card)) return idex+"_4_1";//提，不同玩法，摸牌提，他人不能胡
			
			if(otherHandleFlag == 3) return otherHandleFlagStr+"_3";//别人胡了
			
			if(PhzLib.check_peng(cards, cur_card)) return idex+"_5_1";//偎
			
			if(otherHandleFlag == 6) return otherHandleFlagStr+"_3";//别人碰
			
			if(PhzLib.check_chi(cards, cur_card)) return idex+"_7_1";//自己吃
			
			if(otherHandleFlag == 7) return idex+"_2";//别人吃
			
			members.get(i).setHandleFlag(otherHandleFlag);//自动过牌，下家摸牌
			
			this.putCardOffset++;
			
			return otherHandleFlagStr;
		}
		return null;
	}
	
	/**
	 * 出牌
	 * @param userId 玩家ID
	 * @param cur_card 出牌数
	 * @return 返回出牌结果 > 0_0；前面数字代表玩家ID，后面代表出牌下家结果。0=出的牌没有人要，3=胡，4=跑，5=偎，6=碰，7=吃
	 */
	public String putCard(int userId,int cur_card){
		List<GameRoomMember> members = gameRoomRound.getMembers();
		int idex = members.indexOf(userId);
		if(idex>-1){
			GameRoomMember self = members.get(idex);
			if(self.getCards()[cur_card] > 0) {
				
				//减少牌数
				--self.getCards()[cur_card];
				self.setHandleFlag(0);//0=禁止操作，1=出牌，2=摸牌 >2 提示 3=胡，4=跑，5=偎，6=碰，7=吃
				
				/**
				 * 下面是判断出的牌，下家是否可以干，碰，吃
				 */
				String handle = checkOtherCard(idex, cur_card);
				String hs[] = handle.split("\\_");
				int i = Integer.parseInt(hs[0]), handleFlag = Integer.parseInt(hs[1]);
				members.get(i).setHandleFlag(handleFlag);//设置下家操作状态
				return handle + "_2";
			}
		}
		return null;
	}
	
	public String checkOtherCard(int idex,int cur_card){
		List<GameRoomMember> members = gameRoomRound.getMembers();
		/**
		 * 下面是判断出的牌，下家是否可以干，碰，吃
		 */
		int s = idex+1,i = 0;
		while((s == idex && i == 3)){
			if(s > members.size() -1){
				s = 0;
			}
			GameRoomMember other = members.get(s);
			char[] cards = other.getCards();
			
			if(i == 1 && PhzLib.check_hu(cards, cur_card, minHuxi)) return s+"_3";
			
			if(i == 2 && PhzLib.check_gang(cards, cur_card)) return s+"_4";
			
			if(i == 3 && PhzLib.check_peng(cards, cur_card)) return s+"_6";
			
			if(i == 4 && PhzLib.check_chi(cards, cur_card)) return s+"_7";
			
			if(s == idex){
				s = idex+1;
				i++;
			}
		}
		int next = idex+1 > members.size() -1 ? 0 : idex+1;
		return next+"_2";
	}
	
	public static void main(String[] args) {
		
		GameRoomMember memberA = new GameRoomMember(1,1,true,"A",""),
				memberB = new GameRoomMember(2,0,false,"B",""),
				memberC = new GameRoomMember(3,0,false,"C","");
		List<GameRoomMember> members = new ArrayList<GameRoomMember>();
		members.add(memberA);
		members.add(memberB);
		members.add(memberC);
		new PhzGameRound(14, 6).initCard(members);
	}
}