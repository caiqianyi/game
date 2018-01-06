package com.lebaoxun.phz.game.core.cal;

import com.lebaoxun.phz.game.core.Item;

public class PhzLib {
	public final static String base_cards[] = new String[]{"一","二","三","四","五","六","七","八","九","十","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"};
	
	public static void memcpy(char tmp_cards[], char[] cards, int offset){
		if(tmp_cards.length > cards.length){
			throw new RuntimeException();
		}
		
	    for(int i = 0; i < tmp_cards.length; i++){
	    	tmp_cards[i] = cards[i+offset];
	    }
	}
	
	public static boolean check_chi(char[] cards, int cur_card){
		if (cur_card < 8 && cards[cur_card + 1] > 0 && cards[cur_card + 2] > 0) {
			return true;
		}
	
		// 小小大绞
		if (cards[cur_card] == 2 && cards[cur_card + 10] >= 1) {
			return true;
		}
	
		// 大大小绞
		if (cards[cur_card + 10] == 2) {
			return true;
		}
	
		// 2 7 10
		if (cur_card == 1 && cards[6] > 0 && cards[9] > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean check_gang(char[] cards, int cur_card){
		if (cards[cur_card] == 3) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查是否可以碰，如果是自己抓的则为
	 * @param cards
	 * @param cur_card
	 * @return
	 */
	public static boolean check_peng(char[] cards, int cur_card){
		if (cards[cur_card] == 2) {
			return true;
		}
		return false;
	}
	
	public static boolean check_hu(char[] cards, int cur_card){
		char[] temp = new char[20];
		System.arraycopy(cards, 0, temp, 0, 20);
		++temp[cur_card];
		int huxi = get_huxi(temp);
		if (huxi > -1) {
			return true;
		}
		return false;
	}
	
	public static int get_huxi(char[] cards) {
		int huxi = get_kan_huxi(cards);

		int sum = 0;
		for (int i = 0; i < 20; i++) {
			sum += cards[i];
		}

		// 不带将
		if (sum % 3 == 0) {
			// 除坎以外的顺子的胡息
			int other_huxi = get_shun_huxi_xiao(cards);
			if (other_huxi < 0) {
				return -1;
			}
			return huxi + other_huxi;
		}

		// 带将
		int max_huxi = -1;
		char tmp_cards[] = new char[20];
		for (int i = 0; i < 20; i++) {
			if (cards[i] != 2) continue;
			memcpy(tmp_cards, cards, 0);
			//memcpy(tmp_cards, cards, 20);
			tmp_cards[i] -= 2;
			int other_huxi = get_shun_huxi_xiao(tmp_cards);
			System.out.println("other_huxi="+other_huxi);
			if (other_huxi > max_huxi) max_huxi = other_huxi;
		}

		if (max_huxi < 0) return -1;

		return max_huxi + huxi;
	}

	// 获取坎牌的胡息，大坎6，小坎3
	private static int get_kan_huxi(char[] cards) {
		int huxi = 0;
		for (int i = 0; i < 10; ++i) {
			if (cards[i] != 3) continue;
			cards[i] = 0;
			huxi += 3;
		}
		
		for (int i = 10; i < 20; ++i) {
			if (cards[i] != 3) continue;
			cards[i] = 0;
			huxi += 6;
		}
		return huxi;
	}

	private static int get_shun_huxi_xiao(char[] cards) {
		Item items[] = new Item[7];
		for(int i=0;i<items.length;i++){
			items[i] = new Item();
		}

		int cur_card = 0;
		int cur_item = -1;
		int find = 1;
		int max_huxi = -1;

		// 先拆小的
		while (true) {
			if (find == 1) {
				find = 0;
				int finded = 0;
				if (cur_item == 7) {
					//*(int*)0 = 0;
				}
				// 找到第一张牌
				for (int i = cur_card; i < 10; i++) {
					if (cards[i] != 0) {
						cur_card = i;
						++cur_item;
						items[cur_item].setCard(cur_card);
						finded = 1;
						break;
					}
				}
				if (finded == 0) {
					// 小牌胡息
					int huxi = 0;
					for (int i = 0; i <= cur_item; ++i) {
						huxi += items[i].getHuxi();
					}
					int da_huxi = get_shun_huxi_da(cards);
					if (da_huxi >= 0) {
						if (da_huxi + huxi > max_huxi) {
							max_huxi = da_huxi + huxi;
						}
					}
					int r = huisu(cards,items,cur_item,cur_card);
					if(r == -1){
						break;
					}
				}
			}

			// 顺子
			if (items[cur_item].getI() == 0) {
				items[cur_item].setI(1);
				if (cur_card < 8 && cards[cur_card + 1] > 0 && cards[cur_card + 2] > 0) {
					items[cur_item].setJ(1);
					items[cur_item].setCard(cur_card);
					--cards[cur_card];
					--cards[cur_card + 1];
					--cards[cur_card + 2];
					find = 1;
					if (cur_card == 0) {
						items[cur_item].setHuxi(3);
					}
					continue;
				}
			}

			// 小小大绞
			if (items[cur_item].getI() == 1) {
				items[cur_item].setI(2);
				if (cards[cur_card] == 2 && cards[cur_card + 10] >= 1) {
					items[cur_item].setJ(2);
					cards[cur_card] -= 2;
					--cards[cur_card + 10];
					find = 1;
					continue;
				}
			}

			// 大大小绞
			if (items[cur_item].getI() == 2) {
				items[cur_item].setI(3);
				if (cards[cur_card + 10] == 2) {
					items[cur_item].setJ(3);
					cards[cur_card+10] -= 2;
					--cards[cur_card];
					find = 1;
					continue;
				}
			}

			// 2 7 10
			if (items[cur_item].getI() == 3) {
				items[cur_item].setI(4);
				if (cur_card == 1 && cards[6] > 0 && cards[9] > 0) {
					items[cur_item].setJ(4);
					items[cur_item].setHuxi(3);

					find = 1;
					--cards[1];
					--cards[6];
					--cards[9];
					continue;
				}
			}
			
			int r = huisu(cards,items,cur_item,cur_card);
			if(r == -1){
				break;
			}
		}
		return max_huxi;
	}

	private static int huisu(char[] cards,Item items[],int cur_item,int cur_card){	// 回溯
		if (cur_item < 0) return -1;

		if (items[cur_item].getI() == 0 || (items[cur_item].getI() == 4 && items[cur_item].getJ() != 4)) {
			items[cur_item].memset(0);
			//memset(&items[cur_item], 0, sizeof(struct Item));
			if (cur_item == 0) return -1;
			--cur_item;
			return huisu(cards,items, cur_item, cur_card);
		}

		cur_card = items[cur_item].getCard();
		if (items[cur_item].getI() > 0 && items[cur_item].getI() == items[cur_item].getJ()) {
			items[cur_item].setHuxi(0);
			if (items[cur_item].getJ() == 1) {
				++cards[cur_card];
				++cards[cur_card + 1];
				++cards[cur_card + 2];
			}
			else if (items[cur_item].getJ() == 2) {
				cards[cur_card] += 2;
				++cards[cur_card + 10];
			}
			else if (items[cur_item].getJ() == 3) {
				cards[cur_card + 10] += 2;
				++cards[cur_card];
			}
			else if (items[cur_item].getJ() == 4) {
				++cards[1];
				++cards[6];
				++cards[9];
				
				items[cur_item].memset(0);
				//memset(&items[cur_item], 0, sizeof(struct Item));
				if (cur_item == 0) return -1;
				--cur_item;
				return huisu(cards,items, cur_item, cur_card);
			}
		}
		return 0;
	}

	private static int get_shun_huxi_da(char[] cards) {
		if (cards[10] > cards[11] || cards[10] > cards[12]) return -1;
		
		int sum = 0;
		for (int i = 10; i < 20; ++i) {
			sum += cards[i];
		}
		if (sum == 0) {
			return 0;
		}

		// 只需要拆2 7 10 和顺子
		char tmp_cards[] = new char[10];
		memcpy(tmp_cards, cards, 10);
		//memcpy(tmp_cards, &cards[10], 10);

		int n_123 = tmp_cards[0];
		tmp_cards[0] = 0;
		tmp_cards[1] -= n_123;
		tmp_cards[2] -= n_123;
		int max_huxi = -1;

		for (int i = 0; i < 5; ++i) {
			memcpy(tmp_cards, cards, 10);
			//memcpy(tmp_cards, &cards[10], 10);
			tmp_cards[0] = 0;
			tmp_cards[1] -= n_123;
			tmp_cards[2] -= n_123;
			if (tmp_cards[1] < i || tmp_cards[6] < i || tmp_cards[9] < i) {
				break;
			}

			int huxi = get_shun_huxi_da_without_2_7_10(tmp_cards, i);
			if (huxi < 0) continue;
			if (huxi + n_123 * 6 > max_huxi) max_huxi = huxi + n_123 * 6;
		}
		return max_huxi;
	}
	
	//
	private static int get_shun_huxi_da_without_2_7_10(char[] cards, int num) {
		char tmp_cards[] = new char[10];
		memcpy(tmp_cards, cards, 0);
		//memcpy(tmp_cards, cards, 10);
		tmp_cards[1] -= num;
		tmp_cards[6] -= num;
		tmp_cards[9] -= num;
		
		for (int i = 0; i < 10; ++i) {
			int n = tmp_cards[i];
			if (n == 0) continue;
			if (i + 2 >= 10) return -1;
			if (tmp_cards[i + 1] < n || tmp_cards[i + 2] < tmp_cards[i + 1]) return -1;

			tmp_cards[i + 1] -= n;
			tmp_cards[i + 2] -= n;
		}

		return num * 6;
	}
	
	public static void main(String[] args) {
		System.out.println(PhzLib.get_huxi(new char[]{1,1,1,0,3,0,3,0,0,0,
				1,2,1,0,0,0,1,0,0,1}));
	}
}
