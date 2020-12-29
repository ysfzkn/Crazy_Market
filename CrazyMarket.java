/** Yusuf Özkan - 19120205038 
 *  Market Simulation with implement Circular Array Queue.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CrazyMarket implements MyQueue<Customer>
{
	private int bas ;
	private int son ; 
	private int sayi ;
	private Customer[] myqueue;
	private double zaman; 
	private Customer customer;

	public static void main(String[] args) 
	{
		new CrazyMarket(10);
	}

	/**default tekerleme * */                     
	
	String tekerleme = "O piti piti karamela sepeti " + "\nTerazi lastik jimnastik "
			+ "\nBiz size geldik bitlendik hamama gittik temizlendik.";

	/**
	 * numberOfCustumer ile verilen sayida musteri hizmet gorene kadar calismaya
	 * devam eder
	 */

	public CrazyMarket(int numberOfCustomer) 
	{
		bas = -1;
		son = -1; 
		sayi = 0; // kuyruktaki anlık eleman sayısı
		myqueue = new Customer[numberOfCustomer];

		zaman = 0d 			 ;
		int hizmetgoren = 0  ;
		double musaitmi = 0d ;
		double bekleme = 0;
		enqueue(new Customer());
		 // queue initialized.
		
		while(hizmetgoren < numberOfCustomer) // verilen sayı kadar müşteri hizmet alana kadar döner.
		{
			if(!isEmpty() && musaitmi <= zaman-musaitmi) // kasa müsaitse işleme alınır.
			{
				customer = chooseCustomer(); 				// dequeue işlemleri süreye göre yapılır.
				musaitmi += customer.getserviceTime() ;
				zaman += customer.getarrivalTime() + customer.getserviceTime() ;
				bekleme = zaman - customer.getarrivalTime() ;
				System.out.println(customer.toString()+ " has been waited : "+bekleme+" s");
				hizmetgoren++ ;
			}
			else
			{
				enqueue(new Customer()); // rastgele sayılar customer.java da üretilir .
				zaman += myqueue[bas].getarrivalTime() ;
			}
		}
	}

	/**
	 * numberOfCustumer ile verilen sayida musteri hizmet gorene kadar calismaya
	 * devam eder, calisirken verilen tekerlemeyi kullanir
	 */
	
	public CrazyMarket(int numberOfCustomer, String tekerleme) 
	{
		this.tekerleme = tekerleme ; // bununla başlatırılırsa default tekerleme alınmaz..
		
		bas = -1;
		son = -1; 
		sayi = 0; // kuyruktaki anlık eleman sayısı
		myqueue = new Customer[numberOfCustomer];

		zaman = 0d 			 ;
		int hizmetgoren = 0  ;
		double musaitmi = 0d ;
		double bekleme = 0;
		enqueue(new Customer());
		 // queue initialized.
		
		while(hizmetgoren < numberOfCustomer) // verilen sayı kadar müşteri hizmet alana kadar döner.
		{
			if(!isEmpty() && musaitmi <= zaman-musaitmi)
			{
				customer = chooseCustomer(); 				// dequeue işlemleri süreye göre yapılır.
				musaitmi += customer.getserviceTime() ;
				zaman += customer.getarrivalTime() + customer.getserviceTime() ;
				bekleme = zaman - customer.getarrivalTime() ;
				System.out.println(customer.toString()+ " has been waited : "+bekleme+" s");
				hizmetgoren++ ;
			}
			else
			{
				enqueue(new Customer()); // rastgele sayılar customer.java da üretilir .
				zaman += myqueue[bas].getarrivalTime() ;
			}
		}
	}

	/**
	 * kuyrugun basindaki musteriyi yada tekerleme ile buldugu musteriyi return eder
	 */
	public Customer chooseCustomer()
	{
		Customer eleman;
		double waitingTime = (zaman - myqueue[bas].getarrivalTime()) ; // müşterinin bekleme zamanını hesaplar ona göre seçer
		
		if(waitingTime > 10)
		{	
			eleman = dequeuNext();
		}
		else
		{
			eleman = dequeuWithCounting(tekerleme);
		}

		return eleman;
	}

	/** tekerlemede kac sesli harf olduğunu sayar */
	public static int kacSesli(String tekerleme) 
	{
		String sesliler = "aeoöuüıiAEOÖUÜIİ";
		int toplam = 0;
		for(int i=0 ; i < tekerleme.length() ; i++)
		{
			if(sesliler.indexOf(tekerleme.charAt(i))!=-1)
			{
				toplam++;
			}
		}
		return toplam;
	}     

	@Override
	public Iterator<Customer> iterator() 
	{
		return new Iterator<Customer>()
		{
			private int current = bas;

			@Override
			public boolean hasNext() 
			{
				return current < size() && myqueue[current] != null; 
			}

			@Override
			public Customer next()
			{
				Customer data ;
				if(!hasNext())
				{
					throw new NoSuchElementException();
				}
				data = myqueue[current % myqueue.length] ;
				current++ ;
				return data ; // circular olduğundan mod alıp diğer elemana geçmek gerek.
			}

		};
	}

	@Override
	public boolean isEmpty() 
	{
		return (sayi == 0); // boş ise true
	}

	@Override
	public boolean enqueue(Customer item) 
	{
		if (isFull())
		{
			genislet();
			//throw new IndexOutOfBoundsException("Queue is full, cannot insert new element !") ;
		}
		else if( bas == -1 && son == -1)
		{
			bas = 0;
			son = 0;
			myqueue[son] = item ;
			sayi++;
		}
		else
		{
			son = (son+1) % myqueue.length ; // circular olduğundan mod alınır.
			myqueue[son] = item ;
			sayi++;
		}
			
		return true; 
	}

	/** Eğer sınır aşılır ise kuyruğu 2 katına çıkarır. */
	private void genislet() 
	{
		Customer[] genis = new Customer[myqueue.length *2];   

		for(int i=0; i < sayi; i++)
		{
			genis[i] = myqueue[bas];
			bas=(bas+1) % myqueue.length;
		}

		// System.out.println(genis);
		bas = 0;
		son = sayi;
		myqueue = genis;
	}

	@Override
	public Customer dequeuWithCounting(String tekerleme) 
	{
		Customer secilen = myqueue[bas];

		if (isEmpty())
		{
			throw new IndexOutOfBoundsException("Queue is empty, cannot delete element !") ; // myqueue boş ise hata verir
		}
		else if(bas == son) // sadece 1 eleman kalması durumu
		{
			myqueue[bas] = null;
			sayi-- ;
			bas = -1;
			son = -1;
		}
		else 
		{
			int ilerleme = (kacSesli(tekerleme)) % size();
			int secilenIndex=(bas+ilerleme-1) % myqueue.length; // ilerleme -1 yapmamın nedeni indexlerin 0'dan başlamasıdır.
			secilen = myqueue[secilenIndex];
			aradanSil(secilenIndex);
			sayi--;
		}

		return secilen;
	}

	/** Tekerleme ile bulunan elemanı circular arrraydan çıkarır */

	private void aradanSil(int secilenIndex)  
	{
		int son2 = son;

		for (int i = secilenIndex; i <= myqueue.length; i++) 
		{
			
			if(((i+1) % myqueue.length) == bas) // son elemandan baştakine kadar shift edilir.
			{
				myqueue[i % myqueue.length] = null; // shift edildikten sonra boşalan yer null yapılır.
				break;
			}

			if(myqueue[i% myqueue.length] != null)
			{
				myqueue[i % myqueue.length] = myqueue[((i + 1) % myqueue.length)];
				
				if(myqueue[((i + 1) % myqueue.length)]!= null)
				{
					son2 = i % myqueue.length ;
				}
			}
		}
		son = son2;
	}

	@Override
	public Customer dequeuNext() 
	{
		Customer silinen = myqueue[bas];

		if (isEmpty()) // underflow condition
		{
			throw new IndexOutOfBoundsException("Queue is empty, cannot delete element !") ; // myqueue boş ise hata verir
		}
		else if(bas == son) // sadece 1 eleman kalması durumu
		{
			myqueue[bas] = null;
			sayi-- ;
			bas = -1;
			son = -1;
		}
		else
		{
			myqueue[bas] = null;

			bas = (bas+1) % myqueue.length ;
			sayi -- ;
		}
		return silinen;
	}

	private boolean isFull()
	{
		return (size() == myqueue.length || ((son+1)%myqueue.length == bas)) ; // overflow condidition
	}

	@Override
	public String toString() // kuyruğu stringe çevirir
	{
		String str = "[";

		for(Customer e : myqueue)
		{	
			str += e + ",";
		}
		str += "]";
		return str;
    }

	@Override
	public int size() 
	{
		return sayi;
	}

	public void display()
	{
		if(!isEmpty())
		{
			int i=0;
			while(i != myqueue.length)
			{
				if(myqueue[i] != null)
				{
					// System.out.println(String.format("%.3g", myqueue[i].arrivalTime));
					System.out.print(myqueue[i] + " ");
					// i = (i+1) % myqueue.length ;
					i++ ;
				}
				else
				{
					// i = (i+1) % myqueue.length ;
					System.out.print("* ");
					i++ ;
				}
			}
				// System.out.println(String.format("%.3g",myqueue[son].arrivalTime));	
				System.out.println(" bas:"+bas+" son:" + son);
		}
		else
			System.out.print("Queue is empty !!"); 

		
	}
}

