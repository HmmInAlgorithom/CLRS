package chap11_HashTables;

import java.util.Arrays;

/**
 * 开放定址法哈希。
 * 默认的版本里，不支持从HashTable删除。这个例子要支持删除。（习题11.4-2）
 * @author xiuzhu
 * 160111
 */
public class HashTable_OpenAddressing {

	private int[] elements;	//hashtable存放元素的数组。
	private int[] delete_flag;	//标识一个hashtable中的index的元素的状态。-1:未使用过。 0:已删除。 1:被使用中，有元素。
	private int m;
	private int size = 0;	//hashtable中元素的个数
	
	public HashTable_OpenAddressing(int m_size){
		this.elements = new int[m_size];
		this.delete_flag = new int[m_size];
		m = m_size;
	}
	
	/**
	 * 插入数据
	 * @param k	待插入的元素
	 * @return	如果成功返回插入的位置。否则返回-1。
	 */
	public int insert(int k){
		if(size == m){
			System.out.println("Hash Table is full!");
			return -1;
		}
		else{
			size ++;
			int index = hash_doubleHashing(k, 0);
			for (int i = 1; delete_flag[index] == 1; i++) {
				index = hash_doubleHashing(k, i);
			}
			elements[index] = k;
			delete_flag[index] = 1;
			return index;
		}
	}
	
	/**
	 * 删除数据
	 * @param k	待删除的元素。
	 * @return  如果成功返回删除的位置。否则返回-1。
	 */
	public int delete(int k){
		int index = this.search(k);
		if(index != -1){
			delete_flag[index] = 0;
			size --;
		}
		return index;
	}
	
	/**
	 * 双重哈希probe的哈希函数
	 * @param ele 输入元素。
	 * @param i 第i次哈希
	 * @return 哈希函数的返回值
	 */
	private int hash_doubleHashing(int ele, int i){
		/**
		 *  h1(k) =  k mod m;
		 *	h2(k) = 1 + (k mod (m - 1));
		 */
		return (ele + i * (1 + (ele % (m - 1)))) % m;
	}
	
	/**
	 * 查找元素
	 * @param k 待查找的元素
	 * @return	-1 if not found, else index of k
	 */
	public int search(int k){
		int rest = -1;
		for (int i = 0; i < m; i++) {
			int temp = hash_doubleHashing(k, i);
			if(delete_flag[temp] == -1)
				break;	//not found
			else if (delete_flag[temp] == 0)	//被删除的标志位，需要继续search
				continue;
			else if (delete_flag[temp] == 1){
				if(elements[temp] == k){
					rest = temp;
				}
			}
		}
		return rest;
	}
	
	/**
	 * HashTable元素个数
	 * @return
	 */
	public int size(){
		return size;
	}
	
	//test
	public static void main(String[] args) {
		HashTable_OpenAddressing h = new HashTable_OpenAddressing(11);
		h.insert(4);
		h.insert(15);
		System.out.println(h.search(15));
		h.insert(26);
		h.delete(15);
		System.out.println(h.search(15));
		System.out.println(h.search(26));
		System.out.println(h.delete(26));
		System.out.println(h.size());
		h.delete(4);
		
		//10,22,31,4,15,28,17,88,59
		h.insert(10);
		h.insert(22);
		h.insert(31);
		h.insert(4);
		h.insert(15);
		h.insert(28);
		h.insert(17);
		h.insert(88);
		h.insert(59);
		h.insert(58);
		h.insert(57);
		h.insert(56);
		
		System.out.println(h.size());
		
		System.out.println(Arrays.toString(h.elements));
		System.out.println(Arrays.toString(h.delete_flag));
	}

}
