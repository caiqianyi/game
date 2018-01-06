package com.lebaoxun.phz.game.core.config;

public enum PhzPaixing {
	
	TÍ(101,"提牌","一条龙"),
	WĒI(102,"偎牌","笑到"),
	CHÒU(103,"臭偎","忍碰之后，又偎牌，称为臭偎"),
	PÈNG(104,"碰牌","当别人打或摸的牌自己手中有一对，则可以碰牌"),
	PǍO(105,"跑牌","当别人打或摸的牌自己手中有一坎，或者是已经偎牌的牌，则可以跑牌，跑牌后将牌放到桌面。或者当别人从墩上摸的牌，是自己已经碰牌的牌，则同样可以跑牌。如果没有跑牌，称为走跑。（有跑必跑 ,第一次跑需要打出一张牌,之后若再跑称为重跑,不需打牌,直接给下家发牌）"),
	CHĪ(106,"吃牌","上家出的或者自己摸的牌，示众后没有人碰牌或者跑牌，则可以与自己手中的牌组合成一句话，或者绞牌，放到桌面，称为吃牌"),
	KǍN(107,"坎牌","三张同样的牌，为坎牌"),
	DUÌZI(108,"对子","对子"),
	DĀN(100,"单张","单张");
	
	private Integer value;
	private String name;
	private String descr;
	
	private PhzPaixing(Integer value,String name,String descr) {
		// TODO Auto-generated constructor stub
		this.value = value;
		this.name = name;
		this.descr = descr;
	}
	
	public Integer getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	public String getDescr() {
		return descr;
	}
}
