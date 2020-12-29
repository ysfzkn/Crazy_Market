
public interface MyQueue<T> extends Iterable<T>{
	
	/**kuyruktaki toplam eleman sayisi*/
	int size();

	/**kuyruk boş mu diye kontrol eder */
	boolean isEmpty();

	/**kuyrugun sonuna item ekler
	 * @throws Exception*/
	boolean enqueue(T item) throws Exception;
	
	/** kuyrugun basindan eleman cikarir*/
	T dequeuNext();
	
	/** tekerleme metnini kullanarak bir sonraki elemani secer*/
	T dequeuWithCounting(String tekerleme);

}
